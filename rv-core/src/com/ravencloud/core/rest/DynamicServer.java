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

import java.lang.annotation.Annotation;

import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import lombok.Setter;

public class DynamicServer implements Server {
	
	@Setter private String url;
	
	@Setter private String description;
	
	@Setter private ServerVariable[] variables;
	
	@Setter private Extension[] extension;
	
	public DynamicServer() {
		
		url = "";
		
		description = "";
		
		variables = new ServerVariable[] {};
		
		extension = new Extension[] {};
	}
	
	@Override
	public Class<? extends Annotation> annotationType() {
		
		return DynamicServer.class;
	}
	
	@Override
	public String url() {
		
		return url;
	}
	
	@Override
	public String description() {
		
		return description;
	}
	
	@Override
	public ServerVariable[] variables() {
		
		return variables.clone();
	}
	
	@Override
	public Extension[] extensions() {
		
		return extension.clone();
	}
	
}
