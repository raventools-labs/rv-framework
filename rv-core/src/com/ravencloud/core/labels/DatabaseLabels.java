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

import com.ravencloud.core.bd.dao.TranslationDao;
import com.ravencloud.util.general.Condition;
import com.ravencloud.util.labels.LabelsStrategy;

public class DatabaseLabels implements LabelsStrategy {

	private static LabelsStrategy instance;

	public static synchronized LabelsStrategy getInstance() {

		if(Condition.empty(instance)) instance = new DatabaseLabels();

		return instance;
	}

	@Override
	public String get(String key, String locale) {

		return TranslationDao.INSTANCE.getValue(key, locale);
	}

	@Override
	public void put(String key, String value, String locale) {}
}
