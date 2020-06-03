<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>
<%@ page import="com.ascap.apm.common.context.UserSessionState"%>
<c:if test="${ not empty sessionScope[UserSessionState.HTTP_SESSION_KEY].infoBar}">
<c:if test="${not empty sessionScope[UserSessionState.HTTP_SESSION_KEY].infoBar.programPerformanceInfo}">
<table class="info2">

<tr align="center" valign="top"> 
<c:forEach var="element" items="${sessionScope[UserSessionState.HTTP_SESSION_KEY].infoBar.programPerformanceInfo}" >
<td>
<span class="txtBlk_bold"><c:out value="${element.fieldDescription}" /></span><br/>
<span class="txtBlk"><c:out value="${element.fieldValue}" /></span>
</td>
</c:forEach>
</tr>
</table>
</c:if>
</c:if>