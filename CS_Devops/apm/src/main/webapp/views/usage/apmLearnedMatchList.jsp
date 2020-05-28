<!DOCTYPE HTML>
<%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>

<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>
<%@ page import="com.ascap.apm.controller.utils.Utils"%>
<%@ page import="com.ascap.apm.controller.utils.HtmlSelectOption"%>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>	

<%pageContext.setAttribute("UsageSuppliersList", HtmlSelectOption.getLookUpTable("UsageSuppliers"), PageContext.PAGE_SCOPE); %>
<html>
<head>
<title>APM</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 

<%@ include file = "/views/common/uiWorkMatchWidges.jsp"%>
<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css">
<style>
.redError {color:#FF0000; }
.uppercase {text-transform:uppercase}
</style>

<script type="text/javascript">


$(function() {
	$('#mainData').css('line-height','1.7em');
	
	sortTable('LM-100','mainData', [0,1]);
	filterTable('LM-100','mainData' , [0,1]);
	
	
	$('a[id=_viewId]').click( function(){		
		var srow = $('a[id=_viewId]').index(this);
		document.forms[0].selectdRowId.value = srow;  		
		document.forms[0].operationType.value = 'RETRIEVE';
		document.forms[0].submit();
	});
		$('a[id^=_Delete]').click(function() {
			resetValues();
			
			var errorsFound = false;
			var nonMultRow = false; var multRow = false;
			
		$("input[name=selectedIndex]:checked").each(function(){
			var checkedIndex = this.value;
			if($.trim($("input[name=multWorkId]:eq("+checkedIndex+")").val()) == '') {
				nonMultRow = true;
			}
			if($.trim($("input[name=multWorkId]:eq("+checkedIndex+")").val()) != '') {
				multRow = true;
			}
			
		});
			
			if(nonMultRow && multRow) {
				//alert('both present');
				errorsFound = true;
				$("#serverErrorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.learnedmatch.delete.mixrows"/></span>');				
				unCheckSub($('a[id^=_Delete'));
				location.href="#";
				return;
			}
			
			//if(!errorsFound) {
			
			
			
			if($("table#mainData tr input[name='selectedIndex']:checked").length > 0) {	
				$("#serverErrorMessages").html("");
				$("#uierror").html("");				
		       	$( "#dialog:ui-dialog" ).dialog( "destroy" );
		       	
		       	var newDiv = $(document.createElement('div')); 
		       	var cnt = $("table#mainData tr input[name='selectedIndex']:checked").length;
		       	var txt = '';
		       	if(nonMultRow) {txt = 'WARNING: The '+cnt+' selected row(s) will be DELETED from the cross reference.  Do you want to continue?';}       	
		       	else if(multRow) {txt = 'The entire multiple works group will be deleted.  Do you want to proceed?  If you only want to delete one work in the group, please use the Update function to remove the work.';}
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
		 					deleteGroups(cnt);
		 				}
		 			}
				});
		    }
	    	else {
	    		$("#serverErrorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.archives.delete.selection"/></span>');
						 
				location.href="#";
				return;
	    	}
	    	//}
			
	    	
    	});
    	
    	$('a[id=_Match_to_Multiple_Works]').click(function() {
    		resetValues();
    		if($("table#mainData tr input[name='selectedIndex']:checked").length > 1) {    			
    			$("#serverErrorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.manualmatch.multiplematch.selection.gt1"/></span>'); 
				location.href="#";
				return;
    		}
    		else if($("table#mainData tr input[name='selectedIndex']:checked").length < 1) {    			
    			$("#serverErrorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.manualmatch.multiplematch.selection.lt1"/></span>'); 
				location.href="#";
				return;
			
    		}
    		else if($("table#mainData tr input[name='selectedIndex']:checked").length == 1){
    			var lrnDel = false;
    			$("input[name=selectedIndex]:checked").each(function(){
    				var cIndex = this.value;
    				if('Y'==$("input[name=learnedDeleteFlag]:eq("+cIndex+")").val()) {
						lrnDel = true;
					}
    			});
    			
    			if(lrnDel) {
    				$("#serverErrorMessages").html("");
					$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.learneddelete.multipleworks"/></span>'); 
					location.href="#";
					return;
    			}
    			
    			$("input[name=selectedIndex]:checked").each(function(){    				
					var checkedIndex = this.value;
					
					if( $.trim(  $("input[name=multWorkId]:eq("+checkedIndex+")").val()) != '') {
					//alert('val found');
					pleaseWait();
					document.forms[0].operationType.value = 'MEDLEY_RETRIEVE';
					document.forms[0].submit();
					}
					else {
						pleaseWait();
						document.forms[0].operationType.value = 'MEDLEY_NEW';
						document.forms[0].submit();
    				}
				});
    			
    		}
    	});
    	
}); 

function setNavigationType(navigation) {
		showProgress();
		document.forms[0].navigationType.value = navigation;
		document.forms[0].submit();
}

function filter() {
			fworkIdNumeric = true;
			ferrMsg = '';
			if($.trim($("input[name=filterWorkId]").val()) == '') {				
			}
			else {
				fwId = $.trim($("input[name=filterWorkId]").val());
				if(isNaN(fwId) || Number(fwId) <=0 || !allNumeric(fwId)) {
					fworkIdNumeric = false;
					ferrMsg += '<li><spring:message code="us.error.apm.archives.workid.nonnumber" arguments="${fwId}"/>';
				}
			}
 		if(!fworkIdNumeric) {
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed>'+ferrMsg+'</span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
		}
		else {
			showProgress();
			document.forms[0].operationType.value = 'SEARCH';
			document.forms[0].submit();
		}
}
function add(obj) {
	document.forms[0].operationType.value = 'ADD';
	document.forms[0].submit();
}

function update(obj) {
	resetValues();
	var wId;
	var errMsg = '';
	if($("table#mainData tr input:checked").length == 0) {	
			$("#serverErrorMessages").html("");
			$("#errorMessages").html("");
			$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.archives.update.selection"/></span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;}
	else {	 
		var workIdExists = true; var lrnDel = false; 		
		var workIdNumeric = true;
		var nonMultRow = false; var multRow = false;
		$("input[name=selectedIndex]:checked").each(function(){
			var checkedIndex = this.value;
			if($.trim($("#multWorkId_"+checkedIndex).val()) == '') {
				nonMultRow = true;
			}
			if($.trim($("#multWorkId_"+checkedIndex).val()) != '') {
				multRow = true;
			}
			
		});
			
			if(multRow) {
				$("#serverErrorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.learnedmatch.update.mixrows"/></span>');				
				unCheckSub(obj);
				location.href="#";
				return;
			}
	
		$("input[name=selectedIndex]:checked").each(function(){
			var checkedIndex = this.value;
			if('Y'==$("#learnedDeleteFlag_"+checkedIndex).val()) {
				lrnDel = true;}
			else if($.trim($("#wId_"+checkedIndex).val()) == '') {
				workIdExists = false;}
			else {
				wId = $("#wId_"+checkedIndex).val();
				if(isNaN(wId) || Number(wId) <=0 || !allNumeric(wId)) {
					workIdNumeric = false;
					errMsg += '<li><spring:message code="us.error.apm.archives.workid.nonnumber" arguments="${wId}"/>';
				}
			}
		});
		if(lrnDel) {
			$("#serverErrorMessages").html("");
			$("#errorMessages").html("");
			$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.learnedmatch.update.leanreddelete"/></span>');		
			unCheckSub(obj);
	 		location.href="#"; 
	 		return;
		}
		else if(!workIdExists) {		
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.archives.workid.required"/></span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
		}
		else if(!workIdNumeric) {
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed>'+errMsg+'</span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
		}
		else {
		
		var numPerf = $("input[name='selectedIndex']:checked").length;
		var updateProgressDiv = $(document.createElement('div')); 
		var txt = '<span style="font-size:1.3em;">Please Wait.. </span><br> APM is updating the selected learned matches.<br><img src="<%=request.getContextPath()%>/images/indicator.gif">';
		if(numPerf && !isNaN(numPerf)) {
    		txt = '<span style="font-size:1.3em;">Please Wait.. </span><br> APM is updating '+ numPerf +' learned matches.<br><img src="<%=request.getContextPath()%>/images/indicator.gif">';
    	}
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
		document.forms[0].operationType.value = 'UPDATE';
		document.forms[0].submit();
		}
	}
}
function resetValues() {
	$("input[name=selectedIndex]").each(function(i){
		this.value = i;
	});
}


function deleteGroups(cnt) {
	var progressDiv = $(document.createElement('div')); 
    var txt = '<span style="font-size:1.3em;">Please Wait.. </span><br> APM is deleting the '+cnt+' selected row(s) from the cross reference.<br><img src="<%=request.getContextPath()%>/images/indicator.gif">';
      
	progressDiv.html(txt);
	progressDiv.dialog({
	resizable: false,
	height:'auto',
 	minHeight: 'auto',
	modal: true,
	show: "slide",
	hide: "slide"
	});
	$(".ui-dialog-titlebar").hide();
	document.forms[0].operationType.value = 'DELETE';
	document.forms[0].submit();
}
function showProgress() {
	var updateProgressDiv = $(document.createElement('div')); 
	var txt = '<span style="font-size:1.3em;">Please Wait.. </span><br> APM is gathering the data you requested.<br><br><img src="<%=request.getContextPath()%>/images/indicator.gif">';	      
	updateProgressDiv.html(txt);
	updateProgressDiv.dialog({
	resizable: false,
	height:'auto',
		minHeight: 'auto',
	modal: false
	});
	$(".ui-dialog-titlebar").hide();
}

function retrieveLearnedMatch() {resetValues();}

</script>
</head>
<body> 

<div class="menubg"  style="width: 100%; border: 0px; height:90px;"> 
<jsp:include page="/views/menu.jsp"/>

<%--
<iframe  style="width: 100%; border: 0px; height:90px;"   
            id="topIFrame" name="topIFrame" scrolling="no"
            src="<%=request.getContextPath()%>/menu.jsp">   
</iframe>
 --%>
</div>
<div></div>
 
<table class="titletable">
<tr>
	<td>Learned Match List [LM-100]</td>
</tr>
 </table>
 
 
 <table class="errortable" style="width:1000px;">
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
 
 
 
<form:form action="apmLearnedMatch" modelAttribute="apmLearnedMatchList">
 
<form:hidden name="apmLearnedMatchList" path="navigationType" />
<form:hidden name="apmLearnedMatchList" path="operationType" />
<form:hidden name="apmLearnedMatchList" path="selectdRowId" />

<div>
<div style= "">
   
<table class="detailstable1000" style="width:1000px; font-size: 11px; font-weight: bold; color: #000000; line-height: 11px; margin:0 auto;"> 
	<tr>	
		<td>Supplier<br><form:select path="filterSupplierCode" Id="filterSupplier">
			<form:option value="" />
			<form:option value="ALL_SUPPLIERS">All Suppliers</form:option>
			<form:options items="${UsageSuppliersList}" itemLabel="displayName" itemValue="id"/>
		</form:select></td>
		
		<td>Work Title<br><form:input path="filterWorkTitle" Class="uppercase" /></td>
		<td>Title Search Type<br>
		    <form:select path="workTitleSearchType">
				<form:option value="BGNS">Begins</form:option>
				<form:option value="CNTS">Contains-Begins</form:option>
			</form:select></td>
			
		<td>Performer<br><form:input path="filterPerformerName" Class="uppercase" /></td>
		<td>Performer Search Type<br>
		    <form:select path="performerSearchType">
				<form:option value="BGNS">Begins</form:option>
				<form:option value="CNTS">Contains-Begins</form:option>
			</form:select></td>
			
		<td>Work ID<br><form:input path="filterWorkId" size="11" maxlength="11"/></td>	
		<td>Learn Type<br>
		    <form:select path="filterMatchTypeCode">
				<form:option value=""></form:option>
				<form:option value="MATCH">Match</form:option>
				<form:option value="DELETE">Delete</form:option>
			</form:select></td>		
				
	</tr>
</table> 
</div>
</div> 

<div>  
<div style="width:1000px;margin:0 auto;">    
  
<%@ include file = "/views/common/coPageFilters.jsp"%> 



<table class="buttonstable" style="width:1000px;">
 <tr>
 <td align="left" width="20%">
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_CREATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_ADD %>" id="<%=UIWidgetConstants.ID_ADD%>" title="Update">onclick="javascript:add(this);"</prep:uiWidget> 
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_UPDATE %>" id="<%=UIWidgetConstants.ID_UPDATE%>" title="Update">onclick="javascript:update(this);"</prep:uiWidget>
 </td>	
<td align="left" valign="bottom" width="15%" style="padding-left:50px;">	
<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_SEARCH %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_APPLY_FILTER %>" id="<%=UIWidgetConstants.ID_APPLY_FILTER%>" title="Filter">onclick="javascript:filter();"</prep:uiWidget>
</td>
 <td width="45%"><%@ include file="/views/common/navigationIncludeWithNoBSC.jsp"%></td>
 <td width="20%" valign="bottom" align="right">
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_MATCH_MULTIPLE_WORKS %>" id="<%=UIWidgetConstants.ID_MATCH_MULTIPLE_WORKS%>" title="Match to Multiple Works">onclick="javascript:void(0);"</prep:uiWidget> 
 </td>
</tr>
 </table>



<table class="contenttable alternatecolors" id="mainData" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >     
<thead> 
<tr class="tablerowheader" > 
	<th width="3%">Select</th> 
	<th width="7%">Work ID</th>   
	<th width="7%">Supplier</th>
	<th width="25%">Work Title</th>
	<th width="25%">Performer</th> 
	<th width="25%">Writer</th> 
	<th width="7%">Multiple Work ID</th> 
	<th width="5%">Learn Type</th> 
</tr>
</thead>


<tbody>
  	
  	<c:if test="${not empty apmLearnedMatchList.searchResults}" >  
 <c:forEach var="apmPerformanceBulkRequest" items= "${apmLearnedMatchList.searchResults}" varStatus="currentIndexId"  >
 <c:choose>
<c:when test="${currentIndexId.index % 2 == 0}">
<tr class="o20">
</c:when>
<c:otherwise>
<tr>
</c:otherwise>
</c:choose>
<td align="center">
<form:checkbox path="selectedIndex" style="padding-top:0px;" value="${currentIndexId.index}"></form:checkbox>
</td>
<td align="left">

<c:if test="${apmPerformanceBulkRequest.invalidWorkId eq 'Y'}">
<input type="text" name="workId" value="${apmPerformanceBulkRequest.workId}" id="wId_${currentIndexId.index}" maxlength="11" size="11" style="redError">
<%-- <form:input path="searchResults[${currentIndexId.index}].workId" maxlength="11" size="11" cssStyle="redError" id="wId_${currentIndexId.index}" value="${apmPerformanceBulkRequest.workId}"/> --%>	
</c:if>
<c:if test="${apmPerformanceBulkRequest.invalidWorkId ne 'Y'}">
<input type="text" name="workId" value="${apmPerformanceBulkRequest.workId}" id="wId_${currentIndexId.index}">
<%-- <form:input path="searchResults[${currentIndexId.index}].workId" maxlength="11" size="11" id="wId_${currentIndexId.index}"/> --%>
</c:if>

</td>

<td align="left"><c:out value="${apmPerformanceBulkRequest.supplierCode}"/></td>
<td align="left"><c:out value="${apmPerformanceBulkRequest.workTitle}"/></td> 
<td align="left"><c:out value="${apmPerformanceBulkRequest.performerName}"/></td>
<td align="left"><c:out value="${apmPerformanceBulkRequest.writerName}"/></td>
<td align="center"><c:out value="${apmPerformanceBulkRequest.multWorkId}"/></td>
<td align="left"><c:out value="${apmPerformanceBulkRequest.learnType}"/></td>	

<input type="hidden" name="lmType" id="lmType_${currentIndexId.index}" value="${apmPerformanceBulkRequest.lmType}"/>
<input type="hidden" name="supplierCode" id="supplierCode_${currentIndexId.index}" value="${apmPerformanceBulkRequest.supplierCode}"/> 
<input type="hidden" name="performerName" id="performerName_${currentIndexId.index}" value="${apmPerformanceBulkRequest.performerName}"/>
<input type="hidden" name="writerName" id="writerName_${currentIndexId.index}" value="${apmPerformanceBulkRequest.writerName}"/>
<input type="hidden" name="workTitle" id="workTitle_${currentIndexId.index}" value="${apmPerformanceBulkRequest.workTitle}"/>
<input type="hidden" name="originalWorkId" id="originalWorkId_${currentIndexId.index}" value="${apmPerformanceBulkRequest.originalWorkId}"/>
<input type="hidden" name="learnedDeleteFlag" id="learnedDeleteFlag_${currentIndexId.index}" value="${apmPerformanceBulkRequest.learnedDeleteFlag}"/>
<input type="hidden" name="learnType" id="learnType_${currentIndexId.index}" value="${apmPerformanceBulkRequest.learnType}"/>
<input type="hidden" name="multWorkId" id="multWorkId_${currentIndexId.index}" value="${apmPerformanceBulkRequest.multWorkId}"/>
<input type="hidden" name="cloneCount" id="cloneCount_${currentIndexId.index}" value="${apmPerformanceBulkRequest.cloneCount}"/>

</tr> 

</c:forEach>
</c:if>
</tbody>
</table>
 
 <table class="buttonstable">
	<tr class="searchpagestatus">
		<td align="right">Results found so far ${apmLearnedMatchList.numberOfRecordsFound}</td>
	</tr>
</table>
 <input type="hidden" name="tempNoOfResults" value="${apmLearnedMatchList.numberOfRecordsFound}"/>
 <input type="hidden" name="tempCurPageNr" value="${apmLearnedMatchList.currentPageNumber}"/>
<table class="buttonstable" style="width:1000px;">
 <tr>
 <td align="left" width="20%">
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_CREATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_ADD %>" id="<%=UIWidgetConstants.ID_ADD%>" title="Update">onclick="javascript:add(this);"</prep:uiWidget> 
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_UPDATE %>" id="<%=UIWidgetConstants.ID_UPDATE%>" title="Update">onclick="javascript:update(this);"</prep:uiWidget> 
 </td>	
<td align="left" valign="bottom" width="15%" style="padding-left:50px;">	
<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_SEARCH %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_APPLY_FILTER %>" id="<%=UIWidgetConstants.ID_APPLY_FILTER%>" title="Filter">onclick="javascript:filter();"</prep:uiWidget>
</td>
 <td width="50%"><%@ include file="/views/common/navigationIncludeWithNoBSC.jsp"%></td>
 <td width="15%" valign="bottom" align="right"> 
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_DELETE %>" id="<%=UIWidgetConstants.ID_DELETE%>" title="Delete">onclick="javascript:void(0);"</prep:uiWidget>
 </td>
</tr>
 </table>

 </form:form>
 
 </div>
	<div style="float:left; width:20%;"></div>
</div>
 <div id="dialog_Delete" title="Delete?" style="display:none">Are you sure you want to delete the selected performances?</div>
 
</body>
</html>