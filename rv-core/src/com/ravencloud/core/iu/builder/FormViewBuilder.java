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

import com.ravencloud.core.iu.model.FormView;
import com.ravencloud.core.iu.model.IView;
import com.ravencloud.util.exception.BuildException;

public class FormViewBuilder extends AbstractGroupElementViewBuilder<FormView> {

	public FormViewBuilder(Class<? extends IView> classView) {
		super(classView);
	}

	public FormViewBuilder() {
		super();
	}

	@Override
	public FormView build() throws BuildException {

		return new FormView(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected FormViewBuilder getThis() { return this; }
}
