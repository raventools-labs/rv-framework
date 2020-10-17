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
package com.ravencloud.core.implemented.view;

import com.ravencloud.core.iu.InputUtils;
import com.ravencloud.core.iu.NoModel;
import com.ravencloud.core.iu.ViewUtils;
import com.ravencloud.core.iu.annotation.MetaAttribute;
import com.ravencloud.core.iu.annotation.View;
import com.ravencloud.core.iu.builder.ActionViewBuilder;
import com.ravencloud.core.iu.model.RavencloudContext;
import com.ravencloud.core.iu.model.SterotypeAttribute;
import com.ravencloud.util.general.App;

import htmlflow.DynamicHtml;
import htmlflow.HtmlView;
import lombok.Getter;
import lombok.Setter;


@View(name = LoginView.MODULE_NAME)
public class LoginView extends NotInitModelView {
	
	public static final String MODULE_NAME = "login";
	
	@MetaAttribute(type = SterotypeAttribute.EMAIL, required = true, icon = "at") 
	@Getter @Setter private String name;
	
	@MetaAttribute(type = SterotypeAttribute.PASSWORD, required = true, icon = "key") 
	@Getter @Setter private String password;
	
	@Override
	public HtmlView<RavencloudContext<NoModel>> getView() {
		
		return DynamicHtml.view(ViewUtils.INSTANCE.createTemplate(content));
	}
	
	//@formatter:off
	private static final DynamicHtml<RavencloudContext<NoModel>> content = DynamicHtml.view((view, context) -> 
		
		view.div().attrClass("container-fadeIn fadeInDown")
			.div().attrClass("form-fadeIn")
			
				.div().attrClass("row fadeIn")
					.div().attrClass("col-md-12")
						.img().attrClass("form-fadeIn-img")
							.attrSrc(String.format(ViewUtils.PATH_RESOURCE, App.INSTANCE.contextPath(),"img/logo.png"))
						.__()
					.__()
				.__()
				
				.form().attrId("Module_" + MODULE_NAME)
				.attrClass("form-fadeIn-content needs-validation center").attrNovalidate(true).attrOnsubmit("return false;")
					.div().attrId(MODULE_NAME + "_message_error").attrClass("card text-white bg-danger mb-3 hidden error-message")
						.div().attrClass("card-body")
							.p().attrClass("card-text").__()
						.__()
					.__()
					.div().attrClass("row fadeIn")
						.of(a -> view.addPartial(InputUtils.INSTANCE.builder(LoginView.class,"name").build().getView()))
						.of(a -> view.addPartial(InputUtils.INSTANCE.builder(LoginView.class,"password").build().getView()))
						.of(a -> view.addPartial(ActionViewBuilder.builder(LoginView.class,"signIn").build().getView()))
					.__()
				.__()
			
				.div().attrClass("form-fadeIn-footer")
					.a().attrClass("").attrHref("#").text("Forgot Password?")
					.__()
				.__()
				
			.__()
		.__()
	);
	//@formatter:on
}
