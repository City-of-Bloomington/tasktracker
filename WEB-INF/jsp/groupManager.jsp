<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="groupManager" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />
	<h2>Group Managers</h2>
	<ul>
		<li>To start, pick a group from the list</li>
		<li>* checkbox to remove the user from managing this group </li>
		<li>** checkbox to mark the user as group manager</li>
	</ul>
  <s:if test="hasActionErrors()">
		<div class="errors">
      <s:actionerror/>
	</div>
  </s:if>
  <s:elseif test="hasActionMessages()">
		<div class="welcome">
      <s:actionmessage/>
		</div>
  </s:elseif>
	<dl class="fn1-output-field">
		<dt>Groups</dt>
		<dd><s:select name="groupManager.group_id" value="%{groupManager.group_id}" list="groups" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Group" id="selection_id" /></dd>
	</dl>
	<s:if test="groupManager.hasGroupManagers()">
		<table width="90%" border="1"><caption><s:property value="groupManagersTitle" /></caption>
			<tr><td>* </td><td>Users </td></tr>			
			<s:iterator var="one" value="groupManager.groupManagers">
			<tr>
				<td><input type="checkbox" name="groupManager.del_users" value="<s:property value='id' />" /></td>
				<td><s:property value="fullname" /></td>
			</tr>
		</s:iterator>
		</table>
		<s:submit name="action" type="button" value="Remove managers of this group" class="fn1-btn"/>		
	</s:if>	
	<s:if test="groupManager.hasOtherUsers()">
		<table width="90%" border="1"><caption><s:property value="otherUsersTitle" /></caption>
			<tr><td>** </td><td>Users</td></td></tr>
			<s:iterator var="one" value="groupManager.otherUsers">
				<tr>
					<td><input type="checkbox" name="groupManager.add_users" value="<s:property value='id' />" /></td>
					<td><s:property value="fullname" /></td>
				</tr>
			</s:iterator>
		</table>
		<s:submit name="action" type="button" value="Add managers to this group" class="fn1-btn"/>
	</s:if>

</s:form>

<%@  include file="footer.jsp" %>


