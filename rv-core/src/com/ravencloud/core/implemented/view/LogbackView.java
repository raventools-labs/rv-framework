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

import java.util.stream.Stream;

import org.slf4j.LoggerFactory;
import org.xmlet.htmlapifaster.EnumScopeType;

import com.ravencloud.core.bd.model.Role;
import com.ravencloud.core.iu.InputUtils;
import com.ravencloud.core.iu.ViewUtils;
import com.ravencloud.core.iu.annotation.MetaAttribute;
import com.ravencloud.core.iu.annotation.View;
import com.ravencloud.core.iu.builder.ActionViewBuilder;
import com.ravencloud.core.iu.model.IModuleView;
import com.ravencloud.core.iu.model.RavencloudContext;
import com.ravencloud.core.iu.model.SterotypeAttribute;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import htmlflow.DynamicHtml;
import htmlflow.HtmlView;
import lombok.Getter;
import lombok.Setter;

@View(name = LogbackView.MODULE_NAME, role = Role.ADMIN)
public class LogbackView implements IModuleView<RavencloudContext<Stream<Logger>>> {
	
	public static final String MODULE_NAME = "logback";
	
	@MetaAttribute(type = SterotypeAttribute.TEXT, search = true, icon = "search")
	@Getter @Setter private String name;
	
	@MetaAttribute(type = SterotypeAttribute.TEXT) 
	@Getter @Setter private String level;
	
	@Override
	public HtmlView<RavencloudContext<Stream<Logger>>> getView() {
		
		return DynamicHtml.view(ViewUtils.INSTANCE.createTemplate(content));
	}
	
	//@formatter:off
	private static final DynamicHtml<RavencloudContext<Stream<Logger>>> content = DynamicHtml.view((view, context) -> 
		
		view.div()
			.form().attrId("Module_" + MODULE_NAME)
			.attrClass("form-fadeIn-content needs-validation").attrNovalidate(true).attrOnsubmit("return false;")
				.div().attrClass("row")
					.div().attrClass("col-md-5")
						.of(a -> view.addPartial(InputUtils.INSTANCE.builder(LogbackView.class,"name").build().getView()))
					.__()
					.div().attrClass("col-md-3")
						.of(a -> view.addPartial(InputUtils.INSTANCE.builder(LogbackView.class,"level").build().getView()))
					.__()
					.div().attrClass("col-md-4")
						.of(a -> view.addPartial(ActionViewBuilder.builder(LogbackView.class,"reconfigureLogger").build().getView()))
					.__()
				.__()
				.div().attrClass("row")
					.div().attrClass("col-md-8").__()
					.div().attrClass("col-md-4")
						.of(a -> view.addPartial(ActionViewBuilder.builder(LogbackView.class,"reloadFromDisk").build().getView()))
					.__()
				.__()
				.dynamic(row -> row.of(div -> view.addPartial(LogbackView.tableLogger,context.getModel())))
			.__()
		.__()
	);
	
	private static final DynamicHtml<Stream<Logger>> tableLogger = DynamicHtml.view((DynamicHtml<Stream<Logger>> view, Stream<Logger> loggers) -> {
		
		view.div().attrClass("row")
			.div().attrClass("col-md-12 table-responsive-xl")
				.table().attrId("table-" + MODULE_NAME).attrClass("table")
					.thead()
						.tr().attrClass("bg-primary text-light")
							.th().attrScope(EnumScopeType.COL).text("#").__()
							.th().attrScope(EnumScopeType.COL).text("Logger").__()
							.th().attrScope(EnumScopeType.COL).text("Level").__()
						.__()
					.__()
					.tbody().attrClass("list")
						.dynamic(tbody -> 
							loggers.forEach(logger -> tbody
		                        .tr()
		                        	.th().attrScope(EnumScopeType.ROW).text("").__()
		                        	.td().attrClass("logger-name").text(logger.getName()).__()
									.td().attrClass("logger-level").text(logger.getEffectiveLevel()).__()
		                        .__()
							)
						)    
					.__()
				.__()
				.script()
					.text("new Tablesort(document.getElementById('table-" + MODULE_NAME +"'));new List('Module_" + MODULE_NAME + "', {valueNames: ['logger-name', 'logger-level']});")
				.__()
			.__()
		.__();
	});

	@Override
	public RavencloudContext<Stream<Logger>> getInitModel(String moduleName) {

		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		
		Stream<Logger> loggers = loggerContext.getLoggerList().stream();

		return new RavencloudContext<>(LogbackView.MODULE_NAME, loggers);
	}
	
//	.select().attrClass("u-full-width").attrId("level").attrName("level")
//	.option().attrValue("all").text("all").__()
//	.option().attrValue("trace").text("trace").__()
//	.option().attrValue("debug").attrSelected(true).text("debug").__()
//	.option().attrValue("info").text("info").__()
//	.option().attrValue("warn").text("warn").__()
//	.option().attrValue("error").text("error").__()
//	.option().attrValue("off").text("off").__()
//.__()
	
	//@formatter:on
}
