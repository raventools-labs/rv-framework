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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lombok.Getter;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TypeRestServer {
	
	public enum TypeURL {
		
		REST(RestServlet.PATH_REST),
		MODULE(RestServlet.PATH_MODULE);
		
		@Getter private String url;
		
		private TypeURL(String url) {
			
			this.url = url;
		}
	}
	
	public TypeURL type() default TypeURL.REST;
}
