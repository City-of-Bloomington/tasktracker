package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReportAction extends TopAction{

		static final long serialVersionUID = 97L;	
   
		static Logger logger = LogManager.getLogger(ReportAction.class);
		Report report = null;
		List<Group> groups = null;
		List<Type> types = null;
		List<Location> locations = null;
		public String execute(){
				String ret = INPUT;            
				if(action.equals("Submit")){
						ret = SUCCESS;
						String back = report.find();
						if(!back.equals("")){
								addActionError(back);
								ret = INPUT;
						}
				}
				return ret;
		}			 
		public Report getReport(){
				if(report == null){
						report = new Report(debug);
				}
				return report;
		}
		public void setReport(Report val){
				if(val != null)
						report = val;
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

}





































