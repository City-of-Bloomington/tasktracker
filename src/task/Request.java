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
				location="", 
				date="", summary="", due_date="",
				time="", description=""; 				
		// employee info from ldap
		String username="",
				fullname="", 
				phone="",
				dept="", // we merged dept and division
				division="";
		String assign_user_id=""; // adding new assignment
		String user_id = ""; // needed for logs
		Type type=null;
		Group group = null;
		List<Task> tasks = null;
		List<Assignment> assignments = null;
		List<RequestLog> logs = null;
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
									 String val12,
									 String val13,
									 String val14,
									 String val15
									 ){
				super(deb);
				setValues(val, val2, val3, val4, val5, val6, val7, val8, val9,
									val10, val11, val12, val13, val14, val15);
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
											String val12,
											String val13,
											String val14,
											String val15
											){
				setId(val);
				setType_id(val2);
				setStatus(val3);
				setDate(val4);
				setTime(val5);
				setUsername(val6);
				setFullname(val7);
				setPhone(val8);
				setDept(val9);
				setDivision(val10);
				setGroup_id(val11);
				setLocation(val12);
				setSummary(val13);
				setDescription(val14);
				setDue_date(val15);
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
				void setStatus(String val){
				if(val != null){				
						status = val;
				}
    }
    public
				void setUsername(String val){ 
				if(val != null)								
						username = val;
    }
    public
				void setFullname(String val){ 
				if(val != null)								
						fullname = val;
    }		
    public
				void setPhone(String val){
				if(val != null)				
						phone = val.trim();
    }
    public
				void setLocation(String val){
				if(val != null)				
						location = val.trim();
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
				void setDept(String val){
				if(val != null)				
						dept = val.trim();
    }
    public
				void setDivision(String val){
				if(val != null)				
						division = val.trim();
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

    public
				String  getPhone(){
				return phone;
    }
    public
				String  getLocation(){
				return location;
    }
    public
				String  getDate(){
				if(date.equals("")){
						date = Helper.getToday();
				}
				return date;
    }
    public
				String  getTime(){
				return time;
    }
    public				
				String  getUsername(){
				return username;
    }
    public
				String  getFullname(){
				return fullname;
    }		
    public
				String  getSummary(){
				return summary;
    }
    public
				String  getDept(){
				return dept;
    }
    public
				String  getDivision(){
				return division;
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

    public String doSave(){
				String msg = "";
				Connection con = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				String qq = "insert into requests values(0,?,?,now(),?, ?,?,?,?,?, ?,?,?,?)";
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
								notes = " request assigned to user id "+assign_user_id;
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
						if(username.equals(""))
								stmt.setNull(jj++, Types.VARCHAR);
						else 
								stmt.setString(jj++, username);
						if(fullname.equals(""))
								stmt.setNull(jj++, Types.VARCHAR);
						else 
								stmt.setString(jj++, fullname);						
						if(phone.equals(""))
								stmt.setNull(jj++, Types.VARCHAR);
						else 
								stmt.setString(jj++, phone);			
						if(dept.equals("")){
								stmt.setNull(jj++, Types.VARCHAR);
						}
						else {
								stmt.setString(jj++, dept);			
						}
						if(division.equals("")){
								stmt.setNull(jj++, Types.VARCHAR);
						}
						else {
								stmt.setString(jj++, division);			
						}
						if(group_id.equals("")){
								stmt.setNull(jj++, Types.INTEGER);
						}
						else {
								stmt.setString(jj++, group_id);			
						}			
						if(location.equals("")){
								stmt.setNull(jj++, Types.VARCHAR);
						}
						else {
								stmt.setString(jj++, location);		
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
				String qq = "update requests set type_id=?,username=?,fullname=?,phone=?,dept=?, division=?,group_id=?, location=?,summary=?, description=?, due_date=? where id=? ";
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
								stmt.setString(12,id);
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
								notes = " new assignment to user id "+assign_user_id;
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
						"username,"+
						"fullname,"+
						"phone,"+
						"dept,"+
						"division,"+
						"group_id,"+
						"location,"+ 						
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
													rs.getString(12),
													rs.getString(13),
													rs.getString(14),
													rs.getString(15));
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
}






















































