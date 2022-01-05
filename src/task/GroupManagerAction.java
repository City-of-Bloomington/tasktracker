package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GroupManagerAction extends TopAction{

		static final long serialVersionUID = 1210L;	
		static Logger logger = LogManager.getLogger(GroupManagerAction.class);
		//
		String group_id="";
		User user = null;
		List<Type> groups = null;
		GroupManager groupManager = null;
		List<User> other_users = null;
		String groupManagersTitle = "Managers of this group";
		String otherUsersTitle = "Other Users";
		public String execute(){
				String ret = SUCCESS;
				String back = doPrepare();
				if(!back.equals("")){
						try{
								HttpServletResponse res = ServletActionContext.getResponse();
								String str = url+"Login";
								res.sendRedirect(str);
								return super.execute();
						}catch(Exception ex){
								System.err.println(ex);
						}	
				}
				if(action.startsWith("Add")){
						back = groupManager.doAdd();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Added Successfully");
						}
				}				
				else if(action.startsWith("Remove")){ 
						back = groupManager.doRemove();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Removed Successfully");
						}
				}
				else{		
						getGroupManager();
				}
				return ret;
		}
		public GroupManager getGroupManager(){ 
				if(groupManager == null){
						groupManager = new GroupManager();
						if(!group_id.equals(""))
								groupManager.setGroup_id(group_id);
				}		
				return groupManager;
		}

		public void setGroupManager(GroupManager val){
				if(val != null){
						groupManager = val;
				}
		}

		public String getGroupManagersTitle(){
				return groupManagersTitle;
		}
		public String getOtherUsersTitle(){
				return otherUsersTitle;
		}		
		public void setAction2(String val){
				if(val != null && !val.equals(""))		
						action = val;
		}
		public List<Type> getGroups(){
				if(groups == null){
						TypeList tl = new TypeList(debug, null, "groups");
						tl.setActiveOnly();
						String back = tl.find();
						if(back.equals("")){
								List<Type> ones = tl.getTypes();
								if(ones != null && ones.size() > 0){
										groups = ones;
								}
						}
				}
				return groups;
		}
		public void setGroup_id(String val){
				if(val != null && !val.equals(""))		
						group_id = val;
		}
		public String getGroup_id(){
				return group_id;
		}

}





































