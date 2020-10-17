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

import java.lang.reflect.Field;

import org.xmlet.htmlapifaster.EnumTypeInputType;

import com.ravencloud.core.iu.annotation.MetaAttribute;
import com.ravencloud.core.iu.model.IView;
import com.ravencloud.core.iu.model.InputView;
import com.ravencloud.util.exception.BuildException;

import lombok.Getter;

public class InputViewBuilder extends AbstractElementViewBuilder<InputView> {
	
	@Getter private String name;
	
	@Getter private boolean readonly;
	
	@Getter private boolean required;
	
	@Getter private boolean search;
	
	@Getter private String icon;
	
	@Getter private String min;
	
	@Getter private String max;
	
	public InputViewBuilder(Class<? extends IView> classView, String property) {

		super(classView);

		try {

			Field field = classView.getDeclaredField(property);

			MetaAttribute meta = field.getAnnotation(MetaAttribute.class);

			this.name = property;

			this.required = meta.required();

			this.readonly = meta.readonly();
			
			this.search = meta.search();
			
			this.icon = meta.icon();
			
			this.min = meta.min();
			
			this.max = meta.max();

		} catch(Exception ex) {

			throw new BuildException(ex);
		}
	}
	
	public InputViewBuilder() {
		super();
	}
	
	public InputViewBuilder name(String name) {
		
		this.name = name;
		
		return getThis();
	}
	
	public InputViewBuilder required(boolean required) {
		
		this.required = required;
		
		return getThis();
	}
	
	public InputViewBuilder readonly(boolean readonly) {
		
		this.readonly = readonly;
		
		return getThis();
	}
	
	public InputViewBuilder min(String min) {
		
		this.min = min;
		
		return getThis();
	}
	
	public InputViewBuilder max(String max) {
		
		this.max = max;
		
		return getThis();
	}
	
	public EnumTypeInputType getType() { return EnumTypeInputType.TEXT; }

	@Override
	@SuppressWarnings("unchecked")
	protected InputViewBuilder getThis() { return this; }

	@Override
	public InputView build() {
		
		return new InputView(this);
	}
}
