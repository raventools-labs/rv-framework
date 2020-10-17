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
package com.ravencloud.core.iu;

import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xmlet.htmlapifaster.Body;
import org.xmlet.htmlapifaster.EnumHttpEquivType;
import org.xmlet.htmlapifaster.EnumRelType;
import org.xmlet.htmlapifaster.EnumTypeContentType;
import org.xmlet.htmlapifaster.EnumTypeScriptType;
import org.xmlet.htmlapifaster.Html;

import com.ravencloud.core.implemented.view.LoginView;
import com.ravencloud.core.iu.annotation.View;
import com.ravencloud.core.iu.builder.AbstractElementViewBuilder;
import com.ravencloud.core.iu.builder.AbstractGroupElementViewBuilder;
import com.ravencloud.core.iu.builder.FormViewBuilder;
import com.ravencloud.core.iu.builder.RowViewBuilder;
import com.ravencloud.core.iu.model.AbstractElementView;
import com.ravencloud.core.iu.model.ActionView;
import com.ravencloud.core.iu.model.FormView;
import com.ravencloud.core.iu.model.IView;
import com.ravencloud.core.iu.model.RavencloudContext;
import com.ravencloud.core.iu.model.RowView;
import com.ravencloud.util.exception.BuildException;
import com.ravencloud.util.general.App;
import com.ravencloud.util.general.Condition;
import com.ravencloud.util.session.SessionManager;

import htmlflow.DynamicHtml;
import htmlflow.HtmlView;
import lombok.Cleanup;

public enum ViewUtils {
	
	INSTANCE;
	
	public <M> BiConsumer<DynamicHtml<RavencloudContext<M>>, RavencloudContext<M>> createTemplate(
		DynamicHtml<RavencloudContext<M>> moduleView) {
		
		return (DynamicHtml<RavencloudContext<M>> view, RavencloudContext<M> model) -> {
			ViewUtils.INSTANCE.view(view, model, moduleView);
		};
	}
	
	//@formatter:off
	public <M> void view(DynamicHtml<RavencloudContext<M>> view, RavencloudContext<M> model, DynamicHtml<RavencloudContext<M>> moduleView) {

		Html<HtmlView> html = view.html();
		
		html = ViewUtils.INSTANCE.addHead(html);
		
		Body<Html<HtmlView>> body = html.body();
		
		body = ViewUtils.INSTANCE.addTopNavbar(body);
		
		if(SessionManager.INSTANCE.isSignIn()) {
			body = ViewUtils.INSTANCE.addLateralNavbar(body);
		}

		body.div().attrClass("container pt-4")
			.div().attrClass("row")
				.div().attrId("container-ravencloud").attrClass("col-md-12")
			.of(div -> view.addPartial(moduleView,model))
				.__()
			.__()	
		.__();
		
		body = ViewUtils.INSTANCE.addFooter(body);
		
		body.__().__();
	}
	//@formatter:on
	
	public static final String PATH_RESOURCE = "%s%s";

