<!DOCTYPE HTML>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants"%>

<%@ page import="com.ascap.apm.controller.utils.Utils"%>
<%@ page import="com.ascap.apm.controller.utils.HtmlSelectOption"%>
<%@ page
	import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants"%>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants"%>

<%
	pageContext.setAttribute("distirbutionYearQuarter",
			HtmlSelectOption.getOptionsValues(Utils.getNextSurveyYearQuarters(12)), PageContext.PAGE_SCOPE);
	pageContext.setAttribute("UsageSuppliersList", HtmlSelectOption.getLookUpTable("DistributionSuppliers"),
			PageContext.PAGE_SCOPE);
	pageContext.setAttribute("FileTypeList", HtmlSelectOption.getLookUpTable("FileType"),
			PageContext.PAGE_SCOPE);
	pageContext.setAttribute("FileStatusList", HtmlSelectOption.getLookUpTable("FileStatus"),
			PageContext.PAGE_SCOPE);
%>
<html>
<head>
<title>APM</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<%@ include file="/views/common/uiWidgets.jsp"%>
<script type="text/javascript">
	$(function() {
		sortTable('US-500', 'mainData', [ 0, 10 ]);
		filterTable('US-500', 'mainData', [ 0, 10 ]);
	});

	function setNavigationType(navigation) {
		//alert(navigation);
		document.forms[0].navigationType.value = navigation;
		document.forms[0].submit();
	}
	function update(obj) {
		if ($("table#mainData tr input:checked").length == 0) {
			$("#errorMessages").html("");
			$("#uierror")
					.html(
							'<span class=txtRed><li><spring:message code="error.apm.archives.update.selection"/></span>');
			unCheckSub(obj);
			location.href = "#";
			return;
		} else {
			var originalExists = false;
			$("table#mainData tr input:checked").each(function() {
				var checkedIndex = this.value;
			});
			document.forms[0].operationType.value = 'UPDATE';
			document.forms[0].submit();

		}
	}

	function submitAction(operation) {
		document.forms[0].operationType.value = operation;
		document.forms[0].submit();
	}
</script>
<link href="<%=request.getContextPath()%>/themes/stylesheet.css"
	rel="stylesheet" type="text/css">



</head>
<body>

	<div class="menubg" style="width: 100%; border: 0px; height: 90px;">
		<jsp:include page="/views/menu.jsp" />


		<%--
<iframe  style="width: 100%; border: 0px; height:90px;"   
            id="topIFrame" name="topIFrame" scrolling="no"
            src="<%=request.getContextPath()%>/menu.jsp">   
