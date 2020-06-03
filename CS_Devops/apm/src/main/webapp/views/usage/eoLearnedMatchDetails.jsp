<!DOCTYPE HTML>

<%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
 pageContext.setAttribute("UsageSuppliersList", HtmlSelectOption.getLookUpTable("UsageSuppliers"), PageContext.PAGE_SCOPE);
%>	

<%
com.ascap.apm.vob.usage.EOLearnedMatch xApmLearnedMatchForm;
java.util.List<String> xMedleyOrigWorks = new java.util.ArrayList<String>();
java.lang.String xOperType = ""; 
String[] xMedleyCloneCountsTemp = null;
try{
	xApmLearnedMatchForm =(com.ascap.apm.vob.usage.EOLearnedMatch) request.getAttribute("eoLearnedMatch");
	if(xApmLearnedMatchForm != null) {
		xMedleyOrigWorks = xApmLearnedMatchForm.getWorkIdCollection();
		xOperType = xApmLearnedMatchForm.getOperationType();
		xMedleyCloneCountsTemp = xApmLearnedMatchForm.getMedleyCloneCounts();
	}
	if(xMedleyOrigWorks == null) xMedleyOrigWorks = new java.util.ArrayList<String>();
	if(xMedleyCloneCountsTemp == null) {
		xMedleyCloneCountsTemp = new String[1];
		xMedleyCloneCountsTemp[0] = "1";
	}
	
}
catch(ClassCastException cce){}

//for(String x : xMedleyOrigWorks) {
	//System.out.println("YEEEEEEEEEEEEEEEEEEa " + x );
//}

 
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
.errortable li {
	text-align:left;
}
</style>

<script type="text/javascript">

var manageSupplier = function () {

    if ($("#_matchType").val() === 'ISWC' || $("#_matchType").val() === 'ISRC' || $("#_matchType").val() === 'URL' ) {
    	$("#_supplierCode").val('');
        $("#_supplierCode").attr('disabled', 'disabled');
    }
    else {
        $("#_supplierCode").removeAttr("disabled");
    }
    
    if( $("#_matchType").val() === 'URL' || $("#_matchType").val() === 'SUPPLIERID') {
		$("#_plus_minus_btns").show();
		if($('input[name=operationType]').val() === 'EDIT') {
			 $("#_supplierCode").attr('disabled', 'disabled');
		}
		//$('input[name="abcdef_chk"]').show();
	}
    else {
    	$("#_plus_minus_btns").hide();
    	
    	$("#mdlTbl>tbody").find("tr:gt(1)").remove();
    	//.find("tr:gt(0)").remove();
    	
    	//$('input[name="abcdef_chk"]').hide();
    }

};






