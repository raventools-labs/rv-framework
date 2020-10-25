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

import org.xmlet.htmlapifaster.Body;
import org.xmlet.htmlapifaster.Html;

import com.ravencloud.core.bd.model.Role;
import com.ravencloud.core.iu.NoModel;
import com.ravencloud.core.iu.ViewUtils;
import com.ravencloud.core.iu.annotation.View;
import com.ravencloud.core.iu.model.RavencloudContext;
import com.ravencloud.util.general.App;
import com.ravencloud.util.session.SessionManager;

import htmlflow.DynamicHtml;
import htmlflow.HtmlView;

@View(name = SwaggerView.MODULE_NAME, role = Role.REST_UI)
public class SwaggerView extends NotInitModelView {
	
	public static final String MODULE_NAME = "swagger";
	
	@Override
	public HtmlView<RavencloudContext<NoModel>> getView() {
		
		return DynamicHtml.view(SwaggerView::view);
	}
	
	//@formatter:off
	private static void view(DynamicHtml<RavencloudContext<NoModel>> view, RavencloudContext<NoModel> context) {
		
		String contextPath = App.INSTANCE.contextPath();
			
		Html<HtmlView> html = view.html();
		
		html = ViewUtils.INSTANCE.addHead(html);
		
		Body<Html<HtmlView>> body = html.body();
		
		if(SessionManager.INSTANCE.isSignIn()) {
			body = ViewUtils.INSTANCE.addLateralNavbar(body);
		}
			
		body.div().attrId("swagger-ui").__()
			.script().attrSrc(contextPath + "rest-ui/swagger-ui-bundle.js").__()
			.script().attrSrc(contextPath + "rest-ui/swagger-ui-standalone-preset.js").__()
			.script().text("rv.initSwagger('" + contextPath + "');").__()
		.__().__();
	}
	//@formatter:on
}
