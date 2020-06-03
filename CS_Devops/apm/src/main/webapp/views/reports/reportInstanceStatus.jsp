<!DOCTYPE HTML>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<HEAD>
<%@ include file = "/views/common/uiWorkMatchWidges.jsp"%> 
<script type="text/javascript">
$(function() {	  
		sortTable('RP_101' + '_' + '<%=((Report)session.getAttribute("report")).getReportName()%>','mainData', [7,8]);
		
}); 
</script>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%@ page import="java.util.*,com.ascap.apm.common.utils.constants.PrepConstants"%>
<%@ page import="com.ascap.apm.vob.reports.Report"%>

<%-- <%@ page import="com.ascap.apm.servicehelpers.common.*"%>  --%>
<%@ page import="com.ascap.apm.common.utils.constants.Constants"%>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.*"%>
<%@ page import="com.ascap.apm.common.utils.PrepUtils"%>

<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">

<link href="<%=request.getContextPath()%>/themes/stylesheet.css"
	rel="stylesheet" type="text/css">

<TITLE>APM</TITLE>
</HEAD>




<script language="javascript">
			    
function refreshData() {


        
       var reportName='<%=((Report)session.getAttribute("report")).getReportName()%>';
       var reqAction = '<%=(String)com.ascap.apm.common.utils.constants.PrepConstants.REPORT_VIEW_REPORT_INSTANCE%>'; 


    
       document.forms[0].action = "apmreport?reportName=" + reportName + "&doAction="+reqAction;
     
	   document.forms[0].submit();

}

function goHome(){
 
 
      var module = '<%=(String)session.getAttribute("module")%>';
      document.forms[0].action = "report?module=" + module ;
      document.forms[0].submit();
	
 
}

function viewReportInstance(indx,option) {

            
     var reqAction = '<%=(String)com.ascap.apm.common.utils.constants.PrepConstants.REPORT_VIEW%>';
   
  if(option=='rpt'){ 
	  window.open('<%= request.getContextPath() %>/report/apmreport?instanceId='+indx +'&export='+option+'&doAction='+reqAction,'viewer');
     
	}else{
     document.forms[0].action = "apmreport?instanceId=" + indx +"&export="+option+"&doAction="+reqAction;
     document.forms[0].submit();
   }  

}
function setNavigationType(val1){
	var reportName='<%=((Report)session.getAttribute("report")).getReportName()%>';
	var reqAction = '<%=(String)com.ascap.apm.common.utils.constants.PrepConstants.REPORT_VIEW_REPORT_INSTANCE%>'; 
    document.forms[0].action = "apmreport.do?reportName=" + reportName + "&doAction=" +reqAction;
	document.forms[0].navigationType.value = val1;
	document.forms[0].submit();
}

function executeReport(reportName, reportDesc){
    
   //fix for defect #3287; escaping the & symbol
    var newReportDesc = reportDesc.replace("&", "%26");
    //alert(newReportDesc);

    var reqAction = '<%=(String)com.ascap.apm.common.utils.constants.PrepConstants.REPORT_SHOWINPUTS%>'; 
  document.forms[0].action = "apmreport?reportName=" + reportName + "&doAction=" + reqAction + "&description=" + newReportDesc;
  document.forms[0].submit();
	
 
}

function viewReportArchives(reportName, reportDesc){

 //fix for defect #3287; escaping the & symbol
 var newReportDesc = reportDesc.replace("&", "%26");
 //alert(newReportDesc);


  var reqAction = '<%=(String)com.ascap.apm.common.utils.constants.PrepConstants.REPORT_VIEW_ARCHIVES%>'; 
  document.forms[0].action = "apmreport?reportName=" + reportName + "&doAction=" + reqAction + "&description=" + newReportDesc;
  document.forms[0].submit();
	
 
}


