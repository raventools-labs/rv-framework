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
package com.ravencloud.core.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ravencloud.core.implemented.view.SwaggerView;
import com.ravencloud.util.general.App;

@WebFilter(filterName = "Rest IU filter", urlPatterns = { RedirectRestUIFilter.URL_PATTERN_REST_UI })
public class RedirectRestUIFilter extends AbstractFilter {
	
	protected static final String URL_PATTERN_REST_UI = "/rest-ui/*";
	
	@Override
	protected void filter(HttpServletRequest request, HttpServletResponse response,
		FilterChain chain)
		throws IOException, ServletException {
		
		response.sendRedirect(App.INSTANCE.contextPath() + "m/" + SwaggerView.MODULE_NAME + ".ui");
	}
	
}
