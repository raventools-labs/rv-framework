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
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

import lombok.Getter;

public class DBManager implements EntityManager, Closeable {

	private EntityManager manager;

	private boolean rollback;

	@Getter private boolean lockClose;

	public DBManager(EntityManager manager) {
		
		this(manager, false);
	}

	public DBManager(EntityManager manager,boolean lockClose) {
		
		this.manager = manager;
		rollback = false;
		this.lockClose = lockClose;
	}

	@Override
	public void persist(Object entity) {
		manager.persist(entity);
	}

	@Override
	public <T> T merge(T entity) {
		return manager.merge(entity);
	}

	@Override
	public void remove(Object entity) {
		manager.remove(entity);
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey) {
		return manager.find(entityClass, primaryKey);
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
		return manager.find(entityClass, primaryKey, properties);
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
		return manager.find(entityClass, primaryKey, lockMode);
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
		return manager.find(entityClass, primaryKey, lockMode, properties);
	}

	@Override
	public <T> T getReference(Class<T> entityClass, Object primaryKey) {
		return manager.getReference(entityClass, primaryKey);
	}

	@Override
	public void flush() {
		manager.flush();
	}

	@Override
	public void setFlushMode(FlushModeType flushMode) {
		manager.setFlushMode(flushMode);
	}

	@Override
	public FlushModeType getFlushMode() {
		return manager.getFlushMode();
	}

	@Override
	public void lock(Object entity, LockModeType lockMode) {
		manager.lock(entity, lockMode);
	}

	@Override
	public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
		manager.lock(entity, lockMode, properties);
	}

	@Override
	public void refresh(Object entity) {
		manager.refresh(entity);
	}

	@Override
	public void refresh(Object entity, Map<String, Object> properties) {
		manager.refresh(entity, properties);
	}

	@Override
	public void refresh(Object entity, LockModeType lockMode) {
		manager.refresh(entity, lockMode);
	}

	@Override
	public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
		manager.refresh(entity, lockMode, properties);

	}

	@Override
	public void clear() {
		manager.clear();
	}

	@Override
	public void detach(Object entity) {
		manager.detach(entity);
	}

	@Override
	public boolean contains(Object entity) {
		return manager.contains(entity);
	}

	@Override
	public LockModeType getLockMode(Object entity) {
		return manager.getLockMode(entity);
	}

	@Override
	public void setProperty(String propertyName, Object value) {
		manager.setProperty(propertyName, value);
	}

	@Override
	public Map<String, Object> getProperties() {
		return manager.getProperties();
	}

	@Override
	public Query createQuery(String qlString) {
		return manager.createQuery(qlString);
	}

	@Override
	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
		return manager.createQuery(criteriaQuery);
	}

	@Override
	public Query createQuery(@SuppressWarnings("rawtypes") CriteriaUpdate updateQuery) {
		return manager.createQuery(updateQuery);
	}

	@Override
	public Query createQuery(@SuppressWarnings("rawtypes") CriteriaDelete deleteQuery) {
		return manager.createQuery(deleteQuery);
	}

	@Override
	public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
		return manager.createQuery(qlString, resultClass);
	}

	@Override
	public Query createNamedQuery(String name) {
		return manager.createNamedQuery(name);
	}

	@Override
	public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
		return manager.createNamedQuery(name, resultClass);
	}

	@Override
	public Query createNativeQuery(String sqlString) {
		return manager.createNativeQuery(sqlString);
	}

	@Override
	public Query createNativeQuery(String sqlString, @SuppressWarnings("rawtypes") Class resultClass) {
		return manager.createNativeQuery(sqlString, resultClass);
	}

	@Override
	public Query createNativeQuery(String sqlString, String resultSetMapping) {
		return manager.createNativeQuery(sqlString, resultSetMapping);
	}

	@Override
	public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
		return manager.createNamedStoredProcedureQuery(name);
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
		return manager.createStoredProcedureQuery(procedureName);
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(String procedureName,
			@SuppressWarnings("rawtypes") Class... resultClasses) {

		return manager.createStoredProcedureQuery(procedureName, resultClasses);
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(String procedureName, String... resultSetMappings) {
		return manager.createStoredProcedureQuery(procedureName, resultSetMappings);
	}

	@Override
	public void joinTransaction() {
		manager.joinTransaction();
	}

	@Override
	public boolean isJoinedToTransaction() {
		return manager.isJoinedToTransaction();
	}

	@Override
	public <T> T unwrap(Class<T> cls) {
		return manager.unwrap(cls);
	}

	@Override
	public Object getDelegate() {
		return manager.getDelegate();
	}

	@Override
	public boolean isOpen() {
		return manager.isOpen();
	}

	@Override
	public EntityTransaction getTransaction() {
		return manager.getTransaction();
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return manager.getEntityManagerFactory();
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return manager.getCriteriaBuilder();
	}

	@Override
	public Metamodel getMetamodel() {
		return manager.getMetamodel();
	}

	@Override
	public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
		return manager.createEntityGraph(rootType);
	}

	@Override
	public EntityGraph<?> createEntityGraph(String graphName) {
		return manager.createEntityGraph(graphName);
	}

	@Override
	public EntityGraph<?> getEntityGraph(String graphName) {
		return manager.getEntityGraph(graphName);
	}

	@Override
	public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
		return manager.getEntityGraphs(entityClass);
	}

	public void rollback() {

		if(manager != null && manager.isOpen()){

			manager.getTransaction().rollback();

			rollback = true;
		}
	}

	public void unlockClose() {
		this.lockClose = false;
	}

	@Override
	public void close() throws RollbackException {
		
		if(!lockClose && manager != null && manager.isOpen()) {

			try {
				
				if(manager.getTransaction().isActive() && !rollback) {
					
					manager.getTransaction().commit();
				}

			} catch(RollbackException ex) {

				rollback();
				
				throw ex;

			} finally {

				manager.close();
			}
		}
	}
}
