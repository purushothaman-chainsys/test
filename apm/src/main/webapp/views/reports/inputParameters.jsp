
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<HEAD>
<%@ include file = "/views/common/uiWorkMatchWidges.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="com.ascap.apm.vob.reports.Report,com.ascap.apm.vob.reports.Parameter"%>
<%@ page import="com.ascap.apm.common.utils.cache.PrepKeyValueObject"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ascap.apm.common.utils.constants.*"%>
<%@ page import="com.ascap.apm.common.utils.ValidationUtils"%>



<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="IBM WebSphere Studio">
<meta http-equiv="Content-Style-Type" content="text/css">

<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css">

<script language="JavaScript" src="<%=request.getContextPath()%>/js/pupdate.js"></script>

<TITLE>APM</TITLE>

</HEAD>
<script language="javascript">

function submitForm() {
    var reqAction = '<%=(String)com.ascap.apm.common.utils.constants.PrepConstants.REPORT_EXECUTE %>'	
    document.forms[0].action = "apmreport?doAction="+reqAction;
	document.forms[0].submit();
}

function viewInstances(reportName, description) {
    
    //fix for defect #3287; escaping the & symbol
    var newReportDesc = description.replace("&", "%26");
    //alert(newReportDesc);

  var reqAction = '<%=(String)com.ascap.apm.common.utils.constants.PrepConstants.REPORT_VIEW_REPORT_INSTANCE%>'  
  //document.forms[0].selectedReportID.value =reportName;
  document.forms[0].action = "apmreport?reportName=" + reportName + "&doAction=" +reqAction + "&description=" + newReportDesc;
  document.forms[0].description = description;
  document.forms[0].submit();
}

function goHome(){
  var module = '<%=(String)session.getAttribute("module")%>';
  document.forms[0].action = "report?module=" + module ;
  document.forms[0].submit();
}

function setradio(){
  var schType= '<%=(String)((Report)session.getAttribute("report")).getReportType()%>'; 
  if(schType=="now"){
	document.forms[0].radioValue[0].checked=true;
  }		
}

<%--function resetData(){
  var reqAction = '<%=(String)com.ascap.apm.common.utils.constants.PrepConstants.REPORT_SHOWINPUTS%>' 
  
  alert(reqAction);
  
  var reportName= '<%= (String)((Report)session.getAttribute("reportForm")).getReportName() %>'
  //alert(reportName);
  	alert(reportName);
  document.forms[0].action = "apmreport.do?reportName=" + reportName + "&doAction=" + reqAction;
  document.forms[0].submit();
  
  
}--%>


function resetData(reportName, reportDesc){
    
   //fix for defect #3287; escaping the & symbol
    var newReportDesc = reportDesc.replace("&", "%26");
    //alert(newReportDesc);

    var reqAction = '<%=(String)com.ascap.apm.common.utils.constants.PrepConstants.REPORT_SHOWINPUTS%>' 
    document.forms[0].action = "apmreport?reportName=" + reportName + "&doAction=" + reqAction + "&description=" + newReportDesc;
    document.forms[0].submit();
  
  
	
 
}

function showDate(){
}

function validateMulti(from, to){
    var values = '';
    for (var i=0, l=from.options.length;i<l;i++) {
        if (from.options[i].selected){
            if(values != ''){
            	values = values + ',';
            }
            values = values + from.options[i].value;
        }
    }
    to.value = values;
}

</script>

<BODY onload="setradio()">       


<div class="menubg"  style="width: 100%; border: 0px; height:90px;"> 
<jsp:include page="/views/menu.jsp"/>

</div>



<div></div>



<form:form action="apmreport" modelAttribute="report" name="reportForm">
<%--------------------------------- TITLE TABLE ------------------------------------%>
<table class="titletable">
		<tr>
			<td align="left">Report Parameters [RP-102]</td>
			<td  align="right">
				<%String screenNumber="RP-102";%>
				
			</td>
		</tr>
</table>

<table class="detailstable">
		<tr>
			<td>
				&nbsp;<span class="textlabellarge">Report Name: </span><span class="textlabelmedium"><c:out value="${report.reportDesc}" /></span>
			</td>
		</tr>
		<tr>
			<td>
				&nbsp;<span class="textlabellarge">Report ID: </span><span class="textlabelmedium"><c:out value="${report.reportName}" /></span>
			</td>
		</tr>
	</table>
