<!DOCTYPE HTML>
<%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
	
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

<% pageContext.setAttribute("LogRequestSuppliersList", HtmlSelectOption.getLookUpTable("LogRequestCallLtrs"), PageContext.PAGE_SCOPE);
   pageContext.setAttribute("LogRequestStatusList", HtmlSelectOption.getLookUpTable("LogRequestStatuses"), PageContext.PAGE_SCOPE);
   pageContext.setAttribute("LogRequestTargetSurveyYearQtrList", HtmlSelectOption.getLookUpTable("LogRequestTargetSurveyYearQtr"), PageContext.PAGE_SCOPE);
  %>


<html>
<head>
<title>APM</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 
  
<%-- <%@ include file = "/common/uiWorkMatchWidges.jsp"%> --%>

<link href="${pageContext.request.contextPath}/themes/stylesheet.css" rel="stylesheet" type="text/css">
  
  
<%@ include file = "/views/common/uiWidgets.jsp"%>
<script type="text/javascript">

$(function() {
		sortTable('US-500','mainData', [0,10]);
		filterTable('US-500','mainData' , [0,10]);

});


$(function() {
		
$('.noDblClick').dblclick(function(e){ 
    e.preventDefault();
});


	$('input[id^="usformatdatepicker"]').datepicker({
			showOn: "both",
			buttonImage: "${pageContext.request.contextPath}/js/calendar.gif",
			buttonImageOnly: true,
			changeMonth: true, 
			changeYear: true,
			dateFormat: "mm/dd/yy",
			showButtonPanel: true,
			buttonText: null,
			beforeShow: function(input, inst) {
				$('button.ui-datepicker-current').removeClass('ui-priority-secondary').addClass('ui-priority-primary');
            }
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

function update(obj) {  
    
	var dateChanged = false;
	var logReleased = false;
	var dateChangedNlogReleased = false;
		
	if($("input[name=selectedLogRequest]:checked").length === 0) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.archives.update.selection"/></span>');
		//unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	}
	 
	$("input[name=lmIdWorkIdStr]").each(function(){
		$(this).val('');
	});
	 
	$("input[name=selections]").each(function(){
		$(this).val('');
	});
	 
	
	$("input[name=selectedLogRequest]:checked").each(function(){
	    
	 	dateChanged = false;
	 	logReleased = false;
	 	dateChangedNlogReleased = false;
	
	 	var status = $.trim(    $(this).closest('tr').find('input[name=status]').val()   );
	    var statusRD = 'Released';  
		if(status == statusRD ) 	
		   logReleased = true; 
	    
		var sDate=  $.trim(    $(this).closest('tr').find('input[name=startDate]').val()      ); 
		var sDateDummy=  $.trim(    $(this).closest('tr').find('input[name=startDateDummy]').val()      ); 
		var eDate=  $.trim(    $(this).closest('tr').find('input[name=endDate]').val()      ); 
		var eDateDummy=  $.trim(    $(this).closest('tr').find('input[name=endDateDummy]').val()      ); 
	  
		if (sDate != sDateDummy  || eDate != eDateDummy)
			dateChanged = true;
		
		if (dateChanged && logReleased)
		   dateChangedNlogReleased = true;
		     
		$(this).closest('tr').find('input[name=selections]').val($(this).val() + '##' + $(this).closest('tr').find('input[name=startDate]').val()  + '##' + $(this).closest('tr').find('input[name=endDate]').val() + '##' +  $(this).closest('tr').find('input[name=notes]').val()  + '##' +  $(this).closest('tr').find('input[name=dateReceived]').val()  );
		  
	 });
	
	 
	if(dateChangedNlogReleased) {
			
			var newDiv = $(document.createElement('div')); 
	       	var txt = ' Usage has already been released under the current log dates. The new date will only apply to usage released going forward. Do you want to proceed? ';		       	
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
	 					 	//unCheckSub(obj);
			 				location.href="#";  
		 					$( this ).dialog( "close" );  
	 					},		 					
	 				"OK": function() {
	 					$( this ).dialog( "close" );  
						document.forms[0].operationType.value='UPDATE';
						document.forms[0].submit();
	 				}
	 			}
			 }); 
			 
	} else {  
			document.forms[0].operationType.value='UPDATE';  
			document.forms[0].submit();  
	}

}

