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
package org.fireflow.misc;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author 非也 nychen2000@163.com
 * Fire Workflow 官方网站：www.firesoa.com 或者 www.fireflow.org
 *
 */
public class ObjectListXmlAdapter extends XmlAdapter<ObjectWrapper[], Object[]> {

	/* (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
	@Override
	public Object[] unmarshal(ObjectWrapper[] v) throws Exception {
		if (v==null)return null;
		Object[] result = new Object[v.length];
		for (int i=0;i<v.length;i++){
			ObjectWrapper wrapper = v[i];
			result[i] = wrapper.getOriginalValue();
			
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public ObjectWrapper[] marshal(Object[] v) throws Exception {
		if (v==null){
			return null;
		}
		ObjectWrapper[] result = new ObjectWrapper[v.length];
		for (int i=0;i<v.length;i++){
			Object obj = v[i];
			ObjectWrapper wrapper = new ObjectWrapper();
			wrapper.setOriginalValue(obj);
			result[i] = wrapper;
		}
		return result;
	}

}
