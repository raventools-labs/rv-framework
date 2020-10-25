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
package com.ravencloud.core.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.validation.ValidationException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import com.ravencloud.core.bd.model.ActivityHistory;
import com.ravencloud.core.iu.action.IAction;
import com.ravencloud.core.iu.action.ManagerActions;
import com.ravencloud.core.rest.ThreadContextRest;
import com.ravencloud.core.util.Message;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Hidden
@Path("/action")
public class ExecutorAction {

	@POST
	@Path("{nameModule}/{nameAction}")
	public Response executeAction(
			@PathParam("nameModule") String nameModule,
			@PathParam("nameAction") String nameAction,
			InputStream inputParameter) {


		Response response;
		
		ActivityHistory activity = ThreadContextRest.INSTANCE.getActivity();
		
		activity.setMethod(activity.getMethod() + "." + nameAction);

		try(InputStream is = inputParameter) {

			String json = IOUtils.toString(is, StandardCharsets.UTF_8);

			Class<? extends IAction> typeAction = ManagerActions.INSTANCE.getAction(nameAction);

			if(typeAction != null) {

				IAction action = typeAction.getDeclaredConstructor(String.class,String.class).newInstance(nameModule,json);

				response = action.execute();

			} else {

				log.error(String.format("action %s not found", nameAction));

				response = ManagerActions.error(Message.err.processIsFail);
			}

		} catch (ReflectiveOperationException ex) {

			log.error(String.format("bad definition action %s. Constructor must have two parameter (nameModule,json)", nameAction),ex);

			response = ManagerActions.error(Message.err.processIsFail);

		} catch (IOException ex) {

			log.error("Problem to extract json", ex);

			response = ManagerActions.error(Message.err.processIsFail);

		} catch (ValidationException ex) {

			response = ManagerActions.error(ex.getMessage());

		} catch (Exception ex) {

			log.error("Unknown error", ex);

			response = ManagerActions.error(Message.err.processIsFail);
		}

		return response;
	}
}
