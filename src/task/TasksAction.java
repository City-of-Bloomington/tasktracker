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

public class TasksAction extends TopAction{

		static final long serialVersionUID = 2210L;	
		static Logger logger = Logger.getLogger(TasksAction.class);
		//
		String request_id = "";
		Task task = null;
		List<Task> tasks = null;
		String tasksTitle = "Current Active Actions";
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
				getTasks();
				return ret;
		}

		public String getTasksTitle(){
				return tasksTitle;
		}
		public void setAction2(String val){
				if(val != null && !val.equals(""))		
						action = val;
		}
		public List<Task> getTasks(){
				if(tasks == null){
						TaskList tl = new TaskList(debug);
						tl.setActiveOnly();
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





































