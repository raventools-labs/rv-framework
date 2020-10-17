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
package com.ravencloud.core.util;

public final class InternalMessage {
	
	public static final InfoMessage info = InfoMessage.INSTANCE;
	
	public static final ErrorMessage err = ErrorMessage.INSTANCE;

	private InternalMessage() {}
	
	public enum InfoMessage {
		
		INSTANCE;
		
		private static final String TAG_INFO = "InternalMessage.info.";
		
		public final String loginOk = TAG_INFO + "loginOk";
	}
	
	public enum ErrorMessage {
		
		INSTANCE;
		
		private static final String TAG_ERROR = "InternalMessage.err.";
		
		public final String badCredentialsOrWithoutPermision = TAG_ERROR + "badCredentialsOrWithoutPermision";
		
		public final String emptyHeader = TAG_ERROR + "emptyHeader";
	}
}
