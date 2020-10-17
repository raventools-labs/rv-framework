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

import com.ravencloud.util.general.Condition;

public abstract class AbstractSafeNull implements ISafeNull {

	public enum ValueNull {
		
		INSTANCE
	}
	
	private boolean valueNull;
	
	protected AbstractSafeNull() { 
		
		this.valueNull = false;
	}
	
	public AbstractSafeNull(ValueNull valueNull) { 
		
		this.valueNull = !Condition.empty(valueNull);
	}
	
	@Override
	public boolean isNull() {
		return valueNull;
	}
}