$(function() {
	
	if($("#_matchType").val() === 'URL') {
		$("#_plus_minus_btns").show();
	}

	$('#_matchType').change(manageSupplier).change();

	$('#_Cancel').click(function() {
		document.forms[0].operationType.value='CANCEL';
		document.forms[0].submit();
	});
	
	
	$('#_Update').click(function() {		
		
		var errMessage = '<span class=txtRed>';
		var errorsFound = false;
		var workIdMissing = false; 
		
		if($('#_matchIdValue').val() == '') {
			errorsFound = true;
			errMessage += '<li><spring:message code="us.error.apm.eo.learnedmatch.requiredfields.matchvalue"/>';
		}
		else {
			$('#_matchIdValue').val($.trim($('#_matchIdValue').val()));
			if($('#_matchIdValue').val().indexOf(' ') >= 0) {
				errorsFound = true;
				errMessage += '<li><spring:message code="us.error.apm.eo.learnedmatch.url.spacechar"/>';
			}
		}
		
		if($('input[name=workIds]').length < 1) {
			errorsFound = true;
			errMessage += '<li><spring:message code="us.error.apm.eo.learnedmatch.requiredfields.workid"/>';
		}
		
		
		$("input[name=workIds]").each(function(){
			//workIdExists = true;
			$(this).val( $.trim(this.value));
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
						errMessage += '<li><spring:message code="us.error.apm.archives.workid.nonnumber" arguments="'+wId+'"/>';
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
	
	
	
	
	$('#_Submit').click(function() {
		if($("#_matchType").val() != 'URL' &&  $("#_matchType").val() != 'SUPPLIERID' && $("#_matchType").val() != '') {
			$('#_matchIdValue').val($.trim($('#_matchIdValue').val().toUpperCase()));
			$('#_workId').val($.trim($('#_workId').val().toUpperCase()));
		}
		
		
		var errMessage = '<span class=txtRed>';
		var errorsFound = false;
		
		
		if( $("#_matchType").val() != 'URL' && $("#_matchType").val() != 'SUPPLIERID') {
			//alert(  $('input[name=workIds]').length);
			if($('input[name=workIds]').length != 1) {
				errorsFound = true;
				errMessage += '<li><spring:message code="us.error.apm.eo.learnedmatch.workid.gt1"/>';
			}
			
			$('#_matchIdValue').val($.trim($('#_matchIdValue').val()));
			if($('#_matchIdValue').val().indexOf(' ') >= 0) {
				errorsFound = true;
				errMessage += '<li><spring:message code="us.error.apm.eo.learnedmatch.url.spacechar"/>';
			}
		}
		else {
			if($('input[name=workIds]').length < 1) {
				errorsFound = true;
				errMessage += '<li><spring:message code="us.error.apm.eo.learnedmatch.requiredfields.workid"/>';
			}
			$('#_matchIdValue').val($.trim($('#_matchIdValue').val()));
			
			if( $("#_matchType").val() === 'URL') {
				if($('#_matchIdValue').val().toLowerCase().indexOf("<%=com.ascap.apm.common.utils.constants.UsageConstants.YOUTUBE_URL_PREFIX %>") != 0) {
					errorsFound = true;
					errMessage += "<li><spring:message code='us.error.apm.learnedmatch.url.format.invalid'/>";
				}
				else {
					if($.trim($('#_matchIdValue').val()).length <= "<%=com.ascap.apm.common.utils.constants.UsageConstants.YOUTUBE_URL_PREFIX %>".length){
						errorsFound = true;
						errMessage += "<li><spring:message code='us.error.apm.learnedmatch.url.videoid.invalid'/>";
					}
				}
			}
			
			else if( $("#_matchType").val() === 'SUPPLIERID') {
				if($.trim($('#_matchIdValue').val()).length == 0) {
				}
			}
			
			
			var workIdMissing = false;
			$("input[name=workIds]").each(function(){
				//workIdExists = true;
				var wId = this.value;
				if($.trim(wId) === '' ) {
					
					errorsFound = true;	
					if(!workIdMissing) {
						errMessage += '<li><spring:message code="us.error.apm.archives.workid.required"/>';			
						workIdMissing = true;
					}
					
				}
				else {
					workIdExists = true;
					if(isNaN(wId) || Number(wId) <=0 || !allNumeric(wId)) {
							errorsFound = true;
							errMessage += '<li><spring:message code="us.error.apm.archives.workid.nonnumber" arguments="'+wId+'"/>';
						}
					}
			});
			
			
		}
		
		
		if($.trim($('#_supplierCode').val()) == '' && $("#_matchType").val() === 'SUPPLIERID') {
			errorsFound = true;
			errMessage += '<li><spring:message code="us.error.apm.eo.learnedmatch.requiredfields.supplier"/>';
		}
		if($('#_matchType').val() == '') {
			errorsFound = true;
			errMessage += '<li><spring:message code="us.error.apm.eo.learnedmatch.requiredfields.matchtype"/>';
		}
		if($('#_matchIdValue').val() == '' ) {
			errorsFound = true;
			errMessage += '<li><spring:message code="us.error.apm.eo.learnedmatch.requiredfields.matchvalue"/>';
		}
		if($("#_matchType").val() != 'URL' && $("#_matchType").val() != 'SUPPLIERID') {
			if($.trim($('#_workId').val()) === '') {
				errorsFound = true;
				errMessage += '<li><spring:message code="us.error.apm.eo.learnedmatch.requiredfields.workid"/>';
			}
		else {
			var wId = $('#_workId').val();
			workIdExists = true;
				if(isNaN(wId) || Number(wId) <=0 || !allNumeric(wId)) {
						errorsFound = true;
						errMessage += '<li><spring:message code="us.error.apm.archives.workid.nonnumber" arguments="'+wId+'"/>';
				}
			}
		}
			
		
		
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
});


function addRows()	{
	if( $("#_matchType").val() === 'SUPPLIERID') {
		$("#mdlTbl>tbody").append('<tr><td width="20px;"><input type="checkbox" name="abcdef_chk"></input></td><td align="left"><input name="workIds" value="${apmLearnedMatchForm.workIds}" value="" size="15" maxlength="11"/>&nbsp;&nbsp;<input name="medleyCloneCounts" value="${apmLearnedMatchForm.medleyCloneCounts}" value="1" size="1" maxlength="2"/></td></tr>');
	}
	else if( $("#_matchType").val() === 'URL') {
		$("#mdlTbl>tbody").append('<tr><td width="20px;"><input type="checkbox" name="abcdef_chk"></input></td><td align="left"><input name="workIds" value="${apmLearnedMatchForm.workIds}" value="" size="15" maxlength="11"/></td></tr>');
	}
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
<fieldset class="fieldsetfull" style="width:600px;"><legend class="legendfull">Learned Match Details [LM-201]</legend>


 <table class="errortable" style="width:600px;">
		<tr>
			<td>
				<div id="serverErrorMessages">
				<span class="txtGreen"><c:if test="${not empty systemmessage }"><li> <c:out value="${systemmessage}"/></li></c:if></span>
				<span class="txtRed" style="align:left;"><c:if test="${not empty systemerror }"><li><c:out value="${systemerror}" escapeXml="false"/></li></c:if></span>
				</div>
				<div id="uierror"></div>
				<div id="errorMessages"></div>
			</td>
		</tr>
</table>


<form:form action="learnedMatchDetailsEO" modelAttribute="eoLearnedMatch">

<% boolean isDisabled = false; %>
<c:if test="${eoLearnedMatch.operationType eq 'EDIT' }">
<%  isDisabled = true; %>
</c:if>


<form:hidden path="operationType" />

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
				<form:select  path="supplierCode" size="1"  style="width:180px;" id="_supplierCode"  disabled="<%=isDisabled%>">
					<form:option value="" />
					<form:options items="${ UsageSuppliersList}" itemLabel="displayName" itemValue="id" />
				</form:select>
				</td>
 </tr>				
 
 <tr class="txtBlk_bold">
				<td></td>
				<td align="left">Match Type<br>	
				<form:select path="matchType" id="_matchType"  disabled="<%=isDisabled%>">
				<form:option value=""></form:option>
				<form:option value="ISRC">ISRC</form:option>
				<form:option value="ISWC">ISWC</form:option>
				<form:option value="SUPPLIERID">Supplier Song Id</form:option>
				<form:option value="URL">URL</form:option>
			</form:select>
				
				
					
				</td>
 </tr>				
 
<tr class="txtBlk_bold">
				<td></td>				
				<td align="left">Match Value<br>
					<form:input path="matchIdValue" size="49" maxlength="50" styleClass="Xuppercase" id="_matchIdValue"  disabled="<%=isDisabled%>"/>
				</td>
 </tr>	
	
	
	
	</table>
	
	
	<table id="mdlTbl"  class="detailstable">
	<tbody>
	<tr height="2em">	
				<td width="55"></td>
				<td width="535"></td>
	</tr>	
	<% int wNum = 0;    for(String x : xMedleyOrigWorks) { wNum ++ ;%>	
	<tr><td width="20px:"><input type="checkbox" name="abcdef_chk"></input></td><td align="left"><form:input path="workIds" value="<%=x%>" size="15" maxlength="11" id="_workId"/>
	&nbsp;<form:input path="medleyCloneCounts" value="<%= xMedleyCloneCountsTemp[wNum-1]%>" size="1" maxlength="2"/>
	</td></tr>
	<% } %>	
	<% if(wNum == 0) { %>
	<tr><td width="20px:"><input type="checkbox" name="abcdef_chk"></input></td><td align="left"><form:input  path="workIds" value="" size="15" maxlength="11" id="_workId"/>
	&nbsp;<form:input path="medleyCloneCounts" value="1" size="1" maxlength="2"/>
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
 
<c:if test="${eoLearnedMatch.operationType eq 'EDIT' }">
 	<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_UPDATE %>" id="<%=UIWidgetConstants.ID_UPDATE%>" title="Update">onclick="javascript:void(0);"</prep:uiWidget>
 	<form:hidden path="supplierCode" />
	<form:hidden  path="matchIdValue" />
	<form:hidden  path="matchType" />
	<form:hidden  path="multWorkId" />
 </c:if> 
 
<c:if test="${eoLearnedMatch.operationType ne 'EDIT' }">
 <prep:uiWidget name="${ProtectedResourcesConstants.APM_LM_100_LEARNED_MATCH_CREATE}" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_SUBMIT %>" id="<%=UIWidgetConstants.ID_SUBMIT%>" title="Submit">onclick="javascript:void(0);"</prep:uiWidget>
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