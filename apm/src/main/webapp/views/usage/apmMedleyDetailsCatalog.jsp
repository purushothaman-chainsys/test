<!DOCTYPE HTML>
<%@page	language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>

<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>
<%@ page import="com.ascap.apm.controller.utils.Utils"%>
<%@ page import="com.ascap.apm.controller.utils.HtmlSelectOption"%>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>	

<%
com.ascap.apm.vob.usage.ApmPerformanceBulkRequestList xApmPerformanceBulkRequestListForm;
java.util.List<String> xMedleyOrigWorks = new java.util.ArrayList<String>();
java.lang.String xOperType = ""; 

String[] xMedleyCloneCountsTemp = null;

try{
	xApmPerformanceBulkRequestListForm =(com.ascap.apm.vob.usage.ApmPerformanceBulkRequestList) request.getAttribute("apmPerformanceBulkRequestList");
	if(xApmPerformanceBulkRequestListForm != null) {
		
		xMedleyOrigWorks = xApmPerformanceBulkRequestListForm.getMedleyOriginalWorkList();
		xOperType = xApmPerformanceBulkRequestListForm.getOperationType();
		xMedleyCloneCountsTemp = xApmPerformanceBulkRequestListForm.getMedleyCloneCounts();
		
	}
}

catch(ClassCastException cce){} 
%>



