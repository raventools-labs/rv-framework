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
package com.ravencloud.core.implemented.action;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;

import com.ravencloud.core.implemented.view.NotInitModelView;
import com.ravencloud.core.iu.action.AbstractAction;
import com.ravencloud.core.iu.action.ManagerActions;
import com.ravencloud.core.iu.annotation.Action;
import com.ravencloud.util.session.SessionManager;
import com.ravencloud.util.session.SessionParameter;

@Action(key = "signOut")
public class SignOutAction extends AbstractAction<NotInitModelView> {
	
	public SignOutAction(String nameModule,String json) {
		super(nameModule,json);
	}
	
	@Override
	public void setUp() throws ValidationException {}

	@Override
	public Response run() {
			
		SessionManager.INSTANCE.set(SessionParameter.TOKEN, null);
		
		return ManagerActions.redirect("");
	}

	@Override
	public Class<NotInitModelView> getViewClass() {
		return NotInitModelView.class;
	}
}
