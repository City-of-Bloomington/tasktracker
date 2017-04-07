<?xml version="1.0" encoding="UTF-8" ?>
<%@  include file="header.jsp" %>
<s:form action="report" method="post">    
  <h3> Task Tracker Reports</h3>
  <s:if test="hasActionErrors()">
		<div class="errors">
      <s:actionerror/>
		</div>
  </s:if>
  <s:if test="hasActionMessages()">
		<div class="welcome">
      <s:actionmessage/>
		</div>
  </s:if>
  <table border="1" width="90%">
		<tr>
			<td align="left"><label>Report Type</label></td>
			<td align="left"><label>Classified by</lable></tr>
		</tr>
		<tr>
			<td align="left" valign="top"><input type="radio" name="report.report_type" value="stats" <s:if test="report.report_type == 'stats'">checked="checked" </s:if> />Stats</td>
			<td align="left">
				<s:radio name="report.by" value="report.by" list="#{'type':'Request Types','assign':'Assignments','group':'Groups'}" /></td>
		</tr>
		<tr>
			<td align="left" valign="top">&nbsp;</td>
			<td align="left"><table width="100%">
				<tr>  
					<td align="left"><label>Period </label></td>
					<td align="left"><s:radio name="report.period_type" list="#{'today':'Today','last_serven_days':'Last 7 Days','period_below':'Period set below'}" value="%{report.period_type}" /> </td>
				</tr>
				<tr><td></td><td>(for 'Period set below' enter the start and end date below) </td></tr>
				<tr>
					<td align="left"><label>Date, from: </label></td>
					<td align="left"><s:textfield name="report.date_from" maxlength="10" size="10" value="%{report.date_from}" cssClass="date" /><label> To </label><s:textfield name="report.date_to" maxlength="10" size="10" value="%{report.date_to}" cssClass="date" /></td>
				</tr>
				<tr>
					<td align="left"><label>Type: </label></td>
					<td align="left"><s:select name="report.type_id" value="%{report.type_id}" list="types" listKey="id" listValue="name" headerKey="-1" headerValue="All" /></td>
				</tr>		
				<tr>
					<td align="left"><label>Location: </label></td>
					<td align="left"><s:select name="report.location_id" value="%{report.location_id}" list="locations" listKey="id" listValue="name" headerKey="-1" headerValue="All" /></td>
				</tr>
				<tr>
					<td align="left"><label>Group: </label></td>
					<td align="left"><s:select name="report.group_id" value="%{report.group_id}" list="groups" listKey="id" listValue="name" headerKey="-1" headerValue="All" /></td>
				</tr>		
				<tr><td>&nbsp;</td><td>&nbsp;</td></tr>			
			</table></td>
		</tr>	
		<tr>
			<td colspan="2" valign="top" align="right">
				<s:submit name="action" type="button" value="Submit" />
			</td>
		</tr>
  </table>
</s:form>
<%@  include file="footer.jsp" %>	






































