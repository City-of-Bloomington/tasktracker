package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import javax.naming.*;
import javax.naming.directory.*;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;

public class CommonInc{

		boolean debug = false;
		static final long serialVersionUID = 300L;			
		static Logger logger = Logger.getLogger(CommonInc.class);
   	String message = "";
		List<String> errors = null;
		//
		public CommonInc(){
		}
		public CommonInc(boolean deb){
				debug = deb;
		}

		public String getMessage(){
				return message;
		}
		public boolean hasMessage(){
				return !message.equals("");
		}
		public boolean hasErrors(){
				return (errors != null && errors.size() > 0);
		}
		public List<String> getErrors(){
				return errors;
		}
		void addError(String val){
				if(errors == null)
						errors = new ArrayList<>();
				errors.add(val);
		}

}
