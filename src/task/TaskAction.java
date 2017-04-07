package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;  
import org.apache.log4j.Logger;

public class TaskAction extends TopAction{

		static final long serialVersionUID = 2200L;	
		static Logger logger = Logger.getLogger(TaskAction.class);
		//
		String request_id = "";
		Task task = null;
		List<Task> tasks = null;
		String tasksTitle = "Current Tasks";
		public String execute(){
				String ret = SUCCESS;
				String back = doPrepare();
				if(!back.equals("")){
						try{
								HttpServletResponse res = ServletActionContext.getResponse();
								String str = url+"Login";
								res.sendRedirect(str);
								return super.execute();
						}catch(Exception ex){
								System.err.println(ex);
						}	
				}
				System.err.println(" action "+action);
				if(action.equals("Save")){
						task.setTask_by(user.getId());
						back = task.doSave();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Added Successfully");
								if(task.isCompleted()){
										try{
												HttpServletResponse res = ServletActionContext.getResponse();
												String str = url+"request.action?id="+task.getRequest_id();
												res.sendRedirect(str);
												return super.execute();
										}catch(Exception ex){
												System.err.println(ex);
										}											
								}
						}
				}				
				else if(action.startsWith("Save")){
						task.setTask_by(user.getId());
						back = task.doUpdate();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Updated Successfully");
								if(task.isCompleted()){
										try{
												HttpServletResponse res = ServletActionContext.getResponse();
												String str = url+"request.action?id="+task.getRequest_id();
												res.sendRedirect(str);
												return super.execute();
										}catch(Exception ex){
												System.err.println(ex);
										}	
								}
						}
				}
				else if(action.startsWith("Completed")){
						task.setTask_by(user.getId());
						task.setPercent("100");
						back = task.doUpdate();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Updated Successfully");
								try{
										HttpServletResponse res = ServletActionContext.getResponse();
										String str = url+"request.action?id="+task.getRequest_id();
										res.sendRedirect(str);
										return super.execute();
								}catch(Exception ex){
										System.err.println(ex);
								}	
						}
				}				
				else{		
						getTask();
						if(!id.equals("")){
								back = task.doSelect();
								if(!back.equals("")){
										addActionError(back);
								}								
						}
				}
				return ret;
		}
		public Task getTask(){ 
				if(task == null){
						task = new Task(debug);
						if(!request_id.equals("")){
								task.setRequest_id(request_id);
						}
						task.setId(id);
				}		
				return task;
		}

		public void setTask(Task val){
				if(val != null){
						task = val;
				}
		}

		public String getTasksTitle(){
				return tasksTitle;
		}
		public void setAction2(String val){
				if(val != null && !val.equals(""))		
						action = val;
		}
		public void setRequest_id(String val){
				if(val != null && !val.equals(""))		
						request_id = val;
		}		
		public List<Task> getTasks(){
				if(tasks == null){
						TaskList tl = new TaskList(debug);
						if(request_id.equals("") && task != null){
								request_id = task.getRequest_id();
								id = task.getId();
						}
						if(!id.equals("")){
								tl.setExclude_task_id(id);
						}
						if(!request_id.equals("")){
								tl.setRequest_id(request_id);
						}
						else{
								tl.setActiveOnly();
						}
						String back = tl.find();
						if(back.equals("")){
								List<Task> ones = tl.getTasks();
								if(ones != null && ones.size() > 0){
										tasks = ones;
								}
						}
				}
				return tasks;
		}

}





































