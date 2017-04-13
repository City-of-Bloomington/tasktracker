package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

public class GroupManager extends CommonInc {

    String group_id="";
		String[] del_users = null, add_users=null;
		List<User> group_managers = null;
		List<User> other_users = null;
		static final long serialVersionUID = 1110L;
		static Logger logger = Logger.getLogger(GroupManager.class);
		//
		public GroupManager(){
				super();
		}
		public GroupManager(boolean deb){
				//
				// initialize
				//
				super(deb);
    }
		public GroupManager(String val){
				//
				setGroup_id(val);
    }
		public GroupManager(boolean deb, String val){
				//
				// initialize
				//
				super(deb);
				setGroup_id(val);
    }


		public String getGroup_id(){
				return group_id;
		}

		public void setGroup_id(String val){
				if(val != null && !val.equals("-1"))
						group_id = val;
		}

		public void setDel_users(String[] vals){
				if(vals != null)
						del_users = vals;
		}
		public void setAdd_users(String[] vals){
				if(vals != null)
						add_users = vals;
		}
		public boolean hasGroupManagers(){
				getGroupManagers();
				return group_managers != null && group_managers.size() > 0;
		}
		public boolean hasOtherUsers(){
				//
				// we need to pick a group to show availabe users
				//
				if(group_id.equals("")) return false;
				getOtherUsers();
				return other_users != null && other_users.size() > 0;
		}		
		public List<User> getGroupManagers(){
				if(group_managers == null && !group_id.equals("")){
						UserList ul = new UserList();
						ul.setGroupManagers_id(group_id);
						String back = ul.find();
						if(back.equals("")){
								group_managers = ul.getUsers();
						}
				}
				return group_managers;
		}
		public List<User> getOtherUsers(){
				if(other_users == null){
						UserList ul = new UserList();
						ul.setRole("Admin"); // only admin users can manage group
						ul.setActiveOnly();
						if(!group_id.equals("")){
								ul.setExcludeGroupManagers_id(group_id);
						}
						String back = ul.find();
						if(back.equals("")){
								other_users = ul.getUsers();
						}
				}
				return other_users;
		}		
		//
    public String doAdd(){
				String msg = "";
				Connection con = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;		
				String qq = " insert into group_managers values(0,?,?) "; // group_id, user_id
				if(add_users == null || add_users.length < 1){
						msg = "users not set ";
						addError(msg);
						return msg;
				}
				else if(group_id.equals("")){
						msg = "group not set ";
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
						for(String user_id:add_users){
								stmt.setString(1, group_id);																
								stmt.setString(2, user_id);
								stmt.executeUpdate();
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
    public String doRemove(){
				String msg = "";
				Connection con = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;		
				String qq = " delete from group_managers where user_id=? and group_id=? "; // user_id, group_id
				if(group_id.equals("")){
						msg = "group not set ";
						addError(msg);
						return msg;
				}
				else if(del_users == null || del_users.length < 1){
						msg = "no user set to be deleted ";
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
						for(String user_id:del_users){
								stmt.setString(1, user_id);
								stmt.setString(2, group_id);								
								stmt.executeUpdate();
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
