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

import java.util.List;

import com.ravencloud.core.iu.builder.AbstractGroupElementViewBuilder;

import lombok.Getter;

public abstract class AbstractGroupElementView extends AbstractElementView {

	@Getter protected List<AbstractElementView> elements;

	protected <B extends AbstractGroupElementViewBuilder<?>> AbstractGroupElementView(B builder) {
		
		super(builder);
		
		this.elements = builder.getElements();
	}
}