	public Html<HtmlView> addHead(Html<HtmlView> html) {
		
		String applicationPath = App.INSTANCE.contextPath();
		
		return html.head()
			.title().text(App.INSTANCE.appName()).__()
			.meta().attrHttpEquiv(EnumHttpEquivType.CONTENT_TYPE).attrContent("text/html; charset=UTF-8").__()
			.meta().attrName("viewport").attrContent("width=device-width").__()
			.link().attrRel(EnumRelType.ICON).addAttr("type", "image/png").addAttr("sizes", "32x32")
				.attrHref(String.format(PATH_RESOURCE, applicationPath,"img/favicon-32x32.png"))
			.__()
			.link().attrRel(EnumRelType.ICON).addAttr("type", "image/png").addAttr("sizes","16x16")
				.attrHref(String.format(PATH_RESOURCE, applicationPath,"img/favicon-16x16.png"))
			.__()
			.link().attrRel(EnumRelType.STYLESHEET).attrType(EnumTypeContentType.TEXT_CSS)
				.attrHref(applicationPath + "/rest-ui/swagger-ui.css")
			.__()
			.link().attrRel(EnumRelType.STYLESHEET).attrType(EnumTypeContentType.TEXT_CSS)
				.attrHref(String.format(PATH_RESOURCE, applicationPath,"rv/css/bootstrap.min.css"))
			.__()
			.link().attrRel(EnumRelType.STYLESHEET).attrType(EnumTypeContentType.TEXT_CSS)
				.attrHref(String.format(PATH_RESOURCE, applicationPath,"rv/fontawesome/css/all.css"))
			.__()
			.link().attrRel(EnumRelType.STYLESHEET).attrType(EnumTypeContentType.TEXT_CSS)
				.attrHref(String.format(PATH_RESOURCE, applicationPath,"rv/css/floating-labels.css"))
			.__()
			.link().attrRel(EnumRelType.STYLESHEET).attrType(EnumTypeContentType.TEXT_CSS)
				.attrHref(String.format(PATH_RESOURCE, applicationPath,"rv/css/floating-side-menu.css"))
			.__()
			.link().attrRel(EnumRelType.STYLESHEET).attrType(EnumTypeContentType.TEXT_CSS)
				.attrHref(String.format(PATH_RESOURCE, applicationPath,"rv/css/ravencloud.css"))
			.__()
			.script().attrType(EnumTypeScriptType.TEXT_JAVASCRIPT)
				.attrSrc("//cdnjs.cloudflare.com/ajax/libs/tablesort/2.2.4/tablesort.min.js")
			.__()
			.script().attrType(EnumTypeScriptType.TEXT_JAVASCRIPT)
				.attrSrc("//cdnjs.cloudflare.com/ajax/libs/list.js/1.1.1/list.min.js")
			.__()
			.script().attrType(EnumTypeScriptType.TEXT_JAVASCRIPT)
				.attrSrc(String.format(PATH_RESOURCE, applicationPath,"rv/js/jquery-3.2.1.min.js"))
			.__()
			.script().attrType(EnumTypeScriptType.TEXT_JAVASCRIPT)
				.attrSrc(String.format(PATH_RESOURCE, applicationPath,"rv/js/jquery.blockUI.js"))
			.__()
			.script().attrType(EnumTypeScriptType.TEXT_JAVASCRIPT)
				.attrSrc(String.format(PATH_RESOURCE, applicationPath,"rv/js/tether.min.js"))
			.__()
			.script().attrType(EnumTypeScriptType.TEXT_JAVASCRIPT)
				.attrSrc(String.format(PATH_RESOURCE, applicationPath,"rv/js/popper/popper.min.js"))
			.__()
			.script().attrType(EnumTypeScriptType.TEXT_JAVASCRIPT)
				.attrSrc(String.format(PATH_RESOURCE, applicationPath,"rv/js/bootstrap/bootstrap.min.js"))
			.__()
			.script().attrType(EnumTypeScriptType.TEXT_JAVASCRIPT)
				.attrSrc(String.format(PATH_RESOURCE, applicationPath,"rv/js/ie10-viewport-bug-workaround.js"))
			.__()
			.script().attrType(EnumTypeScriptType.TEXT_JAVASCRIPT)
				.attrSrc(String.format(PATH_RESOURCE, applicationPath,"rv/js/menu.js"))
			.__()
			.script().attrType(EnumTypeScriptType.TEXT_JAVASCRIPT)
				.attrSrc(String.format(PATH_RESOURCE, applicationPath,"rv/js/ravencloud.js"))
			.__()
			.script().attrType(EnumTypeScriptType.TEXT_JAVASCRIPT)
				.attrSrc(String.format(PATH_RESOURCE, applicationPath,"rv/js/icons.js"))
			.__()
		.__();
	}	
	
