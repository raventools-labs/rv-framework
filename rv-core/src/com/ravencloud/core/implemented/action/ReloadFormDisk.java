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
package com.ravencloud.core.implemented.action;

import java.net.URL;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;

import org.slf4j.LoggerFactory;

import com.ravencloud.core.implemented.view.LogbackView;
import com.ravencloud.core.iu.action.AbstractAction;
import com.ravencloud.core.iu.action.ManagerActions;
import com.ravencloud.core.iu.annotation.Action;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

@Action(key = "reloadFromDisk")
public class ReloadFormDisk extends AbstractAction<LogbackView> {
	
	public ReloadFormDisk(String nameModule,String json) {
		super(nameModule,json);
	}
	
	@Override
	public void setUp() throws ValidationException {}

	@Override
	public Response run() {
			
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		
		ContextInitializer ci = new ContextInitializer(loggerContext);
		
		URL url = ci.findURLOfDefaultConfigurationFile(true);
		
		try {
			
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(loggerContext);
			loggerContext.reset();
			configurator.doConfigure(url);
			
		} catch (JoranException ignored) {}
		
		StatusPrinter.printInCaseOfErrorsOrWarnings(loggerContext);
		
		return ManagerActions.redirect("");
	}

	@Override
	public Class<LogbackView> getViewClass() { return LogbackView.class; }
}