function cancelReportInstance(indx){
var confirm= window.confirm('Are you sure!.You want to cancel the Pending report?');
//alert(confirm);
if(confirm){
var reqAction = '<%=(String)com.ascap.apm.common.utils.constants.PrepConstants.REPORT_CANCEL_INSTANCE%>';   
document.forms[0].action = "apmreport?instanceId=" + indx +"&doAction="+reqAction;
document.forms[0].submit();
} 
 
}

function purgeReport(){

}

</script>

<body leftmargin="0" rightmargin="0">          
	
<div class="menubg"  style="width: 100%; border: 0px; height:90px;"> 
<jsp:include page="/views/menu.jsp"/>

</div>

<div></div>

<form:form action="apmreport" modelAttribute="report">    
	
	
	
	
	<form:hidden path="navigationType" />
<%--------------------------------- TITLE TABLE ------------------------------------%>
<table class="titletable">
		<tr>
			<td align="left">Run Reports [RP-101]</td>
			<td  align="right">
				<%String screenNumber="RP-101";%>
				<%--@ include file = "/common/goToPrepRoboHelp.jsp"--%>
				<A href="javaScript:goToPrepScreenHelp();"  class="<%=UIWidgetConstants.STYLE_HELP_ICON_ENABLED%>" title="Help"></A>
			</td>			
		</tr>
</table>


<table class="detailstable">
		<tr>
			<td>&nbsp;<span class="textlabellarge">Report Name: </span><span class="textlabelmedium"><c:out value="${report.reportDesc}" /></span></td>
		<tr>
		<tr>
			<td>&nbsp;<span class="textlabellarge">Report ID: </span><span class="textlabelmedium"><c:out value="${report.reportName}" /></span></td>
		<tr>
		
</table>
<%--------------------------------- ERROR MESSAGE TABLE ------------------------------------%>
<table class="errortable">		
		<tr>
			<td><font class="txtRed"><c:if test="${not empty systemerror}"><li><c:out value="${systemerror}" escapeXml="false"/></li></c:if></font>
				<font class="txtGreen"><c:if test="${not empty systemmessage}"><li><c:out value="${systemmessage}"/></li></c:if></font>
			</td>
		</tr>
</table>
	
 
 <table  class="errortable"> 
<tr> 
  <td valign="top">&nbsp;</td>
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


	
  
  
<%-- <%
	if( request.getAttribute("errors")!=null){ 
	    
		//org.apache.struts.action.ActionErrors ae = (org.apache.struts.action.ActionErrors)request.getAttribute("errors");
%>   	   
	<strong>The following error(s) occurred:</strong>
	<ul> --%>
	
	

  <%-- 	<c:forEach items="${ae.get()}" var="error" type="org.apache.struts.action.ActionMessage">
		<font color="red" style="bold"><li><spring:message  code="error.getKey()" arguments="(String)error.getValues()[0]" /></li></font>
	</c:forEach> --%>
	</ul>
<%}%>
	</td>
</tr>
</table>

<%--------------------DATA TABLE --%>
<%
String reportName =((Report)session.getAttribute("report")).getReportName();
String objectName= "";
%> 

<%
String inputParamsTemp="";
%>
		  
