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
package com.ravencloud.core.labels;

import com.ravencloud.util.general.Condition;
import com.ravencloud.util.labels.LabelsStrategy;
import com.ravencloud.util.session.SessionManager;

import lombok.Setter;

public enum ManagerLabels {
	
	INSTANCE;
	
	private LabelsStrategy firstLevelToFind;
	
	private LabelsStrategy secondLevelToFind;
	
	@Setter private boolean keyIfNotFind;
	
	private ManagerLabels() {
		
		firstLevelToFind = TypeLabelsStrategy.CACHE.getStrategy();
		
		secondLevelToFind = TypeLabelsStrategy.DATABASE.getStrategy();
	}
	
	public String get(String key) {
		
		return get(key, false);
	}
	
	public String get(String key, boolean onlyFirstLevel) {
		
		String locale = SessionManager.INSTANCE.getStringLocale();
		
		String value = firstLevelToFind.get(key,locale);
		
		if(!onlyFirstLevel && Condition.empty(value)) value = secondLevelToFind.get(key, locale);
		
		if(Condition.empty(value) && keyIfNotFind) value = key;
		
		return value;
	}
}
