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
package com.ravencloud.core.rest;

import javax.ws.rs.core.HttpHeaders;

import com.auth0.jwt.JWT;
import com.ravencloud.core.bd.dao.RestDao;
import com.ravencloud.core.bd.model.User;
import com.ravencloud.core.security.UserManager;
import com.ravencloud.util.general.Condition;
import com.ravencloud.util.general.ICurrentUser;
import com.ravencloud.util.session.SessionManager;
import com.ravencloud.util.session.SessionParameter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurrentUserRest implements ICurrentUser {
	
	private static CurrentUserRest instance;
	
	public static synchronized CurrentUserRest getInstance() {
		
		if(Condition.empty(instance)) instance = new CurrentUserRest();
		
		return instance;
	}
	
	@Override
	public String getNameUser() {
		
		return getUserByAuthorizationHeader(
			ThreadContextRest.INSTANCE.getHeader(UserManager.AUTHORIZATION_PROPERTY));
	}
	
	public User getUser() {
		
		return RestDao.INSTANCE.getUser(getNameUser());
	}
	
	public static String getUserByHeader(HttpHeaders header) {
		
		return getUserByAuthorizationHeader(header.getHeaderString(UserManager.AUTHORIZATION_PROPERTY));
	}
	
	public static String getUserByAuthorizationHeader(String authorizationHeader) {
		
		String user = null;
		
		String token = SessionManager.INSTANCE.get(SessionParameter.TOKEN);
		
		if(isValidSessionOrHeader(authorizationHeader,token)) {
			
			if(!Condition.empty(token)) {
				
				user = getUserByToken(token);
				
			} else if(isAuthenticationBearer(authorizationHeader)) {
				
				token = UserManager.INSTANCE.getToken(authorizationHeader);
				
				user = getUserByToken(token);
				
			} else {
				
				user = UserManager.INSTANCE.getCredentials(authorizationHeader).getName();
			}
		} 
		
		return user;
	}
	
	private static boolean isValidSessionOrHeader(String authorizationHeader, String token) {
		
		return !Condition.empty(token) ||
			(!Condition.empty(authorizationHeader)
				&& (isAuthenticationBearer(authorizationHeader) || isAuthenticationBasic(authorizationHeader)));
	}
	
	private static boolean isAuthenticationBearer(String authorizationHeader) {
		
		return authorizationHeader.contains(UserManager.AUTHENTICATION_SCHEME_BEARER);
	}
	
	private static boolean isAuthenticationBasic(String authorizationHeader) {
		
		return authorizationHeader.contains(UserManager.AUTHENTICATION_SCHEME_BASIC);
	}
	
	private static String getUserByToken(String token) {
		
		String user = null;
		
		try {
			
			user = JWT.decode(token).getSubject();
			
		} catch (Exception ex) {
			
			log.warn(ex.getMessage());
		}
		
		return user;
	}
}
