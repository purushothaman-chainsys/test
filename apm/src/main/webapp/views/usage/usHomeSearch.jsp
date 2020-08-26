<!DOCTYPE html>
<%--
/**
 * Description: Main APM Search Screen
 * Maps to [US-100].
 * @Author Manoj_Puli
 * @version $Revision$ $Date$
 */
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>
<%@ page language="java" contentType="text/html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ascap.apm.common.utils.cache.lookup.LookupTableCache"%>
<%@ page import="com.ascap.apm.common.utils.cache.lookup.LookupTables"%>
<%@ page import="com.ascap.apm.controller.utils.*"%>
<%@ page import="com.ascap.apm.vob.usage.*"%>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>
<html>
<head>
<%@ include file = "/views/common/uiWidgets.jsp"%>
<title>APM</title>
<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css">
<style>
.grid {
	width:100%;
	-moz-box-sizing: border-box;
	-webkit-box-sizing: border-box;
	box-sizing: border-box;
}
[class*='col-'] {
  float: left;
}
[class*='col-'] input {
	width: 120px;
	margin-bottom: 6px;
} 
[class*='col-'] input.small {
	width: 80px;
	margin-bottom: 6px;
} 
[class*='col-'] select {
	margin-bottom: 6px;
	font-size: 12px;
} 
[class*='col-'] label {
	display: block;
	text-align: left;
	font-size: 11px;
	font-family: Arial;
	font-weight: bold;
}
.col-1-4 {
  width: 29%;
  padding-left: 0px;
}
.col-2-4 {
  width: 20%;
}
.col-3-4 {
  width: 20%;
}
.col-4-4 {
  width: 30%;
}

/************************ IE Fixes ********************************/
.col-1-4 label{
	display: block;
	text-align: left;
	font-size: 11px;
	font-family: Arial;
	font-weight: bold;
}
.col-2-4 label{
	display: block;
	text-align: left;
	font-size: 11px;
	font-family: Arial;
	font-weight: bold;
}
.col-3-4 label{
	display: block;
	text-align: left;
	font-size: 11px;
	font-family: Arial;
	font-weight: bold;
}
.col-4-4 label{
	display: block;
	text-align: left;
	font-size: 11px;
	font-family: Arial;
	font-weight: bold;
}

.col-1-4 input{
	width: 120px;
	font-size: 12px;
	font-family: Arial;
	margin-bottom: 6px;

}
.col-2-4 input{
	width: 120px;
	font-size: 12px;
	font-family: Arial;
	margin-bottom: 6px;

}
.col-3-4 input{
	width: 120px;
	font-size: 12px;
	font-family: Arial;
	margin-bottom: 6px;

}
.col-4-4 input{
	width: 120px;
	font-size: 12px;
	font-family: Arial;
	margin-bottom: 6px;

}

.col-1-4 select {
	margin-bottom: 6px;
	font-size: 12px;
	font-family: Arial;
}
.col-2-4 select {
	margin-bottom: 6px;
	font-size: 12px;
	font-family: Arial;
}
.col-3-4 select {
	margin-bottom: 6px;
	font-size: 12px;
	font-family: Arial;
}
.col-4-4 select {
	margin-bottom: 6px;
	font-size: 12px;
	font-family: Arial;
}


.col-1-4 input.small{
	width: 80px;
	margin-bottom: 6px;
}
.col-2-4 input.small{
	width: 80px;
	margin-bottom: 6px;
}
.col-3-4 input.small{
	width: 80px;
	margin-bottom: 6px;
}
.col-4-4 input.small{
	width: 80px;
	margin-bottom: 6px;
}

/***********************  IE Fixes End ***********************************/

