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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ravencloud.util.general.Condition;
import com.ravencloud.util.general.InitMonitor;
import com.ravencloud.util.session.SessionManager;

public abstract class AbstractFilter implements Filter {
	
	protected static final String EXCLUDE_EXT = "excludedExt";
	
	private static final String DEFAULT_EXT_EXCLUDE = "css, js, png, jpg";
	
	private Set<String> excluded;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		excluded = new HashSet<>();
		
		String excludedString = filterConfig.getInitParameter(EXCLUDE_EXT);
		
		if(Condition.empty(excludedString)) excludedString = DEFAULT_EXT_EXCLUDE;
		
		excluded.addAll(
			Arrays.asList(Arrays.stream(excludedString.split(",")).map(String::trim).toArray(String[]::new)));
	}
	
	boolean isExcluded(HttpServletRequest request) {
		
		String path = request.getRequestURI();
		
		String extension = path.substring(path.indexOf('.', path.lastIndexOf('/')) + 1).toLowerCase(Locale.ENGLISH);
		
		return excluded.contains(extension);
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
		throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		SessionManager.INSTANCE.setSession(request.getSession());
		
		InitMonitor.INSTANCE.waitForInit();
		
		if(!isExcluded(request)) {
			
			filter(request, response, chain);
			
		} else {
			
			chain.doFilter(request, response);
		}
	}
	
	protected abstract void filter(HttpServletRequest request, HttpServletResponse response,
		FilterChain chain) throws IOException, ServletException;
}
