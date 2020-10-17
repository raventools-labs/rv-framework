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

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ravencloud.core.bd.dao.OrganizationDao;
import com.ravencloud.core.rest.RestServlet;
import com.ravencloud.util.db.OrganizationDatabaseSelector;
import com.ravencloud.util.general.App;

@WebFilter(
	filterName = "Rest filter",
	urlPatterns = {
		RestServlet.URL_PATTERN_REST,
		RestServlet.URL_PATTERN_MODULE
	},
	dispatcherTypes = {
		DispatcherType.FORWARD,
		DispatcherType.REQUEST,
		DispatcherType.INCLUDE,
		DispatcherType.ERROR
	}
)
public class RestFilter extends AbstractFilter {

	@Override
	protected void filter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		
		String organizationDatabase = "";
		
		if(App.INSTANCE.multiOrganization()) {
			
			organizationDatabase = OrganizationDao.INSTANCE.getByDomain(request.getServerName()).getPreffixDatabase();
		}
		
		OrganizationDatabaseSelector.INSTANCE.set(organizationDatabase);
		
		chain.doFilter(request, response);
	}
}
