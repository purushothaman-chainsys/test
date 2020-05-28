<!DOCTYPE HTML>
<%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>
<%@ page import="com.ascap.apm.controller.utils.Utils"%>
<%@ page import="com.ascap.apm.controller.utils.HtmlSelectOption"%>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>	
<html>
<head><title>APM</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 
<%@ include file="/views/common/uiWidgets.jsp" %>
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

function submitReset(obj) {
			unCheckSub(obj);
	 		location.href="#"; 
	 		return;
}

function submitForm(rownum, val, obj) { 
	var totalsamples=  $.trim($("input[name=samplesRequested]:eq("+rownum+")").val()); 
	var errorsFound = false;
 	if(!allNumeric(totalsamples) ||  isNaN(totalsamples) || Number(totalsamples) < 0 ) {
			errorsFound = true;
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.catalogsampling.samplesize.numeric"/></span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
		} 
 	if( Number(totalsamples) == 0 ) {
 		var newDiv = $(document.createElement('div')); 
       	var txt = ' You have selected to bypass manual match.  Are you sure you want to continue? ';		       	
		newDiv.html(txt);
		newDiv.attr('title','Warning:');
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
 					submitReset(obj);
 					},		 					
 				"OK": function() {
 					$( this ).dialog( "close" );
 					//rollupSub();
 					document.forms[0].sampleCount.value=totalsamples;
					document.forms[0].samplingRequestId.value=val;
					document.forms[0].operationType.value='PROCESS';
					document.forms[0].submit();
 				}
 			}
		 });
	}
	else { 	
		if(!errorsFound) { 
			document.forms[0].sampleCount.value=totalsamples;
			document.forms[0].samplingRequestId.value=val;
			document.forms[0].operationType.value='PROCESS';
			document.forms[0].submit();
		}
	}
} 

function closeCatalog(rownum, requestId, obj) { 
    var errorsFound = false;   
 		var newDiv = $(document.createElement('div')); 
       	var txt = ' You have selected to close catalog.  Are you sure you want to continue? ';		       	
		newDiv.html(txt);
		newDiv.attr('title','Warning:');
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
 					submitReset(obj);
 					},		 					
 				"OK": function() {
 					$( this ).dialog( "close" ); 
					document.forms[0].samplingRequestId.value=requestId;
					document.forms[0].operationType.value='CLOSE';
					document.forms[0].submit();
 				}
 			}
		 }); 

}
</script>
  
</head>
<body> 
 
<div class="menubg"  style="width: 100%; border: 0px; height:90px;"> 
<jsp:include page="/views/menu.jsp"/>
</div> 
 
 <table class="errortable" style="font-size:1.2em;width:1000px;margin:0 auto;border:0px">
		<tr>
			<td>
				<div id="serverErrorMessages">
				<span class="txtGreen"><c:out value="${systemmessage}" /></span>
				<span class="txtRed"><c:out value="${systemerror}" /></span>
				</div>
				<div id="uierror"></div>
				<div id="errorMessages"></div>
			</td>
		</tr>
</table>
<form:form action="catalogSamplingSummary" modelAttribute="catalogSamplingSummary">
<form:hidden path="samplingRequestId" />
<form:hidden path="operationType" /> 
<form:hidden path="sampleCount" />

<br/>
<div class="o50" style="font-size:1.3em;height:2em;padding-top:0.5em;font-weight:bold;width:1000px;margin:0 auto;border:0px #cccccc solid;">
Catalog Load Status
</div>
<br/>
<table class="contenttable alternatecolors" id="mainData" style="font-size:1.3em;width:1000px;margin:0 auto;border:0px #cccccc solid;" >     
<thead> 
<tr class="tablerowheader" style="line-height: 2.5em;" >    
	<th width="20%">Supplier</th>
	<th width="10%">Period</th>
	<th width="20%">Load Status</th> 
	<th width="35%">Sampling Status/ Action</th> 
	<th width="15%">Current Step</th>  
</tr>
</thead>
<tbody>
 <% int sampleRowNum = 0; %>  	 	
<c:if test="${not empty catalogSamplingSummary.searchResults}">
<c:forEach items="${catalogSamplingSummary.searchResults}" var="sampleRequest" varStatus="currentIndexId">
<c:choose>
<c:when test="${currentIndexId.index % 2 == 0}">		
<tr class="o20" style="line-height:1.7em;">
</c:when>
 <c:otherwise>
<tr style="line-height:1.7em;">
</c:otherwise> </c:choose>
<td><c:out value="${sampleRequest.callLetter}"/></td>
<td> 
   <c:out value="${sampleRequest.processStartTime}"/> 
