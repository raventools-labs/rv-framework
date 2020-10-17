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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ravencloud.core.bd.model.User;
import com.ravencloud.core.iu.ManagerViews;
import com.ravencloud.core.iu.annotation.View;
import com.ravencloud.core.iu.model.IModuleView;
import com.ravencloud.core.rest.CurrentUserRest;
import com.ravencloud.util.general.Condition;
import com.ravencloud.util.session.SessionManager;

@WebFilter(filterName = "Security Module filter", urlPatterns = { SecurityModuleFilter.URL_PATTERN_MODULE })
public class SecurityModuleFilter extends AbstractFilter {
	
	protected static final String URL_PATTERN_MODULE = "/m/*";
	
	public static final String MODULE_EXTENSION = ".ui";
	
	private static final Pattern PATTERN_URL = Pattern.compile(".*(/)([\\w|_]*)\\.ui");
	
	private static final int POSITION_MODULE = 2;
	
	@Override
	protected void filter(HttpServletRequest request, HttpServletResponse response,
		FilterChain chain)
		throws IOException, ServletException {
		
		Matcher match = PATTERN_URL.matcher(request.getPathInfo());

		if(match.find()) {
			
			String nameModule = match.group(POSITION_MODULE);
			
			IModuleView<?> module = ManagerViews.INSTANCE.getViews().get(nameModule);
			
			String role = module.getClass().getAnnotation(View.class).role();
		
			if(!Condition.empty(role)) {
				
				filterUser(request, response, chain, role);

			} else {
				
				chain.doFilter(request, response);
			}
			
		} else {
			
			chain.doFilter(request, response);
		}
	}
	
	private static void filterUser(HttpServletRequest request, HttpServletResponse response, FilterChain chain, String role)
		throws IOException, ServletException {
		
		if(SessionManager.INSTANCE.isSignIn()) {
			
			User user = CurrentUserRest.getInstance().getUser();
			
			if(user.getRoles().stream().anyMatch(r -> r.getRole().equals(role))) {
				
				chain.doFilter(request, response);
				
			} else {
				
				request.getRequestDispatcher("/m/401.ui").forward(request, response);
			}
			
		} else {
			
			request.getRequestDispatcher("/m/login.ui").forward(request, response);
		}
	}
}
