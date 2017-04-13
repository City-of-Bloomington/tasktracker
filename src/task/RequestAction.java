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

public class RequestAction extends TopAction{

		static final long serialVersionUID = 1900L;	
		static Logger logger = Logger.getLogger(RequestAction.class);
		Request request = null;
		String group_id = "";
		Group group = null;
		List<Request> requests = null;
		List<Type> types = null;
		List<Group> groups = null; // all request will be based on a group selected
		List<User> users = null;
		List<Location> locations = null;
		String requestsTitle = "Current requests";
		String tasksTitle = "Request Tasks";
		String logsTitle = "Request History";		
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
						if(user.hasOneGroupOnly()){
								group = user.getGroup();
								group_id = group.getId();
						}
				}
				if(action.equals("Save")){
						request.setUser_id(user.getId());
						back = request.doSave();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								System.err.println(" active mail "+activeMail);								
								if(activeMail){
										System.err.println(" request check email");
										back = request.checkEmailSendNeed("New", user, url);
										if(!back.equals("")){
												addActionError(back);
										}
								}
								if(back.equals(""))
										addActionMessage("Added Successfully");
						}
				}				
				else if(action.startsWith("Save")){
						request.setUser_id(user.getId());
						back = request.doUpdate();
						group_id = request.getGroup_id();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								if(activeMail){
										request.checkEmailSendNeed("Updated", user, url);
								}
								addActionMessage("Updated Successfully");
						}
				}
				else if(action.startsWith("Cancel")){
						request.setUser_id(user.getId());
						back = request.updateStatus("Cancelled");
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								request.doSelect();
								addActionMessage("Cancelled Successfully");
								ret="view";
						}
				}
				else if(action.startsWith("Open")){
						getRequest();
						request.setUser_id(user.getId());
						back = request.updateStatus("Active");
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								request.doSelect();
						}
				}				
				else{		
						getRequest();
						if(!id.equals("")){
								back = request.doSelect();
								group_id = request.getGroup_id();
								if(!back.equals("")){
										addActionError(back);
								}
								if(!request.canBeChanged()){
										ret = "view";
								}
						}
				}
				return ret;
		}
		public Request getRequest(){ 
				if(request == null){
						request = new Request();
						request.setId(id);
				}		
				return request;
		}

		@Override
		public String getId(){
				if(id.equals("") && request != null){
						id = request.getId();
				}
				return id;
		}
				
		public String getGroup_id(){
				if(group_id.equals("") && request != null){
						group_id = request.getGroup_id();
				}
				return group_id;
		}		
		public void setRequest(Request val){
				if(val != null){
						request = val;
				}
		}

		public void setGroup(Group val){
				if(val != null){
						group = val;
				}
		}		
		public Group getGroup(){
				if(group == null && !group_id.equals("")){
						Group one = new Group(debug, group_id);
						String back = one.doSelect();
						if(back.equals("")){
								group = one;
						}
				}
				return group;
		}				

		public void setGroup_id(String val){
				if(val != null){
						group_id = val;
				}
		}		
		public String getRequestsTitle(){
				return requestsTitle;
		}
		public String getTasksTitle(){
				return tasksTitle;
		}
		public String getLogsTitle(){
				return logsTitle;
		}		
		public void setAction2(String val){
				if(val != null && !val.equals(""))		
						action = val;
		}
		public List<Request> getRequests(){
				if(requests == null){
						RequestList tl = new RequestList(debug);
						String back = tl.find();
						if(back.equals("")){
								List<Request> ones = tl.getRequests();
								if(ones != null && ones.size() > 0){
										requests = ones;
								}
						}
				}
				return requests;
		}
		public List<Type> getTypes(){
				if(types == null){
						TypeList tl = new TypeList(debug, null,"types");
						String back = tl.find();
						if(back.equals("")){
								List<Type> ones = tl.getTypes();
								System.err.println(" size "+ones.size());
								if(ones != null && ones.size() > 0){
										types = ones;
								}
						}
				}
				return types;
		}		
		public List<Group> getGroups(){
				if(groups == null){
						GroupList tl = new GroupList(debug);
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
		public List<Location> getLocations(){
				if(locations == null){
						LocationList tl = new LocationList(debug);
						String back = tl.find();
						if(back.equals("")){
								List<Location> ones = tl.getLocations();
								if(ones != null && ones.size() > 0){
										locations = ones;
								}
						}
				}
				return locations;
		}		
		public List<User> getUsers(){
				if(users == null){
						UserList ul = new UserList(debug);
						ul.setExclude_dept_id("1");// do not include ITS 
						getGroup_id();
						if(!group_id.equals("")){
								ul.setGroup_id(group_id);
						}
						getId();
						if(!id.equals("")){ // request id
								ul.setRequest_id(id);
								ul.excludeCurrentRequestAssignees();
						}
						String back = ul.find();
						if(back.equals("")){
								List<User> ones = ul.getUsers();
								users = ones;
						}
				}
				return users;
		}
		public boolean haveUsers(){
				getUsers();
				return users != null && users.size() > 0;
		}
		public boolean haveTypes(){
				getTypes();
				return types != null && types.size() > 0;
		}		
		
}





































