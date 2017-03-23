<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:if test="tasks != null">
	<s:set var="tasks" value="request.tasks" />
	<s:set var="showRequest" value="true" />
	<s:set var="tasksTitle" value="tasksTitle" />
	<%@ include file="tasks.jsp" %>
</s:if>
<s:else>
	<h3> No active actions found </h3>
</s:else>
<%@  include file="footer.jsp" %>


