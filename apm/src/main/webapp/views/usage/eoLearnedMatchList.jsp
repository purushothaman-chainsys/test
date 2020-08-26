<!DOCTYPE HTML>

<%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

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

<%@ include file = "/views/common/uiWorkMatchWidges.jsp"%>
<link href="${pageContext.request.contextPath}/themes/stylesheet.css" rel="stylesheet" type="text/css">
<style>
.redError {color:#FF0000; }
.uppercase {text-transform:uppercase}
.errortable li {
	text-align:left;
}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">

var manageMultWorkId = function () {
	
	    if ($("#_filterMatchTypeCode").val() == 'SUPPLIERID') {
	        $("#_filterMultWorkId").removeAttr("disabled");
	    }
	    else {
	    	$("#_filterMultWorkId").val('');
	        $("#_filterMultWorkId").attr('disabled', 'disabled');
	    }
	    
	};

var manageSupplier = function () {

    if ($("#_filterMatchTypeCode").val() === 'ISWC' || $("#_filterMatchTypeCode").val() === 'ISRC'  || $("#_filterMatchTypeCode").val() === 'URL') {
    	$("#filterSupplier").val('');
        $("#filterSupplier").attr('disabled', 'disabled');
    }
    else {
        $("#filterSupplier").removeAttr("disabled");
    }
};

$(function() {

	$('#_filterMatchTypeCode').change(manageSupplier).change().change(manageMultWorkId).change();

	//$('#_filterMatchTypeCode').change(manageMultWorkId).change();
	
	$('#mainData').css('line-height','1.7em');
	$('input[name=eoLearnedMatchType]').val($('select[name=filterMatchTypeCode]').val());
		
	sortTable('LM-200','mainData', [0,1]);
	
	$('a[id=_Match_to_Multiple_Works]').click(function() {
		
		
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
			
			$("input[name=selectedIndex]:checked").each(function(){
			
				if($(this).closest('tr').find('input[name=multWorkId]').val() == '') {
					pleaseWait();
					document.forms[0].operationType.value = 'MEDLEY_NEW';
					
					$('input[name=multWorkUrl]').val($(this).closest('tr').find('input[name=matchType]').val());
					$('input[name=multWorkMultWorkId]').val($(this).closest('tr').find('input[name=multWorkId]').val());
					$('input[name=multWorkWorkId]').val($(this).closest('tr').find('input[name=workId]').val());
					$('input[name=multWorkSupplierCode]').val($(this).closest('tr').find('input[name=supplierCode]').val());
					
					
					document.forms[0].submit();
					//alert(2222);
				}
				else {
					pleaseWait();
					document.forms[0].operationType.value = 'MEDLEY_RETRIEVE';
					$('input[name=multWorkUrl]').val($(this).closest('tr').find('input[name=matchType]').val());
					$('input[name=multWorkMultWorkId]').val($(this).closest('tr').find('input[name=multWorkId]').val());
					$('input[name=multWorkWorkId]').val($(this).closest('tr').find('input[name=workId]').val());
					$('input[name=multWorkSupplierCode]').val($(this).closest('tr').find('input[name=supplierCode]').val());
					document.forms[0].submit();
					//alert(111);
				}
			
			});
		}
	});
	
	$('a[id^=_Delete]').click(function() {
			//resetValues();
			
			var errorsFound = false;
			var nonMultRow = false; 
			var multRow = false;
			
			$("table#mainData tr input[name='selectedIndex']:checked").each(function(){
				//var checkedIndex = this.value;
				if($.trim($(this).closest('tr').find('input[name=multWorkId]').val()) == '') {
					nonMultRow = true;
				}
				if($.trim($(this).closest('tr').find('input[name=multWorkId]').val()) != '') {
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
			
			if($("table#mainData tr input[name='selectedIndex']:checked").length > 0) {	
				$("#serverErrorMessages").html("");
				$("#uierror").html("");				
		       	$( "#dialog:ui-dialog" ).dialog( "destroy" );
		       	
		       	var newDiv = $(document.createElement('div')); 
		       	var cnt = $("table#mainData tr input[name='selectedIndex']:checked").length;
		       	var txt = '';
		       	
		    	if(nonMultRow) {txt = 'WARNING: The '+cnt+' selected row(s) will be DELETED from the cross reference.  Do you want to continue?';}       	
		       	else if(multRow) {txt = 'The entire multiple works group will be deleted.  Do you want to proceed?  If you only want to delete one work in the group, please use the Update function to remove the work.';}
		       	
		       	
	    		$("table#mainData tr input[name='selectedIndex']:not(:checked)").each(function(){
	    			$(this).closest('tr').find('input[name=multWorkId]').attr("disabled", "true");
	    		});
		    	
		       	//txt = 'WARNING: The '+cnt+' selected row(s) will be DELETED from the cross reference.  Do you want to continue?';       	
		       	
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
});


function deleteGroups(cnt) {
	var progressDiv = $(document.createElement('div')); 
    var txt = '<span style="font-size:1.3em;">Please Wait.. </span><br> APM is deleting the '+cnt+' selected row(s) from the cross reference.<br><img src="${pageContext.request.contextPath}/images/indicator.gif">';
      
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

function update(obj) {
	
	if($("input[name=selectedIndex]:checked").length === 0) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.archives.update.selection"/></span>');
		unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	}
	
	var errMsg = '';
	var workIdExists = true;		
	var workIdNumeric = true;
	$("input[name=lmIdWorkIdStr]").each(function(){
		$(this).val('');
	});
	
	var nonMultRow = false; var multRow = false;
	$("input[name=selectedIndex]:checked").each(function(){
		var checkedIndex = this.value;
		if($(this).closest('tr').find('input[name=multWorkId]').val() == '') {
		//if($.trim($("input[name=multWorkId]:eq("+checkedIndex+")").val()) == '') {
			nonMultRow = true;
		}
		
		if(typeof ($(this).closest('tr').find('input[name=multWorkId]').val()) != 'undefined' ) {
			if($(this).closest('tr').find('input[name=multWorkId]').val() != '') {
				multRow = true;
			}
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
			
			if($(this).closest('tr').find('input[name=workId]').val() == '') {
				workIdExists = false;
			}
			else {
				
				$(this).closest('tr').find('input[name=workId]').val($.trim( $(this).closest('tr').find('input[name=workId]').val()));
				wId = $(this).closest('tr').find('input[name=workId]').val();
				if(isNaN(wId) || Number(wId) <=0 || !allNumeric(wId)) {
					workIdNumeric = false;
					errMsg += '<li><fmt:message key="us.error.apm.archives.workid.nonnumber"><fmt:param value="${wId}"/></fmt:message></li>';
				}
			}
		
		$(this).closest('tr').find('input[name=lmIdWorkIdStr]').val($(this).val() + '##' + $(this).closest('tr').find('input[name=workId]').val());
		
	});
	
	
	if(!workIdExists) {		
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.archives.workid.required"/></li></span>');
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
	
	
	
		var updateProgressDiv = $(document.createElement('div')); 
		var txt = '<span style="font-size:1.3em;">Please Wait.. </span><br>APM is updating the selected Leanred Matches.<br><br><img src="${pageContext.request.contextPath}/images/indicator.gif">';
			       	
      
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

function showProgress() {
	var updateProgressDiv = $(document.createElement('div')); 
	var txt = '<span style="font-size:1.3em;">Please Wait.. </span><br> APM is gathering the data you requested.<br><br><img src="${pageContext.request.contextPath}/images/indicator.gif">';	      
	updateProgressDiv.html(txt);
	updateProgressDiv.dialog({
	resizable: false,
	height:'auto',
		minHeight: 'auto',
	modal: false
	});
	$(".ui-dialog-titlebar").hide();
}
function setNavigationType(navigation) {
		showProgress();
		document.forms[0].navigationType.value = navigation;
		document.forms[0].submit();
}

function add(obj) {
	document.forms[0].operationType.value = 'ADD';
	document.forms[0].submit();
}


function filter() {

	if($('#_filterMatchTypeCode').val()=='') {
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html("");
			$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.eo.learnedmatch.filter.required"/></li></span>');
			//unCheckSub(obj);
		 	location.href="#"; 
		 	return;
		}

			fworkIdNumeric = true;
			fmultWorkIdNumeric = true;
			ferrMsg = '';
			if($.trim($("input[name=filterWorkId]").val()) == '') {				
			}
			else {
				
				$("input[name=filterWorkId]").val($.trim($("input[name=filterWorkId]").val()));
				fwId = $("input[name=filterWorkId]").val();
				if(isNaN(fwId) || Number(fwId) <=0 || !allNumeric(fwId)) {
					fworkIdNumeric = false;
					ferrMsg += '<li><fmt:message key="us.error.apm.archives.workid.nonnumber"><fmt:param value="${fwId}"/></fmt:message>';
				}
			}
			

			if($.trim($("input[name=filterMultWorkId]").val()) == '') {				
			}
			else {
				
				$("input[name=filterMultWorkId]").val($.trim($("input[name=filterMultWorkId]").val()));
				fmwId = $("input[name=filterMultWorkId]").val();
				if(isNaN(fmwId) || Number(fmwId) <=0 || !allNumeric(fmwId)) {
					fmultWorkIdNumeric = false;
					ferrMsg += '<li><fmt:message key="us.error.apm.archives.multworkid.nonnumber"><fmt:param value="${fmwId}"/></fmt:message>';
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
 		else if(!fmultWorkIdNumeric) {
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
</script>
</head>
<body>

<div class="menubg"  style="width: 100%; border: 0px; height:90px;"> 
<jsp:include page="/views/menu.jsp"/>
</div>
<div></div>
<table class="titletable">
<tr>
	<td>Learned Match List [LM-200]</td>
</tr>
 </table>

 
 <table class="errortable" style="width:1000px;">
		<tr>
			<td>
				<div id="serverErrorMessages">
				<span class="txtGreen"><c:if test="${not empty systemmessage }"><li> <c:out value="${systemmessage}"/></li></c:if></span>
				<span class="txtRed"><c:if test="${not empty systemerror }"><li><c:out value="${systemerror}" escapeXml="false"/></li></c:if>
				<c:forEach var="systemerror" items="${systemerrorlist}">
				<li><c:out value="${systemerror}"  escapeXml="false"/></li>
				</c:forEach>
				
				</span>
				</div>
				<div id="uierror"></div>
				<div id="errorMessages"></div>
			</td>
		</tr>
</table>


<form:form action="apmLearnedMatchEO" modelAttribute="eoLearnedMatchList"> 
 
<form:hidden  path="navigationType" />
<form:hidden  path="operationType" />
<form:hidden  path="eoLearnedMatchType" />
<form:hidden  path="multWorkUrl" />
<form:hidden  path="multWorkWorkId" />
<form:hidden  path="multWorkMultWorkId" />
<form:hidden  path="multWorkSupplierCode" />

<div>
<div style= "">
   
<table class="detailstable1000" style="width:1000px; font-size: 11px; font-weight: bold; color: #000000; line-height: 11px; margin:0 auto;"> 
	<tr>	
		
		<td width="15%">Match Type<br>
		    <form:select path="filterMatchTypeCode" id="_filterMatchTypeCode">
				<form:option value=""></form:option>
				<form:option value="ISRC">ISRC</form:option>
				<form:option value="ISWC">ISWC</form:option>
				<form:option value="SUPPLIERID">Supplier Song Id</form:option>
				<form:option value="URL">URL</form:option>
			</form:select></td>
		<td width="10%">Match Value<br><form:input path="filterMatchIdValue" size="11" maxlength="25"/></td>		
			
		<td width="20%">Supplier<br><form:select path="filterSupplierCode" id="filterSupplier">
			<form:option value="" />
			<%-- html:option value="ALL_SUPPLIERS">All Suppliers</html:option>--%>
			<form:options items="${UsageSuppliersList }" itemLabel="displayName" itemValue="id" />
		</form:select></td>
		<td width="20%" align="left">Work ID<br><form:input path="filterWorkId" size="11" maxlength="11" /></td>
		<td width="20%" align="left">Mult Work ID<br><form:input path="filterMultWorkId" size="11" maxlength="11" id="_filterMultWorkId"/></td>
		<td width="10%"></td>
	</tr>
</table> 
</div>
</div> 

<table class="buttonstable" style="width:1000px;">
 <tr>
 <td align="left" width="20%">
 <prep:uiWidget name="${ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_CREATE}" type="${SecurityConstants.ANCHOR_TYPE}" label="${UIWidgetConstants.LABEL_ADD}" id="${UIWidgetConstants.ID_ADD}" title="Update">onclick="javascript:add(this);"</prep:uiWidget> 
 <prep:uiWidget name="${ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_UPDATE}" type="${SecurityConstants.ANCHOR_TYPE}" label="${UIWidgetConstants.LABEL_UPDATE}" id="${UIWidgetConstants.ID_UPDATE}" title="Update">onclick="javascript:update(this);"</prep:uiWidget>
 </td>	
<td align="left" valign="bottom" width="10%" style="padding-left:50px;">	
<prep:uiWidget name="${ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_SEARCH}" type="${SecurityConstants.ANCHOR_TYPE}" label="${UIWidgetConstants.LABEL_APPLY_FILTER}" id="${UIWidgetConstants.ID_APPLY_FILTER}" title="Filter">onclick="javascript:filter();"</prep:uiWidget>
</td>
 <td width="50%"><%@ include file = "/views/common/navigationIncludeWithNoBSC.jsp"%></td>
 <td width="25%" align="right">
 
<c:if test="${eoLearnedMatchList.filterMatchTypeCode eq 'URL'}">
 <prep:uiWidget name="${ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_UPDATE}" type="${SecurityConstants.ANCHOR_TYPE}" label="${UIWidgetConstants.LABEL_MATCH_MULTIPLE_WORKS}" id="${UIWidgetConstants.ID_MATCH_MULTIPLE_WORKS}" title="Match to Multiple Works">onclick="javascript:void(0);"</prep:uiWidget>
 </c:if>
<c:if test="${eoLearnedMatchList.filterMatchTypeCode eq 'SUPPLIERID'}">
<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_MATCH_MULTIPLE_WORKS %>" id="<%=UIWidgetConstants.ID_MATCH_MULTIPLE_WORKS%>" title="Match to Multiple Works">onclick="javascript:void(0);"</prep:uiWidget>
</c:if>
 </td>
 
</tr>
 </table>


<table class="contenttable alternatecolors" id="mainData" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >     
<thead> 
<tr class="tablerowheader" > 
	<th width="5%"></th>    
	<th width="10%">Work ID</th>
	<th width="10%">Supplier</th>
<c:if test="${eoLearnedMatchList.filterMatchTypeCode eq 'ISRC'}">
	<th width="25%">ISRC</th> 
	</c:if>
<c:if test="${eoLearnedMatchList.filterMatchTypeCode eq 'ISWC'}">
	<th width="25%">ISWC</th> 
	</c:if>
<c:if test="${eoLearnedMatchList.filterMatchTypeCode eq 'URL'}">
	<th width="25%">Url</th> 
	<th width="25%">Mult Work Id</th> 
	</c:if>
<c:if test="${eoLearnedMatchList.filterMatchTypeCode eq 'SUPPLIERID'}">	
	<th width="25%">Supplier Song Id</th> 
	<th width="25%">Mult Work Id</th> 
	</c:if>
<c:if test="${empty eoLearnedMatchList.filterMatchTypeCode}">
	<th width="25%"></th> 
	</c:if>
</tr>
</thead>
<tbody>
  	
  	
  	<c:if test="${not empty eoLearnedMatchList.searchResults}">
 <c:forEach var="apmPerformanceBulkRequest" items= "${eoLearnedMatchList.searchResults}" varStatus="currentIndexId">
 <c:choose>
<c:when test="${currentIndexId.index % 2 == 0}">
<tr class="o20">
</c:when>
<c:otherwise>
<tr>
</c:otherwise>
</c:choose>
<td align="center">
<form:checkbox path="selectedIndex" style="padding-top:0px;" value="${apmPerformanceBulkRequest.learnedMatchId}"></form:checkbox>		
<form:hidden path="lmIdWorkIdStr"/>
</td>
<td align="left">

<c:if test="${apmPerformanceBulkRequest.invalidWorkId eq 'Y'}">
<input  name="workId" type="text" value="${apmPerformanceBulkRequest.workId}" maxlength="11" size="11" class="redError" id='wId_${currentIndexId.index}'/>	
</c:if>

<c:if test="${apmPerformanceBulkRequest.invalidWorkId ne 'Y'}">
<input  name="workId" type="text" value="${apmPerformanceBulkRequest.workId}" maxlength="11" size="11" id='wId_${currentIndexId.index}'/>
</c:if>
</td>

<td align="left"><c:out value="${apmPerformanceBulkRequest.supplierCode}"></c:out>
<input type="hidden" name="supplierCode" value="${apmPerformanceBulkRequest.supplierCode}" id='supplierCode_${currentIndexId.index}'/>
</td>
<td align="left">

<c:if test="${eoLearnedMatchList.filterMatchTypeCode eq 'URL'}">
<%=com.ascap.apm.common.utils.constants.UsageConstants.YOUTUBE_URL_PREFIX %><c:out value="${apmPerformanceBulkRequest.matchType}"></c:out>
</c:if>

<c:if test="${eoLearnedMatchList.filterMatchTypeCode ne 'URL'}">
<c:out value="${apmPerformanceBulkRequest.matchType}"></c:out>
</c:if>
<input type="hidden"  name="matchType" value="${apmPerformanceBulkRequest.matchType}" id='matchType_${currentIndexId.index}'/>
</td>
<c:if test="${eoLearnedMatchList.filterMatchTypeCode eq 'URL'}">
<td align="left"><c:out value="${apmPerformanceBulkRequest.multWorkId}"></c:out></td>
<input type="hidden" name="multWorkId"  value ="${apmPerformanceBulkRequest.multWorkId}" Id='"multWorkId_"+${currentIndexId.index}'/> 
</c:if>

<c:if test="${eoLearnedMatchList.filterMatchTypeCode eq 'SUPPLIERID'}">
<td align="left"><c:out value="${apmPerformanceBulkRequest.multWorkId}"></c:out></td>
<input type="hidden" name="multWorkId" value ="${apmPerformanceBulkRequest.multWorkId}" Id='"multWorkId_"+${currentIndexId.index}'/>
</c:if>
</tr> 
</c:forEach>
</c:if>
</tbody>
</table>

 <table class="buttonstable">
	<tr class="searchpagestatus">
		<td align="right">&nbsp;</td>
	</tr>
</table>


<table class="buttonstable" style="width:1000px;">
 <tr>
 <td align="left" width="20%">
 <prep:uiWidget name="${ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_CREATE}" type="${SecurityConstants.ANCHOR_TYPE}" label="${UIWidgetConstants.LABEL_ADD}" id="${UIWidgetConstants.ID_ADD}" title="Update">onclick="javascript:add(this);"</prep:uiWidget> 
 <prep:uiWidget name="${ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_UPDATE}" type="${SecurityConstants.ANCHOR_TYPE }" label="${UIWidgetConstants.LABEL_UPDATE }" id="${UIWidgetConstants.ID_UPDATE}" title="Update">onclick="javascript:update(this);"</prep:uiWidget>
 </td>	
<td align="left" valign="bottom" width="15%" style="padding-left:50px;">	
<prep:uiWidget name="${ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_SEARCH }" type="${SecurityConstants.ANCHOR_TYPE }" label="${UIWidgetConstants.LABEL_APPLY_FILTER }" id="${UIWidgetConstants.ID_APPLY_FILTER}" title="Filter">onclick="javascript:filter();"</prep:uiWidget>
</td>
 <td width="50%"><%@ include file = "/views/common/navigationIncludeWithNoBSC.jsp"%></td>
 <td width="15%" valign="bottom" align="right"> 
 <prep:uiWidget name="${ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_UPDATE}" type="${SecurityConstants.ANCHOR_TYPE }" label="${UIWidgetConstants.LABEL_DELETE }" id="${UIWidgetConstants.ID_DELETE}" title="Delete">onclick="javascript:void(0);"</prep:uiWidget>
 </td>
</tr>
 </table>
</form:form>
</body>
</html>