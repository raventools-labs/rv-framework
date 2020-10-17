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

import com.ravencloud.util.general.Condition;

public class StringToBooleanConverter implements IStringToValueConverter<Boolean> {

	private static StringToBooleanConverter instance;
	
	public static synchronized StringToBooleanConverter getInstance() {
		
		if(Condition.empty(instance)) {
			
			instance = new StringToBooleanConverter();
		}
		
		return instance;
	}
	
	@Override
	public Boolean converter(String value, Boolean defaultValue) {

		return Condition.evalNotEmpty(Boolean.valueOf(value), defaultValue);
	}
	
	@Override
	public Boolean converter(String value) {

		return converter(value, false);
	}
}
