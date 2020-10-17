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

import javax.ws.rs.Path;

import com.ravencloud.core.rest.RestServlet;
import com.ravencloud.core.security.UserManager;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(servers = @Server(url = RestServlet.URL_PATTERN_REST))
@SecurityScheme(
	name = UserManager.AUTHENTICATION_SCHEME_BEARER, scheme = "bearer", type = SecuritySchemeType.HTTP,
	in = SecuritySchemeIn.HEADER, bearerFormat = UserManager.BEARER_FORMAT
)
@Path("/openapi.{type:json|yaml}")
public class OpenApi extends OpenApiResource {}
