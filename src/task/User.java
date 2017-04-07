package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

public class User extends CommonInc implements java.io.Serializable{

    String id="", username="", role="user", // user, admin
				email="",
				dept_id="", division_id="", inactive="";
		String fullname="";
		static final long serialVersionUID = 2900L;
		static Logger logger = Logger.getLogger(User.class);
		static Map<String, String> rolesMap = null;
		List<Group> groups = null;
		Group group = null;
		//
		public User(){
				super();
		}
		public User(boolean deb){
				//
				// initialize
				//
				super(deb);
    }
		public User(String val){
				//
				setId(val);
    }
		public User(boolean deb, String val){
				//
				// initialize
				//
				super(deb);
				setId(val);
    }
		public User(boolean deb, String val, String val2){
				//
				// initialize
				//
				debug = deb;
				setId(val);
				setUsername(val2);
    }
		public User(boolean deb, String val, String val2, String val3){
				//
				// initialize
				//
				debug = deb;
				setId(val);
				setUsername(val2);
				setFullname(val3);
    }		
		
		public User(boolean deb,
								String val,
								String val2,
								String val3,
								String val4,
								String val5,
								String val6,
								String val7,
								boolean val8
								){
				//
				// initialize
				//
				debug = deb;
				setId(val);
				setUsername(val2);
				setFullname(val3);
				setRole(val4);				
				setDept_id(val5);
				setDivision_id(val6);
				setEmail(val7);
				setInactive(val8);
    }
		public String getId(){
				return id;
		}
		public String getUsername(){
				return username;
		}		
		public String getRole(){
				return role;
		}
		public String getRoleInfo(){
				String ret = "";
				setRoleMap();
				if(rolesMap != null && rolesMap.containsKey(role)){
						ret = rolesMap.get(role);
				}
				return ret; 
		}
		public String getDept_id(){
				return dept_id;
		}
		public String getDivision_id(){
				return division_id;
		}				
		public String getFullname(){
				return fullname;
		}
		public String getEmail(){
				return email;
		}		
		public boolean getInactive(){
				return !inactive.equals("");
		}

