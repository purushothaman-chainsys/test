<!DOCTYPE HTML>
<%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>
<%@ page import="com.ascap.apm.controller.utils.Utils"%>
<%@ page import="com.ascap.apm.controller.utils.HtmlSelectOption"%>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>	

<% pageContext.setAttribute("LogRequestSuppliersList", HtmlSelectOption.getLookUpTable("LogRequestCallLtrs"), PageContext.PAGE_SCOPE);
   pageContext.setAttribute("LogRequestStatusList", HtmlSelectOption.getLookUpTable("LogRequestStatuses"), PageContext.PAGE_SCOPE);
   pageContext.setAttribute("LogRequestTargetSurveyYearQtrList", HtmlSelectOption.getLookUpTable("LogRequestTargetSurveyYearQtr"), PageContext.PAGE_SCOPE);
  %>

<html>
<head>

<title>APM</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 
  

<link href="${pageContext.request.contextPath}/themes/stylesheet.css" rel="stylesheet" type="text/css">
  
  
<%@ include file = "/views/common/uiWidgets.jsp"%>
<script type="text/javascript">
 
$(function() {
		sortTable('US-500','mainData', [0,10]);
		filterTable('US-500','mainData' , [0,10]);

});
 

function addRow()	{
	$("#mdlTbl>tbody").append('<tr><td><input type="checkbox" name="abcdef_chk"></td><td><input type="text" name="titles" value="" size="60" maxlength="150" /></td><td><input type="text" name="artists" value="" size="60" maxlength="100"/></td><td><input type="text" name="workids" value="" size="11" maxlength="11" /></td><td><input type="text" name="durations" value="" size="11" maxlength="11" /></td><td><input type="text" name="plays" value="" size="11" maxlength="11" /></td><td><select name="usetypes" ><option value="F">F</option><option value="S">S</option></select></td><td><select name="instvocals" ><option value="V">V</option><option value="I">I</option></select><input type="hidden" name="insTitles"/><input type="hidden" name="insArtists" /><input type="hidden" name="insWorkids"/><input type="hidden" name="insDurations" /><input type="hidden" name="insPlays" /><input type="hidden" name="insUsetypes"/><input type="hidden" name="insInstvocals"  /></td></tr>');		
}


function addRow_OLD()	{
	$("#mdlTbl>tbody").append('<tr><td><input type="checkbox" name="abcdef_chk"></input></td><td><input type="text" name="titles" value="" size="60" maxlength="150" /></td><td><input type="text" name="artists" value="" size="60" maxlength="100"/></td><td><input type="text" name="workids" value="" size="11" maxlength="11" /></td><td><input type="text" name="durations" value="" size="11" maxlength="11" /></td><td><input type="text" name="plays" value="" size="11" maxlength="11" /></td><td><select name="usetypes" ><option value="F">F</option><option value="S">S</option></select></td><td><select name="instvocals" ><option value="V">V</option><option value="I">I</option></select></td></tr>');		
}
  
function deleteRow() {
	if($("#mdlTbl1>tbody tr input:checked").length == 0) {}
	$("#mdlTbl>tbody tr input:checked").parent().parent().remove();
}


function allNumeric(inputtxt)  {  
   
   inputtxt = $.trim(inputtxt);
   var numbers = /^[0-9]+$/;  
   if(inputtxt.match(numbers)) {    
	   return true;  
   }  
   else  
	   return false;  
    
}   