</iframe>
 --%>
	</div>
	<div></div>
	<table class="titletable">
		<tr>
			<td>File List [FL-100]</td>
		</tr>
	</table>

	<table class="errortable">
		<tr>
			<td>
				<div id="serverErrorMessages">

					<span class="txtGreen"><c:out value="${systemmessage}" /></span> <span
						class="txtRed"><c:out value="${systemerror}" /></span>
				</div>
				<div id="uierror"></div>
				<div id="errorMessages"></div>
			</td>
		</tr>
	</table>


	<form:form action="apmFiles" modelAttribute="ApmFileList">

		<form:hidden path="navigationType" />
		<form:hidden path="operationType" />

		<div>
			<div style="">

				<table class="detailstable1000"
					style="width: 1200px; font-size: 11px; font-weight: bold; color: #000000; line-height: 11px; margin: 0 auto;">

					<tr>
						<td>File Type<br> <form:select path="fileType">
								<form:option value="" />
								<form:options items="${FileTypeList}" itemLabel="displayName"
									itemValue="id" />
							</form:select>
						</td>
						<td>Status<br> <form:select path="fileStatus" size="1"
								style="width:120px;">
								<form:option value="" />
								<form:options items="${FileStatusList}" itemLabel="displayName"
									itemValue="id" />
							</form:select></td>

						<td>Supplier<br> <form:select path="supplierCode"
								size="1" style="width:120px;">
								<form:option value="" />
								<form:options items="${UsageSuppliersList}"
									itemLabel="displayName" itemValue="id" />
							</form:select></td>

						<td width="17%" valign="bottom" align="right"><prep:uiWidget
								name="<%=ProtectedResourcesConstants.APM_FL_100_SEARCH_APM_FILE%>"
								type="<%=SecurityConstants.ANCHOR_TYPE%>"
								label="<%=UIWidgetConstants.LABEL_APPLY_FILTER%>"
								id="<%=UIWidgetConstants.ID_APPLY_FILTER%>" title="Filter Files">onclick="javascript:submitAction('SEARCH_FILES');"</prep:uiWidget>
						</td>

						<td width="17%" valign="bottom" align="right"><prep:uiWidget
								name="<%=ProtectedResourcesConstants.APM_FL_100_UPLOAD_FILE%>"
								type="<%=SecurityConstants.ANCHOR_TYPE%>"
								label="<%=UIWidgetConstants.LABEL_UPLOAD%>"
								id="<%=UIWidgetConstants.ID_UPLOAD%>" title="Upload File">onclick="javascript:submitAction('UPLOAD_FILE');"</prep:uiWidget>
						</td>

					</tr>
				</table>
			</div>
		</div>


		<%@ include file="/views/common/coPageFilters.jsp"%>
		<%@ include file="/views/common/navigationIncludeWithNoBSC.jsp"%>


		<table class="contenttable alternatecolors" id="mainData">
			<thead>
				<tr class="tablerowheader">
					<th width="5%"><INPUT name="selectAllCheckBox" type="checkbox"
						value="selectAll"
						onclick="toggleSelectAll(this.checked,'selectedIds')"></th>
					<th width="5%">File Type</th>
					<th width="5%">File ID</th>
					<th width="10%">File Name</th>
					<th width="5%">Supplier Code</th>
					<th width="10%">Process Date</th>
					<th width="5%">Count</th>
					<th width="10%">Dist</th>
					<th width="5%">File Status</th>

				</tr>
			</thead>

			<tbody>
				<c:if test="${not empty apmFileList}">
					<c:forEach varStatus="currentIndexId" var="apmFile"
						items="${apmFileList.searchResults}">
						<tr align="center">
							<td><c:if test="${apmFile.fileId ne '0'}">
									<form:checkbox path="selectedIndex" value="${currentIndexId}"></form:checkbox>
								</c:if> <form:hidden name="apmFile" path="fileId"></form:hidden></td>

							<td><c:out value="${apmFile.fileType}"></c:out></td>
							<td><c:out value="${apmFile.fileId}"></c:out></td>
							<td align="left"><c:out value="${apmFile.fileName}"></c:out></td>
							<td align="left"><c:out value="${apmFile.supplierCode}"></c:out></td>
							<td><c:out value="${apmFile.processDate}"></c:out></td>
							<td align="right"><c:out value="${apmFile.detailCount}"></c:out></td>
							<td><form:input
									path="targetYearQuarter[${currentIndexId.index}]"
									id="yearquarterpicker_targetYearQuarter_${currentIndexId}"
									size="8" value="${apmFile.targetYearQuarter}" disabled="true" />
								<a
								href="javascript:clearField('<%="yearquarterpicker_targetYearQuarter_"%>${currentIndexId}');"
								class="${UIWidgetConstants.STYLE_CLEAR_FIELD_ICON_ENABLED}"
								title="${UIWidgetConstants.TITLE_CLEAR_FIELD}"></a></td>
							<td><c:out value="${apmFile.fileStatus}"></c:out></td>

						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
<table class="contenttable" cellspacing="0" cellpadding="0">
	<tr class="searchpagestatus">
		<td align="right">Results found so far ${apmFileList.numberOfRecordsFound}</td>
	</tr>
</table>
		<%@ include file="/views/common/navigationIncludeWithNoBSC.jsp"%>

	</form:form>
</body>
</html>