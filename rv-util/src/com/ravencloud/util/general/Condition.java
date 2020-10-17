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

import java.util.List;
import java.util.Map;

public final class Condition {
	
	private Condition() {}

	public static boolean empty(Object object) {
		return object == null;
	}
	
	private static final String STRING_EMPTY = "";
	
	public static boolean empty(String string) {
		
		return string == null || string.trim().equals(STRING_EMPTY);
	}
	
	public static boolean empty(Map<?,?> map) {
		return map == null || map.isEmpty();
	}
	
	public static boolean empty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	public static <V> V eval(boolean condition,V positive,V negative) {
		
		return (condition ? positive : negative);
	}
	
	public static <V> V evalNotEmpty(V positive,V negative) {
		
		return (!empty(positive) ? positive : negative);
	}
}
