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
package com.ravencloud.util.general;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ravencloud.util.exception.ProgrammingException;

public enum EnumUtilities {

	INSTANCE;
	
	private static final String FORMAT_KEY_ENUMS = "%s_%s";
	
	private static final String METHOD_GET_PROPERTY = "get%s";
	
	private Map<String,Map<String,Object>> mapsEnums;
	
	private EnumUtilities() {
		
		mapsEnums = new HashMap<>();
	}
	
	public <T extends Enum<T>> T value(Class<T> enumClass,String key,T defaultValue) {
		
		try {
			
			return Enum.valueOf(enumClass, key);
			
		} catch(Exception ex) {
			
			return defaultValue;
		}
	}
	
	public <T extends Enum<T>> T value(Class<T> enumClass,String key) {
		return value(enumClass, key, null);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Enum<T>> T getValueByProperty(Class<T> enumClass,String nameProperty,String property) {

		return (T) getMapsEnums(enumClass,nameProperty).get(property);
	}
	
	private synchronized <T extends Enum<T>> Map<String,Object> getMapsEnums(Class<T> enumClass,String nameProperty) {
		
		String key = String.format(FORMAT_KEY_ENUMS, enumClass.getSimpleName(),nameProperty);
		
		Map<String, Object> map = mapsEnums.get(key);
		
		if(map == null) {
			
			map = new HashMap<>();
			
			for(T _enum : enumClass.getEnumConstants()) {
				
				map.put(getValueOfMethod(_enum, nameProperty), _enum);
			}
			
			mapsEnums.put(key, map);
		}
		
		return map;
	}
	
	private <T extends Enum<T>> String getValueOfMethod(T enumValue, String nameProperty) {
		
		try {
			
			String method = String.format(METHOD_GET_PROPERTY, StringUtils.capitalize(nameProperty));
			
			return String.valueOf(enumValue.getClass().getMethod(method).invoke(enumValue));
			
		} catch (Exception ex) { throw new ProgrammingException("Fail to access property",ex); }
	}
}
