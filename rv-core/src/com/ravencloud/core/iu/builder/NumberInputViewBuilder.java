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
import com.ravencloud.core.iu.model.InputView;
import com.ravencloud.core.iu.model.NumberInputView;

public class NumberInputViewBuilder extends InputViewBuilder {
	
	public NumberInputViewBuilder(Class<? extends IView> classView, String property) {
		
		super(classView,property);
	}
	
	public NumberInputViewBuilder() {
		
		super();
	}
	
	@Override
	public InputView build() {
		
		return new NumberInputView(this);
	}
}
