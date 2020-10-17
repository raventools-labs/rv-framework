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

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.ravencloud.util.db.DBManager;
import com.ravencloud.util.db.PersistenceManager;

public enum ParamDao {

	INSTANCE;

	public String getValue(String key) {

		try(DBManager em = PersistenceManager.INSTANCE.getManager()) {

			TypedQuery<String> query = em.createNamedQuery("Param.getValueByKey", String.class);

			query.setParameter("key", key);

			return query.getSingleResult();

		} catch(NoResultException ex) {

			return null;
		}
	}
}
