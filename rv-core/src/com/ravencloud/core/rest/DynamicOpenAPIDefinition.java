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

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Setter;

public class DynamicOpenAPIDefinition implements OpenAPIDefinition {
	
	@Setter private Info info;
	
	@Setter private Tag[] tags;
	
	@Setter private Server[] servers;
	
	@Setter private SecurityRequirement[] security;
	
	@Setter private ExternalDocumentation externalDocs;
	
	@Setter private Extension[] extensions;
	
	public DynamicOpenAPIDefinition(OpenAPIDefinition openApi) {
		
		this.info = openApi.info();
		
		this.tags = openApi.tags();
		
		this.servers = openApi.servers();
		
		this.security = openApi.security();
		
		this.externalDocs = openApi.externalDocs();
		
		this.extensions = openApi.extensions();
	}
	
	@Override
	public Class<? extends Annotation> annotationType() {
		
		return DynamicOpenAPIDefinition.class;
	}
	
	@Override
	public Info info() {
		
		return info;
	}
	
	@Override
	public Tag[] tags() {
		
		return tags.clone();
	}
	
	@Override
	public Server[] servers() {
		
		return servers.clone();
	}
	
	@Override
	public SecurityRequirement[] security() {
		
		return security.clone();
	}
	
	@Override
	public ExternalDocumentation externalDocs() {
		
		return externalDocs;
	}
	
	@Override
	public Extension[] extensions() {
		
		return extensions.clone();
	}
	
}
