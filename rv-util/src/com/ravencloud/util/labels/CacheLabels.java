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

import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;

import com.ravencloud.util.general.App;
import com.ravencloud.util.general.ClassUtils;
import com.ravencloud.util.general.Condition;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CacheLabels implements LabelsStrategy {
	
	private static final String PATH_DEFAULT_CONFIG = "locale.ehcache.xml";
	
	private static final String KEY_PATH_CONFIG = "cache.locale.path";
	
	private static CacheLabels instance;
	
	private Map<String,CacheLabelsLocale> labels;
	
	@Getter private CacheManager cacheManager;
	
	private CacheLabels() {
		
		String path = null;
		
		try {
			
			String defaultPath = "file:" + ClassUtils.INSTANCE.getPathClass(PATH_DEFAULT_CONFIG);
			
			path = App.INSTANCE.getProperty(String.class, KEY_PATH_CONFIG, defaultPath);
			
			XmlConfiguration xmlConfiguration = new XmlConfiguration(new URL(path));
			
			cacheManager = CacheManagerBuilder.newCacheManager(xmlConfiguration);
			
			labels = new HashMap<>();
			
			cacheManager.init();
			
		} catch (Exception ex) {
			
			log.error(MessageFormat.format("Problem to load cache properties {0}", path));
		}
	}
	
	public static synchronized CacheLabels getInstance() {
		
		if(Condition.empty(instance)) instance = new CacheLabels();
		
		return instance;
	}
	
	private synchronized CacheLabelsLocale getLabelLocale(String locale){
		
		CacheLabelsLocale labelLocale = labels.get(locale);
		
		if(Condition.empty(labelLocale)) {
			
			labelLocale = new CacheLabelsLocale(locale);
			
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
