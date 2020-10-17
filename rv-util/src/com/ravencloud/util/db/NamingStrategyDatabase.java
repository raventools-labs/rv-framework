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

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import com.ravencloud.util.general.App;
import com.ravencloud.util.general.Condition;

public class NamingStrategyDatabase implements PhysicalNamingStrategy {

	public static final String KEY_WITH_SUFFIX = "db.database.suffix.";

	private Identifier catalog;
	
	public NamingStrategyDatabase() {
		
		catalog = null;
	}
	
	@Override
	public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		
		catalog = name;
		
		return getIdentifier(name, jdbcEnvironment.getDialect().canCreateSchema());
	}

	@Override
	public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		
		if(!Condition.empty(catalog)) {
			
			name = catalog;
			
			catalog = null;
		}
		
		return getIdentifier(name, !jdbcEnvironment.getDialect().canCreateSchema());
	}

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		
		return name;
	}

	@Override
	public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return name;
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return name;
	}
	
	private static Identifier getIdentifier(Identifier identifier, boolean isTypeIncorrectSupport) {
		
		if(isTypeIncorrectSupport) identifier = null;

		if(identifier != null) {
			
			String keyProperty = identifier.getText();
	
			if(keyProperty.startsWith(KEY_WITH_SUFFIX)) {
	
				String preffix = "";
				
				if(App.INSTANCE.multiOrganization()) preffix = OrganizationDatabaseSelector.INSTANCE.getDatabase();

				String strIdentifier = Condition.evalNotEmpty(preffix, "");
				
				String suffix = App.INSTANCE.getProperty(String.class,keyProperty);
	
				strIdentifier += Condition.evalNotEmpty(suffix, "");
	
				if(!Condition.empty(strIdentifier)) identifier = Identifier.toIdentifier(strIdentifier);
				
			} else if(keyProperty.contains(DatabaseConstantsApp.MASTER_DATABASE)) {
				
				identifier = Identifier.toIdentifier(App.INSTANCE.masterDatabase());
			}
		}

		return identifier;
	}

}
