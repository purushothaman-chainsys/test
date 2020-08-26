<!DOCTYPE HTML>
<%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>


<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>
<%@ page import="com.ascap.apm.controller.utils.Utils"%>
<%@ page import="com.ascap.apm.controller.utils.HtmlSelectOption"%>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>

<% pageContext.setAttribute("distirbutionYearQuarter", HtmlSelectOption.getOptionsValues(Utils.getNextSurveyYearQuarters(12)), PageContext.PAGE_SCOPE);
  pageContext.setAttribute("FileTypeList", HtmlSelectOption.getLookUpTable("FileType"), PageContext.PAGE_SCOPE); 
  %> 
<html>
<head>
<title>APM</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<%@ include file = "/views/common/uiWidgets.jsp"%>
<script type="text/javascript">
$(function() {
		sortTable('US-500','mainData', [0,10]);
		filterTable('US-500','mainData' , [0,10]);
}); 

function setNavigationType(navigation) {
		//alert(navigation);
		document.forms[0].navigationType.value = navigation;
		document.forms[0].submit();
}
function update(obj) {
	if($("table#mainData tr input:checked").length == 0) {	
			$("#errorMessages").html("");
			$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.archives.update.selection"/></span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
		 }
	else {	 
		var originalExists = false;
		$("table#mainData tr input:checked").each(function(){
			var checkedIndex = this.value;			
		});
		document.forms[0].operationType.value = 'UPDATE';
		document.forms[0].submit();
		
		
		}
}

function uploadFile() {
		document.forms[0].submit();
}

function cancel() {
   document.forms[0].reset();
   document.forms[0].action = "apmFiles";
   document.forms[0].submit();  
}



</script>
<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css">



</head>
<body> 

<div class="menubg"  style="width: 100%; border: 0px; height:90px;"> 
<jsp:include page="/views/menu.jsp"/>

</div>
<div></div>
<table class="titletable">
<tr>
	<td>File List [FL-101]</td>
</tr>
 </table>
 
 <table class="errortable">
		<tr>
			<td>
				<div id="serverErrorMessages">
<span class="txtGreen"><c:if test="${not empty systemmessage}"><li><c:out value="${systemmessage}" /></li></c:if></span>
<span class="txtRed"><c:if test="${not empty systemerror}"><li><c:out value="${systemerror}" escapeXml="false"/></li></c:if>
				<c:forEach var="systemerror" items="${systemerrorlist}">
				<li><c:out value="${systemerror}" escapeXml="false"/></li>
				</c:forEach>
				</span>
				</div>
				<div id="uierror"></div>
				<div id="errorMessages"></div>
			</td>
		</tr>
</table>
 
 
 <form:form action="apmFileUpload"  enctype="multipart/form-data" modelAttribute="apmFileUpload">
 


<div>
<div style= "">
   
<table class="detailstable1000" style="width:1200px; font-size: 11px; font-weight: bold; color: #000000; line-height: 11px; margin:0 auto;">
	 
	<tr>	
		
		<td>File Type<br>
		   <form:select  path="fileType">
                <form:option value="" />
				<form:options items="${FileTypeList}" itemLabel="displayName" itemValue="id" />
			</form:select>
		</td>
		<td>Distribution Quarter<br>
		   <form:input path="targetYearQuarter" id='<%="yearquarterpicker_targetYearQuarter_"%>' size="8" readonly="true"/><a href="javascript:clearField('<%="yearquarterpicker_targetYearQuarter_"%>');" class="<%=UIWidgetConstants.STYLE_CLEAR_FIELD_ICON_ENABLED%>" title="<%=UIWidgetConstants.TITLE_CLEAR_FIELD%>"></a>
		</td>
		
	</tr>
   <tr>
  
   <td>
       <br> <form:input type="file" path="radioFile" id="file"/> </td>
               
 <td width="17%" valign="bottom" align="left">
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_FL_100_UPLOAD_FILE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_IMPORT %>" id="<%=UIWidgetConstants.ID_IMPORT%>" title="Upload File">onclick="javascript:uploadFile();"</prep:uiWidget>
 </td>
	  <td align="left">			  

	<prep:uiWidget type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_CANCEL %>"
				   id="<%=UIWidgetConstants.ID_CANCEL %>">onclick="javascript:cancel();"</prep:uiWidget>
	 </td>
  </tr>					
</table>
</div>
</div> 

   </form:form>
</body>
</html>