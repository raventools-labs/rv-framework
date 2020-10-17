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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.ravencloud.core.iu.annotation.MetaAttribute;
import com.ravencloud.core.iu.builder.InputViewBuilder;
import com.ravencloud.core.iu.model.IView;
import com.ravencloud.core.iu.model.SterotypeAttribute;
import com.ravencloud.util.exception.BuildException;
import com.ravencloud.util.general.Condition;


public enum InputUtils {
	
	INSTANCE;
	
	private Map<Class<?>, SterotypeAttribute> mapSterotypeByClass;
	
	private InputUtils() {
		
		mapSterotypeByClass = new HashMap<>();
		
		mapSterotypeByClass.put(String.class, SterotypeAttribute.TEXT);
	}
	
	public InputViewBuilder builder(Class<? extends IView> classView, String property) {
		
		try {
			
			Field field = classView.getDeclaredField(property);
			
			SterotypeAttribute sterotype = getSterotype(field.getType());
			
			if(field.isAnnotationPresent(MetaAttribute.class)
				&& !Condition.empty(field.getAnnotation(MetaAttribute.class).type())) {
				
				sterotype = field.getAnnotation(MetaAttribute.class).type();
			}
			
			return (InputViewBuilder) sterotype.getClassBuilder()
				.getDeclaredConstructor(Class.class,String.class)
				.newInstance(classView, property);
			
		} catch (Exception ex) {
			
			throw new BuildException(ex);
		}
		
	}
	
	public SterotypeAttribute getSterotype(Class<?> type) {
		
		SterotypeAttribute sterotype = mapSterotypeByClass.get(type);
		
		if(Condition.empty(sterotype)) sterotype = SterotypeAttribute.TEXT;
		
		return sterotype;
	}
}