<%--------------------------------- ERROR MESSAGE TABLE ------------------------------------%>
<table class="errortable">		
		<tr>
			<td width="100%"><font class="txtRed"><c:if test="${not empty systemerror}"><li><c:out value="${systemerror}" escapeXml="false"/></li></c:if></font>
				<font class="txtGreen"><c:if test="${not empty systemmessage}"><li><c:out value="${systemmessage}"/></li></c:if></font>
			</td>
		</tr>
</table>

	
	<table class="detailstable">
		<tr>
			<td valign="top" width="2px">&nbsp;</td>
			<td valign="top">
 			<%
if( request.getAttribute("errors")!=null){
   
   Map<String,Object> ae = (Map<String,Object>) request.getAttribute("errors");
%>    
<strong>The following error(s) occurred:</strong>
<ul>
  <c:forEach items="${ae.entrySet()}" var="error" >
<font color="red" style="bold"><li><spring:message  code="${error.getKey()}" arguments="${error.getValues()[0]}" /></li></font>
</c:forEach></ul>
 	<%}%>		
 			
 			
 			
 			
 			
 			<%
 			  List berror = (List)request.getAttribute("berrors");
		      
		      if(berror!=null && berror.size()!=0){
		    %>
		        <strong>The following error(s) occurred:</strong>
		       
		        <% 
		        	Iterator iterator = berror.iterator();
		        	
		        	while(iterator.hasNext()){
		        		String errorStr= (String)iterator.next();
		        %>		        
		        		<font color="red" style="bold">
		        		<ul>
		       				<li><%=errorStr %></li> 
		       			</ul>
		       			</font>
		       	 <% } %>		    	        
		   <% }%>

				<div class="bor">
					<table width="700" border="0" cellpadding="0" cellspacing="0" class="txtBlk" style="font-size: 1.2em;">

					<!-- Code addded by vzayyadevara-->
						<tr>
							<td colspan="4">&nbsp;</td>
						</tr>
						<tr>
							<td width="4">&nbsp;</td>
							<td class="input" colspan="4" height="50">
								<%
									String reportName = (((Report) session.getAttribute("report")).getReportName());
			                    	String scheduleValue1 = ((Report) session.getAttribute("report")).getReportType();
				                    String arg1 = null;
                				    if (scheduleValue1 != null && scheduleValue1.equalsIgnoreCase("now")) {
				                        arg1 = "next few minutes";
				                    } else {
				                        arg1 = "next 24 hours";
				                    }
				                    System.out.println("arg1::" + arg1);
				                    pageContext.setAttribute("arg1", arg1);
			                    %> 
			                    <spring:message  code="ap.prep.report.header" arguments="${report.reportName};${arg1}" argumentSeparator=";"/>
			                </td>
						</tr>

				<!-- Code ended by vzayyadevara-->
						<tr>
							<td colspan="4">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="4">&nbsp;</td>
						</tr>
						
						<c:if test="${not empty report.parameters}"><!-- logic:present  -->
							<c:forEach items="${report.parameters}" var="parameters" varStatus="currIndex">
							
							<c:set var="currIndex" value="${currIndex.index}"/>
							
								<%
									boolean isMultiValued = ((Parameter) ((Report) session.getAttribute("report")).getParameters().get((Integer.valueOf(pageContext.getAttribute("currIndex").toString())).intValue())).isMultiValued() ||
									!((Parameter) ((Report) session.getAttribute("report")).getParameters().get((Integer.valueOf(pageContext.getAttribute("currIndex").toString())).intValue())).isCustomValued();
                                %>
								<tr>
									<td width="1%">&nbsp;</td>
									<td width="30%" class="label_1">
										<c:if test="${parameters.required eq true}">
											<font color="red" style="bold"> * </font>
										</c:if>
										
										
										<%-- jason <bean:write property="paramName" name="parameters" />--%>
										<c:if test="${empty parameters.paramLabel}">
											<c:out value="${parameters.paramName}" />
										</c:if>
										<c:if test="${not empty parameters.paramLabel}">
											<c:out value="${parameters.paramLabel}" />
										</c:if>
										<c:if test="${parameters.defaultList eq true}">
											<% if (isMultiValued){ %>
											<br>(Ctrl + Selection for multi select)
											<%} %>
										</c:if>
									</td>
									<td width="1%">:</td>

									<%
									String defaultValue = ((Parameter) ((Report) session.getAttribute("report")).getParameters().get((Integer.valueOf("" + pageContext.getAttribute("currIndex").toString())).intValue())).getParameterValue();
	                                %>
									<%
										String inputValue = ((Parameter) ((Report) session.getAttribute("report")).getParameters().get((Integer.valueOf("" + pageContext.getAttribute("currIndex").toString())).intValue())).getInputValue();
	                                %>
									<%
										 //System.out.println("input value::" +inputValue);
        		                         //System.out.println("default value::" +defaultValue);
        		                        
		                                String setValue = null;
        		                        if ((inputValue == null || inputValue.trim().length() <= 0) && (request.getAttribute("errors") == null)) {
                		                    setValue = defaultValue;
                        		        } else {
                                		    setValue = inputValue;
		                                }
        		                        // String type=getObjType(((Parameter)((Report)session.getAttribute("reportForm")).getParameters().get(index.intValue())).getParameterType());
                		                // String type=((Parameter)((Report)session.getAttribute("reportForm")).getParameters().get(index.intValue())).getParameterType();
                        		        // System.out.println("type::" + type);
	                                %>
										<td class="input">
											<c:if test="${parameters.defaultList eq true}">
											<% if (isMultiValued == false){ %>
												
													<%
														ArrayList valueList = (ArrayList) ((Parameter) ((Report) session.getAttribute("report")).getParameters().get((Integer.valueOf("" + pageContext.getAttribute("currIndex").toString())).intValue())).getMultipleValues();
				                                        for (int i = 0; i < valueList.size(); i++) {
		        	            	                       
		        	            	                        //String item = (String) valueList.get(i);
                       		        	            	    PrepKeyValueObject parameter = (PrepKeyValueObject) valueList.get(i);
                       		        	            	    //if there isn't a description use key value
                       		        	            	    if(parameter.getValue()== null){
                       		        	            	     parameter.setValue(parameter.getKey());
                       		        	            	    } 
														}
														pageContext.setAttribute("valueList", valueList, PageContext.PAGE_SCOPE); 
		            		                        %>
											<form:select path="parameters[${currIndex}].inputValue" id="inputValue" value="<%=setValue%>">
						<form:options items="${valueList}" itemLabel="value" itemValue="key" />
					</form:select>
<%--												</logic:equal> --%>
		            		                 <%} else {%>												
<%--												<logic:equal property="multiValued" name="parameters" value="true"> --%>
													<select name="multiSelect${currIndex}" multiple="multiple" style="width:30em" onchange="validateMulti(this, document.forms['reportForm'].elements['parameters[${currIndex}].inputValue']); return false;">
													<%
														ArrayList valueList=(ArrayList) ((Parameter)((Report)session.getAttribute("report")).getParameters().get((Integer.valueOf("" + pageContext.getAttribute("currIndex").toString())).intValue())).getMultipleValues(); 
														String[] currentSelectionStrArray=null;
														List currentSelectionList=null; 
														currentSelectionStrArray=request.getParameterValues("multiSelect"+(Integer.valueOf("" + pageContext.getAttribute("currIndex").toString())).intValue());
														if(ValidationUtils.hasCollectionAnyElements(currentSelectionStrArray)){
														currentSelectionList= Arrays.asList(currentSelectionStrArray);
														}
														//System.out.println(com.ascap.apm.common.utils.DebugHelperUtils.debugCollections("*********************currentSelectionList",    currentSelectionList));
														for(int i=0; i<valueList.size();i++){
															//String item = (String)valueList.get(i); 
															PrepKeyValueObject parameter = (PrepKeyValueObject) valueList.get(i);

															//if there isn't a description use key value
                       		        	            	    if(parameter.getValue()== null ){
                       		        	            	    parameter.setValue(parameter.getKey());
                       		        	            	    } 
		                       		        	            //System.out.println(com.ascap.apm.common.utils.DebugHelperUtils.debugStringArray("*********************input parameters",    request.getParameterValues("multiSelect"+index.intValue())));
		                       		        	            if(currentSelectionList != null && (ValidationUtils.isEmptyOrNull(parameter.getKey())==false) ){
			                       		        	            if( currentSelectionList.contains(parameter.getKey())){
		                       		        	                 %>
		                       		        	                      <option value="<%=parameter.getKey()%>" selected="selected"><%=parameter.getValue()%></option>
		                       		        	                 <%
		                       		        	                 }else{
														         %>
																      <option value="<%=parameter.getKey()%>"><%=parameter.getValue()%></option>
														         <%
														         }
															}else{
															%>
																<option value="<%=parameter.getKey()%>"><%=parameter.getValue()%></option>
															<%}
													    }%>
													</select>												
													<%-- <html:hidden property="inputValue" name="parameters" indexed="true"/> --%>
													<form:hidden path="parameters[${currIndex}].inputValue" />
													
		            		                 <%}%>												
											</c:if>
											<c:if test="${parameters.defaultList ne true}">
												<form:input size="10" path="parameters[${currIndex}].inputValue" value="<%=setValue%>" indexed="true" />
												<%
													String parametertype = ((Parameter) ((Report) session.getAttribute("report")).getParameters().get((Integer.valueOf("" + pageContext.getAttribute("currIndex").toString())).intValue())).getParameterType();
				                                    if (parametertype.equalsIgnoreCase(com.ascap.apm.common.utils.constants.PrepConstants.TYPE_DATETIME) || parametertype.equalsIgnoreCase(com.ascap.apm.common.utils.constants.PrepConstants.TYPE_DATE) || parametertype.equalsIgnoreCase(com.ascap.apm.common.utils.constants.PrepConstants.TYPE_TIME)) {
		                                        %>
														<A  onClick="cal.select(document.forms['reportForm'].elements['parameters[${currIndex}].inputValue'],'calendear${currIndex}','MM/dd/yyyy'); return false;"
														    NAME="calendear${currIndex}" ID="calendear${currIndex}" class="<%=UIWidgetConstants.STYLE_CALENDAR_ICON_ENABLED%>" title="Select Date" ></A>
												<%
													}
												%>
											</c:if>
										</td>
									
								</tr>
							</c:forEach>
						</c:if>

						<!--code added by vzayyadevara/Begin -->
						<!--Radio Button for Schedule now or latter-->
						<c:if test="${not empty report.reportType}">
						<%
							String scheduleValue = ((Report) session.getAttribute("report")).getReportType();
                        	System.out.println("scheduleValue in jsp:" + scheduleValue);
                        	if (scheduleValue != null && scheduleValue.equalsIgnoreCase("now")) {
                        %>
							<tr>
								<td width="4">&nbsp;</td>
								<td width="100" class="txtBlk_bold" style="font-size: 1.1em;">Schedule : </td>
								<td width="4"></td>
								<td>
									<table class="detailtable" style="font-size: 1.0em;">
									<%
										if (scheduleValue1 != null && scheduleValue1.equalsIgnoreCase("now")) {
    	                            %>
										<tr>
											<td class="txtBlk" style="font-size: 1.0em;">
												<form:radiobutton path="radioValue" value="0"/>
													 <spring:message code="ap.prep.report.schedule.now" />
											</td>
										</tr>
										<tr>
											<td class="txtBlk" style="font-size: 1.0em;">
												<form:radiobutton path="radioValue" value="1"/>
												 	<spring:message code="ap.prep.report.schedule.latter" />
											</td>
										</tr>
									<%
										}
									%>
									</table>
								</td>
							</tr>
						<%}%>
					</c:if>

					<!--code added by vzayyadevara/End -->

					<tr>
						<td width="4">&nbsp;</td>
						<td class="input" colspan="4" align="center">&nbsp;</td>
					</tr>
					<!-- LOGIN BUTTONS COME HERE-->
					
				</table>
			</div>
		</td>
	</tr>
