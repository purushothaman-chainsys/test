<!DOCTYPE HTML>
<%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>


<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>
<%@ page import="com.ascap.apm.controller.utils.Utils"%>
<%@ page import="com.ascap.apm.controller.utils.HtmlSelectOption"%>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>	
<%  
 pageContext.setAttribute("UsageSuppliersList", HtmlSelectOption.getLookUpTable("UsageSuppliers"), PageContext.PAGE_SCOPE);
%>



<%
com.ascap.apm.vob.usage.ApmLearnedMatch xApmLearnedMatchForm;
java.util.List<String> xMedleyOrigWorks = new java.util.ArrayList<String>();
java.lang.String xOperType = ""; 
String[] xMedleyCloneCountsTemp = null;
try{
	xApmLearnedMatchForm =(com.ascap.apm.vob.usage.ApmLearnedMatch) request.getAttribute("apmLearnedMatchForm");
	if(xApmLearnedMatchForm != null) {
		xMedleyOrigWorks = xApmLearnedMatchForm.getWorkIdCollection();
		xOperType = xApmLearnedMatchForm.getOperationType();
		xMedleyCloneCountsTemp = xApmLearnedMatchForm.getMedleyCloneCounts();
		
		if(xMedleyCloneCountsTemp == null) {
			xMedleyCloneCountsTemp = new String[1];
			xMedleyCloneCountsTemp[0] = "1";
		}
		
	}
}
catch(ClassCastException cce){}

for(String x : xMedleyOrigWorks) {
}

 
%>




<html>
<head>
<title>APM</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 

<%@ include file = "/views/common/uiWorkMatchWidges.jsp"%>
<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css">
<style>
.uppercase {text-transform:uppercase}
#content {
  width: 700px ;
  margin-left: auto ;
  margin-right: auto ;
}
</style>

<script type="text/javascript">
var manageWorkIdField = function () {
    if ($("#_learnTypeFLD").val() == 'N') {
        $("input[name=workIds]").removeAttr("disabled");
        $("input[name=medleyCloneCounts]").removeAttr("disabled");
        $("#_plus_minus_btns").show();
    }
    else {
    	$("input[name=workIds]").val('');
        $("input[name=workIds]").attr('disabled', 'disabled');
        $("input[name=medleyCloneCounts]").attr('disabled', 'disabled');
        $("#_plus_minus_btns").hide();
    }
};


