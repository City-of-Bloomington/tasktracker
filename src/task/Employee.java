package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Employee extends CommonInc implements java.io.Serializable{

    String email="", fullname="", id="",
				dept="", division="", job_title="", phone="";
		static final long serialVersionUID = 1400L;
		static Logger logger = LogManager.getLogger(Employee.class);
		static Map<String, String> rolesMap = null;
		//
		public Employee(){
				super();
		}
		public Employee(boolean deb){
				//
				// initialize
				//
				super(deb);
    }
		public Employee(boolean deb, String val){
				//
				// initialize
				//
				super(deb);
				setId(val);
    }
		public Employee(boolean deb,
										String val,
										String val2,
										String val3,
										String val4,
										String val5,
										String val6,
										String val7
										){
				//
				// initialize
				//
				debug = deb;
				setId(val);
				setEmail(val2);
				setFullname(val3);
				setJobTitle(val4);				
				setPhone(val5);
				setDept(val6);
				setDivision(val7);
				
    }
		public String getId(){
				return id;
		}		
		public String getEmail(){
				return email;
		}		
		public String getPhone(){
				return phone;
		}
		public String getDept(){
				return dept;
		}
		public String getDivision(){
				return division;
		}				
		public String getFullname(){
				return fullname;
		}
		public String getJobTitle(){
				return job_title;
		}		
		public String getDeptInfo(){
				String ret = division;
				if(!dept.equals("")){
						if(!ret.equals("")) ret += " - ";
						ret += dept;
				}
				return ret;
		}
		public void setId(String val){
				if(val != null)
						id = val;
		}
		public void setPhone(String val){
				if(val != null)
						phone = val;
		}
		public void setEmail(String val){
				if(val != null)
						email = val;
		}		
		public void setDept(String val){
				if(val != null)
						dept = val;
		}
		public void setDivision(String val){
				if(val != null)
						division = val;
		}		
		public void setFullname(String val){
				if(val != null)
						fullname = val;
		}
		public void setJobTitle(String val){
				if(val != null)
						job_title = val;
		}
		public boolean hasData(){
				return !id.equals("") || !fullname.equals("");
		}
    //
    public String toString(){
				return fullname;
    }
		public boolean equals(Object obj){
				if(obj instanceof Employee){
						Employee one =(Employee)obj;
						return id.equals(one.getId());
				}
				return false;				
		}
		public int hashCode(){
				int seed = 37;
				if(!id.equals("")){
						seed += id.hashCode();
				}
				return seed;
		}
		/**
		 * this function tries to find if the employee is in already in
		 * the system (DB) by calling doSelect(), if not it will save by
		 * calling doSave()
		 */
		public String addEmployee(){
				String back = doSelect();
				if(!back.equals("")){
						back = doSave();
				}
				return back;
		}
    public String doSave(){
		
				Connection con = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;		
		
				String str="", msg="";
				String qq = "";
				if(fullname.equals("")){
						msg = "full name not set";
						return msg;
				}
				qq = "insert into employees values(0,?,?,?,?,?,?)";
				//
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
						stmt.setString(1,fullname);						
						if(email.equals(""))
								stmt.setNull(2,Types.VARCHAR);
						else
								stmt.setString(2, email);						
						stmt.setString(3, job_title);
						if(phone.equals(""))
								stmt.setNull(4,Types.VARCHAR);
						else
								stmt.setString(4, phone);
						if(dept.equals(""))
								stmt.setNull(5,Types.VARCHAR);
						else
								stmt.setString(5, dept);
						if(division.equals(""))
								stmt.setNull(6,Types.VARCHAR);
						else
								stmt.setString(6, division);

						stmt.executeUpdate();
						qq = "select LAST_INSERT_ID() ";
						if(debug){
								logger.debug(qq);
						}
						stmt = con.prepareStatement(qq);				
						rs = stmt.executeQuery();
						if(rs.next())
								id = rs.getString(1);
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
				String qq = " select id,fullname,email,phone,job_title,dept,division from employees where  ";
				if(!id.equals("")){
						qq += " id = ? ";
				}
				else if(!email.equals("")){
						qq += " email = ? ";
				}
				else if(!fullname.equals("")){
						qq += " fullname like ? ";
				}
				else {
						msg = " Employee id, email and fullname not set";
						addError(msg);
						return msg;
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
						if(!id.equals(""))
								stmt.setString(1, id);
						else if(!email.equals(""))
								stmt.setString(1, email);
						else
								stmt.setString(1, fullname);
						rs = stmt.executeQuery();
						if(rs.next()){
								setId(rs.getString(1));
								setFullname(rs.getString(2));
								setEmail(rs.getString(3));
								setJobTitle(rs.getString(4));
								setPhone(rs.getString(5));								
								setDept(rs.getString(6));
								setDivision(rs.getString(7));
						}
						else{
								msg = " No such user";
								message = msg;
						}
				}catch(Exception ex){
						logger.error(ex+" : "+qq);
						msg += " "+ex;
						addError(msg);
				}
				finally{
						Helper.databaseDisconnect(con, stmt, rs);
				}
				return msg;
    }
		
}
