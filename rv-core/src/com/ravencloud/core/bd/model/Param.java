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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

import com.ravencloud.util.db.DatabaseConstantsApp;
import com.ravencloud.util.db.model.AbstractIdentifiable;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(catalog = DatabaseConstantsApp.GENERAL_DATABASE, name = "PARAM")
@NamedQueries({
	@NamedQuery(
			name="Param.getValueByKey",
			query="SELECT p.value FROM Param p WHERE p.name = :name",
			hints={
					@QueryHint(name="org.hibernate.cacheable", value="true"),
					@QueryHint(name="org.hibernate.cacheRegion",value="Queries")
			})
})
@AttributeOverride(name = "id", column = @Column(name = "P_ID"))
public class Param extends AbstractIdentifiable {

	@Column(name = "P_NAME", length = 100)
	@Getter @Setter private String name;

	@Column(name = "P_VALUE", length = 500)
	@Getter @Setter private String value;
}
