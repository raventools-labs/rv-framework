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
package com.ravencloud.core.rest;

import org.glassfish.jersey.server.ResourceConfig;

import com.ravencloud.util.general.App;

public class RestConfig extends ResourceConfig {
	
    public RestConfig() {
        
		super(AuthenticationFilterRest.class, ResponseFilterRest.class);
		
		packages("com.ravencloud.core.services", App.INSTANCE.restPackages());
		
    }
}
