<!DOCTYPE HTML>

<%--
/**
 * Description: Work Performance Detail Screen
 * @Author Manoj_Puli
 * @version $Revision$ $Date$
 */
--%> 
<%@ page language="java" contentType="text/html"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ascap.apm.common.utils.cache.lookup.LookupTableCache"%>
<%@ page import="com.ascap.apm.common.utils.cache.lookup.LookupTables"%>
<%@ page import="com.ascap.apm.common.utils.UsageCommonValidations"%>
<%@ page import="com.ascap.apm.controller.utils.*"%>
<%@ page import="com.ascap.apm.vob.usage.*"%>
<%@ page import="com.ascap.apm.common.utils.constants.UsageConstants"%>
<%@ page import="com.ascap.apm.common.utils.constants.LicenseTypeConstants"%>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>
<html>
<head>
<%@ include file = "/views/common/uiWidgets.jsp"%>

<title>APM</title>
<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css" />
<style>
br {
	padding-bottom: 10px;
}
input:not([type="image"]), textarea {
	box-sizing: border-box;
	outline: none;
	font-size: 12px;	
  	border: 1px solid #aaaaaa;
  	
}
input[type=text]:focus, textarea:focus {
  @include box-shadow(0 0 5px rgba(81, 203, 238, 1));
  /*padding: 3px 0px 3px 3px;
  margin: 5px 1px 3px 0px;*/
  border: 1px solid rgba(81, 203, 238, 1);
}



</style>
<SCRIPT type="text/javaxcript" src="<%=request.getContextPath()%>/js/common.js"></SCRIPT>
<script type="text/javascript">

$(function() {
	if('SOUNDMOUSE' === $('#_suppCode').val()) {
		if($('#_opMode').val() === '' ) {	
			if($('#_useTypeCode').val() === '') {
				$('#_useTypeCode').val('NF');
			}		
			if($('#_duration').val() === '') {
				$('#_duration').val('1');
			}
		}
	}
});


function changeAction(operation) {
	document.workPerformanceForm.actionSelected.value = operation;
	var stopSubmit=false;
	var formObject=document.workPerformanceForm;	
	
	if(operation == "DELETE_WORK_PERFORMANCE"){
		if(confirmDelete(formObject, 'workperformanceId',  'Work Performances')) {
		    stopSubmit = false;
		}else{
			stopSubmit = true;
		}
	}
	$("#_Add").replaceWith("<em>Submitting...</em>");
	if(stopSubmit == false){
		document.workPerformanceForm.submit();
	}
	
}

