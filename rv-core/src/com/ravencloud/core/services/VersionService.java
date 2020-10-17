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

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ravencloud.util.general.App;

import io.swagger.v3.oas.annotations.tags.Tag;

@Path("version")
@Tag(name = "Version")
public class VersionService {

	@GET
 	@PermitAll
 	@Produces(MediaType.TEXT_PLAIN)
    public Response version() {
		
		return Response.ok(App.INSTANCE.version(), MediaType.TEXT_PLAIN).build();
	}
}
