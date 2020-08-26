<!DOCTYPE HTML>

<%-- 
/**
 * Description: Work Performance Search Results Screen
 * @Author Manoj_Puli
 */  
--%>
<%@ page language="java" contentType="text/html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

<head>
<title>APM</title>
<%@ include file = "/views/common/uiWidgets.jsp"%> 
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/struts/js/base/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/struts/js/base/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/struts/js	/base/jquery.dragtable.js"></script>--%>
<style>
/*
table#mainData {
  -moz-border-radius: 5px !important;
  -webkit-border-radius: 5px;
   border-radius: 5px;
  border-collapse: collapse !important;
  border: none !important;
}
table#mainData th, table td { border: none !important }
table#mainData th:first-child {
  -moz-border-radius: 5px 0 0 0 !important;
  -webkit-border-radius: 5px 0 0 0;
   border-radius: 5px 0 0 0;
}
table#mainData th:last-child {
  -moz-border-radius: 0 5px 0 0 !important;
  -webkit-border-radius: 0 5px 0 0 ;
   border-radius: 0 5px 0 0 ;
}
table#mainData tr:last-child td:first-child {
  -moz-border-radius: 0 0 0 5px !important;
  -webkit-border-radius: 0 0 0 5px;
   border-radius: 0 0 0 5px;
}
table#mainData tr:last-child td:last-child {
  -moz-border-radius: 0 0 5px 0 !important;
  -webkit-border-radius: 0 0 5px 0 ;
   border-radius: 0 0 5px 0 ;
}
table#mainData tr:hover td { background-color: #999 !important }
table#mainData td, th {
   border:solid white 10px;
}
*/
</style>

<script type="text/javaScript">
$(function() {

		sortTable('US-230','mainData', [0,1]);
		filterTable('US-230','mainData' , [0,1]);
		/*$('#mainData').dragtable({maxMovingRows:1});*/
		
		
		
		
		$('#_Update').click(function() {
		
			var useTypeChanged = false;
			var ix = -1;
			
			$("input[name=selectedIds]:checked").each(function(){
				 ix = $(this).closest("tr").index();
				 
				 var tempval = $(this).val() + '-_-' + $ ('#_useTypeCode'+ix).val()  ;
				 // alert('temp val ' + tempval);
				 $(this).val(tempval);
				 useTypeChanged = true;
				 
				 //$(this).val( $(this.val() + '-' + $ ('select[name=useTypeCode]:eq('+ix+')').val())    );
				 
				 
			});
		
		
		/* 
			var useTypeChanged = false;
			$('input[name=selectedIds]').attr('checked', false);				
			$("#serverErrorMessages").html("");
			$("#uierror").html("");
			
			$("input[name=selectedIndexes]").each(function(){
					var checkedIndex = this.value;
					
					$('input[name=selectedUseTypeCodes]:eq('+checkedIndex+')').val($('select[name=useTypeCode]:eq('+checkedIndex+')').val());
					
					if($('select[name=useTypeCode]:eq('+checkedIndex+')').val() !== $('input[name=originalUseTypeCodes]:eq('+checkedIndex+')').val()) {
						useTypeChanged = true;
					}
					else {
						$('input[name=selectedUseTypeCodes]:eq('+checkedIndex+')').attr("disabled","disabled");
						$('input[name=originalUseTypeCodes]:eq('+checkedIndex+')').attr("disabled","disabled");
						$('input[name=originalWorkPerformanceIds]:eq('+checkedIndex+')').attr("disabled","disabled");
					}
			}); */
				
			if(useTypeChanged) {			
				changeWrkPerfAction('UPDATE_WORK_PERFORMANCES_MULT');
			}
			else {
				$("#serverErrorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><spring:message code="message.apm.workperf.mult.update.nochange"/></span>'); 
				location.href="#";
				unCheckSub($('a[id^=_Update'));
				
				$("input[name=selectedIndexes]").each(function(){
					var checkedIndex = this.value;
					$('input[name=selectedUseTypeCodes]:eq('+checkedIndex+')').attr("disabled","");
					$('input[name=originalUseTypeCodes]:eq('+checkedIndex+')').attr("disabled","");
					$('input[name=originalWorkPerformanceIds]:eq('+checkedIndex+')').attr("disabled","");
					
				});
				return;
			}
    	});
		
		
		
		
		
		
		
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
 						changeWrkPerfAction('DELETE_WORK_PERFORMANCE');
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
    	
    	$('#_Move').click(function() {
    	if($("table#mainData tr input[name='selectedIds']:checked").length > 0) {    		
			$("#serverErrorMessages").html("");
			$("#uierror").html("");
       		$( "#dialog:ui-dialog" ).dialog( "destroy" );
 			$( "#dialog_Move" ).dialog({
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
 						changeWrkPerfAction('MOVE_WORK_PERFORMANCE');
 					}
 				}
			});
			}
			else {
				$("#serverErrorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.perfheader.move.selection"/></span>'); 
				location.href="#";
				return;
			}
    	});
		
		$("a[id='#top']").click(function() {
  			$("html, body").animate({ scrollTop: 0 }, 0);
  			//alert('bottom : ' + ($(window).scrollTop() + $(window).height()));
  			return false;
		});
		$("a[id='#bottom']").click(function() {
			$('html, body').animate({
        		scrollTop: $(document).height()
    		}, 0);
		});
		
		
}); 
</script>


