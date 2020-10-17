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

import java.lang.reflect.Array;
import java.util.List;

public enum ListUtil {
	
	INSTANCE;
	
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(Class<T> type, List<T> list) {
		
		T[] array = (T[]) Array.newInstance(type, list.size());
		
		return list.toArray(array);
	}
}
