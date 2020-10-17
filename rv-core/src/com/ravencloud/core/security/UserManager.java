/**
 * Ravencloud, open source library.
 * Copyright (c) 2020 Alejandro Silva Sanahuja
 * mailto:asilva@ravencloud.es
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */
package com.ravencloud.core.security;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ResourceInfo;

import org.apache.commons.codec.digest.DigestUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ravencloud.core.bd.dao.RestDao;
import com.ravencloud.core.bd.model.CredentialsRest;
import com.ravencloud.core.bd.model.Role;
import com.ravencloud.core.bd.model.User;
import com.ravencloud.core.exception.UnauthorizedException;
import com.ravencloud.core.rest.ThreadContextRest;
import com.ravencloud.core.util.InternalMessage;
import com.ravencloud.core.util.Message;
import com.ravencloud.util.db.ThreadDBManager;
import com.ravencloud.util.db.UUIDGenerator;
import com.ravencloud.util.general.App;
import com.ravencloud.util.general.Condition;
import com.ravencloud.util.general.StringUtilities;
import com.ravencloud.util.session.SessionManager;
import com.ravencloud.util.session.SessionParameter;

public enum UserManager {

    INSTANCE;

	public static final String AUTHORIZATION_PROPERTY = "Authorization";

	public static final String AUTHENTICATION_SCHEME_BASIC = "Basic";

	public static final String AUTHENTICATION_SCHEME_BEARER = "Bearer";
	
	private static final Pattern PATTERN_AUTHENTICATION_SCHEME_BEARER = Pattern.compile(AUTHENTICATION_SCHEME_BEARER);

	public static final String BEARER_FORMAT = "JWT";

	private static final String CLAIM_ROLES = "roles";
	
	private static final String PROPERY_JWT_PASSWORD = "jwt.password";

	private static final long EXPIRATION_TIME_TOKEN_IN_MINUTES = 30L;

	private String secretPassword;

	private UserManager() {

		secretPassword = App.INSTANCE.getProperty(String.class, PROPERY_JWT_PASSWORD, UUIDGenerator.generate(this));
	}

	public CredentialsRest getCredentials(String strHeaderAuthorization) {

		CredentialsRest credentials = new CredentialsRest();

		if(!Condition.empty(strHeaderAuthorization)) {

			final String encodedUserPassword = strHeaderAuthorization.replaceFirst(AUTHENTICATION_SCHEME_BASIC + " ", "");

			String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword),
				StandardCharsets.UTF_8);

			final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");