</td>
<td> 
		<c:if test="${sampleRequest.loadStatus eq 'ND'}">
		No Data
		</c:if>
		<c:if test="${sampleRequest.loadStatus eq 'AM'}">
		Auto Match in Progress
		</c:if>
		<c:if test="${sampleRequest.loadStatus eq 'PS'}">
		Pending Sampling	
		</c:if>
		<c:if test="${sampleRequest.loadStatus eq 'MM'}">	
		Manual Match in Progress
		</c:if>
		<c:if test="${sampleRequest.loadStatus eq 'CO'}">
		Completed	
		</c:if>
		<c:if test="${sampleRequest.loadStatus eq 'PC'}"> 
		Pending Close
		</c:if>
		<c:if test="${sampleRequest.loadStatus eq 'CL'}">
		Closed
		</c:if> 
</td> 

<td>
    	<c:if test="${sampleRequest.loadStatus eq 'PS'}">
    		<c:if test="${sampleRequest.statusCode eq 'PE'}">
		Not Started 
			<c:if test="${catalogSamplingSummary.hasActiveSamplingProcessOrInError eq 'N'}">  
			 
			 <prep:hasPermission name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L1_REQUEST%>" value="Y">
			 	- <br/> <a href="javascript:void(0)" id="_ConinueSampling" class="noDblClick" onclick="javascript:checkSub(this);javascript:submitForm('<%=sampleRowNum%>','${sampleRequest.samplingRequestId}',this);">Start Sampling</a>
			 </prep:hasPermission>
			 <prep:hasPermission name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L1_REQUEST%>" value="N">
			  	- Start Sampling 
			 </prep:hasPermission>
			  &nbsp;&nbsp;<form:input path="samplesRequested" size="3" maxlength="5"/>
			  <% sampleRowNum++; %>  -
			</c:if>  
			</c:if>
		</c:if>	
		<c:if test="${sampleRequest.statusCode eq 'CA'}">
		Cancelled
			<c:if test="${catalogSamplingSummaryForm.hasActiveSamplingProcessOrInError eq 'N'}">
			
			<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L1_REQUEST%>" value="Y">
			 	- <a href="javascript:void(0)" id="_ConinueSampling" class="noDblClick" onclick="javascript:checkSub(this);javascript:submitForm('<%=sampleRowNum%>','${sampleRequest.samplingRequestId}',this);">Start Sampling</a>
			</prep:hasPermission>
			
			<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L1_REQUEST%>" value="Y">
			 	- Start Sampling 
			</prep:hasPermission>
			
			 &nbsp;&nbsp;<form:input path="samplesRequested" size="3" maxlength="5"/>
			  <% sampleRowNum++; %>  -
			 </c:if>  
			
		</c:if>
		<c:if test="${sampleRequest.statusCode eq 'PR'}">
		In Progress	- System is working on the request. Please check back later  
		</c:if> 
		<c:if test="${sampleRequest.statusCode eq 'IQ'}">
		In Progress	- System is working on the request. Please check back later	 
		</c:if>
		<c:if test="${sampleRequest.statusCode eq 'BP'}">	
		In Progress	- System is working on the request. Please check back later	 
		</c:if>	
		<c:if test="${sampleRequest.statusCode eq 'BD'}">	
		Bypass Completed - 
		</c:if>
		<c:if test="${sampleRequest.statusCode eq 'CO'}">
		
		Completed - 
		</c:if>
		<c:if test="${sampleRequest.statusCode eq 'AB'}">
		Error - Please contact ISD Support 
		</c:if>  
		<c:if test="${sampleRequest.loadStatus ne 'PC'}">
		<c:if test="${sampleRequest.loadStatus ne 'CL'}">
		<c:if test="${sampleRequest.loadStatus ne 'ND'}">
			<c:if test="${sampleRequest.statusCode ne 'PR'}">
			<c:if test="${sampleRequest.statusCode ne 'IQ'}">
			<c:if test="${sampleRequest.statusCode ne 'AB'}">
			<c:if test="${sampleRequest.statusCode ne 'BP'}">
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L1_REQUEST%>" value="Y"> 
						  	<a href="javascript:void(0)" id="_ConinueSampling" class="noDblClick" onclick="javascript:checkSub(this);javascript:closeCatalog('<%=sampleRowNum%>','${sampleRequest.samplingRequestId}',this);">Close Catalog</a>
					 </prep:hasPermission>
					 <prep:hasPermission name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L1_REQUEST%>" value="N"> 
						  	   Close Catalog 
				     </prep:hasPermission>
			</c:if> 
			</c:if> 
			</c:if> 
			</c:if>  
		</c:if> 
		</c:if> 
		</c:if> 
</td>
<td>    <c:if test="${sampleRequest.statusCode eq 'PR'}">
		Picked up by process	
		</c:if>
		<c:if test="${sampleRequest.statusCode eq 'IQ'}">
		Waiting in queue 	
		</c:if>
		<c:if test="${sampleRequest.statusCode eq 'BP'}">	
		Bypass In Progress	
		</c:if>
		<c:if test="${sampleRequest.loadStatus eq 'PC'}">
		Waiting in queue 
		</c:if>
</td>
</c:forEach>
</c:if>
</tbody>
</table>
<table class="buttonstable">
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