<html>
<head>
<title>APM</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%@ include file = "/views/common/uiWidgets1200.jsp"%>
<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css">
<style>
.redError {color:#FF0000; }
</style>



<script type="text/javascript">
var wLen=0;
$(function() {
	//console.log("medley work ids: "+   $("input[name=medleyWorkIds]").length);
	wLen = $("input[name=medleyWorkIds]").length;
});



function filter() {
		pleaseWait();
		document.forms[0].operationType.value = 'SEARCH';
		document.forms[0].submit();
}

function addRows()	{
	$("#mdlTbl>tbody").append('<tr><td><input type="checkbox" name="abcdef_chk"></input></td><td><input  name="medleyWorkIds" value="" />&nbsp;&nbsp;<input name="medleyCloneCounts" value="1" size="1" maxlength="2"/></td></tr>');	
}

function deleteRows() {
	if($("#mdlTbl>tbody tr input:checked").length == 0) {}
	$("#mdlTbl>tbody tr input:checked").parent().parent().remove();
}

function deleteRowsSel() {
	$("#mdlTbl>tbody tr input:checked").parent().parent().remove();
}




function confirmUnmatch(obj) {



var multiClone = false;
$("input[name=medleyCloneCounts]").each(function(){
	//alert('clone count '+ this.value);
	var readonly =  $(this).attr('readonly');
	if(readonly && Number(this.value) > 1) {
		multiClone = true;
	}
	
});


	if(multiClone && $("input[name=medleyOriginalExists]").val() !== 'Y') {	
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed>'+'<li><spring:message code="us.error.apm.manualmatch.multiplematch.original.none.unmatch"/>'+'</span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
	}	
				$("#serverErrorMessages").html("");
				$("#uierror").html("");				
		       	$( "#dialog:ui-dialog" ).dialog( "destroy" );
				var wLenNew = $("input[name=medleyWorkIds]").length;
				if(wLen === wLenNew) {
					$("#uierror").html('<span class=txtRed>'+'<li><spring:message code="us.error.apm.manualmatch.multiplematch.workid.unmatch.none"/>'+'</span>');
					unCheckSub(obj);
					location.href="#"; 
					return;
				}
		       	
		       	var numWorks = 0;

$("input[name=medleyCloneCounts]").each(function(){
	numWorks++;
});

//alert('one value ' + $("input[name=medleyCloneCounts]:eq(0)").val());
if(numWorks === 1  && $("input[name=medleyCloneCounts]:eq(0)").val() > 1) {

		$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed>'+'<li><spring:message code="us.error.apm.manualmatch.multiplematch.workid.one"/>'+'</span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
}
		       	
		       			       	
		       	var newDiv = $(document.createElement('div')); 
		       	var txt = 'Are you sure you want to unmatch the removed Work Id(s)?';
				newDiv.html(txt);
				newDiv.attr('title','Unmatch?');
		 		newDiv.dialog({
		 			resizable: false,
		 			height:'auto',
 					minHeight: 'auto',
		 			modal: true,
 					show: "slide",
 					hide: "slide",
		 			buttons: {
		 				Cancel: function() {
		 					unCheckSub(obj);
			 				location.href="#"; 
			 				//return;
		 					$( this ).dialog( "close" );
		 					},		 					
		 				"OK": function() {
		 					$( this ).dialog( "close" );
		 					updateMdl(obj);
		 				}
		 			}
				});
	}


function updateMdl(obj) {

		
		var updateProgressDiv = $(document.createElement('div')); 
		var txt = '<span style="font-size:1.3em;">Please Wait.. </span><br>APM is unmatching the selected work performances.<br><br><img src="<%=request.getContextPath()%>/images/indicator.gif">';
      
		updateProgressDiv.html(txt);
		updateProgressDiv.dialog({
		resizable: false,
		height:'auto',
 		minHeight: 'auto',
		modal: true,
		show: "slide",
		hide: "slide"
		});
		$(".ui-dialog-titlebar").hide();


	document.forms[0].operationType.value = 'MEDLEY_UPDATE';
	document.forms[0].submit();
}

function addMdl(obj) {
var multiClone = false;
var countsNumeric = true;
var countsInLimit = true;

var numWorks = 0;

$("input[name=medleyCloneCounts]").each(function(){
	numWorks++;
	var cloneValue = $(this).val();
	if(isNaN(cloneValue) || Number(cloneValue) <=0 || !allNumeric(cloneValue)) {
		countsNumeric = false;
	}
	if(countsNumeric) {
	if(Number(cloneValue) > 20) {
		countsInLimit = false;
	}
	var readonly =  $(this).attr('readonly');
		if(readonly && Number(cloneValue) > 1) {
			multiClone = true;
		}
	}
	
});

	if(numWorks <= 1 ) {
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed>'+'<li><spring:message code="us.error.apm.manualmatch.multiplematch.workid.one"/>'+'</span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
	}

	if(!countsInLimit) {
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed>'+'<li><spring:message code="us.error.apm.manualmatch.multiplematch.clonecount.maxlimit"/>'+'</span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
	}

	if(!countsNumeric) {
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed>'+'<li><spring:message code="us.error.apm.manualmatch.multiplematch.clonecount.numeric"/>'+'</span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
	}


	if(multiClone && $("input[name=medleyOriginalExists]").val() !== 'Y') {	
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed>'+'<li><spring:message code="us.error.apm.manualmatch.multiplematch.original.none.add"/>'+'</span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
	}
	
	
	
	

	var workIdNumeric = true;
	var errMsg = '';
	var workIdExists = false;
	$("input[name=medleyWorkIds]").each(function(){
			//workIdExists = true;
			$(this).val( $.trim(this.value));
			var wId = this.value;
			
			if($.trim(wId) === '' ) {
				workIdExists = false;
				workIdNumeric = false;				
				errMsg += '<li><spring:message code="us.error.apm.archives.workid.required"/>';
			}
			else {
				workIdExists = true;
				if(isNaN(wId) || Number(wId) <=0 || !allNumeric(wId)) {
						workIdNumeric = false;
						errMsg += '<li><spring:message code="us.error.apm.archives.workid.nonnumber"/>';
					}
				}
		});
		
		
	if(!workIdExists) {	
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed>'+'<li><spring:message code="us.error.apm.manualmatch.multiplematch.workid.none"/>'+'</span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
	}	

	if(workIdNumeric) {
	
	var uniquewids = $( "input[name=medleyWorkIds]" ).map(function(){
   		return this.value;
	}).get();
	uniquewids = jQuery.unique( uniquewids );
	if(uniquewids.length === 1) {
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed>'+'<li><spring:message code="us.error.apm.manualmatch.multiplematch.workid.one"/>'+'</span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
	}
	//alert('u length ' + uniquewids.length);
		
		var updateProgressDiv = $(document.createElement('div')); 
		var txt = '<span style="font-size:1.3em;">Please Wait.. </span><br>APM is updating the selected work performances.<br><br><img src="<%=request.getContextPath()%>/images/indicator.gif">';
      
		updateProgressDiv.html(txt);
		updateProgressDiv.dialog({
		resizable: false,
		height:'auto',
 		minHeight: 'auto',
		modal: true,
		show: "slide",
		hide: "slide"
		});
		$(".ui-dialog-titlebar").hide();
	
	
	
		document.forms[0].operationType.value = 'MEDLEY_ADD';
		document.forms[0].submit();
	}
	else {
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed>'+errMsg+'</span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
		}
}

 
</script>


</head>
<body> 

<div class="menubg"  style="width: 100%; border: 0px; height:90px;"> 
<jsp:include page="/views/menu.jsp"/> 
</div>

<table class="titletable" style="display:none" style="width:600px;">		
	<tr><td></td></tr>
</table>

<form:form action="catalogManualMatchRequest" modelAttribute="apmPerformanceBulkRequestList"> 
<p><p>


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


<div id="filtertarget111"  style="width:1200px; font-size: 11px; font-weight: bold; color: #000000; line-height: 11px; margin:0 auto;">


<%-- <fieldset class="fieldset600"><legend>Work Match Details[WM-101]</legend>--%>
<fieldset class="fieldsetfull" style="width:600px;"><legend class="legendfull">Catalog Match Details [CM-101]</legend>
<BR/>
<table style="font-size: 1.1em; width: 100%; font-weight: bold; color: #000000; line-height: 1.5em;;">
<tr>
	<td width="20%">Supplier:</td>
	<td width="80%">
	<c:if test="${not empty apmPerformanceBulkRequestList.medleySupplierCode}">	
	<c:out value="${apmPerformanceBulkRequestList.medleySupplierCode}"></c:out>
	</c:if>
	<c:if test="${empty apmPerformanceBulkRequestList.medleySupplierCode}">	
	All Suppliers	
	</c:if>
	</td>
</tr>
<tr>
	<td>Work Title:</td>
	<td><c:out value="${apmPerformanceBulkRequestList.medleyWorkTitle}"></c:out></td>
</tr>
<tr>
	<td>Performer:</td>
	<td> <c:out value="${apmPerformanceBulkRequestList.medleyPerformerName}"></c:out></td>
</tr>
<tr>
	<td>Writer:</td>
	<td><c:out value="${apmPerformanceBulkRequestList.medleyWriterName}"></c:out></td>
</tr>
</table>
<br/>

<table style="font-size: 1.0em; width: 100%; font-weight: bold; color: #000000; line-height: 1.5em;" id="mdlTbl">

<tbody>

<% boolean readonly = false; 
   boolean disableDelete = true; %>

<c:if test="${not empty apmPerformanceBulkRequestList.medleyWorkIds}">

<c:forEach var="source" items= "${apmPerformanceBulkRequestList.medleyWorkIds}" varStatus="currIndexId">

<% String wIdTemp = ""; %> 
<c:if test ="${not empty source}">
<c:set var="_wIdTemp" value="${source}"></c:set>
<%String _wIdTemp=(String)pageContext.getAttribute("_wIdTemp"); %>
<% wIdTemp = _wIdTemp;%>
</c:if>
<% if(!"MEDLEY_NEW".equals(xOperType) || xMedleyOrigWorks.contains(wIdTemp)) {
	readonly = true;  
}
if("MEDLEY_UNMATCH".equals(xOperType) || !xMedleyOrigWorks.contains(wIdTemp)) {
	disableDelete = false;
}
%>
<tr>
<td>
<% if(disableDelete) { %>
<input type="checkbox" name="abcdef_chk" disabled="disabled"/>
<% } else { %>
<input type="checkbox" name="abcdef_chk"/>
<% } %>
</td>
<td>

<% if (xMedleyOrigWorks.contains(wIdTemp)) { %>
<input type="hidden" name="medleyWorkOriginalInd" value="Y"/>
<input  name="medleyWorkIds" value="${_wIdTemp}" readonly="true" />
&nbsp;<form:input path="medleyCloneCounts" value="${apmPerformanceBulkRequestList.medleyCloneCounts[currIndexId.index]}" size="1" maxlength="2"  readonly="true"/>
<% } else { %>
<input type="hidden" name="medleyWorkOriginalInd" value="N"/>
<input  name="medleyWorkIds" value="${_wIdTemp}" />
&nbsp;<form:input path="medleyCloneCounts" value="${apmPerformanceBulkRequestList.medleyCloneCounts[currIndexId.index]}" size="1" maxlength="2"/>
<% } %>


</td>
</tr>
  
</c:forEach>  
</c:if>
<c:if test="${apmPerformanceBulkRequestList.operationType eq 'MEDLEY_NEW' }">
<tr>
<% disableDelete = true; %>
<td><input type="checkbox" name="abcdef_chk"/></td>
<td><input  name="medleyWorkIds" value=""/>
&nbsp;<form:input  path="medleyCloneCounts" value="1" size="1" maxlength="2"/>
</td>
</tr>

</c:if>


</tbody>

</table>

<br/>


<table style="width:60%;">
<tr>
<td align="left" style="font-size: 0.7em;width:30%;">
<c:if test="${apmPerformanceBulkRequestList.operationType ne 'MEDLEY_UNMATCH' }">
<prep:uiWidget id="<%=UIWidgetConstants.ID_ADD_NEW_ENTRY %>" type="<%= SecurityConstants.ICON_TYPE %>" >				    			    		
						onclick="javascript:addRows();"
					</prep:uiWidget>
</c:if>					
					<prep:uiWidget id="<%=UIWidgetConstants.ID_REMOVE %>" type="<%= SecurityConstants.ICON_TYPE %>" >				    			    		
						onclick="javascript:deleteRows();"
					</prep:uiWidget>
</td>
<td align="left" style="font-size: 0.7em;width:50%;">


<c:if test="${apmPerformanceBulkRequestList.operationType ne 'MEDLEY_UNMATCH' }">

<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_SUBMIT %>" id="<%=UIWidgetConstants.ID_SUBMIT%>" title="Update">onclick="javascript:addMdl(this);"</prep:uiWidget>
</c:if>


<c:if test="${apmPerformanceBulkRequestList.operationType eq 'MEDLEY_UNMATCH' }">
<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_SUBMIT %>" id="<%=UIWidgetConstants.ID_SUBMIT%>" title="Update">onclick="javascript:confirmUnmatch(this);"</prep:uiWidget>
</c:if>
<prep:uiWidget type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_CANCEL %>" id="<%=UIWidgetConstants.ID_CANCEL%>" title="Cancel">onclick="javascript:filter();"</prep:uiWidget>

</td>
</tr>					
</table>			
		
</div>
</fieldset>	
</div>

<form:hidden  path="apmPerformanceBulkRequestListType" value="CATALOG"/>
<form:hidden  path="medleyOriginalExists" />
<form:hidden  path="navigationType" />
<form:hidden  path="operationType" />
<form:hidden  path="medelyView" />
<form:hidden  path="writerExists" />

<form:hidden  path="medleySupplierCode" />
<form:hidden  path="medleyMultiWorkId" />
<form:hidden  path="medleyWorkTitle" />
<form:hidden  path="medleyPerformerName" />
<form:hidden  path="medleyWriterName" />

<form:hidden  path="filterSupplierCode" />
<form:hidden  path="filterWorkTitle" />
<form:hidden  path="workTitleSearchType" />
<form:hidden  path="filterPerformerName" />
<form:hidden  path="performerSearchType" />
<form:hidden  path="filterMatchStatusCode" />
<form:hidden  path="filterResultViewType" />

<input type="hidden" name="tempNoOfResults" value="<c:out value="${apmPerformanceBulkRequestList.numberOfRecordsFound}"></c:out>"/>
<input type="hidden" name="tempCurPageNr" value="<c:out value="${apmPerformanceBulkRequestList.currentPageNumber}"></c:out>"/>
 


<br/><br/>

<div>

<div style="filter: alpha(opacity=60);    -moz-opacity: 0.6;    -khtml-opacity: 0.6;    opacity: 0.6;">
<table class="contenttable alternatecolors" id="mainData" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >     
<thead> 
<tr class="tablerowheader" >
	<th width="2%"><input type="checkbox" name="sA" id="_sA" value="SA" onclick="javascript:manageSelectAll();"></th> 
	<th width="3%">Work ID</th>
    <c:if test="${apmPerformanceBulkRequestList.filterResultViewType eq 'SPLR' }"> 
	<th width="10%">Supplier</th>
	<th width="5%">MM ID</th>
	</c:if>
    <c:if test="${apmPerformanceBulkRequestList.writerExists eq 'Y' }">
	<th width="24%">Work Title</th>
	<th width="24%">Performer</th>
	<th width="18%">Writer</th>
	</c:if>
	
    <c:if test="${apmPerformanceBulkRequestList.writerExists eq 'N' }">
	<th width="31%">Work Title</th>
	<th width="31%">Performer</th>
	<th width="4%">Writer</th>
	</c:if>
	<th width="5%">Play Count</th>   
	<th width="5%">Rec Count</th>
	<th width="5%">Mult Work ID</th>
	<th width="5%">Priority</th>
	<th width="7%">Matched By</th> 
	<th width="7%">Assigned To</th>
</tr>
</thead>


<tbody>
  	
  	<c:if test="${not empty apmPerformanceBulkRequestList.searchResults}"> 
 <c:forEach var="apmPerformanceBulkRequest" items= "${apmPerformanceBulkRequestList.searchResults}" varStatus="currentIndexId"> 
 
<%-- <% if (currentIndexId % 2 == 0) { %>		
<tr class="o20">
<% } else  { %>	
<tr>
<% } %> --%>
<c:choose>
<c:when test="${currentIndexId.index % 2 == 0}">		
<tr class="o20">
</c:when>
<c:otherwise>	
<tr>
</c:otherwise>
</c:choose>
<td align="center">
<c:if test="${apmPerformanceBulkRequest.deleteFlag eq 'Y' }">
 <form:checkbox path="selectedIndex" style="padding-top:0px;"   value="${currentIndexId.index}"></form:checkbox> 
</c:if>

<c:if test="${apmPerformanceBulkRequest.deleteFlag ne 'Y' }">
 <form:checkbox path="selectedIndex" style="padding-top:0px;"   value="${currentIndexId.index}"></form:checkbox> 
</c:if>		
	<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].requestId"></form:hidden>

