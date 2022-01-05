package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Assignment extends CommonInc{

    String user_id="", id="", request_id="", date="";
		static final long serialVersionUID = 100L;	
		static Logger logger = LogManager.getLogger(Assignment.class);
		User user = null;
    public Assignment(boolean deb,
										String val, 
										String val2,
										String val3,
										String val4
					){
				super(deb);
				setId(val);
				setUser_id(val2);
				setRequest_id(val3);
				setDate(val4);
    }
		// for new assignment
    public Assignment(boolean deb,
										String val, 
										String val2
					){
				super(deb);
				setUser_id(val);
				setRequest_id(val2);
    }		

    public Assignment(){

    }
    public Assignment(boolean deb,
										String val
					){
				super(deb);
				setId(val);
    }		
    //
    // getters
    //
    public String getId(){
				return id;
    }
    public String getUser_id(){
				return user_id;
    }
    public String getRequest_id(){
				return request_id;
    }
    public String getDate(){
				return date;
    }		
    //
    // setters
    //
    public void setId (String val){
				if(val != null)
						id = val;
    }
    public void setUser_id (String val){
				if(val != null)
						user_id = val;
    }
    public void setRequest_id (String val){
				if(val != null)
						request_id = val;
    }
    public void setDate(String val){
				if(val != null)
						date = val;
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
				String qq = "insert into assignments values(0,?,?,now())";
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
						stmt.setString(1, user_id);
						stmt.setString(2, request_id);
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
				String qq = "select id,user_id,"+
						"request_id,"+
						"date_format(date,'%m/%d/%Y') "+  
						"from assignments where id=? ";
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
								setUser_id(rs.getString(2));
								setRequest_id(rs.getString(3));
								setDate(rs.getString(4));
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
