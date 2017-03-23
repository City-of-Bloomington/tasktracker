<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<h1>Request: <s:property value="request.summary" /></h1>
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
	<div class="tt-split-container">		
		<dl class="fn1-output-field">
			<dt>ID </dt>
			<dd><s:property value="request.id" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Summary </dt>
			<dd><s:property value="%{request.summary}" /> </dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt> Type</dt>
				<dd><s:property value="%{request.type}" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Status </dt>
				<dd><s:property value="%{request.status}" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt> Related Group</dt>
				<dd><s:property value="%{request.group}" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Location </dt>
				<dd><s:property value="%{request.location}" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Date </dt>
				<dd><s:property value="%{request.date}" /> Time:<s:property value="%{request.time}" /></dd>
			</dl>
		</div>
		<div class="tt-split-container">
			<dl class="fn1-output-field">
				<dt>Due Date </dt>
				<dd><s:property value="%{request.due_date}" /> </dd>
			</dl>				
			<dl class="fn1-output-field">
				<dt>Employee Name</dt>
				<dd><s:property value="%{request.fullname}" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Employee Dept </dt>
				<dd id="dept"> <s:property value="request.dept" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Employee Phone</dt>
				<dd id="phone"><s:property value="request.phone" /></dd>
			</dl>
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
			<dd><s:property value="%{request.description}" /></dd>
		</dl>
		<a href="<s:property value='#application.url' />request.action?action=Open&id=<s:property value='id' />">Re-Open This Request</a></td>		
</div>
<s:if test="request.hasTasks()">
	<s:set var="tasks" value="request.tasks" />
	<s:set var="showRequest" value="false" />
	<s:set var="tasksTitle" value="tasksTitle" />
	<%@ include file="tasks.jsp" %>
</s:if>
<%@  include file="footer.jsp" %>


