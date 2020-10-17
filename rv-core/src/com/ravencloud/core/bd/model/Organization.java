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
package com.ravencloud.core.bd.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

import com.ravencloud.util.db.DatabaseConstantsApp;
import com.ravencloud.util.db.model.AbstractBasicFieldAllTable;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(catalog = DatabaseConstantsApp.MASTER_DATABASE, name = "ORGANIZATION")
@NamedQueries({
	@NamedQuery(
		name = "Organization.getByDomain",
		query = "SELECT o FROM Organization o WHERE o.domain = :domain", hints = {
			@QueryHint(name = "org.hibernate.cacheable", value = "true"),
			@QueryHint(name = "org.hibernate.cacheRegion", value = "Queries") }
	), @NamedQuery(
		name = "Organization.getAllActive", query = "SELECT o FROM Organization o WHERE o.active = :active"
	)
})
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "O_ID")),
	@AttributeOverride(name = "creationTimestamp", column = @Column(name = "O_CREATION_TIMESTAMP")),
	@AttributeOverride(name = "lastUpdate", column = @Column(name = "O_LAST_UPDATE")),
	@AttributeOverride(name = "lastUserModify", column = @Column(name = "O_LAST_USER_MODIFY"))
})
public class Organization extends AbstractBasicFieldAllTable {

	@Column(name = "O_NAME", length = 40)
	@Getter @Setter private String name;

	@Column(name = "O_DESCRIPTION", length = 200)
	@Getter @Setter private String description;

	@Column(name = "O_DOMAIN", length = 150)
	@Getter @Setter private String domain;

	@Column(name = "O_PREFFIXDB", length = 5)
	@Getter @Setter private String preffixDatabase;
	
	@Column(name = "O_ACTIVE")
	@Getter @Setter private boolean active;
}
