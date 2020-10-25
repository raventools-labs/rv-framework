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

import org.xmlet.htmlapifaster.EnumTypeInputType;
import org.xmlet.htmlapifaster.Input;

import com.ravencloud.core.iu.builder.ActionViewBuilder;
import com.ravencloud.core.iu.builder.InputViewBuilder;
import com.ravencloud.core.labels.ManagerLabels;
import com.ravencloud.util.general.App;

import htmlflow.HtmlView;
import htmlflow.StaticHtml;

public class ActionView extends AbstractElementView {

	private static final String CLASS_PRINCIPAL_DIV = "form-label-group col-md-%s mb-3";
	
	public static final String ON_CLICK = "rv.executeAction(this,'%s','%s','%s')";
	
	private static final String LABEL = "Module.%s.action.%s";
	
	private String name;
	
	public ActionView(ActionViewBuilder builder) {

		super(builder);

		this.name = builder.getName();
	}

	public ActionView(InputViewBuilder builder) {

		super(builder);
	}
	
	//@formatter:off
	@Override
	public HtmlView<?> getView() {
		
		StaticHtml view = StaticHtml.view();
		
		Input input = view.div().attrClass(String.format(CLASS_PRINCIPAL_DIV, sizeColumn)).input();
		
		input.attrId(module + "_" + name);
		
		input.attrClass("form-control btn btn-primary");
		
		input.attrType(EnumTypeInputType.SUBMIT);
		
		input.attrValue(ManagerLabels.INSTANCE.get(String.format(LABEL, module, name)));
		
		input.attrOnclick((String.format(ON_CLICK, App.INSTANCE.contextPath(), module, name)));
		
		input.__().__();
		
		return view;
	}
	//@formatter:on
}
