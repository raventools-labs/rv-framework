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

import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ravencloud.util.stringconverter.StringToBigDecimalConverter;
import com.ravencloud.util.stringconverter.StringToIntConverter;
import com.ravencloud.util.stringconverter.StringToLongConverter;

public final class Json {
	
	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private static Gson gson;
	
	private Json() {}
	
	public static synchronized Gson getInstance() {
		
		if(Condition.empty(gson)) {
			
			gson = new GsonBuilder()
				.setDateFormat(DEFAULT_DATE_FORMAT)
				.registerTypeAdapter(int.class, StringToIntConverter.getInstance())
			    .registerTypeAdapter(Integer.class, StringToIntConverter.getInstance())
			    .registerTypeAdapter(long.class, StringToLongConverter.getInstance())
			    .registerTypeAdapter(Long.class, StringToLongConverter.getInstance())
			    .registerTypeAdapter(BigDecimal.class, StringToBigDecimalConverter.getInstance())
				.create();
		}
		
		return gson;
	}
}
