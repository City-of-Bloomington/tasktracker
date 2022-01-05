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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AssignmentList extends CommonInc{

		static Logger logger = LogManager.getLogger(AssignmentList.class);
		static final long serialVersionUID = 200L;
		String name = "", id="", limit="limit 30";
		String request_id = "", user_id="", group_id="";
		List<Assignment> assignments = null;
		public AssignmentList(){
				super();
		}
		public AssignmentList(boolean deb){
				super(deb);
		}		
		public AssignmentList(boolean deb, String val){
				super(deb);
				setRequest_id(val);
		}
		public List<Assignment> getAssignments(){
				return assignments;
		}
		public void setRequest_id(String val){
				if(val != null)
						request_id = val;
		}
		public void setId(String val){
				if(val != null)
						id = val;
		}
		public void setUser_id(String val){
				if(val != null)
					 user_id = val;
		}
		public void setGroup_id(String val){
				if(val != null)
					 group_id = val;
		}		
		public String getId(){
				return id;
		}
		public String getRequest_id(){
				return request_id;
		}
		public String getUser_id(){
				return user_id;
		}
		public String getGroup_id(){
				return group_id;
		}		

		public void setNoLimit(){
				limit = "";
		}
		public String find(){
		
				String back = "";
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				Connection con = Helper.getConnection();
				String qq = "select u.id,u.user_id,u.request_id,date_format(u.date,'%m/%d/%Y') from assignments u ", qw ="";
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				if(!id.equals("")){
						qw += " u.id = ? ";
				}
				else{
						if(!request_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " u.request_id=? ";
						}
						if(!user_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " u.user_id=? ";
						}
						if(!group_id.equals("")){
								qq += ", group_users g ";
								if(!qw.equals("")) qw += " and ";
								qw += " u.user_id=g.user_id and g.group_id=? ";
						}						
				}
				if(!qw.equals("")){
						qq += " where "+qw;
				}
				qq += " order by u.id ";
				
				if(!limit.equals("")){
						qq += limit;
				}
				System.err.println(qq);
				try{
						if(debug){
								logger.debug(qq);
						}
						pstmt = con.prepareStatement(qq);
						if(!id.equals("")){
								pstmt.setString(1, id);
						}
						else {
								int jj=1;
								if(!request_id.equals("")){
										pstmt.setString(jj++,request_id);		
								}
								if(!user_id.equals("")){
										pstmt.setString(jj++,user_id);		
								}								
								if(!group_id.equals("")){
										pstmt.setString(jj++,group_id);		
								}
						}
						rs = pstmt.executeQuery();
						while(rs.next()){
								if(assignments == null)
										assignments = new ArrayList<>();
								Assignment one =
										new Assignment(debug,
																	 rs.getString(1),
																 rs.getString(2),
																 rs.getString(3),
																 rs.getString(4));
								assignments.add(one);
						}
				}
				catch(Exception ex){
						back += ex+" : "+qq;
						logger.error(back);
						addError(back);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return back;
		}
}






















































