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
package com.ravencloud.core.iu.model;

import org.xmlet.htmlapifaster.Input;

import com.ravencloud.core.iu.builder.InputViewBuilder;

public class NumberInputView extends InputView {

	public NumberInputView(InputViewBuilder builder) {

		super(builder);
	}
	
	private static final String ON_INPUT = "this.value = this.value.replace(/[^0-9.]/g, ''); this.value = this.value.replace(/(\\..*)\\./g, '$1');";
	
	@Override
	protected void setExtraInput(Input input) {


		input.attrOninput(ON_INPUT);
	}
}
