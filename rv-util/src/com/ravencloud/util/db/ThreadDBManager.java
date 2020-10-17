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

import java.io.Closeable;

public enum ThreadDBManager implements Closeable {

	INSTANCE;

	private ThreadLocal<DBManager> manager;

	private ThreadDBManager() {

		manager = new ThreadLocal<>();
	}

	public ThreadDBManager init() {

		DBManager db = PersistenceManager.INSTANCE.getManagerWithoutBeginTransaction(true);

		db.getTransaction().begin();
		
		manager.set(db);
		
		return this;
	}

	public DBManager get() {
		
		return manager.get();
	}

	@Override
	public void close() {

		DBManager db = manager.get();

		db.unlockClose();

		manager.remove();
		
		db.close();
	}
}
