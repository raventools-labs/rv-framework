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

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class QueueJobMonitor<C> {
	
	private Queue<C> queue;
	
	private final Lock lock;

	public QueueJobMonitor() {
		
		queue = new LinkedList<>();
		
		lock = new ReentrantLock();
	}

	public void add(C container) {
	
		try {
			
			lock.lock();
			
			queue.add(container);
		
		} finally {
			
			lock.unlock();
		}
	}
	
	public C get() {
		
		try {
			
			lock.lock();
			
			return queue.poll();

		} finally {
			
			lock.unlock();
		}
	}
	
	public int size() {
		
		try {
			
			lock.lock();
			
			return queue.size();
		
		} finally {
			
			lock.unlock();
		}
	}
}
