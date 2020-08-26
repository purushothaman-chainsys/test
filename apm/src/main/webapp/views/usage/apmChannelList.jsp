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
<% 	com.ascap.apm.vob.usage.ApmChannelList listForm = (com.ascap.apm.vob.usage.ApmChannelList) request.getAttribute("apmChannelList");
java.util.List<java.lang.Object> csupp =  new java.util.ArrayList<java.lang.Object>();

if(listForm != null) { 
	if(listForm.getChannelSuppliers() != null) {
	  	if( listForm.getChannelSuppliers().size() > 0) {
			csupp = listForm.getChannelSuppliers();
		}
	}
}
pageContext.setAttribute("csupp", csupp, PageContext.PAGE_SCOPE);
%>

<html>
<head><title>APM</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 

<%@ include file= "/views/common/uiWorkMatchWidges.jsp"%>
<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css">

<script type="text/javascript">

$(function(){ 
	$('#mainData tbody tr').css('line-height','1.7em');	
	//sortTable('CH-100','mainData', [0,1,2,3,4,5]);
	$('.noDblClick').dblclick(function(e){e.preventDefault();
});
var tempSuppFilter = '<%=listForm.getFilterSupplierCode()%>';
$("#filterSupplier option").each(function() {
    $(this).val($(this).text());
    if($(this).text() == tempSuppFilter) {
    	$(this).attr('selected', 'selected');            
  	}
});
});

function refresh() {
	document.forms[0].operationType.value='';
	document.forms[0].submit();
}

function update(thisObj) {
	var errMessage = '<span class=txtRed>';
	var rowsSelected = false;
	$('input[name=selectedIds]').each(function(i){
		var rval = $('input[name=channelType_'+i+']:checked').val();		
		var oval =  $("input[name=originalChannelType]:eq("+i+")").val();
		if(rval != oval) {
			$("input[name=selectedIndex]:eq("+i+")").attr('checked', true);
			$("input[name=newChannelType]:eq("+i+")").val(rval);
			rowsSelected = true;
		}
	});	
	
	if(!rowsSelected) {
		errMessage += '<li><spring:message code="us.error.apm.channels.update.selection"/>';
		$("#serverErrorMessages").html("");
		$("#uierror").html(''+errMessage+'</span>');
		unCheckSub(thisObj);
		return;
	}
	else {	
		document.forms[0].operationType.value='UPDATE';
		document.forms[0].submit();
	}	
}

function assignAsNC(cnt) {
	if(cnt == '') return;
	var newDiv = $(document.createElement('div')); 
	var txt = cnt +' unassigned channels will be set to Non Classical.  Do you want to continue?';      	
	       			       	
	newDiv.html(txt);
	newDiv.attr('title','Assign?');  
		newDiv.dialog({
			resizable: false,
			height:'auto',  
			minHeight: 'auto',
			modal: true, 
			show: "slide",
			hide: "slide", 
			buttons: {
				Cancel: function() {
					$( this ).dialog( "close" );
					},		 					
				"OK": function() {
					$( this ).dialog( "close" );
					document.forms[0].operationType.value='ASSIGN';
					document.forms[0].submit();
				}
			}
	});
	
}

