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

public final class Message {
	
	public static final InfoMessage info = InfoMessage.INSTANCE;
	
	public static final ErrorMessage err = ErrorMessage.INSTANCE;

	private Message() {}
	
	public enum InfoMessage {
		
		INSTANCE;
		
		public static final String TAG_INFO = "Message.info.";
	}
	
	public enum ErrorMessage {
		
		INSTANCE;
		
		private static final String TAG_ERROR = "Message.err.";
		
		public final String processIsFail = TAG_ERROR + "processIsFail";
		
		public final String badCredentials = TAG_ERROR + "badCredentials";
		
		public final String emptyCredentials = TAG_ERROR + "emptyCredentials";
	}
}
