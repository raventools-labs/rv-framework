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
package com.ravencloud.util.concurrent;

import org.quartz.JobDetail;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractManagerGroupJob<J extends AbstractGroupJob<C>,C> {
	
	private QueueJobMonitor<C> monitor;
	
	private int quantityBlockNewJob ;
	
	private int maxJobs;
	
	private String group;
	
	protected AbstractManagerGroupJob(int maxJobs,int quantityBlockNewJob,String group){

		this.quantityBlockNewJob = quantityBlockNewJob;
		
		if(quantityBlockNewJob <= 0) quantityBlockNewJob = 1;
		
		this.maxJobs = maxJobs;
		
		if(maxJobs <= 0) maxJobs = 1;
		
		this.group = group;
		
		monitor = new QueueJobMonitor<>();
	}
	
	public synchronized void addContainerToProccess(C container) {
		
		monitor.add(container);

		int activeJobs = ConcurrentUtilities.INSTANCE.getActiveJobs(ManagerBackgroundJob.getScheduler(), group);
		
		int size = monitor.size();
				
		if(activeJobs <= 0 || (((Math.floorDiv(size, quantityBlockNewJob ) + 1) > activeJobs) && activeJobs < maxJobs)){
			
			JobDetail job = ConcurrentUtilities.INSTANCE.buildJob(getJobClass(), group, monitor);
			
			log.info("Create job " + activeJobs + " " + size);
			
			ConcurrentUtilities.INSTANCE.addJob(job, group);
		}
	}

	protected abstract Class<J> getJobClass();
}