fieldset {}
fieldset.fieldsetfull {
-moz-box-sizing: border-box;
-webkit-box-sizing: border-box;
box-sizing: border-box;
width: 1200px;
margin-bottom: 12px;
border-color: #999;
border-radius: 8px;
-moz-border-radius: 8px;
-webkit-border-radius: 8px;
-webkit-box-shadow: #BBB 5px 5px 5px;
-moz-box-shadow: #bbb 5px 5px 5px;
box-shadow: #BBB 5px 5px 5px;
text-align:left;
margin:0 auto;
}

br {
	clear: left;
}



input[type=text], textarea {
  @include transition(all 0.30s ease-in-out);
  outline: none;
  /*padding: 3px 0px 3px 3px;
  margin: 5px 1px 3px 0px;*/
  border: 1px solid #aaaaaa;
  font-size: 12px;
  line-height: 1.4em;
}
 
input[type=text]:focus, textarea:focus {
  @include box-shadow(0 0 5px rgba(81, 203, 238, 1));
  /*padding: 3px 0px 3px 3px;
  margin: 5px 1px 3px 0px;*/
  border: 1px solid rgba(81, 203, 238, 1);
}


.stiched {
   padding: 5px 10px;
   margin: 10px;
   background: #cccccc;
   color: #f00;
   font-size: 12px;
   font-weight: normal;
   line-height: 1.3em;
   border: 2px dashed #fff;
   border-top-left-radius: 3px;
   -moz-border-radius-topleft: 3px;
   -webkit-border-top-left-radius: 3px;
   border-bottom-right-radius: 3px;
   -moz-border-radius-bottomright: 3px;
   -webkit-border-bottom-right-radius: 3px;
   border-top-right-radius: 3px;
   -moz-border-radius-topright: 3px;
   -webkit-border-top-right-radius: 3px;
   -moz-box-shadow: 0 0 0 4px #cccccc, 2px 1px 4px 4px rgba(10,10,0,.5);
   -webkit-box-shadow: 0 0 0 4px #cccccc, 2px 1px 4px 4px rgba(10,10,0,.5);
   box-shadow: 0 0 0 4px #cccccc, 2px 1px 6px 4px rgba(10,10,0,.5);
   #text-shadow: -1px -1px #999999;
   font-weight: normal;
}

.placeholder
{
  color: #aaa;
}

.noplaceholder
{
  color: #000;
}


</style>
<script type="text/javascript">


$(function(){
	
		
		
		$(this).find('input[id^=datepicker_]').each(function() {            
            $(this).attr('placeholder','YYYY/MM/DD');
                if ($(this).val() == '')  {
                $(this).addClass('placeholder');
                $(this).val( $(this).attr('placeholder'));
            }
        });     
        
        
        $(this).find('input[id^=_time_]').each(function() {            
            $(this).attr('placeholder','HH24:MM:SS');
                if ($(this).val() == '')  {
                $(this).addClass('placeholder');
                $(this).val( $(this).attr('placeholder'));
            }
        });  
        
           
        $('[placeholder]').focus(function()
        {
            if ($(this).val() == $(this).attr('placeholder'))
            {
                $(this).val('');
                $(this).removeClass('placeholder');
            }
        }).blur(function()
        {       
            if ($(this).val() == '' || $(this).val() == $(this).attr('placeholder'))
            {
                $(this).val($(this).attr('placeholder'));
                $(this).addClass('placeholder');
            }
        }).change(function()
        {  
            if ($(this).val() == '' || $(this).val() == $(this).attr('placeholder'))
            {
                $(this).val($(this).attr('placeholder'));
                $(this).addClass('placeholder');
            }
            else {
             	$(this).removeClass('placeholder');
            }
        });
        $('[placeholder]').closest('form').submit(function()
        {
            $(this).find('[placeholder]').each(function()
            {
                if ($(this).val() == $(this).attr('placeholder'))
                {
                    $(this).val('');
                }
            })
        });
	
});




