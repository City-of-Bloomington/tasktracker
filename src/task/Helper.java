package task;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.sql.*;
import java.nio.file.*;
import javax.naming.*;
import javax.naming.directory.*;
import org.apache.log4j.Logger;

@SuppressWarnings("unchecked")
public class Helper{

		static final long serialVersionUID = 1300L;
		static Logger logger = Logger.getLogger(Helper.class);		
		static int c_con = 0;
    public final static String statusArr[] = {
				"Unassigned",
				"Active",
				"Closed",
				"Cancelled"};

    public final static String completeArr[] = {"0","25","50","75","100"};
    //
    // xhtmlHeader.inc
    public final static String xhtmlHeaderInc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n"+
				"<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">";

    //
    // A group of javascript utility functions used in multiple places
    // 
						public final static String jsUtils = " function checkDate(date){ \n"+
				"  if(date.length > 0){                         \n"+
				"  var strArr = date.split('/');                \n"+
				"  if(strArr.length == 3){                      \n"+
				"  if(!checkMonth(strArr[0])){                  \n"+
				" alert('Invalid Month '+strArr[0]);            \n"+
				"     return false;}			        \n"+
				"  if(!checkDay(strArr[1])){                    \n"+
				" alert('Invalid Day '+strArr[1]);              \n"+
				"     return false;}			        \n"+
				"  if(!checkYear(strArr[2])){                    \n"+
				" alert('Invalid Year '+strArr[2]);             \n"+
				"     return false;}		                \n"+
				"	}}                                      \n"+
				"     return true;			        \n"+
				"	}                                       \n"+
				"  function checkMonth(mm) {                    \n"+
				"   if(isNaN(mm)) return false;                 \n"+
				"   if(mm < 1 || mm > 12) return false;         \n"+
				"     return true;			        \n"+
				"	}                                       \n"+
				"  function checkDay(dd) {                      \n"+
				"   if(isNaN(dd)) return false;                 \n"+
				"   if(dd < 1 || dd > 31) return false;         \n"+
				"     return true;			        \n"+
				"	}                                       \n"+
				"  function checkYear(yy) {                     \n"+
				"   if(isNaN(yy)) return false;                 \n"+
				"   if(yy < 1990 || yy > 2100) return false;    \n"+
				"     return true;			        \n"+
				"	}                                       \n"+
				" function moveToNext(item, nextItem, e){       \n"+
				"  var keyCode = \" \";                           \n"+
				" keyCode = (window.Event) ? e.which: e.keyCode;  \n"+
				"  if(keyCode > 47 && keyCode < 58){              \n"+
				"  if(item.value.length > 1){                     \n"+
				"  eval(nextItem.focus());                        \n"+
				"  }}}                                ";
    
    //
    // Non static variables
    //
    boolean debug;
    String [] deptIdArr = null;
    String [] deptArr = null;

