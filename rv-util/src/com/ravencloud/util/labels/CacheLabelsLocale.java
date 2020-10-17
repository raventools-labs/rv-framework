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

import org.ehcache.Cache;

public class CacheLabelsLocale extends AbstractLocaleLabels {

	private static final String NAME_CACHE = "Locale.%s";
	
	private Cache<String, String> cacheLabelLocale;
	
	public CacheLabelsLocale(String locale) {
		
		super(locale);
		
		cacheLabelLocale = CacheLabels.getInstance().getCacheManager().getCache(
				String.format(NAME_CACHE, locale), String.class, String.class);
	}

	@Override
	protected String getValue(String key) {
		
		return cacheLabelLocale.get(key);
	}

	@Override
	public void put(String key, String value) {

		cacheLabelLocale.put(key, value);
	}
}
