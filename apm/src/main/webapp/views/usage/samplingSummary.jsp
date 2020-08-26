<!DOCTYPE HTML>
<%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>	
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>

<%@ page import="com.ascap.apm.controller.utils.Utils"%>
<%@ page import="com.ascap.apm.controller.utils.HtmlSelectOption"%>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>	
<html>
<head><title>APM</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 

<%@ include file= "/views/common/uiWorkMatchWidges.jsp"%>
<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css">

<script type="text/javascript">

$(function() {
$('.noDblClick').dblclick(function(e){ 
    e.preventDefault();
});
});

function refresh() {
	document.forms[0].operationType.value='';
	document.forms[0].submit();
}

function submitForm(val1, val2, val3, obj) {
	var errorsFound = false;
	var playCntRange=  $.trim($("input[name=playCountRanges]:eq("+val1+")").val());
	console.log("--- ",playCntRange)
	if(playCntRange == '') {
		errorsFound = true;
		$("#errorMessages").html("");
		$("#serverErrorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.sampling.playcountranges.required"/></span>');
		unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	}
	else {		
		if(!allNumeric(playCntRange) ||  isNaN(playCntRange) || Number(playCntRange) <= 0 ) {
			errorsFound = true;
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.sampling.playcountranges.numeric"/></span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
		}
	}
	
	if(!errorsFound) {
		document.forms[0].callLetter.value=val2;
		document.forms[0].playCountRange.value=playCntRange;
		document.forms[0].samplingRequestId.value=val3;
		document.forms[0].operationType.value='ADD';
		document.forms[0].submit();
	}
}

function getDetails(muCall, stepCode) {
	document.forms[0].callLetter.value=muCall;
	document.forms[0].operationType.value='DETAILS';
	if('L2' === stepCode ){
		document.forms[0].operationType.value='DETAILS2';
	}
	document.forms[0].submit();
}

</script>

</head>
<body> 

<div class="menubg"  style="width: 100%; border: 0px; height:90px;"> 
<%@ include file = "/views/menu.jsp"%>
</div>
<div></div>

 <table class="errortable" style="font-size:1.2em;width:1000px;margin:0 auto;border:0px">
		<tr>
			<td>
				<div id="serverErrorMessages">
				<span class="txtGreen"><c:if test="${not empty systemmessage}"><li><c:out value="${systemmessage}" /></li></c:if></span>
				<span class="txtRed"><c:if test="${not empty systemerror}"><li><c:out value="${systemerror}"  escapeXml="false"/></li></c:if></span>
				</div>
				<div id="uierror"></div>
				<div id="errorMessages"></div>
			</td>
		</tr>
</table>
 
<form:form action="samplingSummary" modelAttribute="samplingSummary">
<form:hidden  path="operationType" />
<form:hidden  path="callLetter" />
<form:hidden  path="playCountRange" />
<form:hidden  path="samplingRequestId" />
<form:hidden path="targetSurveyYearQuarter" />
<br/>
<div class="o50" style="font-size:1.3em;height:2em;padding-top:0.5em;font-weight:bold;width:1000px;margin:0 auto;border:0px #cccccc solid;">
Music Users Subject to Sampling for Target Distribution: <c:out value="${samplingSummary.targetSurveyYearQuarter}"></c:out> 
</div>
<br/>
 
 <table class="errortable" style="font-size:1.2em;width:1000px;margin:0 auto;border:0px">
		<tr>
			<td>
				<div id="serverErrorMessages">
				<span class="txtGreen"><c:out value="${systemmessage}" /></span>
				<span class="txtRed"><c:out value="${systemerror}" escapeXml="false"/></span>
				</div>
				<div id="uierror"></div>
				<div id="errorMessages"></div>
			</td>
		</tr>
</table>

<table class="contenttable alternatecolors" id="mainData" style="font-size:1.3em;width:1000px;margin:0 auto;border:0px #cccccc solid;" >     
<thead> 
<tr class="tablerowheader" style="line-height: 2.5em;" >    
	<th width="15%">Music User</th>
	<th width="20%">Load Status</th>
	<th width="15%">Sampling Status</th> 
	<th width="20%">Current Step</th>
	<th width="30%">Action</th>  
</tr>
</thead>

<tbody>
<% int playCountRowNum = 0; %>  	
  <c:if test ="${ not empty samplingSummary.searchResults}"	>
  <c:forEach var="sampleRequest" items="${samplingSummary.searchResults}"  varStatus="currentIndexId">
  <c:choose>
   <c:when test="${currentIndexId.index % 2 == 0 }">
 <tr class="o20" style="line-height:1.7em;">
 </c:when>
 <c:otherwise>
 <tr style="line-height:1.7em;">
 </c:otherwise>
         </c:choose>
<td><c:out value="${sampleRequest.callLetter}"/></td>

<td>
<c:if test ="${not empty sampleRequest.loadStatus}">
<c:out value="${sampleRequest.loadStatus}">
</c:out>
</c:if>
</td>
<td>

<c:if test="${sampleRequest.manMatchIndicator eq 'Y'}"> 
				Not Applicable  
</c:if>
		
<c:if test="${sampleRequest.manMatchIndicator ne 'Y'}"> 
			
			<c:if test="${sampleRequest.samplingStatusCode eq 'PE'}"> 		
				Not Started 
				</c:if>
				<c:if test="${sampleRequest.samplingStatusCode ne 'PE'}"> 
				
				<c:if test="${sampleRequest.samplingStatusCode ne 'CO'}"> 
			
					<c:if test="${sampleRequest.samplingStatusCode eq 'PR'}"> 
					In Progress	
					</c:if>
					<c:if test="${sampleRequest.samplingStatusCode eq 'AB'}"> 
					Error	
					</c:if>
					<c:if test="${sampleRequest.samplingStatusCode eq 'CA'}"> 
					Cancelled	
					</c:if>
					<c:if test="${sampleRequest.samplingStatusCode eq 'BP'}"> 
					In Progress	
					</c:if>
					<c:if test="${sampleRequest.samplingStatusCode eq 'BD'}"> 
					Completed	
					</c:if>		
					</c:if>
					<c:if test="${sampleRequest.samplingStatusCode eq 'CO'}"> 				
					Completed
					</c:if>
			</c:if>
</c:if>				
</td>

<td>
<c:if test="${not empty sampleRequest.stepCode}">
<c:out value="${sampleRequest.stepDescription}"></c:out> - <c:out value="${sampleRequest.statusDescription}"></c:out>
</c:if>
</td>
<td>

<c:if test ="${sampleRequest.manMatchIndicator eq 'Y'}">
				No Action Applicable 
</c:if>

<c:if test ="${sampleRequest.manMatchIndicator ne 'Y'}">

                 <c:if test ="${samplingSummary.hasActiveSamplingProcess eq 'N'}">
				 
				 <c:if test ="${sampleRequest.statusCode eq 'PE'}">
					
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L1_REQUEST%>" value="Y">
					<a href="javascript:void(0)" id="_ConinueSampling" class="noDblClick" onclick="javascript:checkSub(this);javascript:submitForm('<%=playCountRowNum%>','<c:out value="${sampleRequest.callLetter}"/>','<c:out value="${sampleRequest.samplingRequestId}"/>',this);">Start Sampling</a>
					</prep:hasPermission>
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L1_REQUEST%>" value="N">
						Start Sampling
					</prep:hasPermission>
					&nbsp;&nbsp;
					<input type="text" name="playCountRanges" value="${sampleRequest.playCountRanges}" size="3" maxlength="3"/><% playCountRowNum++; %> 
					</c:if>
					
					 <c:if test ="${sampleRequest.statusCode ne 'PE'}">
						    <c:if test ="${sampleRequest.statusCode ne 'BD'}">
								 <c:if test ="${sampleRequest.statusCode ne 'CO'}">
									 <c:if test ="${sampleRequest.statusCode ne 'CA'}">
									<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L1_REQUEST%>" value="Y">
									<a href="javascript:void(0)" id="_ConinueSampling" class="noDblClick" onclick="javascript:checkSub(this);javascript:submitForm('<%=playCountRowNum%>','<c:out value="${sampleRequest.callLetter}"/>','<c:out value="${sampleRequest.samplingRequestId}"/>',this);">Start Sampling</a>
									</prep:hasPermission>
									<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L1_REQUEST%>" value="N">
										Start Sampling
									</prep:hasPermission>
							&nbsp;&nbsp;<input type="text" name="playCountRanges" value="${sampleRequest.playCountRanges}" size="3" maxlength="3"/><% playCountRowNum++; %>
									</c:if>
									<c:if test ="${sampleRequest.statusCode ne 'CA'}">
									     The System is working on the request. Please check back later. 
								</c:if>
								</c:if>
								
								<c:if test ="${sampleRequest.statusCode eq 'CO'}">
								</c:if>
							</c:if>
							</c:if>
						</c:if>
				</c:if>
				
				<c:if test ="${samplingSummary.hasActiveSamplingProcess eq 'Y'}">
				
				 <c:if test ="${sampleRequest.statusCode ne 'PE'}">
				
				<c:if test ="${sampleRequest.statusCode ne 'CO'}">
				<c:if test ="${sampleRequest.statusCode eq 'PR'}">
						The System is working on the request. Please check back later. 
						</c:if>
						<c:if test ="${sampleRequest.statusCode eq 'AB'}">
						Process Aborted. Please contact APM Tech Support. 
						</c:if>
						<c:if test ="${sampleRequest.statusCode eq 'CA'}">
						</c:if>
						</c:if>
						<c:if test ="${sampleRequest.statusCode eq 'CO'}">
						<c:if test ="${sampleRequest.stepCode ne 'L3'}">
							<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L1_REQUEST%>" value="Y">
								<a href="javascript:void(0)" id="_ConinueSampling" onclick="javascript:getDetails('<c:out value="${sampleRequest.callLetter}"/>','<c:out value="${sampleRequest.stepCode}"/>');">Continue Sampling</a>
							</prep:hasPermission>	
							<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L1_REQUEST%>" value="N">
								Continue Sampling
							</prep:hasPermission>
							</c:if>
							</c:if>
							</c:if></c:if>

</td>

</tr>
 </c:forEach>
 </c:if>
</tbody>
</table>

<table class="buttonstable" style="width: 1000px;margin: 0px auto;">
<tr>
<td width="30%" align="left">
<br/><prep:uiWidget type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_REFRESH %>" id="<%=UIWidgetConstants.ID_REFRESH%>" title="Cancel Sampling">onclick="javascript:refresh();"</prep:uiWidget>
</td>
<td width="70%"></td>
</tr>
</table>
</form:form>
</body>
</html>