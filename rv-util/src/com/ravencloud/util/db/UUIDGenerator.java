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

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Locale;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.ravencloud.util.general.StringUtilities;
import com.ravencloud.util.general.StringUtilities.Align;

public class UUIDGenerator implements IdentifierGenerator {

	public static final int LENGHT_INDENTITY_HASH = 8;

	public static final int LENGHT_RANDOM_NUMBER = 12;

	public static final int LENGHT_TIMESTAMP = 12;

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object model) {

		return generate(model);
	}

	public static String generate(Object model) {

		String indentityHashCode = hexFormat(System.identityHashCode(model),LENGHT_INDENTITY_HASH);

		return getUUID(indentityHashCode);
	}

	public static String generate(byte[] byteArray) {

		String indentityHashCode = hexFormat(byteArray,LENGHT_INDENTITY_HASH);

		return getUUID(indentityHashCode);
	}

	private static String getUUID(String indentityHashCode) {

		SecureRandom secureRandom = new SecureRandom();

		String randomNumber = hexFormat(secureRandom.nextLong(),LENGHT_RANDOM_NUMBER);

		String timestamp = hexFormat(System.currentTimeMillis(),LENGHT_TIMESTAMP);

		return indentityHashCode + randomNumber + timestamp;
	}

	public static String hexFormat(long number,int lenght) {

		return StringUtilities.format(Long.toHexString(number), lenght, Align.RIGHT, '0')
			.toUpperCase(Locale.getDefault());
	}

	public static String hexFormat(byte[] byteArray,int lenght) {

		return StringUtilities.format(DigestUtils.sha1Hex(byteArray), lenght, Align.RIGHT, '0')
			.toUpperCase(Locale.getDefault());
	}
}
