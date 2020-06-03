<!DOCTYPE HTML>
<!--
/**
 * Description: Program Performance Detail Screen
 * Maps to [US-412].
 * @Author Manoj Puli
 * @version $Revision: 1.0 $ $Date: 03/18/2004 $
 */
-->
<%-- All taglib directives Start Here --%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>
<%@ page language="java" contentType="text/html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ascap.apm.common.utils.cache.lookup.LookupTableCache"%>
<%@ page import="com.ascap.apm.common.utils.cache.lookup.LookupTables"%>
<%@ page import="com.ascap.apm.controller.utils.*"%>
<%@ page import="com.ascap.apm.vob.usage.*"%>
<%@ page import="com.ascap.apm.common.utils.constants.PerformanceMusicUserUIGroups"%>
<%@ page import="com.ascap.apm.common.utils.constants.UsageConstants"%>
<%@ page import="com.ascap.apm.common.utils.constants.LicenseTypeConstants"%>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.Constants" %>


<% pageContext.setAttribute("SurveyTypesList", HtmlSelectOption.getLookUpTable("SurveyTypes"), PageContext.PAGE_SCOPE); %>
<% pageContext.setAttribute("SampleTypesList", HtmlSelectOption.getLookUpTable("SampleTypes"), PageContext.PAGE_SCOPE); %>
<% pageContext.setAttribute("ProgramOverlapCodesList", HtmlSelectOption.getLookUpTable("ProgramOverlapCodes"), PageContext.PAGE_SCOPE); %>
<% pageContext.setAttribute("UsageSuppliersList", HtmlSelectOption.getLookUpTable("DistributionSuppliers"), PageContext.PAGE_SCOPE); %>
<% pageContext.setAttribute("distirbutionYearQuarter", HtmlSelectOption.getOptionsValues(Utils.getNextSurveyYearQuarters(12)), PageContext.PAGE_SCOPE); %>
<% pageContext.setAttribute("ApmUsersList", HtmlSelectOption.getLookUpTable("ApmUsers"), PageContext.PAGE_SCOPE); %>



<% String defaultTgtSurvYear  = ""; 
	 defaultTgtSurvYear = Utils.getSingleOutputValue(Constants.SINGLE_OUTPUT_VALUE_TYPE_TARGET_DIS); 
	if(defaultTgtSurvYear == null ) defaultTgtSurvYear = "";
 %>

<html>
<head>
<%@ include file = "/views/common/uiWidgets.jsp"%>
<title>APM</title>
<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css">
<style>
.emptytr {
	height: 10px;
}