function add(obj) {    
    
	$("input[name=selections]").each(function(){
		$(this).val('');
	});  
	 
	
	var validNumeric = true; 
	var widValidNumeric = true; 
	var durationValidNumeric = true; 
	var playsValidNumeric = true; 
		
	 	
	$("input[name=titles]").each(function(){  
	
	var wid = 		$.trim($(this).closest('tr').find('input[name=workids]').val());
	var duration = 	$.trim($(this).closest('tr').find('input[name=durations]').val());
	var plays = 	$.trim($(this).closest('tr').find('input[name=plays]').val());   
	 
	if (wid != ''){
		if (!allNumeric(wid))
		widValidNumeric = false; 
	}
	if (duration != ''){
		if (!allNumeric(duration))
		durationValidNumeric = false; 
	}
	if (plays != ''){
		if (!allNumeric(plays))
		playsValidNumeric = false; 
	}
	 
	  if (validNumeric) {	 
	   
		if ($.trim(    $(this).closest('tr').find('input[name=titles]').val()   ) == '') 
		     $(this).closest('tr').find('input[name=insTitles]').val('$######$######$'); 
		else
			  $(this).closest('tr').find('input[name=insTitles]').val( $.trim( $(this).closest('tr').find('input[name=titles]').val()) ); 
		   
	  if ($.trim(    $(this).closest('tr').find('input[name=artists]').val()   ) == '') 
		     $(this).closest('tr').find('input[name=insArtists]').val('$######$######$'); 
		else
			  $(this).closest('tr').find('input[name=insArtists]').val( $.trim( $(this).closest('tr').find('input[name=artists]').val()) );  
	  
	  if ($.trim(    $(this).closest('tr').find('input[name=workids]').val()   ) == '') 
		     $(this).closest('tr').find('input[name=insWorkids]').val('$######$######$'); 
		else
			  $(this).closest('tr').find('input[name=insWorkids]').val( $.trim( $(this).closest('tr').find('input[name=workids]').val()) ); 
			  
	  
	  if ($.trim(    $(this).closest('tr').find('input[name=durations]').val()   ) == '') 
		     $(this).closest('tr').find('input[name=insDurations]').val('$######$######$'); 
		else
			  $(this).closest('tr').find('input[name=insDurations]').val( $.trim( $(this).closest('tr').find('input[name=durations]').val()) ); 
			  
			  
	  if ($.trim(    $(this).closest('tr').find('input[name=plays]').val()   ) == '') 
		     $(this).closest('tr').find('input[name=insPlays]').val('$######$######$'); 
		else
			  $(this).closest('tr').find('input[name=insPlays]').val( $.trim( $(this).closest('tr').find('input[name=plays]').val()) ); 
			  
		
	  if ($.trim(    $(this).closest('tr').find('input[name=usetypes]').val()   ) == '') 
		     $(this).closest('tr').find('input[name=insUsetypes]').val('$######$######$'); 
		else
			  $(this).closest('tr').find('input[name=insUsetypes]').val( $.trim( $(this).closest('tr').find('input[name=usetypes]').val()) ); 
			  
		
	  if ($.trim(    $(this).closest('tr').find('input[name=instvocals]').val()   ) == '') 
		     $(this).closest('tr').find('input[name=insInstvocals]').val('$######$######$'); 
		else
			  $(this).closest('tr').find('input[name=insInstvocals]').val( $.trim( $(this).closest('tr').find('input[name=instvocals]').val()) ); 
	 	
			  	 
	 }
	});  
	 
   if(!widValidNumeric) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.logusage.insert.widnumericerr"/></span>'); 
		unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	}
	if(!durationValidNumeric) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.logusage.insert.durationnumericerr"/></span>'); 
		unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	}
	if(!playsValidNumeric) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.logusage.insert.playsnumericerr"/></span>'); 
		unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	}
	 
	document.forms[0].operationType.value='INSERT';
	document.forms[0].submit(); 
	}
 
 
function add_OLD(obj) {    
    
	$("input[name=selections]").each(function(){
		$(this).val('');
	});  
	 
	var validNumeric = true; 
	var widValidNumeric = true; 
	var durationValidNumeric = true; 
	var playsValidNumeric = true; 
		
	$("input[name=titles]").each(function(){  
	
	var wid = 		$.trim($(this).closest('tr').find('input[name=workids]').val());
	var duration = 	$.trim($(this).closest('tr').find('input[name=durations]').val());
	var plays = 	$.trim($(this).closest('tr').find('input[name=plays]').val());   
	 
	if (wid != ''){
		if (!allNumeric(wid))
		widValidNumeric = false; 
	}
	if (duration != ''){
		if (!allNumeric(duration))
		durationValidNumeric = false; 
	}
	if (plays != ''){
		if (!allNumeric(plays))
		playsValidNumeric = false; 
	}
	 
	 if (validNumeric) {	
		if ($.trim(    $(this).closest('tr').find('input[name=titles]').val()   ) == '') 
		    $(this).val('$######$######$'); 
		if ($.trim(    $(this).closest('tr').find('input[name=artists]').val()   ) == '') 
		    $(this).closest('tr').find('input[name=artists]').val('$######$######$');  
		if ($.trim(    $(this).closest('tr').find('input[name=workids]').val()   ) == '') 
		    $(this).closest('tr').find('input[name=workids]').val('$######$######$');  
		if ($.trim(    $(this).closest('tr').find('input[name=durations]').val()   ) == '') 
		    $(this).closest('tr').find('input[name=durations]').val('$######$######$');  
		if ($.trim(    $(this).closest('tr').find('input[name=plays]').val()   ) == '') 
		    $(this).closest('tr').find('input[name=plays]').val('$######$######$');  
	 }
	        
	});  
	 
   if(!widValidNumeric) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.logusage.insert.widnumericerr"/></span>'); 
		unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	}
	if(!durationValidNumeric) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.logusage.insert.durationnumericerr"/></span>'); 
		unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	}
	if(!playsValidNumeric) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.logusage.insert.playsnumericerr"/></span>'); 
		unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	}
	  
	 
	 
	$("input[name=updTitles]").each(function(){  
	$(this).val(     $.trim(  $(this).closest('tr').find('input[name=title]').val()  )      ); 
	$(this).closest('tr').find('input[name=updArtists]').val(  $(this).closest('tr').find('input[name=artist]').val()  ); 
	$(this).closest('tr').find('input[name=updWorkids]').val(  $(this).closest('tr').find('input[name=workid]').val()  );
	$(this).closest('tr').find('input[name=updDurations]').val(  $(this).closest('tr').find('input[name=duration]').val()  ); 
	});
	 
	document.forms[0].operationType.value='INSERT';
	document.forms[0].submit(); 
	}
 
 
