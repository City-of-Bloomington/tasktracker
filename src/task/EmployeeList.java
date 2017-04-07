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


public class EmployeeList extends CommonInc{

		static Logger logger = Logger.getLogger(EmployeeList.class);
		static final long serialVersionUID = 3150L;
		String name = "", id="", email="", phone="", dept="", division="",
				request_id="",
				limit="";
		
		List<Employee> employees = null;
		public EmployeeList(){
				super();
		}
		public EmployeeList(boolean deb){
				super(deb);
		}		
		public EmployeeList(boolean deb, String val){
				super(deb);
				setName(val);
		}
		public List<Employee> getEmployees(){
				return employees;
		}
		public void setName(String val){
				if(val != null)
						name = val;
		}
		public void setEmail(String val){
				if(val != null)
						email = val;
		}		
		public void setId(String val){
				if(val != null)
						id = val;
		}
		public void setDept(String val){
				if(val != null)
						dept = val;
		}
		public void setRequest_id(String val){
				if(val != null && !val.equals("-1"))
						request_id = val;
		}		
		public void setDivision(String val){
				if(val != null)
						division = val;
		}				
		public String getId(){
				return id;
		}
		public String getDept(){
				return dept;
		}
		public String getDivision(){
				return division;
		}
		public String getRequest_id(){
				return request_id;
		}		
		public String getName(){
				return name;
		}
		public String getEmail(){
				return email;
		}		
		public void setNoLimit(){
				limit = "";
		}
		public String find(){
		
				String back = "";
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				Connection con = Helper.getConnection();
				String qq = "select u.id,u.fullname,u.email,u.job_title,u.phone,u.dept,u.division from employees u ", qw ="";
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				if(!id.equals("")){
						qw += " u.id = ? ";
				}
				else{
						if(!name.equals("")){
								qw += " u.fullname like ? ";
						}
						if(!email.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " u.email like ? ";
						}						
						if(!phone.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " u.phone=? ";
						}
						if(!dept.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " u.dept=? ";
						}
						if(!division.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " u.division=? ";
						}						
				}
				if(!qw.equals("")){
						qq += " where "+qw;
				}
				qq += " order by u.fullname ";
				
				if(!limit.equals("")){
						qq += limit;
				}
				try{
						if(debug){
								logger.debug(qq);
						}
						//
						// System.err.println(qq);
						//
						pstmt = con.prepareStatement(qq);
						if(!id.equals("")){
								pstmt.setString(1, id);
						}
						else {
								int jj=1;
								if(!name.equals("")){
										pstmt.setString(jj++,"%"+name+"%");
								}
								if(!email.equals("")){
										pstmt.setString(jj++,email+"%");		
								}
								if(!phone.equals("")){
										pstmt.setString(jj++,phone);		
								}								
								if(!dept.equals("")){
										pstmt.setString(jj++,dept);		
								}
								if(!division.equals("")){
										pstmt.setString(jj++,division);		
								}								
						}
						rs = pstmt.executeQuery();
						while(rs.next()){
								if(employees == null)
										employees = new ArrayList<>();
								Employee one =
										new Employee(debug,
																 rs.getString(1),
																 rs.getString(2),
																 rs.getString(3),
																 rs.getString(4),
																 rs.getString(5),
																 rs.getString(6),
																 rs.getString(7));
								if(!employees.contains(one))
										employees.add(one);
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






















































