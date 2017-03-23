package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */

import java.sql.*;
import java.io.*;
import java.util.List;
import org.apache.log4j.Logger;


public class Division extends Type{

		static final long serialVersionUID = 500L;			
		static Logger logger = Logger.getLogger(Division.class);		
		String dept_id = "";

    public Division(){
				setTable_name("divisions");
    }
    public Division(boolean deb, String val, String val2){
				super(deb, val, val2);
				setTable_name("divisions");
    }		
    public Division(boolean deb, String val, String val2, boolean val3){
				super(deb, val, val2, val3);
				setTable_name("divisions");
    }
    public Division(boolean deb, String val, String val2, boolean val3, String val4){
				super(deb, val, val2, val3);
				setTable_name("divisions");
				setDept_id(val4);
    }		
		public void setDept_id(String val){
				if(val != null)
						dept_id = val;
		}
		public String getDept_id(){
				return dept_id;
		}
		@Override
		public String doSelect(){
				String back = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String qq = "select id,name,inactive,dept_id "+
						"from divisions where id=?";
				con = Helper.getConnection();
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				try{
						if(debug){
								logger.debug(qq);
						}				
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1,id);
						rs = pstmt.executeQuery();
						if(rs.next()){
								setName(rs.getString(2));
								setInactive(rs.getString(3) != null);
								setDept_id(rs.getString(4));
						}
						else{
								back ="Record "+id+" Not found";
								message = back;
						}
				}
				catch(Exception ex){
						back += ex+":"+qq;
						logger.error(back);
						addError(back);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);			
				}
				return back;
		}
		@Override
		public String doSave(){
		
				String back = "";
		
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String qq = "insert into divisions values(0,?,null,?)";
				if(name.equals("")){
						back = "department name not set ";
						logger.error(back);
						addError(back);
						return back;
				}
				con = Helper.getConnection();
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				try{
						pstmt = con.prepareStatement(qq);
						if(debug){
								logger.debug(qq);
						}
						pstmt.setString(1,name);
						if(dept_id.equals(""))
								pstmt.setNull(2, Types.INTEGER);
						else
								pstmt.setString(2, dept_id);
						pstmt.executeUpdate();
						//
						// get the id of the new record
						//
						qq = "select LAST_INSERT_ID() ";
						if(debug){
								logger.debug(qq);
						}
						pstmt = con.prepareStatement(qq);				
						rs = pstmt.executeQuery();
						if(rs.next()){
								id = rs.getString(1);
						}
				}
				catch(Exception ex){
						back += ex;
						logger.error(back);
						addError(back);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return back;

		}
		@Override
		public String doUpdate(){
		
				String back = "";
				if(name.equals("")){
						back = " name not set ";
						logger.error(back);
						addError(back);
						return back;
				}
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String str="";
				String qq = "";
		
				con = Helper.getConnection();
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				try{
						qq = "update divisions set name=?,inactive=?,dept_id=? "+
								"where id=?";
						if(debug){
								logger.debug(qq);
						}
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1,name);
						if(inactive.equals(""))
								pstmt.setNull(2, Types.VARCHAR);
						else
								pstmt.setString(2, "y");
						if(dept_id.equals(""))
								pstmt.setNull(3, Types.INTEGER);
						else
								pstmt.setString(3, dept_id);						
						pstmt.setString(4,id);
						pstmt.executeUpdate();
				}
				catch(Exception ex){
						back += ex+":"+qq;
						logger.error(back);
						addError(back);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return back;

		}
		@Override
		public String doDelete(){
		
				String back = "", qq = "";
		
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
		
				con = Helper.getConnection();
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				try{
						qq = "delete from divisions where id=?";
						if(debug){
								logger.debug(qq);
						}
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1,id);
						pstmt.executeUpdate();
						message="Deleted Successfully";
						id="";
						name="";
				}
				catch(Exception ex){
						back += ex+":"+qq;
						logger.error(back);
						addError(back);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return back;
		}

}