function del(obj) {  
		
		if($("input[name=selectedIndex]:checked").length === 0) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.logusage.delete.selection"/></span>');
		unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	    }
		
		
		var nodelete = false;
		
		$("input[name=selectedIndex]:checked").each(function(){   
		var logstatus = $(this).closest('tr').find('input[name=status]').val();  
		 if (logstatus == "Ready for Release" || logstatus == "System Error" || logstatus == "Validation Error") 
		 {}
		 else 
		     nodelete = true;  
		 }); 
		 
		 if(nodelete) {	
			$("#serverErrorMessages").html("");
			$("#errorMessages").html("");
			$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.logusage.update.statuserr"/></span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
		  }
		 
		$("input[name=selections]").each(function(){
			$(this).val('');
		}); 
		 
		$("input[name=selectedIndex]:checked").each(function(){   
		 var id =   $.trim( $(this).val() ); 
			  $(this).closest('tr').find('input[name=selections]').val(id);  
		 }); 
	 
		var newDiv = $(document.createElement('div')); 
       	var txt = ' Selected logs will be deleted. Do you want to proceed? ';		       	
		newDiv.html(txt);
		newDiv.attr('title','Warning:');
 		newDiv.dialog({
 			resizable: false,
 			height:'auto',
				minHeight: 'auto',
 			modal: true,
				show: "slide",
				hide: "slide",
 			buttons: {
 				Cancel: function() {
 					 	unCheckSub(obj);
		 				location.href="#";  
	 					$( this ).dialog( "close" );  
 					},		 					
 				"OK": function() {
 					$( this ).dialog( "close" );  
					document.forms[0].operationType.value='DELETE';
					document.forms[0].submit();
 				}
 			}
		 }); 
}	


function update_Original(obj) {   
	
	var noupdate = false;
		
	if($("input[name=selectedIndex]:checked").length === 0) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.archives.update.selection"/></span>');
		unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	}
	  
	
	$("input[name=selections]").each(function(){
		$(this).val('');
	}); 
	$("input[name=updWrkperfids]").each(function(){
		$(this).val('');
	}); 
	$("input[name=updTitles]").each(function(){
		$(this).val('');
	}); 
	$("input[name=updArtists]").each(function(){
		$(this).val('');
	}); 
	$("input[name=updWorkids]").each(function(){
		$(this).val('');
	}); 
	$("input[name=updDurations]").each(function(){
		$(this).val('');
	}); 
	  
	 $("input[name=selectedIndex]:checked").each(function(){  
	 
	 var logstatus = $(this).closest('tr').find('input[name=status]').val(); 
	  
	 if (logstatus == "Ready for Release" || logstatus == "System Error" || logstatus == "Validation Error") 
	 {}
	 else 
	     noupdate = true; 
	 
	 var id =   $.trim( $(this).val() );
	 var ttl =  $.trim( $(this).closest('tr').find('input[name=title]').val() );
	 var art =  $.trim( $(this).closest('tr').find('input[name=artist]').val());
	 var wid =  $.trim( $(this).closest('tr').find('input[name=workid]').val());
	 var dun =  $.trim( $(this).closest('tr').find('input[name=duration]').val());
	 var pl =   $.trim( $(this).closest('tr').find('input[name=plays]').val());
	 var ust =  $.trim( $(this).closest('tr').find('select[name=usetypes]').val());
	 var iv =   $.trim( $(this).closest('tr').find('select[name=instvocal]').val());
	 var value = id + "############" + ttl 
		  +"############"+art+  "############" + wid+  "############" +dun 
		  +  "############" + pl +  "############" +ust +  "############"+iv+ "############" ;
	 
		  $(this).closest('tr').find('input[name=updWrkperfids]').val( id );  
		  $(this).closest('tr').find('input[name=updTitles]').val(ttl); 
		  $(this).closest('tr').find('input[name=updArtists]').val( art ); 
		  $(this).closest('tr').find('input[name=updWorkids]').val( wid );
		  $(this).closest('tr').find('input[name=updDurations]').val(dun); 
		  $(this).closest('tr').find('input[name=updPlays]').val(pl); 
		  $(this).closest('tr').find('input[name=updUsetypes]').val(ust); 
		  $(this).closest('tr').find('input[name=updInstvocals]').val(iv); 
		   
		  $(this).closest('tr').find('input[name=selections]').val(value);   
	 
	 });
	 
	 if(noupdate) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.logusage.update.statuserr"/></span>');
		unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	}
	 
			 
	 var newDiv = $(document.createElement('div')); 
       	var txt = ' Selected logs will be updated. Do you want to proceed? ';		       	
		newDiv.html(txt);
		newDiv.attr('title','Warning:');
 		newDiv.dialog({
 			resizable: false,
 			height:'auto',
				minHeight: 'auto',
 			modal: true,
				show: "slide",
				hide: "slide",
 			buttons: {
 				Cancel: function() {
 					 	unCheckSub(obj);
		 				location.href="#";  
	 					$( this ).dialog( "close" );  
 					},		 					
 				"OK": function() {
 					$( this ).dialog( "close" );  
					document.forms[0].operationType.value='UPDATE';  
					document.forms[0].submit(); 
 				}
 			}
		 }); 
}	


