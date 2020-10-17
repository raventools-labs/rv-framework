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

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.LocaleUtils;

import com.ravencloud.util.general.App;
import com.ravencloud.util.general.ClassUtils;
import com.ravencloud.util.general.Condition;
import com.ravencloud.util.labels.LabelsStrategy;

public enum SessionManager {

	INSTANCE;
	
	private ISessionManager manager;
	
	private synchronized ISessionManager getManager() {

		if(Condition.empty(manager)) {
			
			manager = ClassUtils.INSTANCE.getInstance(App.INSTANCE.classSessionManager());
		}
		
		return manager;
	}

	public void setSession(HttpSession session) {
		getManager().setSession(session);
	}

	public void unsetSession() {
		getManager().unsetSession();
	}

	public void set(String key, Object value) {
		getManager().set(key, value);
	}

	public void set(String key, String value) {
		getManager().set(key, value);
	}

	public <T> T get(String key, Class<T> clazz) {
		return getManager().get(key,clazz);
	}

	public String get(String key) {
		return getManager().get(key);
	}

	public void sessionDestroyed(HttpSession session) {
		getManager().sessionDestroyed(session);
	}
	
	public boolean isSignIn() {
		
		return !Condition.empty(getManager().get(SessionParameter.TOKEN));
	}
	
	public String getStringLocale() {
		
		return Condition.evalNotEmpty(SessionManager.INSTANCE.get(SessionParameter.LOCALE),
			LabelsStrategy.getDefault());
	}
	
	public Locale getLocale() {
		
		return LocaleUtils.toLocale(getStringLocale());
	}
}
