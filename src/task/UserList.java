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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserList extends CommonInc{

		static Logger logger = LogManager.getLogger(UserList.class);
		static final long serialVersionUID = 3100L;
		String name = "", id="", username="", role="",
				limit="";
		String group_id = "", request_id="", exclude_group_id="",
				group_managers_id="", exclude_group_managers_id="",
				exclude_dept_id="",
				dept_id="", division_id="";
		boolean exclude_current_request_assignees = false, active_only = false;
		
		List<User> users = null;
		public UserList(){
				super();
		}
		public UserList(boolean deb){
				super(deb);
		}		
		public UserList(boolean deb, String val){
				super(deb);
				setName(val);
		}
		public List<User> getUsers(){
				return users;
		}
		public void setName(String val){
				if(val != null)
						name = val;
		}
		public void setUsername(String val){
				if(val != null)
						username = val;
		}		
		public void setId(String val){
				if(val != null)
						id = val;
		}
		public void setGroup_id(String val){
				if(val != null && !val.equals("-1"))
						group_id = val;
		}
		public void setDept_id(String val){
				if(val != null && !val.equals("-1"))
						dept_id = val;
		}
		public void setRequest_id(String val){
				if(val != null && !val.equals("-1"))
						request_id = val;
		}		
		public void setDivision_id(String val){
				if(val != null && !val.equals("-1"))
						division_id = val;
		}				
		public void setExcludeGroup_id(String val){
				if(val != null)
						exclude_group_id = val;
		}
		public void setExcludeGroupManagers_id(String val){
				if(val != null)
						exclude_group_managers_id = val;
		}
		public void setGroupManagers_id(String val){
				if(val != null)
						group_managers_id = val;
		}		
		public void setExclude_dept_id(String val){
				if(val != null)
						exclude_dept_id = val;
		}		
		public void setRole(String val){
				if(val != null && !val.equals("-1")){
						role = val;
				}
		}
		public String getId(){
				return id;
		}
		public String getGroup_id(){
				return group_id;
		}
		public String getGroupManagers_id(){
				return group_managers_id;
		}
		public String getExcludeGroupManagers_id(){
				return exclude_group_managers_id; // group id
		}		
		public String getDept_id(){
				if(dept_id.equals("")){
						return "-1";
				}
				return dept_id;
		}
		public String getDivision_id(){
				if(division_id.equals("")){
						return "-1";
				}
				return division_id;
		}
		public String getRequest_id(){
				return request_id;
		}		
		public String getName(){
				return name;
		}
		public String getUsername(){
				return username;
		}		
		public String getRole(){
				if(role.equals("")){
						return "-1";
				}
				return role;
		}
		public void excludeCurrentRequestAssignees(){
				exclude_current_request_assignees = true;
		}
		public void setNoLimit(){
				limit = "";
		}
		public void setActiveOnly(){
				active_only = true;
		}
		public String find(){
		
				String back = "";
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				Connection con = Helper.getConnection();
				String qq = "select u.id,u.username,u.fullname,u.role,u.dept_id,u.division_id,u.email,u.inactive from users u ", qw ="";
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
						if(!username.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " u.username like ? ";
						}
						if(active_only){
								if(!qw.equals("")) qw += " and ";								
								qw += " u.inactive is null ";
						}
						if(!role.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " u.role=? ";
						}
						if(!dept_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " u.dept_id=? ";
						}
						else if(!exclude_dept_id.equals("")){
								if(!qw.equals("")) qw += " and ";								
								qw += " (u.dept_id != ? or u.dept_id is null)";
						}
						if(!division_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " u.division_id=? ";
						}						
						if(!group_id.equals("")){
								qq += ", group_users ug ";
								if(!qw.equals("")) qw += " and ";
								qw += " ug.user_id=u.id and ug.group_id=?";								
						}
						else if(!exclude_group_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " not u.id in (select ug.user_id from group_users ug where ug.group_id = ?)";			
								
						}
						if(!group_managers_id.equals("")){
								qq += ", group_managers gm ";
								if(!qw.equals("")) qw += " and ";
								qw += " gm.user_id=u.id and gm.group_id=?";								
						}
						else if(!exclude_group_managers_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " not u.id in (select gm.user_id from group_managers gm where gm.group_id = ?)";			
								
						}						
						if(!request_id.equals("") && exclude_current_request_assignees){
								if(!qw.equals("")) qw += " and ";
								qw += " not u.id in (select a.user_id from assignments a where a.request_id=? ) ";
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
								if(!username.equals("")){
										pstmt.setString(jj++,username+"%");		
								}
								if(!role.equals("")){
										pstmt.setString(jj++,role);		
								}
								if(!dept_id.equals("")){
										pstmt.setString(jj++,dept_id);		
								}
								else if(!exclude_dept_id.equals("")){
										pstmt.setString(jj++,exclude_dept_id);		
								}
								if(!division_id.equals("")){
										pstmt.setString(jj++,division_id);		
								}								
								if(!group_id.equals("")){
										pstmt.setString(jj++,group_id);		
								}
								else if(!exclude_group_id.equals("")){
										pstmt.setString(jj++,exclude_group_id);		
								}
								if(!group_managers_id.equals("")){
										pstmt.setString(jj++,group_managers_id);		
								}
								else if(!exclude_group_managers_id.equals("")){
										pstmt.setString(jj++,exclude_group_managers_id);		
								}								
								if(!request_id.equals("") && exclude_current_request_assignees){
										pstmt.setString(jj++,request_id);
								}
						}
						rs = pstmt.executeQuery();
						while(rs.next()){
								if(users == null)
										users = new ArrayList<>();
								User one =
										new User(debug,
														 rs.getString(1),
														 rs.getString(2),
														 rs.getString(3),
														 rs.getString(4),
														 rs.getString(5),
														 rs.getString(6),
														 rs.getString(7),
														 rs.getString(8) != null);
								if(!users.contains(one))
										users.add(one);
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






















