<script type="text/javascript">
var TVPPRuleGroupMusicUserTypesArray = new Array();
<c:if test="${not empty TVPPRuleGroupMusicUserTypesList}">
	<c:forEach items="${TVPPRuleGroupMusicUserTypesList}" var="xTVPPRuleGroupMusicUserTypeItem" varStatus="currentIndexId">
		TVPPRuleGroupMusicUserTypesArray['<c:out value="${xTVPPRuleGroupMusicUserTypeItem.id}"/>']="<c:out value="${xTVPPRuleGroupMusicUserTypeItem.displayName}"/>";
	</c:forEach>
</c:if>
var sourceSystemsOnlineEditableFlagArray = new Array();
<c:if test="${not empty SourceSystemsOnlineEditableFlagList}">
	<c:forEach items="${SourceSystemsOnlineEditableFlagList}" var="xSourceSystemsOnlineEditableFlagItem" varStatus="currentIndexId">
		sourceSystemsOnlineEditableFlagArray['<c:out value="${xSourceSystemsOnlineEditableFlagItem.id}"/>']="<c:out value="${xSourceSystemsOnlineEditableFlagItem.displayName}"/>";
	</c:forEach>
</c:if>
</script>
<link href="<%=request.getContextPath()%>/themes/stylesheet.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript">
function retrieveWorkPerformance(formObject, operation, id) {
	formObject.actionSelected.value = "RETRIEVE_WORK_PERFORMANCE";
	formObject.selectedWorkPerformanceId.value = id;
	formObject.submit();
}


function changeWrkPerfAction(operation) {
	
	if('DELETE_WORK_PERFORMANCE' == operation || 'MOVE_WORK_PERFORMANCE' == operation) {
		document.forms[0].action = '<%=request.getContextPath()%>/workPerformancesListMultiAction';
		document.forms[0].actionSelected.value = operation;
		if('MOVE_WORK_PERFORMANCE' == operation) {
			document.forms[0].actionSelected.value = 'DELETE_WORK_PERFORMANCE';
			document.forms[0].deleteType.value = 'MOVE';
		}
		else {
			document.forms[0].deleteType.value = '';
		}
		document.forms[0].submit();
	}
	else {		
		document.forms[0].action = '<%=request.getContextPath()%>/workPerformancesListMultiAction';
		document.forms[0].actionSelected.value = operation;
		document.forms[0].submit();
	}
}