function release(obj) {  

	var dateChanged = false;
	var noUsageForRelease = false;
	var usageInError = false;
	
	    	
	if($("input[name=selectedLogRequest]:checked").length === 0) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.logrequest.release.selection"/></span>');
		//unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	}
	
	
	$("input[name=selections]").each(function(){
		$(this).val('');
	});
	
	
	$("input[name=selectedLogRequest]:checked").each(function(){
	 
	    var status = $.trim(    $(this).closest('tr').find('input[name=status]').val()      );
	    
	    
		if(status ==  'New' || status == 'Released' || status == 'Processing') 	
		   noUsageForRelease = true; 
		if(status == 'Validation Error' ||  status == 'System Error') 	
		   usageInError = true;  
	  
		var sDate=  $.trim(    $(this).closest('tr').find('input[name=startDate]').val()      ); 
		var sDateDummy=  $.trim(    $(this).closest('tr').find('input[name=startDateDummy]').val()      ); 
		var eDate=  $.trim(    $(this).closest('tr').find('input[name=endDate]').val()      ); 
		var eDateDummy=  $.trim(    $(this).closest('tr').find('input[name=endDateDummy]').val()      ); 
	  
		if (sDate != sDateDummy  || eDate != eDateDummy)
			dateChanged = true;
		
		$(this).closest('tr').find('input[name=selections]').val($(this).val() + '##' + $(this).closest('tr').find('input[name=startDate]').val()  + '##' + $(this).closest('tr').find('input[name=endDate]').val() + '##' +  $(this).closest('tr').find('input[name=notes]').val()  );
		  
	 });
	 
		if(noUsageForRelease && !usageInError ) {	 
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.logrequest.release.nousage"/></span>');
		//unCheckSub(obj);
	 	location.href="#"; 
	 	return;
		}
		
		if(!noUsageForRelease && usageInError ) {	 
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.logrequest.release.haveerrors"/></span>');
		//unCheckSub(obj);
	 	location.href="#"; 
	 	return;
		}
		
		if(noUsageForRelease && usageInError ) {	 
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.logrequest.release.nousageandhaveerrors"/></span>');
		//unCheckSub(obj);
	 	location.href="#"; 
	 	return;
		}

		var newDiv = $(document.createElement('div')); 
       	var txt = ' Releasing the Log Period(s) will create programs in APM. Do you want to proceed? ';		       	
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
 					 	//unCheckSub(obj);
		 				location.href="#";  
	 					$( this ).dialog( "close" ); 
 					},		 					
 				"OK": function() {
 					$( this ).dialog( "close" );  
					document.forms[0].operationType.value='RELEASE';
					document.forms[0].submit();
 				}
 			}
		 }); 
}

function del(obj) {  

	var filterSupplierCode = $.trim($("input[name=filterSupplierCode]").val()); 
	var filterStatus = $.trim($("select[name=filterStaus]").val()); 
	
	if (filterSupplierCode != '' || filterStatus != ''){ 
	 $("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.logrequest.delete.filters"/></span>');
	 	location.href="#"; 
	 	return;
	} 
	
	
	var newDiv = $(document.createElement('div')); 
      	var txt = ' The entire Log Request will be deleted for the selected quarter.  Do you want to proceed? ';		       	
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
	 				location.href="#";  
 					$( this ).dialog( "close" ); 
					},		 					
				"OK": function() {
					$( this ).dialog( "close" );  
				document.forms[0].operationType.value='DELETE';
				document.forms[0].submit();
				}
			}
	 }); 

}

