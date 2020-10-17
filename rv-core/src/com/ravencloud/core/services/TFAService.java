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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ravencloud.core.bd.model.Role;
import com.ravencloud.core.bd.model.User;
import com.ravencloud.core.rest.CurrentUserRest;
import com.ravencloud.core.rest.ThreadContextRest;
import com.ravencloud.core.security.TOTPManager;

import io.swagger.v3.oas.annotations.tags.Tag;

@Path("tfa")
@Tag(name = "tfa")
public class TFAService {

	@GET
	@RolesAllowed(Role.ACTIVE)
	@Produces("image/png")
    public Response version() {
		
		try {
			
			User user = CurrentUserRest.getInstance().getUser();
			
			String email = user.getEmail();
			
			String totpKey = user.getCredentials().getTotpKey();
			
			String domain = ThreadContextRest.INSTANCE.getRequest().getServerName();
			
			return Response.ok(TOTPManager.INSTANCE.createQRCode(email, totpKey, domain, 
				200, 200), MediaType.APPLICATION_OCTET_STREAM).build();
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return Response.serverError().build();
		}
	}
}
