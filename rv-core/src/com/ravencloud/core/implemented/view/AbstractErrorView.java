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

import com.ravencloud.core.iu.NoModel;
import com.ravencloud.core.iu.ViewUtils;
import com.ravencloud.core.iu.model.RavencloudContext;
import com.ravencloud.util.general.App;

import htmlflow.DynamicHtml;
import htmlflow.HtmlView;

public abstract class AbstractErrorView extends NotInitModelView {
	
	@Override
	public HtmlView<RavencloudContext<NoModel>> getView() {
		
		return DynamicHtml.view(ViewUtils.INSTANCE.createTemplate(content));
	}
	
	//@formatter:off
	private static final DynamicHtml<RavencloudContext<NoModel>> content = DynamicHtml.view((view,context) -> 
		
		view.div().attrClass(
			"container-fadeIn fadeInDown")
			.div().attrClass("form-fadeIn bg-warning bd-warning")
			
				.div().attrClass("row fadeIn")
					.div().attrClass("col-md-12")
						.img().attrClass("form-fadeIn-img-error")
							.attrSrc(String.format(ViewUtils.PATH_RESOURCE, App.INSTANCE.contextPath(),"img/" + context.getModuleName() + ".png"))
						.__()
					.__()
				.__()
			.__()
		.__()
	);
}
