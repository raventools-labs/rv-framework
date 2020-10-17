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

import com.ravencloud.core.iu.InputUtils;
import com.ravencloud.core.iu.model.AbstractElementView;
import com.ravencloud.core.iu.model.InputView;
import com.ravencloud.util.exception.BuildException;

public final class AttributeViewBuilder extends AbstractElementViewBuilder<AbstractElementView> {

	private AttributeViewBuilder() {}

	@Override
	public InputView build() throws BuildException {
		
		throw new UnsupportedOperationException();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected AttributeViewBuilder getThis() { return this; }
	
	public static AbstractElementViewBuilder<?> builder(Class<? extends InputView> classView, String property) {

		try {

			return InputUtils.INSTANCE.builder(classView, property);

		} catch(Exception ex) {

			throw new BuildException(ex);
		}
	}
}
