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

import java.util.Locale;

public interface LabelsStrategy {
	
	public static String getDefault() {
		
		Locale locale = Locale.getDefault();
		
		return locale.getLanguage() + "_" + locale.getCountry();
	}
	
	public String get(String key, String locale);
	
	public void put(String key, String value, String locale);
}
