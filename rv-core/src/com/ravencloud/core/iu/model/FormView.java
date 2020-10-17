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


import org.xmlet.htmlapifaster.Form;

import com.ravencloud.core.iu.builder.FormViewBuilder;

import htmlflow.HtmlView;
import htmlflow.StaticHtml;

public class FormView extends AbstractGroupElementView {

	public FormView(FormViewBuilder builder) {

		super(builder);
	}
	
	//@formatter:off
	@Override
	public HtmlView<?> getView() {
		
		StaticHtml view = StaticHtml.view();
		
		Form<?> form = view.div().form().attrId("Module_" + module).attrClass("needs-validation").attrNovalidate(true);
		
		form.attrOnsubmit("return false;");
		
		form.div().attrId(module + "_message_error").attrClass("card border-danger mb-3 hidden error-message")
			.div().attrClass("card-body text-danger")
				.p().attrClass("card-text").__()
			.__()
		.__();
		
		for(IView<?> element :elements) form.of(e -> view.addPartial(element.getView()));
		
		form.__().__();
		
		return view;
	}
	//@formatter:on
	
	public static FormViewBuilder builder(Class<? extends IView> classView) {

		return new FormViewBuilder(classView);
	}

}
