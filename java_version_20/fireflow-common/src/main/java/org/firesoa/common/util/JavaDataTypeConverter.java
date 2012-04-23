/**
 * Copyright 2007-2010 非也
 * All rights reserved. 
 * 
 * This library is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License v3 as published by the Free Software
 * Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with this library; if not, see http://www.gnu.org/licenses/lgpl.html.
 *
 */
package org.firesoa.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.firesoa.common.schema.NameSpaces;

/**
 *
 * @author 非也 nychen2000@163.com
 * Fire Workflow 官方网站：www.firesoa.com 或者 www.fireflow.org
 *
 */
public class JavaDataTypeConverter {
//	/**
//	 * 将string 转换成相应的java对象，例如Integer,Float等
//	 * 
//	 * @param valueAsStr
//	 * @param dataType
//	 * @param pattern
//	 * @return
//	 */
//	public static Object string2Object(String valueAsStr, QName qnDataType,
//			String pattern) {
//		String dataType = qnDataType.toString();
//
//		if (dataType == null || dataType.trim().equals("")
//				|| dataType.trim().equals("java:java.lang.String")) {
//			return valueAsStr;
//		}
//		try {
//			if (dataType.trim().equals("java:int")
//					|| dataType.trim().equals("java:java.lang.Integer")) {
//				return new Integer(valueAsStr);
//			} else if (dataType.trim().equals("java:long")
//					|| dataType.trim().equals("java:java.lang.Long")) {
//				return new Long(valueAsStr);
//			} else if (dataType.trim().equals("java:float")
//					|| dataType.trim().equals("java:java.lang.Float")) {
//				return new Float(valueAsStr);
//			} else if (dataType.trim().equals("java:double")
//					|| dataType.trim().equals("java:java.lang.Double")) {
//				return new Double(valueAsStr);
//			} else if (dataType.trim().equals("java:short")
//					|| dataType.trim().equals("java:java.lang.Short")) {
//				return new Short(valueAsStr);
//			} else if (dataType.trim().equals("java:byte")
//					|| dataType.trim().equals("java:java.lang.Byte")) {
//				return new Byte(valueAsStr);
//			} else if (dataType.trim().equals("java:boolean")
//					|| dataType.trim().equals("java:java.lang.Boolean")) {
//				return new Boolean(valueAsStr);
//			}
//			// else if
//			// (dataType.trim().equals("char")||dataType.trim().equals("java.lang.Char")){
//			// return new java.lang.Character(valueAsStr);
//			// }
//			else if (dataType.trim().equals("java:java.util.Date")) {
//				SimpleDateFormat format = new SimpleDateFormat(pattern);
//				Date d = format.parse(valueAsStr);
//				return d;
//			}
//
//			else {
//				return valueAsStr;
//			}
//		} catch (Exception e) {
//			return null;
//		}
//	}

	/**
	 * 判断是否是基本数据类型或者其包装类型
	 * 
	 * @param dataTypeStr
	 * @return
	 */
	private static boolean isPrimaryDataType(String dataTypeStr) {
		if (StringUtils.isEmpty(dataTypeStr)) {
			return false;
		}
		String s = dataTypeStr.trim();

		if (isInt(s) || isFloat(s) || isDouble(s) || isLong(s) || isShort(s)
				|| isChar(s) || isBoolean(s)) {
			return true;
		}

		return false;
	}

	private static boolean isChar(String dataTypeStr) {
		if (StringUtils.isEmpty(dataTypeStr)) {
			return false;
		}
		String s = dataTypeStr.trim();

		if (s.equals("char") || s.equals("java.lang.Character")) {
			return true;
		}

		return false;
	}

	private static boolean isBoolean(String dataTypeStr) {
		if (StringUtils.isEmpty(dataTypeStr)) {
			return false;
		}
		String s = dataTypeStr.trim();

		if (s.equals("boolean") || s.equals("java.lang.Boolean")) {
			return true;
		}

		return false;
	}
	
	private static boolean isDate(String dataTypeStr){
		if (StringUtils.isEmpty(dataTypeStr)) {
			return false;
		}
		String s = dataTypeStr.trim();
		
		if (s.equals("java.util.Date")){
			return true;
		}
		return false;
	}
	
