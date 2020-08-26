<!DOCTYPE HTML>
<%@page	language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>
<html>
<HEAD>
<%@ include file = "/views/common/uiWorkMatchWidges.jsp"%>
<script language="JavaScript">
$(function() {	  
		sortTable('RP_105','mainData', [2,6]);
		filterTable('RP_105','mainData', [2,6]);
}); 
</script>
<%@ page import="java.util.*,com.ascap.apm.common.utils.constants.PrepConstants"%>
<%@ page import="com.ascap.apm.vob.reports.Report"%>

<%-- <%@ page import="com.ascap.apm.servicehelpers.common.*"%> --%>
<%@ page import="com.ascap.apm.common.utils.constants.Constants"%>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.*"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">


<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css">

<TITLE>APM</TITLE>
</HEAD>
<script language="javascript">
function refreshData() {
       var reportName='<%=((Report)session.getAttribute("report")).getReportName()%>';
       var reqAction = '<%=(String)com.ascap.apm.common.utils.constants.PrepConstants.REPORT_VIEW_ARCHIVES%>';
       document.forms[0].action = "apmreport?reportName=" + reportName + "&doAction="+reqAction;
	   document.forms[0].submit();
}

function goBack(){
       var reportName='<%=((Report)session.getAttribute("report")).getReportName()%>';
       var reqAction = '<%=(String)com.ascap.apm.common.utils.constants.PrepConstants.REPORT_VIEW_REPORT_INSTANCE%>'; 
       document.forms[0].action = "apmreport?reportName=" + reportName + "&doAction="+reqAction;
	   document.forms[0].submit();
}

function returnReportInstance(indx) {
     var reqAction = '<%=(String)com.ascap.apm.common.utils.constants.PrepConstants.RETURN_REPORT%>';
     document.forms[0].action = "apmreport?instanceId=" + indx +"&doAction="+reqAction;
     document.forms[0].submit();
}

function setNavigationType(val1){
	var reportName='<%=((Report)session.getAttribute("report")).getReportName()%>';
	var reqAction = '<%=(String)com.ascap.apm.common.utils.constants.PrepConstants.REPORT_VIEW_REPORT_INSTANCE%>'; 
    document.forms[0].action = "apmreport?reportName=" + reportName + "&doAction=" +reqAction;
	document.forms[0].navigationType.value = val1;
	document.forms[0].submit();
}

function cancelReportInstance(indx){
var confirm= window.confirm('Are you sure!.You want to cancel the Pending report?');
if(confirm){
var reqAction = '<%=(String)com.ascap.apm.common.utils.constants.PrepConstants.REPORT_CANCEL_INSTANCE%>';   
document.forms[0].action = "apmreport?instanceId=" + indx +"&doAction="+reqAction ;
document.forms[0].submit();
} 
}

</script>
<form:form action="apmreport" modelAttribute="report">
<body>        
<div class="menubg"  style="width: 100%; border: 0px; height:90px;"> 
<jsp:include page="/views/menu.jsp"/>
</div>
<div></div>
<form:hidden  path="navigationType" />

<table class="titletable">
		<tr>
			<td align="left">Report Archives [RP-105]</td>
			<td  align="right">
				<%String screenNumber="RP-105";%>
				<A href="javaScript:goToPrepScreenHelp();"  class="<%=UIWidgetConstants.STYLE_HELP_ICON_ENABLED%>" title="Help"></A>
			</td>			
		</tr>
</table>
	<table class="buttonstable">
		<tr>
		
			<td>&nbsp;<span class="textlabellarge">Report Name: </span><span class="textlabelmedium"><c:out value="${report.reportDesc}"/></span></td>
		<tr>
		<tr>
			<td>&nbsp;<span class="textlabellarge">Report ID: </span><span class="textlabelmedium"><c:out value="${report.reportName}" /></span></td>
		<tr>
		
	</table>
<%--------------------------------- ERROR MESSAGE TABLE ------------------------------------%>
<table class="errortable">		
		<tr>
			<td width="100%"><font class="txtRed"><c:if test="${not empty systemerror }"><li><c:out value="${systemerror}"  escapeXml="false"/></li></c:if></font>
				<font class="txtGreen"><c:if test="${not empty systemmessage }"><li><c:out value="${systemmessage}" /></li></c:if></font>
			</td>
		</tr>
</table>
   <table>
		<tr>
		  <td width="2px" valign="top">&nbsp;</td>
		  <td valign="top">
		  	<%
if( request.getAttribute("errors")!=null){
   
   Map<String,Object> ae = (Map<String,Object>) request.getAttribute("errors");
%>
			 <strong>The following error(s) occurred:</strong>
		 		<ul>
	    			<c:forEach items="${ae.entrySet()}" var="error" >
						<font color="red" style="bold"><li><spring:message  code="${error.getKey()}" arguments="${error.getValues()[0]}" /></li></font>
					</c:forEach>
 				</ul>
 				<% }%>>
          </td>
		</tr>
