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
package com.ravencloud.core.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ravencloud.core.rest.ThreadContextRest;

import lombok.Getter;

public class RestBadStatusException extends RuntimeException {
	
	@Getter private final Status status;
	
	@Getter private final String internalMessage;
	
	public RestBadStatusException(Status status,String message) {
		this(status,message,message);
	}
	
	public RestBadStatusException(Status status,String message,String internalMessage) {
		
		super(message);
		
		this.status = status;
		
		this.internalMessage = internalMessage;
	}
	
	public Response response() {
		
		ThreadContextRest.INSTANCE.getActivity().setObservations(internalMessage);
		
		return Response.status(status).entity(getMessage()).build();
	}
	
	public Response responseWithoutMessage() {
		
		ThreadContextRest.INSTANCE.getActivity().setObservations(internalMessage);
		
		return Response.status(status).build();
	}
}
