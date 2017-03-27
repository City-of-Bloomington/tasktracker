/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package task;
import java.io.*;
import java.sql.*;
import javax.naming.*;
import javax.naming.directory.*;
import java.nio.charset.*;
import org.apache.log4j.Logger;


public class FileUpload implements java.io.Serializable{

    String id="", file_name="", old_file_name="", user_id="", date="";
		String request_id="", notes="";
		static final long serialVersionUID = 760L;		
		static Logger logger = Logger.getLogger(FileUpload.class);
		User user = null;
		Request request = null;
    public FileUpload(){
    }		
    public FileUpload(String val){
				setId(val);
    }	
    public FileUpload(
											String val,
											String val2,
											String val3,
											String val4,
											String val5,
											String val6,
											String val7){
				setValues(val,
									val2,
									val3,
									val4,
									val5,
									val6,
									val7);

    }
		void setValues(String val,
									 String val2,
									 String val3,
									 String val4,
									 String val5,
									 String val6,
									 String val7){
				setId(val);
				setFile_name(val2);
				setOld_file_name(val3);
				setRequest_id(val4);
				setDate(val5);
				setUser_id(val6);				
				setNotes(val7);
		}
    //
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
    public String getFile_name(){
				return file_name;
    }
    public String getOld_file_name(){
				return old_file_name;
    }	
    public String getDate(){
				return date;
    }
    public String getNotes(){
				return notes;
    }
		
		public User getUser(){
				if(user == null && !user_id.equals("")){
						User one = new User(false, user_id);
						String back = one.doSelect();
						if(back.equals("")){
								user = one;
						}
				}
				return user;
		}
		public Request getRequest(){
				if(request == null && !request_id.equals("")){
						Request one = new Request(false, request_id);
						String back = one.doSelect();
						if(back.equals("")){
								request = one;
						}
				}
				return request;
		}
    //
    // setters
    //
    public void setId(String val){
				if(val != null)
						id = val;
    }	
    public void setFile_name (String val){
				if(val != null)
						file_name = val;
    }
    public void setOld_file_name (String val){
				if(val != null)
						old_file_name = val;
    }	
    public void setDate (String val){
				if(val != null)
						date = val;
    }
    public void setNotes(String val){
				if(val != null)
						notes = val;
    }		
	
    public void setUser_id (String val){
				if(val != null)
						user_id = val;
    }
    public void setRequest_id (String val){
				if(val != null)
					 request_id = val;
    }
	
    public String toString(){
				return old_file_name;
    }
		String genNewFileName(String file_ext){
				findNewId(); // first get the next id
				if(file_ext == null)
						file_ext="";
				if(!id.equals("")){
						file_name = "request_"+id+"."+file_ext; 
				}
				return file_name;
		}
		@Override
		public int hashCode() {
				int hash = 7, id_int = 0;
				if(!id.equals("")){
						try{
								id_int = Integer.parseInt(id);
						}catch(Exception ex){}
				}
				hash = 67 * hash + id_int;
				return hash;
		}
		@Override
		public boolean equals(Object obj) {
				if (obj == null) {
						return false;
				}
				if (getClass() != obj.getClass()) {
						return false;
				}
				final FileUpload other = (FileUpload) obj;
				return this.id.equals(other.id);
		}
		String findNewId(){
				String msg="";
				PreparedStatement pstmt = null;
				Connection con = null;
				ResultSet rs = null;
				String qq = "insert into media_seq values(0)";
				logger.debug(qq);
				con = Helper.getConnection();
				if(con == null){
						msg += " could not connect to database";
						return msg;
				}		
				try{
						pstmt = con.prepareStatement(qq);
						pstmt.executeUpdate();
						qq = "select LAST_INSERT_ID() ";
						pstmt = con.prepareStatement(qq);
						rs = pstmt.executeQuery();
						if(rs.next()){
								id = rs.getString(1);
						}
				}
				catch(Exception ex){
						msg += " "+ex;
						logger.error(ex+":"+qq);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;		

		}
		public String doSelect(){
				String msg="";
				PreparedStatement pstmt = null;
				Connection con = null;
				ResultSet rs = null;		
				String qq = "select id,file_name,old_file_name,request_id,date_format(date,'%m/%d/%Y'),user_id,notes from media where id=?";
				logger.debug(qq);
				con = Helper.getConnection();
				if(con == null){
						msg += " could not connect to database";
						return msg;
				}		
				try{
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, id);				
						rs = pstmt.executeQuery();
						if(rs.next()){
								setValues(rs.getString(1),
													rs.getString(2),
													rs.getString(3),
													rs.getString(4),
													rs.getString(5),
													rs.getString(6),
													rs.getString(7));
						}
				}
				catch(Exception ex){
						msg += " "+ex;
						logger.error(ex+":"+qq);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;
		}
		public String doSave(){
				String msg="";
				PreparedStatement pstmt = null;
				Connection con = null;
				ResultSet rs = null;
				if(file_name.equals("")){
						msg = "File name is not set ";
						return msg;
				}
				String qq = "insert into media values(?,?,?,?,now(),?,?)";
				logger.debug(qq);
				con = Helper.getConnection();
				if(con == null){
						msg += " could not connect to database";
						return msg;
				}		
				try{
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, id);
						pstmt.setString(2, file_name);
						pstmt.setString(3, old_file_name);
						pstmt.setString(4, request_id);
						pstmt.setString(5, user_id);
						if(notes.equals(""))
								pstmt.setNull(6, Types.VARCHAR);
						else
								pstmt.setString(6, notes);
						
						pstmt.executeUpdate();
				}
				catch(Exception ex){
						msg += " "+ex;
						logger.error(ex+":"+qq);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;
		}

}
