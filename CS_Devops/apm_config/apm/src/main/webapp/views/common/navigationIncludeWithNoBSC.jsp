<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>

<%try{
	boolean backToSearchButtonNeeded = false;	
%>



<form:hidden path="showBackToSearchButton" value="N" />
<%@include file="/views/common/navigationButtonsInclude.jsp"%>
<%}catch(Exception e){
%>	
	Error in navigationIncludeWithNoBSC 
<%}%>