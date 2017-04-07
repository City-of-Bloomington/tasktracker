<?xml version="1.0" encoding="UTF-8" ?>
<%@  include file="header.jsp" %>


<h3> <s:property value="report.title" /></h3>

<table border="1" width="60%">
  <tr>
	<td><label><s:property value="report.columnTitle.first" /></label></td>		
	<td><label><s:property value="report.columnTitle.second" /></label></td>
	<s:if test="report.columnTitle.size == 3">
	  <td><label><s:property value="report.columnTitle.third" /></label></td>
	</s:if>
  </tr>
  <s:iterator value="report.rows" status="rowStatus">
	  <tr>
		<td><s:property value="first" /></td>
		<s:if test="report.columnTitle.size == 2">
		  <td align="right"><s:property value="second" /></td>
		</s:if>
		<s:else>
		  <td><s:property value="second" /></td>
		</s:else>
		<s:if test="report.columnTitle.size == 3">
		  <td align="right"><s:property value="third" /></td>
		</s:if>		
	  </tr>
  </s:iterator>
</table>
</div>
</body>
</html>






















