<td valign="middle" align="center">
<c:if test="${apmPerformanceBulkRequest.invalidWorkId eq 'Y' }">
<form:input path="workId" value="${apmPerformanceBulkRequest.workId}" maxlength="11" size="10" styleClass="redError" Id='"wId_"+${currentIndexId.index}'/>
</c:if>

<c:if test="${apmPerformanceBulkRequest.invalidWorkId ne 'Y' }">
<form:input path="workId" value="${apmPerformanceBulkRequest.workId}" maxlength="11" size="10" styleClass="redError" Id='"wId_"+${currentIndexId.index}'/>
</c:if>
</td>


<c:if test="${apmPerformanceBulkRequestList.filterResultViewType eq 'SPLR' }">
<%-- <logic:equal name="apmPerformanceBulkRequestListForm" property="filterResultViewType" value="SPLR"> --%>
	<td align="left"><c:out value="${apmPerformanceBulkRequest.supplierCode}"></c:out></td>
	<td align="left"><c:out value="${apmPerformanceBulkRequest.providerId}"></c:out></td>
</c:if>
<td align="left"><c:out value="${apmPerformanceBulkRequest.workTitle}"></c:out></td> 
<td align="left"><c:out value="${apmPerformanceBulkRequest.performerName}"></c:out></td>
<td align="left"><c:out value="${apmPerformanceBulkRequest.writerName}"></c:out></td>
<td align="right"><c:out value="${apmPerformanceBulkRequest.playCount}"></c:out></td>
<td align="right"><c:out value="${apmPerformanceBulkRequest.workPerfCount}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;<%-- <a href="#" onclick="javascript:getWorkPerfPopup('<bean:write name="apmPerformanceBulkRequest" property="workTitle" />','<bean:write name="apmPerformanceBulkRequest" property="performerName" />','<bean:write name="apmPerformanceBulkRequest" property="supplierCode" />')" style="text-decoration:none;"></a>--%></td>
<td align="right"><c:out value="${apmPerformanceBulkRequest.multWorkId}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;
<td align="right"><c:out value="${apmPerformanceBulkRequest.priority}"></c:out></td>
<td align="left"><c:out value="${apmPerformanceBulkRequest.manualMatchUserId}"></c:out></td>	
<td align="left"><c:out value="${apmPerformanceBulkRequest.assignedToUser}"></c:out></td>	

 
<input type="hidden"  name="multWorkId" value ="${apmPerformanceBulkRequest.multWorkId}" Id='"multWorkId_"+${currentIndexId.index}'/> 
<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].supplierCode" value ="${apmPerformanceBulkRequest.supplierCode}" Id='"supplierCode_"+${currentIndexId.index}'/> 
<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].performerName" value ="${apmPerformanceBulkRequest.performerName}" Id='"performerName_"+${currentIndexId.index}'/>
<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].workTitle" value ="${apmPerformanceBulkRequest.workTitle}" Id='"workTitle_"+${currentIndexId.index}'/>
<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].writerName" value ="${apmPerformanceBulkRequest.writerName}" Id='"writerName_"+${currentIndexId.index}'/>
<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].playCount" value ="${apmPerformanceBulkRequest.playCount}" Id='"playCount_"+${currentIndexId.index} '/>
<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].workPerfCount"  value ="${apmPerformanceBulkRequest.workPerfCount}" Id='"workPerfCount_"+${currentIndexId.index}'/>
<form:hidden  path="originalWorkId" value ="${apmPerformanceBulkRequest.originalWorkId}" Id='"originalWorkId_"+${currentIndexId.index}'/>
<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].manualMatchUserId" value ="${apmPerformanceBulkRequest.manualMatchUserId}" Id='"manualMatchUserId_"+${currentIndexId.index} '/>
<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].newWorkId" value ="${apmPerformanceBulkRequest.newWorkId}" Id='"newWorkId"+${currentIndexId.index} '/>
<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].priority"  value ="${apmPerformanceBulkRequest.priority}"  Id='"priority"+${currentIndexId.index} '/>
<form:hidden  path="assignedToUser"  value ="${apmPerformanceBulkRequest.assignedToUser}" Id='"assignedToUser_"+${currentIndexId.index} '/>
<input type="hidden" name="groupingSubpplierCode" value="<c:out value="${apmPerformanceBulkRequestList.filterSupplierCode}" /> "/>

</c:forEach>
</c:if>
</tbody>
</table>
</div>
</div>




</form:form>



</body>
</html>