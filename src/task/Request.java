package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.io.*;
import java.util.List;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;

public class Request extends CommonInc{

		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		static final long serialVersionUID = 1800L;	
		static Logger logger = Logger.getLogger(Request.class);	
    String id="", 
				type_id="", 
				status="", 
				group_id="", 
				location_id="", other_location="", 
				date="", summary="", due_date="",
				time="", description="",employee_id=""; 				
		String assign_user_id=""; // new assignment
		String user_id = ""; // needed for logs
		User user = null;
		Type type=null;
		Group group = null;
		Location location = null;
		List<Task> tasks = null;
		List<Assignment> assignments = null;
		List<RequestLog> logs = null;
		Employee employee = null;
		User assignee = null;  // for a new assignment
    //
    // basic constructor
    //
    public Request(){

    }
		public Request(boolean deb, String val){
				super(deb);
				setId(val);
		}
		public Request(boolean deb,
									 String val,
									 String val2,
									 String val3,
									 String val4,
									 String val5,
									 String val6,
									 String val7,
									 String val8,									 
									 String val9,
									 String val10,
									 String val11,
									 String val12
									 ){
				super(deb);
				setValues(val, val2, val3, val4, val5, val6, val7, val8, val9,
									val10, val11, val12);
		}
		private void setValues(
													 String val,
													 String val2,
													 String val3,
													 String val4,
													 String val5,
													 String val6,
													 String val7,
													 String val8,									 
													 String val9,
													 String val10,
													 String val11,
													 String val12
											){
				setId(val);
				setType_id(val2);
				setStatus(val3);
				setDate(val4);
				setTime(val5);
				/*
				setRequesterEmail(val6);
				setRequesterFullname(val7);
				setRequesterPhone(val8);
				setRequesterDept(val9);
				*/
				setEmployee_id(val6);
				setGroup_id(val7);
				setLocation_id(val8);
				setOther_location(val9);
				setSummary(val10);
				setDescription(val11);
				setDue_date(val12);
		}
    //
    // setters
    //
    public
				void setId(String val){
				if(val != null)
						id = val;
    }
    public
				void setType_id(String val){
				if(val != null && !val.equals("-1"))
						type_id = val;
    }
    public
				void setEmployee_id(String val){
				if(val != null && !val.equals("-1"))
						employee_id = val;
    }		
    public
				void setStatus(String val){
				if(val != null){				
						status = val;
				}
    }
		/*
    public
				void setRequestEmail(String val){ 
				if(val != null)								
						requester_email = val;
    }
    public
				void setRequesterFullname(String val){ 
				if(val != null)								
						requester_fullname = val;
    }		
    public
				void setRequesterPhone(String val){
				if(val != null)				
						requester_phone = val.trim();
    }
		*/
    public
				void setLocation_id(String val){
				if(val != null)				
						location_id = val;
    }
    public
				void setOther_location(String val){
				if(val != null)				
						other_location = val.trim();
    }		
    public
				void setDate(String val){
				if(val != null)				
						date = val;
    }
    public
				void setTime(String val){
				if(val != null)				
						time = val;
    }		
    public
				void setSummary(String val){
				if(val != null)				
						summary = val.trim();
    }
    public
				void setDue_date(String val){
				if(val != null)				
						due_date = val;
    }
    public
				void setDescription(String val){
				if(val != null)				
						description = val.trim();
    }
    public
				void setGroup_id(String val){
				if(val != null && !val.equals("-1"))				
						group_id = val;
    }
    public
				void setAssign_user_id(String val){
				if(val != null && !val.equals("-1"))				
						assign_user_id = val;
    }
    public
				void setUser_id(String val){
				if(val != null && !val.equals("-1"))				
						user_id = val;
    }					
    //
    // getters
    //
    public
				String  getId(){
				return id;
    }
    public
				String  getType_id(){
				return type_id;
    }
    public
				String  getStatus(){
				return status;
    }
		/*
    public
				String  getRequesterPhone(){
				return requester_phone;
    }
		*/
    public
				String  getLocation_id(){
				return location_id;
    }
    public
				String  getOther_location(){
				return other_location;
    }		
    public
				String  getDate(){
				if(id.equals("")){
						date = Helper.getToday();
				}
				return date;
    }
    public
				String  getTime(){
				if(id.equals("")){
						time = Helper.getTimeNow();
				}				
				return time;
    }
		/*
    public				
				String  getRequestEmail(){
				return requester_email;
    }
    public
				String  getRequesterFullname(){
				return requester_fullname;
    }
    public
				String  getRequesterDept(){
				return requester_dept;
    }
    public
				String  getDivision(){
				return division;
    }		
		*/
    public
				String  getSummary(){
				return summary;
    }

