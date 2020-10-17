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

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.HttpHeaders;

import com.ravencloud.core.bd.model.ActivityHistory;
import com.ravencloud.util.general.Condition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public enum ThreadContextRest implements AutoCloseable {

	INSTANCE;
	
	private ThreadLocal<ContextRest> threadLocal;

	private ThreadContextRest() {

		threadLocal = new ThreadLocal<>();
	}

	@AllArgsConstructor
	public class ContextRest {
		
		@Getter private HttpServletRequest request;
		
		@Getter private HttpHeaders headers;
		
		@Getter private ResourceInfo resourceInfo;
		
		@Getter @Setter private ActivityHistory activity;
	}

	public ThreadContextRest init(HttpServletRequest request, HttpHeaders headers, ResourceInfo resourceInfo) {

		if(Condition.empty(threadLocal.get())) {
			
			ActivityHistory activity = newActivityHistory(request, headers, resourceInfo);
			
			ContextRest context = new ContextRest(request, headers, resourceInfo, activity);
			
			threadLocal.set(context);
		}
		
		return this;
	}

	private ActivityHistory newActivityHistory(HttpServletRequest request, HttpHeaders headers,
		ResourceInfo resourceInfo) {

		ActivityHistory activity = new ActivityHistory();

		activity.setIp(request.getRemoteAddr());

		activity.setMethod(getNameMethod(resourceInfo));
		
		activity.setUser(CurrentUserRest.getUserByHeader(headers));

		return activity;
	}

	private static final String PATTERN_NAME_METHOD = "%s.%s";

	private String getNameMethod(ResourceInfo resourceInfo) {

		return String.format(PATTERN_NAME_METHOD, resourceInfo.getResourceClass().getSimpleName(),
			resourceInfo.getResourceMethod().getName());
	}

	public HttpServletRequest getRequest() {

		return threadLocal.get().getRequest();
	}

	public String getHeader(String name) {

		String value = null;
		
		if(!Condition.empty(threadLocal.get()) && !Condition.empty(threadLocal.get().getHeaders())) {
			
			value = threadLocal.get().getHeaders().getHeaderString(name);
		}
		
		return value;
	}

	public HttpHeaders getHeaders() {

		return threadLocal.get().getHeaders();
	}

	public ResourceInfo getResourceInfo() {
		
		return threadLocal.get().getResourceInfo();
	}
	
	public ActivityHistory getActivity() {

		ActivityHistory activity = null;
		
		if(!Condition.empty(threadLocal.get())) {
			
			activity = threadLocal.get().getActivity();
		}
		
		return activity;
	}

	@Override
	public void close() {

		threadLocal.remove();
	}
}
