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
package com.ravencloud.core.iu;

import java.util.HashMap;
import java.util.Map;

import com.ravencloud.core.iu.builder.AbstractElementViewBuilder;
import com.ravencloud.core.iu.builder.ActionViewBuilder;
import com.ravencloud.core.iu.builder.AttributeViewBuilder;

import lombok.Getter;

public enum TypeElementView {

	ATTRIBUTE("$",AttributeViewBuilder.class), //BUILDER
	ACTION("@",ActionViewBuilder.class); 
	
	@Getter private String token;
	
	@Getter private Class<? extends AbstractElementViewBuilder<?>> classBuilder;
	
	private TypeElementView(
		String token,
		Class<? extends AbstractElementViewBuilder<?>> classBuilder) {
		
		this.token = token;
		this.classBuilder = classBuilder;
	}
	
	private static Map<String,TypeElementView> mapToken;
	
	private static synchronized Map<String,TypeElementView> getMapToken() {
		
		if(mapToken == null) {
			
			mapToken = new HashMap<>();
			
			for(TypeElementView type : TypeElementView.values()) {
				
				mapToken.put(type.getToken(), type);
			}
		}
		
		return mapToken;
	}
	
	public static TypeElementView getType(String token) {
		
		return getMapToken().get(token);
	}
}
