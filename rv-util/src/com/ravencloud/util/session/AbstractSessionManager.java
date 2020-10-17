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
package com.ravencloud.util.session;

import javax.servlet.http.HttpSession;

import lombok.Getter;

public abstract class AbstractSessionManager implements ISessionManager {

	@Getter private ThreadLocal<String> sessionId;
	
	protected AbstractSessionManager() {
		sessionId = new ThreadLocal<>();
	}
	
	@Override
	public void setSession(HttpSession session) {
		sessionId.set(session.getId());
	}

	protected String getSession() {
		return sessionId.get();
	}

	@Override
	public void unsetSession() {
		sessionId.remove();
	}
}
