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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import com.ravencloud.util.safenull.AbstractSafeNull;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public abstract class AbstractIdentifiable extends AbstractSafeNull implements Serializable {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "com.ravencloud.util.db.UUIDGenerator")
	@Column(length = 32)
	@Getter @Setter private String id;

	protected AbstractIdentifiable() {
		super();
	}

	public AbstractIdentifiable(ValueNull valueNull) {
		super(valueNull);
	}
}
