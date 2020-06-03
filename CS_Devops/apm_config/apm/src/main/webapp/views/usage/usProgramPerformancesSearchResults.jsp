<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>

<%@ page isELIgnored="false" %>

<%@ page import="java.util.*"%>
<%@ page import="com.ascap.apm.common.utils.cache.lookup.LookupTableCache"%>
<%@ page import="com.ascap.apm.common.utils.cache.lookup.LookupTables"%>
<%@ page import="com.ascap.apm.controller.utils.*"%>
<%@ page import="com.ascap.apm.vob.usage.*"%>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>

<% pageContext.setAttribute("MusicUserTypesList", HtmlSelectOption.getLookUpTable("MusicUserTypes"), PageContext.PAGE_SCOPE); %>
<% pageContext.setAttribute("UseTypesList", HtmlSelectOption.getLookUpTable("UseTypes"), PageContext.PAGE_SCOPE); %>
<% pageContext.setAttribute("LicenseTypesList", HtmlSelectOption.getLookUpTable("LicenseTypes"), PageContext.PAGE_SCOPE); %>
<% pageContext.setAttribute("SurveyTypesList", HtmlSelectOption.getLookUpTable("SurveyTypes"), PageContext.PAGE_SCOPE); %>
<% pageContext.setAttribute("SampleTypesList", HtmlSelectOption.getLookUpTable("SampleTypes"), PageContext.PAGE_SCOPE); %>
<% pageContext.setAttribute("ProgramOverlapCodesList", HtmlSelectOption.getLookUpTable("ProgramOverlapCodes"), PageContext.PAGE_SCOPE); %>
<% pageContext.setAttribute("PerformanceErrorWarningCodesList", HtmlSelectOption.getLookUpTable("PerformanceErrorWarningCodes"), PageContext.PAGE_SCOPE); %>
<% pageContext.setAttribute("TVPPRuleGroupMusicUserTypesList", HtmlSelectOption.getLookUpTable("TVPPRuleGroupMusicUserTypes"), PageContext.PAGE_SCOPE); %>
<% pageContext.setAttribute("SourceSystemsOnlineEditableFlagList", HtmlSelectOption.getLookUpTable("SourceSystemsOnlineEditableFlag"), PageContext.PAGE_SCOPE); %>
<% pageContext.setAttribute("ApmUsersList", HtmlSelectOption.getLookUpTable("ApmUsers"), PageContext.PAGE_SCOPE);  %>

<html>
<head>
<title>APM</title>
<style>
#star-five {
   margin: 50px 0;
   position: relative;
   display: block;
   color: red;
   width: 0px;
   height: 0px;
   border-right:  10px solid transparent;
   border-bottom: 7px  solid red;
   border-left:   10px solid transparent;
   -moz-transform:    rotate(35deg);
   -webkit-transform: rotate(35deg);
   -ms-transform:     rotate(35deg);
   -o-transform:      rotate(35deg);
}
#star-five:before {
   border-bottom: 8px solid red;
   border-left: 3px solid transparent;
   border-right: 3px solid transparent;
   position: absolute;
   height: 0;
   width: 0;
   top: -4px;
   left: -6px;
   display: block;
   content: '';
   -webkit-transform: rotate(-35deg);
   -moz-transform:    rotate(-35deg);
   -ms-transform:     rotate(-35deg);
   -o-transform:      rotate(-35deg);
   
}
#star-five:after {
   position: absolute;
   display: block;
   color: red;
   top: 3px;
   left: -10px;
   width: 0px;
   height: 0px;
   border-right: 10px solid transparent;
   border-bottom: 7px solid red;
   border-left: 10px solid transparent;
   -webkit-transform: rotate(-70deg);
   -moz-transform:    rotate(-70deg);
   -ms-transform:     rotate(-70deg);
   -o-transform:      rotate(-70deg);
   content: '';
}
</style>

<%@ include file = "/views/common/uiWidgets.jsp"%>

