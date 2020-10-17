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

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.glassfish.jersey.servlet.ServletContainer;

@WebServlet(
	urlPatterns = {
		RestServlet.URL_PATTERN_MODULE,
		RestServlet.URL_PATTERN_REST
	}, loadOnStartup = 1,
	initParams = {
		@WebInitParam(name = "javax.ws.rs.Application", value = "com.ravencloud.core.rest.RestConfig") }
)
public class RestServlet extends ServletContainer {
	
	public static final String PATH_REST = "/rest/";
	
	public static final String URL_PATTERN_REST = PATH_REST + "*";
	
	public static final String PATH_MODULE = "/m/";
	
	public static final String URL_PATTERN_MODULE = PATH_MODULE + "*";
}
