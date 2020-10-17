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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class CredentialsRest implements Serializable {

	@Column(name = "U_NAME", length = 50)
	@Getter @Setter private String name;
	
	@Column(name = "U_PASSWORD", length = 32)
	@Getter @Setter private String password;
	
	@Column(name = "U_SALT", length = 32)
	@Getter @Setter private String salt;
	
	@Column(name = "U_TOTPKEY", length = 32)
	@Getter @Setter private String totpKey;
}