function filter() { 
            
            if(  $("input[name=filtertargetSurveyYearQuarter]").val()  == '' || 
                 $("input[name=filtertargetSurveyYearQuarter]").length === 0)  {	
				$("#serverErrorMessages").html("");
				$("#errorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.logrequest.filter.tgtsurvyearqtrblank"/></span>');
				unCheckSub(obj);
			 	location.href="#"; 
			 	return;
			}
			
			showProgress();
			document.forms[0].operationType.value = 'SEARCH';
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

function usage_OLD(obj) {   
	 
	var distQtrErr = false;
		
	if($("input[name=selectedLogRequest]:checked").length === 0) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.logrequest.gotousage.selection"/></span>');
	 	location.href="#"; 
	 	return;
	} 
	
	if($("input[name=selectedLogRequest]:checked").length > 1) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.logrequest.gotousage.multiselection"/></span>');
		//unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	}
	
	$("input[name=selections]").each(function(){
		$(this).val('');
	}); 
	
	$("input[name=selectedLogRequest]:checked").each(function(){ 
	 	$(this).closest('tr').find('input[name=selections]').val($(this).val() + '##' + $(this).closest('tr').find('input[name=callLetter]').val() + '##' + $(this).closest('tr').find('input[name=startDate]').val()  + '##' + $(this).closest('tr').find('input[name=endDate]').val() + '##' +  $(this).closest('tr').find('input[name=accountNumber]').val()  );
		   
		var targetSurveyYearQuarter = $.trim( $(this).closest('tr').find('input[name=targetSurveyYearQuarter]').val());
		var activeqtr  = $.trim( $("input[name=activeqtr]").val()); 
		if( targetSurveyYearQuarter < activeqtr  )
				distQtrErr = true;
	 });
	
	 if( distQtrErr) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.logrequest.gotousage.tgtsurvyearqtrnotactive"/></span>');
	 	location.href="#"; 
	 	return;
		}
		 
		document.forms[0].operationType.value='USAGE';
		document.forms[0].submit();
 
}

function usage(logRequestId,callLetter,startDate,endDate,accountNumber,targetSurveyYearQuarter) {   
	
	var distQtrErr = false; 
	var count = 1;  
	
	$("input[name=selections]").each(function(){
	  if (count == 1)
		$(this).val(logRequestId+'##'+callLetter+'##'+startDate+'##'+endDate+'##'+accountNumber);
		else
		$(this).val(''); 
		count++;
	}); 
	  
	var activeqtr = $.trim( $("input[name=activeqtr]").val()); 
	
	if( targetSurveyYearQuarter < activeqtr  )
				distQtrErr = true; 
	
	 if( distQtrErr) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.logrequest.gotousage.tgtsurvyearqtrnotactive"/></span>');
	 	location.href="#"; 
	 	return;
		}
		 
		document.forms[0].operationType.value='USAGE';
		document.forms[0].submit(); 
}

function setNavigationType(navigation) {
		document.forms[0].operationType.value='SEARCH';
		document.forms[0].navigation.value = navigation;
		document.forms[0].submit();
}

function setPageNumber() {
		var gotopage =  $.trim( $("input[name=pageNumber]").val());
		document.forms[0].operationType.value='SEARCH';
		document.forms[0].navigation.value = 'GOTOPAGE';
		document.forms[0].currentPg.value = gotopage ; 
		document.forms[0].submit();
}  

 
function selectRows(obj) {
	
	var ix = $(obj).closest("tr").index();
	var newVal = $(obj).val();  
	var ide = $(obj).attr("id");
	var origVal = null;
	 
	
	if (ide == 'dateReceived')
	origVal =  $('input[name=originalDateReceived]:eq('+ix+')').val();
	
	if (ide == 'notes')
	origVal =  $('input[name=originalNotes]:eq('+ix+')').val();
	
	if (ide.match("^usformatdatepicker_startDate")) 
	origVal =  $('input[id^=startDateDummy]:eq('+ix+')').val();  
	
	if (ide.match("^usformatdatepicker_endDate")) 
	origVal =  $('input[id^=endDateDummy]:eq('+ix+')').val();   
		    
	if(newVal !== origVal) {
		$('input[name=selectedLogRequest]:eq('+ix+')').attr('checked', true);
	}
	else {
		$('input[name=selectedLogRequest]:eq('+ix+')').attr('checked', false);
	}
	
} 
  