function update(obj) {   
	
	var noupdate = false;
		
	if($("input[name=selectedIndex]:checked").length === 0) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.archives.update.selection"/></span>');
		unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	}
	  
	
	$("input[name=selections]").each(function(){
		$(this).val('');
	}); 
	$("input[name=updWrkperfids]").each(function(){
		$(this).val('');
	}); 
	$("input[name=updTitles]").each(function(){
		$(this).val('');
	}); 
	$("input[name=updArtists]").each(function(){
		$(this).val('');
	}); 
	$("input[name=updWorkids]").each(function(){
		$(this).val('');
	}); 
	$("input[name=updDurations]").each(function(){
		$(this).val('');
	}); 
	$("input[name=updPlays]").each(function(){
		$(this).val('');
	}); 
	$("input[name=updUsetypes]").each(function(){
		$(this).val('');
	});
	$("input[name=updInstvocals]").each(function(){
		$(this).val('');
	}); 
	   
	 var validNumeric = true;
	 
	 $("input[name=selectedIndex]:checked").each(function(){   
	 
	 var logstatus = $(this).closest('tr').find('input[name=status]').val();  
	 if (logstatus == "Ready for Release" || logstatus == "System Error" || logstatus == "Validation Error") 
	 {}
	 else 
	     noupdate = true; 
	 
	 var id =   $.trim( $(this).val() );
	 var ttl =  $.trim( $(this).closest('tr').find('input[name=title]').val() );
	 var art =  $.trim( $(this).closest('tr').find('input[name=artist]').val());
	 var wid =  $.trim( $(this).closest('tr').find('input[name=workid]').val());
	 var dun =  $.trim( $(this).closest('tr').find('input[name=duration]').val());
	 var pl =   $.trim( $(this).closest('tr').find('input[name=plays]').val());
	 var ust =  $.trim( $(this).closest('tr').find('select[name=usetypes]').val());
	 var iv =   $.trim( $(this).closest('tr').find('select[name=instvocal]').val());
	 var value = id + "############" + ttl 
		  +"############"+art+  "############" + wid+  "############" +dun 
		  +  "############" + pl +  "############" +ust +  "############"+iv+ "############" ;
	 
		  $(this).closest('tr').find('input[name=updWrkperfids]').val( id );  
		  $(this).closest('tr').find('input[name=updTitles]').val("############"+ttl); 
		  $(this).closest('tr').find('input[name=updArtists]').val( "############"+art ); 
		  $(this).closest('tr').find('input[name=updWorkids]').val( "############"+wid );
		  $(this).closest('tr').find('input[name=updDurations]').val("############"+dun); 
		  $(this).closest('tr').find('input[name=updPlays]').val("############"+pl); 
		  $(this).closest('tr').find('input[name=updUsetypes]').val(ust); 
		  $(this).closest('tr').find('input[name=updInstvocals]').val(iv); 
		   
		  $(this).closest('tr').find('input[name=selections]').val(value);   
	 
		if (wid != ''){
				if (!allNumeric(wid))
				validNumeric = false; 
			}
		if (dun != ''){
				if (!allNumeric(dun))
				validNumeric = false; 
			}
		if (pl != ''){
				if (!allNumeric(pl))
				validNumeric = false; 
			}
	 });
	 
	 if(!validNumeric) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.logusage.insert.numericerr"/></span>');
		unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	}
	
	 if(noupdate) {	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><spring:message code="us.error.apm.logusage.update.statuserr"/></span>');
		unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	}
	 
	var newDiv = $(document.createElement('div')); 
       	var txt = ' Selected logs will be updated. Do you want to proceed? ';		       	
		newDiv.html(txt);
		newDiv.attr('title','Warning:');
 		newDiv.dialog({
 			resizable: false,
 			height:'auto',
				minHeight: 'auto',
 			modal: true,
				show: "slide",
				hide: "slide",
 			buttons: {
 				Cancel: function() {
 					 	unCheckSub(obj);
		 				location.href="#";  
	 					$( this ).dialog( "close" );  
 					},		 					
 				"OK": function() {
 					$( this ).dialog( "close" );  
					document.forms[0].operationType.value='UPDATE';  
					document.forms[0].submit(); 
 				}
 			}
		 }); 
}	


