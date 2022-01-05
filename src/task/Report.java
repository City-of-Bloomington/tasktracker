package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.*;
import javax.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Report{
	
		static Logger logger = LogManager.getLogger(Report.class);
		static final long serialVersionUID = 51L;
		static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
		String year = "", 
				date_from="",date_to="", type_id="", location_id="",
				group_id="", report_type="stats";
		String period_type="today"; // today, last_seven_days, date_set
		String title = "";
		boolean debug = false, needTotal = false;
		List<ReportRow> rows = new ArrayList<ReportRow>();
		ReportRow columnTitle = null;
		int totalIndex = 2; // DB index for row with 2 items
		String by="type";
		public Report(){

		}
		public Report(boolean deb){
				debug = deb;
		}
		public void setGroup_id(String val){
				if(val != null && !val.equals("-1"))
						group_id = val;
		}
		public void setLocation_id(String val){
				if(val != null && !val.equals("-1"))
						location_id = val;
		}		
		public void setPeriod_type(String val){
				if(val != null)
						period_type = val;
		}
		public void setReport_type(String val){
				if(val != null)
						report_type = val;
		}			
		public void setDate_from(String val){
				if(val != null)
						date_from = val;
		}	
		public void setDate_to(String val){
				if(val != null)
						date_to = val;
		}
		public void setBy(String val){
				if(val != null)
						by = val;
		}
		public void setType_id(String val){
				if(val != null && !val.equals("-1"))
						type_id = val;
		}		
		//
		// getters
		//
		public String getReport_type(){
				return report_type;
		}
		public String getPeriod_type(){
				return period_type;
		}		
		public String getDate_from(){
				return date_from ;
		}	
		public String getDate_to(){
				return date_to ;
		}
		public String getType_id(){
				if(type_id.equals("")) return "-1";
				return type_id ;
		}
		public String getGroup_id(){
				if(group_id.equals("")) return "-1";
				return group_id ;
		}
		public String getLocation_id(){
				if(location_id.equals("")) return "-1";
				return location_id ;
		}		
		public String getTitle(){
				return title;
		}	
		public List<ReportRow> getRows(){
				return rows;
		}
		public ReportRow getColumnTitle(){
				return columnTitle;
		}
		public String getBy(){
				return by ;
		}		
		public String find(){
		
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String msg = "";
				String which_date = "";
				String qq = "", qw="", qg="";
				if(report_type.equals("stats")){
						which_date="r.date";
						columnTitle = new ReportRow(debug);			
						needTotal = true;
						totalIndex = 3;
						columnTitle.setSize(3);
						if(by.equals("type")){
								title = "Request Type Stats ";
								which_date="r.date";
								columnTitle.setRow("Type","Status","Count");
								qq = " select t.name name,r.status status,count(*) cnt from requests r,types t ";
								qw = " r.type_id=t.id ";
								qg = " group by name, status ";								
						}
						else if(by.equals("assign")){
								which_date="a.date";
								title = "Request Assignee Stats ";
								columnTitle.setRow("Assignee","Status","Count");
								qq = " select u.fullname name,r.status status,count(*) cnt from requests r left join assignments a on a.request_id=r.id left join users u on u.id=a.user_id ";
								qg = " group by name, status ";											
						}
						else if(by.equals("group")){
								title = "Request Group Stats ";
								which_date="r.date";
								columnTitle.setRow("Group","Status","Count");
								qq = " select g.name name,r.status status,count(*) cnt from requests r left join groups g on r.group_id=g.id ";
								qg = " group by name, status ";										

						}

				}
				if(period_type.equals("today")){
						title +=" Today ";
						if(!qw.equals("")) qw += " and ";
						qw += " day("+which_date+") = now() ";
				}
				else if(period_type.equals("last_seven_days")){
						date_to = Helper.getToday();
						date_from = Helper.getDateFromToday(-6);
						title += " For Last 7 Days ";
						if(!qw.equals("")) qw += " and ";
						qw += which_date+" >= ? ";
						qw += " and ";
						qw += which_date+" <= ? ";						
				}
				else {
						if(!date_from.equals("")){
								title += " Period "+date_from;
								if(!qw.equals("")) qw += " and ";
								qw += which_date+" >= ? ";
						}
						if(!date_to.equals("")){
								title += " - "+date_to;
								if(!qw.equals("")) qw += " and ";
								qw += which_date+" <= ? ";
						}
				}
				if(!type_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " r.type_id=? ";
				}
				if(!location_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " r.location_id=? ";
				}
				if(!group_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " r.group_id=? ";
				}				
				if(!qw.equals("")){
						qq += " where "+qw;
				}
				qq += qg;
				if(debug)
						logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(period_type.equals("today")){
								// 
						}
						else {
								if(!date_from.equals("")){
										pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_from).getTime()));
								}
								if(!date_to.equals("")){
										pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_to).getTime()));
								}
						}
						if(!type_id.equals("")){
								pstmt.setString(jj++,type_id); 
						}
						if(!location_id.equals("")){
								pstmt.setString(jj++,location_id); 
						}
						if(!group_id.equals("")){
								pstmt.setString(jj++,group_id); 
						}										

						rs = pstmt.executeQuery();
						int total = 0;
						while(rs.next()){
								String str = rs.getString(1);
								if(str == null){
										if(by.equals("type"))
												str = "Unspecified";
										else if(by.equals("assing"))
												str = "Unassigned";
										else if(by.equals("group"))
												str = "Unspecified";												
								}
								ReportRow one = new ReportRow(debug, 3);
								one.setRow(str,
													 rs.getString(2),
													 rs.getString(3)
													 );
								if(needTotal){
										total += rs.getInt(totalIndex);
								}
								rows.add(one);
						}
						if(needTotal){
								ReportRow one = new ReportRow(debug, 3);
								one.setRow("","Total",""+total);
								rows.add(one);
						}			
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}		
				return msg;
		}
	
}






















































