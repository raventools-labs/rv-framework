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

import static com.ravencloud.core.rest.ServiceUtils.setObservationsByKeyLocale;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;

import com.ravencloud.core.bd.model.CredentialsRest;
import com.ravencloud.core.exception.RestBadStatusException;
import com.ravencloud.core.implemented.view.LoginView;
import com.ravencloud.core.iu.action.AbstractAction;
import com.ravencloud.core.iu.action.ManagerActions;
import com.ravencloud.core.iu.annotation.Action;
import com.ravencloud.core.rest.ThreadContextRest;
import com.ravencloud.core.security.UserManager;
import com.ravencloud.core.util.InternalMessage;

@Action(key = "signIn")
public class SignInAction extends AbstractAction<LoginView> {
	
	public SignInAction(String nameModule,String json) {
		super(nameModule,json);
	}

	@Override
	public void setUp() throws ValidationException {}
	
	@Override
	public Response run() {
		
		Response response;
		
		ThreadContextRest.INSTANCE.getActivity().setUser(getView().getName());
		
		try {
			
			CredentialsRest credentials = new CredentialsRest();
			
			credentials.setName(getView().getName());
			
			credentials.setPassword(getView().getPassword());
			
			UserManager.INSTANCE.login(credentials);
			
			setObservationsByKeyLocale(InternalMessage.info.loginOk);
			
			response = ManagerActions.redirect("");
			
		} catch (RestBadStatusException ex) {
			
			response = ManagerActions.error(ex.getMessage());
		}
		
		return response;
	}

	@Override
	public Class<LoginView> getViewClass() { return LoginView.class; }
}
