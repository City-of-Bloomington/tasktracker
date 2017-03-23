<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#tasksTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<s:if test="#showRequest">
				<th align="center"><b>Request ID</b></th>
			</s:if>
			<th align="center"><b>Head Line</b></th>
			<th align="center"><b>Date</b></th>
			<th align="center"><b>By</b></th>
			<th align="center"><b>Completed %</b></th>			
			<th align="center"><b>Notes</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#tasks">
			<tr>
				<td><a href="<s:property value='#application.url' />task.action?id=<s:property value='id' />"><s:property value="id" /></a></td>
				<s:if test="#showRequest">
					<td><a href="<s:property value='#application.url' />request.action?id=<s:property value='request_id' />"><s:property value="request_id" /></a></td>
				</s:if>
				<td><s:property value="name" /></td>				
				<td><s:property value="date" /></td>
				<td><s:property value="user" /></td>				
				<td><s:property value="completed" /></td>
				<td><s:property value="notes" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
