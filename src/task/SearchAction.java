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

public class SearchAction extends TopAction{

		static final long serialVersionUID = 2220L;	
		static Logger logger = Logger.getLogger(SearchAction.class);
		//
		String assigned_user_id = "", group_id="";
		boolean active_only = false;
		List<Request> requests = null;
		RequestList reqlst = null;
		List<Type> types = null;
		List<Group> groups = null;
		List<Location> locations = null;		
		List<User> users = null;
		String requestsTitle = "";
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
				// reqlst = new RequestList(debug);
				if(action.equals("Search")){
						getReqlst();
						if(!assigned_user_id.equals("")){
								reqlst.setAssigned_user_id(assigned_user_id);
						}
						if(active_only){
								reqlst.setActiveOnly();
						}
						back = reqlst.find();
						if(!back.equals("")){
								addActionError(back);
						}								
						else {
								requests = reqlst.getRequests();
								if(requests == null || requests.size() == 0){
										addActionMessage(" No match found ");
								}
								else{
										addActionMessage(" Found "+requests.size()+" requests");
								}
						}
				}
				else if(action.equals("Find")){
						if(assigned_user_id.equals("")){
								addActionError(" No user selected");
						}
						else{
								getReqlst();
								if(!assigned_user_id.equals("")){
										reqlst.setAssigned_user_id(assigned_user_id);
								}
								back = reqlst.find();
								if(!back.equals("")){
										addActionError(back);
								}								
								else {
										String fullname = "";
										User assignedUser = new User(debug, assigned_user_id);
										back = assignedUser.doSelect();
										if(back.equals("")){
												fullname = assignedUser.getFullname();
										}
										requests = reqlst.getRequests();
										if(requests == null || requests.size() == 0){
												addActionMessage(" No requests assigned to "+fullname+" found");
										}
										else{
												addActionMessage(" Found "+requests.size()+" active requests assigned to "+fullname);
												requestsTitle=" active requests assigned to "+fullname;

										}
								}
								
						}
				}
				return ret;
		}

		public String getRequestsTitle(){
				return requestsTitle;
		}
		public RequestList getReqlst(){
				if(reqlst == null){
						reqlst = new RequestList(debug);
				}
				return reqlst;
		}
		public void setActiveOnly(String val){
				requestsTitle = "Active Requests ";
				active_only = true;
		}
		public void setAssigned_user_id(String val){
				if(val != null && !val.equals("")){		
						assigned_user_id = val;
						if(requestsTitle.equals("")){
								requestsTitle = "Requests ";
						}
						requestsTitle += " Assigned to you ";
				}
		}		
		public void setAction2(String val){
				if(val != null && !val.equals(""))		
						action = val;
		}
		public List<Request> getRequests(){
				return requests;
		}
		public List<Type> getTypes(){
				if(types == null){
						TypeList tl = new TypeList(debug, null,"types");
						String back = tl.find();
						if(back.equals("")){
								List<Type> ones = tl.getTypes();
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
						if(!group_id.equals("")){
								ul.setGroup_id(group_id);
						}
						String back = ul.find();
						if(back.equals("")){
								List<User> ones = ul.getUsers();
								users = ones;
						}
				}
				return users;
		}
		
}





