</script>
</head>
<body> 
 
<div class="menubg"  style="width: 100%; border: 0px; height:90px;"> 
<jsp:include page="/views/menu.jsp"/>
</div> 


<form:form action="logrequestSummary" modelAttribute="logRequestSummaryForm">
<table class="titletable">
<tr>
	<td>Log Request Summary [LR-100]</td>
</tr>
 </table>
 <table class="errortable" style="font-size:1.2em;width:1200px;margin:0 auto;border:0px">
		<tr>
			<td>
				<div id="serverErrorMessages">
				<span class="txtGreen"><c:out value="${systemmessage}"/></span>
				<span class="txtRed"><c:out value="${systemerror}"/></span>
				</div>
				<div id="uierror"></div>
				<div id="errorMessages"><form:hidden path="activeqtr" /> </div>
			</td>
		</tr>
</table>

 
<form:hidden  path="operationType" /> 
<form:hidden  path="navigation" /> 
<form:hidden  path="currentPg" />
<form:hidden  path="totalPg" />  
<form:hidden  path="canDeleteLogRequest" />  
              
<form:hidden  path="filterSupplierCodeOrig" />  
<form:hidden  path="filtertargetSurveyYearQuarterOrig" />  
<form:hidden  path="filterStausOrig" />  
  

<div style="width:1200px;margin:0 auto;">
<table class="detailstable1000" style="width:1200px; font-size: 11px; font-weight: bold; color: #000000; line-height: 11px; margin:0 auto;">
	 
	<tr>	
		 
		 <td align="left"  width="20%"> 
			Distribution-Qtr <br>  
			
			<form:input  path="filtertargetSurveyYearQuarter" Id="yearquarterpicker_targetYearQuarter" cssClass="small" readonly="true"/><a href="javascript:clearField('yearquarterpicker_targetYearQuarterFrom');" class="${UIWidgetConstants.STYLE_CLEAR_FIELD_ICON_ENABLED}" title="${UIWidgetConstants.TITLE_CLEAR_FIELD}"></a>

		<td > 
		<td align="left"  width="20%"> 
		 	Call-Letter<br>   
		 
		<form:input path="filterSupplierCode" cssClass="uppercase" maxlength="170" />
       	</td>
		
		<td align="left"  width="20%">  
		    Log Request Status<br> 
		    <form:select path="filterStaus" id="filterSupplier">
			<form:option value="" />
			<form:options items="${LogRequestStatusList}" itemLabel="displayName" itemValue="id" />
			</form:select>
		 </td> 
		
		<td align="left"  width="20%">   
		</td>
		<td align="left"  width="20%">   
		</td>
  
	</tr>
	
	
	
</table>  
</div>
  
<div style="width:1200px;margin:0 auto;">
  
 
<table class="detailstable1000" style="width:1200px; font-size: 11px; font-weight: bold; color: #000000; line-height: 11px; margin:0 auto; padding-top:15px; padding-bottom:15px;"> 
<tr>  </tr>
 <tr> 
	  <td align="left" width="10%">
	     <prep:uiWidget name="${ProtectedResourcesConstants.APM_LR_100_LOG_REQ_UPDATE}" type="${SecurityConstants.ANCHOR_TYPE}" label="${UIWidgetConstants.LABEL_UPDATE}" id="${UIWidgetConstants.ID_UPDATE}" title="Update">onclick="javascript:update(this);"</prep:uiWidget>
	  </td>	
	   
      <td align="left" width="10%"> 
		 <prep:uiWidget name="${ProtectedResourcesConstants.APM_LR_100_LOG_REQ_RELEASE}" type="${SecurityConstants.ANCHOR_TYPE}" label="${UIWidgetConstants.LABEL_RELEASE}" id="${UIWidgetConstants.ID_RELEASE}" title="Release">onclick="javascript:release(this);"</prep:uiWidget>
	  </td>	
	   
      <td align="left"  width="10%">	  
		<prep:uiWidget name="${ProtectedResourcesConstants.APM_LR_100_LOG_REQ_SEARCH}" type="${SecurityConstants.ANCHOR_TYPE}" label="${UIWidgetConstants.LABEL_APPLY_FILTER}" id="${UIWidgetConstants.ID_APPLY_FILTER}" title="Filter">onclick="javascript:filter();"</prep:uiWidget>
	  </td> 


	  <td align="left" width="15%" >
		Found <c:out value="${logRequestSummaryForm.searchResultRows}"></c:out> Result(s) 
	  </td>


 <td align="left" width="15%" >
