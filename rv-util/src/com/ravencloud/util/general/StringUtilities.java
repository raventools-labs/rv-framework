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

public class StringUtilities {

	private static final char DEFAULT_ADD_CHARACTER = ' ';
	
	public static String format(String string,int lenght) {
		
		return format(string,lenght,Align.LEFT,DEFAULT_ADD_CHARACTER);
	}
	
	public static String format(String string,int lenght,Align align) {
		
		return format(string,lenght, align, DEFAULT_ADD_CHARACTER);
	}
	
	public static String format(String string,int newLenght,Align align,char addChar) {
		
		String newString = string;
		
		int initLenght = string.length();
		
		if(initLenght >= newLenght) {
			
			newString = align.cut(newString,initLenght,newLenght);
			
		}else {
			
			newString = align.fill(newString,initLenght,newLenght,addChar);
		}

		return newString;
	}
	
	public enum Align {
		
		LEFT(new MethodAlignLeft()),
		RIGHT(new MethodAlignRigth());
		
		private MethodAlign method;
		
		private Align(MethodAlign method) {
			this.method = method;
		}
		
		public String cut(String string, int initLenght, int newLenght) {
			return method.cut(string, initLenght, newLenght);
		}
		
		public String fill(String string, int initLenght, int newLenght, char addChar) {
			return method.fill(string, initLenght, newLenght, addChar);
		}
	}
	
	private interface MethodAlign {
		
		public String cut(String string, int initLenght, int newLenght);
		
		public String fill(String string, int initLenght, int newLenght, char addChar);
	}
	
	public static final class MethodAlignLeft implements MethodAlign {
		
		@Override
		public String cut(String string, int initLenght, int newLenght) {
			
			return string.substring(0, newLenght);
		}
		
		@Override
		public String fill(String string, int initLenght, int newLenght, char addChar) {
			
			StringBuilder sb = new StringBuilder(string);
			
			for(int i = initLenght; i < newLenght; i++) sb.append(addChar);
			
			return sb.toString();
		}
	}
	
	public static final class MethodAlignRigth implements MethodAlign {
		
		@Override
		public String cut(String string, int initLenght, int newLenght) {
			
			return string.substring(string.length() - newLenght, string.length());
		}
		
		@Override
		public String fill(String string, int initLenght, int newLenght, char addChar) {
			
			StringBuilder sb = new StringBuilder(string);
			
			for(int i = initLenght; i < newLenght; i++) sb.insert(0, addChar);
			
			return sb.toString();
		}
	}
	
}
