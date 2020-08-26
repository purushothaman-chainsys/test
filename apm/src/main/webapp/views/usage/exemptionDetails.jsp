<!DOCTYPE HTML>
<%@page	language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
	
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>
<%@ page import="com.ascap.apm.controller.utils.Utils"%>
<%@ page import="com.ascap.apm.controller.utils.HtmlSelectOption"%>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>	

<% pageContext.setAttribute("exemptionTypes", HtmlSelectOption.getLookUpTable("ExemptionTypes"), PageContext.PAGE_SCOPE); %>

<html>
<head>

<title>APM</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 

<%@ include file= "/views/common/uiWorkMatchWidges.jsp"%>
<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css">
<style>
.redError {color:#FF0000; }
.uppercase {text-transform:uppercase}
</style>

<script type="text/javascript">

var manageFieldLengths = function () {
 	if ($("#_exemptionType").val() == 'PTY_ID' || $("#_exemptionType").val() == 'WRK_ID'  || $("#_exemptionType").val() == 'PROVIDER_ID' ) {
 		$("#_exemptionValue").attr('maxlength', 15);
 	}
 	else if ($("#_exemptionType").val() == 'WRK_TTL'  ) {
 		$("#_exemptionValue").attr('maxlength', 150);
 	}
 	else if ($("#_exemptionType").val() == 'LIBRARY_DISC_ID'  ) {
 		$("#_exemptionValue").attr('maxlength', 20);
 	}
 	else if ($("#_exemptionType").val() == 'WRITER'  ) {
 		$("#_exemptionValue").attr('maxlength', 100);
 	}
};

$(function() {

	$('#_exemptionType').change(manageFieldLengths).change();

	$('#_Cancel').click(function() {
		document.forms[0].operationType.value = 'CANCEL';
		document.forms[0].submit();
	});
	
	$('#_Submit').click(function() {
		errorsFound = false;
		errMsg = '<span class=txtRed>';
		$('#_exemptionValue').val($.trim($('#_exemptionValue').val().toUpperCase()));
		
		if($('#_exemptionType').val() == '') {
			errorsFound = true;
			errMsg += '<li><spring:message code="us.field.errors.generic.required" arguments="Exemption Type"/></li>';
		} 
		if($('#_exemptionValue').val() == '') {
			errorsFound = true;
			errMsg += '<li><spring:message code="us.field.errors.generic.required" arguments="Exemption Value"/></li>';
		} 
		if(!errorsFound) {
			if("PTY_ID"===$('#_exemptionType').val() || "WRK_ID"===$('#_exemptionType').val() || "PROVIDER_ID"===$('#_exemptionType').val() ) {
				if($('#_exemptionValue').val().length > 15) {
					errorsFound = true;
					errMsg += '<li><spring:message code="us.error.apm.exemption.value.digits.15"/></li>';
				}	
			}
			else if("LIBRARY_DISC_ID"===$('#_exemptionType').val() ) {
				if($('#_exemptionValue').val().length > 20) {
					errorsFound = true;
					errMsg += '<li><spring:message code="us.error.apm.exemption.value.length.20"/></li>';
				}	
			}
			else if("WRK_TTL"===$('#_exemptionType').val() ) {
				if($('#_exemptionValue').val().length > 150) {
					errorsFound = true;
					errMsg += '<li><spring:message code="us.error.apm.exemption.value.length.150"/></li>';
				}	
			}
			else if("WRITER"===$('#_exemptionType').val() ) {
				if($('#_exemptionValue').val().length > 100) {
					errorsFound = true;
					errMsg += '<li><spring:message code="us.error.apm.exemption.value.length.100"/></li>';
				}	
			}
		}
		if(!errorsFound) {
			if("PTY_ID"===$('#_exemptionType').val() || "WRK_ID"===$('#_exemptionType').val() || "PROVIDER_ID"===$('#_exemptionType').val() ) {
				var exVal = $('#_exemptionValue').val();
				if(isNaN(exVal) || Number(exVal) <=0 || !allNumeric(exVal)) {
					errorsFound = true;
					if("PTY_ID"===$('#_exemptionType').val()) {
						errMsg += '<li><spring:message code="us.field.errors.generic.numeric" arguments="Exemption Value"/></li>';
					}
					else if("WRK_ID"===$('#_exemptionType').val()) {
						errMsg += '<li><spring:message code="us.field.errors.generic.numeric" arguments="Exemption Value"/></li>';
					}
					else if("PROVIDER_ID"===$('#_exemptionType').val() ) {
						errMsg += '<li><spring:message code="us.field.errors.generic.numeric" arguments="Exemption Value"/></li>';
					}				
				}
			}
		}
		if(errorsFound) {
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html("");
			$("#uierror").html(errMsg+'</span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
		}
		document.forms[0].operationType.value = 'CREATE';
		document.forms[0].submit();
	});
	$('#_Update').click(function() {
		errorsFound = false;
		errMsg = '<span class=txtRed>';
		$('#_exemptionValue').val($.trim($('#_exemptionValue').val().toUpperCase()));
		if($('#_exemptionType').val() == '') {
			errorsFound = true;
			errMsg += '<li><spring:message code="us.field.errors.generic.required" arguments="Exemption Type"/></li>';
		} 
		if($('#_exemptionValue').val() == '') {
			errorsFound = true;
			errMsg += '<li><spring:message code="us.field.errors.generic.required" arguments="Exemption Value"/></li>';
		} 
		if(!errorsFound) {
			if("PTY_ID"===$('#_exemptionType').val() || "WRK_ID"===$('#_exemptionType').val() || "PROVIDER_ID"===$('#_exemptionType').val() ) {
				var exVal = $('#_exemptionValue').val();
				if(isNaN(exVal) || Number(exVal) <=0 || !allNumeric(exVal)) {
					errorsFound = true;
					if("PTY_ID"===$('#_exemptionType').val()) {
						errMsg += '<li><spring:message code="us.field.errors.generic.numeric" arguments="Exemption Value"/></li>';
					}
					else if("WRK_ID"===$('#_exemptionType').val()) {
						errMsg += '<li><spring:message code="us.field.errors.generic.numeric" arguments="Exemption Value"/></li>';
					}
					else if("PROVIDER_ID"===$('#_exemptionType').val() ) {
						errMsg += '<li><spring:message code="us.field.errors.generic.numeric" arguments="Exemption Value"/></li>';
					}				
				}
			}
		}
		if(errorsFound) {
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html("");
			$("#uierror").html(errMsg+'</span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
		}
		document.forms[0].operationType.value = 'UPDATE';
		document.forms[0].submit();
	});
});

function setNavigationType(navigation) {
		document.forms[0].operationType.value = 'SEARCH';
		document.forms[0].navigationType.value = navigation;
		document.forms[0].submit();
}
function filter() {
		document.forms[0].operationType.value = 'SEARCH';
		document.forms[0].submit();
}

function changeAction(oType) {
		document.forms[0].operationType.value = oType;
		document.forms[0].submit();
}

function submitForm(oType) {
		document.forms[0].operationType.value = oType;
		document.forms[0].submit();
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
	<td></td>
</tr>
 </table>

<form:form action="exemptionMaitain" modelAttribute="exemption">
<form:hidden path="operationType" />
<form:hidden  path="exemptionId" />
<fieldset class="fieldsetfull" style="width:600px;"><legend class="legendfull">Dedup Exemption Details [EX-101]</legend>
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

<table class="detailstable" style="width:50%;margin:auto;">
		<tr>
			<td>&nbsp;</td>
		</tr>
		
		<tr height="10px"></tr>
		<tr>
			<td>Exemption Type</td>
			<td>
			<c:if test="${exemption.operationType eq 'RETRIEVE'}">
				<form:hidden path="exemptionType" />
				<form:select path="exemptionType" styleId="_exemptionType" disabled="true">	
					<form:option value="" />
					<form:options items="${exemptionTypes}" itemLabel="displayName" itemValue="id" />
				</form:select>	
			</c:if>	
			
			<c:if test="${exemption.operationType ne 'RETRIEVE'}">
				<form:select path="exemptionType" id="_exemptionType">	
					<form:option value="" />
					<form:options items="${exemptionTypes}" itemLabel="displayName" itemValue="id" />
				</form:select>	
			</c:if>
			
			</td>
		</tr>
		<tr height="10px"></tr>
		<tr>
			<td>Exemption Value</td>
			<td><form:input size="50" maxlength="150"  path="exemptionValue" styleClass="uppercase"  id="_exemptionValue"/></td>
		</tr>
</table>

<table class="buttonstable"  style="width:50%;margin:auto;">
		<tr>
			<td align="right">
				<prep:uiWidget
					type="<%= SecurityConstants.ANCHOR_TYPE %>"
					label="<%= UIWidgetConstants.LABEL_CANCEL %>"
					id="<%=UIWidgetConstants.ID_CANCEL %>"
					title="<%= UIWidgetConstants.LABEL_CANCEL %>"> 
					onclick="javascript:void(0);"
				</prep:uiWidget>
				<prep:uiWidget
					type="<%= SecurityConstants.ANCHOR_TYPE %>"
					label="<%= UIWidgetConstants.LABEL_RESET %>"
					id="<%=UIWidgetConstants.ID_RESET %>"
					title="<%= UIWidgetConstants.LABEL_RESET %>"> 
					onclick="javascript:document.forms[0].reset();"
				</prep:uiWidget>
				<c:if test="${exemption.operationType eq 'RETRIEVE'}">
				<prep:uiWidget 
					name="<%= ProtectedResourcesConstants.APM_EX_101_EXEMPTION_UPDATE %>"
					type="<%= SecurityConstants.ANCHOR_TYPE %>"
					label="<%= UIWidgetConstants.LABEL_UPDATE %>"
					id="<%=UIWidgetConstants.ID_UPDATE %>"
					title="<%= UIWidgetConstants.LABEL_UPDATE %>">
					onclick="javascript:void(0);"
				</prep:uiWidget>
				</c:if>
				<c:if test="${exemption.operationType ne 'RETRIEVE'}">
				<prep:uiWidget 
					name="<%= ProtectedResourcesConstants.APM_EX_101_EXEMPTION_CREATE %>"
					type="<%= SecurityConstants.ANCHOR_TYPE %>"
					label="<%= UIWidgetConstants.LABEL_SUBMIT %>"
					id="<%=UIWidgetConstants.ID_SUBMIT %>"
					title="<%= UIWidgetConstants.LABEL_SUBMIT %>">
					onclick="javascript:void(0);"
				</prep:uiWidget>
				</c:if>
			</td>
		</tr>
	</table>
</fieldset>		
</form:form>
</body>
</html>