	private static boolean isString(String dataTypeStr){
		if (StringUtils.isEmpty(dataTypeStr)) {
			return false;
		}
		String s = dataTypeStr.trim();
		
		if (s.equals("java.lang.String")){
			return true;
		}
		return false;
	}

	private static boolean isShort(String dataTypeStr) {
		if (StringUtils.isEmpty(dataTypeStr)) {
			return false;
		}
		String s = dataTypeStr.trim();

		if (s.equals("short") || s.equals("java.lang.Short")) {
			return true;
		}

		return false;
	}

	private static boolean isLong(String dataTypeStr) {
		if (StringUtils.isEmpty(dataTypeStr)) {
			return false;
		}
		String s = dataTypeStr.trim();

		if (s.equals("long") || s.equals("java.lang.Long")) {
			return true;
		}

		return false;
	}

	private static boolean isInt(String dataTypeStr) {
		if (StringUtils.isEmpty(dataTypeStr)) {
			return false;
		}
		String s = dataTypeStr.trim();

		if (s.equals("int") || s.equals("java.lang.Integer")) {
			return true;
		}

		return false;
	}

	private static boolean isFloat(String dataTypeStr) {
		if (StringUtils.isEmpty(dataTypeStr)) {
			return false;
		}
		String s = dataTypeStr.trim();

		if (s.equals("float") || s.equals("java.lang.Float")) {
			return true;
		}

		return false;
	}

	private static boolean isDouble(String dataTypeStr) {
		if (StringUtils.isEmpty(dataTypeStr)) {
			return false;
		}
		String s = dataTypeStr.trim();

		if (s.equals("double") || s.equals("java.lang.Double")) {
			return true;
		}

		return false;
	}

	/**
	 * 如果targetDataType是基本数据类型，则进行类型转换。否则检查类型是否匹配。
	 * 
	 * @param targetDataType
	 * @param source
	 * @param dataPattern TODO
	 * @return
	 * @throws ClassCastException
	 */
	public static Object dataTypeConvert(QName targetDataType, Object source, String dataPattern)
			throws ClassNotFoundException, ClassCastException {
		// 类型验证和类型转换
		if (targetDataType.getNamespaceURI().equals(NameSpaces.JAVA.getUri())) {
			String typeClassStr = targetDataType.getLocalPart();

			if (isPrimaryDataType(typeClassStr)) {
				if (isInt(typeClassStr)) {
					return convertToInt(source);
				} else if (isFloat(typeClassStr)) {
					return convertToFloat(source);
				} else if (isDouble(typeClassStr)) {
					return convertToDouble(source);
				} else if (isLong(typeClassStr)) {
					return convertToLong(source);
				} else if (isShort(typeClassStr)) {
					return convertToShort(source);
				} else if (isBoolean(typeClassStr)) {
					return convertToBoolean(source);
				} else if (isChar(typeClassStr)) {
					return convertToChar(source);
				}
				throw new ClassCastException("Can not convert from "
						+ source.getClass().getName() + " to "
						+ typeClassStr);
			}
			else if (isDate(typeClassStr)){
				if (source instanceof Date){
					return (Date)source;
				}
				else if (source instanceof String){
					String _pattern = StringUtils.isEmpty(dataPattern)?"yyyy-MM-dd HH:mm:ss":dataPattern;
					SimpleDateFormat df = new SimpleDateFormat(_pattern);
					try {
						return df.parse((String)source);
					} catch (ParseException e) {
						throw new ClassCastException("Can not convert from "
								+ source.getClass().getName() + " to "
								+ typeClassStr+",data patter is "+dataPattern);
					}
				}else{
					throw new ClassCastException("Can not convert from "
							+ source.getClass().getName() + " to "
							+ typeClassStr);
				}
			}
			else if (isString(typeClassStr)){
				if (source==null){
					return source;
				}else{
					return source.toString();
				}
			}
			else {
				Class typeClass = Class.forName(typeClassStr);
				if (!typeClass.isAssignableFrom(source.getClass())) {
					throw new ClassCastException("Can not convert from "
							+ source.getClass().getName() + " to "
							+ typeClassStr);
				}

				return source;
			}
		} else {
			// 其他数据类型paramValue必须是datacontainer
			// TODO 类型 验证待补充

			return source;
		}
	}

