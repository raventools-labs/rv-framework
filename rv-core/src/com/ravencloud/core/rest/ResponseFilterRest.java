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

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.ravencloud.core.bd.model.ActivityHistory;
import com.ravencloud.util.db.GenericDao;
import com.ravencloud.util.general.Condition;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class ResponseFilterRest implements ContainerResponseFilter {

	@Context private ResourceInfo resourceInfo;
	
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
	    throws IOException {

		try (ThreadContextRest threadContext = ThreadContextRest.INSTANCE) {
			
			ActivityHistory activity = threadContext.getActivity();
			
			if(!Condition.empty(activity)) {
				
				activity.setStatus(responseContext.getStatus());
				
				if(responseContext.getStatus() != Status.OK.getStatusCode()
					&& Condition.empty(activity.getObservations())) {
					
					activity.setObservations(responseContext.getEntity().toString());
				}
				
				GenericDao.INSTANCE.persist(activity);
			}
		}
    }
}
