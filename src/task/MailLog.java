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

public class MailLog extends CommonInc{

		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		static final long serialVersionUID = 2160L;	
		static Logger logger = Logger.getLogger(MailLog.class);	
    String id="", 
				request_id="",
				date="",
				mail_to="", mail_from="", mail_cc="", mail_subject="",mail_msg="",
				status="",
				error_details="";
    //
    public MailLog(){

    }
    public MailLog(boolean deb){
				super(deb);
		}		
    public MailLog(boolean deb, String val){
				super(deb);
				setId(val);
    }
    public MailLog(boolean deb,
									 String val,
									 String val2,
									 String val3,
									 String val4,
									 String val5,
									 String val6,
									 String val7,
									 String val8,
									 String val9,
									 String val10
									 ){
				super(deb);
				setValues(val, val2, val3, val4, val5, val6, val7, val8, val9, val10);
    }
		// for save
    public MailLog(boolean deb,
									 String val,
									 String val2,
									 String val3,
									 String val4,
									 String val5,
									 String val6,
									 String val7,
									 String val8
									 ){
				super(deb);
				setValues(null, val, null, val2, val3, val4, val5, val6, val7, val8);
				
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
													 String val10
													 ){
				setId(val);
				setRequest_id(val2);
				setDate(val3);
				setMailTo(val4);
				setMailFrom(val5);
				setMailCc(val6);
				setMailSubject(val7);
				setMailMsg(val8);
				setStatus(val9);
				setErrorDetails(val10);
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
		void setDate(String val){
		if(val != null)
			date = val;
    }
    public
		void setMailTo(String val){
		if(val != null)
			mail_to = val;
    }		
    public
		void setMailFrom(String val){
				if(val != null)
						mail_from = val;
    }
    public
		void setMailCc(String val){
				if(val != null)
						mail_cc = val;
    }
    public
		void setMailSubject(String val){
				if(val != null)
						mail_subject = val;
    }
    public
		void setMailMsg(String val){
				if(val != null)
						mail_msg = val;
    }
    public
		void setStatus(String val){
		if(val != null)
			status = val;
    }
    public
		void setErrorDetails(String val){
		if(val != null)
			error_details = val;
    }		

    //
    // getters
    //
    public
		String  getId(){
				return id;
    }
    public
		String  getRequest_id(){
				return request_id;
    }
    public
		String  getDate(){
				return date;
    }		
    public
				String  getMailTo(){
				return mail_to;
    }		
    public
				String  getMailCc(){
				return mail_cc;
				
    }
    public
				String  getMailSubject(){
				return mail_subject;
    }
    public
				String  getMailFrom(){
				return mail_from;
    }
    public
				String  getMailMsg(){
				return mail_msg;
    }
    public
		String  getStatus(){
				return status;
    }
    public
		String  getErrorDetails(){
				return error_details;
    }				
    public String doSave(){
				String msg = "";
				Connection con = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				String qq = "insert into mail_logs values(0,?,now(),?,?, ?,?,?,?,?)";
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
						
						if(mail_to.equals(""))
								stmt.setNull(jj++, Types.VARCHAR);
						else 
								stmt.setString(jj++, mail_to);
						if(mail_from.equals(""))
								stmt.setNull(jj++, Types.VARCHAR);
						else 
								stmt.setString(jj++, mail_from);
						if(mail_cc.equals(""))
								stmt.setNull(jj++, Types.VARCHAR);
						else 
								stmt.setString(jj++, mail_cc);
						if(mail_subject.equals(""))
								stmt.setNull(jj++, Types.VARCHAR);
						else 
								stmt.setString(jj++, mail_subject);
						if(mail_msg.equals(""))
								stmt.setNull(jj++, Types.VARCHAR);
						else 
								stmt.setString(jj++, mail_msg);
						stmt.setString(jj++, status);
						if(error_details.equals(""))
								stmt.setNull(jj++, Types.VARCHAR);
						else 
								stmt.setString(jj++, error_details);						
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
						"date_format(date,'%m/%d/%Y (%h:%i %p)'), "+
						"mail_to,"+
						"mail_from,"+
						"mail_cc,"+
						"mail_subject,"+
						"mail_msg,"+
						"status,"+
						"error_details "+
						"from mail_logs where id=? ";

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
													rs.getString(10)
													);
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






















































