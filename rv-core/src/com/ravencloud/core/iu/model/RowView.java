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

import com.ravencloud.core.iu.builder.RowViewBuilder;

import htmlflow.HtmlView;
import htmlflow.StaticHtml;

public class RowView extends AbstractGroupElementView {

	public RowView(RowViewBuilder builder) {

		super(builder);
	}

	//@formatter:off
	@Override
	public HtmlView<?> getView() {
		
		StaticHtml view = StaticHtml.view();
		
		Div<?> div = view.div().attrClass("row");
		
		for(IView<?> element :elements) div.of(e -> view.addPartial(element.getView()));
		
		div.__();
		
		return view;
	}
	//@formatter:on
	
	public static RowViewBuilder builder(Class<? extends IView> classView) {

		return new RowViewBuilder(classView);
	}
}
