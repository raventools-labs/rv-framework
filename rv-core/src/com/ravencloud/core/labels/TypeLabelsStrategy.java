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

import com.ravencloud.util.general.ClassUtils;
import com.ravencloud.util.general.Condition;
import com.ravencloud.util.labels.CacheLabels;
import com.ravencloud.util.labels.LabelsStrategy;
import com.ravencloud.util.labels.MemoryLabels;

public enum TypeLabelsStrategy {

	MEMORY(MemoryLabels.class),
	CACHE(CacheLabels.class),
	DATABASE(DatabaseLabels.class);

	private LabelsStrategy strategy;

	private Class<? extends LabelsStrategy> classStrategy;

	private TypeLabelsStrategy(Class<? extends LabelsStrategy> classStrategy) {

		this.classStrategy = classStrategy;
	}

	public LabelsStrategy getStrategy() {

		if(Condition.empty(strategy)) strategy = ClassUtils.INSTANCE.getInstance(classStrategy);

		return strategy;
	}
}
