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
package com.ravencloud.core.iu.action;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ravencloud.core.iu.model.IView;
import com.ravencloud.util.general.Condition;
import com.ravencloud.util.general.Json;

import lombok.Getter;

public abstract class AbstractAction<V extends IView> implements IAction {

	protected Logger log;
	
	@Getter private String nameModule;

	@Getter private V view;

	@Getter private boolean auditable;

	@Getter private long timeInitAction;

	public AbstractAction(String nameModule,String json) {

		this.nameModule = nameModule;

		if(!Condition.empty(json)) initView(json);

		log = LoggerFactory.getLogger(getClass());

		this.auditable = true;
	}

	public AbstractAction(String nameModule,String json,boolean auditable) {

		this.nameModule = nameModule;

		initView(json);

		log = LoggerFactory.getLogger(getClass());

		this.auditable = auditable;
	}

	private void initView(String json) {

		this.view = Json.getInstance().fromJson(json, getViewClass());
	}

	public abstract Class<V> getViewClass();

	@Override
	public Response execute() {

		timeInitAction = System.currentTimeMillis();

		try {

			setUp();

			return run();

		} finally {
			
			if(auditable) {

				String message = String.format("Execute in %s ms",
					String.valueOf((System.currentTimeMillis() - timeInitAction)));
				
				log.info(message);
			}
		}
	}

	public abstract void setUp() throws ValidationException;

	public abstract Response run();
}
