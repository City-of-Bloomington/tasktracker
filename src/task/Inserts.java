package task;
import java.sql.*;

/**
 *
 */

class Inserts{

    boolean debug;
    //
    // xhtmlHeaderInc
    final static String xhtmlHeaderInc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
		"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n"+
		"<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">";
    //
    // basic constructor
    public Inserts(boolean deb){
		//
		// initialize
		//
		debug = deb;
    }
    //
    // main page banner
    //
    public final static String banner(String url){

		String banner = "<head>\n"+
			"<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />\n"+
			"<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\" />\n"+
			"<link rel=\"SHORTCUT ICON\" href=\"https://apps.bloomington.in.gov/favicon.ico\" />\n"+
			"<style type=\"text/css\" media=\"screen\">\n"+
			"           @import url(\"https://apps.bloomington.in.gov/skins/default/screen.css\");\n"+
			"</style>\n"+
			"<style type=\"text/css\" media=\"print\">\n"+
			"      @import url(\"https://apps.bloomington.in.gov/skins/default/print.css\");\n"+
			"</style>\n"+
			"<style type=\"text/css\">"+  // user defined
			"table.wide {width:100%;border:none}"+
			"table.widesolid {width:100%;border-style:solid}"+
			"table.box {width:100%;border:2px solid}"+
			"table.box th {border:2px solid; text-align:center; vertical-align:bottom; color:black} "+
			"table.box td {border:2px solid; color:black} "+
			"table.control {width:100%} "+
			"table.control td {text-align:center; padding:.5em} "+
			"tr.solid {border-style:solid}\n"+
			"td.right {text-align:right}\n"+
			"</style>\n"+
			//
			// Java Script common for all pages
			//
			"<script type=\"text/javascript\">\n"+
			"  function checkDate(dt){ \n                       "+     
			"    var dd = dt.value;                             \n"+
			"   if(dd.length == 0) return true;                 \n"+
			"   var dar = dd.split(\"/\");                      \n"+
			" if(dar.length < 3){                               \n"+
			" alert(\"Not a valid date: \"+dd);                 \n"+
			"      dt.select();                                 \n"+
			"      dt.focus();                                  \n"+
			"      return false;}                               \n"+
			"   var m = dar[0];                                 \n"+
			"   var d = dar[1];                                 \n"+
			"   var y = dar[2];                                 \n"+
			"   if(isNaN(m) || isNaN(d) || isNaN(y)){           \n"+
			" alert(\"Not a valid date: \"+dd);                 \n"+
			"      dt.select();                                 \n"+
			"      dt.focus();                                  \n"+
			"      return false; }                              \n"+
			"   if( !((m > 0 && m < 13) && (d > 0 && d <32) &&  \n"+
			"    (y > 1950 && y < 2099))){                      \n"+
			" alert(\"Not a valid date: \"+dd);                 \n"+
			"      dt.select();                                 \n"+
			"      dt.focus();                                  \n"+
			"      return false;                                \n"+
			"      }                                            \n"+
			"    return true;                                   \n"+
			"    }                                              \n"+
			"  function checkNumber(dt){                        \n"+     
			"    var dd = dt.value;                             \n"+
			"   if(dd.length == 0) return false;                \n"+
			"     if(isNaN(dd)){                                \n"+
			"      alert(\"Not a valid Number: \"+dd);          \n"+
			"      dt.select();                                 \n"+
			"      dt.focus();                                  \n"+
			"      return false;                                \n"+
			"        }                                          \n"+
			"    return true;                                   \n"+
			"   }                                               \n"+
			"  function validateDelete(){	                    \n"+
			"   var x = false;                                  \n"+
			" x = confirm(\"Are you sure you want to delete this record\");\n"+
			"   return x;                                        \n"+
			"	}	             		                         \n"+
			"  function refreshPage(){	              \n"+
			"  document.forms[0].submit();            \n"+
			"  }                                      \n"+
			" </script>				                  \n"+
			"<title>WorkTrack - City of Bloomington, Indiana</title>\n"+
			"</head>\n"+
			"<body>\n"+
			"<div id=\"banner\">\n"+
			"  <div id=\"application_name\">WorkTrack</div>\n"+
			"  <div id=\"location_name\">City Of Bloomington, IN</div>\n"+
			"  <div id=\"navigation\"></div>\n"+
			"</div>";
		return banner;
    }
    //
    public final static String menuBar(String url, boolean logged){
		String menu = "<div class='menuBar'>\n<ul>";
	    //  "<li><a href=\""+url+"\">Home</a></li>\n"+
	    // " <li><a href=\""+url+"/status.html\">Status</a></li>\n";
		if(logged){
			menu += "<li><a href='"+url+"/Logout'>Logout</a></li>\n";
		}
		menu += "</ul></div>\n";
		menu += "<div>"; // to include the sidebar and maninContent
		return menu;
    }
    //
    // sidebar
    //
    public final static String sideBar(String url,
									   User user, 
									   boolean workGroup){
		String ret = "<div id='leftSidebar' class='left sidebar' >"+
			"<h3 class='titleBar'>Request Actions</h3>\n"+
			"<ul>\n";
		if(workGroup && user != null){
			ret += "<li><a href='"+url+"/NewWorkServ?action=New'>New Request</a></li>\n";
			ret += "<li><a href='"+url+"/Browse?'>Search</a></li>\n"+
				"<li><a href='"+url+
				"/BrowseData?outStand=y'>On going</a></li>\n"+
				"<li><a href='"+url+
				"/BrowseData?status=Unassigned'>Unassigned</a></li>\n"+
				"<li><a href='"+url+
				"/BrowseData?outStand=y&assigned="+user.getUsername()+
				"'>Assigned to Me</a></li>\n"+
				"<li><a href='"+url+"/Reporter'>Reports</a></li>\n";
		}
		else{
			ret += "<li><a href='"+url+"/ReqWork'>New Request</a></li>\n";
		}
		ret += "</ul></div>";
		return ret;

    }

}






















