<table class="buttonstable">
  <tr>
    <td align="right" >
	<prep:uiWidget name="" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_VIEW_ARCHIVES %>" id="<%=UIWidgetConstants.ID_VIEW_ARCHIVES %>" title="<%= UIWidgetConstants.LABEL_VIEW_ARCHIVES %>"> 
	onClick="javascript:viewReportArchives('<c:out value="${report.reportName}"/>','<c:out value="${report.reportDesc}"/>');"
	</prep:uiWidget>
	<prep:uiWidget name="" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_REFRESH %>" id="<%=UIWidgetConstants.ID_REFRESH %>" title="<%= UIWidgetConstants.LABEL_REFRESH %>"> 
	onClick="javascript:refreshData();"
	</prep:uiWidget>
	<%-- prep:secure name="<%= objectName %>" type="<%= SecurityConstants.EXECUTE_REPORT_TYPE %>">--%>
		<c:if test="${report.adminRunnableFlag eq 'true'}">
		<% String reportDescription = ((Report)session.getAttribute("report")).getReportDesc();
					
		 
		    String source = reportDescription;
		    String pattern = "'";
		    String replace = "\\'";
		    StringBuffer sb = new StringBuffer();
		   
		final int len = pattern.length();
		
		int found = -1;
		int start = 0;
		
		while ((found = source.indexOf(pattern, start)) != -1) {
			sb.append(source.substring(start, found));
			
			sb.append(replace);
			start = found + len;
		}
		
		sb.append(source.substring(start));
		  
		  
		  %>

		<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_RP_100_REPORT_CRYSTAL_RUN%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_RUN %>" id="<%=UIWidgetConstants.ID_RUN %>" title="<%= UIWidgetConstants.LABEL_RUN %>">
		onClick="javascript:executeReport('<c:out value="${report.reportName}"/>','<%=sb.toString()%>');"
		</prep:uiWidget>
		</c:if>
	<%-- </prep:secure>--%>
	
	<prep:uiWidget name="" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_BACK %>" id="<%=UIWidgetConstants.ID_BACK %>" title="<%= UIWidgetConstants.LABEL_BACK %>"> 
	onClick="javascript:goHome();"
	</prep:uiWidget>
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
           
<table class="contenttable alternatecolors" border="0" cellpadding="2" cellspacing="2" id="mainData" >
<thead>
<tr class="tablerowheader">
	<th width="5%" >Instance ID</th>
	<th width="15%" >User Id</th>
	<th width="20%" >Input Parameters</th>
	<th width="15%" >Req Date</th>
	<th width="15%" >Run Date</th>
	<th width="11%">Status</th>
	<th width="5%">Returned Until</th>
	<th width="15%" align="center" colspan=3>Actions</th>
</tr>
</thead> 
<tbody>


<c:if test="${not empty report.statusList}">

<c:forEach var="statusList" items="${report.statusList}" varStatus="index">
<tr style="line-height:30px;">
	<td align="center"><b><c:out value="${statusList.instanceID}"/></b></td>
	<td align="center"><c:out value="${statusList.runBy}"/></td>                
	<td align="center">
	<%
	inputParamsTemp = "";
	%>
<c:if test="${not empty statusList.inputParams}">
<c:set var="inputParamsT" value="${statusList.inputParams}" />

<% inputParamsTemp =  PrepUtils.breakCSVList(pageContext.getAttribute("inputParamsT").toString()); 
%>   
</c:if>
<%=inputParamsTemp %>
	</td>
	<td align="center"><c:out value="${statusList.requestedDate}"/></td>
	<td align="center"><c:out value="${statusList.endDate}"/></td>
	<td width="11%" align="center" ><c:out value="${statusList.status}"/></td>
	<td align="center"><c:out value="${statusList.returnedEndDate}"/></td>
	
	 <%
	pageContext.setAttribute("reportStatus", PrepConstants.REPORT_STATUS_SUCCESS);
	pageContext.setAttribute("reportStatus1",PrepConstants.REPORT_STATUS_FAIL);
	pageContext.setAttribute("reportStatus2",PrepConstants.REPORT_STATUS_PENDING);
	pageContext.setAttribute("reportStatus3",PrepConstants.REPORT_STATUS_RUNNING);
	pageContext.setAttribute("reportStatus4",PrepConstants.REPORT_STATUS_UNKNOWN);
	pageContext.setAttribute("reportStatus5",PrepConstants.REPORT_STATUS_PAUSED);
	%> 
	
	<c:if test="${statusList.status ne reportStatus}">
	
	</c:if>
	<c:if test="${statusList.status eq reportStatus}">
	
	<td width="10%" align="center">
	<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_RP_100_REPORT_CRYSTAL_VIEW%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_VIEW %>" id="<%=UIWidgetConstants.ID_VIEW %>" title="<%= UIWidgetConstants.LABEL_VIEW %>"> 
			onClick="javascript:viewReportInstance('<c:out value="${statusList.instanceID}"/>','rpt');"
			</prep:uiWidget>
	
	</td>
	
	<td width="10%" align="center">
	<c:if test="${statusList.adminExportFlag eq 'true' }">
	<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_RP_100_REPORT_CRYSTAL_EXPORT%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_EXPORT %>" id="<%=UIWidgetConstants.ID_EXPORT %>" title="<%= UIWidgetConstants.LABEL_EXPORT %>"> 
				onClick="javascript:viewReportInstance('<c:out value="${statusList.instanceID}"/>','csv');"
				</prep:uiWidget>
	</c:if>			
	
	</td>
		
