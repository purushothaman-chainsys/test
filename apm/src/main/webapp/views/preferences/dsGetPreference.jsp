<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>

<!DOCTYPE HTML>

<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.controller.utils.*"%>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %> 
<% pageContext.setAttribute("themes", HtmlSelectOption.getLookUpTable("ThemePaths"), PageContext.PAGE_SCOPE); %>

<script type="text/javascript">

function submitForm(){
	document.preferencesForm.submit();
}


</script>
<html>
<head>
<title>APM</title>
<%@ include file="/views/common/uiWidgets.jsp"%> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
$(function(){
	$('div.themeclass').each(function(n){ 
		console.log('Each '+ n);
		$(this).load(
			'/PrepWeb/common/uiThemes.jsp',
			{ theme: (n+1) }
			);
	});
});	

</script>
</head>
<body>  
<div class="menubg"  style="width: 100%; border: 0px; height:90px;"> 
	<jsp:include page="/views/menu.jsp"/>
</div>
<div></div>
<fieldset class="fieldsetfull"><legend class="legendfull">My Preferences [PF-100]</legend>

<form:form modelAttribute="userPreferences" name= "preferencesForm"   action ="submitPreferences">
	<form:hidden 	path="userId" />	
	
	<%--------------------------------- TITLE TABLE ------------------------------------%>

	<table class="titletable" style="display:none;">
		<tr>
			<td align="left">My Preferences [PF-100]</td>
		</tr>
	</table>

	<%--------------------------------- ERROR MESSAGE TABLE ------------------------------------%>
	<table class="errortable">
		<tr>
			<td width="100%">
			<span class="txtGreen"><c:if test="${not empty systemmessage}"><li><c:out value="${systemmessage}" /></li></c:if></span>
				<span class="txtRed"><c:if test="${not empty systemerror}"><li><c:out value="${systemerror}" escapeXml="false"/></li></c:if>
				<c:forEach var="systemerror" items="${systemerrorlist}">
				<li><c:out value="${systemerror}" escapeXml="false"/></li>
				</c:forEach>
				</span>
			</td>
		</tr>
	</table>
	
	
	
	<table class="detailstable" style="width:50%;margin:auto;">
		<tr>
			<td>&nbsp;</td>
		</tr>
		
		<tr height="10px"></tr>
		<tr>
			<td>Maximum Search Results</td>
			<td><form:input size="10" maxlength="5"  path="maxSearchResults"  /></td>
		</tr>
		<tr height="10px"></tr>
		<tr>
			<td>Search Results per Page</td>
			<td><form:input size="10" maxlength="5"  path="nofSrchRsltsPerPage" /> 	</td>
			
		</tr>
		 
		<tr height="10px"></tr>
		<tr>
			<td>Theme</td>	
			<td>
			<%
			request.setAttribute("themeTypeCode", com.ascap.apm.common.utils.constants.PrepConstants.THEME_RANDOM);
			%>
				<form:select path="themeTypeCode">
						<form:options items="${themes}" itemLabel="displayName" itemValue="id" />
				</form:select>
				<c:if test="${not empty sessionScope[UserSessionState.HTTP_SESSION_KEY].userPreference }">
			<c:if test ="${sessionScope[UserSessionState.HTTP_SESSION_KEY].userPreference.themeTypeCode  eq themeTypeCode }">
					<c:if test="${not empty sessionScope[UserSessionState.HTTP_SESSION_KEY].userPreference.randomThemeTypeCode }">
					 <c:set var ="randomThemeDes" value="${sessionScope[UserSessionState.HTTP_SESSION_KEY].userPreference.randomThemeTypeCode}"/>
						<span class="txtBlk">			
							<%= HtmlSelectOption.getLookUpTable("ThemePaths", (String)pageContext.getAttribute("randomThemeDes")) %>
						</span>	
			</c:if>
			</c:if>
			</c:if>
				
			</td>
		</tr>
		
		<tr height="10px"></tr>
		<tr>
			<td>Background</td>	
			<td>
			<form:select  path="backgroundThemeFlag" >
				<form:option value="N">No</form:option>
				<form:option value="Y">Yes</form:option>
			</form:select>
			
			
			</td>
		</tr>	
		<tr height="20px"></tr>
		
	</table>		
	
	
	<table class="navigationtable"  style="width:50%;margin:auto;">
		<tr>

			<td align="right"><prep:uiWidget
				type="<%= SecurityConstants.ANCHOR_TYPE %>"
				label="<%= UIWidgetConstants.LABEL_RESET %>"
				id="<%=UIWidgetConstants.ID_RESET %>"
				title="<%= UIWidgetConstants.LABEL_RESET %>"> 
								onclick="javascript:document.forms[0].reset();"
				</prep:uiWidget>
				<prep:uiWidget 
				type="<%= SecurityConstants.ANCHOR_TYPE %>"
				label="<%= UIWidgetConstants.LABEL_UPDATE %>"
				id="<%=UIWidgetConstants.ID_UPDATE %>"
				title="<%= UIWidgetConstants.LABEL_UPDATE %>">
									onclick="javascript:submitForm();"
					</prep:uiWidget>
					</td>

		</tr>
</table>
</form:form>
</fieldset>	
</body>
</html>

