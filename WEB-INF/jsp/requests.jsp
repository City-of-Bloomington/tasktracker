<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:if test="#requests">
	<table class="fn1-table">
		<caption><s:property value="#requestsTitle" /></caption>
		<thead>
			<tr>
				<th align="center"><b>Request</b></th>
				<th align="center"><b>Date</b></th>
				<th align="center"><b>Type</b></th>
				<th align="center"><b>Group</b></th>
				<th align="center"><b>Location</b></th>				
				<th align="center"><b>Status</b></th>
				<th align="center"><b>Destails</b></th>
			</tr>
		</thead>
		<tbody>
			<s:iterator var="one" value="#requests">
				<tr>
					<td><a href="<s:property value='#application.url' />request.action?id=<s:property value='id' />"><s:property value="summary" /></a></td>
					<td><s:property value="date" /></td>
					<td><s:property value="type" /></td>
					<td><s:property value="group" /></td>
					<td><s:property value="locationInfo" /></td>					
					<td><s:property value="status" /></td>
					<td><s:property value="description" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:if>