		public void setId(String val){
				if(val != null)
						id = val;
		}
		public void setUsername(String val){
				if(val != null)
						username = val;
		}		
		public void setRole(String val){
				if(val != null)
						role = val;
		}
		public void setEmail(String val){
				if(val != null)
						email = val;
		}		
		public void setDept_id(String val){
				if(val != null)
						dept_id = val;
		}
		public void setDivision_id(String val){
				if(val != null)
						division_id = val;
		}		
		public void setFullname(String val){
				if(val != null)
						fullname = val;
		}
		public void setInactive(boolean val){
				if(val)
						inactive = "y";
		}		
		//
		public boolean userExists(){
				return !fullname.equals("");
		}
		    //
    public boolean hasRole(String val){
				return role != null && role.indexOf(val) > -1;
    }
		public boolean canEdit(){
				return hasRole("Edit");
		}
		public boolean canDelete(){
				return hasRole("Delete");
		}
		public boolean isAdmin(){
				return hasRole("Admin") || hasRole("Super");
		}
		public boolean isSuper(){
				return hasRole("Super");
		}
		public boolean hasEmail(){
				return !email.equals("");
		}
    //
    public String toString(){
				return fullname;
    }
		private void setRoleMap(){
				if(rolesMap == null){
						rolesMap = new HashMap<>();
						rolesMap.put("View","View");
						rolesMap.put("Edit","Edit");
						rolesMap.put("Admin","Admin");
						rolesMap.put("Super","Super User(All)");						
				}
		}
		public boolean equals(Object obj){
				if(obj instanceof User){
						User one =(User)obj;
						return id.equals(one.getId());
				}
				return false;				
		}
		public boolean hasGroups(){
				getGroups();
				return groups != null && groups.size() > 0;
		}
		public Group getGroup(){
				if(group == null){
						getGroups();
						if(groups != null){
								group = groups.get(0);
						}
				}
				return group;
		}
		public String getGroupNames(){
				String ret = "";
				getGroups();
				if(groups != null){
						for(Group one:groups){
								if(!ret.equals("")) ret +=", ";
								ret += one.getName();
						}
				}
				return ret;
		}
		//
		// when a user has only one group (mostly reqular users and admins
		// multiple users or no users will be for super user only
		//
		public boolean hasOneGroupOnly(){
				getGroups();
				return groups != null && groups.size() == 1;
		}
		public int hashCode(){
				int seed = 37;
				if(!id.equals("")){
						try{
								seed += Integer.parseInt(id);
						}catch(Exception ex){
						}
				}
				return seed;
		}
		List<Group> getGroups(){
				if(groups == null && !id.equals("")){
						GroupList gl = new GroupList(debug);
						gl.setUser_id(id);
						String back = gl.find();
						if(back.equals(""))
								groups = gl.getGroups();
				}
				return groups;
		}
		//
    public String doSelect(){
				String msg = "";
				Connection con = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;		
				String qq = " select id,username,fullname,role,dept_id,division_id,email,inactive from users where  ";
				if(!id.equals("")){
						qq += " id = ? ";
				}
				else if(!username.equals("")){
						qq += " username = ? ";
				}
				else {
						msg = " User id or username not set";
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
						else
								stmt.setString(1, username);								
						rs = stmt.executeQuery();
						if(rs.next()){
								setId(rs.getString(1));
								setUsername(rs.getString(2));
								setFullname(rs.getString(3));
								setRole(rs.getString(4));								
								setDept_id(rs.getString(5));
								setDivision_id(rs.getString(6));
								setEmail(rs.getString(7));
								setInactive(rs.getString(8) != null);
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
		//
    public String doSave(){
		
				Connection con = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;		
		
				String str="", msg="";
				String qq = "";
				if(username.equals("") || fullname.equals("")){
						msg = "username or  full name not set";
						return msg;
				}
				qq = "insert into users values(0,?,?,?,?,?,?,?)";
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
						stmt.setString(1, username);
						stmt.setString(2,fullname);
						stmt.setString(3, role);						
						if(dept_id.equals(""))
								stmt.setNull(4,Types.INTEGER);
						else
								stmt.setString(4, dept_id);
						if(division_id.equals(""))
								stmt.setNull(5,Types.INTEGER);
						else
								stmt.setString(5, division_id);

						if(email.equals(""))
								stmt.setNull(6,Types.INTEGER);
						else
								stmt.setString(6, email);
						if(inactive.equals(""))						
								stmt.setNull(7,Types.CHAR);
						else
								stmt.setString(7, "y");						
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
    public String doUpdate(){
		
				Connection con = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;		
		
				String str="", msg="";
				String qq = "";
				qq = "update users set username=?,fullname=?,role=?,dept_id=?,division_id=?,email=?,inactive=? where id=?";
				//
				if(id.equals("") || username.equals("") || fullname.equals("")){
						msg = "User id, username or full name not set";
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
						stmt.setString(1, username);
						stmt.setString(2, fullname);
						if(role.equals(""))
								stmt.setNull(3,Types.VARCHAR);
						else
								stmt.setString(3, role);						
						if(dept_id.equals(""))
								stmt.setNull(4,Types.INTEGER);
						else
								stmt.setString(4, dept_id);
						if(division_id.equals(""))
								stmt.setNull(5,Types.INTEGER);
						else
								stmt.setString(5, division_id);
						if(email.equals(""))
								stmt.setNull(6,Types.VARCHAR);
						else
								stmt.setString(6, email);								
						if(inactive.equals(""))
								stmt.setNull(7,Types.CHAR);
						else
								stmt.setString(7, "y");						
						stmt.setString(8, id);
						stmt.executeUpdate();
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
		//
    String doDelete(){

				String qq = "",msg="";
				Connection con = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;			
				qq = "delete from  users where id=?";
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to DB";
						addError(msg);
						return msg;
				}			
				try{
						stmt = con.prepareStatement(qq);
						stmt.setString(1,id);
						stmt.executeUpdate();
						message="Deleted Successfully";
						//
				}
				catch(Exception ex){
						msg = ex+" : "+qq;
						logger.error(msg);
						addError(msg);
				}
				finally{
						Helper.databaseDisconnect(con, stmt, rs);
				}			
				return msg; 
    }


}
