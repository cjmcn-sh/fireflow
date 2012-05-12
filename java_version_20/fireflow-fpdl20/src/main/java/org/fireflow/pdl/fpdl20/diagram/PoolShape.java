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
package org.fireflow.pdl.fpdl20.diagram;

import java.util.List;

/**
 *
 * @author 非也 nychen2000@163.com
 * Fire Workflow 官方网站：www.firesoa.com 或者 www.fireflow.org
 *
 */
public interface PoolShape extends DiagramElement {
	/**
	 * 是否是抽象Pool
	 * （该属性无意义，2012-05-10）
	 */
//	public boolean isAbstract();
//	public void setAbstract(boolean b);
	
	/**
	 * 获得所有的泳道
	 * @return
	 */
	public List<LaneShape> getLanes();
	
	/**
	 * 增加泳道
	 * @param ln
	 */
	public void addLane(LaneShape ln);

}
