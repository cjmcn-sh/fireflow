/**
 * Copyright 2007-2010 非也
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
package org.fireflow.pdl.fpdl20.behavior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fireflow.engine.WorkflowSession;
import org.fireflow.engine.context.RuntimeContext;
import org.fireflow.engine.entity.runtime.ActivityInstance;
import org.fireflow.engine.entity.runtime.ActivityInstanceState;
import org.fireflow.engine.entity.runtime.ProcessInstance;
import org.fireflow.engine.entity.runtime.ScheduleJob;
import org.fireflow.engine.entity.runtime.ScheduleJobState;
import org.fireflow.engine.entity.runtime.impl.ActivityInstanceImpl;
import org.fireflow.engine.entity.runtime.impl.ScheduleJobImpl;
import org.fireflow.engine.impl.WorkflowSessionLocalImpl;
import org.fireflow.engine.modules.calendar.CalendarService;
import org.fireflow.engine.modules.instancemanager.ActivityInstanceManager;
import org.fireflow.engine.modules.persistence.ActivityInstancePersister;
import org.fireflow.engine.modules.persistence.PersistenceService;
import org.fireflow.engine.modules.persistence.ProcessInstancePersister;
import org.fireflow.engine.modules.persistence.ScheduleJobPersister;
import org.fireflow.engine.modules.persistence.TokenPersister;
import org.fireflow.engine.modules.schedule.Scheduler;
import org.fireflow.pdl.fpdl20.behavior.router.JoinEvaluator;
import org.fireflow.pdl.fpdl20.behavior.router.SplitEvaluator;
import org.fireflow.pdl.fpdl20.misc.FpdlConstants;
import org.fireflow.pdl.fpdl20.process.Synchronizer;
import org.fireflow.pvm.kernel.PObjectKey;
import org.fireflow.pvm.kernel.Token;
import org.fireflow.pvm.kernel.TokenState;
import org.fireflow.pvm.pdllogic.BusinessStatus;
import org.fireflow.pvm.pdllogic.ContinueDirection;
import org.fireflow.pvm.pdllogic.ExecuteResult;

/**
 * @author 非也
 * @version 2.0
 */
public abstract class AbsSynchronizerBehavior extends AbsNodeBehavior{
	//（2012-02-05，Cancel动作容易和handleTermination混淆，意义也不是特别大，暂且注销）
//	protected CancellationHandler cancellationHandler = new SynchronizerCancellationHandler();
	

	/* (non-Javadoc)
	 * @see org.fireflow.pvm.pdllogic.WorkflowBehavior#execute(org.fireflow.engine.WorkflowSession, org.fireflow.pvm.kernel.Token, java.lang.Object)
	 */
	public ExecuteResult execute(WorkflowSession session, Token token,
			Object workflowElement) {
	
		ExecuteResult result = new ExecuteResult();
		result.setStatus(BusinessStatus.COMPLETED);
		return result;

	}
	
	
	public abstract Boolean canBeFired(WorkflowSession session, Token token,
			Synchronizer synchronizer);
	
