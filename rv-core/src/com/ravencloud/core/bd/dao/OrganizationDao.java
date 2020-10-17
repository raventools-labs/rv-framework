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

import java.util.List;

import javax.persistence.NoResultException;

import com.ravencloud.core.bd.model.Organization;
import com.ravencloud.util.db.DBManager;
import com.ravencloud.util.db.PersistenceManager;

public enum OrganizationDao {

	INSTANCE;
	
	public Organization getByDomain(String domain) {

		try (DBManager db = PersistenceManager.INSTANCE.getManager()) {

			return db.createNamedQuery("Organization.getByDomain", Organization.class)
				.setParameter("domain", domain)
				.getSingleResult();

		} catch (NoResultException ex) {

			return null;
		}
	}
	
	public List<Organization> getAllActive() {
		
		try (DBManager db = PersistenceManager.INSTANCE.getManager()) {
			
			return db.createNamedQuery("Organization.getAllActive", Organization.class)
				.setParameter("active", true)
				.getResultList();
		}
	}
}
