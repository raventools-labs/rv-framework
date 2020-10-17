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
package com.ravencloud.core.iu.builder;


import com.ravencloud.core.iu.ViewUtils;
import com.ravencloud.core.iu.model.AbstractElementView;
import com.ravencloud.core.iu.model.IView;
import com.ravencloud.util.exception.BuildException;
import com.ravencloud.util.general.Builder;

import lombok.Getter;

public abstract class AbstractElementViewBuilder<E extends AbstractElementView> implements Builder<E> {

	private static final int DEFAULT_SIZE_COLUMN = 12;

	@Getter protected String module;

	@Getter protected int sizeColumn;

	protected AbstractElementViewBuilder(Class<? extends IView> classView) throws BuildException {

		try {

			this.module = ViewUtils.INSTANCE.getNameModule(classView);

			sizeColumn = DEFAULT_SIZE_COLUMN;

		} catch (Exception ex) {

			throw new BuildException(ex);
		}
	}

	protected AbstractElementViewBuilder() {}

	public <B extends AbstractElementViewBuilder<E>> B sizeColumn(int sizeColumn) {

		this.sizeColumn = sizeColumn;

		return getThis();
	}

	public <B extends AbstractElementViewBuilder<E>> B module(String module) {

		this.module = module;

		return getThis();
	}

	protected abstract <B extends AbstractElementViewBuilder<?>> B getThis();
}
