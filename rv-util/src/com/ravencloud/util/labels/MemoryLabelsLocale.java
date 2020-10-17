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
package com.ravencloud.util.labels;

import java.util.HashMap;
import java.util.Map;

public class MemoryLabelsLocale extends AbstractLocaleLabels {

	private Map<String,String> mapLabelLocale;
	
	public MemoryLabelsLocale(String locale) {
		
		super(locale);
		
		mapLabelLocale = new HashMap<>();
	}

	@Override
	protected String getValue(String key) {

		return mapLabelLocale.get(key);
	}

	@Override
	public void put(String key, String value) {

		mapLabelLocale.put(key, value);
	}

}