function filter() {	

	var suppFilter = $('select[name=filterSupplierCode]').val();
	if(suppFilter === '') {
		$("#errorMessages").html("");
		$("#serverErrorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><spring:message code="error.usage.performanceSearch.invalid.supplierCode"/></span>');
		
	 	location.href="#"; 
	 	return;
	}
	else {
		document.forms[0].operationType.value='LIST';
		document.forms[0].submit();		
	}
	
}

</script>


</head>
<body> 


<div class="menubg"  style="width: 100%; border: 0px; height:90px;"> 
<%@ include file = "/views/menu.jsp"%>
</div>
<div></div>
 
 
 
<table class="titletable">
<tr>
	<td>Channel List [CH-100]</td>
</tr>
 </table>
 
 <table class="errortable" style="font-size:1.2em;width:1000px;margin:0 auto;border:0px;align:left;">
		<tr>
			<td>
				<div id="serverErrorMessages" style="align:left;">
				<span class="txtGreen" style="text-align:left;"><c:if test="${not empty systemmessage }"><li> <c:out value="${systemmessage}"/></li></c:if></span>
				<span class="txtRed" style="text-align:left;"><c:if test="${not empty systemerror }"><li><c:out value="${systemerror}" escapeXml="false"/></li></c:if></span>
				</div>
				<div id="uierror"  style="text-align:left;"></div>
				<div id="errorMessages" style="text-align:left;"></div>
			</td>
		</tr>
</table>

<form:form action="channelList" modelAttribute="apmChannelList">
<form:hidden path="operationType"/>
<table class="detailstable1000" style="width:1000px; font-size: 11px; font-weight: bold; color: #000000; line-height: 11px; margin:0 auto;"> 
	<tr>
		<td width="25%">Supplier<br>
				
		<form:select path="filterSupplierCode" id="filterSupplier">
		<form:option value=""></form:option>
		<form:options items="${csupp}"/>
		</form:select>
		</td>	
		<td width="25%">Channel Type<br><form:select path="filterChannelType">
			<form:option value="">All</form:option>
			<form:option value="CL">Classical</form:option>
			<form:option value="NC">Non Classical</form:option>
			<form:option value="NM">Non Musical</form:option>
			<form:option value="UA">Unassigned</form:option>
			</form:select>
		</td>
		<td width="50%">
		<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_CH_100_CHANNEL_SEARCH %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_APPLY_FILTER %>" id="<%=UIWidgetConstants.ID_APPLY_FILTER%>" title="Filter">onclick="javascript:filter();"</prep:uiWidget>
		</td>		
	</tr>
</table>		
<br>

<table class="detailstable1000" style="width:1000px; font-size: 1.2em; font-weight: bold; color: #000000;  margin:0 auto;">
<tr>
<td width="15%" align="left">
Unassigned Channels: 
</td>
<td width="5%" align="right" style="color:red"><c:out value="${apmChannelList.countUnassigned}"> </c:out></td>
<td width="80%" align="left">&nbsp;&nbsp;&nbsp;
<c:if test = "${not empty apmChannelList.countUnassigned }">
<c:if test ="${apmChannelList.countUnassigned ne 0 }">
<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_CH_100_CHANNEL_UPDATE%>" value="Y">
<a href="javascript:void(0)" style="color:#2445AE;font-size:1em;color:blue;font-weight:normal;" onclick="javascript:assignAsNC('<c:out value ="${apmChannelList.countUnassigned}"/>')">Assign As Non Classical</a>
</prep:hasPermission>
<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_CH_100_CHANNEL_UPDATE%>" value="N">
</prep:hasPermission>
</c:if>
</c:if>
</td>
</tr>

<tr>
<td width="15%" align="left">
Non Classical Channels:	
</td>
<td width="5%" align="right" style="font-weight:normal;"> <c:out value="${apmChannelList.countNonClassical}"></c:out> </td>
<td width="80%" align="left"></td>
</tr>

<tr>
<td width="15%" align="left">
Classical Channels:	
</td>
<td width="5%" align="right" style="font-weight:normal;"><c:out value="${apmChannelList.countClassical}"></c:out></td>
<td width="80%" align="left"></td>
</tr>

<tr>
<td width="15%" align="left">
Non Musical Channels: 
</td>
<td width="5%" align="right" style="font-weight:normal;"> <c:out value="${apmChannelList.countNonMusical}"></c:out></td>
<td width="80%" align="left"></td>
</tr>
</table>

<table class="buttonstable" style="width:1000px;margin:0 auto;">
 <tr>
 <td align="left" width="20%">
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_CH_100_CHANNEL_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_UPDATE %>" id="<%=UIWidgetConstants.ID_UPDATE%>" title="Update">onclick="javascript:checkSub(this);update(this);"</prep:uiWidget>
 </td>	
</tr>
 </table>
 
<table class="contenttable alternatecolors" id="mainData" style="font-size:1.3em;width:1000px;margin:0 auto;border:0px #cccccc solid;" >     
<thead> 
<tr class="tablerowheader" style="line-height: 2.5em;" >    
	<%-- <th width="15%">Supplier</th>--%>
	<th width="12%">Channel</th> 
	<th width="13%">Description</th> 
	<th width="10%">Non Classical</th>
	<th width="10%">Classical</th>
	<th width="10%">Non Musical</th>  
	<th width="10%">Unassigned</th>  
</tr>
</thead>

<tbody>
<c:if test="${not empty apmChannelList.searchResults}">
<c:forEach  var ="apmChannel" items="${apmChannelList.searchResults}" varStatus="currentIndexId" >
<c:choose>
<c:when test="${currentIndexId.index % 2 == 0}" >		
<tr class="o20" style="line-height:1.7em;">
</c:when>
<c:otherwise> 
<tr style="line-height:1.7em;"/>
 </c:otherwise>	
 </c:choose>

<td align="left"><c:out value="${apmChannel.channelName}"></c:out>
<input type="hidden" name="selectedIds" value='<c:out value="${apmChannel.channelId}"></c:out>'/>
<form:checkbox path="selectedIndex" style="display:none" value="${currentIndexId.index}"></form:checkbox>
<input type="hidden" name="originalChannelType" value='<c:out value="${apmChannel.channelType}"/>'/>
<form:hidden  path="newChannelType" value=""/>
</td>

<td align="left" > <c:out value="${apmChannel.channelDescription}"/></td>

<td>
<c:if test="${apmChannel.channelType eq 'NC'}">
<input type="radio" name="channelType_${currentIndexId.index}" checked="checked" value="NC"/>
</c:if>
<c:if test="${apmChannel.channelType ne 'NC'}">
<input type="radio" name="channelType_${currentIndexId.index}" value="NC"/>
</c:if>
</td>

<td>
<c:if test="${apmChannel.channelType eq'CL'}">
<input type="radio" name="channelType_${currentIndexId.index}" checked="checked" value="CL"/>
</c:if>
<c:if test="${apmChannel.channelType ne'CL'}">
<input type="radio" name="channelType_${currentIndexId.index}" value="CL"/>
</c:if>
</td>

<td>

<c:if test="${apmChannel.channelType eq'NM'}">
<input type="radio" name="channelType_${currentIndexId.index}" checked="checked" value="NM"/>
</c:if>

<c:if test="${apmChannel.channelType ne'NM'}">

<input type="radio" name="channelType_${currentIndexId.index}" value="NM"/>
</c:if>
</td>
<td>
<c:if test="${apmChannel.channelType eq'UA'}">
<input type="radio" name="channelType_${currentIndexId.index}" checked="checked" value="UA"/>
</c:if>
<c:if test="${apmChannel.channelType ne'UA'}">
<input type="radio" name="channelType_${currentIndexId.index}" value="UA"/>
</c:if>
</td>
</tr>
</c:forEach>
</c:if>
</tbody>
</table>

<table class="buttonstable" style="width:1000px;margin:0 auto;">
 <tr>
 <td align="left" width="20%">
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_CH_100_CHANNEL_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_UPDATE %>" id="<%=UIWidgetConstants.ID_UPDATE%>" title="Update">onclick="javascript:update(this);"</prep:uiWidget>
 </td>	
</tr>
 </table>
</form:form>
</body>
</html>