    //
    // basic constructor
    public Helper(boolean deb){
				//
				// initialize
				//
				debug = deb;
    }
		final static String getFileExtension(String filename, File file) {
				String ext = "";
				try {
						// name does not include path
						if(filename.indexOf(".") > -1){						
								ext =  filename.substring(filename.lastIndexOf(".") + 1);
						}
						if(ext.equals("")){
								String name = file.getName();
								String pp = file.getAbsolutePath();
								Path path = Paths.get(pp);
								String fileType = Files.probeContentType(path);
								if(fileType != null){
										// application/pdf
										if(fileType.endsWith("pdf")){
												ext="pdf";
										}
										//image/jpeg
										else if(fileType.endsWith("jpeg")){
												ext="jpg";
										}
										//image/gif
										else if(fileType.endsWith("gif")){
												ext="gif";
										}
										//image/bmp
										else if(fileType.endsWith("bmp")){
												ext="bmp";
										}
										// application/msword
										else if(fileType.endsWith("msword")){
												ext="doc";
										}
										//application/vnd.ms-excel
										else if(fileType.endsWith("excel")){
												ext="csv";
										}
										//application/vnd.openxmlformats-officedocument.wordprocessingml.document
										else if(fileType.endsWith(".document")){
												ext="docx";
										}
										// text/plain
										else if(fileType.endsWith("plain")){
												ext="txt";
										}
										//application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
										else if(fileType.endsWith(".sheet")){
												ext="xlsx";
										}
										// audio/wav
										else if(fileType.endsWith("wav")){
												ext="wav";
										}
										// text/xml
										else if(fileType.endsWith("xml")){
												ext="xml";
										}										
										else if(fileType.endsWith("html")){
												ext="html";
										}
										// video/mng
										else if(fileType.endsWith("mng")){
												ext="mng";
										}
										else if(fileType.endsWith("mpeg")){
												ext="mpg";
										}
										// video/mp4
										else if(fileType.endsWith("mp4")){
												ext="mp4";
										}										
										else if(fileType.endsWith("avi")){
												ext="avi";
										}
										else if(fileType.endsWith("mov")){
												ext="mov";
										}
										// quick time video
										else if(fileType.endsWith("quicktime")){
												ext="qt";
										}
										else if(fileType.endsWith("wmv")){
												ext="wmv"; 
										}
										else if(fileType.endsWith("asf")){
												ext="asf";
										}
										// flash video
										else if(fileType.endsWith("flash")){
												ext="swf";
										}										
										else if(fileType.startsWith("image")){
												ext="jpg";
										}

								}
						}
				} catch (Exception e) {
						System.err.println(e);
				}
				return ext;
		}		
    //
    /**
     * Adds escape character before certain characters
     *
     */
    public final static String escapeIt(String s) {
				StringBuffer safe = new StringBuffer(s);
				int len = s.length();
				int c = 0;
				boolean noSlashBefore = true;
				while (c < len) {                           
						if ((safe.charAt(c) == '\'' ||
								 safe.charAt(c) == '"') && noSlashBefore){ 
								safe.insert(c, '\\');
								c += 2;
								len = safe.length();
								noSlashBefore = true;
						}
						else if(safe.charAt(c) == '\\'){
								noSlashBefore = false;
								c++;
						}
						else {
								noSlashBefore = true;
								c++;
						}
				}
				return safe.toString();
    }
		//
		public final static Connection getConnection(){
		
				Connection con = null;
				int trials = 0;
				boolean pass = false;
				while(trials < 3 && !pass){
						try{
								trials++;
								Context initCtx = new InitialContext();
								Context envCtx = (Context) initCtx.lookup("java:comp/env");
								DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQL_task");
								con = ds.getConnection();
								if(con == null){
										String str = " Could not connect to DB ";
										logger.error(str);
								}
								else{
										pass = testCon(con);
										if(pass){
												c_con++;
												logger.debug("Got connection: "+c_con);
												logger.debug("Got connection at try "+trials);
										}
								}
						}
						catch(Exception ex){
								logger.error(ex);
						}
				}
				return con;
		}
		//
		final static boolean testCon(Connection con){
				boolean pass = false;
				Statement stmt  = null;
				ResultSet rs = null;
				String qq = "select 1+1";		
				try{
						if(con != null){
								stmt = con.createStatement();
								logger.debug(qq);
								rs = stmt.executeQuery(qq);
								if(rs.next()){
										pass = true;
								}
						}
						rs.close();
						stmt.close();
				}
				catch(Exception ex){
						logger.error(ex+":"+qq);
				}
				return pass;
		}
    //
    // users are used to enter comma in numbers such as xx,xxx.xx
    // as we can not save this in the DB as a valid number
    // so we remove it 
    public final static String cleanNumber(String s) {

				if(s == null) return null;
				String ret = "";
				int len = s.length();
				int c = 0;
				int ind = s.indexOf(",");
				if(ind > -1){
						ret = s.substring(0,ind);
						if(ind < len)
								ret += s.substring(ind+1);
				}
				else
						ret = s;
				return ret;
    }
    /**
     * replaces the special chars that has certain meaning in html
     *
     * @param s the passing string
     * @returns string the modified string
     */
    public final static String replaceSpecialChars(String s) {
				char ch[] ={'\'','\"','>','<'};
				String entity[] = {"&#39;","&#34;","&gt;","&lt;"};
				//
				// &#34; = &quot;

				String ret ="";
				int len = s.length();
				int c = 0;
				boolean in = false;
				while (c < len) {             
						for(int i=0;i< entity.length;i++){
								if (s.charAt(c) == ch[i]) {
										ret+= entity[i];
										in = true;
								}
						}
						if(!in) ret += s.charAt(c);
						in = false;
						c ++;
				}
				return ret;
    }
		/**
     * Disconnect the database and related statements and result sets
     * 
     * @param con
     * @param stmt
     * @param rs
     */
    public final static void databaseDisconnect(Connection con,Statement stmt,
																								ResultSet rs) {
				try {
						if(rs != null) rs.close();
						rs = null;
						if(stmt != null) stmt.close();
						stmt = null;
						if(con != null) con.close();
						con = null;
						logger.debug("Closed Connection "+c_con);
						c_con--;
						if(c_con < 0) c_con = 0;
				}
				catch (Exception e) {
						e.printStackTrace();
				}
				finally{
						if (rs != null) {
								try { rs.close(); } catch (SQLException e) { ; }
								rs = null;
						}
						if (stmt != null) {
								try { stmt.close(); } catch (SQLException e) { ; }
								stmt = null;
						}
						if (con != null) {
								try { con.close(); } catch (SQLException e) { ; }
								con = null;
						}
				}
    }
    /**
     * Write the number in bbbb.bb format needed for currency.
     * = toFixed(2)
     * @param dd the input double number
     * @returns the formated number as string
     */
    public final static String formatNumber(double dd){
				//
				dd = dd + 0.005; // to avoid 8.80999
				String str = ""+dd;
				String ret="";
				int l = str.length();
				int i = str.indexOf('.');
				int r = i+3;  // required length to keep only two decimal
				// System.err.println(str+" "+l+" "+r);
				if(i > -1 && r<l){
						ret = str.substring(0,r);
				}
				else{
						ret = str;
				}
				return ret;
    }
    //
    // main page banner
    //
    public final static String banner(String url){

				String banner = "<head>\n"+
						"<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />\n"+
						"<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\" />\n"+
						"<link rel=\"SHORTCUT ICON\" href=\"/favicon.ico\" />\n"+
						"<style type=\"text/css\" media=\"screen\">\n"+
						"@import url(\"/skins/default/skin.css\");\n"+
						"</style>\n"+
						"<style type=\"text/css\" media=\"print\">@import url(\"/skins/default/print.css\");</style>\n"+
						"<script src=\"/functions.js\" type=\"text/javascript\"></script>\n"+
						"<title>RiskTrack - City of Bloomington, Indiana</title>\n"+
						"</head>\n"+
						"<body>\n"+
						"<div id=\"banner\">\n"+
						"<h1><a href=\""+url+"RiskTrack\">RiskTrack</a></h1><h2>City of Bloomington, Indiana</h2>\n"+
						"</div>";
				return banner;
    }
    //
    public final static String menuBar(String url, boolean logged){
				String menu = "<div class=\"menuBar\">\n"+
						"<a href=\""+url+"RiskTrack\">Home</a>\n"+
						"<a href=\""+url+"RiskTrack/status.html\">Status</a>\n";
				if(logged){
						menu += "<a href=\""+url+"RiskTrack/Logout\">Logout</a>\n";
				}
				menu += "</div>\n";
				return menu;
    }
    //
    // Non static methods and variables
    //
    String[] getDeptIdArr(){
				return deptIdArr;
    }
    String[] getDeptArr(){
				return deptArr;
    }
    public String setArrays(Statement stmt, ResultSet rs){

				String str ="",str2="", qq ="", ret="";
				int cnt = 0, i=0;
				qq = "select count(*) from departments";
				try {
						if(debug){
								logger.debug(qq);
						}
						rs = stmt.executeQuery(qq);
						if(rs.next()){
								cnt = rs.getInt(1);
						}
						if(cnt > 0){
								deptArr = new String[cnt];
								deptIdArr = new String[cnt];
								for(int j=0;j<cnt;j++){ 
										deptArr[j] = "";
										deptIdArr[j] = "";
								}
								qq = " select * from departments ";
								if(debug){
										logger.debug(qq);
								}
								rs = stmt.executeQuery(qq);
								while(rs.next()){
										str = rs.getString(1);
										str2 = rs.getString(2);
										if(str == null) str = "";
										if(str2 == null) str2 = "";
										deptIdArr[i] = str;
										deptArr[i] = str2;
										i++;
								}
						}
				}
				catch(Exception e){
						ret += e;
						logger.error(e+":"+qq);
				}
				return ret;
    }
    /**
     * given dept id, returns the dept name.
     *
     * @param array of departments ids
     * @param array of departments names
     * @param specific dept id
     * @returns dept name
     */
    public final static String getDeptName(String[] deptIdArr,
																					 String[] deptArr,
																					 String id){
				String ret = "";
				if(deptArr != null){
						for(int i=0;i<deptArr.length;i++){
								if(deptIdArr[i].equals(id)){
										ret = deptArr[i];
										break;
								}
						}
				}
				return ret;
    }
    //
    public final static String getToday(){

				String day="",month="",year="";
				Calendar current_cal = Calendar.getInstance();
				int mm =  (current_cal.get(Calendar.MONTH)+1);
				int dd =   current_cal.get(Calendar.DATE);
				year = ""+ current_cal.get(Calendar.YEAR);
				if(mm < 10) month = "0";
				month += mm;
				if(dd < 10) day = "0";
				day += dd;
				return month+"/"+day+"/"+year;
    }
    public final static String getDateFromToday(int days){

				String day="",month="",year="";
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, days);
				