function logrequest(obj) {    
			 
	    	var logRequestPg = $("input[name=logRequestPg]").val();  
	    	document.forms[0].currentPg.value=logRequestPg; 
	    	
			document.forms[0].action = '<%=request.getContextPath()%>/usage/logrequestSummary';
			document.forms[0].operationType.value='BACK'; 
			document.forms[0].submit(); 
			
}	


function setNavigationType(navigation) {
		document.forms[0].navigation.value = navigation;
		document.forms[0].submit();
}  


function setPageNumber() {
		var gotopage =  $.trim( $("input[name=pageNumber]").val()); 
		document.forms[0].navigation.value = 'GOTOPAGE';
		document.forms[0].currentPg.value = gotopage ; 
		document.forms[0].submit();
}  
</script>
</head>
<body> 
<div class="menubg"  style="width: 100%; border: 0px; height:90px;">
<%@ include file = "/views/menu.jsp"%> 
</div>
 
<table class="titletable" style="display:none" style="width:600px;">		
	<tr><td></td></tr>
</table>
<form:form action="logusage" modelAttribute="logUsage">
<table class="errortable">
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
 
<form:hidden  path="operationType" />  
<form:hidden  path="filterSupplierCode" />  
<form:hidden  path="filtertargetSurveyYearQuarter" />  
<form:hidden  path="filterStaus" />  
<form:hidden  path="logRequestPg" />  
<form:hidden  path="currentPg" /> 
<form:hidden  path="totalPg" />  
<form:hidden  path="navigation" />  
<div style="width:1200px;margin:0 auto;">
</div>
 
 </br></br>
 
<div id="filtertarget111"  style="width:1200px; font-size: 11px; font-weight: bold; color: #000000; line-height: 11px; margin:0 auto;">

   
<fieldset class="fieldsetfull" style="width:1200px;"><legend class="legendfull">Log Usage [LR-101]</legend>
  
<div align="center">
<table style="font-size: 1.1em; width: 800px; font-weight: bold; color: #000000; line-height: 1.5em;;">
 
<tr> 
	<td>   
    <form:hidden  path="headerid"  /> 
    <form:hidden  path="callLetter"  /> 
    <form:hidden  path="startDate"  /> 
    <form:hidden  path="endDate"  /> 
    <form:hidden  path="accountNumber"  /> 
	</td>
</tr>

<tr>
	<td>Call Letter: ${logUsage.callLetter}</td>
	<td>Start Date: ${logUsage.startDate}</td>
	<td>End Date: ${logUsage.endDate} </td>
	<td>Account Number: ${logUsage.accountNumber}</td>
</tr>
 
  
</table> 
</div>
<br/>
  
  
<table  style="font-size: 1.0em; width: 100%; font-weight: bold; color: #000000; line-height: 1.5em;" id="mdlTbl">  
<thead>   
<tr  style="line-height: 2.5em;" align="center" >  
	<th width="1%"></th>  
	<th width="20%">Title</th>
	<th width="20%">Performer</th>
	<th width="5%">Work ID</th> 
	<th width="5%">Duration</th> 
	<th width="5%">Plays</th> 
	<th width="5%">Use Type</th>  
	<th width="5%">I/V</th> 
	<th width="9%"></th>  
