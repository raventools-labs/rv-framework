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

import org.xmlet.htmlapifaster.Div;
import org.xmlet.htmlapifaster.EnumTypeInputType;
import org.xmlet.htmlapifaster.Input;

import com.ravencloud.core.iu.builder.InputViewBuilder;
import com.ravencloud.core.labels.ManagerLabels;
import com.ravencloud.util.general.Condition;

import htmlflow.HtmlView;
import htmlflow.StaticHtml;

public class InputView extends AbstractElementView {

	protected static final String CLASS_PRINCIPAL_DIV = "form-label-group col-md-%s mb-3";
	
	protected static final String PLACEHOLDER = "Module.%s.input[placeholder].%s";
	
	protected String name;
	
	protected EnumTypeInputType type;

	protected boolean readonly;

	protected boolean required;
	
	protected boolean search;
	
	protected String icon;
	
	protected String min;
	
	protected String max;
	
	protected final String label;

	public InputView(InputViewBuilder builder) {

		super(builder);

		this.name = builder.getName();

		this.type = builder.getType();

		this.required = builder.isRequired();

		this.readonly = builder.isReadonly();
		
		this.search = builder.isSearch();
		
		this.icon = builder.getIcon();
		
		this.min = builder.getMin();
		
		this.max = builder.getMax();
		
		this.label = ManagerLabels.INSTANCE.get(String.format(PLACEHOLDER, module, name));
	}
	
	//@formatter:off
	@Override
	public HtmlView<?> getView() {
		
		StaticHtml view = StaticHtml.view();
		
		Input input = getInput(view);
		
		setExtraInput(input);
		
		((Div<?>) input.__()).label().attrFor(module + "_" + name)
				.i().attrClass("fas fa-" + icon).__().text(label)
			.__()
		.__();
		
		return view;
	}
	
	protected Input getInput(StaticHtml view) {
		
		Div div = view.div().attrClass(String.format(CLASS_PRINCIPAL_DIV, sizeColumn));
		
		Input input = div.input();
		
		input.attrId(module + "_" + name);
		
		String attrClass = "form-control ";
		
		if(search) attrClass = attrClass + "search";
		
		input.attrClass(attrClass);
		
		input.attrType(type);
		
		input.attrPlaceholder(label);
		
		if(required) input.attrRequired(required);
		
		if(readonly) input.attrReadonly(readonly);
		
		if(!Condition.empty(min)) input.attrMin(min);
		
		if(!Condition.empty(max)) input.attrMax(max);
		
		return input;
	}
	
	protected void setExtraInput(Input input) {}
	//@formatter:on
}
