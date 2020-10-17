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
package com.ravencloud.util.db;

public enum OrganizationDatabaseSelector {

	INSTANCE;
	
	private ThreadLocal<String> threadDatabase;

	private OrganizationDatabaseSelector() {
		
		threadDatabase = new ThreadLocal<>();
	}

	public void set(String database) {
		
		threadDatabase.set(database);
	}

	public String getDatabase() {
		
		return threadDatabase.get();
	}

	public void unset() {
		
		threadDatabase.remove();
	}
}
