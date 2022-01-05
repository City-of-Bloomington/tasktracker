package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Location extends Type{

		static final long serialVersionUID = 800L;	
		static Logger logger = LogManager.getLogger(Location.class);
		List<User> users = null;
		//
		
		public Location(){
				super();
				setTable_name("locations");
		}
		public Location(boolean deb){
				//
				// initialize
				//
				super(deb);
				setTable_name("locations");
    }				
		public Location(boolean deb, String val){
				//
				// initialize
				//
				super(deb, val);
				setTable_name("locations");
    }		
		public Location(boolean deb, String val, String val2){
				//
				// initialize
				//
				super(deb, val, val2);
				setTable_name("locations");
    }
		public Location(boolean deb, String val, String val2, boolean val3){
				//
				// initialize
				//
				super(deb, val, val2, val3);
				setTable_name("locations");
    }

}