<%pageContext.setAttribute("totalPg", String.valueOf(0), PageContext.PAGE_SCOPE);%>
<c:if test="${logRequestSummaryForm.totalPg eq totalPg }">
			Page <c:out value="${logRequestSummaryForm.currentPg}"></c:out> of  
			<c:out value="${logRequestSummaryForm.totalPg}"></c:out>
		 </c:if>
	  </td>
	  
	  
	 <td align="left" width="25%" >
	 <%pageContext.setAttribute("totalPg", String.valueOf(0), PageContext.PAGE_SCOPE);%>
<c:if test="${logRequestSummaryForm.totalPg ne totalPg }">
<%pageContext.setAttribute("totalPg", String.valueOf(1), PageContext.PAGE_SCOPE);%>
<c:if test="${logRequestSummaryForm.totalPg ne totalPg }">
		Page #&nbsp; 
		<input type="text" name="pageNumber" size="1" maxlength="5"  value="${logRequestSummaryForm.currentPg}" onkeyup="javascript:resetPageNumber(this.value);" />
		<prep:uiWidget type="${SecurityConstants.ICON_TYPE}" 
		title="${UIWidgetConstants.LABEL_NAV_GO}" 
		id="${UIWidgetConstants.ID_NAV_GO}">
		onclick="javascript:setPageNumber();"</prep:uiWidget>

	    <%pageContext.setAttribute("firstPg", "N", PageContext.PAGE_SCOPE);%>
       <c:if test="${logRequestSummaryForm.firstPg eq firstPg }">
		<prep:uiWidget type="${SecurityConstants.ICON_TYPE}" title="${UIWidgetConstants.LABEL_NAV_FIRST}" id="${UIWidgetConstants.ID_NAV_FIRST}">onclick="javascript:setNavigationType('FIRST');"</prep:uiWidget>
		<prep:uiWidget type="${SecurityConstants.ICON_TYPE}" title="${UIWidgetConstants.LABEL_NAV_PREV}" id="${UIWidgetConstants.ID_NAV_PREV}">onclick="javascript:setNavigationType('PREV');"</prep:uiWidget>
		</c:if>
	
	   <%pageContext.setAttribute("lastPg", "N", PageContext.PAGE_SCOPE);%>
       <c:if test="${logRequestSummaryForm.lastPg eq lastPg }">
		<prep:uiWidget type="${SecurityConstants.ICON_TYPE}" 
		title="${UIWidgetConstants.LABEL_NAV_NEXT}" 
		id="${UIWidgetConstants.ID_NAV_NEXT}">
		onclick="javascript:setNavigationType('NEXT');"</prep:uiWidget>
		
		<prep:uiWidget type="${SecurityConstants.ICON_TYPE}" title="${UIWidgetConstants.LABEL_NAV_LAST}" id="${UIWidgetConstants.ID_NAV_LAST}">onclick="javascript:setNavigationType('LAST');"</prep:uiWidget>
		</c:if>		
			
	</c:if>
	</c:if>
	</td>

	  <td align="left" width="15%"> 
      </td>	 
 </tr>
</table>

 </div>


<%@ include file = "/views/common/coPageFilters.jsp"%>  
 <div style="width:1200px;margin:0 auto;">
	  
<table class="contenttable alternatecolors" id="mainData" style="width:1200px; font-size: 11px; font-weight: normal; color: #000000; line-height: 11px; margin:0 auto;">  

 
<thead>  

