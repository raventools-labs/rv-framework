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
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.ravencloud.util.general.Condition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractLocaleLabels {

	protected Locale locale;
	
	public AbstractLocaleLabels(String locale) {
		
		this.locale = LocaleUtils.toLocale(locale);
	}
	
	public String get(String key) {
		
		String label = getValue(key);
		
		if(Condition.empty(label)) {
			
			try {
				
				ResourceBundle resource = ResourceBundle.getBundle("Labels", locale);
				
				label = resource.getString(key);
				
				if(!Condition.empty(key)) put(key, label);
				
			} catch(MissingResourceException ex) {
				
				log.debug(ExceptionUtils.getRootCauseMessage(ex));
			} 
		}
		
		return label;
	}

	protected abstract String getValue(String key);
	
	public abstract void put(String key, String value);
}
