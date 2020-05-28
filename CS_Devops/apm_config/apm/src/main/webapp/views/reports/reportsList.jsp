<!DOCTYPE HTML>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>

 <%-- <jsp:useBean id="reportListSearch"  scope="session"	type="com.ascap.apm.vob.reports.ReportListSearch" /> --%>
<%-- <%@ jsp:setProperty property="*" name="reportListSearch" %> --%>
<html>
<HEAD>

<style>
a {
	text-decoration: none;
}
</style>


<%@ include file="/views/common/uiWorkMatchWidges.jsp"%>
<script type="text/javascript">
$(function() {	  
		sortTable('RP_100','mainData');
		//filterTable('RP_100','mainData');
}); 
</script>
																																																																																																																																																																																																																																																																																																																																																			
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="com.ascap.apm.vob.reports.Report, java.util.*,com.ascap.apm.vob.reports.ReportListSearch"%>
<%@ page import="com.ascap.apm.service.*"%>
<%@ page import="com.ascap.apm.common.utils.constants.Constants"%>
<%@ page
	import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants"%>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants"%>
<%@ page import="com.ascap.apm.common.utils.constants.PrepConstants"%>
<%@ page import="com.ascap.apm.vob.BaseSearchVOB"%>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<link href="<%=request.getContextPath()%>/css/stylesheet.css"
	rel="stylesheet" type="text/css">

<TITLE>APM</TITLE>

<%
ReportListSearch  report= (ReportListSearch)pageContext.getAttribute("reportListSearch",PageContext.SESSION_SCOPE);  %>
<script language="javascript">
function submitForm(reportName) {	
	 var reqAction = '<%=(String) com.ascap.apm.common.utils.constants.PrepConstants.REPORT_SHOWINPUTS%>'
	 document.forms[0].action = "apmreport.do?reportName=" + reportName + "&doAction=" + reqAction;
	 document.forms[0].submit();
}

function viewInstances(reportName, description) {
    
    //fix for defect #3287; escaping the & symbol
    var newReportDesc = description.replace("&", "%26");
    //alert(newReportDesc);

    
	var reqAction = '<%=(String) com.ascap.apm.common.utils.constants.PrepConstants.REPORT_VIEW_REPORT_INSTANCE%>';
		//document.forms[0].selectedReportID.value =reportName;
		document.forms[0].action = "apmreport.do?reportName=" + reportName
				+ "&doAction=" + reqAction + "&description=" + newReportDesc;
		document.forms[0].description = description;
		document.forms[0].submit();
	}

	var comments = new Array();

	function displayComments(srNo) {
	}

	function setNavigationType(val1) {
		document.forms[0].action = "report.do"
		document.forms[0].navigationType.value = val1;
		//	document.ReportListSearchForm.operationFlag.value = "SEARCH_MODE";
		document.forms[0].submit();
	}
</script>

<script language="javascript">
	function resetPageNumber(val) {
		if (document.forms[0].pageNumber.length != undefined) {
			for (var i = 0; i < document.forms[0].pageNumber.length; i++) {
				document.forms[0].pageNumber[i].value = val;
			}
		}
	}

	function setPageNumber() {
		if (document.forms[0].pageNumber.length == undefined) {
			pageNumber = document.forms[0].pageNumber.value;
		} else {
			pageNumber = document.forms[0].pageNumber[0].value;
		}
		setNavigationType(pageNumber);
	}
</script>


</HEAD>

