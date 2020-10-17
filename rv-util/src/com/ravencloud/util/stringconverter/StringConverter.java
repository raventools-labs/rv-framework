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
package com.ravencloud.util.stringconverter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.ravencloud.util.exception.NotImplementedException;
import com.ravencloud.util.general.Condition;

@SuppressWarnings("unchecked")
public enum StringConverter {
	
	INSTANCE;
	
	StringConverter() {}
	
	private Map<Class<?>,IStringToValueConverter<?>> converters;

	public <V> V converter(String value, Class<V> type, V defaultValue) {
		
		if(type.equals(String.class)) {
			
			return (V) Condition.evalNotEmpty(value, defaultValue);
			
		} else {
			
			return getConverter(type).converter(value, defaultValue);
		}
	}
	
	public <V> V converter(String value, Class<V> type) {
		
		if(type.equals(String.class)) {
			
			return (V) value;
			
		} else {
			
			return getConverter(type).converter(value);
		}
	}

	private <V> IStringToValueConverter<V> getConverter(Class<V> type) {
		
		IStringToValueConverter<V> converter = (IStringToValueConverter<V>) getConverters().get(type);
		
		if(converter == null) throw new NotImplementedException("Class: " + type.getCanonicalName());
		
		return converter;
	}
	
	public synchronized Map<Class<?>, IStringToValueConverter<?>> getConverters() {
		
		if(Condition.empty(converters)) {
			
			converters = new HashMap<>();
			
			converters.put(Boolean.class, StringToBooleanConverter.getInstance());
			
			converters.put(Long.class, StringToLongConverter.getInstance());
			
			converters.put(long.class, StringToLongConverter.getInstance());
			
			converters.put(Integer.class, StringToIntConverter.getInstance());
			
			converters.put(int.class, StringToIntConverter.getInstance());
			
			converters.put(BigDecimal.class, StringToBigDecimalConverter.getInstance());
		}
		
		return converters;
	}
}
