<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>

<%
try{
	boolean backToSearchButtonNeeded = true;	
%>

<%
	String showBackToSearchButtonVal = request.getParameter("showBackToSearchButton");
	if(showBackToSearchButtonVal != null && showBackToSearchButtonVal.equals("N")) {
		backToSearchButtonNeeded = false;
		%>
		<form:hidden path="showBackToSearchButton" value="<%=showBackToSearchButtonVal%>"/>
	<% }else{ %>
		<form:hidden path="showBackToSearchButton" />
	<% } %>



<%@include file="/views/common/navigationButtonsInclude.jsp" %>

<%}catch(Exception e){
%>	
	Error in navigationInclude
<%}%> 