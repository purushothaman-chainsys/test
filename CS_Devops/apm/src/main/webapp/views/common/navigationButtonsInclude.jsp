<%@ page import="com.ascap.apm.vob.BaseSearchVOB" %>
<%@ page import="java.util.Enumeration" %>

<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>

<script language="javascript">
function resetPageNumber(val){
	if(document.forms[0].pageNumber.length != undefined){
		for(var i=0; i<document.forms[0].pageNumber.length;i++){
			document.forms[0].pageNumber[i].value=val;
		}
	}
}

function setPageNumber(){
	if(document.forms[0].pageNumber.length == undefined){
		pageNumber = document.forms[0].pageNumber.value;
	} else {
		pageNumber = document.forms[0].pageNumber[0].value;
	}
	setNavigationType(pageNumber);
}


</script>


 <%
 try{
	 BaseSearchVOB searchForm = new BaseSearchVOB();
	int currentPage = 0;
	int totalPages = 0;
	int numberOfRecordsFound = 0;

	Enumeration myEnum = request.getAttributeNames();
	while(myEnum.hasMoreElements()){
		String attrName = (String) myEnum.nextElement();
		if(request.getAttribute(attrName) instanceof BaseSearchVOB){
			searchForm = (BaseSearchVOB) request.getAttribute(attrName);
			currentPage = searchForm.getCurrentPageNumber();
			totalPages = searchForm.getTotalPages();
			numberOfRecordsFound = searchForm.getNumberOfRecordsFound();
			break;
		}
	}
	
%>



<table class="contenttable" cellspacing="0" cellpadding="0">
	<tr class="txtBlk_leading" >
		<td align="left">Found&nbsp;<%= numberOfRecordsFound %> result(s).
			<% if(numberOfRecordsFound != 0){ %>
				Page: <%= currentPage %> of <%= totalPages %>&nbsp;
			<% } %>
		
	<%if(backToSearchButtonNeeded == false && numberOfRecordsFound == 0) {
		//nothign
		}else{ %>
		
		<% if(backToSearchButtonNeeded == true){ %>
			&nbsp;<prep:uiWidget type="<%=com.ascap.apm.common.utils.constants.SecurityConstants.ANCHOR_TYPE %>" label="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_BACK2SEARCH %>" id="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_BACK2SEARCH%>">onclick="javascript:setNavigationType('BACK2SEARCH');"</prep:uiWidget>
		<% }else{ %>
			&nbsp;
		<% } %>	
		</td>
		<td align="right">
		<% if(totalPages > 1){ %>
			Page #&nbsp;
			<input type="text" name="pageNumber" size="1" maxlength="5"  value="<%= currentPage%>" onkeyup="javascript:resetPageNumber(this.value);" />
			<prep:uiWidget type="<%=com.ascap.apm.common.utils.constants.SecurityConstants.ICON_TYPE %>" title="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_NAV_GO %>" id="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_NAV_GO%>">onclick="javascript:setPageNumber();"</prep:uiWidget>
		<% } %>&nbsp;&nbsp;
		
		<% if(numberOfRecordsFound != 0){ %>
				<% if(currentPage != 1){ %>
					<prep:uiWidget type="<%=com.ascap.apm.common.utils.constants.SecurityConstants.ICON_TYPE %>" title="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_NAV_FIRST %>" id="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_NAV_FIRST%>">onclick="javascript:setNavigationType('FIRST');"</prep:uiWidget>
					<prep:uiWidget type="<%=com.ascap.apm.common.utils.constants.SecurityConstants.ICON_TYPE %>" title="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_NAV_PREV %>" id="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_NAV_PREV%>">onclick="javascript:setNavigationType('PREV');"</prep:uiWidget>
				<% } %>

				<% if(currentPage != totalPages){ %>
					<prep:uiWidget type="<%=com.ascap.apm.common.utils.constants.SecurityConstants.ICON_TYPE %>" title="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_NAV_NEXT %>" id="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_NAV_NEXT%>">onclick="javascript:setNavigationType('NEXT');"</prep:uiWidget>
					<prep:uiWidget type="<%=com.ascap.apm.common.utils.constants.SecurityConstants.ICON_TYPE %>" title="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_NAV_LAST %>" id="<%=com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_NAV_LAST%>">onclick="javascript:setNavigationType('LAST');"</prep:uiWidget>
				<% } %>
		<% } %>
	</tr>
	<%} %>
</table>
<%
}catch(Exception e){
%>
	Error in navigationButtonsInclude
<%} %>