    public
				String  getGroup_id(){
				return group_id;
    }		
    public
				String  getDue_date(){
				return due_date;
    }
    public
				String  getDescription(){
				return description;
    }
		public boolean canBeChanged(){
				return !(status.equals("Cancelled") || status.equals("Closed"));

		}
		public Type  getType(){
				if(type == null && !type_id.equals("")){
						Type one = new Type(debug, type_id);
						String back = one.doSelect();
						if(back.equals("")){
								type = one;
						}
				}
				return type;
    }
		public Group getGroup(){
				if(group == null && !group_id.equals("")){
						Group one = new Group(debug, group_id);
						String back = one.doSelect();
						if(back.equals("")){
								group = one;
						}
				}
				return group;
		}
		public Location getLocation(){
				if(location == null && !location_id.equals("")){
						Location one = new Location(debug, location_id);
						String back = one.doSelect();
						if(back.equals("")){
								location = one;
						}
				}
				return location;
		}
		public String getLocationInfo(){
				String ret = "";
				getLocation();
				if(location != null){
						ret = location.getName();
				}
				if(!other_location.equals("")){
						if(!ret.equals("")) ret += " - ";
						ret += other_location;
				}
				return ret;
		}
		public List<Assignment> getAssignments(){
				if(!id.equals("") && assignments == null){
						AssignmentList al = new AssignmentList(debug, id);
						String back = al.find();
						if(back.equals("")){
								List<Assignment> ones = al.getAssignments();
								if(ones  != null && ones.size() > 0){
										assignments = ones;
								}
						}
				}
				return assignments;
		}
		public boolean hasAssignments(){
				if(id.equals("")) return false;
				getAssignments();
				return assignments != null && assignments.size() > 0;
		}
		public List<Task> getTasks(){
				if(!id.equals("") && tasks == null){
						TaskList al = new TaskList(debug, id);
						String back = al.find();
						if(back.equals("")){
								List<Task> ones = al.getTasks();
								if(ones  != null && ones.size() > 0){
										tasks = ones;
								}
						}
				}
				return tasks;
		}
		public boolean isCompleted(){
				if(!canBeChanged()){
						return true;
				}
				getTasks();
				if(tasks != null && tasks.size() > 0){
						for(Task one:tasks){
								if(!one.isCompleted()) return false;
						}
						return true;
				}
				// if no tasks and not cancelled
				return false;
		}
		public boolean hasTasks(){
				if(id.equals("")) return false;
				getTasks();
				return tasks != null && tasks.size() > 0;
		}				
		public List<RequestLog> getLogs(){
				if(!id.equals("") && logs == null){
						RequestLogList al = new RequestLogList(debug, id);
						String back = al.find();
						if(back.equals("")){
								List<RequestLog> ones = al.getLogs();
								if(ones  != null && ones.size() > 0){
										logs = ones;
								}
						}
				}
				return logs;
		}
		
