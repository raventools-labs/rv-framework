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

import com.ravencloud.core.iu.builder.AbstractElementViewBuilder;
import com.ravencloud.core.iu.builder.EmailInputViewBuilder;
import com.ravencloud.core.iu.builder.InputViewBuilder;
import com.ravencloud.core.iu.builder.NumberInputViewBuilder;
import com.ravencloud.core.iu.builder.PasswordInputViewBuilder;

import lombok.Getter;

public enum SterotypeAttribute {

	TEXT(InputViewBuilder.class),
	PASSWORD(PasswordInputViewBuilder.class),
	EMAIL(EmailInputViewBuilder.class),
	NUMBER(NumberInputViewBuilder.class);
	
	@Getter private Class<? extends AbstractElementViewBuilder<?>> classBuilder;
	
	@Getter private Class<?> classAttribute;
	
	private SterotypeAttribute(Class<? extends AbstractElementViewBuilder<?>> classBuilder) {
		this.classBuilder = classBuilder;
	}
}
