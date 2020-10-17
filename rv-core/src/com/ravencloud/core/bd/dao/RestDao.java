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
package com.ravencloud.core.bd.dao;

import javax.persistence.TypedQuery;

import com.ravencloud.core.bd.model.User;
import com.ravencloud.util.db.DBManager;
import com.ravencloud.util.db.GenericDao;
import com.ravencloud.util.db.PersistenceManager;

public enum RestDao {

	INSTANCE;

	public User getUser(String name) {

		try(DBManager db = PersistenceManager.INSTANCE.getManager()) {

			TypedQuery<User> query = db.createNamedQuery("User.get",User.class)
				.setParameter("name", name)
				.setHint("javax.persistence.loadgraph", db.getEntityGraph("withRoles"));

			return GenericDao.INSTANCE.getSingleResult(query);
		} 
	}
}
