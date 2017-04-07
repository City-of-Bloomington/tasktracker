<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="task" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<s:hidden name="request_id" value="%{task.request_id}" />
	<s:if test="task.id == ''">
		<h1>New Task</h1>
	</s:if>
	<s:else>
		<h1>Edit: <s:property value="task.name" /></h1>
		<s:hidden id="task.id" name="task.id" value="%{task.id}" />
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
		<s:if test="id != ''">
			If you make any change, please hit the 'Save Changes' button <br />
		</s:if>
		<s:else>
			You must hit 'Save' button to save data.
		</s:else>
		You may add time spent on this task (in hours), and estimated expenses, normally hours multiply by your hourly rate in the related fields.
	</p>
	<div class="tt-row-container">
		<s:if test="task.id != ''">
			<dl class="fn1-output-field">
				<dt>ID </dt>
				<dd><s:property value="task.id" /> </dd>
			</dl>
		</s:if>
			<dl class="fn1-output-field">
				<dt>Request </dt>
				<dd><a href="<s:property value='#application.url' />request.action?id=<s:property value='task.request_id' />"><s:property value="task.request" /></a>
				</dd>
			</dl>		
			<dl class="fn1-output-field">
			<dt>Summary </dt>
			<dd><s:textfield name="task.name" value="%{task.name}" size="50" maxlength="70" required="true" />* </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Date </dt>
			<dd><s:property value="%{task.date}" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Details </dt>
			<dd>
				<s:textarea name="task.notes" value="%{task.notes}" cols="50" rows="5" />				
			</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Time spent (hrs)</dt>
			<dd><s:textfield name="task.hours" value="%{task.hours}" size="5" maxlength="5" />, Expenses $<s:textfield name="task.expenses" value="%{task.expenses}" size="8" maxlength="6" class="money" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt></dt>
			<dd>	Note: if every task's 'Completed' field is set to 100%, then the request will be closed and no further task is needed. </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Completed (%)</dt>
			<dd><s:radio name="task.percent" value="%{task.percent}" list="#{'0':'0%','25':'25%','50':'50%','75':'75%','100':'100%'}" /> </dd>
		</dl>
		<s:if test="task.id == ''">
			<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
		</s:if>
		<s:else>
			<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
			<s:if test="!task.isCompleted()">
				<s:submit name="action" type="button" value="Completed" class="fn1-btn"/>
			</s:if>
		</s:else>
	</div>
</s:form>
<s:if test="tasks != null">
	<s:set var="tasks" value="tasks" />
	<s:set var="tasksTitle" value="tasksTitle" />
	<%@  include file="tasks.jsp" %>
</s:if>
<%@  include file="footer.jsp" %>


