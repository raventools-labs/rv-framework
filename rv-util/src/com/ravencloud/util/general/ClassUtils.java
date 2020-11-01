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
package com.ravencloud.util.general;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Enumeration;

import com.ravencloud.util.exception.ProgrammingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum ClassUtils {
	
	INSTANCE;
	
	private static final String METHOD_GET_INSTANCE = "getInstance";
	
	@SuppressWarnings("unchecked")
	public <T> T getInstance(Class<T> type) {
		
		try {
			
			return (T) type.getMethod(METHOD_GET_INSTANCE).invoke(null);
			
		} catch (Exception ex) {
			
			throw new ProgrammingException("Not access to getInstance", ex);
		}
		
	}
	
	public boolean isInterface(Class<?> type, Class<?> typeInterface) {
		
		boolean isInterface = false;
		
		Class<?>[] interfaces = type.getInterfaces();
		
		for(int i = 0; !isInterface && i < interfaces.length; i++) {
			
			isInterface = interfaces[i].equals(typeInterface);
		}
		
		if(isInterface || type.getSuperclass() == null) {
			
			return isInterface;
			
		} else {
			
			return isInterface(type.getSuperclass(), typeInterface);
		}
		
	}
	
	public InputStream getResourceFromClasspath(String relativePath) throws IOException {
		
		try {
			
			return new FileInputStream(getPathClass(relativePath));
			
		} catch (IOException e) {
			
			return Thread.currentThread().getContextClassLoader().getResourceAsStream(relativePath);
		}
	}
	
	public File getPathClass(String relativePath) throws IOException {
		
		File file = getDefaultPathClass(relativePath);
		
		Enumeration<URL> en = Thread.currentThread().getContextClassLoader().getResources("");
		
		while (!file.exists() && en.hasMoreElements()) {
			
			try {
				
				file = getFile(en.nextElement(), relativePath);
				
			} catch (URISyntaxException ex) {
				
				log.warn(ex.getMessage());
			}
			
		}
		
		return file;
	}
	
	public File getDefaultPathClass(String relativePath) {
		
		return new File(relativePath);
	}
	
	public String getPackage(Class<?> type) {
		
		return type.getPackage().getName();
	}
	
	private File getFile(URL url, String relativePath) throws URISyntaxException {
		
		URI uri = url.toURI();
		
		String path = uri.getPath();
		
		String newPath = path + relativePath;
		
		return Paths.get(uri.resolve(newPath)).toFile();
	}
}
