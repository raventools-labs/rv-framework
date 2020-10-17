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

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;

import org.slf4j.LoggerFactory;

import com.ravencloud.core.implemented.view.LogbackView;
import com.ravencloud.core.iu.action.AbstractAction;
import com.ravencloud.core.iu.action.ManagerActions;
import com.ravencloud.core.iu.annotation.Action;
import com.ravencloud.util.general.Condition;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

@Action(key = "reconfigureLogger")
public class ReconfigureLogger extends AbstractAction<LogbackView> {
	
	public ReconfigureLogger(String nameModule,String json) {
		super(nameModule,json);
	}
	
	@Override
	public void setUp() throws ValidationException {
		
		if(Condition.empty(getView().getName()) || Condition.empty(getView().getLevel())) {
			
			throw new ValidationException("prueba");
		}
	}

	@Override
	public Response run() {
			
		Logger logger = (Logger) LoggerFactory.getLogger(getView().getName());
		
		logger.setLevel(Level.toLevel(getView().getLevel()));
		
		return ManagerActions.redirect("");
	}

	@Override
	public Class<LogbackView> getViewClass() { return LogbackView.class; }
}
