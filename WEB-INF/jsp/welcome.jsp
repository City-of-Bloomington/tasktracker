<%@  include file="header.jsp" %>
<h3>Task Tracker</h3>
<s:if test="hasActionErrors()">
  <div class="errors">
    <s:actionerror/>
  </div>
</s:if>

<h3> Welcome to <font color=blue>Task Tracker </font></h3>
			
Please Select one of the following options from the top menu:
<ul>
	<li> To submit a new request, select 'New Request' </li>
	<li> For an extensive search for certain requests, select the 'Search' option</li>
	<li> To view all unassigned requests, select the 'Unassigned' option. </li>
	<li> To view all active requests, select the 'Active Requests' option 
		<li> To view all active requests that have been assigned to you, select the 'Assigned to Me' option </li> 
		<li> To run reports, select the 'Reports'. </li>
</ul>


<%@  include file="footer.jsp" %>
