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

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.codec.digest.DigestUtils;

import com.ravencloud.util.general.Condition;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum PersistenceManager {

	INSTANCE;
	
	public static final String DEFAULT_PERSISTENCE_UNIT = "default";
	
	@Setter private String defaultPersistenceUnit;

	@Getter private Map<String,EntityManagerFactory> factorys;

	private PersistenceManager() {

		factorys = new HashMap<>();
		
		defaultPersistenceUnit = DEFAULT_PERSISTENCE_UNIT;
	}

	public DBManager getManagerWithoutBeginTransaction(String persistenceUnitName,boolean lockClose){

		DBManager db = ThreadDBManager.INSTANCE.get();

		if(Condition.empty(db)) {

			EntityManagerFactory factory = getFactory(persistenceUnitName);

			factory = Condition.evalNotEmpty(factory, getFactory(defaultPersistenceUnit));

			db = new DBManager(factory.createEntityManager(),lockClose);
		}

		return db;
	}

	public DBManager getManagerWithoutBeginTransaction(boolean lockClose){

		return getManagerWithoutBeginTransaction(defaultPersistenceUnit,lockClose);
	}
	
	public DBManager getManagerWithoutBeginTransaction(){

		return getManagerWithoutBeginTransaction(false);
	}

	public DBManager getManager(String persistenceUnitName){

		DBManager db = getManagerWithoutBeginTransaction(persistenceUnitName,false);
		
		if(!db.isLockClose()) db.getTransaction().begin();

		return db;
	}

	public DBManager getManager(){

		return getManager(defaultPersistenceUnit);
	}

	private synchronized EntityManagerFactory getFactory(String persistenceUnitName) {

		String key = getKeyFactory(persistenceUnitName);

		EntityManagerFactory factory = factorys.get(key);

		if(Condition.empty(factory)) {

			factory = newFactory(persistenceUnitName);

			factorys.put(key, factory);
		}

		return factory;
	}

	private String getKeyFactory(String persistenceUnitName) {
		
		String preffix = OrganizationDatabaseSelector.INSTANCE.getDatabase();

		String key = persistenceUnitName;

		if(!Condition.empty(preffix)) key = key + ":" + preffix;

		return DigestUtils.sha1Hex(key);
	}

	private EntityManagerFactory newFactory(String persistenceUnitName) {

		EntityManagerFactory factory = null;
		
		try {

			Map<String,String> properties = new HashMap<>();

			factory = Persistence.createEntityManagerFactory(persistenceUnitName, properties);

		} catch(Exception ex) {

			log.warn(String.format("Problems to process %s", persistenceUnitName),ex);
		}
		
		return factory;
	}
}
