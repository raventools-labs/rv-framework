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

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.reflections.Reflections;

import com.ravencloud.core.iu.annotation.Action;
import com.ravencloud.core.labels.ManagerLabels;
import com.ravencloud.util.general.Json;

import lombok.Getter;

public enum ManagerActions {

	INSTANCE;

	@Getter private Map<String,Class<? extends IAction>> actions;

	private ManagerActions() {

		actions = new HashMap<>();
	}

	public Class<? extends IAction> getAction(String action) {
		
		return actions.get(action);
	}

	public static Response redirect(String url) {

		ResponseAction rAction = new ResponseAction();

		rAction.setScript("$(location).attr('href', '" + url + "')");

		return Response.ok(Json.getInstance().toJson(rAction)).build();
	}

	public static Response error(String messageError) {

		ResponseAction response = new ResponseAction();

		response.setMessage(ManagerLabels.INSTANCE.get(messageError));

		response.setError(true);

		return Response.ok(Json.getInstance().toJson(response)).build();
	}

	public synchronized void addActions(String packageToScan) {
		
		Reflections reflections = new Reflections(packageToScan);
		
		for(Class<? extends IAction> type : reflections.getSubTypesOf(IAction.class)) {
			
			if(type.isAnnotationPresent(Action.class)) {
				
				actions.put(type.getAnnotation(Action.class).key(), type);
			}
		}
	}
}