</tr> 
</thead>
<tbody>  
 

<tr align="center">
<td><input type="checkbox" name="abcdef_chk"></input></td>
<td><form:input  path="titles" value="" size="60" maxlength="150"/>

<form:hidden  path="insTitles"  /> 
<form:hidden  path="insArtists"  /> 
<form:hidden  path="insWorkids"  /> 
<form:hidden  path="insDurations"  /> 
<form:hidden  path="insPlays"  /> 
<form:hidden  path="insUsetypes"  /> 
<form:hidden  path="insInstvocals"  /> 
</td>
<td><form:input path="artists" value="" size="60" maxlength="100"/></td>
<td><form:input path="workids" value="" size="11" maxlength="11" /></td>
<td><form:input path="durations" value="" size="11" maxlength="11" /></td>
<td><form:input path="plays" value="" size="11" maxlength="11" /></td>
<td>
<select name="usetypes">
<option value="F">F</option>
<option value="S">S</option>
</select>
</td>
<td>
<select name="instvocals" ><option value="V">V</option>
<option value="I">I</option></select>

</td>
</tr>
</tbody>
</table>

 
<table style="width:60%;">
<tr>
<td align="left" style="font-size: 0.7em;width:30%;">
 
 <prep:uiWidget id="${UIWidgetConstants.ID_ADD_NEW_ENTRY}" type="${SecurityConstants.ICON_TYPE}" >onclick="javascript:addRow();" </prep:uiWidget>
 <prep:uiWidget id="${UIWidgetConstants.ID_REMOVE}" type="${SecurityConstants.ICON_TYPE}" > onclick="javascript:deleteRow();" </prep:uiWidget>
 <prep:uiWidget name="${ProtectedResourcesConstants.APM_LR_100_LOG_REQ_UPDATE}" type="${SecurityConstants.ANCHOR_TYPE}" 
 label="${UIWidgetConstants.LABEL_SUBMIT}" id="${UIWidgetConstants.ID_SUBMIT}" title="Add">onclick="javascript:add(this);"</prep:uiWidget>
  
</td>
<td align="left" style="font-size: 0.7em;width:50%;"> 
 
</td>
</tr>					
</table> 
<br/> 
</div> 
</br>  

<table class="detailstable1000" style="width:1200px; font-size: 11px; font-weight: bold; color: #000000; line-height: 11px; margin:0 auto; padding-top:15px; padding-bottom:15px;"> 
 <tr> 
	  <td align="left" width="20%">
	     <prep:uiWidget name="${ProtectedResourcesConstants.APM_LR_100_LOG_REQ_UPDATE}" type="${SecurityConstants.ANCHOR_TYPE}" label="${UIWidgetConstants.LABEL_UPDATE}" id="${UIWidgetConstants.ID_UPDATE}" title="Update">onclick="javascript:update(this);"</prep:uiWidget>
	     
	      <prep:uiWidget name="${ProtectedResourcesConstants.APM_LR_100_BACK_TO_LOG_REQ}" type="${SecurityConstants.ANCHOR_TYPE}" label="${UIWidgetConstants.LABEL_LOGUSAGE_GOTO_REQ}" 
	      id="${UIWidgetConstants.ID_GOTO_LOGREQUEST}" title="LogRequest">onclick="javascript:logrequest(this);"</prep:uiWidget>
	     
     </td>	 
	  
	 	
	  <td align="left" width="20%"> 
		
	  </td> 
 </tr>
</table>
 