				int mm =  (cal.get(Calendar.MONTH)+1);
				int dd =   cal.get(Calendar.DATE);
				year = ""+ cal.get(Calendar.YEAR);
				if(mm < 10) month = "0";
				month += mm;
				if(dd < 10) day = "0";
				day += dd;
				return month+"/"+day+"/"+year;
    }
    public final static String getDateFrom(String date, int days){

				String dt="";
				if(date == null) return dt;
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				String day="",month="",year="";
				try{
						java.util.Date myDate = format.parse(date);				
						Calendar cal = Calendar.getInstance();
						cal.setTime(myDate);
						cal.add(Calendar.DATE, days);
						dt = format.format(cal.getTime());
				}catch(Exception ex){}
				return dt;
    }		
		//
    public final static String getTimeNow(){

				String time="";
				Calendar current_cal = Calendar.getInstance();
				time =  ""+ current_cal.get(Calendar.HOUR_OF_DAY)+":";
				int mnt = current_cal.get(Calendar.MINUTE);
				if (mnt < 10)  time += "0"+mnt;
				else time += mnt;
				return time;
    }
		public final static LdapUser getUserInfo(String userid, EnvBean bean){
	
				String dept ="",busCat="", fullName="",email="", phone="",
						str="", post="";

				Hashtable env = new Hashtable(11);
				LdapUser user = null;
				if (!connectToServer(env, bean)){
						System.err.println("Unable to connect to ldap");
						return null;
				}
				try{
						//
						DirContext ctx = new InitialDirContext(env);
						SearchControls ctls = new SearchControls();
						String[] attrIDs = {"givenName",
																"department",
																"telephoneNumber",
																"mail",
																"sn",
																"businessCategory",
																"title"};
						//
						ctls.setReturningAttributes(attrIDs);
						ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
						String filter = "(cn="+userid+")";
						NamingEnumeration answer = ctx.search("", filter, ctls);
						if(answer.hasMore()){
								//
								user = new LdapUser();
								user.setUsername(userid);
								SearchResult sr = (SearchResult)(answer.next());
								Attributes atts = sr.getAttributes();
								//
								Attribute givenname = (Attribute)(atts.get("givenName"));
								if (givenname != null){
										fullName = givenname.get().toString();
										// user.setFname(fullName);
								}
								Attribute sn = (Attribute)(atts.get("sn"));
								if (sn != null){
										str = sn.get().toString();
										fullName += " "+sn.get().toString();
										// user.setLname(str);
								}
								if(!fullName.equals(""))
										user.setFullname(fullName);				
								Attribute department = 
										(Attribute)(atts.get("department"));
								if (department != null){
										dept = department.get().toString();
										user.setDept(dept);
								}
								Attribute tele = (Attribute)(atts.get("telephoneNumber"));
								if (tele != null){
										phone = tele.get().toString();
										user.setPhone(phone);
								}
								Attribute cat = (Attribute)(atts.get("businessCategory"));
								if (cat != null){
										busCat = cat.get().toString();
										user.setDivision(busCat);
								}
								Attribute title = (Attribute)(atts.get("title"));
								if (title != null){
										post = title.get().toString();
										// user.setTitle(post);
								}
						}
				}
				catch(Exception ex){
						if(user == null)
								logger.error(ex);
				}
				return user;
    }  
    /**
     * Gets User firs/last name from ldap.
     *
     * @param userid string
     * @return String user name
     */
    final static String getFirstName(String userid, EnvBean bean){
				Hashtable env = new Hashtable(11);
				String fname="";
				if (!connectToServer(env, bean)){
						System.err.println("Error Connecting to LDAp Server");
						return fname;
				}
				try{
						DirContext ctx = new InitialDirContext(env);
						SearchControls ctls = new SearchControls();
						String[] attrIDs = {"givenName",
																"sn"};
						//
						ctls.setReturningAttributes(attrIDs);
						ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
						String filter = "(cn="+userid+")";
						NamingEnumeration answer = ctx.search("", filter, ctls);
						if(answer.hasMore()){
								SearchResult sr = (SearchResult)(answer.next());
								Attributes atts = sr.getAttributes();
								Attribute givenname = (Attribute)(atts.get("givenName"));
								if (givenname != null){
										fname = givenname.get().toString();
								}
						}
				}
				catch(Exception ex){
						logger.error(ex);
				}
				return fname;
    }  
    /**
     * gets matching list from ldap for a given substring of a user name.
     * @param subid the substring of the userid, subid has to be at least 
     * two characters otherwise the ldap will hang for some reason.
     * @return a list of matching caases.
     */
	
    public final static List<LdapUser> getMatchList(String subid, EnvBean bean){
				Hashtable env = new Hashtable(11);
				List<LdapUser> users = new ArrayList<>();
				//
				if (!connectToServer(env, bean)){
						System.err.println("Error Connecting to LDAp Server");
						return null;
				}
				try{
						DirContext ctx = new InitialDirContext(env);
						SearchControls ctls = new SearchControls();
						String[] attrIDs = {"cn","givenName","sn"};
						//
						ctls.setReturningAttributes(attrIDs);
						ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
						String filter = "(cn="+subid+"*)";
						NamingEnumeration answer = ctx.search("", filter, ctls);
						while(answer.hasMore()){
								//
								String userid = "", fullName="";

								SearchResult sr = (SearchResult)(answer.next());
								Attributes atts = sr.getAttributes();

								Attribute cn = (Attribute)(atts.get("cn"));
								if (cn != null){
										userid = cn.get().toString();
								}
				
								Attribute name = (Attribute)(atts.get("givenName"));
								if (name != null){
										fullName = name.get().toString();
								}
								name = (Attribute)(atts.get("sn"));
								if (name != null){
										if(!fullName.equals("")) fullName += " ";
										fullName += name.get().toString();
								}
								if(!fullName.equals("")){
										LdapUser user = new LdapUser(false, userid, fullName);
										users.add(user);
								}
						}
				}
				catch(Exception ex){
						if(users.size() == 0){
								logger.error(ex);
						}
				}
				return users;
    }  
    /**
     * Connect to ldap server.
     * @returns boolean true if ok or false of not
     */
		final static boolean connectToServer(Hashtable env, EnvBean bean){

				if(env != null && bean != null){
						env.put(Context.INITIAL_CONTEXT_FACTORY, 
										"com.sun.jndi.ldap.LdapCtxFactory");
						env.put(Context.PROVIDER_URL, bean.getUrl());
						env.put(Context.SECURITY_AUTHENTICATION, "simple"); 
						env.put(Context.SECURITY_PRINCIPAL, bean.getPrinciple());
						env.put(Context.SECURITY_CREDENTIALS, bean.getPassword());
				}
				else{
						return false;
				}
				try {
						DirContext ctx = new InitialDirContext(env);
				} catch (NamingException ex) {
						System.err.println(" ldap "+ex);
						return false;
				}
				return true;
    }
    //
    // initial cap a word
    //
    final static String initCapWord(String str_in){
				String ret = "";
				if(str_in !=  null){
						if(str_in.length() == 0) return ret;
						else if(str_in.length() > 1){
								ret = str_in.substring(0,1).toUpperCase()+
										str_in.substring(1).toLowerCase();
						}
						else{
								ret = str_in.toUpperCase();   
						}
				}
				return ret;
    }
    //
    // init cap a phrase
    //
    final static String initCap(String str_in){
				String ret = "";
				if(str_in != null){
						if(str_in.indexOf(" ") > -1){
								String[] str = str_in.split("\\s"); // any space character
								for(int i=0;i<str.length;i++){
										if(i > 0) ret += " ";
										ret += initCapWord(str[i]);
								}
						}
						else
								ret = initCapWord(str_in);// it is only one word
				}
				return ret;
    }


}






















































