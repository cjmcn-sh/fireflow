/**
 * Copyright 2007-2008 非也
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
package org.fireflow.engine.entity.runtime.impl;

// Generated Feb 23, 2008 12:04:21 AM by Hibernate Tools 3.2.0.b9
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.fireflow.client.WorkflowSession;
import org.fireflow.engine.entity.runtime.ProcessInstance;
import org.fireflow.engine.exception.EngineException;

/**
 * ProcessInstance generated by hbm2java
 */
@SuppressWarnings("serial")
@XmlRootElement(name="processInstance")
@XmlType(name="processInstanceType")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcessInstanceImpl extends AbsProcessInstance implements ProcessInstance, java.io.Serializable {





    /* (non-Javadoc)
     * @see org.fireflow.engine.IProcessInstance#getProcessInstanceVariables()
     */
//    public Map<String ,Object> getProcessInstanceVariables() {
//		IPersistenceService persistenceService = this.rtCtx.getPersistenceService();
//    	if (processInstanceVariables==null){
//    		//通过数据库查询进行初始化
//    		List<ProcessInstanceVar> allVars = persistenceService.findProcessInstanceVariable(this.getId());
//    		processInstanceVariables = new HashMap<String ,Object>();
//    		if (allVars!=null && allVars.size()!=0){
//    			for (ProcessInstanceVar theVar :allVars){
//    				processInstanceVariables.put(theVar.getVarPrimaryKey().getName(), theVar.getValue());
//    			}
//    		}
//    	}    	    	
//        return processInstanceVariables;
//    	return null;
//    }

//    public void setProcessInstanceVariables(Map<String ,Object> vars) {
//        processInstanceVariables = vars;
//        processInstanceVariables.putAll(vars);
//    }

    /* (non-Javadoc)
     * @see org.fireflow.engine.IProcessInstance#getProcessInstanceVariable(java.lang.String)
     */
//    public Object getProcessInstanceVariable(String name) {
//		IPersistenceService persistenceService = this.rtCtx.getPersistenceService();
//    	if (processInstanceVariables==null){
//    		//通过数据库查询进行初始化
//    		List<ProcessInstanceVar> allVars = persistenceService.findProcessInstanceVariable(this.getId());
//    		processInstanceVariables = new HashMap<String ,Object>();
//    		if (allVars!=null && allVars.size()!=0){
//    			for (ProcessInstanceVar theVar :allVars){
//    				processInstanceVariables.put(theVar.getVarPrimaryKey().getName(), theVar.getValue());
//    			}
//    		}
//    	}    	
//        return processInstanceVariables.get(name);
//    	return null;
//    }

    /* (non-Javadoc)
     * @see org.fireflow.engine.IProcessInstance#setProcessInstanceVariable(java.lang.String, java.lang.Object)
     */
//    public void setProcessInstanceVariable(String name, Object value) {
//		IPersistenceService persistenceService = this.rtCtx.getPersistenceService();
//    	if (processInstanceVariables==null){
//    		//通过数据库查询进行初始化
//    		List<ProcessInstanceVar> allVars = persistenceService.findProcessInstanceVariable(this.getId());
//    		processInstanceVariables = new HashMap<String ,Object>();
//    		if (allVars!=null && allVars.size()!=0){
//    			for (ProcessInstanceVar theVar :allVars){
//    				processInstanceVariables.put(theVar.getVarPrimaryKey().getName(), theVar.getValue());
//    			}
//    		}
//    	}
//    	ProcessInstanceVar procInstVar = new ProcessInstanceVar();
//    	ProcessInstanceVarPk pk = new ProcessInstanceVarPk();
//    	pk.setProcessInstanceId(this.getId());
//    	pk.setName(name);
//    	procInstVar.setVarPrimaryKey(pk);
//    	procInstVar.setValue(value);
//    	procInstVar.setValueType(value.getClass().getName());
//    	
//    	if (processInstanceVariables.containsKey(name)){
//    		persistenceService.updateProcessInstanceVariable(procInstVar);
//    	}else{
//    		persistenceService.saveProcessInstanceVariable(procInstVar);
//    	}
//        processInstanceVariables.put(name, value);
//    }

    /* (non-Javadoc)
     * @see org.fireflow.engine.IProcessInstance#getWorkflowProcess()
     */
//    public Object getWorkflowProcess() throws EngineException {
//        ProcessRepositoryImpl workflowDef = rtCtx.getDefinitionService().getWorkflowDefinitionByProcessIdAndVersionNumber(this.getProcessId(), this.getVersion());
//        WorkflowProcessImpl workflowProcess = null;
//
//        workflowProcess = workflowDef.getWorkflowProcess();
//
//        return workflowProcess;
//    	return null;
//    }



    /* (non-Javadoc)
     * @see org.fireflow.engine.IProcessInstance#abort()
     */
    public void abort() throws EngineException {
//        if (this.state.intValue() == org.fireflow.engine.entity.instance.ProcessInstanceImpl || this.state.intValue() == org.fireflow.engine.entity.instance.ProcessInstanceImpl) {
//            throw new EngineException(this, this.getWorkflowProcess(), "The process instance can not be aborted,the state of this process instance is " + this.getState());
//        }
//        IPersistenceService persistenceService = rtCtx.getPersistenceService();
//        persistenceService.abortProcessInstance(this);
    }



    /* (non-Javadoc)
     * @see org.fireflow.engine.IProcessInstance#suspend()
     */
    public void suspend() throws EngineException {
//        if (this.state == org.fireflow.engine.entity.instance.ProcessInstanceImpl || this.state == org.fireflow.engine.entity.instance.ProcessInstanceImpl) {
//            throw new EngineException(this, this.getWorkflowProcess(), "The process instance can not be suspended,the state of this process instance is " + this.state);
//        }
//        if (this.isSuspended()) {
//            return;
//        }
//        IPersistenceService persistenceService = this.rtCtx.getPersistenceService();
//        persistenceService.suspendProcessInstance(this);
    }

    /* (non-Javadoc)
     * @see org.fireflow.engine.IProcessInstance#restore()
     */
    public void restore() throws EngineException {
//        if (this.state == org.fireflow.engine.entity.instance.ProcessInstanceImpl || this.state == org.fireflow.engine.entity.instance.ProcessInstanceImpl) {
//            throw new EngineException(this, this.getWorkflowProcess(), "The process instance can not be restored,the state of this process instance is " + this.state);
//        }
//        if (!this.isSuspended()) {
//            return;
//        }
//
//        IPersistenceService persistenceService = this.rtCtx.getPersistenceService();
//        persistenceService.restoreProcessInstance(this);

    }

	/* (non-Javadoc)
	 * @see org.fireflow.engine.entity.runtime.ProcessInstance#abort(org.fireflow.engine.WorkflowSession)
	 */
	public void abort(WorkflowSession session) throws EngineException {
		// TODO Auto-generated method stub
		
	}




	/* (non-Javadoc)
	 * @see org.fireflow.engine.entity.runtime.ProcessInstance#restore(org.fireflow.engine.WorkflowSession)
	 */
	public void restore(WorkflowSession session) throws EngineException {
		// TODO Auto-generated method stub
		
	}



	/* (non-Javadoc)
	 * @see org.fireflow.engine.entity.runtime.ProcessInstance#suspend(org.fireflow.engine.WorkflowSession)
	 */
	public void suspend(WorkflowSession session) throws EngineException {
		// TODO Auto-generated method stub
		
	}
}