<c:if test="${logUsage.searchRequested eq 'Y'}"> 
<table class="detailstable1000" style="width:1200px; font-size: 11px;color: #000000; line-height: 11px; margin:0 auto;"> 
	<tr > 
	
	<td align="left">Found ${logUsage.searchResultRows} Results 
	</td>	
	
	<td align="left" width="15%" > 
	 
		 <c:if test="${logUsage.totalPg ne '0'}"> 
			Page ${logUsage.currentPg} of  
			${logUsage.totalPg} </c:if>
		</td>
		<td align="left" width="25%" >
		
		<c:if  test="${logUsage.totalPg ne '0'}">
		
		<c:if  test="${logUsage.totalPg ne '1'}">
		
			Page #&nbsp; 
			<input type="text" name="pageNumber" size="1" maxlength="5"  value="${logRequestSummary.currentPg}" onkeyup="javascript:resetPageNumber(this.value);" />
			<prep:uiWidget type="${SecurityConstants.ICON_TYPE }" 
			title="${UIWidgetConstants.LABEL_NAV_GO }" 
			id="${UIWidgetConstants.ID_NAV_GO}">
			onclick="javascript:setPageNumber();"</prep:uiWidget>
			 
			<%pageContext.setAttribute("firstPg","N", PageContext.PAGE_SCOPE);%>
         	<c:if test="${logRequestSummary.firstPg eq firstPg }">
			<prep:uiWidget type="${com.ascap.apm.common.utils.constants.SecurityConstants.ICON_TYPE}" title="${com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_NAV_FIRST}" id="${com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_NAV_FIRST}">onclick="javascript:setNavigationType('FIRST');"</prep:uiWidget>
			<prep:uiWidget type="${com.ascap.apm.common.utils.constants.SecurityConstants.ICON_TYPE}" title="${com.ascap.apm.common.utils.constants.UIWidgetConstants.LABEL_NAV_PREV}" id="${com.ascap.apm.common.utils.constants.UIWidgetConstants.ID_NAV_PREV}">onclick="javascript:setNavigationType('PREV');"</prep:uiWidget>
			</c:if> 
			<%pageContext.setAttribute("lastPg","N", PageContext.PAGE_SCOPE);%>
         <c:if test="${logRequestSummary.lastPg eq lastPg }">
			<prep:uiWidget type="${SecurityConstants.ICON_TYPE}" 
			title="${UIWidgetConstants.LABEL_NAV_NEXT}" 
			id="${UIWidgetConstants.ID_NAV_NEXT}">
			onclick="javascript:setNavigationType('NEXT');"</prep:uiWidget>
			
			<prep:uiWidget type="${SecurityConstants.ICON_TYPE }" title="${UIWidgetConstants.LABEL_NAV_LAST }" id="${UIWidgetConstants.ID_NAV_LAST}">onclick="javascript:setNavigationType('LAST');"</prep:uiWidget>
			</c:if>
				
		</c:if>
		</c:if>
		</td>
	</tr>
</table>
</c:if>
  
<%@ include file = "/views/common/coPageFilters.jsp"%>
<table class="contenttable alternatecolors" id="mainData" style="width:1200px; font-size: 11px; font-weight: normal; color: #000000; line-height: 11px; margin:0 auto;">   
<thead>   
<tr class="tablerowheader"  style="line-height: 2.5em;" >  
	<th width="1%"></th>  
	<th width="20%">Title</th>
	<th width="20%">Performer</th>
	<th width="5%">Work ID</th> 
	<th width="5%">Duration</th> 
	<th width="5%">Plays</th> 
	<th width="5%">Use Type</th>  
	<th width="5%">I/V</th>  
	<th width="9%">Status</th>  
</tr> 
</thead>  
<tbody>
 
 <% int sampleRowNum = 0; %>  	 	 
<c:if test="${not empty logUsage.searchResults}">
<c:forEach varStatus="currentIndexId" var="logUsageResult" items="${logUsage.searchResults}">
<% String useTypeTemp = ""; %> 	
<% String instvocalTemp = ""; %> 	
<% String updTitlesTemp = ""; %> 
<c:set var="useTypeTemp" value="${logUsageResult.usetype}" />
<c:set var="instvocalTemp" value="${logUsageResult.instvocal}" />  
 <c:choose>
<c:when test="${currentIndexId.index % 2 == 0}">
<tr class="o20" style="line-height:1.7em;">
</c:when>
<c:otherwise>
<tr style="line-height:1.7em;">
</c:otherwise>
</c:choose>
<td> 

<c:if test="${logUsageResult.readonly eq 'N'}">
<form:checkbox path="selectedIndex" value="${logUsageResult.wrkperfid}" style="padding-top:0px;" disabled="false"/>
</c:if>
<c:if test="${logUsageResult.readonly eq 'Y'}">
<input type="hidden" name="logUsages[${currentIndexId.index }].wrkperfid" value="${logUsageResult.wrkperfid}"/>
</c:if> 

</td> 
<td>
<c:if test="${logUsageResult.readonly eq 'N'}">
<input type="text" name="title" value="${logUsageResult.title}" maxlength="150" size="60"  styleId='result_${currentIndexId.index}'/> 
</c:if> 
<c:if test="${logUsageResult.readonly eq 'Y'}"> 
 <c:out value="${logUsageResult.title}" />
</c:if>
 
