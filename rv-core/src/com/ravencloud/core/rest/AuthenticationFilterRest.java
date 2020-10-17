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

import java.lang.reflect.Method;
import java.util.Set;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.ravencloud.core.exception.RestBadStatusException;
import com.ravencloud.core.exception.UnauthorizedException;
import com.ravencloud.core.rest.TypeRestServer.TypeURL;
import com.ravencloud.core.security.UserManager;

@Provider
public class AuthenticationFilterRest implements ContainerRequestFilter {

    @Context private HttpServletRequest request;

    @Context private HttpHeaders headers;

	@Context private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) {

		ThreadContextRest.INSTANCE.init(request, headers, resourceInfo);
		
		try {
			
			Method method = resourceInfo.getResourceMethod();
			
			checkServer(method, request.getRequestURL().toString());
			
			Set<String> rolesWithPermission = UserManager.INSTANCE.getRollAllowed();
			
			if(!method.isAnnotationPresent(PermitAll.class)
				&& !method.isAnnotationPresent(DenyAll.class)
				&& !rolesWithPermission.isEmpty()) {
				
				UserManager.INSTANCE.verifyToken(rolesWithPermission);
				
			} else if(method.isAnnotationPresent(DenyAll.class)) {
				
				throw new UnauthorizedException("The service has denied access");
			}
			
		} catch (RestBadStatusException ex) {
			
			requestContext.abortWith(ex.responseWithoutMessage());
		}
	}
	
	public void checkServer(Method method, String url) {
		
		TypeURL type = TypeURL.REST;
		
		if(method.isAnnotationPresent(TypeRestServer.class)) {
			
			type = method.getAnnotation(TypeRestServer.class).type();
		}
		
		if(!url.contains(type.getUrl())) {
			throw new RestBadStatusException(Response.Status.NOT_FOUND, "");
		}
	}
}
