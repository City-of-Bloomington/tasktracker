<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<h3><s:property value="requestsHeader" /></h3>
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

<s:if test="requests">
	<s:set var="requestsTitle" value="requestsTitle" />	
	<s:set var="requests" value="requests" />
	<%@  include file="requests.jsp" %>
</s:if>
<s:else>
	<p><s:property value="requestsTitle" /></p>
</s:else>
<%@  include file="footer.jsp" %>


