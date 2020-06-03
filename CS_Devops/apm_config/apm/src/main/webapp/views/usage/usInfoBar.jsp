<%@ page language="java" contentType="text/html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>
<%@ page import="com.ascap.apm.common.context.UserSessionState" %>
<table class="info1" style="margin:0 auto;"> 
<tr>
<td width="10%" rowspan="2" align="left" valign="top" class="txtBlk_bold">
<A class="infolink" href="usageHomeSearch?navigationType=BACK2SEARCH">Search Criteria</A><BR>
<A class="infolink" href="usageHomeSearch?backToSearchResults=true">Search Results</A>
</td>
</tr>
<tr align="center" valign="top">
<c:if test="${not empty sessionScope[UserSessionState.HTTP_SESSION_KEY].infoBar}">
<c:if test="${not empty sessionScope[UserSessionState.HTTP_SESSION_KEY].infoBar.contextSearchCriteriaInfo}" var="sessionState" scope="session" >
<c:forEach items="${sessionScope[UserSessionState.HTTP_SESSION_KEY].infoBar.contextSearchCriteriaInfo}" var ="element">
<td>
<span class="txtBlk_bold"><c:out value="${element.fieldDescription}"></c:out></span><br/>
<span class="txtBlk"><c:out value="${element.fieldValue}"></c:out></span>
</td>
</c:forEach>
</c:if>
</c:if>
</tr>
</table>

