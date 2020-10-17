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

import com.ravencloud.core.iu.model.ActionView;
import com.ravencloud.core.iu.model.IView;
import com.ravencloud.util.exception.BuildException;

import lombok.Getter;

public class ActionViewBuilder extends AbstractElementViewBuilder<ActionView> {
	
	@Getter private String name;
	
	public ActionViewBuilder(Class<? extends IView> classView, String name) {

		super(classView);
		
		this.name = name;
	}
	
	public ActionViewBuilder() {
		
		super();
	}
	
	public ActionViewBuilder name(String name) {
		
		this.name = name;
		
		return getThis();
	}
	
	@Override
	public ActionView build() throws BuildException {
		
		return new ActionView(this);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected ActionViewBuilder getThis() { return this; }

	
	public static ActionViewBuilder builder(Class<? extends IView> classView, String name) {
		
		return new ActionViewBuilder(classView, name);
	}
}