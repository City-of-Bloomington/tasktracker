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

public class RequestsAction extends TopAction{

		static final long serialVersionUID = 2220L;	
		static Logger logger = LogManager.getLogger(RequestsAction.class);
		//
		boolean active_only = true;
		boolean unassigned = false;
		String request_id = "";
		String assigned_user_id = "";
		List<Request> requests = null;
		String requestsTitle = "Current Active Requests";
		String requestsHeader = "Active Requests";
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
				getRequests();
				return ret;
		}

		public String getRequestsTitle(){
				return requestsTitle;
		}
		public String getRequestsHeader(){
				return requestsHeader;
		}		
		public void setAction2(String val){
				if(val != null && !val.equals(""))		
						action = val;
		}
		public void setAssigned_user_id(String val){
				if(val != null && !val.equals(""))		
						assigned_user_id = val;
		}		
		public void setActiveOnly(boolean val){
				active_only = val;
		}
		public void setUnassigned(boolean val){
				unassigned = val;
		}
		public List<Request> getRequests(){
				if(requests == null){
						RequestList tl = new RequestList(debug);
						if(!assigned_user_id.equals("")){
								tl.setAssigned_user_id(assigned_user_id);
								tl.setActiveOnly();
						}
						else if(unassigned){
								tl.setStatus("Unassigned");
						}
						else{
								tl.setActiveOnly();
						}
						String back = tl.find();
						if(back.equals("")){
								List<Request> ones = tl.getRequests();
								if(ones != null && ones.size() > 0){
										requests = ones;
								}
						}
						if(!assigned_user_id.equals("")){
								if(requests != null && requests.size() > 0){
										requestsTitle = "Current active requests assigned to you";
								}
								else{
										requestsTitle = "No active requests assigned to you";
								}
						}
						else if(unassigned){
								requestsHeader= "Unassigned Requests";
								if(requests != null && requests.size() > 0){
										requestsTitle = "Current unassinged requests";
								}
								else{
										requestsTitle = "No unassigned requests found";
								}
						}
						else{
								if(requests == null){
										requestsTitle = "No active requests available";
								}
						}
				}
				return requests;
		}
}





