</table>


<table class="buttonstable">
<tr>
						<td width="4">&nbsp;</td>
						<td class="input" colspan="4" align="center">
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
						    
						    <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_RP_100_REPORT_CRYSTAL_RUN%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_EXECUTE %>" id="<%=UIWidgetConstants.ID_EXECUTE %>" title="<%= UIWidgetConstants.LABEL_EXECUTE %>">
								onClick="javascript:submitForm();"
							</prep:uiWidget>&nbsp;&nbsp;&nbsp;
							
							<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_RP_100_REPORT_SEARCH%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_CANCEL %>" id="<%=UIWidgetConstants.ID_CANCEL %>" title="<%= UIWidgetConstants.LABEL_CANCEL %>"> 
								onClick="javascript:viewInstances('${report.reportName}', '<%= sb.toString() %>');"
							</prep:uiWidget>&nbsp;&nbsp;&nbsp; 
							
							<prep:uiWidget name="" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%= UIWidgetConstants.LABEL_RESET %>" id="<%=UIWidgetConstants.ID_RESET %>" title="<%= UIWidgetConstants.LABEL_RESET %>"> 
								onClick="javascript:resetData('${report.reportName}','<%= sb.toString() %>');"
							</prep:uiWidget>

						</td>
					</tr>

</table>


</form:form>
<SCRIPT LANGUAGE="JavaScript" ID="js2">
var cal = new CalendarPopup();
cal.showYearNavigation();
cal.showYearNavigationInput();
</SCRIPT>
</BODY>
</html>
