package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.SimpleDateFormat;
import javax.sql.*;
import org.apache.log4j.Logger;


public class RequestList extends CommonInc{

		static Logger logger = Logger.getLogger(RequestList.class);
		
		static final long serialVersionUID = 2000L;
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String id="", limit="limit 30";
		String group_id = "", division="", dept="", type_id="", status="",
				date_from="", date_to="", which_date = "r.date", location="",
				content="", // summary or description
				username="", phone="", assigned_user_id="";
		boolean active_only = false;
		List<Request> requests = null;
		public RequestList(){
				super();
		}
		public RequestList(boolean deb){
				super(deb);
		}		
		public List<Request> getRequests(){
				return requests;
		}
		public void setContent(String val){
				if(val != null)
						content = val;
		}
		public void setId(String val){
				if(val != null)
						id = val;
		}
		public void setGroup_id(String val){
				if(val != null && !val.equals("-1"))
						group_id = val;
		}
		public void setAssigned_user_id(String val){
				if(val != null && !val.equals("-1"))
						assigned_user_id = val;
		}		
		public void setDept(String val){
				if(val != null)
						dept = val;
		}
		public void setDivision(String val){
				if(val != null)
						division = val;
		}		
		public void setType_id(String val){
				if(val != null && !val.equals("-1"))
						type_id = val;
		}
		public void setStatus(String val){
				if(val != null && !val.equals("-1"))
						status = val;
		}		
		public void setDate_from(String val){
				if(val != null){
						date_from = val;
				}
		}
		public void setDate_to(String val){
				if(val != null){
						date_to = val;
				}
		}		
		public String getId(){
				return id;
		}
		public String getGroup_id(){
				if(group_id.equals("")){
						return "-1";
				}
				return group_id;
		}
		public String getAssigned_user_id(){
				if(assigned_user_id.equals("")){
						return "-1";
				}
				return assigned_user_id;
		}		
		public String getType_id(){
				if(type_id.equals("")){
						return "-1";
				}
				return type_id;
		}		
		public String getDept(){
				return dept;
		}
		public String getStatus(){
				if(status.equals("")){
						return "-1";
				}
				return status;
		}		
		public String getDivision(){
				return division;
		}		
		public String getContent(){
				return content;
		}
		public String getDate_from(){
				return date_from;
		}
		public String getDate_to(){
				return date_to;
		}		
		public void setNoLimit(){
				limit = "";
		}
		public void setActiveOnly(){
				active_only = true;
		}
		public String find(){
		
				String back = "", qw="";
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				Connection con = Helper.getConnection();
				String qq = "select r.id,"+
						"r.type_id,"+
						"r.status,"+
						"date_format(r.date,'%m/%d/%Y'), "+   // 23
						"date_format(r.date,'%h:%i %p'), "+   // time h,l=hour, i=minute
						"r.username,"+
						"r.fullname,"+
						"r.phone,"+
						"r.dept,"+
						"r.division,"+
						"r.group_id,"+
						"r.location,"+ 						
						"r.summary,"+
						"r.description,"+
						"date_format(r.due_date,'%m/%d/%Y') "+						
						"from requests r";
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				if(!id.equals("")){
						qw += " r.id = ? ";
				}
				else{
						if(!content.equals("")){
								qw += " (r.summary like ? or r.description like ?) ";
						}
						if(!type_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.type_id = ? ";
						}						
						if(!status.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.status=? ";
						}
						if(!dept.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.dept like ? ";
						}
						if(!division.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.division like ? ";
						}						
						if(!group_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " r.group_id=?";								
						}
						if(!date_from.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += which_date+" >= ?";										
						}
						if(!date_to.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += which_date+" <= ?";										
						}
						if(!assigned_user_id.equals("")){
								if(!qw.equals("")) qw += " and ";
								qq += ", assignments a ";
								qw += " a.user_id = ? and a.request_id=r.id ";
						}
						if(active_only){
								if(!qw.equals("")) qw += " and ";
								qw += "(r.status = 'Active' || r.status ='Unassigned')";				
						}
				}
				if(!qw.equals("")){
						qq += " where "+qw;
				}
				qq += " order by r.id desc "+limit;
				try{
						System.err.println(qq);
						if(debug){
								logger.debug(qq);
						}
						pstmt = con.prepareStatement(qq);
						if(!id.equals("")){
								pstmt.setString(1, id);
						}
						else {
								int jj=1;
								if(!content.equals("")){
										pstmt.setString(jj++,"%"+content+"%");
										pstmt.setString(jj++,"%"+content+"%");
								}
								if(!type_id.equals("")){
										pstmt.setString(jj++,type_id);		
								}
								if(!status.equals("")){
										pstmt.setString(jj++,status);		
								}
								if(!dept.equals("")){
										pstmt.setString(jj++,dept);		
								}
								if(!division.equals("")){
										pstmt.setString(jj++,division);		
								}								
								if(!group_id.equals("")){
										pstmt.setString(jj++,group_id);		
								}
								if(!date_from.equals("")){
										java.util.Date date_tmp = df.parse(date_from);
										pstmt.setDate(jj++, new java.sql.Date(date_tmp.getTime()));
								}
								if(!date_to.equals("")){
										java.util.Date date_tmp = df.parse(date_to);
										pstmt.setDate(jj++, new java.sql.Date(date_tmp.getTime()));
								}
								if(!assigned_user_id.equals("")){
										pstmt.setString(jj++,assigned_user_id);		
								}
						}
						rs = pstmt.executeQuery();
						while(rs.next()){
								if(requests == null)
										requests = new ArrayList<>();
								Request one =
										new Request(debug,
																rs.getString(1),
																rs.getString(2),
																rs.getString(3),
																rs.getString(4),
																rs.getString(5),
																rs.getString(6),
																rs.getString(7),
																rs.getString(8),
																rs.getString(9),
																rs.getString(10),
																rs.getString(11),
																rs.getString(12),
																rs.getString(13),
																rs.getString(14),
																rs.getString(15)
																);
								requests.add(one);
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






















































