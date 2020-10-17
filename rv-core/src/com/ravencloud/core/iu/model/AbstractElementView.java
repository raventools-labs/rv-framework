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

import lombok.Getter;

public abstract class AbstractElementView implements IView {
	
	@Getter protected String module;
	
	@Getter protected int sizeColumn;
	
	protected AbstractElementView(AbstractElementViewBuilder<?> builder) {
		
		this.module = builder.getModule();
		
		this.sizeColumn = builder.getSizeColumn();
	}
}
