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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import com.ravencloud.util.exception.ProgrammingException;

/*
 * Inspired on: https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-reflection/src/main/java/com/baeldung/reflection/java/reflection/GreetingAnnotation.java
 */

public enum AnnotationUtil {
	
	INSTANCE;
	
	private static final String ANNOTATION_METHOD = "annotationData";
	
	private static final String ANNOTATIONS = "annotations";
	
	@SuppressWarnings("unchecked")
	public void alterAnnotation(Class<?> targetClass, Class<? extends Annotation> targetAnnotation, Annotation targetValue) {
		
		try {
			
			Method method = Class.class.getDeclaredMethod(ANNOTATION_METHOD);
			method.setAccessible(true);
			
			Object annotationData = method.invoke(targetClass);
			
			Field annotations = annotationData.getClass().getDeclaredField(ANNOTATIONS);
			annotations.setAccessible(true);
			
			Map<Class<? extends Annotation>, Annotation> map = (Map<Class<? extends Annotation>, Annotation>) annotations.get(annotationData);
			map.put(targetAnnotation, targetValue);
			
			method.setAccessible(false);
			annotations.setAccessible(false);
			
		} catch (Exception ex) {
			
			throw new ProgrammingException("Not change annotation", ex);
		}
	}
}