<tr class="tablerowheader" style="line-height: 2.5em;" >  
	<th width="1%"></th>  
	<th width="6%">Call Letter</th>
	<th width="12%">Start Date</th>
	<th width="12%">End Date</th> 
	<th width="9%">Location</th> 
	<th width="3%">State</th>  
	<th width="8%">Account Nbr</th>
	<th width="6%">Mode</th>
	<th width="8%">Format</th> 
	<th width="7%">Received Date</th> 
	<th width="10%">Comments</th> 
	<th width="10%">Note</th> 
	<th width="9%">Status</th> 
</tr>
</thead>
<tbody>
 
 <% int sampleRowNum = 0; %>  	 	
 
 
 <c:if test="${not empty logRequestSummaryForm.searchResults}"> 
 <c:forEach var="logRequestResult" items= "${logRequestSummaryForm.searchResults}" varStatus="currentIndexId">
 <c:choose>
<c:when test="${currentIndexId.index % 2 == 0}">
<tr class="o20" style="line-height:1.7em;">
</c:when>
<c:otherwise>
<tr style="line-height:1.7em;">
</c:otherwise>
</c:choose>
<td>
<form:checkbox path="selectedLogRequest" value="${logRequestResult.logRequestId}" />
<form:hidden path="lmIdWorkIdStr"/>
<form:hidden path="selections"/>
</td>

<td>   
<prep:hasPermission name="${ProtectedResourcesConstants.APM_LR_100_LOG_REQ_GOTO_USAGE}" value="Y">
<a href="javascript:void(0)" id="_GotoUsage" class="noDblClick" 
onclick="javascript:usage('<c:out value="${logRequestResult.logRequestId}"></c:out>','<c:out value="${logRequestResult.callLetter}"></c:out>','<c:out value="${logRequestResult.startDate}"></c:out>','<c:out value="${logRequestResult.endDate}"></c:out>','<c:out value="${logRequestResult.accountNumber}"></c:out>','<c:out value="${logRequestResult.targetSurveyYearQuarter}"></c:out>');"><c:out value="${logRequestResult.callLetter}"></c:out></a>
</prep:hasPermission>
 
<prep:hasPermission name="${ProtectedResourcesConstants.APM_LR_100_LOG_REQ_GOTO_USAGE}" value="N">
<a href="javascript:void(0)" id="_GotoUsage" class="noDblClick" 
onclick="javascript:usage('<c:out value="${logRequestResult.logRequestId}"></c:out>','<c:out value="${logRequestResult.callLetter}"></c:out>','<c:out value="${logRequestResult.startDate}"></c:out>','<c:out value="${logRequestResult.endDate}"></c:out>','<c:out value="${logRequestResult.accountNumber}"></c:out>','<c:out value="${logRequestResult.targetSurveyYearQuarter}"></c:out>');"><c:out value="${logRequestResult.callLetter}"></c:out></a>
</prep:hasPermission>

<input type="hidden" value="${logRequestResult.logRequestId}"/>
</td>
<td>  
<input name="startDate" onchange="javascript:selectRows(this)" value="${logRequestResult.startDate}" maxlength="11" size="11" Id='usformatdatepicker_startDate_${currentIndexId.index}'/>
 <a href="javascript:clearField('usformatdatepicker_startDate_${currentIndexId.index}');" class="${UIWidgetConstants.STYLE_CLEAR_FIELD_ICON_ENABLED}" title="${UIWidgetConstants.TITLE_CLEAR_FIELD}"></a><br>

</td>
<td> 
<input name="endDate" onchange="javascript:selectRows(this)" value="${logRequestResult.endDate}" maxlength="11" size="11" id='usformatdatepicker_endDate_${currentIndexId.index}'/>
 <a href="javascript:clearField('usformatdatepicker_endDate_${currentIndexId.index}');" class="${UIWidgetConstants.STYLE_CLEAR_FIELD_ICON_ENABLED}" title="${UIWidgetConstants.TITLE_CLEAR_FIELD}"></a><br>
 
