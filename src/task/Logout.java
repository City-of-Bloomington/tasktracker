package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logout extends HttpServlet{

		static Logger logger = LogManager.getLogger(Logout.class);
		static final long serialVersionUID = 1700L;		
    String url = "", url4="", cas_url="";

    public void doGet(HttpServletRequest req, 
					  HttpServletResponse res) 
		throws ServletException, IOException{

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		Enumeration values = req.getParameterNames();
		String name= "";
		String value = "";
		if(url == null || url.equals("")){
			url = getServletContext().getInitParameter("url");
			cas_url = getServletContext().getInitParameter("cas_url");
		}
		HttpSession session = null;
		session = req.getSession(false);
		if(session != null){
			session.invalidate();
		}
		String str = cas_url;
		res.sendRedirect(str);
		return;
		/*
		out.println("<HTML><HEAD><TITLE>Log out</TITLE>");
		out.println("<body><center>You have successfully logged out."+
					"<p><a href="+ url + "Login> "+
					"Login again</a>");
		out.println("</center></body></html>");
		out.close();
		*/

		
    }

}






















