function openPartySearch(val1,partySearchType,mode,musicUserSearch,allowSearchTypeSwitch){
window.open('<%=request.getContextPath()%>/usage/searchParty.do?navigationType=NEW_SEARCH&actionType=context&partySearchType=' + partySearchType + '&musicUserSearch=' + musicUserSearch + '&allowSearchTypeSwitch=' + allowSearchTypeSwitch+ '&mode='+mode +'&rowNum=' + val1,'', 'width=750,height=500,toolbar=0,status=1,scrollbars=1,location=0,resizable=0');
}

function tempWork(val1){
window.open('<%=request.getContextPath()%>/usage/searchWork.do?actionType=context&mode=usageHomeSearch&onlySurvivalWorks=N&num=' + val1,'', 'width=750,height=500,toolbar=0,status=1,scrollbars=1,location=0,resizable=0');
}

function changeAction(formObject, operation) {
	formObject.actionSelected.value = operation;
	if(operation == "SEARCH_PROGRAM_PERFORMANCES"){
		formObject.performanceSearchType.value = "PROGRAM_PERFORMANCE";
	}else if(operation == "SEARCH_WORK_PERFORMANCES"){
		formObject.performanceSearchType.value = "WORK_PERFORMANCE";
	}
	if($('input[name="performanceStartDate"]').val() == 'YYYY/MM/DD') {$('input[name="performanceStartDate"]').val('');}
	if($('input[name="performanceEndDate"]').val() == 'YYYY/MM/DD') {$('input[name="performanceEndDate"]').val('');}
	if($('input[name="statusDateFrom"]').val() == 'YYYY/MM/DD') {$('input[name="statusDateFrom"]').val('');}
	if($('input[name="statusDateTo"]').val() == 'YYYY/MM/DD') {$('input[name="statusDateTo"]').val('');}
	if($('input[name="performanceStartTime"]').val() == 'HH24:MM:SS') {$('input[name="performanceStartTime"]').val('');}
	if($('input[name="performanceEndTime"]').val() == 'HH24:MM:SS') {$('input[name="performanceEndTime"]').val('');}
	
	
	formObject.submit();
}

    
    
function openMusicUserSearch() {
	var effDate = '';
	window.open('<%=request.getContextPath()%>/usage/musicUserSearch?filterEffectiveDate='+effDate,'', 'width=800,height=500,toolbar=0,status=1,scrollbars=1,location=0,resizable=0');
}   

</script>
</head>
<body> 


<div class="menubg"  style="width: 100%; border: 0px; height:90px;"> 
<jsp:include page="/views/menu.jsp"/>

</div>
<div></div>  
<p>
<div class="titletable" style="height:0;width:0;display:none"></div>
<div class="ui-widget-content" style="height:0;width:0;display:none"></div>
<%  
 pageContext.setAttribute("MusicUserTypesList", HtmlSelectOption.getLookUpTable("MusicUserTypes"), PageContext.PAGE_SCOPE); 
 pageContext.setAttribute("UseTypesList", HtmlSelectOption.getLookUpTable("UseTypes"), PageContext.PAGE_SCOPE); 
 pageContext.setAttribute("LicenseTypesList", HtmlSelectOption.getLookUpTable("LicenseTypes"), PageContext.PAGE_SCOPE); 
 pageContext.setAttribute("SurveyTypesList", HtmlSelectOption.getLookUpTable("SurveyTypes"), PageContext.PAGE_SCOPE); 
 pageContext.setAttribute("SampleTypesList", HtmlSelectOption.getLookUpTable("SampleTypes"), PageContext.PAGE_SCOPE); 
 pageContext.setAttribute("ProgramOverlapCodesList", HtmlSelectOption.getLookUpTable("ProgramOverlapCodes"), PageContext.PAGE_SCOPE);
 pageContext.setAttribute("UsageSuppliersList", HtmlSelectOption.getLookUpTable("DistributionSuppliers"), PageContext.PAGE_SCOPE); 
 pageContext.setAttribute("PerformanceErrorWarningCodesList", HtmlSelectOption.getLookUpTable("PerformanceErrorWarningCodes"), PageContext.PAGE_SCOPE); 
 pageContext.setAttribute("SearchResultsCountPoliciesList", HtmlSelectOption.getLookUpTable("SearchResultsCountPolicies"), PageContext.PAGE_SCOPE);  
 pageContext.setAttribute("distirbutionYearQuarter", HtmlSelectOption.getOptionsValues(Utils.getNextSurveyYearQuarters(12)), PageContext.PAGE_SCOPE);
 pageContext.setAttribute("MatchTypesList", HtmlSelectOption.getLookUpTable("MatchTypes"), PageContext.PAGE_SCOPE);  
 pageContext.setAttribute("ApmUsersList", HtmlSelectOption.getLookUpTable("ApmUsers"), PageContext.PAGE_SCOPE); 