<body>
	<%-- onKeyPress="return submitThruEnter();" leftmargin="0" rightmargin="0"> --%>


	<div class="menubg" style="width: 100%; border: 0px; height: 90px;">
		<jsp:include page="/views/menu.jsp" />


	</div>

	<div></div>




	<form:form action="/apmreport" modelAttribute="reportListSearch">

		<form:hidden path="operationFlag" />
		<form:hidden path="navigationType" />


		<%--------------------------------- TITLE TABLE ------------------------------------%>
		<table class="titletable">
			<tr>
				<td align="left">Main Reports Search Screen [RP-100]</td>
				<td align="right">
					<%
						String screenNumber = "RP-100";
					%> <%-- <%@ include file = "/common/goToPrepRoboHelp.jsp"%> --%>
					<A href="javaScript:goToPrepScreenHelp();"
					class="<%=UIWidgetConstants.STYLE_HELP_ICON_ENABLED%>" title="Help"></A>
				</td>
			</tr>
		</table>

		<%--------------------------------- ERROR MESSAGE TABLE ------------------------------------%>
		<table class="errortable">
			<tr>
				<td width="100%"><font class="txtRed"><c:out
							value="${systemerror}" /> </font> <font class="txtGreen"> <c:out
							value="${systemmessage}" /></font></td>
			</tr>
		</table>

		<%@ include file="/views/common/coPageFilters.jsp"%>
		<%-- include file = "/common/navigationIncludeWithNoBSC.jsp" TOP INCLUDE START --%>


		<%
			try {
					boolean backToSearchButtonNeeded = false;
		%>
		<form:hidden path="showBackToSearchButton" value="N" />
		<%
			try {
						BaseSearchVOB searchVOB = new BaseSearchVOB();
						int currentPage = 0;
						int totalPages = 0;
						int numberOfRecordsFound = 0;

						searchVOB = (BaseSearchVOB) session.getAttribute("reportListSearch");
						currentPage = searchVOB.getCurrentPageNumber();
						totalPages = searchVOB.getTotalPages();
						numberOfRecordsFound = searchVOB.getNumberOfRecordsFound();
		%>



		<table class="buttonstable" cellspacing="0" cellpadding="0">
			<tr class="txtBlk_leading">
				<td align="left">Found&nbsp;<%=numberOfRecordsFound%>
					result(s). <%
					if (numberOfRecordsFound != 0) {
				%> Page: <%=currentPage%>
					of <%=totalPages%>&nbsp; <%
 	}
 %> <%
 	if (backToSearchButtonNeeded == false && numberOfRecordsFound == 0) {
 					//nothign
 				} else {
 %> <%
 	if (backToSearchButtonNeeded == true) {
 %> &nbsp;<prep:uiWidget
						type="<%=com.ascap.apm.common.utils.constants.SecurityConstants.ANCHOR_TYPE%>"
						label="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_BACK2SEARCH%>"
						id="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_BACK2SEARCH%>">onclick="javascript:setNavigationType('BACK2SEARCH');"</prep:uiWidget>
					<%
						} else {
					%> &nbsp; <%
 	}
 %>
				</td>
				<td align="right">
					<%
						if (totalPages > 1) {
					%> Page #&nbsp; <input type="text"
					name="pageNumber" size="1" maxlength="5" value="<%=currentPage%>"
					onkeyup="javascript:resetPageNumber(this.value);" /> <prep:uiWidget
						type="<%=com.ascap.apm.common.utils.constants.SecurityConstants.ICON_TYPE%>"
						title="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_NAV_GO%>"
						id="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_NAV_GO%>">onclick="javascript:setPageNumber();"</prep:uiWidget>
					<%
						}
					%>&nbsp;&nbsp; <%
 	if (numberOfRecordsFound != 0) {
 %> <%
 	if (currentPage != 1) {
 %>
					<prep:uiWidget
						type="<%=com.ascap.apm.common.utils.constants.SecurityConstants.ICON_TYPE%>"
						title="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_NAV_FIRST%>"
						id="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_NAV_FIRST%>">onclick="javascript:setNavigationType('FIRST');"</prep:uiWidget>
					<prep:uiWidget
						type="<%=com.ascap.apm.common.utils.constants.SecurityConstants.ICON_TYPE%>"
						title="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_NAV_PREV%>"
						id="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_NAV_PREV%>">onclick="javascript:setNavigationType('PREV');"</prep:uiWidget>
					<%
						}
					%> <%
 	if (currentPage != totalPages) {
 %> <prep:uiWidget
						type="<%=com.ascap.apm.common.utils.constants.SecurityConstants.ICON_TYPE%>"
						title="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_NAV_NEXT%>"
						id="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_NAV_NEXT%>">onclick="javascript:setNavigationType('NEXT');"</prep:uiWidget>
					<prep:uiWidget
						type="<%=com.ascap.apm.common.utils.constants.SecurityConstants.ICON_TYPE%>"
						title="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_NAV_LAST%>"
						id="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_NAV_LAST%>">onclick="javascript:setNavigationType('LAST');"</prep:uiWidget>
					<%
						}
					%> <%
 	}
 %>
				
			</tr>
			<%
				}
			%>
		</table>
		<%
			} catch (Exception e) {
		%>
	Error in navigationButtonsInclude
<%
			}
		%>

		<%
			} catch (Exception e) {
		%>	
	Error in navigationIncludeWithNoBSC 
<%
			}
		%>
		<%-- include file = "/common/navigationIncludeWithNoBSC.jsp"  TOP INCLUDE END --%>

		<table class="contenttable alternatecolors" border="0" cellpadding="2"
			cellspacing="2" id="mainData">
			<thead>
				<tr class="tablerowheader">
					<th width="80%" align="left">Report Name</th>
					<th width="20%" align="left">Report Type</th>
				</tr>
			</thead>

			<form:hidden path="selectedReportID" />
			<form:hidden path="actionSubmit" />
			<tbody>

				<%-- <logic:present property="reports" name="reportListSearchForm" scope="session">
 --%>
				<c:if test="${not empty reports }">
					<%
						int rowIndex = 0;
					%>

					<%-- <logic:iterate id="report" name="reportListSearchForm" property="reports" indexId="indexId"> --%>
					<c:forEach var="report" items="${reports}" varStatus="indexId">


						<%
							String reportName = ((Report) ((ReportListSearch) session.getAttribute("reportListSearch")).getReports()
												.get((Integer) pageContext.getAttribute("indexId"))).getReportName();
										//System.out.println("reportName"+ reportName);
										String objectName = "";//ProtectedResourcesConstants.PREP_RE_ROOT + "/RP-100" + reportName;
										//System.out.println("objectName::"+ objectName);
						%>

						<%-- <prep:hasPermission name="<%= objectName %>" type="<%=SecurityConstants.SHOW_REPORT_TYPE%>" value="Y">--%>
						<%
							rowIndex++;
						%>
						<%--</prep:hasPermission>--%>

						<%-- <prep:secure name="<%= objectName %>" type="<%=SecurityConstants.SHOW_REPORT_TYPE%>">--%>
						<tr style="line-height: 20px">
							<td class="txtBlk" width="50%">
								<%
									String reportDescription = ((Report) ((ReportListSearch) session.getAttribute("reportListSearch"))
														.getReports().get((Integer) pageContext.getAttribute("indexId"))).getDescription();
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
								%> <%-- <prep:secure name="<%= objectName %>" type="<%= ${SecurityConstants.READ_REPORT_TYPE %>">--%>
								<a
								href="javascript:viewInstances('<c:out value="${reportName}" />','<%= sb.toString() %>');"
								class="txtBlk"> <c:out value="${reportName}"></c:out>&nbsp;<c:out
										value="${description}"></c:out>
							</a> <%--</prep:secure>--%>
							</td>
							<td class="txtBlk"><c:out value="${reportType}" /></td>
						</tr>
						<%-- </prep:secure>--%>
					</c:forEach>
				</c:if>
			</tbody>
		</table>

		<%-- include file = "/common/navigationIncludeWithNoBSC.jsp" BOTTOM INCLUDE START --%>


		<%
			try {
					boolean backToSearchButtonNeeded = false;
		%>

		<form:hidden path="showBackToSearchButton" value="N" />
		<%
			try {
						BaseSearchVOB searchVOB = new BaseSearchVOB();
						int currentPage = 0;
						int totalPages = 0;
						int numberOfRecordsFound = 0;

						searchVOB = (BaseSearchVOB) session.getAttribute("reportListSearch");
						currentPage = searchVOB.getCurrentPageNumber();
						totalPages = searchVOB.getTotalPages();
						numberOfRecordsFound = searchVOB.getNumberOfRecordsFound();
		%>



		<table class="buttonstable" cellspacing="0" cellpadding="0">
			<tr class="txtBlk_leading">
				<td align="left">Found&nbsp;<%=numberOfRecordsFound%>
					result(s). <%
					if (numberOfRecordsFound != 0) {
				%> Page: <%=currentPage%>
					of <%=totalPages%>&nbsp; <%
 	}
 %> <%
 	if (backToSearchButtonNeeded == false && numberOfRecordsFound == 0) {
 					//nothign
 				} else {
 %> <%
 	if (backToSearchButtonNeeded == true) {
 %> &nbsp;<prep:uiWidget
						type="<%=com.ascap.apm.common.utils.constants.SecurityConstants.ANCHOR_TYPE%>"
						label="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_BACK2SEARCH%>"
						id="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_BACK2SEARCH%>">onclick="javascript:setNavigationType('BACK2SEARCH');"</prep:uiWidget>
					<%
						} else {
					%> &nbsp; <%
 	}
 %>
				</td>
				<td align="right">
					<%
						if (totalPages > 1) {
					%> Page #&nbsp; <input type="text"
					name="pageNumber" size="1" maxlength="5" value="<%=currentPage%>"
					onkeyup="javascript:resetPageNumber(this.value);" /> <prep:uiWidget
						type="<%=com.ascap.apm.common.utils.constants.SecurityConstants.ICON_TYPE%>"
						title="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_NAV_GO%>"
						id="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_NAV_GO%>">onclick="javascript:setPageNumber();"</prep:uiWidget>
					<%
						}
					%>&nbsp;&nbsp; <%
 	if (numberOfRecordsFound != 0) {
 %> <%
 	if (currentPage != 1) {
 %>
					<prep:uiWidget
						type="<%=com.ascap.apm.common.utils.constants.SecurityConstants.ICON_TYPE%>"
						title="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_NAV_FIRST%>"
						id="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_NAV_FIRST%>">onclick="javascript:setNavigationType('FIRST');"</prep:uiWidget>
					<prep:uiWidget
						type="<%=com.ascap.apm.common.utils.constants.SecurityConstants.ICON_TYPE%>"
						title="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_NAV_PREV%>"
						id="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_NAV_PREV%>">onclick="javascript:setNavigationType('PREV');"</prep:uiWidget>
					<%
						}
					%> <%
 	if (currentPage != totalPages) {
 %> <prep:uiWidget
						type="<%=com.ascap.apm.common.utils.constants.SecurityConstants.ICON_TYPE%>"
						title="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_NAV_NEXT%>"
						id="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_NAV_NEXT%>">onclick="javascript:setNavigationType('NEXT');"</prep:uiWidget>
					<prep:uiWidget
						type="<%=com.ascap.apm.common.utils.constants.SecurityConstants.ICON_TYPE%>"
						title="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_NAV_LAST%>"
						id="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_NAV_LAST%>">onclick="javascript:setNavigationType('LAST');"</prep:uiWidget>
					<%
						}
					%> <%
 	}
 %>
				
			</tr>
			<%
				}
			%>
		</table>
		<%
			} catch (Exception e) {
		%>
	Error in navigationButtonsInclude
<%
			}
		%>

		<%
			} catch (Exception e) {
		%>	
	Error in navigationIncludeWithNoBSC 
<%
			}
		%>
		<%-- include file = "/common/navigationIncludeWithNoBSC.jsp"  BOTTOM INCLUDE END --%>
		<%--
Note: reports return results does not follow the prep search results standard
--%>
	</form:form>
</body>
</html>