<input type="hidden" name="endDate" value="${logRequestResult.endDate}" id='endDate_${currentIndexId.index}'/> 
<input type="hidden" name="startDateDummy" value="${logRequestResult.startDateDummy}" id="startDateDummy_${currentIndexId.index}"/> 
<input type="hidden" name="endDateDummy" value="${logRequestResult.endDateDummy}" id="endDateDummy_${currentIndexId.index}"/>
<input type="hidden" name="targetSurveyYearQuarter" value="${logRequestResult.targetSurveyYearQuarter}" id="targetSurveyYearQuarter_${currentIndexId.index}"/>

<input type="hidden"  id="_tempDateReceived" value='<c:out value="${logRequestResult.dateReceived} "></c:out>'/>
<input type="hidden" id="_tempNotes" value='<c:out value="${logRequestResult.notes} "></c:out>'/>

</td>

<td><c:out value="${logRequestResult.location} "></c:out></td>
<td><c:out value="${logRequestResult.state} "></c:out></td>
<td><c:out value="${logRequestResult.accountNumber} "></c:out></td>
<td><c:out value="${logRequestResult.logMode} "></c:out></td>
<td><c:out value="${logRequestResult.format} "></c:out></td>
<td> 
<input name="dateReceived" value="${logRequestResult.dateReceived}" maxlength="10" size="10" Id="dateReceived_${currentIndexId.index}" onchange="javascript:selectRows(this)"/>
</td>
<td> 
 <c:out value="${logRequestResult.logComments} "></c:out>
</td>
<td>
 
<input name="notes" value="${logRequestResult.notes}"  maxlength="150" size="11" id="notes_${currentIndexId.index}" onchange="javascript:selectRows(this)"/>
</td>
<td>
 <c:out value="${logRequestResult.status} "></c:out>
<input type="hidden"  name="status" value="${logRequestResult.status}" id='status_${currentIndexId.index}'/>
</td>
</tr>
</c:forEach>
</c:if>

</tbody>
</table>