function changeWrkPerfMedleyAction(operation, obj) {
	if(operation == 'ADD_TO_MEDLEY_WORK_PERFORMANCE') {
		if($("table#mainData tr input[name='selectedIds']:checked").length <= 1) {
				$("#serverErrorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><spring:message code="error.usage.addToMedley.nofWorkPerformancesInsufficient"/></span>');				
				unCheckSub(obj);
				location.href="#";
				return;
		}		
	}
	else if(operation == 'REMOVE_FROM_MEDLEY_WORK_PERFORMANCE') {	
		if($("table#mainData tr input[name='selectedIds']:checked").length < 1) {
				$("#serverErrorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><spring:message code="error.usage.removeFromMedley.nofWorkPerformancesInsufficient"/></span>');			
				location.href="#";
				return;
		}
	}		
	document.forms[0].action = '<%=request.getContextPath()%>/workPerformanceAction';
	document.forms[0].actionSelected.value = operation;
	document.forms[0].submit();
	
}


function changeAction(operation) {
    var formObject = document.performanceSearchForm;
	formObject.actionSelected.value = operation;
	var stopSubmit=false;
	var dynaErrorMessage='';
	
	if(operation == 'SEARCH_PROGRAM_PERFORMANCES'){
		formObject.performanceSearchType.value = 'PROGRAM_PERFORMANCE';
	}else if(operation == "SEARCH_WORK_PERFORMANCES"){
		formObject.performanceSearchType.value = 'WORK_PERFORMANCE';
	}else if(operation == 'DELETE_WORK_PERFORMANCE'){
	
		if($("table#mainData tr input[name='selectedIds']:checked").length <= 0) {
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.perfheader.delete.selection"/></span>'); 
			location.href="#";
			return;
		}
		else if (confirmDelete(formObject, 'selectedIds',  'Work Performances')) {
			stopSubmit = false;
		    document.forms[0].navigationType.value = "CURRENT";
			//Check the Source System of Work Performances To Delete
			for (i=0, n = formObject.selectedIds.length; i < n; i++) {
					if (formObject.selectedIds[i].checked == true) {
						//dynaErrorMessage = dynaErrorMessage +  '\nformObject.allSourceSystems[' + i + ']=' + formObject.allSourceSystems[i].value + 'sourceSystemsOnlineEditableFlagArray[formObject.allSourceSystems[i].value] =' + sourceSystemsOnlineEditableFlagArray[formObject.allSourceSystems[i].value];
						if(formObject.allSourceSystems[i].value != undefined 
						&& sourceSystemsOnlineEditableFlagArray[formObject.allSourceSystems[i].value] != undefined 
						&& sourceSystemsOnlineEditableFlagArray[formObject.allSourceSystems[i].value] == 'N'){
							dynaErrorMessage = dynaErrorMessage +  '<li>Work Performance ID = ' + formObject.selectedIds[i].value + ', Source System = ' + formObject.allSourceSystems[i].value + '</li>';
							stopSubmit=true;
						}
					}
			}
			
			if(stopSubmit){
				y = document.getElementById('serverErrorMessages');
				y.innerHTML = '';
				
				y = document.getElementById('errorMessages');
				y.innerHTML = '';
				y.innerHTML = '<ul><li>US_100454 - The following Work Performances cannot be deleted</li><ul>' + dynaErrorMessage + '</ul></ul>';
			}else{
				y = document.getElementById('errorMessages');
				y.innerHTML = '';
			}
		}else{
			stopSubmit = true;
		}

	}else if(operation == 'BULK_UPDATE_WORK_PERFORMANCE'){
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
	
	if(stopSubmit == false){
		formObject.submit();
	}
}

// this function will set the navigation type based on the button pressed
function setNavigationType(navigation) {
		document.forms[0].performanceSearchType.value = "WORK_PERFORMANCE";
		document.forms[0].navigationType.value = navigation;
		document.forms[0].actionSelected.value = "SEARCH_WORK_PERFORMANCES";
		document.forms[0].submit();
}


function retrieveProgramPerformance(formObject, operation, id){ 
	formObject.actionSelected.value = "RETRIEVE_PROGRAM_PERFORMANCE";
	formObject.selectedProgramPerformanceId.value = id;
	formObject.submit();
}

function retrieveWorkPerfList(formObject, operation, id){ 
	formObject.actionSelected.value = "RETRIEVE_WORK_PERFORMANCES_LIST";
	formObject.selectedProgramPerformanceId.value = id;
	formObject.submit();
}


function retrieveNextWorkPerfList(formObject, operation, id){ 
	formObject.actionSelected.value = "RETRIEVE_WORK_PERFORMANCES_LIST";
	formObject.selectedProgramPerformanceId.value = id;
	formObject.nextActionType.value = operation;
	formObject.submit();
}


function goToTop() {
	$('html, body').animate({ scrollTop: 0 }, 50);
}

function selectRows(obj) {
	
	var ix = $(obj).closest("tr").index();
	var newVal = $(obj).val();
	var origVal =  $('input[name=originalUseTypeCodes]:eq('+ix+')').val();
	
	if(newVal !== origVal) {
		$('input[name=selectedIds]:eq('+ix+')').attr('checked', true);
	}
	else {
		$('input[name=selectedIds]:eq('+ix+')').attr('checked', false);
	}
	
}


function applyToSelected(obj) {
	$("#serverErrorMessages").html("");
	$("#errorMessages").html("");
	$("#uierror").html("");
	
	var val = $('select[name=useTypeCodeAll]').val();
	
	if($.trim(val) === '') {
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><spring:message code="error.usage.workPerformance.required.useTypeCode"/></span>');
 		location.href="#"; 
 		return;
	}
	
	if($("table#mainData tr input[name='selectedIds']:checked").length > 0) {		
		$("input[name=selectedIds]:checked").each(function(){
			//var checkedIndex = this.value;
			var ix = $(this).closest("table#mainData tr").index();
			//alert('index ' + ix);
			//alert('setting value ' + val+ 'checkedIndex ' + checkedIndex);
			
			$("select[name=useTypeCode]:eq("+ix+")").val(val);
			
			
			var tempval = $(this).val() + '-_-' + val  ;
		//	alert('temp val ' + tempval);
			$(this).val(tempval);
			
			
			//$('input[name=selectedIds]:eq('+ix+')').val($('select[name=useTypeCode]:eq('+ix+')').val());
		});
		changeWrkPerfAction('UPDATE_WORK_PERFORMANCES_MULT');
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

<%--
<iframe  style="width: 100%; border: 0px; height:90px;"   
            id="topIFrame" name="topIFrame" scrolling="no"
            src="<%=request.getContextPath()%>/menu.jsp">   
</iframe>
 --%>
</div>
<div></div>

<form:form action="usageHomeSearch" modelAttribute="performanceSearch" name ="performanceSearchForm" >
		<form:hidden path="onlineEditable"/>
		<form:hidden path="performanceSearchType"/>
		<form:hidden path="actionSelected"/>
		<form:hidden path="navigationType"/>
		<form:hidden path="selectedWorkPerformanceId"/>		
		<form:hidden path="selectedProgramPerformanceId"/>
		<form:hidden path="headerIdForWorkPerf"/>
		<form:hidden path="deleteType"/>
		
		<form:hidden path="headerIdCurrent"/>
		<form:hidden path="headerIdNext"/>
		<form:hidden path="headerIdPrev"/>
		<form:hidden path="headerIdCurrentRowNum"/>
		<form:hidden path="nextActionType"/>


<!-- Table 1:  Screen Header  & errors -->
<table class="titletable">
<c:if test="${performanceSearch.actionSelected ne  'RETRIEVE_WORK_PERFORMANCES_LIST'}">
<tr>
	<td>Work Performance Search Results [WP-100]</td>
</tr>
</c:if>
<c:if test="${performanceSearch.actionSelected eq  'RETRIEVE_WORK_PERFORMANCES_LIST'}">
		<tr>
		<td>Work Performance List [WP-101]</td>
	</tr>
</c:if>
</table>
<table class="infotable" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<%@ include file="/views/usage/usInfoBar.jsp"%>
	</td>  
</tr>
<c:if test="${performanceSearch.actionSelected eq  'RETRIEVE_WORK_PERFORMANCES_LIST'}">
<tr>
	<td>
		<%@ include file="/views/usage/usProgramPerformanceInfoBar.jsp"%>
	</td>  
</tr>
</c:if>
</table>

<table class="errortable"  width="90%">
		<tr>
			<td>
				<div id="serverErrorMessages">
				<span class="txtGreen"><c:out value="${systemmessage}"/></span>
				<span class="txtRed"><c:out value="${systemerror}"/></span>
				</div>
				<div id="uierror"></div>
				<div id="errorMessages"></div>
			</td>
		</tr>
</table>
 <%-- 
Curr: <bean:write  name="performanceSearchForm" property="headerIdCurrent"/>
		Next: <bean:write  name="performanceSearchForm" property="headerIdNext"/>
		Prev: <bean:write  name="performanceSearchForm"  property="headerIdPrev"/>
		Row: <bean:write  name="performanceSearchForm"  property="headerIdCurrentRowNum"/>
--%>		
<div> 
 <a id="#bottom" style="padding-left:10px;padding-bottom:10px;cursor:pointer;font-weight: 800; font-size:1.5em;"><%--Bottom &darr; --%></a>
</div>

 <table class="buttonstable">
 <tr>
 <td align="left" width="20%">
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_PH_100_VIEW_PERF_HDR%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_PERF_HDR_DETAILS %>" id="<%=UIWidgetConstants.ID_PERF_HDR_DETAILS%>" title="Perf Header Details">onclick="javascript:retrieveProgramPerformance(document.performanceSearchForm, 'RETRIEVE_PROGRAM_PERFORMANCE', '${performanceSearch.headerIdForWorkPerf}');"</prep:uiWidget>
 </td>
 <td width="30%" align="left">
 <c:if test="${performanceSearch.actionSelected eq  'RETRIEVE_WORK_PERFORMANCES_LIST'}">
 <b>Show Deleted:</b> <form:select path="showDeletedRows"><form:option value="N">No</form:option><form:option value="Y">Yes</form:option></form:select>
 
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_PH_100_VIEW_PERF_HDR%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_NAV_GO%>" id="<%=UIWidgetConstants.ID_PERF_HDR_DETAILS%>" title="Perf Header Details">onclick="javascript:retrieveWorkPerfList(document.performanceSearchForm, 'RETRIEVE_WORK_PERFORMANCES_LIST', '${performanceSearch.headerIdForWorkPerf}');"</prep:uiWidget>
 </c:if>
 </td>
 <td width="50%" align="left">
 <c:if test="${performanceSearch.actionSelected eq 'RETRIEVE_WORK_PERFORMANCES_LIST'}">
  <c:if test="${performanceSearch.headerIdPrev ne '0'}">
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_PH_100_VIEW_PERF_HDR%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="PREV" id="<%=UIWidgetConstants.ID_PERF_HDR_DETAILS%>" title="Perf Header Details">onclick="javascript:retrieveNextWorkPerfList(document.performanceSearchForm, 'PREV', '${performanceSearch.headerIdPrev}');"</prep:uiWidget>
 </c:if>
<c:if test="${performanceSearch.headerIdNext ne '0'}"> 
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_PH_100_VIEW_PERF_HDR%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="NEXT" id="<%=UIWidgetConstants.ID_PERF_HDR_DETAILS%>" title="Perf Header Details">onclick="javascript:retrieveNextWorkPerfList(document.performanceSearchForm, 'NEXT', '${performanceSearch.headerIdNext}');"</prep:uiWidget>
 </c:if>
 </c:if>
 </td>
 </tr>
  <c:if test="${performanceSearch.actionSelected eq 'RETRIEVE_WORK_PERFORMANCES_LIST'}">
 <tr>
 <td align="left" style="padding-top:3px;font-weight:800;"> 
 Found <c:out value="${performanceSearch.workPerfCount}"/> result(s)
<form:hidden path="workPerfCount"/>
 </td>
 <td></td>
 </tr> 
 </c:if>
 </table> 

<%@ include file = "/views/common/coPageFilters.jsp"%>

 <c:if test="${performanceSearch.actionSelected ne 'RETRIEVE_WORK_PERFORMANCES_LIST'}">
<%@ include file = "/views/common/navigationInclude.jsp"%> 
</c:if>
<% boolean viewPermission = true; %>
<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_WP_100_VIEW_WRK_PERF %>" value="Y">			
	<%  viewPermission = true; %>			
</prep:hasPermission>	


<table class="contenttable"  id="mainData" cellspacing="1">
<thead>
<tr class="ui-widget-header" >
	<th width="2%"><INPUT name="selectAllCheckBox" type="checkbox" value="selectAll" onclick="javascript:toggleSelectAllEnabled(this.checked,'selectedIds')"></th>
	<th width="3%">View </th>
	<th width="4%">Work ID</th>
	<th width="11%">Work Title</th>
	<th width="10%">Performer</th>
	<th width="2%">Use Type</th>
	<th width="3%">Call Letter</th>
	<c:if test="${performanceSearch.actionSelected ne 'RETRIEVE_WORK_PERFORMANCES_LIST'}">	
	<th width="5%">Supplier Call Letter</th>	
	<th width="5%">Music User ID</th>	
	<th width="10%">Music User Type</th>
	<th width="3%" title="B - Blanket &#013;PP - Per Program">License Type</th>
	<th width="6%">Start Date &amp; Time</th>
	<th width="6%">End Date &amp; Time</th>
	</c:if>
	<c:if test="${performanceSearch.actionSelected eq 'RETRIEVE_WORK_PERFORMANCES_LIST'}">	
	<th width="10%">Writer / Composer</th>	
	<th width="5%">Detection Time</th>
	<th width="5%">Confidence Level</th>
	</c:if>
	<th width="3%">Work Seq #</th>
	<th width="3%">Medley Seq #</th>
	<th width="3%">Duration</th>
	<th width="3%">Play Count</th>
	<th width="5%">Supplier</th>
	<th width="3%">File ID</th>
	<c:if test="${performanceSearch.actionSelected ne 'RETRIEVE_WORK_PERFORMANCES_LIST'}">
	<th width="4%">Header Details</th>
	<th width="6%">Header's Work Perf List</th>
	</c:if>
	<th width="5%">APM Work Perf ID</th>
</tr>
</thead>    
<tbody>     	
<c:if test="${not empty performanceSearch}">
<c:forEach items="${performanceSearch.searchResults}" var="workPerformance" varStatus="currentIndexId">		
	
<tr align="center">

<td>

<input type="hidden" name="selectedIndexes" value='${currentIndexId.index}'/>
<input type="hidden" name="allPerformanceIds" value='${workPerformance.performanceHeaderId}'/>
<input type="hidden" name="allPerformanceVersionNumbers" value='${workPerformance.performanceHeaderVersionNumber}'/>
<input type="hidden" name="allSourceSystems" value='${workPerformance.sourceSystem}'/>
<%-- <input type="hidden" name="allLegacyPerformanceHeaderIds" value='${workPerformance.legacyProgramPerformanceIdm}'/>
<%-- <input type="hidden" name="allPostingStatuses" value='${workPerformance.postingIndicator}'/> --%>
<input type="hidden" name="allWorkPerformanceIds" value='${workPerformance.workPerformanceId}'/>
<input type="hidden" name="allWorkPerformanceVersionNumbers" value='${workPerformance.versionNumber}'/>
<input type="hidden" name="allLegacyWorkPerformanceIds" value='${workPerformance.legacyWorkPerformanceId}'/>	
<c:out value="${workPerformance.workPerformanceDeleteFlag}" />

<c:if test="${workPerformance.onlineWorkPerfEditable eq 'Y'}">
<c:if test="${workPerformance.workPerformanceDeleteFlag eq 'Y'}">
	<form:checkbox path="selectedIds" value="${workPerformance.workPerformanceId}" disabled="true" />
</c:if>
<c:if test="${workPerformance.workPerformanceDeleteFlag ne 'Y'}">
	<form:checkbox path="selectedIds" value="${workPerformance.workPerformanceId}"/>
</c:if>	
</c:if>
<c:if test="${workPerformance.onlineWorkPerfEditable ne 'Y'}">
	<form:checkbox path="selectedIds" value="${workPerformance.workPerformanceId}" disabled="true" />
</c:if>
<input type="hidden" name="originalWorkPerformanceIds" value="${workPerformance.workPerformanceId} "/>
</td>
<td valign="middle" align="right">
		<c:if test="${workPerformance.errorFlag eq '2'}"> 
			<div class="redcircle"></div>
		</c:if>
		<c:if test="${workPerformance.errorFlag eq '1'}">  
			<div class="yellowcircle"></div>
		</c:if>
<c:if test="${workPerformance.workPerformanceDeleteFlag ne 'Y'}">		
	<% if(viewPermission) { %>
		<a href="javascript:retrieveWorkPerformance(document.forms[0], 'RETRIEVE_WORK_PERFORMANCE', ${workPerformance.workPerformanceId});" class="<%=UIWidgetConstants.STYLE_VIEW_ICON_ENABLED%>" title="View Details"></a>
	<% } else { %>
		<a class="<%=UIWidgetConstants.STYLE_VIEW_ICON_DISABLED%>"></a>
	<% } %>
</c:if>	
</td>
<td><c:out value="${workPerformance.workId}"/></td>
<td align="left"><c:out value="${workPerformance.workTitle}"/></td>
<td align="left"><c:out value="${workPerformance.performerName}"/></td>
<td align="center">
<c:if test="${workPerformance.workPerformanceDeleteFlag ne 'Y'}">
					<form:select path="searchResults[${currentIndexId.index}].useTypeCode" size="1" id="_useTypeCode${currentIndexId.index}" onchange="javascript:selectRows(this)">
						<form:option value="" />
						<form:options items="${UseTypesList}" itemLabel="displayName"	itemValue="id" />
					</form:select>
</c:if>
<c:if test="${workPerformance.workPerformanceDeleteFlag eq 'Y'}">
					<form:select path="searchResults[${currentIndexId.index}].useTypeCode" size="1" id="_useTypeCode" disabled="true">
						<form:option value="" />
						<form:options items="${UseTypesList}" itemLabel="displayName"	itemValue="id" />
					</form:select>
</c:if>					

<input type="hidden" name="selectedUseTypeCodes" id="_tempUseTypeCode" value='${workPerformance.useTypeCode}'/>					
<input type="hidden" name="originalUseTypeCodes" id="_tempUseTypeCode" value='${workPerformance.useTypeCode}'/>
</td>
<td class="txtBlk"><c:out value="${workPerformance.musicUserFullName}"/></td>

	<c:if test="${performanceSearch.actionSelected ne 'RETRIEVE_WORK_PERFORMANCES_LIST'}">
<td><c:out value="${workPerformance.musicUserCallLetterSuffix}"/></td>

<td><c:out value="${workPerformance.musicUserId}"/></td>
<td align="left"><c:out value="${workPerformance.musicUserTypeDescription}"/></td>
<td><c:out value="${workPerformance.licenseTypeCode}"/></td>
<td><c:out value="${workPerformance.performanceStartDate}"/> <c:out value="${workPerformance.performanceStartTime}"/></td> 
<td><c:out value="${workPerformance.performanceEndDate}"/> <c:out value="${workPerformance.performanceEndTime}"/></td>

	</c:if>

<c:if test="${performanceSearch.actionSelected eq 'RETRIEVE_WORK_PERFORMANCES_LIST'}">
<td align="left"><c:out value="${workPerformance.writer}"/></td>
<td align="left"><c:out value="${workPerformance.detectionTime}"/></td>
<td><c:out value="${workPerformance.confidenceLevel}"/></td>
</c:if>

	
<td><c:out value="${workPerformance.workSequenceNumber}"/></td>
<td><c:out value="${workPerformance.medleySequenceNumber}"/></td>
<td><c:out value="${workPerformance.workPerformanceDuration}"/></td>
<td><c:out value="${workPerformance.playCount}"/></td>
<td align="left"><c:out value="${workPerformance.supplierCode}"/></td>
<td><c:out value="${workPerformance.fileId}"/></td>
<c:if test="${performanceSearch.actionSelected ne 'RETRIEVE_WORK_PERFORMANCES_LIST'}">
<td align="right">
<a href="javascript:retrieveProgramPerformance(document.performanceSearchForm, 'RETRIEVE_PROGRAM_PERFORMANCE', ${workPerformance.performanceHeaderId});">
	<c:out value="${workPerformance.performanceHeaderId}"/>
</a>	
</td>
<td align="right">
<a href="javascript:retrieveWorkPerfList(document.performanceSearchForm, 'RETRIEVE_PROGRAM_PERFORMANCE',  ${workPerformance.performanceHeaderId});">
	<c:out value="${workPerformance.performanceHeaderId}"/>
</a>
</td>
</c:if>
<td align="right"><c:out value="${workPerformance.workPerformanceId}"/></td>
</tr>
</c:forEach>
</c:if>
</tbody>				                 
</table>

<c:if test="${performanceSearch.actionSelected ne 'RETRIEVE_WORK_PERFORMANCES_LIST'}">
<table class="contenttable" cellspacing="0" cellpadding="0">
	<tr class="searchpagestatus">
		<td align="right">Results found so far <b><c:out value="${performanceSearch.progressiveResultsCount}" /></b>.</td>
	</tr>
</table>
<%@ include file = "/views/common/navigationInclude.jsp"%> 
<table class="buttonstable" cellspacing="0" cellpadding="2">
<tr height="10"></tr>
<tr>
<td align="left">
<c:if test="${performanceSearch.actionSelected eq 'RETRIEVE_WORK_PERFORMANCES_LIST'}">
	<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WP_101_DELETE_WRK_PERF%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_DELETE %>" id="<%=UIWidgetConstants.ID_DELETE%>" title="Delete selected Work Performances">onclick="javascript:changeAction('DELETE_WORK_PERFORMANCE');"</prep:uiWidget>
</c:if>	
</td>
</tr>
</table>
</c:if>
<c:if test="${performanceSearch.actionSelected eq 'RETRIEVE_WORK_PERFORMANCES_LIST'}">
<table class="buttonstable">


 <tr style="font-weight:800;">
 <td align="left" style="padding-top:5px;"> 
 Found <c:out value="${performanceSearch.workPerfCount}"/> result(s)
 </td>
 </tr> 


		<tr>
			<td align="left" width="30%">	
				<c:if test="${performanceSearch.onlineEditable eq 'Y'}">
				<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WP_101_DELETE_WRK_PERF %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_DELETE %>" id="<%=UIWidgetConstants.ID_DELETE%>" title="Delete">onclick="javascript:void(0);"</prep:uiWidget>
				<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WP_101_MOVE_WRK_PERF %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_MOVE %>" id="<%=UIWidgetConstants.ID_MOVE%>" title="Move">onclick="javascript:void(0);"</prep:uiWidget>		  
				<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WP_200_CREATE_WRK_PERF %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_ADD_NEW_ENTRY %>" id="<%=UIWidgetConstants.ID_ADD_NEW_ENTRY%>" title="Add New Entry">onclick="javascript:changeWrkPerfAction('ADD_NEW_WORK_PERFORMANCE');"</prep:uiWidget>					  
				<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WP_200_UPDATE_WRK_PERF %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_UPDATE %>" id="<%=UIWidgetConstants.ID_UPDATE%>" title="Update">onclick="javascript:void(0);"</prep:uiWidget>
				
				</c:if>
				<c:if test="${performanceSearch.onlineEditable ne 'Y'}">
				<prep:uiWidget disabled="true" name="<%= ProtectedResourcesConstants.APM_WP_101_DELETE_WRK_PERF %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_DELETE %>" id="<%=UIWidgetConstants.ID_DELETE%>" title="Delete">onclick="javascript:changeWrkPerfAction('DELETE_WORK_PERFORMANCE');"</prep:uiWidget>
				<prep:uiWidget disabled="true" name="<%= ProtectedResourcesConstants.APM_WP_101_MOVE_WRK_PERF %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_MOVE %>" id="<%=UIWidgetConstants.ID_MOVE%>" title="Move">onclick="javascript:changeWrkPerfAction('DELETE_WORK_PERFORMANCE');"</prep:uiWidget>		  
				<prep:uiWidget disabled="true" name="<%= ProtectedResourcesConstants.APM_WP_200_CREATE_WRK_PERF %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_ADD_NEW_ENTRY %>" id="<%=UIWidgetConstants.ID_ADD_NEW_ENTRY%>" title="Add New Entry">onclick="javascript:changeWrkPerfAction('ADD_NEW_WORK_PERFORMANCE');"</prep:uiWidget>
				</c:if>		  
			</td>
			<td width="20%" align="left">
				<form:select path="useTypeCodeAll" size="1" id="_useTypeCodeAll">
					<form:option value="" />
					<form:options items="${UseTypesList}" itemLabel="displayName"	itemValue="id" />
				</form:select>
				&nbsp;&nbsp;
				<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WP_200_UPDATE_WRK_PERF%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_ASSIGN_USAGE %>" id="<%=UIWidgetConstants.ID_ASSIGN_USAGE %>" title="<%=UIWidgetConstants.LABEL_ASSIGN_USAGE %>"> onclick="javascript:applyToSelected(this);"</prep:uiWidget>					
			</td>
			
			<td width="50%">				
				<c:if test="${performanceSearch.onlineEditable eq 'Y'}">
				<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WP_101_WRK_PERF_ADD_MEDLEY%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_ADD_TO_MEDLEY %>" id="<%=UIWidgetConstants.ID_ADD_TO_MEDLEY%>" title="Add to Medley">onclick="javascript:changeWrkPerfMedleyAction('ADD_TO_MEDLEY_WORK_PERFORMANCE', this);"</prep:uiWidget>
				<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WP_101_WRK_PERF_DELETE_MEDLEY%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_REMOVE_FROM_MEDLEY %>" id="<%=UIWidgetConstants.ID_REMOVE_FROM_MEDLEY%>" title="Remove from Medley">onclick="javascript:changeWrkPerfMedleyAction('REMOVE_FROM_MEDLEY_WORK_PERFORMANCE', this);"</prep:uiWidget>
				</c:if>
				<c:if test="${performanceSearch.onlineEditable ne 'Y'}">
				<prep:uiWidget disabled="true" name="<%= ProtectedResourcesConstants.APM_WP_101_WRK_PERF_ADD_MEDLEY%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_ADD_TO_MEDLEY %>" id="<%=UIWidgetConstants.ID_ADD_TO_MEDLEY%>" title="Add to Medley">onclick="javascript:changeWrkPerfMedleyAction('ADD_TO_MEDLEY_WORK_PERFORMANCE', this);"</prep:uiWidget>
				<prep:uiWidget disabled="true" name="<%= ProtectedResourcesConstants.APM_WP_101_WRK_PERF_DELETE_MEDLEY%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_REMOVE_FROM_MEDLEY %>" id="<%=UIWidgetConstants.ID_REMOVE_FROM_MEDLEY%>" title="Remove from Medley">onclick="javascript:changeWrkPerfMedleyAction('REMOVE_FROM_MEDLEY_WORK_PERFORMANCE', this);"</prep:uiWidget>
				</c:if>
			</td>
			<td>&nbsp;
			</td>
			<td align="right">
			</td>
			</tr>
	</table>
	</c:if>

<div> 
 <a id="#top" style="padding-left:10px;padding-bottom:10px;cursor:pointer;font-weight: 800; font-size:1.5em;"><%--Top &uarr;--%></a>
</div>
<div id="dialog_Delete" title="Delete ?" style="display:none">Are you sure you want to Delete the selected Work Performances?</div>
<div id="dialog_Move" title="Move ?" style="display:none">Are you sure you want to Move the selected Work Performances to a new Perf Header?</div>
</form:form>
</body>
</html>