</c:if> 
	
	<c:if test="${statusList.status eq reportStatus1}">
	<td width="10%" align="center"></td>
	<td width="10%" align="center">
	</td>
	</c:if>
	<c:if test="${statusList.status eq reportStatus2}">
	<td width="10%" align="center"></td>
	<td width="10%" align="center"></td>
	</c:if>
	<c:if test="${statusList.status eq reportStatus3}">
	<td width="10%" align="center"></td>
	<td width="10%" align="center"></td>
	</c:if>
	<c:if test="${statusList.status eq reportStatus4}">
	<td width="10%" align="center"></td>
	<td width="10%" align="center"></td>
	</c:if>
	<c:if test="${statusList.status eq reportStatus5}">
	<td width="10%" align="center"></td>
	<td width="10%" align="center"></td>
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
    <td align="right" >
	<prep:uiWidget name="" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_VIEW_ARCHIVES %>" id="<%=UIWidgetConstants.ID_VIEW_ARCHIVES %>" title="<%= UIWidgetConstants.LABEL_VIEW_ARCHIVES %>"> 
	onClick="javascript:viewReportArchives('<c:out value="${report.reportName}"/>','<c:out value="${report.reportDesc}"/>');"
	</prep:uiWidget>
	<prep:uiWidget name="" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_REFRESH %>" id="<%=UIWidgetConstants.ID_REFRESH %>" title="<%= UIWidgetConstants.LABEL_REFRESH %>"> 
	onClick="javascript:refreshData();"
	</prep:uiWidget>
	<%-- prep:secure name="<%= objectName %>" type="<%= SecurityConstants.EXECUTE_REPORT_TYPE %>">--%>
		<c:if test="${report.adminRunnableFlag eq 'true'}">
		<% String reportDescription = ((Report)session.getAttribute("report")).getReportDesc();
					
		 
		    String source = reportDescription;
		    String pattern = "'";
		    String replace = "\\'";
		    StringBuffer sb = new StringBuffer();
		   
		final int len = pattern.length();
		
		int found = -1;
		int start = 0;
		
		while ((found = source.indexOf(pattern, start)) != -1) {
			sb.append(source.substring(start, found));
			
			sb.append(replace);
			start = found + len;
		}
		
		sb.append(source.substring(start));
		  
		  
		  %>

		<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_RP_100_REPORT_CRYSTAL_RUN%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_RUN %>" id="<%=UIWidgetConstants.ID_RUN %>" title="<%= UIWidgetConstants.LABEL_RUN %>">
		onClick="javascript:executeReport('<c:out value="${report.reportName}"/>','<%= sb.toString() %>');"
		</prep:uiWidget>
		</c:if>
	<%-- </prep:secure>--%>
	
	<prep:uiWidget name="" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_BACK %>" id="<%=UIWidgetConstants.ID_BACK %>" title="<%= UIWidgetConstants.LABEL_BACK %>"> 
	onClick="javascript:goHome();"
	</prep:uiWidget>
	</td>
</tr>
</table>	           
           
 </form:form>          
           
           

		<%-- MENU OPTIONS AND PAGINATION TABLE --%>
	
</body>

</html>
