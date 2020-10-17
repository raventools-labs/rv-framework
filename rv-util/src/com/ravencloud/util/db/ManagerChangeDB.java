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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.dialect.Dialect;
import org.hibernate.internal.SessionFactoryImpl;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum ManagerChangeDB {

	INSTANCE;
	
	public static final String DEFAULT_DATABASE = "DEFAULT_DATABASE";

	public synchronized void checkUpdate(String ruteChangelog,String defaultDatabase)  {

		try (DBManager entityManager = PersistenceManager.INSTANCE.getManagerWithoutBeginTransaction()) {

			Session hibernateSession = entityManager.unwrap(Session.class);
			
			final SessionFactoryImpl sessionFactory = (SessionFactoryImpl) hibernateSession.getSessionFactory();
			
			final Dialect dialect = sessionFactory.getJdbcServices().getDialect();

			hibernateSession.doWork(new org.hibernate.jdbc.Work() {
				
				@Override
				public void execute(Connection connection) throws SQLException {
					
					try {
						
						createDatabaseIfNotExists(connection, defaultDatabase, dialect);
						
						System.setProperty(DEFAULT_DATABASE, defaultDatabase);
						
						@SuppressWarnings("resource")
						Liquibase liquibase = new Liquibase(ruteChangelog,
							new ClassLoaderResourceAccessor(),
							getDatabase(connection, defaultDatabase));
						
						liquibase.clearCheckSums();
						
						liquibase.update(new Contexts(), new LabelExpression());
						
					} catch (Exception ex) {
						
						log.error("Changes in database could not be updated", ex);
					}
					
				}
			});
		}
	}
	
	private void createDatabaseIfNotExists(Connection connection, String defaultDatabase, Dialect dialect) throws SQLException {

		if(!existsDatabase(connection, defaultDatabase, dialect)) {
			
			String sql;
			
			if(dialect.canCreateSchema()) {
				
				sql = dialect.getCreateSchemaCommand(defaultDatabase)[0];
				
			} else {
				
				sql = dialect.getCreateCatalogCommand(defaultDatabase)[0];
			}
			
			try(DBManager db = PersistenceManager.INSTANCE.getManager()) {
				
				db.createNativeQuery(String.format(sql, defaultDatabase)).executeUpdate();
			}
		}
	}
	
	private boolean existsDatabase(Connection connection, String defaultDatabase, Dialect dialect) throws SQLException {
		
		boolean exists = false;
		
		ResultSet resultSetAux;
		
		if(dialect.canCreateSchema()) {
			
			resultSetAux = connection.getMetaData().getSchemas();
			
		} else {
			
			resultSetAux = connection.getMetaData().getCatalogs();
		}
			
		try (ResultSet resultSet = resultSetAux) {
			
			while(!exists && resultSet.next()) {

				exists = resultSet.getString(POSITION_DATABASE).equalsIgnoreCase(defaultDatabase);
			}
			
		} catch (SQLException ex) {
			
			log.warn("Fail to read list databases",ex);
		}	
		
		return exists;
	}
	
	private static final int POSITION_DATABASE = 1;

	private Database getDatabase(Connection connection,String defaultDatabase) throws DatabaseException, SQLException {
		
		connection.setSchema(defaultDatabase);

		Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

		database.setDefaultSchemaName(defaultDatabase);
		
		return database;
	}
}
