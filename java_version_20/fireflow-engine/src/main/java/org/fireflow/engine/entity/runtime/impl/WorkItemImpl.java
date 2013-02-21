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
import org.fireflow.engine.entity.runtime.WorkItem;
import org.fireflow.engine.exception.EngineException;
import org.fireflow.engine.exception.InvalidOperationException;
import org.fireflow.pvm.kernel.KernelException;

/**
 * WorkItem generated by hbm2java
 */
@SuppressWarnings("serial")
@XmlRootElement(name="workItem")
@XmlType(name="workItemType")
@XmlAccessorType(XmlAccessType.FIELD)
public class WorkItemImpl extends AbsWorkItem implements WorkItem, java.io.Serializable {

	/* (non-Javadoc)
	 * @see org.fireflow.engine.entity.runtime.WorkItem#claim()
	 */
//	public WorkItem claim(WorkflowSession session) throws InvalidOperationException {
//		WorkflowStatement stmt = session.createWorkflowStatement();
//		return stmt.claimWorkItem(this.getId());
//	}

	/* (non-Javadoc)
	 * @see org.fireflow.engine.entity.runtime.WorkItem#complete()
	 */
//	public void complete(WorkflowSession session) throws InvalidOperationException {
//		WorkflowStatement stmt = session.createWorkflowStatement();
//		stmt.completeWorkItem(this.getId());
//		
//	}

	/* (non-Javadoc)
	 * @see org.fireflow.engine.entity.runtime.WorkItem#complete(java.lang.String)
	 */
//	public void complete(WorkflowSession session,String comments) throws InvalidOperationException {
//		WorkflowStatement stmt = session.createWorkflowStatement();
//		stmt.completeWorkItem(this.getId(),comments);
//		
//	}

	/* (non-Javadoc)
	 * @see org.fireflow.engine.entity.runtime.WorkItem#disclaim(java.lang.String)
	 */
//	public void disclaim(WorkflowSession session,String note) throws InvalidOperationException {
//		WorkflowStatement stmt = session.createWorkflowStatement();
//		stmt.disclaimWorkItem(this.getId());
//		
//	}


	/* (non-Javadoc)
	 * @see org.fireflow.engine.entity.runtime.WorkItem#jumpTo(java.lang.String)
	 */
//	public void jumpTo(WorkflowSession session,String targetActivityId) throws InvalidOperationException {
//		WorkflowStatement stmt = session.createWorkflowStatement();
//		stmt.completeWorkItemAndJumpTo(this.getId(), targetActivityId);
//		
//	}

	/* (non-Javadoc)
	 * @see org.fireflow.engine.entity.runtime.WorkItem#jumpTo(java.lang.String, java.lang.String)
	 */
	public void jumpTo(WorkflowSession session,String targetActivityId, String comments)
			throws InvalidOperationException{
		//TODO 待补充
//		WorkflowStatement stmt = session.createWorkflowStatement();
//		stmt.completeWorkItemAndJumpTo(this.getId(), targetActivityId,comments);
		
	}

	/* (non-Javadoc)
	 * @see org.fireflow.engine.entity.runtime.WorkItem#reassignTo(java.lang.String)
	 */
	public WorkItem reassignTo(WorkflowSession session,String actorId) throws EngineException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.fireflow.engine.entity.runtime.WorkItem#reassignTo(java.lang.String, java.lang.String)
	 */
	public WorkItem reassignTo(WorkflowSession session,String actorId, String comments)
			throws EngineException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.fireflow.engine.entity.runtime.WorkItem#reject()
	 */
	public void reject(WorkflowSession session) throws EngineException, KernelException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.fireflow.engine.entity.runtime.WorkItem#reject(java.lang.String)
	 */
	public void reject(WorkflowSession session,String comments) throws EngineException, KernelException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.fireflow.engine.entity.runtime.WorkItem#withdraw()
	 */
	public WorkItem withdraw(WorkflowSession session) throws EngineException, KernelException {
		// TODO Auto-generated method stub
		return null;
	}

}