$(function() {

    $('.detailstable').css('width','600px');
    $('.detailstable').css('margin','0 auto');


	$('#_learnTypeFLD').change(manageWorkIdField).change(); 
	
	$('#_Submit').click(function() {
		$('#_workTitle').val($.trim($('#_workTitle').val().toUpperCase()));
		$('#_performerName').val($.trim($('#_performerName').val().toUpperCase()));
		$('#_writerName').val($.trim($('#_writerName').val().toUpperCase()));
		
		var errMessage = '<span class=txtRed>';
		var errorsFound = false;
		var workIdMissing = false; 
		
		if($('#_workTitle').val() == '') {
			errorsFound = true;
			errMessage += '<li><spring:message code="us.error.apm.learnedmatch.requiredfields.workTitle"/>';
		}
		if($('#_performerName').val() == '' && $('#_writerName').val() == '') {
			errorsFound = true;
			errMessage += '<li><spring:message code="us.error.apm.learnedmatch.requiredfields.pfrName"/>';
		}
		else if($('#_performerName').val() != '' && $('#_writerName').val() != '') {
			errorsFound = true;
			errMessage += '<li><spring:message code="us.error.apm.learnedmatch.requiredfields.pfrNameAndWriter"/>';
		}
		
		
		if($("#_learnTypeFLD").val() == 'N' &&
			($("input[name=workIds]").length == 0)) {			
			errorsFound = true;
			errMessage += '<li><spring:message code="us.error.apm.learnedmatch.requiredfields.workId"/>';
		}
		
		$("input[name=workIds]").each(function(){
			//workIdExists = true;
			var wId = this.value;
			if($.trim(wId) === '' ) {
				if ($("#_learnTypeFLD").val() == 'N') {
					errorsFound = true;	
					if(!workIdMissing) {
						errMessage += '<li><spring:message code="us.error.apm.archives.workid.required"/>';			
						workIdMissing = true;
					}
				}
			}
			else {
				workIdExists = true;
				if(isNaN(wId) || Number(wId) <=0 || !allNumeric(wId)) {
						errorsFound = true;
						errMessage += '<li><spring:message code="us.error.apm.archives.workid.nonnumber" arguments="${wId}"/>';
					}
				}
		});
		
		
		
		var countsNumeric = true;
		$("input[name=medleyCloneCounts]").each(function(){
			var cloneValue = $(this).val();
			if(isNaN(cloneValue) || Number(cloneValue) <=0 || !allNumeric(cloneValue)) {
				countsNumeric = false;
				errorsFound = true;
				errMessage += '<li><spring:message code="us.error.apm.manualmatch.multiplematch.clonecount.numeric"/>';
			}
			if(countsNumeric) {
				if(Number(cloneValue) > 20) {
					errorsFound = true;
					errMessage += '<li><spring:message code="us.error.apm.manualmatch.multiplematch.clonecount.maxlimit"/>';
				}
			}
	
		});
		
		
		
		if(errorsFound) {
			$("#serverErrorMessages").html("");
			$("#uierror").html(''+errMessage+'</span>');
			unCheckSub($('a[id^=_Submit'));
			return;
		}
		else {
			$("#serverErrorMessages").html("");
			$("#uierror").html('');
		
		var updateProgressDiv = $(document.createElement('div')); 
		var txt = '<span style="font-size:1.3em;">Please Wait.. </span><br> APM is updating the selected learned matches.<br><img src="<%=request.getContextPath()%>/images/indicator.gif">';
		
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
		
		
		
			document.forms[0].operationType.value='CREATE_LEARNED_MATCH';
			document.forms[0].submit();
		}
	});
	$('#_Update').click(function() {
	
		$('#_workTitle').val($.trim($('#_workTitle').val().toUpperCase()));
		$('#_performerName').val($.trim($('#_performerName').val().toUpperCase()));
		$('#_writerName').val($.trim($('#_writerName').val().toUpperCase()));
		
		var errMessage = '<span class=txtRed>';
		var errorsFound = false;
		var workIdMissing = false; 
		
		if($('#_workTitle').val() == '') {
			errorsFound = true;
			errMessage += '<li><spring:message code="us.error.apm.learnedmatch.requiredfields.workTitle"/>';
		}
		
		if($('#_performerName').val() == '' && $('#_writerName').val() == '') {
			errorsFound = true;
			errMessage += '<li><spring:message code="us.error.apm.learnedmatch.requiredfields.pfrName"/>';
		}
		else if($('#_performerName').val() != '' && $('#_writerName').val() != '') {
			errorsFound = true;
			errMessage += '<li><spring:message code="us.error.apm.learnedmatch.requiredfields.pfrNameAndWriter"/>';
		}
		if($("#_learnTypeFLD").val() == 'N' &&
			($("input[name=workIds]").length == 0)) {			
			errorsFound = true;
			errMessage += '<li><spring:message code="us.error.apm.learnedmatch.requiredfields.workId"/>';
		}
		
		$("input[name=workIds]").each(function(){
			//workIdExists = true;
			var wId = this.value;
			if($.trim(wId) === '' ) {
				if ($("#_learnTypeFLD").val() == 'N') {
					errorsFound = true;	
					if(!workIdMissing) {
						errMessage += '<li><spring:message code="us.error.apm.archives.workid.required"/>';			
						workIdMissing = true;
					}
				}
			}
			else {
				workIdExists = true;
				if(isNaN(wId) || Number(wId) <=0 || !allNumeric(wId)) {
					errorsFound = true;
					errMessage += '<li><spring:message code="us.error.apm.archives.workid.nonnumber" arguments="${wId}" />';
				}
			}
		});
		
		
		
		var countsNumeric = true;
		$("input[name=medleyCloneCounts]").each(function(){
			var cloneValue = $(this).val();
			if(isNaN(cloneValue) || Number(cloneValue) <=0 || !allNumeric(cloneValue)) {
				countsNumeric = false;
				errorsFound = true;
				errMessage += '<li><spring:message code="us.error.apm.manualmatch.multiplematch.clonecount.numeric"/>';
			}
			if(countsNumeric) {
				if(Number(cloneValue) > 20) {
					errorsFound = true;
					errMessage += '<li><spring:message code="us.error.apm.manualmatch.multiplematch.clonecount.maxlimit"/>';
				}
			}
	
		});
		
		
		
		
		if(errorsFound) {
			$("#serverErrorMessages").html("");
			$("#uierror").html(''+errMessage+'</span>');
			unCheckSub($('a[id^=_Update'));
			return;
		}
		else {
		
		
		var updateProgressDiv = $(document.createElement('div')); 
		var txt = '<span style="font-size:1.3em;">Please Wait.. </span><br> APM is updating the selected learned matches.<br><img src="<%=request.getContextPath()%>/images/indicator.gif">';
		
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
		
			document.forms[0].operationType.value='UPDATE_LEARNED_MATCH';
			document.forms[0].submit();
		}
	});
	
	
	$('#_Cancel').click(function() {
		document.forms[0].operationType.value='CANCEL';
		document.forms[0].submit();
	});
});




