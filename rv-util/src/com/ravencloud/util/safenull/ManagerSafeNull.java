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
package com.ravencloud.util.safenull;

import java.util.HashMap;
import java.util.Map;

import com.ravencloud.util.exception.ProgrammingException;
import com.ravencloud.util.general.Condition;
import com.ravencloud.util.safenull.AbstractSafeNull.ValueNull;

public enum ManagerSafeNull {
	
	INSTANCE;
	
	private ManagerSafeNull() {}
	
	private static Map<Class<? extends ISafeNull>,ISafeNull> mapClass;
	
	public synchronized <C extends ISafeNull> C instance(Class<C> type) {
		
		if(Condition.empty(mapClass)) mapClass = new HashMap<>();
		
		@SuppressWarnings("unchecked")
		C instance = (C) mapClass.get(type);
		
		if(Condition.empty(instance)) {
			
			try { 
				
				instance = type.getDeclaredConstructor(ValueNull.class).newInstance(ValueNull.INSTANCE);
				
			} catch (Exception ex) { 
				
				throw new ProgrammingException("Bad definition constructor", ex); 
			}
			
			mapClass.put(type, instance);
		}
		
		return instance;
	}
	
	public synchronized <C extends ISafeNull> C instance(C instance, Class<C> type) {

		return Condition.evalNotEmpty(instance, instance(type));
	} 
}