		public boolean hasLogs(){
				if(id.equals("")) return false;
				getLogs();
				return logs != null && logs.size() > 0;
		}
		public Employee getEmployee(){
				if(employee == null){
						if(!employee_id.equals("")){
								Employee one = new Employee(debug, employee_id);
								String back = one.doSelect();
								employee = one;
						}
						else{
								employee = new Employee(debug);
						}
				}
				return employee;
		}
		public boolean hasEmployee(){
				return !employee_id.equals("");
		}
		public User getAssignee(){
				if(assignee == null && !assign_user_id.equals("")){
						User one = new User(debug, assign_user_id);
						String back = one.doSelect();
						assignee = one;
				}
				return assignee;
		}
		public boolean hasNewAssignee(){
				getAssignee();
				return assignee != null;
		}
		public String toString(){
				return summary;
		}
    public String doSave(){
				String msg = "";
				Connection con = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				if(group_id.equals("")){
						msg = " Need to pick a group ";
				}
				if(summary.equals("")){
						msg = " You need to provide a summary of the request";
				}
				getEmployee();
				if(employee.hasData()){
						msg = employee.addEmployee();
						if(msg.equals("")){
								employee_id = employee.getId();
						}
				}
				String qq = "insert into requests values(0,?,?,now(),?, ?,?,?,?,?, ?)";
				if(debug){
						logger.debug(qq);
				}
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to DB";
						addError(msg);
						return msg;
				}			
				try{
						stmt = con.prepareStatement(qq);
						if(assign_user_id.equals(""))
								status = "Unassigned";
						else
								status = "Active";
						msg = setParams(stmt);
						if(msg.equals("")){
								stmt.executeUpdate();
								qq = "select LAST_INSERT_ID() ";
								if(debug){
										logger.debug(qq);
								}
								stmt = con.prepareStatement(qq);				
								rs = stmt.executeQuery();
								if(rs.next()){
										id = rs.getString(1);
								}
						}
				}
				catch(Exception ex){
						msg = ex+": "+qq;
						logger.error(msg);
						addError(msg);
						return msg;
				}
				finally{
						Helper.databaseDisconnect(con, stmt, rs);
				}
				String notes = null;
				if(msg.equals("")){
						if(!assign_user_id.equals("")){
								Assignment asm = new Assignment(debug, assign_user_id, id);
								msg = asm.doSave();
								User tmp_user = new User(debug, assign_user_id);
								msg = tmp_user.doSelect();
								notes = " request assigned to user "+tmp_user.getFullname();
						}
				}				
				if(msg.equals("")){
						RequestLog rl = new RequestLog(debug, null, id, user_id, null, "Created",notes);
						msg = rl.doSave();
						msg += doSelect();
				}
				return msg; 
		}
		private String setParams(PreparedStatement stmt){
				String back = "";
				int jj=1;
				try{
						if(type_id.equals(""))
								stmt.setNull(jj++, Types.INTEGER);
						else
								stmt.setString(jj++, type_id);
						if(id.equals("")){ // we do not change otherwise
								if(status.equals(""))
										stmt.setNull(jj++, Types.INTEGER);
								else
										stmt.setString(jj++, status);
						}
						if(employee_id.equals("")){
								stmt.setNull(jj++, Types.INTEGER);
						}
						else {
								stmt.setString(jj++, employee_id);			
						}															
						if(group_id.equals("")){
								stmt.setNull(jj++, Types.INTEGER);
						}
						else {
								stmt.setString(jj++, group_id);			
						}

						if(location_id.equals("")){
								stmt.setNull(jj++, Types.INTEGER);
						}
						else {
								stmt.setString(jj++, location_id);		
						}
						if(other_location.equals("")){
								stmt.setNull(jj++, Types.VARCHAR);
						}
						else {
								stmt.setString(jj++, other_location);		
						}						
						if(summary.equals("")){
								stmt.setNull(jj++, Types.VARCHAR);
						}
						else {
								stmt.setString(jj++, summary);		
						}
						if(description.equals("")){
								stmt.setNull(jj++, Types.VARCHAR);
								
						}
						else {
								stmt.setString(jj++, description);		
						}
						if(due_date.equals(""))
								stmt.setNull(jj++, Types.VARCHAR);
						else{
								java.util.Date date_tmp = df.parse(due_date);
								stmt.setDate(jj++, new java.sql.Date(date_tmp.getTime()));
						}
				}
				catch(Exception ex){
						logger.error(ex);
						back += ex;
				}
				
				return back;
    }
    //
    public String doUpdate(){
				String msg = "";
				Connection con = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				
				String qq = "update requests set type_id=?,employee_id=?,"+
						"group_id=?, location_id=?,other_location=?,summary=?, "+
						"description=?, due_date=? where id=? ";
				getEmployee();
				if(employee.hasData()){
						msg = employee.addEmployee();
						if(msg.equals("")){
								employee_id = employee.getId();
						}
				}				
				if(debug){
						logger.debug(qq);
				}
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to DB";
						addError(msg);
						return msg;
				}			
				try{
						stmt = con.prepareStatement(qq);
						msg = setParams(stmt);
						if(msg.equals("")){
								stmt.setString(9,id);
								stmt.executeUpdate();
						}
				}
				catch(Exception ex){
						logger.error(ex+":"+qq);
						msg += ex;
				}
				finally{
						Helper.databaseDisconnect(con, stmt, rs);
				}
				String notes = null; // for logs
				if(msg.equals("")){
						if(!assign_user_id.equals("")){
								Assignment asm = new Assignment(debug, assign_user_id, id);
								msg = asm.doSave();
								User tmp_user = new User(debug, assign_user_id);
								msg = tmp_user.doSelect();								
								notes = " new assignment to user  "+tmp_user.getFullname();
						}
				}
				if(msg.equals("")){				
						if(status.equals("Unassigned") && !assign_user_id.equals("")){
								// status = "Active";
								msg = updateStatus("Active");
						}
				}
				if(msg.equals("")){
						RequestLog rl = new RequestLog(debug, null, id, user_id, null, "Updated",notes);
						msg = rl.doSave();
						msg += doSelect();
				}
				return msg; 
    }
    public String updateStatus(String new_status){
				String msg = "";
				Connection con = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				String qq = "update requests set status=? where id=? ";
				if(debug){
						logger.debug(qq);
				}
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to DB";
						addError(msg);
						return msg;
				}			
				try{
						stmt = con.prepareStatement(qq);
						stmt.setString(1, new_status);
						stmt.setString(2,id);
						stmt.executeUpdate();
				}
				catch(Exception ex){
						logger.error(ex+":"+qq);
						msg += ex;
				}
				finally{
						Helper.databaseDisconnect(con, stmt, rs);
				}
				/**
				 * changing status to Active, means it was either Closed or Cancelled
				 */
				if(msg.equals("")){
						String notes = "status changed to "+new_status;
						String type = "Updated";
						if(new_status.equals("Closed")) type ="Closed";
						else if(new_status.equals("Cancelled")) type ="Cancelled";
						else if(new_status.equals("Active") && !status.equals("Unassigned")) type="Reopen";
						status = new_status;
						RequestLog rl = new RequestLog(debug, null, id, user_id, null, type,notes);
						msg = rl.doSave();
				}
				return msg; 
    }		 
    public String doSelect(){
				String msg = "";
				Connection con = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				String qq = "select id,"+
						"type_id,"+
						"status,"+
						"date_format(date,'%m/%d/%Y'), "+   // 23
						"date_format(date,'%h:%i %p'), "+   // time h,l=hour, i=minute
						"employee_id,"+
						"group_id,"+
						"location_id,"+
						"other_location,"+
						"summary,"+
						"description,"+
						"date_format(due_date,'%m/%d/%Y') "+						
						"from requests where id=? ";

				if(debug){
						logger.debug(qq);
				}
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to DB";
						addError(msg);
						return msg;
				}
				try{
						stmt = con.prepareStatement(qq);
						stmt.setString(1, id);
						rs = stmt.executeQuery();
						if(rs.next()){
								setValues(rs.getString(1),
													rs.getString(2),
													rs.getString(3),
													rs.getString(4),
													rs.getString(5),
													rs.getString(6),
													rs.getString(7),
													rs.getString(8),
													rs.getString(9),
													rs.getString(10),
													rs.getString(11),
													rs.getString(12));
								if(!employee_id.equals("")){
										getEmployee();
								}
						}
						else{
								msg = "no match found for "+id;
						}
				}
				catch(Exception ex){
						logger.error(ex+":"+qq);
				msg += ex;
				}
				finally{
						Helper.databaseDisconnect(con, stmt, rs);
				}
				return msg;
		}
    public String doClose(){
				return updateStatus("Closed");
    }
		/**
		 * email is sent when a new assignment is set (on save or update)
		 * @param type, 'New' for new request, or 'Update' for updated with a new group for example
		 */
		public String checkEmailSendNeed(String type, User user, String url){
				// this new assignee (if any)
				String back = "";
				String mail_from = user.getEmail();
				String mail_to = null;
				String mail_subject = "TaskTracker ";
				String mail_msg = "";
				String mail_cc = null;
				String mail_status = "Success";
				if(type.equals("New")){
						mail_subject += " new request ";
				}
				else{
						mail_subject += " request ";
				}
				if(!summary.equals("")){
						if(summary.length() > 30){
								mail_subject += summary.substring(0,30); 
						}
						else{
								mail_subject += summary;
						}
				}
				mail_msg = summary;
				getAssignee();
				if(assignee != null){
						//
						// send an email to assignee to inform him about the request
						//
						mail_to = assignee.getEmail();
						mail_msg += " this request has been assigned to you. ";
						mail_msg += "<a href=\""+url+"Login?id="+id+"\"> Click here to login </a>";
						
						MsgMail mail = new MsgMail(mail_to,
																			 mail_from,
																			 mail_subject,
																			 mail_msg,
																			 debug);
						back = mail.doSend();
						if(!back.equals("")){
								mail_status = "Failure";
						}
						MailLog mlog = new MailLog(debug, id, mail_to, mail_from, mail_cc, mail_subject, mail_msg, mail_status, back);
						back += mlog.doSave();
				}
				//
				// inform the group manager if it is new request
				//
				if(type.equals("New")){
						getGroup();
						if(group != null){
								User gman = group.getOneGroupManager();
								// inform group manager about the new request
								mail_msg = summary;
								if(assignee != null){
										
										mail_msg += " this request has been assigned to "+assignee.getFullname();
								}
								mail_to = gman.getEmail();
								MsgMail mail = new MsgMail(mail_to,
																					 mail_from,
																					 mail_subject,
																					 mail_msg,
																					 debug);
								String back2 = mail.doSend();
								if(!back2.equals("")){
										mail_status = "Failure";
										if(!back.equals("")) back +=", "; 
										back += back2;
								}
								MailLog mlog = new MailLog(debug, id, mail_to, mail_from, mail_cc, mail_subject, mail_msg, mail_status, back2);
								back = mlog.doSave();
						}
				}
				return back;
		}
}






















