	/* (non-Javadoc)
	 * @see org.fireflow.pvm.pdllogic.WorkflowBehavior#prepare(org.fireflow.engine.WorkflowSession, org.fireflow.pvm.kernel.Token, java.lang.Object)
	 */
	public Boolean prepare(WorkflowSession session, Token token,
			Object workflowElement) {
		Synchronizer node = (Synchronizer)workflowElement;
		RuntimeContext ctx = ((WorkflowSessionLocalImpl) session)
		.getRuntimeContext();
		PersistenceService persistenceStrategy = ctx.getEngineModule(
				PersistenceService.class, FpdlConstants.PROCESS_TYPE);
		TokenPersister tokenPersister = persistenceStrategy.getTokenPersister();

		boolean multiEnteringTransitions = false;// 表示是否有多条输入边
		if (node.getEnteringTransitions() != null
				&& node.getEnteringTransitions().size() > 1) {
			multiEnteringTransitions = true;
		}
		
		//1、缺省采用AndJoinEvaluator判断
		Boolean canBeFired = this.canBeFired(session, token, node);

		// 2、如果汇聚完成，则创建对应的ActivityInstance
		if (canBeFired) {
			int stepNumber = token.getStepNumber();
			List<Token> siblings = null;
			if (multiEnteringTransitions) {
				siblings = tokenPersister.findSiblings(token);
				if (siblings != null) {
					for (Token sibling : siblings) {
						if (sibling.getStepNumber() > stepNumber) {
							stepNumber = sibling.getStepNumber();
						}
					}
				}
			}

			ActivityInstanceManager activityInstanceMgr = ctx.getEngineModule(
					ActivityInstanceManager.class, FpdlConstants.PROCESS_TYPE);
			ActivityInstancePersister actInstPersistSvc = persistenceStrategy
					.getActivityInstancePersister();
			ProcessInstance processInstance = session
					.getCurrentProcessInstance();
			// 1、创建并保存活动实例
			ActivityInstanceImpl activityInstance = (ActivityInstanceImpl) activityInstanceMgr
					.createActivityInstance(session, processInstance,
							node);

			activityInstance.setStepNumber(stepNumber);
			activityInstance.setTokenId(token.getId());
			actInstPersistSvc.saveOrUpdate(activityInstance);

			// 2、设置session和token
			((WorkflowSessionLocalImpl) session)
					.setCurrentActivityInstance(activityInstance);
			token.setElementInstanceId(activityInstance.getId());
			tokenPersister.saveOrUpdate(token);

			if (siblings != null && siblings.size() > 0) {
				for (Token sibling : siblings) {
					if (!sibling.getId().equals(token.getId())) {
						sibling.setElementInstanceId(activityInstance.getId());
						sibling.setState(TokenState.COMPLETED);
						tokenPersister.saveOrUpdate(sibling);
					}
				}
			}
		}
		
		return canBeFired;
	}
	
	
	/* (non-Javadoc)
	 * @see org.fireflow.pvm.pdllogic.WorkflowBehavior#continueOn(org.fireflow.engine.WorkflowSession, org.fireflow.pvm.kernel.Token, java.lang.Object)
	 */
	public ContinueDirection continueOn(WorkflowSession session, Token token,
			Object workflowElement) {
		//检验currentActivityInstance和currentProcessInstance的一致性
		ProcessInstance oldProcInst = session.getCurrentProcessInstance();
		ActivityInstance oldActInst = session.getCurrentActivityInstance();
		
		RuntimeContext ctx = ((WorkflowSessionLocalImpl)session).getRuntimeContext();
		PersistenceService persistenceStrategy = ctx.getEngineModule(PersistenceService.class, FpdlConstants.PROCESS_TYPE);
		ActivityInstancePersister actInstPersistenceService = persistenceStrategy.getActivityInstancePersister();
		ProcessInstancePersister processInstancePersister = persistenceStrategy.getProcessInstancePersister();
		
		if (oldProcInst==null || !oldProcInst.getId().equals(token.getProcessInstanceId())){
			ProcessInstance procInst = processInstancePersister.find(ProcessInstance.class, token.getProcessInstanceId());
			((WorkflowSessionLocalImpl)session).setCurrentProcessInstance(procInst);
		}
		if (oldActInst==null || !oldActInst.getId().equals(token.getElementInstanceId())){
			ActivityInstance actInst = actInstPersistenceService.find(ActivityInstance.class, token.getElementInstanceId());
			((WorkflowSessionLocalImpl)session).setCurrentActivityInstance(actInst);
		}

		
		if (token.getState().getValue()<TokenState.DELIMITER.getValue() && 
				token.getState().getValue()!=TokenState.RUNNING.getValue()){
			return ContinueDirection.closeMe();
		}

		List<PObjectKey> nextPObjectKeys = determineNextPObjectKeys(session,token,workflowElement);
		
		ContinueDirection direction = ContinueDirection.closeMe();
		if (nextPObjectKeys.size()>0){
			direction.setNextProcessObjectKeys(nextPObjectKeys);
		}
		
		((WorkflowSessionLocalImpl)session).setCurrentProcessInstance(oldProcInst);
		((WorkflowSessionLocalImpl)session).setCurrentActivityInstance(oldActInst);
		return direction;
	}
	
	//（2012-02-05，Cancel动作容易和handleTermination混淆，意义也不是特别大，暂且注销）
//	public CancellationHandler getCancellationHandler(){
//		return this.cancellationHandler;
//	}
	
