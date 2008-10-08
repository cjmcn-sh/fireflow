/**
 * Copyright 2007-2008 陈乜云（非也,Chen Nieyun）
 * All rights reserved. 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation。
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses. *
 */
package org.fireflow.engine;

import org.fireflow.kenel.KenelException;

/**
 * @author chennieyun
 * 
 */
public interface IFireflowSession {
	public void setRuntimeContext(RuntimeContext ctx);
        
        public RuntimeContext getRuntimeContext();
	
	public void begin(Object dbsession);
	public void end();

	public Object execute(IFireflowSessionCallback callbak)
			throws EngineException, KenelException;


	public IProcessInstance createProcessInstance(String workflowProcessName)
			throws EngineException,KenelException;
	
	public IWorkItem findWorkItemById(String id);
	
	public ITaskInstance findTaskInstanceById(String id);
}
