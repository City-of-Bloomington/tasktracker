<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="search" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<h1>Search</h1>
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
	<div class="tt-row-container">
		<dl class="fn1-output-field">
			<dt>Request ID</dt>
				<dd><s:textfield name="reqlst.id" value="%{reqlst.name}" size="10" maxlength="10" /> </dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Type </dt>
			<dd><s:select name="reqlst.type_id" value="%{reqlst.type_id}" list="types" listKey="id" listValue="name" headerKey="-1" headerValue="All" /></dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Group </dt>
			<dd><s:select name="reqlst.group_id" value="%{reqlst.group_id}" list="groups" listKey="id" listValue="name" headerKey="-1" headerValue="All"/></dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Assigned To </dt>
			<dd><s:select name="reqlst.assigned_user_id" value="%{reqlst.assigned_user_id}" list="users" listKey="id" listValue="fullname" headerKey="-1" headerValue="All"/></dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Status</dt>
			<dd><s:radio name="reqlst.status" value="%{reqlst.status}" list="#{'-1':'All','Unassinged':'Unassigned','Active':'Active','Cancelled':'Cancelled','Closed':'Closed'}" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Date, From</dt>
				<dd><s:textfield name="reqlst.date_from" value="%{reqlst.date_from}" size="10" maxlength="10" cssClass="date" /> To <s:textfield name="reqlst.date_to" value="%{reqlst.date_to}" size="10" maxlength="10" cssClass="date" /> 
				</dd>
		</dl>				
		<s:submit name="action" type="button" value="Search" class="fn1-btn"/>
	</div>
</s:form>
<s:if test="action != ''">
	<s:if test="requests">
		<s:set var="requests" value="requests" />
		<s:set var="requestsTitle" value="requestsTitle" />
		<%@  include file="requests.jsp" %>
	</s:if>
</s:if>
<%@  include file="footer.jsp" %>