function submitForm(obj) {
	$('#_workTitle').val($.trim($('#_workTitle').val()));
	
	if($('#_workTitle').val() == '') {
		unCheckSub(obj);
		location.href="#";
		return;
	}
	document.forms[0].operationType.value='CREATE_LEARNED_MATCH';	
}


function addRows()	{
	$("#mdlTbl>tbody").append('<tr><td><input type="checkbox" name="abcdef_chk"></input></td><td style="text-align:left;"><input type="text"  name="workIds" value="" size="15" maxlength="11" id="_workId"/>&nbsp;&nbsp;<input type="text" name="medleyCloneCounts" value="1" size="1" maxlength="2"/></td></tr>');		
}
function deleteRows() {
	if($("#mdlTbl>tbody tr input:checked").length == 0) {}
	$("#mdlTbl>tbody tr input:checked").parent().parent().remove();
}






</script>


</head>
<body>  



<div class="menubg" style="width: 100%; border: 0px; height:90px;">
<jsp:include page="/views/menu.jsp"/>
</div>
 
<div id="content">
<fieldset class="fieldsetfull" style="width:600px;"><legend class="legendfull">Learned Match Details [LM-101]</legend>


 <table class="errortable" style="width:600px;">
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


<form:form action="apmLearnedMatchDetails" modelAttribute="apmLearnedMatchForm">  


<% boolean isDisabled = false; %>
<c:if test ="${apmLearnedMatchForm.operationType eq 'EDIT' }">
<%  isDisabled = true; %>
</c:if>


<form:hidden path="operationType" />
<form:hidden  path="learnedMatchType" />
<form:hidden  path="lmType" />

<table class="titletable" style="display:none" style="width:600px;">		
	<tr><td></td></tr>
</table>

<table class="detailstable">	

	<tr height="2em">	
				<td width="55"></td>
				<td width="530"></td>
	</tr>				
	<tr class="txtBlk_bold">
				<td></td>
				<td align="left">Supplier<br>
				<form:select  path="supplierCode" size="1"  style="width:180px;" disabled="<%=isDisabled%>">
					<form:option value="" />
					<form:options items="${UsageSuppliersList}" itemLabel="displayName" itemValue="id" />
				</form:select>
				</td>
 </tr>				
 <tr class="txtBlk_bold">
				<td></td>
				<td align="left">Work Title<br>	
					<input  name="workTitle"  value="${apmLearnedMatchForm.workTitle}" size="50" maxlength="150" class="uppercase"  id="_workTitle" disabled="<%=isDisabled%>"/>
				</td>
 </tr>				
<tr class="txtBlk_bold">
				<td></td>				
				<td align="left">Performer<br>	
					<form:input path="performerName" size="50" maxlength="100" class="uppercase" id="_performerName"  disabled="<%=isDisabled%>"/>
				</td>
 </tr>	
 
 
 		
