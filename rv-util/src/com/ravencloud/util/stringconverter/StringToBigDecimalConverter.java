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
import java.math.BigDecimal;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.ravencloud.util.general.Condition;

public class StringToBigDecimalConverter extends TypeAdapter<BigDecimal>
	implements IStringToValueConverter<BigDecimal> {

	private static StringToBigDecimalConverter instance;
	
	public static synchronized StringToBigDecimalConverter getInstance() {
		
		if(Condition.empty(instance)) {
			
			instance = new StringToBigDecimalConverter();
		}
		
		return instance;
	}
	
	@Override
	public BigDecimal converter(String value, BigDecimal defaultValue) {

		return new BigDecimal(StringToNumberUtils.INSTANCE.converter(value, defaultValue).toString());
	}
	
	@Override
	public BigDecimal converter(String value) {
		
		return new BigDecimal(StringToNumberUtils.INSTANCE.converter(value).toString());
	}
	
	@Override
	public void write(JsonWriter out, BigDecimal value) throws IOException {
		
		out.value(value);
	}
	
	@Override
	public BigDecimal read(JsonReader in) throws IOException {

		return new BigDecimal(StringToNumberUtils.INSTANCE.read(in).toString());
	}
}
