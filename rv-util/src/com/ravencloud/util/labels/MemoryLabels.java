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

import com.ravencloud.util.general.Condition;

public final class MemoryLabels implements LabelsStrategy {
	
	private static LabelsStrategy instance;
	
	private Map<String,MemoryLabelsLocale> labels;
	
	public static synchronized LabelsStrategy getInstance() {
		
		if(Condition.empty(instance)) instance = new MemoryLabels();
		
		return instance;
	}

	private MemoryLabels() {
		labels = new HashMap<>();
	}
	
	public synchronized MemoryLabelsLocale getLabelLocale(String locale){
		
		MemoryLabelsLocale labelLocale = labels.get(locale);
		
		if(Condition.empty(labelLocale)) {
			
			labelLocale = new MemoryLabelsLocale(locale);
			
			labels.put(locale, labelLocale);
		}
		
		return labelLocale;
	}
	
	@Override
	public String get(String key,String locale) {

		return getLabelLocale(locale).get(key);
	}
	
	@Override
	public void put(String key,String value,String locale) {

		getLabelLocale(locale).put(key, value);
	}
}
