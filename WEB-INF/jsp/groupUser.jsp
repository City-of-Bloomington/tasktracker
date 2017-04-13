<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="groupUser" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />
	<h2>Manage Users in Groups</h2>
	<ul>
		<li>To start, pick a group from the list</li>
		<li>* checkbox to remove the user from the group </li>		
		<li>** Checkbox to add user to the group </li>
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
		<dd><s:select name="groupUser.group_id" value="%{groupUser.group_id}" list="groups" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Group" id="selection_id" /></dd>
	</dl>
	<s:if test="groupUser.hasGroupUsers()">
		<table width="90%" border="1"><caption><s:property value="groupUsersTitle" /></caption>
			<tr><td>* </td><td>Users </td></tr>			
			<s:iterator var="one" value="groupUser.group_users">
			<tr>
				<td><input type="checkbox" name="groupUser.del_users" value="<s:property value='id' />" /></td>
				<td><s:property value="fullname" /></td>
			</tr>
		</s:iterator>
		</table>
		<s:submit name="action" type="button" value="Remove from this group" class="fn1-btn"/>		
	</s:if>	
	<s:if test="groupUser.hasOtherUsers()">
		<table width="90%" border="1"><caption><s:property value="otherUsersTitle" /></caption>
			<tr><td>** </td><td>Users</td></tr>
			<s:iterator var="one" value="groupUser.other_users">
				<tr>
					<td><input type="checkbox" name="groupUser.add_users" value="<s:property value='id' />" /></td>
					<td><s:property value="fullname" /></td>
				</tr>
			</s:iterator>
		</table>
		<s:submit name="action" type="button" value="Add to this group" class="fn1-btn"/>
	</s:if>

</s:form>

<%@  include file="footer.jsp" %>