<tr class="txtBlk_bold">
				<td></td>				
				<td align="left" style="padding-left:150px;">OR<br>
				</td>
 </tr>	
 
 
 
 			
<tr class="txtBlk_bold">
				<td></td>				
				<td align="left">Writer<br>	
					<form:input path="writerName" size="50" maxlength="100" styleClass="uppercase" id="_writerName"  disabled="<%=isDisabled%>"/>
				</td>
 </tr>					
<tr class="txtBlk_bold">
				<td></td>				
				<td align="left">Learn Type <br>
				<form:select path="learnedDeleteFlag" id="_learnTypeFLD"  style="width:80px;" disabled="<%=isDisabled%>">
					<form:option value="N">Match</form:option>
					<form:option value="Y">Delete</form:option>
				</form:select>	
				</td>
	</tr>
	
	
	
	
	
	</table>
	
	<table id="mdlTbl"  class="detailstable">
	<tbody>
	<tr height="2em">	
				<td width="55"></td>
				<td width="535"></td>
	</tr>	
	<% int wNum = 0; for(String x : xMedleyOrigWorks) { wNum ++ ;%>	
	<tr><td><input type="checkbox" name="abcdef_chk"></input></td><td  style="text-align: left;">
	<form:input path="workIds" value="<%=x%>" size="15" maxlength="11" id="_workId"/>
	&nbsp;<form:input  path="medleyCloneCounts" value="<%= xMedleyCloneCountsTemp[wNum-1]%>" size="1" maxlength="2"/>
	</td></tr>
	<% } %>	
	<% if(wNum == 0) { %>
	<tr><td><input type="checkbox" name="abcdef_chk"></input></td><td style="text-align: left;">
	<form:input path="workIds" value="" size="15" maxlength="11" id="_workId"/>
	&nbsp;<form:input  path="medleyCloneCounts" value="1" size="1" maxlength="2"/></td></tr>
	<% } %>
	</tbody>
	</table>
	
	
	<table  class="buttonstable600" id="_plus_minus_btns" style="display:none;text-align:left;">
	<tr>
	<td></td>
	<td>
		<prep:uiWidget id="<%=UIWidgetConstants.ID_ADD_NEW_ENTRY %>" type="<%= SecurityConstants.ICON_TYPE %>" >				    			    		
			onclick="javascript:addRows();"
		</prep:uiWidget>
		<prep:uiWidget id="<%=UIWidgetConstants.ID_REMOVE %>" type="<%= SecurityConstants.ICON_TYPE %>" >				    			    		
			onclick="javascript:deleteRows();"
		</prep:uiWidget>
	
	</td>
	</tr>
	</table>
	
	
	
	
	
	<table class="buttonstable600">
 <tr>
 <td style="width:20%"></td>
 <td align="center" width="20%">
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_CREATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_CANCEL %>" id="<%=UIWidgetConstants.ID_CANCEL%>" title="Cancel">onclick="javascript:void(0);"</prep:uiWidget>
 
 <c:if test="${apmLearnedMatchForm.operationType eq 'EDIT'}">
 	<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_UPDATE %>" id="<%=UIWidgetConstants.ID_UPDATE%>" title="Update">onclick="javascript:void(0);"</prep:uiWidget>
 	<form:hidden  path="supplierCode" />
	<form:hidden  path="workTitle" />
	<form:hidden  path="performerName" />
	<form:hidden  path="writerName" />
	<form:hidden  path="learnedDeleteFlag" />
	<form:hidden  path="multWorkId" />
 </c:if> 
 <c:if test="${apmLearnedMatchForm.operationType ne 'EDIT'}">
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_CREATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_SUBMIT %>" id="<%=UIWidgetConstants.ID_SUBMIT%>" title="Submit">onclick="javascript:void(0);"</prep:uiWidget>
 </c:if>
  </td>	
 <td style="width:20%"></td>
</tr>
 </table>




</form:form>
</fieldset>
</div>

</body>
</html>