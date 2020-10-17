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
package com.ravencloud.core.rest;

import com.ravencloud.core.labels.ManagerLabels;

public final class ServiceUtils {
	
	private ServiceUtils() {}
	
	public static void setObservations(String observations) {
		
		ThreadContextRest.INSTANCE.getActivity().setObservations(observations);
	}
	
	public static void setObservationsByKeyLocale(String keyLocal) {
		
		ThreadContextRest.INSTANCE.getActivity().setObservations(
			ManagerLabels.INSTANCE.get(keyLocal, true));
	}
}
