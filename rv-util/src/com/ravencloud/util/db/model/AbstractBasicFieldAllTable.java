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
package com.ravencloud.util.db.model;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.ravencloud.util.general.App;
import com.ravencloud.util.general.ClassUtils;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public abstract class AbstractBasicFieldAllTable extends AbstractIdentifiable {

	@Getter @Setter private transient LocalDateTime creationTimestamp;

	@Getter @Setter private transient LocalDateTime lastUpdate;
	
	@Getter @Setter private String lastUserModify;

	protected AbstractBasicFieldAllTable() {
		super();
	}

	public AbstractBasicFieldAllTable(ValueNull valueNull) {
		super(valueNull);
	}

	@PrePersist
	private void onPrePersit() {

		creationTimestamp = LocalDateTime.now();
		lastUpdate = LocalDateTime.now();
		lastUserModify = ClassUtils.INSTANCE.getInstance(App.INSTANCE.classManagerCurrentUser()).getNameUser();
	}

	@PreUpdate
	private void onPreUpdate() {

		lastUpdate = LocalDateTime.now();
		lastUserModify = ClassUtils.INSTANCE.getInstance(App.INSTANCE.classManagerCurrentUser()).getNameUser();
	}
}
