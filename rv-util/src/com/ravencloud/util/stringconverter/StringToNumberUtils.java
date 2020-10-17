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
import java.text.NumberFormat;
import java.text.ParseException;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.ravencloud.util.exception.ProgrammingException;
import com.ravencloud.util.general.Condition;
import com.ravencloud.util.session.SessionManager;

public enum StringToNumberUtils implements IStringToValueConverter<Number> {

	INSTANCE;
	
	@Override
	public Number converter(String value, Number defaultValue) {

		Number numberValue;
		
		try {
			
			numberValue = Condition.evalNotEmpty(sessionNumberFormat().parse(value), defaultValue);
			
		} catch (ParseException ex) {
			
			numberValue = defaultValue;
		}
		
		return numberValue;
	}
	
	@Override
	public Number converter(String value) {

		try {
			
			return converter(value, sessionNumberFormat().parse("0"));
			
		} catch (ParseException ex) {
			
			throw new ProgrammingException("Problem to convert number", ex);
		}
	}
	
	public void write(JsonWriter out, Number value) throws IOException {
		
		out.value(value);
	}
	

	public Number read(JsonReader in) throws IOException {
		
		String value = "0";
		
		if(in.peek() != JsonToken.NULL) value = in.nextString();
		
		return converter(value);
	}
	
	public NumberFormat sessionNumberFormat() {
		
		return NumberFormat.getInstance(SessionManager.INSTANCE.getLocale());
	}
}
