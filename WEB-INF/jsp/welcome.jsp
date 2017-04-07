<%@  include file="header.jsp" %>
<h3>Welcome to Task Tracker</h3>
<s:if test="hasActionErrors()">
  <div class="errors">
    <s:actionerror/>
  </div>
</s:if>
			
Please Select one of the following options from the top menu:
<ul>
	<li> To submit a new request, select 'New Request' tab</li>
	<li> To search for certain requests, select the 'Search' tab</li>
	<li> To view all unassigned requests, select the 'Unassigned' tab. </li>
	<li> To view all active requests, select the 'Active Requests' tab </li>
	<li> To view all active tasks, select the 'Active Tasks' tab </li>	
	<li> To view all active requests that have been assigned to you, select the 'Assigned to Me' tab </li>
	<li> To view all active requests that have been assigned to others, select the 'Assigned to Others' tab </li>
	<s:if test="#session != null && #session.user.isAdmin()">
		<li> To run reports, select the 'Reports' from the 'Admin' link in top. </li>
		<li> To add/Edit a group, select 'Groups' from the 'Admin' link in top.</li>
		<li> To add/remove a user from a group select 'Manage Goups' from the 'Admin' link in top.</li>
	</s:if>
	<li> When you are done, click on your name link in top and then click 'Logout' </li>
</ul>


<%@  include file="footer.jsp" %>
