package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Login extends HttpServlet{

		static Logger logger = LogManager.getLogger(Login.class);
		static final long serialVersionUID = 1600L;		
    String url="";
		EnvBean bean = null;
		boolean debug = false;

    public void doGet(HttpServletRequest req, 
											HttpServletResponse res) 
				throws ServletException, IOException {
				String message="";
				res.setContentType("text/html");
				PrintWriter out = res.getWriter();
				Enumeration values = req.getParameterNames();
				String name, value, id="";
				while (values.hasMoreElements()) {
						name = ((String)values.nextElement()).trim();
						value = (req.getParameter(name)).trim();
						if (name.equals("id")) {
								id = value;
						}
				}
				if(url.equals("")){
						url  = getServletContext().getInitParameter("url");
						String str = getServletContext().getInitParameter("debug");
						if(str != null && str.equals("true")) debug = true;
						bean = new EnvBean();
						str = getServletContext().getInitParameter("ldap_url");
						if(str != null)
								bean.setUrl(str);
						str = getServletContext().getInitParameter("ldap_principle");
						if(str != null)
								bean.setPrinciple(str);
						str = getServletContext().getInitParameter("ldap_password");
						if(str != null)
								bean.setPassword(str);
				}
				HttpSession session = null;
				boolean userFound = false;
				String userid = req.getRemoteUser(); 
				if(userid != null){
						session = req.getSession(false);			
						String url2 = url;
						User user = getUser(userid);
						if(user != null && session != null){
								userFound = true;
								session.setAttribute("user",user);
								String action = url+"welcome.action";
								// we need this when we send a url to a user
								// when assigned to him/her
								if(!id.equals("")){
										action = url+"request.action?id="+id;
								}
								out.println("<head><title></title><META HTTP-EQUIV=\""+
														"refresh\" CONTENT=\"0; URL=" + action+
														"\"></head>");
								out.println("<body>");
								out.println("</body>");
								out.println("</html>");
								out.flush();
								return;								
						}
				}
				message += " You can not access this system, check with IT or try again later";
				out.println("<head><title></title><body>");
				out.println("<p><font color=red>");
				out.println(message);
				out.println("</p>");
				out.println("</body>");
				out.println("</html>");
				out.flush();	
    }
    User getUser(String username){

				User user = null;
				String message="";
				User one = new User(debug, null, username);
				String back = one.doSelect();
				if(!back.equals("")){
						message += back;
						logger.error(back);
				}
				else{
						user = one;
				}
				return user;
    }
}






















