<table class="detailstable1000" style="width:1200px; font-size: 11px; font-weight: bold; color: #000000; line-height: 11px; margin:0 auto; padding-top:15px; padding-bottom:15px;"> 
<tr>  </tr>
 <tr> 
	  <td align="left" width="10%">
	     <prep:uiWidget name="${ProtectedResourcesConstants.APM_LR_100_LOG_REQ_UPDATE}" type="${SecurityConstants.ANCHOR_TYPE }" label="${UIWidgetConstants.LABEL_UPDATE }" id="${UIWidgetConstants.ID_UPDATE}" title="Update">onclick="javascript:update(this);"</prep:uiWidget>
	  </td>	
	   
      <td align="left" width="10%"> 
		 <prep:uiWidget name="${ProtectedResourcesConstants.APM_LR_100_LOG_REQ_RELEASE}" type="${SecurityConstants.ANCHOR_TYPE }" label="${UIWidgetConstants.LABEL_RELEASE }" id="${UIWidgetConstants.ID_RELEASE}" title="Release">onclick="javascript:release(this);"</prep:uiWidget>
	  </td>	
	   
      <td align="left"  width="10%"  ">	  
		<prep:uiWidget name="${ProtectedResourcesConstants.APM_LR_100_LOG_REQ_SEARCH }" type="${SecurityConstants.ANCHOR_TYPE }" label="${UIWidgetConstants.LABEL_APPLY_FILTER }" id="${UIWidgetConstants.ID_APPLY_FILTER}" title="Filter">onclick="javascript:filter();"</prep:uiWidget>
	  </td> 
	   
	  
	  <td align="left" width="15%" >
		Found <c:out value="${logRequestSummaryForm.searchResultRows} "></c:out> Result(s) 
		</td>
		<td align="left" width="15%" >
		<%pageContext.setAttribute("totalPg",String.valueOf(0), PageContext.PAGE_SCOPE);%>
         <c:if test="${logRequestSummaryForm.totalPg ne totalPg }">
			Page <c:out value="${logRequestSummaryForm.currentPg} "></c:out> of  
			<c:out value="${logRequestSummaryForm.totalPg} "></c:out>
		 </c:if>
		</td>
		<td align="left" width="25%" >
		<%pageContext.setAttribute("totalPg",String.valueOf(0), PageContext.PAGE_SCOPE);%>
         <c:if test="${logRequestSummaryForm.totalPg ne totalPg }">
         <%pageContext.setAttribute("totalPg",String.valueOf(1), PageContext.PAGE_SCOPE);%>
         <c:if test="${logRequestSummaryForm.totalPg ne totalPg }">
			Page #&nbsp; 
			<input type="text" name="pageNumber" size="1" maxlength="5"  value="<c:out value="${logRequestSummaryForm.currentPg} "></c:out>" onkeyup="javascript:resetPageNumber(this.value);" />
			<prep:uiWidget type="${SecurityConstants.ICON_TYPE }" 
			title="${UIWidgetConstants.LABEL_NAV_GO }" 
			id="${UIWidgetConstants.ID_NAV_GO}">
			onclick="javascript:setPageNumber();"</prep:uiWidget>
			 
			<%pageContext.setAttribute("firstPg","N", PageContext.PAGE_SCOPE);%>
         <c:if test="${logRequestSummaryForm.firstPg eq firstPg }">
			 
			<prep:uiWidget type="${SecurityConstants.ICON_TYPE }" title="${UIWidgetConstants.LABEL_NAV_FIRST }" id="${UIWidgetConstants.ID_NAV_FIRST}">onclick="javascript:setNavigationType('FIRST');"</prep:uiWidget>
			<prep:uiWidget type="${SecurityConstants.ICON_TYPE }" title="${UIWidgetConstants.LABEL_NAV_PREV }" id="${UIWidgetConstants.ID_NAV_PREV}">onclick="javascript:setNavigationType('PREV');"</prep:uiWidget>
			</c:if>
			
		<%pageContext.setAttribute("lastPg","N", PageContext.PAGE_SCOPE);%>
         <c:if test="${logRequestSummaryForm.lastPg eq lastPg }">
			<prep:uiWidget type="${SecurityConstants.ICON_TYPE}" 
			title="${UIWidgetConstants.LABEL_NAV_NEXT}" 
			id="${UIWidgetConstants.ID_NAV_NEXT}">
			onclick="javascript:setNavigationType('NEXT');"</prep:uiWidget>
			
			<prep:uiWidget type="${SecurityConstants.ICON_TYPE }" title="${UIWidgetConstants.LABEL_NAV_LAST }" id="${UIWidgetConstants.ID_NAV_LAST}">onclick="javascript:setNavigationType('LAST');"</prep:uiWidget>
			</c:if>		
				
					</c:if>		
			</c:if>		

		</td>
		
	  
	  <td align="right" width="15%"> 
		
		<%pageContext.setAttribute("canDeleteLogRequest","Y", PageContext.PAGE_SCOPE);%>
         <c:if test="${logRequestSummaryForm.canDeleteLogRequest eq canDeleteLogRequest }">
		<prep:uiWidget name="${ProtectedResourcesConstants.APM_LR_100_LOG_REQ_UPDATE}" type="${SecurityConstants.ANCHOR_TYPE}" label="${UIWidgetConstants.LABEL_DELETE }" id="${UIWidgetConstants.ID_DELETE}" title="Delete">onclick="javascript:del(this);"</prep:uiWidget>
		</c:if>
		
		
		<%pageContext.setAttribute("canDeleteLogRequest","N", PageContext.PAGE_SCOPE);%>
         <c:if test="${logRequestSummaryForm.canDeleteLogRequest eq canDeleteLogRequest }">
		<prep:uiWidget disabled = "true" name="${ProtectedResourcesConstants.APM_LR_100_LOG_REQ_UPDATE}" type="${SecurityConstants.ANCHOR_TYPE }" label="${UIWidgetConstants.LABEL_DELETE }" id="${UIWidgetConstants.ID_DELETE}" title="Delete"> onclick="javascript:del(this);"</prep:uiWidget>
		</c:if>
		
      </td>	 
 </tr>
</table>
</div>
</form:form>
</body>
</html>