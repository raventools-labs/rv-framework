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
package com.ravencloud.core.iu.model;

import lombok.Getter;

public class RavencloudContext<M> {
	
	@Getter private String moduleName;
	
	@Getter private M model;
	
	public RavencloudContext(String moduleName, M model) {
		
		this.moduleName = moduleName;
		
		this.model = model;
	}
	
	public RavencloudContext(String moduleName) {
		
		this.moduleName = moduleName;
	}
}
