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

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.ravencloud.util.db.DatabaseConstantsApp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(catalog = DatabaseConstantsApp.GENERAL_DATABASE, name = "ACTIVITY_HISTORY")
public class ActivityHistory {

	@PrePersist
	private void triggerPrePersist() {

		executionTime = System.currentTimeMillis() - timestamp.getTime();
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "com.ravencloud.util.db.UUIDGenerator")
	@Column(name = "A_ID", length = 32)
	@Getter @Setter private String id;

	@Column(name = "A_TIMESTAMP")
	@Getter @Setter private Timestamp timestamp;

	@Column(name = "A_IP", length = 40)
	@Getter @Setter private String ip;

	@Column(name = "A_USER", length = 50)
	@Getter @Setter private String user;

	@Column(name = "A_METHOD", length = 80)
	@Getter @Setter private String method;

	@Column(name = "A_OBSERVATIONS", length = 300)
	@Getter @Setter private String observations;

	@Column(name = "A_STATUS",length = 5)
	@Getter @Setter private int status;

	@Column(name = "A_EXECUTION_TIME",length = 10)
	@Getter @Setter private long executionTime;

	public ActivityHistory() {
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}
}
