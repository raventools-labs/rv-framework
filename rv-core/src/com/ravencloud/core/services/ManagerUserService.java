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
package com.ravencloud.core.services;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.ravencloud.core.rest.CurrentUserRest;
import com.ravencloud.core.rest.ThreadContextRest;
import com.ravencloud.core.security.UserManager;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("manager/user")
@Tag(name = "Manager user")
public class ManagerUserService {

	private static final String RESPONSE_OK_CHANGE_PASSWORD = "Password change sucessful";
	
	@Context
	private HttpServletRequest request;
	
	@Context
	private ResourceInfo resourceInfo;
	
	@GET
	@RolesAllowed("ACTIVE")
	@Path("changePassword")
	public Response changePassword(@QueryParam(value = "newPassword") String newPassword) {

		Response response;
		
		try {
			
			UserManager.INSTANCE.changePassword(CurrentUserRest.getInstance().getNameUser(), newPassword);
			
			ThreadContextRest.INSTANCE.getActivity().setObservations(RESPONSE_OK_CHANGE_PASSWORD);
			
			response = Response.ok(RESPONSE_OK_CHANGE_PASSWORD).build();
			
		} catch(Exception ex) {
			
			log.error("Problem with change password", ex);
				
			response = Response.serverError().entity("Service no enabled").build();
		}
		
		return response;
	}
	
	@GET
	@RolesAllowed("ACTIVE")
	@Path("regenerateTotp")
	public Response regenerateTotp() {

		Response response;
		
		try {
			
			UserManager.INSTANCE.regenerateTotp(CurrentUserRest.getInstance().getNameUser());
			
			ThreadContextRest.INSTANCE.getActivity().setObservations(RESPONSE_OK_CHANGE_PASSWORD);
			
			response = Response.ok().build();
			
		} catch(Exception ex) {
			
			log.error("Problem with regenerate TOTP", ex);
				
			response = Response.serverError().entity("Service no enabled").build();
		}
		
		return response;
	}
}
