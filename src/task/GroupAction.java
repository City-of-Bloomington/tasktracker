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
import org.apache.log4j.Logger;

public class GroupAction extends TopAction{

		static final long serialVersionUID = 900L;	
		static Logger logger = Logger.getLogger(GroupAction.class);
		//
		User user = null;
		Group group = null;
		List<Group> groups = null;
		String groupsTitle = "Current groups";
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
				if(action.equals("Save")){
						back = group.doSave();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Added Successfully");
						}
				}				
				else if(action.startsWith("Save")){ 
						back = group.doUpdate();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Updated Successfully");
						}
				}
				else{		
						getGroup();
						if(!id.equals("")){
								back = group.doSelect();
								if(!back.equals("")){
										addActionError(back);
								}								
						}
				}
				return ret;
		}
		public Group getGroup(){ 
				if(group == null){
						group = new Group(debug);
						group.setId(id);
				}		
				return group;
		}

		public void setGroup(Group val){
				if(val != null){
						group = val;
				}
		}

		public String getGroupsTitle(){
				return groupsTitle;
		}
		public void setAction2(String val){
				if(val != null && !val.equals(""))		
						action = val;
		}
		public List<Group> getGroups(){
				if(groups == null){
						GroupList tl = new GroupList(debug);
						// tl.setActiveOnly();
						String back = tl.find();
						if(back.equals("")){
								List<Group> ones = tl.getGroups();
								if(ones != null && ones.size() > 0){
										groups = ones;
								}
						}
				}
				return groups;
		}

}





































