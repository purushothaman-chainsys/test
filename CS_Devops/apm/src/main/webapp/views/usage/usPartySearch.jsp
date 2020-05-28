<%-- usPartySearch.jsp --%>
<%@ page language="java" contentType="text/html" 
import="com.ascap.apm.controller.utils.HtmlSelectOption"
import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants"
import="com.ascap.apm.common.utils.constants.SecurityConstants"
import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>

<%  
 pageContext.setAttribute("MusicUserTypesList", HtmlSelectOption.getLookUpTable("MusicUserTypes"), PageContext.PAGE_SCOPE);
 pageContext.setAttribute("LicenseTypesList", HtmlSelectOption.getLookUpTable("LicenseTypes"), PageContext.PAGE_SCOPE); 
 pageContext.setAttribute("CallLetterSuffixList", HtmlSelectOption.getLookUpTable("CallLetterSuffix"), PageContext.PAGE_SCOPE); 
%>

<html>
<head>
<%@ include file = "/views/common/uiWidgets.jsp"%>

<title>APM</title>
<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css">


<script language="JavaScript">

$(function() {
		$('.contenttable').css("width","60%").css('float','left');
		$('.detailstable').css("width","60%").css('float','left');
		sortTable('US-900','mainData', [0]);
		filterTable('US-900','mainData' , [0]);
}); 

function setNavigationType(navigation) {
		document.forms[0].navigationType.value = navigation;
		document.forms[0].submit();
}
  function temp(val1, val2){
	
      this.location.replace('/PrepWeb/usage/searchParty.do?navigationType=NEW_SEARCH&partySearchType=<%= request.getParameter("partySearchType")%>&mode=<%= request.getParameter("mode")%>&rowNum=' + val1+'&musicUserSearch='+val2);
  }

  function submitAction(formObj){
  	formObj.operationType.value='SEARCH';
  	formObj.submit();
  }
  
  function populateMusicUser(partyId) {
  	opener.document.forms[0].musicUserId.value = partyId;
  	window.close();
  }

</script>

</head>

<body> 

<form:form action="musicUserSearch" modelAttribute="musicUserSearch">
<form:hidden path="operationType"/>
<form:hidden path="navigationType" />

<table class="titletable">
	<tr>
		<td valign="top">Music User Search [MU-100]</td>
	</tr>
</table>


 <table class="errortable">
		<tr>
			<td>
				<div id="serverErrorMessages">
				<span class="txtGreen"><c:if test="${not empty systemmessage }"><li> <c:out value="${systemmessage}"/></li></c:if></span>
				<span class="txtRed"><c:if test="${not empty systemerror }"><li><c:out value="${systemerror}"  escapeXml="false"/></li></c:if></span>
				</div>
				<div id="uierror"></div>
				<div id="errorMessages"></div>
			</td>
		</tr>
</table>

<table class="detailstable">
<tr>
	<td>Music User Type<br>
						<form:select path="filterMusicUserType" style="width:200px;">
							<form:option value="" />
							<form:options items="${MusicUserTypesList}" itemLabel="displayName" itemValue="id"/>
						</form:select></td>
	<td>License Type<br>
	<form:select path="filterLicenseType" style="width:100px;">
							<form:option value="" />
							<form:options items="${LicenseTypesList}" itemLabel="displayName" itemValue="id"/>
						</form:select></td>	
	<td>Call Letter<br><form:input path="filterCallLetter" size="10"/></td>
	<td>Call Letter Suffix<br>
	<form:select path="filterCallLetterSuffix" style="width:100px;">
							<form:option value="" />
							<form:options items="${CallLetterSuffixList}" itemLabel="displayName" itemValue="id"/>
						</form:select></td>
	<td>Effective Date<br><form:input path="filterEffectiveDate" size="10" readonly="true" id="datepicker_filterEffectiveDate"/></td>					
	<td><prep:uiWidget type="<%=SecurityConstants.ANCHOR_TYPE %>"  label="<%=UIWidgetConstants.LABEL_APPLY_FILTER %>" id="<%=UIWidgetConstants.ID_APPLY_FILTER%>" title="Search">onclick="javascript:pleaseWait();javascript:submitAction(document.forms[0]);"</prep:uiWidget></td>	
</table>


<%@ include file = "/views/common/coPageFilters.jsp"%>
<%@ include file = "/views/common/navigationIncludeWithNoBSC.jsp"%> 
 
 
<table class="contenttable alternatecolors" id="mainData">
<thead>
<tr class="tablerowheader" >
	<th width="5%">Select</th>	
	<th width="10%">Music User ID</th>
	<th width="10%">Call Letter</th>
	<th width="10%">Call Letter Suffix</th>
	<th width="10%">Music User Type</th>
	<th width="10%">License Type</th>
</tr>
</thead>    
<tbody>
<c:if test="${not empty musicUserSearch.searchResults}">
<c:forEach items="${musicUserSearch.searchResults}" var="musUser" varStatus="currentIndexId">
<tr align="center">
<td><a href="JavaScript:populateMusicUser('${musUser.partyId}')" class="<%=UIWidgetConstants.STYLE_PICK_ICON_ENABLED%>"></a></td>
<td align="left"><c:out value="${musUser.partyId}"/></td>
<td align="left"><c:out value="${musUser.callLetter}"/></td>
<td align="left"><c:out value="${musUser.callLetterSuffix}"/>  </td>
<td align="left"><c:out value="${musUser.musicUserTypeCode}"/> </td>
<td align="left"><c:out value="${musUser.licenseType}"/>  </td>
</tr>
</c:forEach>
</c:if>
</tbody>
</table>	

 <table class="contenttable" cellspacing="0" cellpadding="0">
	<tr class="searchpagestatus">
		<td align="right">Results found so far <b><c:out value="${musicUserSearch.numberOfRecordsFound}" /></b></td>
	</tr>
</table>
<%@ include file = "/views/common/navigationIncludeWithNoBSC.jsp"%>
</form:form>
</body>
</html>