<script language="JavaScript">
$(function() {
		sortTable('US-220','mainData', [0,1]);
		
		$('#_Delete').click(function() {
			if($("table#mainData tr input[name='selectedIds']:checked").length > 0) {	
				$("#serverErrorMessages").html("");
				$("#uierror").html("");
		    	   	$( "#dialog:ui-dialog" ).dialog( "destroy" );
		 			$( "#dialog_Delete" ).dialog({
		 				resizable: false,
		 				height:160,
		 				modal: true,
 						show: "slide",
		 				buttons: {
		 					Cancel: function() {
		 						$( this ).dialog( "close" );
		 					},		 					
		 					"OK": function() {
		 						$( this ).dialog( "close" );
		 						changeAction('DELETE_PROGRAM_PERFORMANCE');
		 					}
		 				}
					});			
		    	}
	    	else {
	    		$("#serverErrorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.perfheader.delete.selection"/></span>'); 
				location.href="#";
				return;
	    	}
    	});
    	
    	$('#_Assign').click(function() {
    		assignUsers('ASSIGN');
    	});
    	$('#_Unassign').click(function() {
    		assignUsers('UNASSIGN');
    	});
		
}); 

function assignUsers(assignType) {
			$("#serverErrorMessages").html("");
			$("#uierror").html("");
			
			if(assignType === 'ASSIGN') {
	    		var val = $('select[name=assignedToUserAll]').val();
		
				if($.trim(val) === '') {
					$("#serverErrorMessages").html("");
					$("#errorMessages").html("");
					$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.perfheader.update.asgusrid.value"/></span>');
			 		location.href="#"; 
			 		return;
				}
	    		$('input[name=assignType]').val('ASSIGN');
	    	}
	    	else {	
	    		$('input[name=assignType]').val('UNASSIGN');
	    	}
			if($("table#mainData tr input[name='selectedIds']:checked").length > 0) {	
				$("#serverErrorMessages").html("");
				$("#uierror").html("");
		    	changeAction('UPDATE_ASSIGNED_USERS');
		    }
	    	else {
	    		$("#serverErrorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.perfheader.update.asgusrid.selection"/></span>'); 
				location.href="#";
				return;
	    	}
}

</script>
<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css">
<script language="javascript">	
var TVPPRuleGroupMusicUserTypesArray = new Array();
<c:if test="${not empty TVPPRuleGroupMusicUserTypesList}" >
	<c:forEach var="xTVPPRuleGroupMusicUserTypeItem" items="${TVPPRuleGroupMusicUserTypesList}">
	TVPPRuleGroupMusicUserTypesArray['<c:out value="${xTVPPRuleGroupMusicUserTypeItem.id}"/>']="<c:out value="${xTVPPRuleGroupMusicUserTypeItem.displayName}"/>";
	</c:forEach>
</c:if>

var sourceSystemsOnlineEditableFlagArray = new Array();
<c:if test="${not empty SourceSystemsOnlineEditableFlagList}" >
	<c:forEach var="xSourceSystemsOnlineEditableFlagItem" items="${SourceSystemsOnlineEditableFlagList}">
	sourceSystemsOnlineEditableFlagArray['<c:out value="${xSourceSystemsOnlineEditableFlagItem.id}"/>']="<c:out value="${xSourceSystemsOnlineEditableFlagItem.displayName}"/>";
	</c:forEach>
</c:if>
</script>	

<script language="javascript">
		
function changeAction( operation) {
    var formObject = document.performanceSearchForm;
	formObject.actionSelected.value = operation;
	var stopSubmit=false;
	var dynaErrorMessage='';
	
	if(operation == "SEARCH_PROGRAM_PERFORMANCES"){
		formObject.performanceSearchType.value = "PROGRAM_PERFORMANCE";
	}else if(operation == "SEARCH_WORK_PERFORMANCES"){
		formObject.performanceSearchType.value = "WORK_PERFORMANCE";
	}else if(operation == "REMATCH_PROGRAM_PERFORMANCE") {
		if(confirm('Are you sure you want to Request Rematch of the selected Performances')) {	
			stopSubmit = false;
			document.forms[0].navigationType.value = "CURRENT";
		}else{
			stopSubmit = true;
		}
	}else if(operation == "REFRESH_PROGRAM_PERFORMANCE") {
		if(confirm('Are you sure you want to Request Refresh of the selected Performances')) {	
			stopSubmit = false;
			document.forms[0].navigationType.value = "CURRENT";
		}else{
			stopSubmit = true;
		}
	}else if(operation == "UNPOST_PROGRAM_PERFORMANCE") {
		if(confirm('Are you sure you want to Request Unpost of the selected Performances')) {	
			stopSubmit = false;
			document.forms[0].navigationType.value = "CURRENT";
		}else{
			stopSubmit = true;
		}
	}else if(operation == 'DELETE_PROGRAM_PERFORMANCE'){
			stopSubmit = false;
			document.forms[0].navigationType.value = "CURRENT";
			for (i=0, n = formObject.selectedIds.length; i < n; i++) {
					if (formObject.selectedIds[i].checked == true) {
						if(formObject.allSourceSystems[i].value != undefined 
						&& sourceSystemsOnlineEditableFlagArray[formObject.allSourceSystems[i].value] != undefined 
						&& sourceSystemsOnlineEditableFlagArray[formObject.allSourceSystems[i].value] == 'N'){
							dynaErrorMessage = dynaErrorMessage +  '<li>Performance Header ID = ' + formObject.selectedIds[i].value + ', Source System = ' + formObject.allSourceSystems[i].value + '</li>';
							stopSubmit=true;
						}
					}
			}

	}
	else if(operation == 'BULK_UPDATE_PROGRAM_PERFORMANCE'){
		count = 0;
		for (i=0, n = formObject.elements.length; i < n; i++) {
			if ('selectedIds'.indexOf(formObject.elements[i].name) != -1) {
				if (formObject.elements[i].checked == true) {
					count++;
				}
			}
		}

		if (count == 0) {
			alert("Please select at least one record in order to perform the Mass Update function.");
			return;
		}
		
	}	


	document.forms[0].navigationType.value = "";

	if(stopSubmit == false){
		formObject.submit();
	}
}

function setNavigationType(navigation) {
		document.forms[0].performanceSearchType.value = "PROGRAM_PERFORMANCE";
		document.forms[0].navigationType.value = navigation;
		document.forms[0].actionSelected.value = "SEARCH_PROGRAM_PERFORMANCES";
		document.forms[0].submit();
}
function retrieveProgramPerformance(formObject, operation, id){ 
	document.forms[0].actionSelected.value = "RETRIEVE_PROGRAM_PERFORMANCE";
	document.forms[0].selectedProgramPerformanceId.value = id;
	document.forms[0].submit();
}


function retrieveWorkPerfList(formObject, operation, id, rNum){ 
	formObject.actionSelected.value = "RETRIEVE_WORK_PERFORMANCES_LIST";
	formObject.selectedProgramPerformanceId.value = id;
	formObject.selectedRownNum.value = rNum;
	
	formObject.submit();
}

function applyToSelected(obj) {
	
	var val = $('select[name=assignedToUserAll]').val();
	
	if($.trim(val) === '') {
		
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.filedash.perfQuartersAll.blank"/></span>');
 		location.href="#"; 
 		return;
	}
	
	if($("table#mainData tr input[name='selectedIds']:checked").length > 0) {	
		
		$("input[name=selectedIds]:checked").each(function(){
			var checkedIndex = $(this).closest("tr").index();
			$("select[name=assignedToUser]:eq("+checkedIndex+")").val(val);
		});
	}
	
	else {
	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.filedash.perfQuartersAll.selection.invalid"/></span>');
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
<div></div>

<form:form action="usageHomeSearch" name="performanceSearchForm" modelAttribute="performanceSearch" >
		<form:hidden path="performanceSearchType"/>
		<form:hidden path="actionSelected"/>
		<form:hidden path="navigationType" id="navigationType"/>
		<form:hidden path="selectedProgramPerformanceId"/>
		<form:hidden path="selectedWorkPerformanceId"/>
		<form:hidden path="assignType"/>
		<form:hidden path="selectedRownNum"/>		
		
<!-- START Screen Header & Errors -->
<table class="titletable">
	<tr>
		<td>Performance Header Search Results [PH-100]</td>
	</tr>
</table>
<table class="infotable" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<%@ include file="/views/usage/usInfoBar.jsp"%>
		</td>  
	</tr>
</table>
<table class="errortable">
		<tr>
			<td>
				<div id="serverErrorMessages">
				<span class="txtGreen">${systemmessage}</span>
				<span class="txtRed">${systemerror}</span>
				</div>
				<div id="uierror"></div>
			</td>
		</tr>
</table>


<%@ include file = "/views/common/coPageFilters.jsp"%>

<div style="float:left;width:50%;">
<%@ include file = "/views/common/navigationInclude.jsp"%> 
</div>

<div style="float:right;width:50%;padding-right:1%;text-align:right;">
<nobr>
<span class="txtBlk_bold">Assigned User:</span> 
<form:select path="assignedToUserAll" style="width:120px;">
						<form:option value="" />
						<form:options items="${ApmUsersList}" itemLabel="displayName" itemValue="id" />
</form:select>
&nbsp;
<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_PH_200_UPDATE_PERF_HDR%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_ASSIGN %>" id="<%=UIWidgetConstants.ID_ASSIGN %>" title="<%=UIWidgetConstants.LABEL_ASSIGN %>"> onclick="javascript:void(0);"</prep:uiWidget>
<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_PH_200_UPDATE_PERF_HDR%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_UNASSIGN %>" id="<%=UIWidgetConstants.ID_UNASSIGN %>" title="<%=UIWidgetConstants.LABEL_UNASSIGN %>"> onclick="javascript:void(0);"</prep:uiWidget>
</nobr>
</div>

<% boolean viewPermission = true; %>
<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_PH_100_VIEW_PERF_HDR %>" value="Y">			
	<%  viewPermission = true; %>			
</prep:hasPermission>	

<table class="contenttable alternatecolors"  cellspacing="1" id="mainData">
<thead>
	<tr class="tablerowheader">
	<th width="2%"> <input name="selectAllCheckBox"   type="checkbox" onclick="javascript:toggleSelectAllEnabled(this.checked,'selectedIds')"/> </th>
	<th width="3%">View</th>
	<th width="3%">Call Letter</th>
	<th width="4%">Supplier Call Letter</th>
	<th width="5%">Music User ID</th>
	<th width="6%">Music User Type</th>
	<th width="3%" title="B - Blanket &#013;PP - Per Program">License Type</th>
	<th width="8%">Start Date &amp; Time</th>
	<th width="8%">End Date &amp; Time</th>
	<th width="3%" title="C - Census &#013;L - Library &#013;S - Sample">Survey Type</th>
	<th width="5%">Supplier</th>
	<th width="5%">Program Number</th>
	<th width="5%">Segment Number</th>
	<th width="5%">Headline Indicator</th>
	<th width="5%">Set List Type</th>
	<th width="3%">Work Perf Count</th>
	<th width="3%">Plays Count</th>
	<th width="4%">Work Perf List</th>
	<th width="7%">Assigned User</th>
</tr>
</thead>
<tbody>
<c:if test="${not empty performanceSearch}">
<c:forEach varStatus="currentIndexId" var="programPerformance" items="${performanceSearch.searchResults}" >			
<tr align="center">
	<td>
	
	
<c:if test="${programPerformance.onlineEditable eq 'Y'}">

<form:checkbox path="selectedIds" value="${programPerformance.performanceHeaderId}"></form:checkbox>
</c:if>
<c:if test="${programPerformance.onlineEditable ne 'Y'}">
		<form:checkbox path="selectedIds" value="${programPerformance.performanceHeaderId}" />
		
</c:if>		

		<form:hidden path="selectedIndexes" value='<c:out value="${currentIndexId.index}"/>'/>
		<form:hidden path="allPerformanceIds" value='<c:out value="${programPerformance.performanceHeaderId}"/>'/>
		<form:hidden path="allPerformanceVersionNumbers" value='<c:out value="${programPerformance.versionNumber}"/>'/>
		<form:hidden path="allSourceSystems" value='<c:out value="${programPerformance.sourceSystem}"/>'/>
		<form:hidden path="allLegacyPerformanceHeaderIds" value='<c:out value="${programPerformance.legacyProgramPerformanceId}"/>'/>
		<form:hidden path="allPostingStatuses" value='<c:out value="${programPerformance.postingIndicator}"/>'/>
	</td>
	<td valign="middle"  align="right">
	
		<c:if test="${programPerformance.errorFlag eq '2'}"> 
			<div class="redcircle"></div>
		</c:if>
		<c:if test="${programPerformance.errorFlag eq '1'}"> 
			<div class="yellowcircle"></div>
		</c:if>
	
		<% if(viewPermission) { %>
			<a href="javascript:retrieveProgramPerformance(document.performanceSearchForm, 'RETRIEVE_PROGRAM_PERFORMANCE', '<c:out value="${programPerformance.performanceHeaderId}"/>');" class="<%=UIWidgetConstants.STYLE_VIEW_ICON_ENABLED %>" title="View Details"></a>
		<% } else { %>
			<a class="<%=UIWidgetConstants.STYLE_VIEW_ICON_DISABLED%>"></a>
		<% } %>
	</td>
	<td align="left"><c:out value="${programPerformance.musicUserCallLetterSuffix}"/></td>
	<td align="left"><c:out value="${programPerformance.legacyMusicUserId}"/></td>
	<td align="right"><c:out value="${programPerformance.musicUserId}"/></td>
	<td align="left"><c:out value="${programPerformance.musicUserTypeDescription}"/></td>
	<td><c:out value="${programPerformance.licenseTypeCode}"/></td>
	<td><c:out value="${programPerformance.performanceStartDate}"/> <c:out value="${programPerformance.performanceStartTime}"/></td> 
	<td><c:out value="${programPerformance.performanceEndDate}"/> <c:out value="${programPerformance.performanceEndTime}"/></td>	
	<td><c:out value="${programPerformance.surveyTypeCode}"/></td>
	<td align="left"><c:out value="${programPerformance.supplierCode}"/></td>
	<td><c:out value="${programPerformance.programNumber}"/></td>
	<td><c:out value="${programPerformance.segmentNumber}"/></td>
	<td><c:out value="${programPerformance.headlineActIndicator}"/></td>
	<td><c:out value="${programPerformance.playListFlag}"/></td>
	<td><c:out value="${programPerformance.totalNumberOfWorkPerformances}"/></td>
	<td><c:out value="${programPerformance.totalNumberPlays}"/></td>
	<td align="right">
		<a href="javascript:retrieveWorkPerfList(document.performanceSearchForm, 'RETRIEVE_PROGRAM_PERFORMANCE', '<c:out value="${programPerformance.performanceHeaderId}"/>', '<c:out value="${programPerformance.headerIdCurrentRowNum}"/>' );">
		<c:out value="${programPerformance.performanceHeaderId}"/>
		</a>
		
		
	</td>
	<td align="left">
	
	 
	&nbsp;&nbsp;<c:out value="${programPerformance.assignedToUser}"/><form:hidden path="assignedToUser"/></td>
	
</tr>
</c:forEach>
</c:if>
</tbody>				                 
</table>
<table class="contenttable" cellspacing="0" cellpadding="0">
	<tr class="searchpagestatus">
		<td align="right">Results found so far <b><c:out value="${performanceSearch.progressiveResultsCount}" /></b>.</td>
	</tr>
</table>
<div style="float:left;width:50%">
<%@ include file = "/views/common/navigationInclude.jsp"%>
</div>

<table class="buttonstable" cellspacing="0" cellpadding="2">
<tr height="10"></tr>
<tr>
<td>
	<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_PH_100_DELETE_PERF_HDR %>" type="<%=SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_DELETE %>" id="<%=UIWidgetConstants.ID_DELETE%>" title="Delete selected Performances">onclick="javascript:void(0);"</prep:uiWidget>
	<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_PH_200_CREATE_PERF_HDR %>" type="<%=SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_ADD_NEW_ENTRY %>" id="<%=UIWidgetConstants.ID_ADD_NEW_ENTRY%>" title="Add New Performance">onclick="javascript:changeAction('ADD_NEW_PROGRAM_PERFORMANCE');"</prep:uiWidget>
</td> 
</tr>
</table>

</form:form>


<div id="dialog_Delete" title="Delete?" style="display:none">Are you sure you want to Delete the selected Perf Headers?</div>
</body>
</html>
