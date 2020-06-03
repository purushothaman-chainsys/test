<!DOCTYPE HTML>
<%@page	language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
	
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>

<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>
<%@ page import="com.ascap.apm.controller.utils.Utils"%>
<%@ page import="com.ascap.apm.controller.utils.HtmlSelectOption"%>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>	

<% pageContext.setAttribute("exemptionTypes", HtmlSelectOption.getLookUpTable("ExemptionTypes"), PageContext.PAGE_SCOPE); %>

<html>
<head>
<title>APM</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 

<%@ include file= "/views/common/uiWorkMatchWidges.jsp"%>
<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css">

<style>
.redError {color:#FF0000; }
.uppercase {text-transform:uppercase}
</style>

<script type="text/javascript">

$(function() {
	sortTable('WM-100','mainData', [0,1]);
	
	$('#_Delete').click(function() {		
			var validForUnmatch = false;
			
			if($("table#mainData tr input[name='selectedIndex']:checked").length > 0) {
					validForUnmatch = true;
			}
			if(validForUnmatch) {
				$("#serverErrorMessages").html("");
				$("#uierror").html("");				
		       	$( "#dialog:ui-dialog" ).dialog( "destroy" );
		       	var newDiv = $(document.createElement('div')); 
		       	var txt = 'Are you sure you want to delete the ';
		       	txt = txt + ' selected exemption(s)? ';
		       	var rowCount = 0;
		       	$("input[name=selectedIndex]:checked").each(function(){
					rowCount = rowCount + 1;
				});			
				newDiv.html(txt);
				newDiv.attr('title','Delete?');
		 		newDiv.dialog({
		 			resizable: false,
		 			height:'auto',
 					minHeight: 'auto',
		 			modal: true,
 					show: "slide",
 					hide: "slide",
		 			buttons: {
		 				Cancel: function() {
		 					$( this ).dialog( "close" );
		 					},		 					
		 				"OK": function() {
		 					$( this ).dialog( "close" );
		 					deleteExempts(rowCount);
		 				}
		 			}
				});
				}
				else {
					$("#errorMessages").html("");
					$("#serverErrorMessages").html("");
					$("#uierror").html("");
					$("#uierror").html('<span class=txtRed><li><spring:message code="error.usage.generic.required.delete" arguments="Exemption"/></li></span>');
					//unCheckSub(obj);
		 			location.href="#"; 
		 			return;
				}
		});
});	
	
function deleteExempts(rowCount) {
	document.forms[0].operationType.value = 'DELETE';
	document.forms[0].submit();
}

function setNavigationType(navigation) {
		document.forms[0].operationType.value = 'SEARCH';
		document.forms[0].navigationType.value = navigation;
		document.forms[0].submit();
}
function filter() {
		if($('#_filterExemptionType').val()=='') {
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html("");
			$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.exemption.filter.required"/></li></span>');
		 	location.href="#"; 
		 	return;
		}
		document.forms[0].operationType.value = 'SEARCH';
		document.forms[0].submit();
}

function changeAction(oType) {
		document.forms[0].operationType.value = oType;
		document.forms[0].submit();
}

function retrieveExemption(f,t,v) {
	document.forms[0].operationType.value = 'RETRIEVE_EXEMPTION';
	document.forms[0].selectedId.value = v;
	document.forms[0].submit();
}
</script>
</head>
<body>

<div class="menubg"  style="width: 100%; border: 0px; height:90px;"> 
<%@ include file = "/views/menu.jsp"%>
</div>
<div></div>
 
<table class="titletable">
<tr>
	<td>Dedup Exemptions [EX-100]</td>
</tr>
 </table>
 
 <table class="errortable">
		<tr>
			<td>
				<div id="serverErrorMessages">
				<span class="txtGreen"><c:if test="${not empty systemmessage }"><li> <c:out value="${systemmessage}"/></li></c:if></span>
				<span class="txtRed"><c:if test="${not empty systemerror }"><li><c:out value="${systemerror}" escapeXml="false"/></li></c:if></span>
				</div>
				<div id="uierror"></div>
				<div id="errorMessages"></div>
			</td>
		</tr>
</table>
 
<form:form action="exemptionList" modelAttribute="exemptionList">
<form:hidden  path="navigationType" />
<form:hidden  path="operationType" />
<form:hidden  path="selectedId" />

<div style= "">
   
<table class="detailstable1000" style="width:1000px; font-size: 11px; font-weight: bold; color: #000000; line-height: 11px; margin:0 auto;"> 
	<tr>	
		<td>Exemption Type<br><form:select path="filterExemptionType" id="_filterExemptionType">
						<form:option value="" />
						<form:options items="${exemptionTypes}" itemLabel="displayName" itemValue="id" />
		</form:select></td>
		<td>Exemption Value<br><form:input path="filterExemptionValue" styleClass="uppercase" maxlength="170" /></td>
		<td>	
</td>
	</tr>
</table> 
</div>
<table class="buttonstable" style="width: 1000px; margin: 0px auto;">
 <tr>
 <td align="left" width="10%">
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_SEARCH %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_APPLY_FILTER %>" id="<%=UIWidgetConstants.ID_APPLY_FILTER%>" title="Filter">onclick="javascript:filter();"</prep:uiWidget>
 </td>	
<td align="left" valign="bottom" width="15%" style="padding-left:50px;">	
</td>
 <td width="52%" align="left"><%@ include file = "/views/common/navigationIncludeWithNoBSC.jsp"%></td>
 <td width="18%" valign="bottom" align="right">
 </td>
</tr>
 </table>
 
<table class="contenttable alternatecolors" id="mainData" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >     
<thead> 
<tr class="tablerowheader" >
	<th width="5%"></th> 
	<th width="5%"></th> 
	<th width="20%">Exemption Type</th>
	<th width="70%">Exemption Value</th>
</tr>
</thead>
<tbody>

<c:if test="${not empty exemptionList.searchResults}">
<c:forEach  var="obj" items="${exemptionList.searchResults}" varStatus="currentIndexId">	
<c:choose>
	<c:when test="${currentIndexId.index % 2 == 0}" >		
<tr class="o20">
</c:when>
<c:otherwise> 
<tr>
 </c:otherwise>
</c:choose>
<td align="center">
	<form:checkbox path="selectedIndex" style="padding-top:0px;" value="${obj.exemptionId}"></form:checkbox>
</td>
<td align="center">
<a href="javascript:retrieveExemption(document.exemptionList, '', '<c:out value='${obj.exemptionId}'/>');" class="<%=UIWidgetConstants.STYLE_VIEW_ICON_ENABLED%>" title="View Details"></a>
</td>

<c:set var="eType" value="${obj.exemptionType}"/>
<%String eType =(String)pageContext.getAttribute("eType");%>

<td valign="middle" align="left">
<%=HtmlSelectOption.getLookUpTable("ExemptionTypes",eType)
%>
</td>

<td valign="middle" align="left">
 <c:out value="${obj.exemptionValue}"/>	
</td>
</tr>
</c:forEach>
</c:if>

</tbody>
</table>	

<table class="buttonstable" style="width: 1000px; margin: 0px auto;">
 <tr>
 <td align="left" width="10%">
 	<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_EX_100_EXEMPTION_DELETE %>" type="<%=SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_DELETE %>" id="<%=UIWidgetConstants.ID_DELETE%>" title="Delete selected Exemptions">onclick="javascript:void(0);"</prep:uiWidget>
 
  </td>	
<td align="left" width="15%">
	<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_EX_101_EXEMPTION_CREATE %>" type="<%=SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_ADD %>" id="<%=UIWidgetConstants.ID_ADD%>" title="Add New Exemption">onclick="javascript:changeAction('ADD_NEW_EXEMPTION');"</prep:uiWidget>
</td>
 <td width="52%" align="left"><%@ include file = "/views/common/navigationIncludeWithNoBSC.jsp"%></td>
 <td width="18%" valign="bottom" align="right">
 </td>
</tr>
 </table>
 </form:form>
 
</body>
</html>