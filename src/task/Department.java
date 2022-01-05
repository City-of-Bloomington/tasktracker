package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */

import java.sql.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Department extends Type{

		static final long serialVersionUID = 400L;			
		static Logger logger = LogManager.getLogger(Department.class);
		List<Division> divisions = null;

    public Department(){
				setTable_name("departments");
    }
    public Department(boolean deb, String val, String val2){
				super(deb, val, val2);
				setTable_name("departments");
    }		
    public Department(boolean deb, String val, String val2, boolean val3){
				super(deb, val, val2, val3);
    }
		public List<Division> getDvisions(){
				if(divisions == null && !id.equals("")){
						DivisionList dl = new DivisionList(debug, id);
						String back = dl.find();
						if(back.equals("")){
								divisions = dl.getDivisions();
						}
				}
				return divisions;
		}

}
