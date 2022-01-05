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

public class DivisionList extends CommonInc{

		static Logger logger = LogManager.getLogger(DivisionList.class);
		static final long serialVersionUID = 600L;
		String name = ""; // for service
		String dept_id = "", exclude_dept_id="";
		boolean active_only = false, no_dept = false; // all
		List<Division> divisions = null;
	
		public DivisionList(){
		}
		public DivisionList(boolean deb){
				super(deb);
		}
		public DivisionList(boolean deb, String val){
				super(deb);
				setDept_id(val);
		}
		public List<Division> getDivisions(){
				return divisions;
		}
		
		public void setName(String val){
				if(val != null)
						name = val;
		}
		public void setActiveOnly(){
				active_only = true;
		}
		public void setDept_id(String val){
				if(val != null)
						dept_id = val;
		}
		public void setExclued_dept_id(String val){
				if(val != null)
						exclude_dept_id = val;
		}
		public void setNoDept(){
				no_dept = true;
		}
		public String find(){
		
				String back = "";
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				Connection con = Helper.getConnection();
				String qq = "select d.id,d.name,d.inactive,d.dept_id from divisions d ";
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				String qw = "";

				try{
						if(!name.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " d.name like ? ";
						}
						if(active_only){
								if(!qw.equals("")) qw += " and ";
								qw += " d.inactive is null ";
						}
						if(!dept_id.equals("")){
								qw += " d.dept_id = ? ";
						}
						else if(!exclude_dept_id.equals("")){
								qw += " d.dept_id <> ? ";
						}
						else if(no_dept){
								qw += " d.dept_id is null ";
						}
						if(!qw.equals("")){
								qq += " where "+qw;
						}
						qq += " order by t.name ";
						if(debug){
								logger.debug(qq);
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!name.equals("")){
								pstmt.setString(jj++,"%"+name+"%");
						}
						if(!dept_id.equals("")){
								pstmt.setString(jj++, dept_id);
						}
						else if(!exclude_dept_id.equals("")){
								pstmt.setString(jj++, exclude_dept_id);
						}						
						rs = pstmt.executeQuery();
						if(divisions == null)
								divisions = new ArrayList<Division>();
						while(rs.next()){
								Division one =
										new Division(debug,
																 rs.getString(1),
																 rs.getString(2),
																 rs.getString(3)!=null,
																 rs.getString(4));
								if(!divisions.contains(one))
										divisions.add(one);
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






















































