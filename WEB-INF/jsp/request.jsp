<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="request" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<s:hidden id="h_username" name="request.username" value="%{request.username}" />
	<s:hidden id="h_dept" name="request.dept" value="%{request.dept}" />
	<s:hidden id="h_phone" name="request.phone" value="%{request.phone}" />				
	<s:if test="id != ''">
		<s:if test="request.canBeChanged()">
			<h1>Edit: <s:property value="request.summary" /></h1>
		</s:if>
		<s:else>
			<h1>Request: <s:property value="request.summary" /></h1>
		</s:else>
		<s:hidden id="request.id" name="request.id" value="%{id}" />
		<s:hidden name="request.status" value="%{request.status}" />		
	</s:if>
	<s:else>
		<h1>New request</h1>
	</s:else>
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
  <p>* Required field <br />
		<s:if test="id">
			If you make any change, please hit the 'Save Changes' button <br />
		</s:if>
		<s:else>
			You must hit 'Save' button to save data. <br />
		</s:else>
	</p>
	<div class="tt-row-container">
		<div class="tt-split-container">		
			<s:if test="request.id != ''">
				<dl class="fn1-output-field">
					<dt>ID </dt>
					<dd><s:property value="request.id" /> </dd>
				</dl>
			</s:if>
			<dl class="fn1-output-field">
				<dt>Summary </dt>
				<dd><s:textfield name="request.summary" value="%{request.summary}" size="30" maxlength="100" required="true" />* </dd>
			</dl>		
			<dl class="fn1-output-field">
				<dt> Type</dt>
				<dd><s:select name="request.type_id" value="%{request.type_id}" list="types" listKey="id" listValue="name" required="true" />*</dd>
			</dl>
			<s:if test="id != ''">
				<dl class="fn1-output-field">
					<dt>Status </dt>
					<dd><s:property value="%{request.status}" /> </dd>
				</dl>
			</s:if>
			<dl class="fn1-output-field">
				<dt> Related Group</dt>
				<dd><s:select name="request.group_id" value="%{request.group_id}" list="groups" listKey="id" listValue="name" required="true" onchange="changeGroupUsers(this,'assign_user_id');"/>*</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Location </dt>
				<dd><s:textfield name="request.location" value="%{request.location}" size="30" maxlength="100" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Date </dt>
				<dd><s:property value="%{request.date}" /> Time:<s:property value="%{request.time}" /></dd>
			</dl>
		</div>
		<div class="tt-split-container">
			<dl class="fn1-output-field">
				<dt>Due Date </dt>
				<dd><s:textfield name="request.due_date" value="%{request.due_date}" size="10" maxlength="10" cssClass="date" /> </dd>
			</dl>				
			<dl class="fn1-output-field">
				<dt> </dt>
				<dd>Request related Employee info (if any)</dd>
				<dd>Start typing the employee last name then </dd>
				<dd>pick from the list</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Employee Name</dt>
				<dd><s:textfield name="request.fullname" value="%{request.fullname}" size="30" maxlength="100" id="emp_name" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Employee Dept </dt>
				<dd id="dept"> <s:property value="request.dept" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Employee Phone</dt>
				<dd id="phone"><s:property value="request.phone" /></dd>
			</dl>
			<s:if test="haveUsers()">
				<dl class="fn1-output-field">
					<dt>New Assignment </dt>
					<dd><s:select name="request.assign_user_id" value="" list="users" listKey="id" listValue="fullname" headerKey="-1" headerValue="Pick a User" id="assign_user_id" />	</dd>
				</dl>
			</s:if>
			<s:if test="request.hasAssignments()">
				<dl class="fn1-output-field">
					<dt>Assignments: </dt>
					<s:iterator var="one" value="request.assignments">
						<dd><s:property value="user" /></dd>
					</s:iterator>				
				</dl>			
			</s:if>
		</div>
		<dl class="fn1-output-field">
			<dt>Details </dt>
			<dd><s:textarea name="request.description" value="%{request.description}" cols="50" rows="5" />*	</dd>
		</dl>
	</div>
	<s:if test="request.id == ''">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/>
	</s:if>
	<s:else>
		<s:if test="request.canBeChanged()">
			<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
			<a href="<s:property value='#application.url' />task.action?request_id=<s:property value='id' />" class="fn1-btn">New Action</a>
			<a href="<s:property value='#application.url' />doUpload.action?request_id=<s:property value='id' />" class="fn1-btn">Attachments</a>			
			<s:submit name="action" type="button" value="Cancel This Request" class="fn1-btn" onclick="return verifyCancel();" />
		</s:if>
		<s:if test="request.hasTasks()">
			<s:set var="tasks" value="request.tasks" />
			<s:set var="showRequest" value="false" />
			<s:set var="tasksTitle" value="tasksTitle" />
			<%@ include file="tasks.jsp" %>
		</s:if>
		<s:if test="request.hasLogs()">
			<s:set var="logs" value="request.logs" />
			<s:set var="logsTitle" value="logsTitle" />
			<%@ include file="logs.jsp" %>			
		</s:if>
	</s:else>
</s:form>
<s:if test="request.id == ''">
	<s:if test="requests">
		<s:set var="requests" value="requests" />
		<s:set var="requestsTitle" value="requestsTitle" />
		<%@  include file="requests.jsp" %>
	</s:if>
</s:if>
<%@  include file="footer.jsp" %>


