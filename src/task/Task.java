package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;

public class Task extends CommonInc{

		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		static final long serialVersionUID = 2100L;	
		static Logger logger = Logger.getLogger(Task.class);	
    String id="", 
				request_id="",
				name="",
				date="",
				task_by="",
				completed="0", 
				notes="";
    double expenses = 0.0, hours = 0.0;
		User user = null;
    //
    public Task(){

    }
    public Task(boolean deb){
				super(deb);
		}		
    public Task(boolean deb, String val){
				super(deb);
				setId(val);
    }
    public Task(boolean deb,
								String val,
								String val2,
								String val3,
								String val4,
								String val5,
								String val6,
								String val7,
								String val8,
								String val9){
				super(deb);
				setValues(val, val2, val3, val4, val5, val6, val7,val8, val9);
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
									String val9
													 ){
				setId(val);
				setRequest_id(val2);
				setName(val3);
				setDate(val4);
				setTask_by(val5);
				setCompleted(val6);
				setNotes(val7);
				setHours(val8);
				setExpenses(val9);
		}
    //
    // setters
    //
    public
		void setRequest_id(String val){
				if(val != null)
						request_id = val;
    }
    public
		void setId(String val){
				if(val != null)				
						id = val;
    }
    public
		void setName(String val){
				if(val != null)				
						name = val;
    }		
    public
		void setDate(String val){
		if(val != null)
			date = val;
    }
    public
		void setTask_by(String val){
				if(val != null)
						task_by = val;
    }
    public
		void setNotes(String val){
				if(val != null)
						notes = val;
    }
    public
		void setCompleted(String val){
				if(val != null)
						completed = val;
    }
    public
		void setHours(String val){
				if(val != null && !val.equals("")){
						hours = Double.valueOf(val).doubleValue();
				}
    }
    public
		void setExpenses(String val){
				if(val != null && !val.equals("")){
						expenses = Double.valueOf(val).doubleValue();
				}
    }
    //
    // getters
    //
    public
		String  getId(){
				return id;
    }
    public
		String  getName(){
				return name;
    }		
    public
		String  getRequest_id(){
				return request_id;
    }
    public
		String  getTask_by(){
				return task_by;
    }
    public
		String  getNotes(){
				return notes;
    }
    public
		String  getDate(){
				return date;
    }
    public
		String  getCompleted(){
				return completed;
    }
    public
		double getHours(){
				return hours;
    }
    public
		double getExpenses(){
				return expenses;
    }
		public boolean hasHoursAndExpenses(){
				return hours > 0 && expenses > 0;
		}
		public boolean isCompleted(){
				return completed.equals("100");
		}
		public User getUser(){
				if(user == null && !task_by.equals("")){
						User one = new User(debug, task_by);
						String back = one.doSelect();
						if(back.equals("")){
								user = one;
						}
				}
				return user;
		}
    public String doSave(){
				String msg = "";
				Connection con = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				String qq = "insert into tasks values(0,?,?,now(),?,?, ?,?,?)";
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
						msg = setParams(stmt, true);
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
				if(msg.equals("")){
						msg = checkRequestCompletion();
				}
				return msg; // success
		}
		private String setParams(PreparedStatement stmt, boolean forSave){
				String back = "";
				int jj=1;
				try{
						if(forSave){
								stmt.setString(jj++, request_id);
						}
						if(name.equals(""))
								stmt.setNull(jj++, Types.VARCHAR);
						else 
								stmt.setString(jj++, name);							
						if(forSave){
								stmt.setString(jj++, task_by);
						}						
						stmt.setString(jj++, completed);
						if(notes.equals(""))
								stmt.setNull(jj++, Types.VARCHAR);
						else 
								stmt.setString(jj++, notes);						
						stmt.setString(jj++, ""+hours);
						stmt.setString(jj++, ""+expenses);
				}
				catch(Exception ex){
						logger.error(ex);
						back += ex;
				}
				return back;
		}
    public String doUpdate(){
				String msg = "";
				Connection con = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				String qq = "update tasks set name=?,completed=?,notes=?, hours=?,expenses=?  where id=? ";
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
						msg = setParams(stmt, false);
						if(msg.equals("")){
								stmt.setString(6,id);
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
				if(msg.equals("")){
						msg = checkRequestCompletion();
				}
				if(msg.equals("")){
						msg = doSelect();
				}
				return msg; 
    }
    public String doSelect(){
				String msg = "";
				Connection con = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				String qq = "select id,"+
						"request_id,"+
						"name,"+
						"date_format(date,'%m/%d/%Y'), "+   // 23
						"task_by,"+
						"completed,"+
						"notes,"+
						"hours,"+
						"expenses "+
						"from tasks where id=? ";

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
													rs.getString(9));
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
		String checkRequestCompletion(){
				String back = "";
				if(!completed.equals("100") || request_id.equals("")){
						return back;
				}
				Request rq = new Request(debug, request_id);
				rq.setUser_id(task_by);
				return rq.doClose();
		}
		
}






















