<form:hidden  path="updWrkperfids"   />
<form:hidden  path="updTitles"   />
<form:hidden   path="updArtists"   /> 
<form:hidden   path="updWorkids"   />
<form:hidden  path="updDurations"   />
<form:hidden   path="updPlays"   />
<form:hidden  path="updUsetypes"   /> 
<form:hidden   path="updInstvocals"  />
<form:hidden path="updStatuses"   />
<form:hidden path="selections"/>

 
</td> 
<td>
<c:if test="${logUsageResult.readonly eq 'N'}">
<input type="text" name="artist" value="${logUsageResult.artist}" maxlength="100" size="60"   styleId='result_${currentIndexId.index}'/>
</c:if> 
<c:if test="${logUsageResult.readonly eq 'Y'}"> 
 <c:out value="${logUsageResult.artist}" />
</c:if>
</td>  
<td>   
<c:if test="${logUsageResult.readonly eq 'N'}">
<input type="text" name="workid" value="${logUsageResult.workid}" maxlength="11" size="11"   styleId='result_${currentIndexId.index}'/>
</c:if>
<c:if test="${logUsageResult.readonly eq 'Y'}"> 
 <c:out value="${logUsageResult.workid}" />
</c:if>
</td>  
<td>  
<c:if test="${logUsageResult.readonly eq 'N'}">
<input type="text" name="duration" value="${logUsageResult.duration}" maxlength="11" size="11"   styleId='result_${currentIndexId.index}'/>
</c:if>
<c:if test="${logUsageResult.readonly eq 'Y'}"> 
 <c:out value="${logUsageResult.duration}" />
</c:if>
</td>
<td>  
<c:if test="${logUsageResult.readonly eq 'N'}">
<input type="text" name="plays" value="${logUsageResult.plays}" maxlength="11" size="11"  styleId='result_${currentIndexId.index}'/>
</c:if>
<c:if test="${logUsageResult.readonly eq 'Y'}"> 
 <c:out value="${logUsageResult.plays}" />
</c:if>
</td>
<td> 
<c:if test="${logUsageResult.readonly eq 'N'}">
<select name="usetypes">
<option value="F" <c:if test="${useTypeTemp eq 'F'}">selected</c:if>>F</option>
<option value="S" <c:if test="${useTypeTemp eq 'S'}">selected</c:if>>S</option>
</select> 
</c:if>
<c:if test="${logUsageResult.readonly eq 'Y'}"> 
  <%=useTypeTemp%>
</c:if>

</td>  
    
<td>   
<c:if test="${logUsageResult.readonly eq 'N'}">
<%-- <% pageContext.setAttribute("instvocalTemp1",instvocalTemp) %> --%>
<select name="instvocal">
<option value="I" <c:if test="${instvocalTemp eq 'I'}">selected</c:if>>I</option>
<option value="V" <c:if test="${instvocalTemp eq 'V'}">selected</c:if>>V</option>
</select>
</c:if>
<c:if test="${logUsageResult.readonly eq 'Y'}"> 
  <%=instvocalTemp%>
</c:if>
</td> 

<td>  <c:out value="${logUsageResult.status}"/> <input type="hidden" name="status" value="${logUsageResult.status}"  /> </td> 
</tr>
</c:forEach>
</c:if>
</tbody>
</table>


<table class="detailstable1000" style="width:1200px; font-size: 11px; font-weight: bold; color: #000000; line-height: 11px; margin:0 auto; padding-top:15px; padding-bottom:15px;"> 
 <tr> 
	  <td align="left" width="20%">
	     <prep:uiWidget name="${ProtectedResourcesConstants.APM_LR_100_LOG_REQ_UPDATE}" type="${SecurityConstants.ANCHOR_TYPE}" label="${UIWidgetConstants.LABEL_UPDATE}" id="${UIWidgetConstants.ID_UPDATE}" title="Update">onclick="javascript:update(this);"</prep:uiWidget>
	     <prep:uiWidget name="${ProtectedResourcesConstants.APM_LR_100_LOG_REQ_GOTO_USAGE}" type="${SecurityConstants.ANCHOR_TYPE}" label="${UIWidgetConstants.LABEL_LOGUSAGE_GOTO_REQ}" id="${UIWidgetConstants.ID_GOTO_LOGREQUEST}" title="LogRequest">onclick="javascript:logrequest(this);"</prep:uiWidget>
     </td>	 
	  <td align="right" width="20%"> 
		 <prep:uiWidget name="${ProtectedResourcesConstants.APM_LR_100_LOG_REQ_RELEASE}" type="${SecurityConstants.ANCHOR_TYPE}" label="${UIWidgetConstants.LABEL_DELETE}" id="${UIWidgetConstants.ID_DELETE}" title="Delete">onclick="javascript:del(this);"</prep:uiWidget>
	 </td>
 </tr>
</table>
</form:form>
</body>
</html>
