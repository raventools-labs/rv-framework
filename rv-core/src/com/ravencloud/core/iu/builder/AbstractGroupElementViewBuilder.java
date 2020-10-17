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

import java.util.ArrayList;
import java.util.List;

import com.ravencloud.core.iu.model.AbstractElementView;
import com.ravencloud.core.iu.model.IView;

import lombok.Getter;

public abstract class AbstractGroupElementViewBuilder<E extends AbstractElementView> extends AbstractElementViewBuilder<E> {
	
	@Getter private List<AbstractElementView> elements;
	
	@Getter private Class<? extends IView> classView;
	
	public AbstractGroupElementViewBuilder(Class<? extends IView> classView) {
		
		super(classView);
		
		this.classView = classView;
	}
	
	public AbstractGroupElementViewBuilder() {
		super();
	}
	
	public AbstractGroupElementViewBuilder<?> element(AbstractElementView element) {
		
		if(this.elements == null) this.elements = new ArrayList<>();
		
		this.elements.add(element);
		
		return this;
	}
}