form1111    {
background: -webkit-gradient(linear, bottom, left 175px, from(#CCCCCC), to(#EEEEEE));
background: -moz-linear-gradient(bottom, #CCCCCC, #EEEEEE 175px);
margin:auto;
position:relative;
width:1200px;
/*height:450px;*/
font-family: Tahoma, Geneva, sans-serif;
font-size: 14px;
font-style: italic;
line-height: 24px;
font-weight: bold;
color: #09C;
text-decoration: none;
-webkit-border-radius: 10px;
-moz-border-radius: 10px;
border-radius: 10px;
padding:10px;
border: 1px solid #999;
border: inset 1px solid #333;
-webkit-box-shadow: 0px 0px 8px rgba(0, 0, 0, 0.3);
-moz-box-shadow: 0px 0px 8px rgba(0, 0, 0, 0.3);
box-shadow: 0px 0px 8px rgba(0, 0, 0, 0.3);
}


</style>
<SCRIPT type="text/javascript">
function changeAction(operation) {
	document.programPerformanceForm.actionSelected.value = operation;
	document.programPerformanceForm.submit();
}

function openPartySearch(val1,partySearchType,mode,musicUserSearch,allowSearchTypeSwitch){
var _partypopup = window.open('<%=request.getContextPath()%>/usage/searchParty.do?navigationType=NEW_SEARCH&actionType=context&partySearchType=' + partySearchType + '&musicUserSearch=' + musicUserSearch + '&allowSearchTypeSwitch=' + allowSearchTypeSwitch+ '&mode='+mode +'&rowNum=' + val1,'_partypopup', 'width=750,height=500,toolbar=0,status=1,scrollbars=1,location=0,resizable=0');
}


function openMusicUserSearch() {
var effDate = $('#datepicker_performanceStartDate').val();
window.open('<%=request.getContextPath()%>/usage/musicUserSearch?filterEffectiveDate='+effDate,'', 'width=800,height=500,toolbar=0,status=1,scrollbars=1,location=0,resizable=0');
}

function cancelAction(operation) {
	document.forms[0].reset();
	changeAction(operation);
}

</script>

</head>
<body style="width:1200px;"> 

<header>

<div class="menubg"  style="width: 100%; border: 0px; height:90px;"> 
<jsp:include page="/views/menu.jsp"/>


</div>
<div></div>
</header>

<fieldset class="fieldsetfull"><legend class="legendfull">Performance Header Details [PH-200]</legend>
<table class="titletable" style="display:none;">
	<tr>
		<td></td>
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
				<span class="txtGreen"><c:if test="${not empty systemmessage}"><li><c:out value="${systemmessage}" /></li></c:if></span>
				<span class="txtRed"><c:if test="${not empty systemerror}"><li><c:out value="${systemerror}"  escapeXml="false"/></li></c:if>
				<c:forEach var="systemerror" items="${systemerrorlist}">
				<li><c:out value="${systemerror}"  escapeXml="false"/></li>
				</c:forEach>
				</span>
			</td>
		</tr>
</table>

<form:form action="programPerformanceMultiAction"  name ="programPerformanceForm" modelAttribute="programPerformance">
		
		<form:hidden path="actionSelected"/>
		<form:hidden path="musicUserInformationMode"/>
		<form:hidden path="operationMode"/>
		<form:hidden path="musicUserTypeCode"/>
		<form:hidden path="licenseTypeCode"/>
		<form:hidden path="performanceHeaderId"/>
		<form:hidden path="versionNumber"/>
		<form:hidden path="sourceSystem"/>
		<form:hidden path="isCurrentVersion" />
		<form:hidden path="isUsedByDistribution" />
		<form:hidden path="legacyProgramPerformanceId" />
		<form:hidden path="legacyMusicUserId" />
		<form:hidden path="legacyMusicUserIdTypeCode" />
		<form:hidden path="proxyRequestId" />
		<form:hidden path="maximumNumberPlays" />
		<form:hidden path="performanceHeaderDisId" />
		<form:hidden path="pointsCollected" />
		<form:hidden path="totalNumberOfWorkPerformances" />
		<form:hidden path="programPerformanceActionCode" />
		<form:hidden path="musicUserCallLetterSuffix" />
		<form:hidden path="musicUserCallLetterOnly" />
		<form:hidden path="musicUserCallLetterSuffixOnly" />
		<form:hidden path="musicUserLastName" />
		<form:hidden path="musicUserFirstName" />
		<form:hidden path="musicUserFullName" />
		<form:hidden path="fileId" />
		<form:hidden path="musicUserTypeDescription" />
		<form:hidden path="licenseTypeDescription" />
<%
com.ascap.apm.vob.usage.ProgramPerformance xProgramPerformanceForm;
String debugShyam="SHYAM-";
boolean isUsedByDistribution=false;
try{
	debugShyam=debugShyam+"1";
xProgramPerformanceForm = (com.ascap.apm.vob.usage.ProgramPerformance) request.getAttribute("programPerformance");
debugShyam=debugShyam+"2";
if(xProgramPerformanceForm!=null){
	debugShyam=debugShyam+"3";
	if(com.ascap.apm.common.utils.UsageCommonValidations.areBothEqualIgnoreCase("Y",xProgramPerformanceForm.getIsUsedByDistribution())){
		debugShyam=debugShyam+"4";
		isUsedByDistribution=true;
	}else{
		debugShyam=debugShyam+"5";
	}
}else{
	debugShyam=debugShyam+"6";	
}
}catch(ClassCastException cce){
	//Nothing to do
	debugShyam=debugShyam+"7";
}
%>


<table class="detailstable">	
	 <c:if test="${not empty programPerformance}"> 
	<c:if test="${programPerformance.musicUserInformationMode eq 'PERFORMANCE_MUSIC_USER_INFO_EDIT_MODE'}">
				<tr class="txtBlk_bold">
				<td>Music User ID : <br>
					<form:input path="musicUserId" id="musicUserId" size="10" maxlength="9" readonly="true"/><a href="javascript:openMusicUserSearch();" class="<%=UIWidgetConstants.STYLE_BINOCULAR_ICON_ENABLED%>" title="Search Music User"></a></td>
				<td>Call Letter :<br>
					<font class="txtBlk">
					<c:out value="${programPerformance.musicUserCallLetterSuffix}" /></font>
				</td>
				<td>Music User Type : <br>
					<c:if test="${not empty programPerformance.musicUserTypeCode}">
						<font class="txtBlk"><c:out value="${programPerformance.musicUserTypeCode}" /> - </font>
						<font class="txtBlk"><c:out value="${programPerformance.musicUserTypeDescription}" /></font>
						</c:if>
					<c:if test="${empty programPerformance.musicUserTypeCode}">
						<font color="#FF0000">Cannot Determine Music User Type</font>
					</c:if>
				</td>
				<td>License Type : <br>
					<c:if test="${not empty programPerformance.licenseTypeCode}">
						<font class="txtBlk"><c:out value="${programPerformance.licenseTypeCode}" /> - </font>
						<font class="txtBlk"><c:out value="${programPerformance.licenseTypeDescription}" /></font>
					</c:if>
					<c:if test="${empty programPerformance.licenseTypeCode}">
						<font color="#FF0000">Cannot Determine License Type</font>
						</c:if>
				</td>				
				<td>Supplier Call Letter : <br><font class="txtBlk"><c:out value="${programPerformance.legacyMusicUserId}" /></font>&nbsp;</td>
			</tr>
			<tr class="emptytr"></tr>
			<tr align="left" class="txtBlk_bold">
				<td>Start Date<br>
				<form:input  path="performanceStartDate" size="10" maxlength="10"  readonly="true" id="datepicker_performanceStartDate"  />
				</td>
				<td>Start Time<br>
					<form:input  path="performanceStartTime" size="8" maxlength="8" />
				</td>
				<td>End Date<br>
					<form:input  path="performanceEndDate" size="10" maxlength="10" readonly="true" id="datepicker_performanceEndDate" />
				</td>
				<td>End Time<br>
					<form:input  path="performanceEndTime" size="8" maxlength="8" />
				</td>
				<td>Duration<br><form:input  path="duration" size="10" maxlength="9"  readonly="true"  /><br>
				</td>
			</tr>
			<tr class="emptytr"></tr>
			<tr>
				<td colspan="5" align="right">						
						<prep:uiWidget type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_CANCEL %>" id="<%=UIWidgetConstants.ID_CANCEL%>" title="Cancel" > onclick="javascript:cancelAction('UPDATE_MUSIC_USER_INFORMATION');" </prep:uiWidget>
						<prep:uiWidget type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_CONTINUE %>" id="<%=UIWidgetConstants.ID_CONTINUE%>" title="Continue" > onclick="javascript:changeAction('UPDATE_MUSIC_USER_INFORMATION');" </prep:uiWidget>
				</td>
			</tr>
	</c:if>
	<c:if test="${empty programPerformance.musicUserInformationMode}" >
			<tr  class="txtBlk_bold">
				<td>Music User ID : <br>
					<form:input path="musicUserId"  size="10" maxlength="9"/><a href="javascript:openMusicUserSearch();" class="<%=UIWidgetConstants.STYLE_BINOCULAR_ICON_ENABLED%>" title="Search Music User"></a>
				</td>
				<td>Call Letter :<br>
					<font class="txtBlk">
					<c:out value="${programPerformance.musicUserCallLetterSuffix}"/>
					</font>
				</td>
				<td>Music User Type : <br>
					<c:if test="${not empty programPerformance.musicUserTypeCode}">
						<font class="txtBlk"><c:out value="${programPerformance.musicUserTypeCode}"/> - </font>
						<font class="txtBlk">${programPerformance.musicUserTypeDescription}<c:out value=""/></font>
					</c:if>
					<c:if test="${empty programPerformance.musicUserTypeCode}">
						<c:if test="${programPerformance.ifFirstTimeClick eq 'N'}">
							<font color="#FF0000">Cannot Determine Music User Type</font>
						</c:if>
					</c:if>
				</td>
				<td>License Type : <br>
					<c:if test="${ not empty programPerformance.licenseTypeCode}">
						<font class="txtBlk"><c:out value="${programPerformance.licenseTypeCode}"/> - </font>
						<font class="txtBlk"><c:out value="${programPerformance.licenseTypeDescription}"/></font>
					</c:if>
					<c:if test="${empty programPerformance.licenseTypeCode}">
						<c:if test="${programPerformance.ifFirstTimeClick eq 'N'}">
							<font color="#FF0000">Cannot Determine License Type</font>
						</c:if>
					</c:if>
				</td>
				<td>Supplier Call Letter : <br><font class="txtBlk"><c:out value="${programPerformance.legacyMusicUserId}"/></font>&nbsp;</td>
			</tr>
			<tr class="emptytr"></tr>
			<tr align="left" class="txtBlk_bold">
				<td>Start Date <br>
					<form:input path="performanceStartDate" size="10" maxlength="10" readonly="true" id="datepicker_performanceStartDate" />
				</td>
				<td>Start Time<br>
					<form:input path="performanceStartTime" size="8" maxlength="8" /></td>
				<td>End Date<br>
					<form:input path="performanceEndDate" size="10" maxlength="10" readonly="true" id="datepicker_performanceEndDate" />
				</td>
				<td>End Time<br>
					<form:input path="performanceEndTime" size="8" maxlength="8" />
				</td>
				<td>Duration<br><form:input path="duration" size="10" maxlength="9"  readonly="true" />
				</td>
			</tr>
			<tr class="emptytr"></tr>
			<tr><td  colspan="5" align="right">
				<prep:uiWidget type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_CONTINUE %>" id="<%=UIWidgetConstants.ID_CONTINUE%>" title="Continue">onclick="javascript:changeAction('UPDATE_MUSIC_USER_INFORMATION');"</prep:uiWidget>
				</td>
			</tr>	
	</c:if>
	<c:if test="${programPerformance.musicUserInformationMode eq 'PERFORMANCE_MUSIC_USER_INFO_FIXED_MODE'}">
			<tr align="left" class="txtBlk_bold">
				<td>Music User ID :<br>
					<form:hidden path="musicUserId"/>
					<font class="txtBlk"><c:out value="${programPerformance.musicUserId}"/></font>
				</td>
				<td>Call Letter :<br>
					<font class="txtBlk">
					<c:out value="${programPerformance.musicUserCallLetterSuffix}"/>
					</font>
				</td>
				<td>Music User Type : <br>
					<c:if test="${not empty programPerformance.musicUserTypeCode}">
						<font class="txtBlk"><c:out value="${programPerformance.musicUserTypeCode}"/> - </font>
						<font class="txtBlk"><c:out value="${programPerformance.musicUserTypeDescription}"/></font>
					</c:if>
					<c:if test="${empty programPerformance.musicUserTypeCode}">
						<font color="#FF0000">Cannot Determine Music User Type</font>
					</c:if>
				</td>
				<td>License Type : <br>
					<c:if test="${not empty programPerformance.licenseTypeCode}">
						<font class="txtBlk"><c:out value="${programPerformance.licenseTypeCode}"/> - </font>
						<font class="txtBlk"><c:out value="${programPerformance.licenseTypeDescription}"/></font>
					</c:if>
					<c:if test="${empty programPerformance.licenseTypeCode}">
						<font color="#FF0000">Cannot Determine License Type</font>
					</c:if>
				</td>
				<td>Supplier Call Letter : <br><font class="txtBlk"> <c:out value="${programPerformance.legacyMusicUserId}"/></font>&nbsp;</td>
			</tr>
			<tr class="emptytr"></tr>
			<tr align="left" class="txtBlk_bold">
				<td>Start Date<br>
					<form:hidden path="performanceStartDate"/>
					<font class="txtBlk"><c:out value="${programPerformance.performanceStartDate}" /></font>
				</td>
				<td>Start Time<br>
					<form:hidden  path="performanceStartTime" />
					<font class="txtBlk"><c:out value="${programPerformance.performanceStartTime}" /></font>
				</td>
				<td>End Date<br>
					<form:hidden  path="performanceEndDate" />
					<font class="txtBlk"><c:out value="${programPerformance.performanceEndDate}" /></font>
				</td>
				<td >End Time<br>
					<form:hidden  path="performanceEndTime" />
					<font class="txtBlk"><c:out value="${programPerformance.performanceEndTime}" /></font>
				</td>
				<td>Duration<br>
					<form:hidden  path="duration" />
					<font class="txtBlk"><c:out value="${programPerformance.duration}" /></font>
			</td>
			</tr>
			
			<tr class="emptytr"></tr>
	
			<tr>
				<td colspan="5" align="right">						
					<c:if test="${programPerformance.onlineEditable eq 'Y'}">
						<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_PH_200_UPDATE_PERF_HDR %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_EDIT %>" id="<%=UIWidgetConstants.ID_EDIT%>" title="Edit Music User Information">onclick="javascript:changeAction('EDIT_MUSIC_USER_INFORMATION');"</prep:uiWidget>
					</c:if>						
					<c:if test="${programPerformance.onlineEditable eq 'N'}">
						<prep:uiWidget disabled="true" name="<%= ProtectedResourcesConstants.APM_PH_200_UPDATE_PERF_HDR %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_EDIT %>" id="<%=UIWidgetConstants.ID_EDIT%>">onclick="javascript:changeAction('EDIT_MUSIC_USER_INFORMATION');"</prep:uiWidget>
					</c:if>
				</td>
			</tr>	
	</c:if>
	</c:if>
	</table>
	<c:if test="${not empty programPerformance}" >
	<c:if test="${programPerformance.musicUserInformationMode eq 'PERFORMANCE_MUSIC_USER_INFO_FIXED_MODE'}">
	<%
	String musicUserTypeDetermined="";
	String musicUserLicenseDetermined="";
	String sourceSystemDetermined="";

	//com.ascap.apm.web.forms.usage.ProgramPerformanceForm xProgramPerformanceForm;
	//xProgramPerformanceForm = (com.ascap.apm.web.forms.usage.ProgramPerformanceForm) pageContext.getAttribute("programPerformanceForm");
	xProgramPerformanceForm = (com.ascap.apm.vob.usage.ProgramPerformance) request.getAttribute("programPerformance");
	musicUserTypeDetermined = xProgramPerformanceForm.getMusicUserTypeCode();
	musicUserLicenseDetermined = xProgramPerformanceForm.getLicenseTypeCode();
	sourceSystemDetermined = xProgramPerformanceForm.getSourceSystem();
	
	if(com.ascap.apm.common.utils.UsageCommonValidations.areBothEqualIgnoreCase("Y",xProgramPerformanceForm.getIsUsedByDistribution())){
		isUsedByDistribution=true;
	}
	

	if(sourceSystemDetermined == null){
		sourceSystemDetermined ="";
	}
	
	//Being Restrictive in terms of what is editable
	boolean isOnlineEditable=true;

	String lookupValueOnlineEditableFlag=null;
	
	
	%>
<table class="detailstable">
	
			<tr>
				<td colspan="5" class="textlabelmediumblue" >General Performance Details</td>
				<%
					if(
						PerformanceMusicUserUIGroups.isMusicUserTypeMemberOftheUIGroup(PerformanceMusicUserUIGroups.INTERNET_PERF_UI_GROUP,musicUserTypeDetermined) == true 
					|| PerformanceMusicUserUIGroups.isMusicUserTypeMemberOftheUIGroup(PerformanceMusicUserUIGroups.INTERNET_AUDIO_VISUAL_PERF_UI_GROUP,musicUserTypeDetermined) == true ){
						//Nothing for these guys totalNumberOfPlays is Editable	
					}else{
				%>
					
				<%	
					}
				%>				
				
			</tr>
			<tr align="left" class="txtBlk_bold">
				<td width="20%">APM Perf Header ID :<br>
						<%-- <form:hidden path="performanceHeaderId"/> --%>
					<font class="txtBlk"><c:out value="${programPerformance.performanceHeaderId}"/></font></td>
				<td width="20%">Status :<br>
					<form:hidden path="processStatus"/>
					<font class="txtBlk"><c:out value="${programPerformance.processStatus}"/></font></td>
				<td width="20%">Total Plays:<br>
					<font class="txtBlk"><c:out value="${programPerformance.totalNumberPlays}"/></font>
					<form:hidden path="totalNumberPlays"/>
				</td>
				<td width="20%">Work Perf Count : <br><font class="txtBlk"><c:out value="${programPerformance.totalNumberOfWorkPerformances}" /></font></td>	
				<td width="20%">File ID : <br><font class="txtBlk"><c:out value="${programPerformance.fileId}" /></font></td>	
			</tr>
			
			<tr class="emptytr"></tr>
			
			<tr class="txtBlk_bold">
				<td>Supplier<br>
				<c:if test="${programPerformance.operationMode ne 'RETRIEVE_MODE'}">				
					<form:select path="supplierCode">
						<form:option value="" />
						<form:options items="${UsageSuppliersList}" itemLabel="displayName" itemValue="id" />
					</form:select>
				</c:if>					
				<c:if test="${programPerformance.operationMode eq 'RETRIEVE_MODE'}">
					<form:select  path="supplierCode" disabled="true">
						<form:option value="" />
						<form:options items="${UsageSuppliersList}" itemLabel="displayName" itemValue="id" />
					</form:select>
					<form:hidden  path="supplierCode"/>
				</c:if>	
				</td>
				
				<td >Survey Type<br>
					<form:select path="surveyTypeCode" size="1">
						<form:option value="" />
						<form:options items="${SurveyTypesList}" itemLabel="displayName" itemValue="id" />
					</form:select>
				</td>
			  <c:if test="${programPerformance.operationMode eq 'RETRIEVE_MODE'}">
				<td>Sample Type<br>
					<form:select path="sampleTypeCode" size="1" disabled="true">
						<form:option value="" />
						<form:options items="${SampleTypesList}" itemLabel="displayName" itemValue="id" />
					</form:select>
				</td>
				</c:if>
				<td>Overlap Code<br>
					<form:select path="programOverlapCode" style="width:200px;">
						<form:option value="" />
						<form:options items="${ProgramOverlapCodesList}" itemLabel="displayName" itemValue="id" />
					</form:select>
				</td>
				
				
				
				
				<td>		
					<c:if test="${programPerformance.channelInd eq 'Y'}">	
					Classical Ind<br>
					<form:select path="classicalIndicator" style="width:60px;">
						<form:option value="N">No</form:option>
						<form:option value="Y">Yes</form:option>
					</form:select> 
	    			</c:if>	
	    			  
	    			 <c:if test="${programPerformance.channelInd ne 'Y'}">	
	    			 <form:hidden path="classicalIndicator"/> 
	    			 </c:if>
				</td>
			</tr>
			<tr class="emptytr"></tr>
			
			<% if(com.ascap.apm.common.utils.UsageCommonValidations.isEmptyOrNull(musicUserTypeDetermined)){ %>
			<tr class="txtBlk_bold">
				<td>Performer<br>
				<form:input path="performerName" size="25" maxlength="100" /></td>
				<td>Play List Flag<br>
				<nobr><form:radiobutton path="playListFlag" value='<%=com.ascap.apm.common.utils.constants.UsageConstants.PLAY_LIST_FLAG_AVERAGE%>' />Average
				<form:radiobutton path="playListFlag" value='<%=com.ascap.apm.common.utils.constants.UsageConstants.PLAY_LIST_FLAG_SPECIAL%>' />Special</nobr></td>
				<td>
				Headline Act<br>
				<form:select path="headlineActIndicator">
					<form:option value=""></form:option>
					<form:option value="Y">Yes</form:option>
					<form:option value="N">No</form:option>
				</form:select>
				</td>
				<td># of Concerts<br>
				<form:input path="numberOfConcerts" size="10" maxlength="10" /></td>
				<td>Artist Revenue<br>
				<form:input path="artistRevenue" size="13" maxlength="13" /></td>
				</tr>
			<%}%>

 	 		<%pageContext.setAttribute("ex1",PerformanceMusicUserUIGroups.getStringMusicUserTypeMemberOftheUIGroup(PerformanceMusicUserUIGroups.LIVE_POP_PERF_UI_GROUP,musicUserTypeDetermined),PageContext.PAGE_SCOPE); %>
			
			<c:if test="${programPerformance.constantAllwaysYES eq ex1}"><tr class="txtBlk_bold">
				<td>Performer<br>
				<form:input path="performerName" size="25" maxlength="100" /></td>
				<td>Play List Flag<br>
				<nobr><form:radiobutton path="playListFlag" value='<%=com.ascap.apm.common.utils.constants.UsageConstants.PLAY_LIST_FLAG_AVERAGE%>' />Average
				<form:radiobutton path="playListFlag" value='<%=com.ascap.apm.common.utils.constants.UsageConstants.PLAY_LIST_FLAG_SPECIAL%>' />Special</nobr></td>
				<td>Headline Act<br>
				<form:select  path="headlineActIndicator">
					<form:option value=""></form:option>
					<form:option value="Y">Yes</form:option>
					<form:option value="N">No</form:option>
				</form:select>
				</td>
				<td># of Concerts<br>
				<form:input path="numberOfConcerts" size="10" maxlength="10" />
				</td>
				<td>Artist Revenue<br>
				<form:input path="artistRevenue" size="13" maxlength="13" /></td>
			</tr>
			</c:if>
			
			
			<tr class="emptytr"></tr>
			
			<tr class="txtBlk_bold">
				<td>Segment Number<br>
					<form:input path="segmentNumber" size="10" maxlength="10" readonly="true"/>
				</td>
				<td>Program Number<br>
					<form:input path="programNumber" size="15" maxlength="15" readonly="true" />
				</td>
				<td>Program Title<br>
					<form:input path="programTitle" size="30" maxlength="50" />
				</td>
				<td>Target Dist<br>		
					<c:if test="${programPerformance.operationMode eq 'RETRIEVE_MODE'}">	
	    			<form:input path="targetYearQuarter" id="yearquarterpicker_targetYearQuarter" size="10" readonly="true"/><a href="javascript:clearField('yearquarterpicker_targetYearQuarter');" class="<%=UIWidgetConstants.STYLE_CLEAR_FIELD_ICON_ENABLED%>" title="<%=UIWidgetConstants.TITLE_CLEAR_FIELD%>"></a>
	    			</c:if>	
					<c:if test="${programPerformance.operationMode ne 'RETRIEVE_MODE'}">	
	    			<form:input path="targetYearQuarter" id="yearquarterpicker_targetYearQuarter" size="10" readonly="true" value="<%=defaultTgtSurvYear%>"/><a href="javascript:clearField('yearquarterpicker_targetYearQuarter');" class="<%=UIWidgetConstants.STYLE_CLEAR_FIELD_ICON_ENABLED%>" title="<%=UIWidgetConstants.TITLE_CLEAR_FIELD%>"></a>
	    			</c:if>
				</td>
				<td>Assigned User<br>
				<form:select  path="assignedToUser">
						<form:option value="" />
						<form:options items="${ApmUsersList}" itemLabel="displayName" itemValue="id" />
					</form:select>
				</td>
			</tr>				
</table>	
	
	<table class="detailstableinner">
		<tr>
			<td colspan="4" >
				<table class="contenttable" >
					<thead>
					<tr>
						<td colspan="3" align="left"  class="textlabelmediumblue">Errors</td>
					</tr>
					</thead>
					<tbody>		
					<c:if test="${not empty programPerformance.errorsAndWarnings}">
					<c:forEach var="performanceMessage" items="${programPerformance.errorsAndWarnings}">
					<c:if test="${performanceMessage.messageCategoryCode eq 'E'}">							
							<input type="hidden" name="errorTypes" value='<c:out value="${performanceMessage.messageCategoryCode}"/>'/>
							<input type="hidden" name="errorCodes" value='<c:out value="${performanceMessage.messageCode}"/>'/>
							<input type="hidden" name="errorMessages" value='<c:out value="${performanceMessage.messageDescription}"/>'/>
				<tr class="tableroweven">
						<td bgcolor="#ff0000" width="1%">&nbsp;</td>
						<td width="10%"><font class="txtBlk"><c:out value="${performanceMessage.messageCode}"/></font></td>
						<td width="90%"><font class="txtBlk"><c:out value="${performanceMessage.messageDescription}"/></font></td>
					</tr>
					</c:if>
					</c:forEach>
					</c:if>
					</tbody>
				</table>
		</td>
	</tr>
	<tr class="txtBlk"><td>&nbsp;</td></tr>
	<tr>
		<td colspan="4">
			<table class="contenttable">
				<thead>
				<tr>
					<td colspan="3" align="left"  class="textlabelmediumblue" >Warnings</td>
				</tr>
				</thead>
				<c:if test="${not empty programPerformance.errorsAndWarnings}">
				<c:forEach var="performanceMessage" items="${programPerformance.errorsAndWarnings}">
				<c:if test="${performanceMessage.messageCategoryCode eq 'W'}">							
							<input type="hidden" name="errorTypes" value='<c:out value="${performanceMessage.messageCategoryCode}"/>'/>
							<input type="hidden" name="errorCodes" value='<c:out value="${performanceMessage.messageCode}"/>'/>
							<input type="hidden" name="errorMessages" value='<c:out value="${performanceMessage.messageDescription}"/>'/>
				<tr class="tableroweven">
					<td bgcolor="#FFA500" width="1%">&nbsp;</td>
					<td width="10%"><font class="txtBlk"><c:out value="${performanceMessage.messageCode}"/></font></td>
					<td width="90%"><font class="txtBlk"><c:out value="${performanceMessage.messageDescription}"/></font></td>
				</tr>
				</c:if>
				</c:forEach>
				</c:if>
				</tbody>
			</table>
		</td>
	</tr>
	<tr class="txtBlk"><td>&nbsp;</td></tr>
		<tr>
			<td colspan="4">&nbsp;</td>
		</tr>
	</table>	
	<table class="buttonstable">
			<tr>
				<c:if test="${not empty programPerformance}">
				<c:if test="${programPerformance.operationMode eq 'RETRIEVE_MODE'}" >
					<td align="left" >						
						<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_PS_100_SEARCH %>" type="<%= SecurityConstants.ANCHOR_TYPE %>"  label="<%=UIWidgetConstants.LABEL_VIEW_WORK_PERFORMANCES %>" id="<%=UIWidgetConstants.ID_VIEW_WORK_PERFORMANCES%>" title="View Work Performances">
							onclick="javascript:changeAction('RETRIEVE_WORK_PERFORMANCES_LIST');" </prep:uiWidget> 
							<span style="vertical-align:bottom;">
							<a href="usageHomeSearch?backToSearchResults=true"
							 class="ui-button ui-button-text-icon-primary ui-widget ui-state-default ui-corner-all">
							<span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
							<span class="ui-button-text">Back To Search Results</span>
							</a> 
							
							</span>
					</td>

					

						
							<td align="right">
								<c:if test="${programPerformance.onlineEditable eq 'Y'}">
									<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_PH_200_UPDATE_PERF_HDR %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_UPDATE %>" id="<%=UIWidgetConstants.ID_UPDATE%>" title="Update" >onclick="javascript:changeAction('UPDATE_PROGRAM_PERFORMANCE');" </prep:uiWidget>
								</c:if>
								<c:if test="${programPerformance.onlineEditable eq 'N'}">
									<prep:uiWidget disabled="true" name="<%= ProtectedResourcesConstants.APM_PH_200_UPDATE_PERF_HDR %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_UPDATE %>" id="<%=UIWidgetConstants.ID_UPDATE%>"> onclick="javascript:changeAction('UPDATE_PROGRAM_PERFORMANCE');" </prep:uiWidget>
								</c:if>								
							</td>
						</c:if>
						<c:if test="${programPerformance.operationMode ne 'RETRIEVE_MODE'}">
							<td align="right">
								<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_PH_200_CREATE_PERF_HDR %>" type="<%= SecurityConstants.ANCHOR_TYPE %>"  label="<%=UIWidgetConstants.LABEL_PERFORMANCE_ADD %>" id="<%=UIWidgetConstants.ID_PERFORMANCE_ADD%>" title="Add" > onclick="javascript:changeAction('CREATE_PROGRAM_PERFORMANCE');" </prep:uiWidget>
							</td>
						</c:if>
					</c:if>
					
			</tr>
		</table>
</c:if>
	</c:if>
<form:hidden path="onlineEditable"/>
<form:hidden path="isInvalidMusicUser"/>
</form:form>

</fieldset>
</body>
</html>