	/* (non-Javadoc)
	 * @see org.fireflow.pvm.pdllogic.WorkflowBehavior#onTokenStateChanged(org.fireflow.engine.WorkflowSession, org.fireflow.pvm.kernel.Token, java.lang.Object)
	 */
	public void onTokenStateChanged(WorkflowSession session, Token token,
			Object workflowElement) {
		
		RuntimeContext ctx = ((WorkflowSessionLocalImpl)session).getRuntimeContext();
		PersistenceService persistenceStrategy = ctx.getEngineModule(PersistenceService.class, FpdlConstants.PROCESS_TYPE);
		ActivityInstancePersister actInstPersistenceService = persistenceStrategy.getActivityInstancePersister();
	
		CalendarService calendarService = ctx.getEngineModule(CalendarService.class,  FpdlConstants.PROCESS_TYPE);
		
		ActivityInstance oldActInst = session.getCurrentActivityInstance();
		ActivityInstance activityInstance = oldActInst;
		if (oldActInst==null || !oldActInst.getId().equals(token.getElementInstanceId())){
			activityInstance = actInstPersistenceService.find(ActivityInstance.class, token.getElementInstanceId());
			((WorkflowSessionLocalImpl)session).setCurrentActivityInstance(activityInstance);
		}
		ActivityInstanceManager activityInstanceMgr = ctx.getEngineModule(ActivityInstanceManager.class, token.getProcessType());
		try{
			ActivityInstanceState state = ActivityInstanceState.valueOf(token.getState().name());

			if (state.getValue()>ActivityInstanceState.DELIMITER.getValue()){
				
				//将调度器中的timmer删除	，同时将ScheduleJob的状态修改为非活动状态。
				ScheduleJobPersister scheduleJobPersister = persistenceStrategy.getScheduleJobPersister();
				List<ScheduleJob> scheduleJobs = scheduleJobPersister.findScheduleJob4ActivityInstance(activityInstance.getId());
				if (scheduleJobs!=null && scheduleJobs.size()>0){
					for (ScheduleJob job : scheduleJobs){
						if (job.getState().getValue()<ScheduleJobState.DELIMITER.getValue()){
							Scheduler scheduler = ctx.getEngineModule(Scheduler.class, activityInstance.getProcessType());
							scheduler.unSchedule(job, ctx);
							ScheduleJobState scheduleJobState = ScheduleJobState.COMPLETED;
							try{
								scheduleJobState = ScheduleJobState.valueOf(state.getValue());
							}catch(Exception e){
								scheduleJobState = ScheduleJobState.ABORTED;
							}
							((ScheduleJobImpl)job).setState(scheduleJobState);
							scheduleJobPersister.saveOrUpdate(job);
						}
					}
				}
			}
			
			//修改ActivityInstance的状态，在此处调用ActivityInstanceManager.changeActivityInstanceState(...)完成
			activityInstanceMgr.changeActivityInstanceState(session, activityInstance, state, workflowElement);
		}finally{
			((WorkflowSessionLocalImpl)session).setCurrentActivityInstance(oldActInst);
		}
	}
	
	/*
	//2012-02-05 abort方法没有意义
	public void abort(WorkflowSession session,Token token,Object workflowElement){
		//1、检验currentActivityInstance和currentProcessInstance的一致性
		ProcessInstance oldProcInst = session.getCurrentProcessInstance();
		ActivityInstance oldActInst = session.getCurrentActivityInstance();

		RuntimeContext ctx = ((WorkflowSessionLocalImpl)session).getRuntimeContext();
		PersistenceService persistenceService = ctx.getEngineModule(PersistenceService.class, FpdlConstants.PROCESS_TYPE);
		ActivityInstancePersister actInstPersistenceService = persistenceService.getActivityInstancePersister();
		ProcessInstancePersister processInstancePersister = persistenceService.getProcessInstancePersister();
		
		if (oldProcInst==null || !oldProcInst.getId().equals(token.getProcessInstanceId())){
			ProcessInstance procInst = processInstancePersister.find(ProcessInstance.class, token.getProcessInstanceId());
			((WorkflowSessionLocalImpl)session).setCurrentProcessInstance(procInst);
		}
		if (oldActInst==null || !oldActInst.getId().equals(token.getElementInstanceId())){
			ActivityInstance actInst = actInstPersistenceService.find(ActivityInstance.class, token.getElementInstanceId());
			((WorkflowSessionLocalImpl)session).setCurrentActivityInstance(actInst);
		}
		
		try{
			ActivityInstance thisActInst = session.getCurrentActivityInstance();

			//将调度器中的timmer删除		
			ScheduleJobPersister scheduleJobPersister = persistenceService.getScheduleJobPersister();
			List<ScheduleJob> scheduleJobs = scheduleJobPersister.findScheduleJob4ActivityInstance(thisActInst.getId());
			if (scheduleJobs!=null && scheduleJobs.size()>0){
				for (ScheduleJob job : scheduleJobs){
					if (job.getState().getValue()<ScheduleJobState.DELIMITER.getValue()){
						Scheduler scheduler = ctx.getEngineModule(Scheduler.class, thisActInst.getProcessType());
						scheduler.unSchedule(job, ctx);
						
						((ScheduleJobImpl)job).setState(ScheduleJobState.ABORTED);
						scheduleJobPersister.saveOrUpdate(job);
					}
				}
			}
		}finally{
			((WorkflowSessionLocalImpl)session).setCurrentProcessInstance(oldProcInst);
			((WorkflowSessionLocalImpl)session).setCurrentActivityInstance(oldActInst);
		}
	}*/
}
