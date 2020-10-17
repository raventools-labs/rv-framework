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

import static com.ravencloud.core.rest.ServiceUtils.setObservationsByKeyLocale;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ravencloud.core.bd.model.CredentialsRest;
import com.ravencloud.core.exception.UnauthorizedException;
import com.ravencloud.core.security.UserManager;
import com.ravencloud.core.util.InternalMessage;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;

@Hidden
@Path("login")
@Tag(name = "Login")
public class LoginService {

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public Response login() {

		Response response;
		
		try {
			
			CredentialsRest credentials = UserManager.INSTANCE.getCredentials();
			
			String token = UserManager.INSTANCE.login(credentials);
			
			setObservationsByKeyLocale(InternalMessage.info.loginOk);
			
			response = Response.ok(token, MediaType.TEXT_PLAIN).build();
			
		} catch (UnauthorizedException ex) {
			
			response = ex.response();
		}

		return response;
    }
}
