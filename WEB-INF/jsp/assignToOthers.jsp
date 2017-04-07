<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="assignToOthers" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<s:hidden name="reqlst.status" value="Active" />	
	<h1>Requests Assigned to Others</h1>
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
	<p>Please pick a user from the list</p>
	<div class="tt-row-container">
		<dl class="fn1-output-field">
			<dt>Group </dt>
			<dd><s:select name="reqlst.group_id" value="%{reqlst.group_id}" list="groups" listKey="id" listValue="name" headerKey="-1" headerValue="Pick a Group" onchange="changeGroupUsers(this,'assigned_user_id');"/>(optional)</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Assigned To </dt>
			<dd><s:select name="assigned_user_id" value="%{assigned_user_id}" list="users" listKey="id" listValue="fullname" headerKey="-1" headerValue="Pick A User" id="assigned_user_id" /></dd>
		</dl>
		<s:submit name="action" type="button" value="Find" class="fn1-btn"/>
	</div>
</s:form>
<s:if test="action != ''">
	<s:if test="requests != null">
		<s:set var="requests" value="requests" />
		<s:set var="requestsTitle" value="requestsTitle" />
		<%@  include file="requests.jsp" %>
	</s:if>
</s:if>
<%@  include file="footer.jsp" %>