%>

<form:form modelAttribute="performanceSearch" action="usageHomeSearch" method="get">
<form:hidden path="performanceSearchType"/>
<form:hidden path="actionSelected"/>
<form:hidden path="isModelling" value='<%= request.getParameter("isModelling") %>'/>
  	
<table class="errortable">	
	<tr>
		<!-- <td class="txtRed"><html:errors /></td>  -->
	</tr>
</table>
 


<div style="margin:0 auto;"  class="grid">
<fieldset class="fieldsetfull" style="position: relative;"><legend class="legendfull" style="top: -0.7em;">Performance Search [PS-100]</legend>
	<div  class="col-1-4" style="float:left;">
	     <label>APM Perf Header ID</label>
	     <form:input path="programPerformanceId" maxlength="15" size="17" /><br>	     
			<label>Music User ID</label>
	     	<form:input path="musicUserId" maxlength="9" id="musicUserId"/><a href="javascript:openMusicUserSearch();" class="<%=UIWidgetConstants.STYLE_BINOCULAR_ICON_ENABLED%>" title="Search Music User"></a>
	     	<a href="javascript:clearField('musicUserId');" class="<%=UIWidgetConstants.STYLE_CLEAR_FIELD_ICON_ENABLED%>" title="<%=UIWidgetConstants.TITLE_CLEAR_FIELD%>"></a>
	     	<br> 
	     <label>Work ID</label>
	     	<form:input path="workId" maxlength="11"  /><br> 
	     <label>File ID</label>
	     <form:input path="fileId" maxlength="10" size="12" /><br>
	     <label>Program Number</label>
	     <form:input path="programNumber" maxlength="10" size="12" /><br>
	<label>License Type</label>
						<form:select path="licenseTypeCode" style="width:120px;" multiple="false">
							<form:option value="" />
							<form:options items="${LicenseTypesList}" itemLabel="displayName" itemValue="id"/>
						</form:select><br>		 
	    			
	    <div>
	    	<div style="float:left;padding-right:20px;">
	    	<label>Target Dist From<br></label>
	    	<form:input path="targetYearQuarterFrom" id="yearquarterpicker_targetYearQuarterFrom" class="small" readonly="true"/><a href="javascript:clearField('yearquarterpicker_targetYearQuarterFrom');" class="<%=UIWidgetConstants.STYLE_CLEAR_FIELD_ICON_ENABLED%>" title="<%=UIWidgetConstants.TITLE_CLEAR_FIELD%>"></a>
			</div>
			<div style="float:left;">
	    	<label>Target Dist To<br></label>
	    	<form:input path="targetYearQuarterTo" id="yearquarterpicker_targetYearQuarterTo" class="small" readonly="true"/><a href="javascript:clearField('yearquarterpicker_targetYearQuarterTo');" class="<%=UIWidgetConstants.STYLE_CLEAR_FIELD_ICON_ENABLED%>" title="<%=UIWidgetConstants.TITLE_CLEAR_FIELD%>"></a>
			</div>
		</div><br>
		 <label>Music User Type<br></label>
						<form:select path="musicUserTypes" multiple="true" size="4" style="width:280px;">
							<option value="" />
							<form:options items="${MusicUserTypesList}" itemLabel="displayName" itemValue="id"/>
						</form:select><br>
						
			<label>Match Type</label>
			<form:select path="matchTypes"  multiple="true" size="4" style="width:120px;">
				<option value="" />
				<form:options items="${MatchTypesList}" itemLabel="displayName" itemValue="id" />
			</form:select><br>
	    
	</div>
	<div  class="col-2-4" style="float:left">
			<label>APM Work Perf ID</label>
	     	<form:input path="workPerformanceId" maxlength="15" size="17" /><br>	
		   	<label>Supplier Call Letter</label>
		   	<form:input path="musicUserCallLetter"/><br>
			<label>Work Title</label>
	     	<form:input path="workTitle" maxlength="150"/><br>
	     	<label>Title Search Type</label>
		    <form:select path="workTitleSearchType" style="width:120px;">
				<form:option value="BGNS">Begins</form:option>
				<form:option value="CNTS">Contains-Begins</form:option>
			</form:select><br>
			<label>Supplier</label>
		    <form:select path="supplierCode" size="1"  style="width:120px;">
				<form:option value="" />
				<form:options items="${UsageSuppliersList}" itemLabel="displayName" itemValue="id" />
			</form:select><br>		
	     	<label>Segment Number</label>
	     	<form:input path="segmentNumber" maxlength="10" size="12" /><br> 
			<label>Headline Indicator</label>
			<form:select path="headlineIndicator" style="width:120px;">
				<form:option value="" />
				<form:option value="Y">Yes</form:option>
				<form:option value="N">No</form:option>
			</form:select><br>		
	     	<label>Survey Type</label>
			<form:select path="surveyTypes"  multiple="true" size="4" style="width:120px;">
				<option value="" />
				<form:options items="${SurveyTypesList}" itemLabel="displayName" property="id" />
			</form:select><br>
			<label>Assigned User</label>
			<form:select path="assignedToUser" style="width:120px;">
						<form:option value="" />
						<form:options items="${ApmUsersList}" itemLabel="displayName" itemValue="id" />
					</form:select>
			<br>
		
	</div>	
	<div  class="col-3-4" style="float:left">
		     <label>Start Date From</label>
		   	 <form:input path="performanceStartDate" maxlength="10" size="10" id="datepicker_performanceStartDate"/><a href="javascript:clearField('datepicker_performanceStartDate');" class="<%=UIWidgetConstants.STYLE_CLEAR_FIELD_ICON_ENABLED%>" title="<%=UIWidgetConstants.TITLE_CLEAR_FIELD%>"></a><br>
		     <label>Start Time From</label>
		   	 <form:input path="performanceStartTime" maxlength="10" size="10" id="_time_performanceStartTime"/><br>
	     	<label>Performer</label>
	     	<form:input path="featuredPerformerName" maxlength="100" /><br>
	     	<label>Performer Search Type</label>
		    <form:select path="performerSearchType" style="width:120px;">
				<form:option value="BGNS">Begins</form:option>
				<form:option value="CNTS">Contains-Begins</form:option>
			</form:select><br>
			<label>Posted</label>
			<form:select path="postedFlag" style="width:120px;">
			<option value=""></option>
			<option  value="N"  <c:if test="${empty performanceSearch.postedFlag or performanceSearch.postedFlag eq 'N'}">  selected="selected" </c:if> > Not Posted</option>
			<option  value="Y"  <c:if test="${performanceSearch.postedFlag eq 'Y'}">  selected="selected" </c:if> >	Posted</option>
			</form:select><br>
			
	     <label>Status Date From</label>
	     <form:input path="statusDateFrom" maxlength="10" size="12" id="datepicker_statusDateFrom"/><a href="javascript:clearField('datepicker_statusDateFrom');" class="<%=UIWidgetConstants.STYLE_CLEAR_FIELD_ICON_ENABLED%>" title="<%=UIWidgetConstants.TITLE_CLEAR_FIELD%>"></a><br> 
	     <label>Set List Type</label>
						<form:select path="setlistType" style="width:120px;">
							<form:option value="" />
							<form:option value="A">Average</form:option>
							<form:option value="S">Special</form:option>
						</form:select><br>	
	     	<label>Sample Type</label>
						<form:select path="sampleTypes" multiple="true" size="4" style="width:120px;">
								<option value="" />
								<%-- <c:forEach var="sample" items="${SampleTypesList}">
								<form:option value="${sample}" />
								
								
								</c:forEach> --%>
								<form:options items="${SampleTypesList}" itemLabel="displayName" itemValue="id" />
							</form:select><br><br><br><br>
		<label>Count Policy<br></label>
			<form:select path="searchResultsCountPolicy" style="width:120px;">
				<form:options items="${SearchResultsCountPoliciesList}" itemLabel="displayName" itemValue="id" />
			</form:select>	 
	</div>
	<div  class="col-4-4" style="float:left">
	
		     <label>Start Date To</label>
		   	 <form:input path="performanceEndDate" maxlength="10" size="10"  id="datepicker_performanceEndDate" /><a href="javascript:clearField('datepicker_performanceEndDate');" class="<%=UIWidgetConstants.STYLE_CLEAR_FIELD_ICON_ENABLED%>" title="<%=UIWidgetConstants.TITLE_CLEAR_FIELD%>"></a><br>
		   	 <label>Start Time To</label>
		   	 <form:input path="performanceEndTime" maxlength="10" size="10" id="_time_performanceEndTime"/><br>
	     <label>Supplier Work Code</label>
	     <form:input path="supplierWorkCode" maxlength="11" /><br>
		   	 		   	    	 
		<label>Error / Warning</label>
		<form:select path="performanceErrorWarningCodes" styleClass="fulllengthdropdown" style="width:280px;" multiple="false">
			<form:option value=""/>
			<form:options items="${PerformanceErrorWarningCodesList}" itemLabel="displayName" itemValue="id" />
		</form:select><br>	<label>Play Count (Above or Equal)</label>
		   	 <form:input path="playCount" maxlength="10" size="10"/><br>
	     <label>Status Date To</label>
	     <form:input path="statusDateTo" maxlength="10" id="datepicker_statusDateTo"/><a href="javascript:clearField('datepicker_statusDateTo');" class="<%=UIWidgetConstants.STYLE_CLEAR_FIELD_ICON_ENABLED%>" title="<%=UIWidgetConstants.TITLE_CLEAR_FIELD%>"></a><br>
	     
	     <label>Classical Ind</label>
	     <form:select path="classicalIndicator" style="width:120px;">
				<form:option value="" />
				<form:option value="N">No</form:option>
				<form:option value="Y">Yes</form:option>
		</form:select><br>
		   	 <label>Use Type</label>
							<form:select path="useTypes" multiple="true" size="4" >
									<option value="" />
									<form:options items="${UseTypesList}" itemLabel="displayName" itemValue="id" />
							</form:select><br><br><br><br>
			<br>
			<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_PS_100_SEARCH %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_SEARCH_PROGRAM_PERFORMANCES %>" id="<%=UIWidgetConstants.ID_SEARCH_PROGRAM_PERFORMANCES %>">onclick="javascript:pleaseWait();javascript:changeAction(document.forms[0], 'SEARCH_PROGRAM_PERFORMANCES');"</prep:uiWidget>&nbsp;&nbsp;&nbsp;
			<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_PS_100_SEARCH %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_SEARCH_WORK_PERFORMANCES %>" id="<%=UIWidgetConstants.ID_SEARCH_WORK_PERFORMANCES %>">onclick="javascript:pleaseWait();javascript:changeAction(document.forms[0], 'SEARCH_WORK_PERFORMANCES');"</prep:uiWidget>
					 
					
	</div>
	<br>
	
</fieldset> 
</div>



</form:form>

</body>
</html>