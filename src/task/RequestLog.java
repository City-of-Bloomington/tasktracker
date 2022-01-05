package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class RequestLog extends CommonInc{

		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		static final long serialVersionUID = 2150L;	
		static Logger logger = LogManager.getLogger(RequestLog.class);	
    String id="", 
				request_id="",
				type="",
				date="",
				user_id="",
				notes="";
		User user = null;
    //
    public RequestLog(){

    }
    public RequestLog(boolean deb){
				super(deb);
		}		
    public RequestLog(boolean deb, String val){
				super(deb);
				setId(val);
    }
    public RequestLog(boolean deb,
								String val,
								String val2,
								String val3,
								String val4,
								String val5,
								String val6){
				super(deb);
				setValues(val, val2, val3, val4, val5, val6);
    }
		private void setValues(
									String val,
									String val2,
									String val3,
									String val4,
									String val5,
									String val6
													 ){
				setId(val);
				setRequest_id(val2);
				setUser_id(val3);
				setDate(val4);
				setType(val5);
				setNotes(val6);
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
		void setUser_id(String val){
				if(val != null)				
						user_id = val;
    }		
    public
		void setDate(String val){
		if(val != null)
			date = val;
    }
    public
		void setType(String val){
		if(val != null)
			type = val;
    }		
    public
		void setNotes(String val){
				if(val != null)
						notes = val;
    }

    //
    // getters
    //
    public
		String  getId(){
				return id;
    }
    public
		String  getUser_id(){
				return user_id;
    }		
    public
		String  getRequest_id(){
				return request_id;
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
		String  getType(){
				return type;
    }
		public User getUser(){
				if(user == null && !user_id.equals("")){
						User one = new User(debug, user_id);
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
				if(request_id.equals("") || user_id.equals("")){
						msg = "request_id and user_id are required";
						return msg;
				}
				String qq = "insert into request_logs values(0,?,?,now(),?,?)";
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
						int jj=1;
						stmt.setString(jj++, request_id);
						stmt.setString(jj++, user_id);
						stmt.setString(jj++, type);
						if(notes.equals(""))
								stmt.setNull(jj++, Types.VARCHAR);
						else 
								stmt.setString(jj++, notes);						
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
				catch(Exception ex){
						msg = ex+": "+qq;
						logger.error(msg);
						addError(msg);
						return msg;
				}
				finally{
						Helper.databaseDisconnect(con, stmt, rs);
				}
				return msg; // success
		}
    public String doSelect(){
				String msg = "";
				Connection con = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				String qq = "select id,"+
						"request_id,"+
						"user_id,"+
						"date_format(date,'%m/%d/%Y (%h:%i %p)'), "+
						"type,"+
						"notes "+
						"from request_logs where id=? ";

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
													rs.getString(6));
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
		
}






















































