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
import javax.naming.*;
import javax.naming.directory.*;
import org.apache.log4j.Logger;


public class EmpList extends CommonInc{

		static Logger logger = Logger.getLogger(EmpList.class);
		static final long serialVersionUID = 3100L;
		static EnvBean bean = null;
		String name = "";
		
		List<Employee> emps = null;
		public EmpList(){
				super();
		}
		public EmpList(boolean deb, EnvBean val){
				super(deb);
				setEnvBean(val);
		}
		public EmpList(boolean deb, EnvBean val, String val2){
				super(deb);
				setEnvBean(val);
				setName(val2);
		}		
		public List<Employee> getEmps(){
				return emps;
		}
		public void setName(String val){
				if(val != null)
						name = val;
		}
		public void setEnvBean(EnvBean val){
				if(val != null)
						bean = val;
		}		
		public String getName(){
				return name;
		}

		boolean connectToServer(Hashtable env){

				if(env != null && bean != null){
						env.put(Context.INITIAL_CONTEXT_FACTORY, 
										"com.sun.jndi.ldap.LdapCtxFactory");
						env.put(Context.PROVIDER_URL, bean.getUrl());
						env.put(Context.SECURITY_AUTHENTICATION, "simple"); 
						env.put(Context.SECURITY_PRINCIPAL, bean.getPrinciple());
						env.put(Context.SECURITY_CREDENTIALS, bean.getPassword());
				}
				else{
						return false;
				}
				try {
						DirContext ctx = new InitialDirContext(env);
				} catch (NamingException ex) {
						System.err.println(" ldap "+ex);
						return false;
				}
				return true;
    }		
		public String find(){
				Hashtable env = new Hashtable(11);
				String back = "", fullName="";
				Employee emp = null;
				if (!connectToServer(env)){
						System.err.println("Unable to connect to ldap");
						return null;
				}
				try{
						//
						DirContext ctx = new InitialDirContext(env);
						SearchControls ctls = new SearchControls();
						String[] attrIDs = {"givenName",
																"department",
																"telephoneNumber",
																"mail",
																"cn",
																"sn",
																"businessCategory",
																"title"};
						//
						ctls.setReturningAttributes(attrIDs);
						ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
						String filter = "(cn="+name+"*)";
						NamingEnumeration answer = ctx.search("", filter, ctls);
						while(answer.hasMore()){
								//
								emp = new Employee();
								SearchResult sr = (SearchResult)(answer.next());
								Attributes atts = sr.getAttributes();
								//
								Attribute cn = (Attribute)(atts.get("cn"));
								if (cn != null){
										String userid = cn.get().toString();
										emp.setUsername(userid);
								}
								Attribute givenname = (Attribute)(atts.get("givenName"));
								if (givenname != null){
										fullName = givenname.get().toString();
								}
								Attribute sn = (Attribute)(atts.get("sn"));
								if (sn != null){
										fullName += " "+sn.get().toString();
								}
								emp.setFullname(fullName);				
								Attribute department = 
										(Attribute)(atts.get("department"));
								if (department != null){
										String dept = department.get().toString();
										emp.setDept(dept);
								}
								Attribute tele = (Attribute)(atts.get("telephoneNumber"));
								if (tele != null){
										String phone = tele.get().toString();
										emp.setPhone(phone);
								}
								Attribute cat = (Attribute)(atts.get("businessCategory"));
								if (cat != null){
										String busCat = cat.get().toString();
										emp.setDivision(busCat);
								}
								Attribute title = (Attribute)(atts.get("title"));
								if (title != null){
										String post = title.get().toString();
										// user.setTitle(post);
								}
								if(emps == null){
										emps = new ArrayList<>();
								}
								emps.add(emp);
						}
				}
				catch(Exception ex){
						logger.error(ex);
				}
				return back;
		}
}






















































