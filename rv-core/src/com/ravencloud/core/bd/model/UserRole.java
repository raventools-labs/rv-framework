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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ravencloud.util.db.DatabaseConstantsApp;
import com.ravencloud.util.db.model.AbstractBasicFieldAllTable;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(catalog = DatabaseConstantsApp.GENERAL_DATABASE, name = "USER_ROL")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "U_ID")),
	@AttributeOverride(name = "creationTimestamp", column = @Column(name = "U_CREATION_TIMESTAMP")),
	@AttributeOverride(name = "lastUpdate", column = @Column(name = "U_LAST_UPDATE")),
	@AttributeOverride(name = "lastUserModify", column = @Column(name = "U_LAST_USER_MODIFY"))
})
public class UserRole extends AbstractBasicFieldAllTable {

	@JoinColumn(name = "U_USER", referencedColumnName = "U_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	@Getter @Setter private User user;

	@Column(name = "U_ROLE", length = 45)
	@Getter @Setter private String role;
}