	public static Integer convertToInt(Object object) throws ClassCastException {
		if (object == null)
			return null;
		if (object instanceof Integer) {
			return (Integer) object;
		}
		if (object instanceof Double) {
			return ((Double) object).intValue();
		}
		if (object instanceof Float) {
			return ((Float) object).intValue();
		}
		if (object instanceof Long) {
			return ((Long) object).intValue();
		}
		if (object instanceof Short) {
			return ((Short) object).intValue();
		}
		if (object instanceof String && StringUtils.isNumeric((String)object)){
			return Integer.parseInt((String)object);
		}
		throw new ClassCastException("Can NOT convert from "
				+ object.getClass().toString() + " to java.lang.Integer");
	}

	public static Float convertToFloat(Object object) throws ClassCastException {
		if (object == null)
			return null;
		if (object instanceof Integer) {
			return new Float((Integer) object);
		}
		if (object instanceof Double) {
			return new Float((Double) object);
		}
		if (object instanceof Float) {
			return ((Float) object);
		}
		if (object instanceof Long) {
			return new Float((Long) object);
		}
		if (object instanceof Short) {
			return new Float((Short) object);
		}
		if (object instanceof String && StringUtils.isNumeric((String)object)){
			return Float.parseFloat((String)object);
		}
		throw new ClassCastException("Can NOT convert from "
				+ object.getClass().toString() + " to java.lang.Float");
	}

	public static Double convertToDouble(Object object)
			throws ClassCastException {
		if (object == null)
			return null;
		if (object instanceof Integer) {
			return new Double((Integer) object);
		}
		if (object instanceof Double) {
			return (Double) object;
		}
		if (object instanceof Float) {
			return new Double((Float) object);
		}
		if (object instanceof Long) {
			return new Double((Long) object);
		}
		if (object instanceof Short) {
			return new Double((Short) object);
		}
		if (object instanceof String && StringUtils.isNumeric((String)object)){
			return Double.parseDouble((String)object);
		}
		throw new ClassCastException("Can NOT convert from "
				+ object.getClass().toString() + " to java.lang.Double");
	}

	public static Long convertToLong(Object object) throws ClassCastException {
		if (object == null)
			return null;
		if (object instanceof Integer) {
			return new Long((Integer) object);
		}
		if (object instanceof Double) {
			return ((Double) object).longValue();
		}
		if (object instanceof Float) {
			return ((Float) object).longValue();
		}
		if (object instanceof Long) {
			return (Long) object;
		}
		if (object instanceof Short) {
			return new Long((Short) object);
		}
		if (object instanceof String && StringUtils.isNumeric((String)object)){
			return Long.parseLong((String)object);
		}
		throw new ClassCastException("Can NOT convert from "
				+ object.getClass().toString() + " to java.lang.Long");
	}

	public static Short convertToShort(Object object) throws ClassCastException {
		if (object == null)
			return null;
		if (object instanceof Integer) {
			return ((Integer) object).shortValue();
		}
		if (object instanceof Double) {
			return ((Double) object).shortValue();
		}
		if (object instanceof Float) {
			return ((Float) object).shortValue();
		}
		if (object instanceof Long) {
			return ((Long) object).shortValue();
		}
		if (object instanceof Short) {
			return (Short) object;
		}
		if (object instanceof String && StringUtils.isNumeric((String)object)){
			return Short.parseShort((String)object);
		}
		throw new ClassCastException("Can NOT convert from "
				+ object.getClass().toString() + " to java.lang.Short");
	}

	public static Boolean convertToBoolean(Object object)
			throws ClassCastException {
		if (object == null)
			return null;

		if (object instanceof Boolean) {
			return (Boolean) object;
		}
		if (object instanceof String){
			return Boolean.parseBoolean((String)object);
		}
		throw new ClassCastException("Can NOT convert from "
				+ object.getClass().toString() + " to java.lang.Boolean");
	}

	public static Character convertToChar(Object object)
			throws ClassCastException {
		if (object == null)
			return null;

		if (object instanceof Character) {
			return (Character) object;
		}
		throw new ClassCastException("Can NOT convert from "
				+ object.getClass().toString() + " to java.lang.Character");
	}
	
}
