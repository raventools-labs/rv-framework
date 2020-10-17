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

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ravencloud.util.db.DatabaseConstantsApp;
import com.ravencloud.util.db.model.AbstractBasicFieldAllTable;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(catalog = DatabaseConstantsApp.GENERAL_DATABASE, name = "USERS")
@NamedQuery(
	name = "User.get",
	query = "FROM User u WHERE u.credentials.name = :name OR u.email = :name"
)
@NamedEntityGraph(name = "withRoles", attributeNodes = {@NamedAttributeNode("roles")})

@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "U_ID")),
	@AttributeOverride(name = "creationTimestamp", column = @Column(name = "U_CREATION_TIMESTAMP")),
	@AttributeOverride(name = "lastUpdate", column = @Column(name = "U_LAST_UPDATE")),
	@AttributeOverride(name = "lastUserModify", column = @Column(name = "U_LAST_USER_MODIFY"))
})
public class User extends AbstractBasicFieldAllTable {

	@Embedded
	@Getter @Setter private CredentialsRest credentials;
	
	@Column(name = "U_EMAIL", length = 320)
	@Getter @Setter private String email;

	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@Getter private List<UserRole> roles;
}
