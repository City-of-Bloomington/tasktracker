package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import org.apache.log4j.Logger;


public class TaskList extends CommonInc{

		static Logger logger = Logger.getLogger(TaskList.class);
		static final long serialVersionUID = 2300L;
		String name = "", id="", limit="limit 30";
		String request_id = "", task_by="", completed="";
		String exclude_task_id = "";
		boolean active_only = false;
		List<Task> tasks = null;
		public TaskList(){
				super();
		}
		public TaskList(boolean deb){
				super(deb);
		}		
		public TaskList(boolean deb, String val){
				super(deb);
				setRequest_id(val);
		}
		public List<Task> getTasks(){
				return tasks;
		}
		public void setRequest_id(String val){
				if(val != null)
						request_id = val;
		}
		public void setId(String val){
				if(val != null)
						id = val;
		}
		public void setTask_by(String val){
				if(val != null)
					 task_by = val;
		}
		public void setExclude_task_id(String val){
				if(val != null)
					 exclude_task_id = val;
		}		
		public String getId(){
				return id;
		}
		public String getRequest_id(){
				return request_id;
		}
		public String getTask_by(){
				return task_by;
		}		
		public String getCompleted(){
				return completed;
		}
		public void setActiveOnly(){
				active_only = true;
		}
		public void setNoLimit(){
				limit = "";
		}
		public String find(){
		
				String back = "";
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				Connection con = Helper.getConnection();
				String qq = "select u.id,u.request_id,u.name,date_format(u.date,'%m/%d/%Y'),u.task_by,u.completed,u.notes,u.hours,u.expenses from tasks u ", qw ="";
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				if(!id.equals("")){
						qw += " u.id = ? ";
				}
				else{
						if(!request_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " u.request_id=? ";
						}
						if(!exclude_task_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " u.id <> ? ";								
						}
						if(!task_by.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " u.task_by=? ";
						}
						if(active_only){
								qw += " u.completed != '100' ";
						}						
						if(!completed.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " u.completed=? ";
						}

				}
				if(!qw.equals("")){
						qq += " where "+qw;
				}
				qq += " order by u.id desc ";
				
				if(!limit.equals("")){
						qq += limit;
				}
				System.err.println(qq);
				try{
						if(debug){
								logger.debug(qq);
						}
						pstmt = con.prepareStatement(qq);
						if(!id.equals("")){
								pstmt.setString(1, id);
						}
						else {
								int jj=1;
								if(!request_id.equals("")){
										pstmt.setString(jj++,request_id);		
								}
								if(!exclude_task_id.equals("")){
										pstmt.setString(jj++,exclude_task_id);		
								}								
								if(!task_by.equals("")){
										pstmt.setString(jj++,task_by);		
								}								
								if(!completed.equals("")){
										pstmt.setString(jj++,completed);		
								}
						}
						rs = pstmt.executeQuery();
						while(rs.next()){
								if(tasks == null)
										tasks = new ArrayList<>();
								Task one =
										new Task(debug,
														 rs.getString(1),
														 rs.getString(2),
														 rs.getString(3),
														 rs.getString(4),
														 rs.getString(5),
														 rs.getString(6),
														 rs.getString(7),
														 rs.getString(8),
														 rs.getString(9));
								tasks.add(one);
						}
				}
				catch(Exception ex){
						back += ex+" : "+qq;
						logger.error(back);
						addError(back);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return back;
		}
}






















































