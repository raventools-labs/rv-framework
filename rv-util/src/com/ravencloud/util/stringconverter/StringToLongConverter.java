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

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.ravencloud.util.general.Condition;

public class StringToLongConverter extends TypeAdapter<Number> implements IStringToValueConverter<Long> {

	private static StringToLongConverter instance;
	
	public static synchronized StringToLongConverter getInstance() {
		
		if(Condition.empty(instance)) {
			
			instance = new StringToLongConverter();
		}
		
		return instance;
	}
	
	@Override
	public Long converter(String value, Long defaultValue) {

		return StringToNumberUtils.INSTANCE.converter(value, defaultValue).longValue();
	}
	
	@Override
	public Long converter(String value) {

		return StringToNumberUtils.INSTANCE.converter(value).longValue();
	}
	
	@Override
	public void write(JsonWriter out, Number value) throws IOException {
		
		StringToNumberUtils.INSTANCE.write(out, value);
	}
	
	@Override
	public Number read(JsonReader in) throws IOException {
		
		return StringToNumberUtils.INSTANCE.read(in).longValue();
	}
}