function searchSreSmcPointAllocationHistory(val1){
window.open('<%=request.getContextPath()%>/usage/sreSmcPointAllocationHistorySearchAction.do?navigationType=FIRST&actionType=context&mode=usWorkPerformance&workId=' + document.workPerformanceForm.workId.value + '&num=' + val1,'', 'width=800,height=500,toolbar=0,status=1,scrollbars=1,location=0,resizable=0');
}
function tempWork(val1){
window.open('<%=request.getContextPath()%>/usage/searchWorkEmpty.do?actionType=context&mode=usWorkPerformance&num=' + val1,'', 'width=750,height=500,toolbar=0,status=1,scrollbars=1,location=0,resizable=0');
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

<% 
	pageContext.setAttribute("UseTypesList", HtmlSelectOption.getLookUpTable("UseTypes"), PageContext.PAGE_SCOPE);   
%>


<fieldset class="fieldsetfull"><legend class="legendfull">Work Perf Details [WP-200]</legend>
<form:form action="workPerformanceMultiAction" modelAttribute="workPerformance" name="workPerformanceForm">



			<form:hidden  path="onlineHeaderEditable" />
			<form:hidden  path="onlineWorkPerfEditable" />
			<form:hidden  path="actionSelected" />
			<form:hidden  path="operationMode" id="_opMode"/>
			<form:hidden  path="musicUserTypeCode" />
			<form:hidden  path="licenseTypeCode" />
			<form:hidden path="performanceHeaderId" />
			<form:hidden path="sourceSystem" />
			
			<form:hidden path="workPerformanceId" />
			<form:hidden path="versionNumber" />
			<form:hidden path="isCurrentVersion" />

			<form:hidden path="showProgramCode" />

			<form:hidden path="legacyProgramPerformanceId" />
			<form:hidden path="legacyWorkPerformanceId" />

			<form:hidden path="distributionId" />
			<form:hidden path="distributionDate" />
			<form:hidden path="runcontrollId" />
			<form:hidden path="usageSelectionId" />

			<form:hidden path="workSequenceNumber" />
			<form:hidden path="medleySequenceNumber" />

			<form:hidden path="allocationGroupId" />
			<form:hidden path="workPerformanceAdjustmentIndicator" />

			<form:hidden path="copyrightArrangementPercentage" />
			<form:hidden path="workPerformanceActionCode" />

			<form:hidden path="musicUserWeight" />
			<form:hidden path="livePopEconomicComponent" />
			<form:hidden path="livePopMusicDensityComponent" />
			<form:hidden path="creditsPerSitePerPlay" />
			<form:hidden path="pointsForTitle" />
			<form:hidden path="useWeight" />
			<form:hidden path="useWeightRuleId" />
			<form:hidden path="creditSymbolForUseWeight" />
			<form:hidden path="timeOfDayWeight" />
			<form:hidden path="perSetCode" />
			<form:hidden path="procStatus" />
			
			<form:hidden path="statusDate" />
			<form:hidden path="fileId" />
			<form:hidden path="matchTypeCode" />
			<form:hidden path="matchTypeDes" />
			<form:hidden path="providerId" />
			<form:hidden path="supplierCode" id="_suppCode"/>
			<form:hidden path="dbWorkTitle" />
			<form:hidden path="dbPerformerName" />
			<form:hidden  path="errorFlag" />


<table class="titletable" style="display:none">		
	<tr>
		<td>Work</td>
	</tr>
</table>


<table class="infotable" cellpadding="0" cellspacing="0">
<tr> 
	<td><%@ include file="/views/usage/usInfoBar.jsp"%></td>
</tr> 
<tr>
	<td><%@ include file="/views/usage/usProgramPerformanceInfoBar.jsp"%></td>
</tr>
</table>



<table class="errortable">
<tr>
	<td colspan="4">
		<span class="txtGreen"><c:if test="${not empty systemmessage }"><li> <c:out value="${systemmessage}"/></li></c:if></span>
		<span class="txtRed"><c:if test="${not empty systemerror }"><li><c:out value="${systemerror}" escapeXml="false"/></li></c:if>
		<c:forEach var="systemerror" items="${systemerrorlist}">
				<li><c:out value="${systemerror}"  escapeXml="false"/></li>
				</c:forEach></span>
	</td>
</tr>
</table>
		
			
			
			
<%
	String musicUserTypeDetermined="";
	String musicUserLicenseDetermined="";
	String sourceSystemDetermined="";
	boolean isUsedByDistribution=false;	
	com.ascap.apm.vob.usage.WorkPerformance xWorkPerformanceForm;
	xWorkPerformanceForm = (com.ascap.apm.vob.usage.WorkPerformance) request.getAttribute("workPerformance");
	musicUserTypeDetermined = xWorkPerformanceForm.getMusicUserTypeCode();
	musicUserLicenseDetermined = xWorkPerformanceForm.getLicenseTypeCode();
	sourceSystemDetermined = xWorkPerformanceForm.getSourceSystem();

	if(com.ascap.apm.common.utils.UsageCommonValidations.areBothEqualIgnoreCase("Y",xWorkPerformanceForm.getIsUsedByDistribution())){
		isUsedByDistribution=true;
	}

	if(sourceSystemDetermined == null){
		sourceSystemDetermined ="";
	}

	//Being Restrictive in terms of what is editable
	boolean isOnlineEditable=false;
	boolean isTVPPRuleGroupMusicUserTypes=false;	
	boolean isDefTVRuleGroupMusicUserTypes=false; // Default TV
	boolean isPayPerViewTVRuleGroupMusicUserTypes=false; // TV Pay Per View

	
	String lookupValueOnlineEditableFlag=null;
	String isOnlineEditableConfigMissingmessage="";

	Map lookupTVPPRuleGroupMusicUserTypes = null;	
	Map lookupDefTVRuleGroupMusicUserTypes = null;
	Map lookupPayPerViewTVRuleGroupMusicUserTypes = null;
	try{

		try{
			lookupTVPPRuleGroupMusicUserTypes = com.ascap.apm.common.utils.cache.lookup.LookupTables.getLookupTableItemsWithKeys("TVPPRuleGroupMusicUserTypes");

			if(lookupTVPPRuleGroupMusicUserTypes != null && musicUserTypeDetermined != null){
				isTVPPRuleGroupMusicUserTypes = lookupTVPPRuleGroupMusicUserTypes.containsKey(musicUserTypeDetermined);	
			}else{
				System.out.println("Lookup Table TVPPRuleGroupMusicUserTypes is not configured Please check !!!");
			}
		} catch (com.ascap.apm.common.exception.cache.InvalidCacheException ice){
			isOnlineEditableConfigMissingmessage="Configuration missing for the TVPP rule group 'TVPPRuleGroupMusicUserTypes' Cannot determine if the performance can be edited.(msg 4).";
		}  catch (Exception knfe){
			//Key Not found Excetion
		}
	

		try{
			lookupDefTVRuleGroupMusicUserTypes = com.ascap.apm.common.utils.cache.lookup.LookupTables.getLookupTableItemsWithKeys("DefTVRuleGroupMusicUserTypes");
			if(lookupDefTVRuleGroupMusicUserTypes != null && musicUserTypeDetermined != null){
				isDefTVRuleGroupMusicUserTypes = lookupDefTVRuleGroupMusicUserTypes.containsKey(musicUserTypeDetermined);	
			}else{
				System.out.println("Lookup Table DefTVRuleGroupMusicUserTypes is not configured Please check !!!");
			}
		} catch (com.ascap.apm.common.exception.cache.InvalidCacheException ice){
			//isOnlineEditableConfigMissingmessage="Configuration missing for the DefTV rule group 'DefTVRuleGroupMusicUserTypes' Cannot determine if the performance can be edited.(msg 5).";
		}  catch (Exception knfe){
			//Key not found Exception
		}
	
		try{
			lookupPayPerViewTVRuleGroupMusicUserTypes = com.ascap.apm.common.utils.cache.lookup.LookupTables.getLookupTableItemsWithKeys("PayPerViewTVRuleGroupMusicUserTypes");
			if(lookupPayPerViewTVRuleGroupMusicUserTypes != null && musicUserTypeDetermined != null){
				isPayPerViewTVRuleGroupMusicUserTypes = lookupPayPerViewTVRuleGroupMusicUserTypes.containsKey(musicUserTypeDetermined);	
			}else{
				System.out.println("Lookup Table DefTVRuleGroupMusicUserTypes is not configured Please check !!!");
			}
		} catch (com.ascap.apm.common.exception.cache.InvalidCacheException ice){
			//isOnlineEditableConfigMissingmessage="Configuration missing for the DefTV rule group 'DefTVRuleGroupMusicUserTypes' Cannot determine if the performance can be edited.(msg 5).";
		}  catch (Exception knfe){
			//Key not found Exception
		}

	
		lookupValueOnlineEditableFlag = com.ascap.apm.common.utils.cache.lookup.LookupTables.getLookupTableItemDescription("SourceSystemsOnlineEditableFlag", sourceSystemDetermined);
		
		if("Y".equalsIgnoreCase(lookupValueOnlineEditableFlag)){
			isOnlineEditable = true;
		}

		if(isTVPPRuleGroupMusicUserTypes && LicenseTypeConstants.PER_PROGRAM.equalsIgnoreCase(musicUserLicenseDetermined)){
			isOnlineEditable = false;
		}
		
		if(com.ascap.apm.common.utils.UsageCommonValidations.isEmptyOrNull(lookupValueOnlineEditableFlag)){
			isOnlineEditableConfigMissingmessage="Configuration missing for the source system '"+sourceSystemDetermined+"' Cannot determine if the performance can be edited. So Editing this performance is not allowed. Please contact PREP Support if this system is valid and the performance should be allowed to be edited online.";
		}
		
	} catch (com.ascap.apm.common.exception.cache.InvalidCacheException ice){
		isOnlineEditableConfigMissingmessage="Configuration missing for the source system '" + sourceSystemDetermined+"' Cannot determine if the performance can be edited. So Editing this performance is not allowed. Please contact PREP Support if this system is valid and the performance should be allowed to be edited online(msg 1).";
	} catch (com.ascap.apm.common.exception.cache.KeyNotFoundException knfe){
		isOnlineEditableConfigMissingmessage="Configuration missing for the source system '" + sourceSystemDetermined+"' Cannot determine if the performance can be edited. So Editing this performance is not allowed. Please contact PREP Support if this system is valid and the performance should be allowed to be edited online(msg 2).";
	}

	//Unconditionally disable editing if used by Distribution
	if(isUsedByDistribution){
		isOnlineEditable = false;
	}

%>

<table class="detailstable">	
				<tr  class="txtBlk_bold" valign="top">
							<td width="20%">APM Work Perf ID: <br><span class="txtBlk"><c:out value="${workPerformance.workPerformanceId}"/> </span></td>
							<td width="20%">Work Seq #: <br><span class="txtBlk"><c:out value="${workPerformance.workSequenceNumber}"/> </span></td>
							<td width="20%">Medley Seq #: <br><span class="txtBlk"><c:out value="${workPerformance.medleySequenceNumber}"/></span></td>
							<td width="20%">Status: <br><span class="txtBlk"><c:out value="${workPerformance.procStatus}"/> </span></td>
							<td width="20%">Status Date: <br><span class="txtBlk"><c:out value="${workPerformance.statusDate}"/></span></td>
				</tr>
				<tr height="20px"></tr>		
				<tr class="txtBlk_bold" valign="top">
							<td>File ID: <br><span class="txtBlk"><c:out value="${workPerformance.fileId}"/> </span></td>
							<td>Match Type: <br><span class="txtBlk"><c:out value="${workPerformance.matchTypeDes}"/> </span></td>
							<td>Supplier Work Code: <br><span class="txtBlk"><c:out value="${workPerformance.providerId}"/></span></td>
							<td>Supplier: <br><span class="txtBlk"><c:out value="${workPerformance.supplierCode}"/> </span></td>
							<td></td>
				</tr>
</table>
<table class="detailstable">	

	<tr height="20px">	
				<td width="20%"></td>
				<td width="25%"></td>	
				<td width="25%"></td>
				<td width="30%"></td>
	</tr>			
	<tr>
		<td colspan="4"><span class="txtRed"><%=isOnlineEditableConfigMissingmessage%></span></td> 
	</tr>

	<tr class="txtBlk_bold">
				<td>Work ID <br>				
					<form:input path="workId" size="11" maxlength="11" />									
				</td>
				<td>Work Title<br>
					<form:input  path="workTitle" size="50" maxlength="150" />
				</td>
				<td>Performer<br>
					<form:input  path="performerName" maxlength="100" size="50"/>
				</td>
				<td></td>
	</tr>
	<tr height="10px"></tr>
	<tr class="txtBlk_bold">
				<td>Use Type<br>
					<form:select path="useTypeCode" size="1" id="_useTypeCode">
						<form:option value="" />
						<form:options items="${UseTypesList}" itemLabel="displayName"	itemValue="id" />
					</form:select>
				</td>
				
				<td>Duration (in Seconds)<br>
					<form:input path="workPerformanceDuration" size="9" maxlength="9" id="_duration"/>
				</td>
				
				<td>Play Count<br>
					<form:input path="playCount" size="10" maxlength="10" />
				</td>	
				<td></td>
	</tr>
	
	
	<c:if test="${workPerformance.supplierCode eq 'SOUNDMOUSE'}">
	<tr height="10px"></tr>
	<tr class="txtBlk_bold">
				<td>Detection Time<br>
					<form:input path="detectionTime" size="15" maxlength="20"/>
				</td>
				
				<td>Confidence Level<br>
					<form:input path="confidenceLevel" size="15" maxlength="10" readonly="true"/>
				</td>
				
				<td>Library Disc Title<br>
					<form:input path="libraryDiscTitle" size="40" maxlength="50" readonly="true" />
				</td>
				
				<td>Library Disc ID<br>
					<form:input path="libraryDiscId" size="15" maxlength="20" readonly="true"/>
				</td>
	</tr>
	<tr height="10px"></tr>
	<tr class="txtBlk_bold">
				<td colspan="4">Writer / Composer<br>
					<form:input  path="writer" maxlength="100" size="50"/>
				</td>
	</tr>
	</c:if>
						
	<tr height="20"></tr>
	</table>
	<table class="contenttable">
					<thead>
					<tr>
						<td colspan="3" align="left"  class="textlabelmediumblue" >Errors</td>
					</tr>
				</thead>
				<tbody>
				<tr>
					<td width="1%" align="left"></td>
					<td width="10%" align="left"></td>
					<td width="80%"></td>
				</tr>
					
					<c:if test="${not empty workPerformance.errorsAndWarnings }">
				
						<c:forEach var="performanceMessage" items="${workPerformance.errorsAndWarnings}">
						
						<c:if test ="${performanceMessage.messageCategoryCode eq 'R' }">
							<input type="hidden" name="errorTypes" value='<c:out value="${performanceMessage.messageCategoryCode }"/>'/>
							<input type="hidden" name="errorCodes" value='<c:out value="${performanceMessage.messageCode }"/>'/>
							<input type="hidden" name="errorMessages" value='<c:out value="${performanceMessage.messageDescription }"/>'/>
								<tr>
									<TD bgcolor="#ff0000">&nbsp;</td>
									<td><font class="txtBlk"><c:out value="${performanceMessage.messageCode }"/> </font></td>
									<td><font class="txtBlk"><c:out value="${performanceMessage.messageDescription }"/> </font></td>
								</tr>
								</c:if>
     					</c:forEach>
					</c:if>			
					
										
					<c:if test="${ not empty workPerformance.errorsAndWarnings}">
							<c:forEach var ="performanceMessage" items="${workPerformance.errorsAndWarnings}">
							<c:if test="${performanceMessage.messageCategoryCode eq 'E' }">
							
							<input type="hidden" name="errorTypes" value='<c:out value="${performanceMessage.messageCategoryCode }"/>'/>
							<input type="hidden" name="errorCodes" value='<c:out value="${performanceMessage.messageCode }"/>'/>
							<input type="hidden" name="errorMessages" value='<c:out value="${performanceMessage.messageDescription }"/>'/>
								<tr class="tableroweven">
									<TD bgcolor="#ff0000">&nbsp;</td>
									<td><font class="txtBlk"><c:out value="${performanceMessage.messageCode }"/></font></td>
									<td><font class="txtBlk"><c:out value="${performanceMessage.messageDescription }"/></font></td>
								</tr>
								</c:if>
						</c:forEach>
	            </c:if>
					
				</tbody>
				</TABLE>
				<table class="contenttable">
					<thead>
					<tr>
					<td colspan="3" align="left"  class="textlabelmediumblue" >Warnings</td>
					</tr>
					</thead>
					<tbody>
				<tr>
					<td width="1%" align="left"></td>
					<td width="10%" align="left"></td>
					<td width="80%"></td>
				</tr>

						<c:if test="${not empty workPerformance.errorsAndWarnings}">
							<c:forEach var ="performanceMessage" items="${workPerformance.errorsAndWarnings }">
							<c:if test="${performanceMessage.messageCategoryCode eq 'W' }">
							<input type="hidden" name="errorTypes" value ="${performanceMessage.messageCategoryCode}"/>
							<input type="hidden" name="errorCodes" value ="${performanceMessage.messageCode}"/>
							<input type="hidden" name="errorMessages" value="${performanceMessage.messageDescription}"/>
								<tr class="tableroweven">
									<TD bgcolor="#FFA500">&nbsp;</td>
									<td><font class="txtBlk"><c:out value ="${performanceMessage.messageCode}"/> </font></td>
									<td><font class="txtBlk"><c:out value ="${performanceMessage.messageDescription}"/></font>
									</td>
								</tr>
								</c:if>
						</c:forEach>
				</c:if>
				</tbody>
	</table>
<table class="buttonstable">
	
<tr>

<c:if test="${workPerformance.operationMode eq 'RETRIEVE_MODE' }">
   <td align="left"> 
	<A href="usageHomeSearch?backToSearchResults=true" class="ui-button ui-button-text-icon-primary ui-widget ui-state-default ui-corner-all">
							<span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
							<span class="ui-button-text">Back To Search Results</span>
	</A>		
	<A href="usageHomeSearch?actionSelected=RETRIEVE_WORK_PERFORMANCES_LIST&amp;selectedProgramPerformanceId=<c:out value="${workPerformance.performanceHeaderId }"/>"
								 class="ui-button ui-button-text-icon-primary ui-widget ui-state-default ui-corner-all">
								<span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
								<span class="ui-button-text"><%=UIWidgetConstants.LABEL_VIEW_WORK_PERFORMANCES %></span>
	</A>	
  	</td>
	<td align="right">	
	<c:if test="${workPerformance.onlineWorkPerfEditable eq 'Y' }">
			<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WP_200_UPDATE_WRK_PERF %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_UPDATE %>" id="<%=UIWidgetConstants.ID_UPDATE%>" title="Update">onclick="javascript:changeAction('UPDATE_WORK_PERFORMANCE');"</prep:uiWidget>
		</c:if>		
		<c:if test="${workPerformance.onlineWorkPerfEditable eq 'N' }">			
			<prep:uiWidget disabled="true" name="<%= ProtectedResourcesConstants.APM_WP_200_UPDATE_WRK_PERF %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_UPDATE %>" id="<%=UIWidgetConstants.ID_UPDATE%>" title="Update">onclick="javascript:changeAction('UPDATE_WORK_PERFORMANCE');"</prep:uiWidget>
		</c:if>
	</td>
	</c:if>
	<c:if test="${workPerformance.operationMode ne 'RETRIEVE_MODE'}">
<td align="left">
<A href="usageHomeSearch?backToSearchResults=true" class="ui-button ui-button-text-icon-primary ui-widget ui-state-default ui-corner-all">
							<span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
							<span class="ui-button-text">Back To Search Results</span>
</A>		
<A href="usageHomeSearch?actionSelected=RETRIEVE_WORK_PERFORMANCES_LIST&amp;selectedProgramPerformanceId=<c:out value='${workPerformance.performanceHeaderId }'/>"
							 class="ui-button ui-button-text-icon-primary ui-widget ui-state-default ui-corner-all">
							<span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
							<span class="ui-button-text"><%=UIWidgetConstants.LABEL_VIEW_WORK_PERFORMANCES %></span>
</A>							
</td>

<td align="right">	
<c:if test ="${workPerformance.onlineHeaderEditable eq 'Y' }">
			<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WP_200_CREATE_WRK_PERF %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_PERFORMANCE_ADD %>" id="<%=UIWidgetConstants.ID_PERFORMANCE_ADD%>" title="Add">onclick="javascript:changeAction('CREATE_WORK_PERFORMANCE');"</prep:uiWidget>
		</c:if>
		<c:if test="${workPerformance.onlineHeaderEditable ne 'Y' }">
			<prep:uiWidget disabled="true" name="<%= ProtectedResourcesConstants.APM_WP_200_CREATE_WRK_PERF %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_PERFORMANCE_ADD %>" id="<%=UIWidgetConstants.ID_PERFORMANCE_ADD%>" title="Add">onclick="javascript:changeAction('CREATE_WORK_PERFORMANCE');"</prep:uiWidget>
		</c:if>
		
</td>					

</c:if>
</tr>	

</table>

</form:form>
</fieldset>
</body>
</html>