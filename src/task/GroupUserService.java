package task;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class GroupUserService extends HttpServlet{

    String url="";
    boolean debug = false;
		static EnvBean envBean = null;
		static final long serialVersionUID = 1250L;
		static Logger logger = Logger.getLogger(GroupUserService.class);


    public void doGet(HttpServletRequest req,
											HttpServletResponse res)
				throws ServletException, IOException {
				doPost(req,res);
    }

    /**
     * Generates the Group form and processes view, add, update and delete operations.
     *
     * @param req The request input stream
     * @param res The response output stream
     */
    public void doPost(HttpServletRequest req,
											 HttpServletResponse res)
				throws ServletException, IOException {

				String id = "";

				//
				String message="", action="";
				res.setContentType("application/json");
				PrintWriter out = res.getWriter();
				String name, value;
				String group_id="";
				boolean success = true;
				HttpSession session = null;
				if(url.equals("")){
						url    = getServletContext().getInitParameter("url");
						//
						String str = getServletContext().getInitParameter("debug");
						if(str.equals("true")) debug = true;
				}
				Enumeration<String> values = req.getParameterNames();
				String [] vals = null;
				while (values.hasMoreElements()){
						name = values.nextElement().trim();
						vals = req.getParameterValues(name);
						value = vals[vals.length-1].trim();
						if (name.equals("group_id")) { 
								group_id = value;
						}
						else{
								System.err.println(name+" "+value);
						}
				}
				UserList userList =  null;
				List<User> users = null;
				if(!group_id.equals("")){
						//
						userList = new UserList(debug);
						userList.setGroup_id(group_id);
						String back = userList.find();
						if(back.equals("")){
								users = userList.getUsers();
						}
				}
				if(users != null && users.size() > 0){
						String json = writeJson(users);
						out.println(json);
				}
				else{
						out.println("[]"); // empty array
				}
				out.flush();
				out.close();
    }

		/**
		 * Creates a JSON array string for a list of users
		 *
		 * Example output:
     * [
     *  {"value":"Walid Sibo",
     *    "id":"sibow",
     *    "dept":"ITS"
     *    },
     *  {"value":"schertza",
     *  "id":"Alan Schertz",
     *  "dept":"ITS"
     *  }
     * ]
		 *
		 * @param users The users
		 * @param type unused
		 * @return The json string
		 */
		String writeJson(List<User> users){
				String json="";
				for(User one:users){
						if(!json.equals("")) json += ",";
						json += "{\"id\":\""+one.getId()+"\",\"fullname\":\""+one.getFullname()+"\"}";
				}
				json = "["+json+"]";
				// System.err.println("json "+json);
				return json;
		}
}
