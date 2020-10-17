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

import com.ravencloud.core.iu.model.IView;
import com.ravencloud.core.iu.model.RowView;
import com.ravencloud.util.exception.BuildException;

public class RowViewBuilder extends AbstractGroupElementViewBuilder<RowView> {

	public RowViewBuilder(Class<? extends IView> classView) {
		super(classView);
	}

	public RowViewBuilder() {
		super();
	}

	@Override
	public RowView build() throws BuildException {

		return new RowView(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected RowViewBuilder getThis() { return this; }
}