	public Body<Html<HtmlView>> addTopNavbar(Body<Html<HtmlView>> body) {
		
		String applicationPath = App.INSTANCE.contextPath();
		
		return body.nav().attrClass("navbar navbar-default bg-primary sidebar")
			.div().attrClass("container")
				.div().attrClass("navbar-header")
				
					.div().attrClass("navbar-brand float-left")
						.a().attrClass("btn btn-without-padding").attrHref(applicationPath)
							.img().attrHeight(35L)
								.attrSrc(String.format(PATH_RESOURCE, applicationPath,"img/title.png"))
							.__()
						.__()
					.__()
				.__()
			.__()
		.__();
	}
	
	public Body<Html<HtmlView>> addLateralNavbar(Body<Html<HtmlView>> body) {
		
		return body.nav().attrClass("floating-menu bg-primary text-center")
			.ul().attrClass("menu")
				.li()
					.a().attrOnclick("")
						.i().attrClass("fas fa-home color-secundary").__()
					.__()
				.__()
				.li()
					.a().attrOnclick(String.format(ActionView.ON_CLICK, App.INSTANCE.artifactId(), LoginView.MODULE_NAME, "signOut"))
						.i().attrClass("fas fa-sign-out-alt color-secundary").__()
					.__()
				.__()
			.__()
		.__();
	}
	
	public Body<Html<HtmlView>> addFooter(Body<Html<HtmlView>> body) {
		
		return body.footer().attrClass("page-footer font-small blue pt-4")
			.div().attrClass("container-fluid text-center text-md-left")
			.__()
		.__();
	}
	
	public String getNameModule(Class<? extends IView> classView) {
		
		return classView.getAnnotation(View.class).name();
	}
	
	public HtmlView createForm(Class<? extends IView> classView) {
		
		View v = classView.getAnnotationsByType(View.class)[0];
		
		FormViewBuilder formBuilder = FormView.builder(classView);
		
		@Cleanup
		Scanner scblock = new Scanner(v.struct());
		
		scblock.useDelimiter(";");
		
		while (scblock.hasNext()) {
			
			RowViewBuilder rowBuilder = RowView.builder(classView);
			
			String line = scblock.next();
			
			@Cleanup
			Scanner scline = new Scanner(line);
			
			scline.useDelimiter(",");
			
			while (scline.hasNext()) addElement(scline.next(), rowBuilder);
			
			formBuilder.element(rowBuilder.build());
		}
		
		return formBuilder.build().getView();
	}
	
	private static final Pattern PATTERN_ELEMENT = Pattern.compile("(\\$|@|#)(\\w+)\\(?([0-9]*)\\)?");
	
	private static final int POSITION_TOKEN = 1;
	
	private static final int POSITION_ELEMENT = 2;
	
	private static final int POSITION_SIZE_COLUMN = 3;
	
	private void addElement(String nextElement, AbstractGroupElementViewBuilder<?> groupElement) {
		
		Matcher match = PATTERN_ELEMENT.matcher(nextElement);
		
		if(match.find()) {
			
			TypeElementView type = TypeElementView.getType(match.group(POSITION_TOKEN));
			
			String element = match.group(POSITION_ELEMENT);
			
			AbstractElementViewBuilder<? extends AbstractElementView> builder = builder(
				type.getClassBuilder(), groupElement.getClassView(), element);
			
			String strSizeColumn = match.group(POSITION_SIZE_COLUMN);
			
			if(!Condition.empty(strSizeColumn)) builder.sizeColumn(Integer.parseInt(strSizeColumn));
			
			groupElement.element(builder.build());
		}
		
	}
	
	public <B extends AbstractElementViewBuilder<?>> B builder(Class<B> classBuilder) {
		
		try {
			
			return classBuilder.newInstance();
			
		} catch (Exception ex) {
			
			throw new BuildException(ex);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public <B extends AbstractElementViewBuilder<?>> B builder(
		Class<B> classBuilder, Class<? extends IView> classView, String name) {
		
		try {
			
			return (B) classBuilder.getMethod(
				"builder", Class.class, String.class).invoke(null, classView, name);
			
		} catch (Exception ex) {
			
			throw new BuildException(ex);
		}
		
	}
}