</table>
 <table class="buttonstable">
   <tr>
     <td align="right">
		<prep:uiWidget name="" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_REFRESH %>" id="<%=UIWidgetConstants.ID_REFRESH %>" title="<%= UIWidgetConstants.LABEL_REFRESH %>"> 
			onClick="javascript:refreshData();"
		</prep:uiWidget>&nbsp;
		<prep:uiWidget name="" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_BACK %>" id="<%=UIWidgetConstants.ID_BACK %>" title="<%= UIWidgetConstants.LABEL_BACK %>"> 
			onClick="javascript:goBack();"
		</prep:uiWidget>&nbsp;
	 </td>
	</tr>
 </table>
 <table class="buttonstable">
 <tr>
 <td>
<%@ include file = "/views/common/navigationIncludeWithNoBSC.jsp"%>
		</td>
	</tr>
</table>
<table class="contenttable">
	<tr>
		<td>
		<table class="contenttable alternatecolors" border="0" cellpadding="2" cellspacing="2" id="mainData">
		  <%String reportName =((Report)session.getAttribute("report")).getReportName();
                String objectName= "";//ProtectedResourcesConstants.PREP_RE_ROOT + "/RP-100" + reportName;
               %> 
			<thead>
			 <tr class="tablerowheader">
                <th width="5%">Instance ID</th>
                <th width="15%">User Id</th>
                <th width="20%">Input Parameters</th>
                <th width="15%">Req Date</th>
                <th width="15%">Run Date</th>
                <th width="10%">Status</th>
				<th width="15%" align="center" colspan=3>Actions</th>
              </tr>
              </thead>
			 <tbody>
			
		    <c:if test="${not empty report.statusList}">
		  
		    <c:forEach  var="statusList" items="${report.statusList}" varStatus="index">
		     <% pageContext.setAttribute("status", PrepConstants.REPORT_STATUS_SUCCESS,PageContext.PAGE_SCOPE); 
			     %>
                <tr>
		 		<td class="txtBlk" align="center"><b><c:out value="${statusList.instanceID}"/></b></td>
		 		<td class="txtBlk" align="center"><c:out value="${statusList.runBy}"/></td>
                <td class="txtBlk" align="center"><c:out value="${statusList.inputParams}"/></td>
                <td class="txtBlk" align="center"><c:out value="${statusList.requestedDate}" />&nbsp;</td>
                <td class="txtBlk" align="center"><c:out value="${statusList.endDate}"/></td>
                <td width="11%" align="center" class="txtBlk"><c:out value="${statusList.status}"/></td>
                <td align="center">
                <c:if test="${statusList.status eq status }">
						<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_RP_100_REPORT_CRYSTAL_VIEW%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_ACTIVATE %>" id="<%=UIWidgetConstants.ID_ACTIVATE %>" title="<%= UIWidgetConstants.LABEL_ACTIVATE %>"> 
							onClick="javascript:returnReportInstance('<c:out value="${statusList.instanceID }"/>');"
						</prep:uiWidget>&nbsp;
                </c:if>
                </td>
                <% pageContext.setAttribute("statusPending", PrepConstants.REPORT_STATUS_PENDING,PageContext.PAGE_SCOPE); %>
                <c:if test="${ statusList.status eq statusPending}">
   				<td width="10%" align="center">
   				</td>
   				<td width="10%" align="center">
   				</td>
   				<td width="10%" align="center">
   				 <prep:secure name="<%= objectName %>" type="<%= SecurityConstants.EXECUTE_REPORT_TYPE %>"> 
					<prep:uiWidget name="" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_CANCEL %>" id="<%=UIWidgetConstants.ID_CANCEL %>" title="<%= UIWidgetConstants.LABEL_CANCEL %>"> 
						onClick="javascript:cancelReportInstance('<c:out value="${statusList.instanceID}"/>');"
					</prep:uiWidget>
				 </prep:secure> 
				</td>
                </c:if>
              </tr>
              </c:forEach>	
			</c:if>
			</tbody>
			  <c:if test="${empty report.statusList}">
			</c:if> 
           </table>

 <table class="buttonstable">
 <tr>
 <td>
<%@ include file = "/views/common/navigationIncludeWithNoBSC.jsp"%>
		</td>
	</tr>
</table>
 <table class="buttonstable">
   <tr>
     <td align="right">
		<prep:uiWidget name="" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_REFRESH %>" id="<%=UIWidgetConstants.ID_REFRESH %>" title="<%= UIWidgetConstants.LABEL_REFRESH %>"> 
			onClick="javascript:refreshData();"
		</prep:uiWidget>&nbsp;
		<prep:uiWidget name="" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_BACK %>" id="<%=UIWidgetConstants.ID_BACK %>" title="<%= UIWidgetConstants.LABEL_BACK %>"> 
			onClick="javascript:goBack();"
		</prep:uiWidget>&nbsp;
	 </td>
	</tr>
 </table>
</body>
</form:form>
</html>

