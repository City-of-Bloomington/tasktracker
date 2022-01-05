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

public class LocationList extends CommonInc{

		static Logger logger = LogManager.getLogger(LocationList.class);
		static final long serialVersionUID = 1000L;
		String user_id="", name="";
		boolean active_only = false; // all
		List<Location> locations = null;
	
		public LocationList(){
		}
		public LocationList(boolean deb){
				super(deb);
		}
		public LocationList(boolean deb, String val){
				super(deb);
				setName(val);
		}
		public List<Location> getLocations(){
				return locations;
		}
		
		public void setName(String val){
				if(val != null)
						name = val;
		}
		public void setUser_id(String val){
				if(val != null)
						user_id = val;
		}		
		public void setActiveOnly(){
				active_only = true;
		}

		public String find(){
		
				String back = "";
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				Connection con = Helper.getConnection();
				String qq = "select t.id,t.name,inactive from locations t ";
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				String qw = "";
				try{
						if(!name.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " t.name like ? ";
						}
						if(active_only){
								if(!qw.equals("")) qw += " and ";
								qw += " t.inactive is null ";
						}
						/*
						if(!user_id.equals("")){
								qq += ", location_users u ";
								if(!qw.equals("")) qw += " and ";
								qw += " t.id = u.location_id and u.user_id=? ";
								
						}
						*/
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
						if(!user_id.equals("")){
								pstmt.setString(jj++, user_id);
						}						
						rs = pstmt.executeQuery();
						if(locations == null)
								locations = new ArrayList<Location>();
						while(rs.next()){
								Location one =
										new Location(debug,
														 rs.getString(1),
														 rs.getString(2),
														 rs.getString(3)!=null && !rs.getString(3).equals(""));
								if(!locations.contains(one))
										locations.add(one);
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






















































