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

import java.util.HashMap;
import java.util.Map;

import org.reflections.Reflections;

import com.ravencloud.core.implemented.view.Error404View;
import com.ravencloud.core.iu.annotation.View;
import com.ravencloud.core.iu.model.IModuleView;
import com.ravencloud.util.general.Condition;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum ManagerViews {

	INSTANCE;

	@Getter private Map<String, IModuleView> views;

	private ManagerViews() {

		views = new HashMap<>();
	}

	@SuppressWarnings("unchecked")
	public <M> String get(String name) {
		
		IModuleView<M> template = views.get(name);
		
		if(Condition.empty(template)) {
			
			template = views.get(Error404View.MODULE_NAME);
		}
		
		return template.getView().render(template.getInitModel(name));
	}

	public synchronized void addViews(String packageToScan) {
		
		Reflections reflections = new Reflections(packageToScan);
		
		for(Class<? extends IModuleView> type : reflections.getSubTypesOf(IModuleView.class)) {
			
			if(type.isAnnotationPresent(View.class)) {
				
				try {
					
					views.put(type.getAnnotation(View.class).name(), type.newInstance());
					
				} catch (InstantiationException | IllegalAccessException ex) {
					
					log.warn("Type " + type + " not load: " + ex.getMessage());
				}
			}
		}
	}
}