			credentials.setName(tokenizer.nextToken());
			credentials.setPassword(tokenizer.nextToken());

		}

		return credentials;
	}

	public CredentialsRest getCredentials() {

		return getCredentials(ThreadContextRest.INSTANCE.getHeader(AUTHORIZATION_PROPERTY));
	}

	public String login(CredentialsRest credentials) {

		if(!Condition.empty(credentials.getName()) && !Condition.empty(credentials.getPassword())) {

			User user = RestDao.INSTANCE.getUser(credentials.getName());
			
			checkUser(user, credentials);
			
			Algorithm algorithm = Algorithm.HMAC256(secretPassword);
			
			String token = JWT.create().withKeyId(user
				.getId())
				.withSubject(user.getCredentials().getName())
				.withArrayClaim(CLAIM_ROLES, user.getRoles().stream().map(e -> e.getRole()).toArray(
					String[]::new))
				.withExpiresAt(getExpirationTimeToken()).sign(algorithm);
			
			SessionManager.INSTANCE.setSession(ThreadContextRest.INSTANCE.getRequest().getSession(true));
			
			SessionManager.INSTANCE.set(SessionParameter.TOKEN, token);
			
			return token;

		} else {

			throw new UnauthorizedException(Message.err.emptyCredentials);
		}
	}
	
	private static final int LENGHT_SALT = 10;
	
	public void changePassword(String nameUser, String newPasword) {
		
		try (ThreadDBManager threadDB = ThreadDBManager.INSTANCE.init()) {
			
			User user = RestDao.INSTANCE.getUser(nameUser);
			
			String salt = StringUtilities.format(UUIDGenerator.generate(user), LENGHT_SALT);
			
			String password = DigestUtils.sha256Hex(newPasword + salt);
			
			user.getCredentials().setPassword(password);
			
			user.getCredentials().setSalt(salt);
		}
	}
	
	public void regenerateTotp(String nameUser) {
		
		try (ThreadDBManager threadDB = ThreadDBManager.INSTANCE.init()) {
			
			User user = RestDao.INSTANCE.getUser(nameUser);
			
			user.getCredentials().setTotpKey(TOTPManager.INSTANCE.generateTOTKey());
		}
	}

	public void verifyToken(Set<String> rolesWithPermission) {

		String token = SessionManager.INSTANCE.get(SessionParameter.TOKEN);
		
		if(Condition.empty(token)) token = getToken(ThreadContextRest.INSTANCE.getHeader(SessionParameter.TOKEN));

		if(!Condition.empty(token)) {

			try {

				Algorithm algorithm = Algorithm.HMAC256(secretPassword);
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT jwt = verifier.verify(token);
				
				checkPermission(jwt.getClaim(CLAIM_ROLES).asList(String.class), rolesWithPermission);

			} catch (UnauthorizedException ex) {

				throw ex;

			} catch (Exception ex) {

				throw new UnauthorizedException(Message.err.badCredentials, ex.getMessage());
			}

		} else {

			throw new UnauthorizedException(Message.err.emptyCredentials, InternalMessage.err.emptyHeader);
		}
	}
	
	public String getToken(String authorizationHeader) {
		
		String token = null;
		
		if(!Condition.empty(authorizationHeader)) {
			
			token = PATTERN_AUTHENTICATION_SCHEME_BEARER.matcher(authorizationHeader).replaceFirst("");
		}
		
		return token;
	}
	
	public Set<String> getRollAllowed() {
		
		ResourceInfo resourceInfo = ThreadContextRest.INSTANCE.getResourceInfo();
		
		Method method = resourceInfo.getResourceMethod();
		
		Class<?> type = resourceInfo.getResourceClass();
		
		Set<String> rolesSet = new HashSet<>();
		
		if(method.isAnnotationPresent(RolesAllowed.class)) {
			
			rolesSet.addAll(Arrays.asList(method.getAnnotation(RolesAllowed.class).value()));
			
		} else if(type.isAnnotationPresent(RolesAllowed.class)) {
			
			rolesSet.addAll(Arrays.asList(type.getAnnotation(RolesAllowed.class).value()));
			
		}
		
		return rolesSet;
	}
	
	private void checkUser(User user, CredentialsRest credentials) {
		
		if(!Condition.empty(user)) {
			
			String preHash = credentials.getPassword() + user.getCredentials().getSalt();
			
			if(!user.getCredentials().getPassword().equals(DigestUtils.sha256Hex(preHash)) ||
				user.getRoles().stream().noneMatch(r -> r.getRole().equals(Role.ACTIVE))) {
				
				throw new UnauthorizedException(Message.err.badCredentials);
			}
			
		} else {
			
			throw new UnauthorizedException(Message.err.badCredentials);
		}
		
	}

	private void checkPermission(List<String> roles, Set<String> rolesWithPermission) {

		boolean permit = false;

		for(int i = 0; !permit && i < roles.size(); i++) {

			permit = rolesWithPermission.contains(roles.get(i));

		}

		if(!permit) {
			
			throw new UnauthorizedException(
				Message.err.badCredentials, InternalMessage.err.badCredentialsOrWithoutPermision);
		}
	}
	
	private Date getExpirationTimeToken() {

		LocalDateTime date = LocalDateTime.now().plusMinutes(EXPIRATION_TIME_TOKEN_IN_MINUTES);

		return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
	}
}
