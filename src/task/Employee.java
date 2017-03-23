package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

public class Employee extends CommonInc implements java.io.Serializable{

    String username="", fullname="", 
				dept="", division="", title="", phone="";
		static final long serialVersionUID = 1400L;
		static Logger logger = Logger.getLogger(Employee.class);
		static Map<String, String> rolesMap = null;
		//
		public Employee(){
				super();
		}
		public Employee(boolean deb){
				//
				// initialize
				//
				super(deb);
    }
		public Employee(boolean deb, String val){
				//
				// initialize
				//
				super(deb);
				setUsername(val);
    }
		public Employee(boolean deb,
										String val,
										String val2,
										String val3,
										String val4,
										String val5
										){
				//
				// initialize
				//
				debug = deb;
				setUsername(val);
				setFullname(val2);
				setPhone(val3);
				setDept(val4);
				setDivision(val5);
				
    }

		public String getUsername(){
				return username;
		}		
		public String getPhone(){
				return phone;
		}
		public String getDept(){
				return dept;
		}
		public String getDivision(){
				return division;
		}				
		public String getFullname(){
				return fullname;
		}
		public String getDept_division(){
				String ret = dept;
				if(!division.equals("")){
						ret += " ("+division+")";
				}
				return ret;
		}

		public void setPhone(String val){
				if(val != null)
						phone = val;
		}
		public void setUsername(String val){
				if(val != null)
						username = val;
		}		
		public void setDept(String val){
				if(val != null)
						dept = val;
		}
		public void setDivision(String val){
				if(val != null)
						division = val;
		}		
		public void setFullname(String val){
				if(val != null)
						fullname = val;
		}
		//
    //
    public String toString(){
				return fullname;
    }
		public boolean equals(Object obj){
				if(obj instanceof Employee){
						Employee one =(Employee)obj;
						return username.equals(one.getUsername());
				}
				return false;				
		}
		public int hashCode(){
				int seed = 37;
				if(!username.equals("")){
						seed += username.hashCode();
				}
				return seed;
		}		

}
