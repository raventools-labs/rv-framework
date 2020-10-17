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

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import lombok.Cleanup;

public enum GenericDao {

	INSTANCE;

	public <E> E find(Class<E> entityClass,Object primaryKey) {

		@Cleanup DBManager em = PersistenceManager.INSTANCE.getManager();

		return em.find(entityClass, primaryKey);
	}
	
	public void persist(Object value) {

		@Cleanup DBManager em = PersistenceManager.INSTANCE.getManager();

		em.persist(value);
	}

	public void merge(Object value) {

		@Cleanup DBManager em = PersistenceManager.INSTANCE.getManager();

		em.merge(value);
	}

	public void remove(Object value) {

		@Cleanup DBManager em = PersistenceManager.INSTANCE.getManager();

		em.remove(value);
	}
	
	public <E> E getSingleResult(TypedQuery<E> query) {
		
		try {
			
			return query.getSingleResult();
			
		} catch(NoResultException ex) {
			
			return null;
		}
	}
}
