<!DOCTYPE HTML> 
<%@page	language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%> 

<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>
<%@ page import="com.ascap.apm.controller.utils.Utils"%>
<%@ page import="com.ascap.apm.controller.utils.HtmlSelectOption"%>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>

<%pageContext.setAttribute("UsageSuppliersList", HtmlSelectOption.getLookUpTable("UsageSuppliers"), PageContext.PAGE_SCOPE); %>
<% 	com.ascap.apm.vob.usage.EOFileListForm oForm = (com.ascap.apm.vob.usage.EOFileListForm) request.getAttribute("eoFileListForm"); %>
<% pageContext.setAttribute("distirbutionYearQuarter", HtmlSelectOption.getOptionsValues(Utils.getLatestDistributions(oForm.getActiveSurveyQuarterApm(),20)), PageContext.PAGE_SCOPE); %>
<% pageContext.setAttribute("distirbutionYearQuarterSpl", HtmlSelectOption.getOptionsValues(Utils.getLatestDistributionsSpl(oForm.getActiveSurveyQuarterApm(),20)), PageContext.PAGE_SCOPE); %>
 
  
<html>
<head> 
<title>APM</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="/views/common/uiWorkMatchWidges.jsp"/>

<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css">
<style>

.uibox {
width:150px;     
height:40px;
display: table-cell;
vertical-align: middle;
text-align: center;
-moz-border-radius: 10px;
-webkit-border-radius: 10px;
border-radius: 10px;
-moz-box-shadow: 0px 0px 4px #000000;
-webkit-box-shadow: 0px 0px 4px #000000;
box-shadow: 0px 0px 4px #000000;
-moz-background-clip: padding;
-webkit-background-clip: padding-box;
background-clip: padding-box;
}

.uibox_pend {
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr = '#7db9e8', endColorstr = '#1e5799');
-ms-filter: progid:DXImageTransform.Microsoft.gradient(startColorstr = '#7db9e8', endColorstr = '#1e5799');
background-image: -moz-linear-gradient(top, #7db9e8, #1e5799);
background-image: -ms-linear-gradient(top, #7db9e8, #1e5799);
background-image: -o-linear-gradient(top, #7db9e8, #1e5799);
background-image: -webkit-gradient(linear, center top, center bottom, from(#7db9e8), to(#1e5799));
background-image: -webkit-linear-gradient(top, #7db9e8, #1e5799);
background-image: linear-gradient(top, #7db9e8, #1e5799);
float:left;
padding-top: 9px;
margin-right: 3px;
}

.uibox_pend:hover {
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr = '#1e5799', endColorstr = '#7db9e8');
-ms-filter: progid:DXImageTransform.Microsoft.gradient(startColorstr = '#1e5799', endColorstr = '#7db9e8');
background-image: -moz-linear-gradient(top, #1e5799, #7db9e8);
background-image: -ms-linear-gradient(top, #1e5799, #7db9e8);
background-image: -o-linear-gradient(top, #1e5799, #7db9e8);
background-image: -webkit-gradient(linear, center top, center bottom, from(#1e5799), to(#7db9e8));
background-image: -webkit-linear-gradient(top, #1e5799, #7db9e8);
background-image: linear-gradient(top, #1e5799, #7db9e8);
float:left;
padding-top: 9px;
margin-right: 3px;
}

.uibox_prog {
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr = '#fac695', endColorstr = '#ef8d31');
-ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr = '#fac695', endColorstr = '#ef8d31')";
background-image: -moz-linear-gradient(top, #fac695, #ef8d31);
background-image: -ms-linear-gradient(top, #fac695, #ef8d31);
background-image: -o-linear-gradient(top, #fac695, #ef8d31);
background-image: -webkit-gradient(linear, center top, center bottom, from(#fac695), to(#ef8d31));
background-image: -webkit-linear-gradient(top, #fac695, #ef8d31);
background-image: linear-gradient(top, #fac695, #ef8d31);
float:left;
padding-top: 9px;
margin-right: 3px;
}
.uibox_prog:hover {
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr = '#ef8d31', endColorstr = '#fac695');
-ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr = '#ef8d31', endColorstr = '#fac695')";
background-image: -moz-linear-gradient(top, #ef8d31, #fac695);
background-image: -ms-linear-gradient(top, #ef8d31, #fac695);
background-image: -o-linear-gradient(top, #ef8d31, #fac695);
background-image: -webkit-gradient(linear, center top, center bottom, from(#ef8d31), to(#fac695));
background-image: -webkit-linear-gradient(top, #ef8d31, #fac695);
background-image: linear-gradient(top, #ef8d31, #fac695);
float:left;
padding-top: 9px;
margin-right: 3px;
}

.uibox_comp {
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr = '#c9de96', endColorstr = '#398235');
-ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr = '#c9de96', endColorstr = '#398235')";
background-image: -moz-linear-gradient(top, #c9de96, #398235);
background-image: -ms-linear-gradient(top, #c9de96, #398235);
background-image: -o-linear-gradient(top, #c9de96, #398235);
background-image: -webkit-gradient(linear, center top, center bottom, from(#c9de96), to(#398235));
background-image: -webkit-linear-gradient(top, #c9de96, #398235);
background-image: linear-gradient(top, #c9de96, #398235);
float:left;
padding-top: 9px;
margin-right: 3px;
}
.uibox_comp:hover {
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr = '#398235', endColorstr = '#c9de96');
-ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr = '#398235', endColorstr = '#c9de96')";
background-image: -moz-linear-gradient(top, #398235, #c9de96);
background-image: -ms-linear-gradient(top, #398235, #c9de96);
background-image: -o-linear-gradient(top, #398235, #c9de96);
background-image: -webkit-gradient(linear, center top, center bottom, from(#398235), to(#c9de96));
background-image: -webkit-linear-gradient(top, #398235, #c9de96);
background-image: linear-gradient(top, #398235, #c9de96);
float:left;
padding-top: 9px;
margin-right: 3px;
}


.uiboxtext {
	font-size: 10pt; font-weight:normal; color:white;
}
.uiboxtext:visited {
	font-family:  Lucinda, Arial, sans-serif; font-size: 10pt; font-weight:normal; color:red;
}
.uiboxtext>a {
	color:#161279;
}
.uiboxtext>a:hover {
	color:#161279;
	text-decoration:none;
}

.outer {
	width:1000px;
	margin:0px auto;
}
</style>

<script type="text/javascript">

var manageSelectAll = function () {
	if($('input[id=_selAll]').is(':checked')) {$('input[name=selectedIndex]:not(:disabled)').attr('checked', true);} else {$('input[name=selectedIndex]:not(:disabled)').attr('checked',false);}
};


$(function() {
	
	if($('input[name=filterFileStatus]').val() == 'LOAD2APM_PEND' ) {
		
		$("input[name=distQuarters]").each(function(){
			if(this.value == '') {
				$(this).val($("input[name=activeSurveyQuarterEo]").val().replace ("Q","D")); 
			}
		});
	}
	
	if($('input[name=loadThresholdExceeded]').val() === 'Y') {
		
		
		
		$("input[name=selectedIndex]:checked").each(function(){			
			$(this).closest('tr').find('input[name=distQuarters]').val($(this).closest('tr').find('input[name=distQuarter]').val())
		});
		
		
		
		var newDiv = $(document.createElement('div')); 
       	var txt = ' The number of roll up records exceeds the daily roll up threshold. Are you sure you want to continue? ';		       	
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
 					$( this ).dialog( "close" );
 					rollupReset(obj);
 					},		 					
 				"OK": function() {
 					$( this ).dialog( "close" );
 					rollupSub();
 				}
 			}
		});
	}
	
	if($('input[name=filterFileStatus]').val() == 'SUCCESS_FILES'
	||
	$('input[name=filterFileStatus]').val() == 'NOT_FOR_PROCESS'
	) {
		sortTable('EO-100','mainData', []);
	}
	
});


function applyToSelected(obj) {
	
	var val = $('input[name=perfQuartersAll]').val();
	
	if($.trim(val) === '') {
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.filedash.perfQuartersAll.blank"/></span>');
 		location.href="#"; 
 		return;
	}
	
	if($("table#mainData tr input[name='selectedIndex']:checked").length > 0) {		
		$("input[name=selectedIndex]:checked").each(function(){
			var checkedIndex = this.value;
			$("input[name=perfQuarters]:eq("+checkedIndex+")").val(val);
		});
	}
	
	else {
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.filedash.perfQuartersAll.selection.invalid"/></span>');
 		location.href="#"; 
 		return;
	}
	
	
}


function details(navigation, filter) {
	$('input[name=loadThresholdExceeded]').val('');
	document.forms[0].filterFileStatus.value = navigation;
	document.forms[0].operationType.value = 'LIST';
	if(!filter) {
		$('select[name=filterPerformanceQuarter]').val('');
		$('select[name=filterPerfPeriod]').val('');
		$('select[name=filterDistributionQuarter]').val('');
		$('select[name=filterCompletedSupplierCode]').val('');
	}
	else if(filter === 'FILTER' && navigation === 'SUCCESS_FILES'){
		if($('select[name=filterPerformanceQuarter]').val() == ''
			&& $('select[name=filterPerfPeriod]').val() == ''
			&& $('select[name=filterDistributionQuarter]').val() == ''
			&& $('select[name=filterCompletedSupplierCode]').val() == ''
			) {
				$("#serverErrorMessages").html("");
				$("#errorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.filedash.perfQuartersANDdistQtr.blank"/></span>');
		 		location.href="#"; 
		 		return;
		}
	}
	document.forms[0].submit();
}


function channelList(navigation) {
	document.forms[0].operationType.value = navigation;
	document.forms[0].submit();
}

function setNavigationType(navigation) {
	document.forms[0].navigationType.value = navigation;
	document.forms[0].submit();
}

function rollupSub() {
	document.forms[0].operationType.value = 'LOAD2APM';
	document.forms[0].submit();
}

function updateFileInventorySub() {
	document.forms[0].operationType.value = 'UPDATEFILEINVENTORY';
	document.forms[0].submit();
}

function updateFileInventoryIGSub() {
	document.forms[0].operationType.value = 'IGNORE_ERRORS';
	document.forms[0].submit();
}



function rollupReset(obj) {
			unCheckSub(obj);
	 		location.href="#"; 
	 		return;
}

function updateFileInventoryReset(obj) {
			unCheckSub(obj);
	 		location.href="#"; 
	 		return;
}


function processSub() {
	document.forms[0].operationType.value = 'LOAD2EO';
	document.forms[0].submit();
}

function processReset(obj) {
	unCheckSub(obj);
	location.href="#"; 
	return;
}


function rollup(obj) {
	
		var distqtrexists = true;
		var distqtrvalid = true;
	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html("");
		if($("table#mainData tr input[name=selectedIndex]:checked").length == 0) {	
			$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.file.selection.invalid"/></span>');
			unCheckSub(obj);
	 		location.href="#"; 
	 		return;
	 	}
		else {
			$("input[name=selectedIndex]:checked").each(function(){
				var checkedIndex = this.value;
				if($(this).closest('tr').find('input[name=distQuarters]').val() === '') {
					distqtrexists = false;
				}
				
				if($(this).closest('tr').find('input[name=distQuarters]').val() <  $("input[name=activeSurveyQuarterEo]").val().replace ("Q","D")) {
					distqtrvalid = false; 
				}
				//checkedIndex = checkedIndex.replace("XXXXXX", $(this).closest('tr').find('input[name=distQuarters]').val());
				checkedIndex = checkedIndex.substring(0, checkedIndex.lastIndexOf(" ")) +
				                " " + $(this).closest('tr').find('input[name=distQuarters]').val() +
				                " " + $(this).closest('tr').find('input[name=perfPeriod]').val() ;
				//alert(':'+checkedIndex+':');
				$(this).val(checkedIndex);
				
			});
				
		}
		
		if(!distqtrexists) {	
			$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.filedash.distqtr.invalid"/></span>');
			unCheckSub(obj);
	 		location.href="#"; 
	 		return;
	 	}
		if(!distqtrvalid) {	
			$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.filedash.distqtr.lt.currentqtr"/></span>');
			unCheckSub(obj);
	 		location.href="#"; 
	 		return;
	 	}
	 	
	 			var newDiv = $(document.createElement('div')); 
		       	var txt = 'Do you confirm that all files have been received for the period and would like to proceed with rollup? ';		       	
				newDiv.html(txt);
				newDiv.attr('title','Rollup?');
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
		 					rollupReset(obj);
		 					},		 					
		 				"OK": function() {
		 					$( this ).dialog( "close" );
		 					rollupSub();
		 				}
		 			}
				});
	 	
	 	
}
 

function updateFileInventory(obj) {
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html("");
		if($("table#mainData tr input[name=selectedIndex]:checked").length == 0) {	
			$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.file.selection.invalid"/></span>');
			unCheckSub(obj);
	 		location.href="#"; 
	 		return;
	 	}
	 	
	 			var newDiv = $(document.createElement('div')); 
		       	var txt = 'Are you sure you want to Process Corrections for the selected providers? ';		       	
				newDiv.html(txt);
				newDiv.attr('title','Reload?');
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
		 					updateFileInventoryReset(obj);
		 					},		 					
		 				"OK": function() {
		 					$( this ).dialog( "close" );
		 					updateFileInventorySub();
		 				}
		 			}
				});
	 	
	 	
}


function updateFileInventoryIG(obj) {
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html("");
		if($("table#mainData tr input[name=selectedIndex]:checked").length == 0) {	
			$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.file.selection.invalid"/></span>');
			unCheckSub(obj);
	 		location.href="#"; 
	 		return;
	 	}
	 	
	 			var newDiv = $(document.createElement('div')); 
		       	var txt = 'Are you sure you want to Ignore Errors for the selected providers? ';		       	
				newDiv.html(txt);
				newDiv.attr('title','Ignore Errors?');
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
		 					updateFileInventoryReset(obj);
		 					},		 					
		 				"OK": function() {
		 					$( this ).dialog( "close" );
		 					updateFileInventoryIGSub();
		 				}
		 			}
				});
	 	
	 	
}

function enablePerfQtr(obj) {
	var cbNum = obj.value;
	if(obj.checked) {$("input[name=perfQuarters]:eq("+cbNum+")").val('').attr("disabled", true);
	$(".ui-yearquarterpicker-trigger:eq("+cbNum+")").hide();
	$(".ui-icon-scissors:eq("+cbNum+")").hide();	
	}
	else {$("input[name=perfQuarters]:eq("+cbNum+")").attr("disabled", false);
	$(".ui-yearquarterpicker-trigger:eq("+cbNum+")").show();
	$(".ui-icon-scissors:eq("+cbNum+")").show();
	}
}


function process(obj) {
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html("");
		if($("table#mainData tr input[name=selectedIndex]:checked").length == 0) {	
		$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.file.selection.invalid"/></span>');
		unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	 	}
	 	else {
	 		var perfQtrExists = true;
	 		$("input[name=selectedIndex]:checked").each(function(){
				var checkedIndex = this.value;
				if($.trim($("input[name=perfQuarters]:eq("+checkedIndex+")").val()) == '') {
					perfQtrExists = false;
				}
				else {
					var rxDatePattern = /^(\d{1,4}[Q][1-4])$/;
    				var dtArray = $("input[name=perfQuarters]:eq("+checkedIndex+")").val().match(rxDatePattern);
		    		if (dtArray == null) { 
        				perfQtrExists = false;
        			}
        		}
			});
			
			
			
			if(!perfQtrExists) {
				$("#serverErrorMessages").html("");
				$("#errorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.filedash.perfqtr.invalid"/></span>');		
				unCheckSub(obj);
		 		location.href="#"; 
		 		return;
			}	
			
			// PerfPeriod validation
			var perfPeriodExists = true;
	 		$("input[name=selectedIndex]:checked").each(function(){
				var ppI = this.value;  
				var pp = $(this).closest('tr').find('select[name=perfPeriods]').val(); 
				
				if(typeof(pp)  === "undefined") 
				pp = "-1"; 
				
				if ($.trim($("input[name=supplierTypes]:eq("+ppI+")").val()) == "CATALOG" && pp == ''){
				perfPeriodExists = false; 
				} 
			}); 
			
			if(!perfPeriodExists) {
				$("#serverErrorMessages").html("");
				$("#errorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.filedash.perfperiod.invalid"/></span>');		
				unCheckSub(obj);
		 		location.href="#"; 
		 		return;
			}
			// 
			
			$("input[name=selectedIndex]:checked").each(function(){
				var cI = this.value;
				$("input[name=donotProcessFlags]:eq("+cI+")").val('N');
			});
			
			var newDiv = $(document.createElement('div')); 
	       	var txt = 'You have selected to load '+$("input[name=selectedIndex]:checked").length +' file(s).  Do you want to continue?';		       	
			newDiv.html(txt);
			newDiv.attr('title','Continue?');
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
	 					processReset(obj);
	 					},		 					
	 				"OK": function() {
	 					$( this ).dialog( "close" );
	 					//disableFields();
	 					processSub();
	 				}
	 			}
			});
	 	}
	 	
}

function disableFields() {
	//console.log('disabling ......');
	$("input[name=selectedIndex]:not(:checked)").each(function(){
		/* console.log($(this).val() + "   " + this.checked);*/
		$(this).closest('tr').find('input[type=hidden]').attr("disabled", "true");
		$(this).closest('tr').find('input[name=selectedIndex]').attr("disabled", "true");
		$(this).closest('tr').find('input[name=perfQuarters]').attr("disabled", "true");
		$(this).closest('tr').find('input[name=perfPeriods]').attr("disabled", "true");
		
		$(this).closest('tr').find('input[name=suppCode]').attr("disabled", "true");
		$(this).closest('tr').find('input[name=fileId]').attr("disabled", "true");
		$(this).closest('tr').find('input[name=fileIds]').attr("disabled", "true");
		$(this).closest('tr').find('input[name=fileName]').attr("disabled", "true");
		$(this).closest('tr').find('input[name=fileTo]').attr("disabled", "true");
		$(this).closest('tr').find('input[name=createDate]').attr("disabled", "true");
		$(this).closest('tr').find('input[name=donotProcessFlags]').attr("disabled", "true");
	});
	
	$("input[name=selectedIndex]:checked").each(function(){ 
		$(this).closest('tr').find('input[name=perfPeriods]').attr("enabled", "true"); 
	});
}


function donotprocess(obj) {
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html("");
		if($("table#mainData tr input[name=selectedIndex]:checked").length == 0) {	
		$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.file.selection.invalid"/></span>');
		unCheckSub(obj);
	 	location.href="#"; 
	 	return;
	 	}
	 	else {			
	 	
	 		$("input[name=selectedIndex]:checked").each(function(){
				var cI = this.value;
				$("input[name=donotProcessFlags]:eq("+cI+")").val('Y');
				$("input[name=perfQuarters]:eq("+cI+")").val('');
			});
			
			
			var newDiv = $(document.createElement('div')); 
	       	var txt = ''+$("input[name=selectedIndex]:checked").length +' file(s) will not be loaded.  Do you want to continue? ';		       	
			newDiv.html(txt);
			newDiv.attr('title','Continue?');
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
	 					processReset(obj);
	 					},		 					
	 				"OK": function() {
	 					$( this ).dialog( "close" );
	 					processSub();
	 				}
	 			}
			});
	 	}
}


function update(obj) {
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html("");
	if($("table#mainData tr input[name=selectedIndex]:checked").length == 0) {	
		$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.file.selection.invalid"/></span>');
		unCheckSub(obj);
	 	location.href="#"; 
	 	return;}
	 	else {	
	 	
	 	
	 	$("input[name=donotProcessFlagsTemp]:checked").each(function(){
			var cI = this.value;
			$("input[name=donotProcessFlags]:eq("+cI+")").val('Y');
		});
	 	
	 	var perfQtrExists = true;
		$("input[name=selectedIndex]:checked").each(function(){
			var checkedIndex = this.value;
			if($.trim($("input[name=perfQuarters]:eq("+checkedIndex+")").val()) == '') {
				if($.trim($("input[name=donotProcessFlags]:eq("+checkedIndex+")").val()) == 'N') {
					perfQtrExists = false;
				}
			}
			else {
				var rxDatePattern = /^(\d{1,4}[Q][1-4])$/;
    			var dtArray = $("input[name=perfQuarters]:eq("+checkedIndex+")").val().match(rxDatePattern);
		    	if (dtArray == null) { 
        			perfQtrExists = false;
        		}
        	}
		});
		if(!perfQtrExists) {
			$("#serverErrorMessages").html("");
			$("#errorMessages").html("");
			$("#uierror").html('<span class=txtRed><li><fmt:message key="error.apm.filedash.perfqtr.invalid"/></span>');		
			unCheckSub(obj);
	 		location.href="#"; 
	 		return;
		}
	 	
	 	
	 	
	 	
	 	
	 	 	
	document.forms[0].operationType.value = 'LOAD2EO';
	document.forms[0].submit();
	 	}
}

</script>
</head>



<body style="width:1000px;margin: 0px auto;">

<div class="menubg"  style="width: 100%; border: 0px; height:90px;">    
<jsp:include page="/views/menu.jsp"/>  
</div>
<div></div>
<p><p>
<br/>
<div style="width:800px;margin:0px auto;font-size:1.5em;font-weight:600;">
Processing Distribution Quarter: <b><c:out value="${eoFileListForm.activeSurveyQuarterApm}"/>
(<c:out value="${eoFileListForm.currentDistQuarterEO}"/>)</b>
</div>
<form:form action="eoFiles" modelAttribute="eoFileListForm">

<form:hidden path="filterFileStatus" />
<form:hidden path="operationType"/>
<form:hidden path="activeSurveyQuarterEo"/>
<form:hidden path="activeSurveyQuarterApm"/>
<form:hidden path="currentPerformanceQuarterEO"/>

<br/>

<table class="outer">
<tr>
<td>
<div class="outer">
<div style="float:left;width:220px;padding-top:10px;font-size:13px;text-align:left;font-weight:900;color:#1e5799;">User Action Required:</div>
<div class="uibox uibox_pend"><span class="uiboxtext"><a href="#" onclick="javascript:details('NEW');">New File Review (<c:out value="${eoFileListForm.newReviewFiles}"/>)</a> </span></div>
<div class="uibox uibox_pend"><span class="uiboxtext"><a href="#" onclick="javascript:details('CHANNELS');">Channel Review (<c:out value="${eoFileListForm.channelReviewFiles}"/>)</a> </span></div>
<div class="uibox uibox_pend"><span class="uiboxtext"><a href="#" onclick="javascript:details('LOAD2APM_PEND');">Load to APM (<c:out value="${eoFileListForm.rolllupReviewFiles}"/>)</a> </span></div>
</div>
</td>
</tr>
<tr height="8px"></tr>
<tr>
<td>
<div class="outer">
<div style="float:left;width:220px;padding-top:10px;font-size:13px;text-align:left;font-weight:900;color:#ef8d31;">Pending System Processing:</div>
<div class="uibox uibox_prog"><span class="uiboxtext"><a href="#" onclick="javascript:details('LOAD2EO_PEND');">Load To EO DB (<c:out value="${eoFileListForm.eoLoadFiles}"/>)</a> </span></div>
<div class="uibox uibox_prog"><span class="uiboxtext"><a href="#" onclick="javascript:details('LOAD2APM_PROG');">Load To APM (<c:out value="${eoFileListForm.apmLoadFiles}"/>)</a> </span></div>
<div class="uibox uibox_prog"><span class="uiboxtext"><a href="#" onclick="javascript:details('ERROR_FILES');">In Error (<c:out value="${eoFileListForm.errorFiles}"/>)</a></span></div>
</div>
</td>
</tr>
<tr height="8px"></tr>
<tr>
<td>
<div class="outer">
<div style="float:left;width:220px;padding-top:10px;font-size:13px;text-align:left;font-weight:900;color:#398235;">Complete:</div>
<div class="uibox uibox_comp"><span class="uiboxtext"><a href="#" onclick="javascript:details('NOT_FOR_PROCESS');">Not For Processing (<c:out value="${eoFileListForm.noProcessFiles}"/>)</a></span></div>
<div class="uibox uibox_comp"><span class="uiboxtext"><a href="#" onclick="javascript:details('SUCCESS_FILES');">Loaded to APM (<c:out value="${eoFileListForm.successFiles}"/>)</a></span></div>
</div>
</td>
</tr>
</table>


 <table class="errortable" style="width:1000px;">
		<tr>
			<td>
				<div id="serverErrorMessages">
				<span class="txtGreen"><c:out value="${systemmessage}" /></span>
				<span class="txtRed"><c:out value="${systemerror}" /></span>
				</div>
				<div id="uierror"></div>
				<div id="errorMessages"></div>
			</td>
		</tr>
</table>


<c:if test="${not empty eoFileListForm.filterFileStatus}">
<%-- ===================================== NEW FILES ======================================================================================================================================== --%>
<c:if test="${eoFileListForm.filterFileStatus eq 'NEW'}">
<br/>
<table class="contenttable" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >
<tr>
<td align="left"><h3>New File Review (<c:out value="${eoFileListForm.newReviewFilesFiltered}"/>)</h3>
</td>
</tr>
</table>

<table class="buttonstable" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >
<tr>
<td width="50%">
<span class="txtBlk_bold" style="font-size:1.1em;">Perf Qtr </span><form:input path="perfQuartersAll" value="" id='<%="yyyyQQ_targetYearQuarter_applyToAllPerfQtr"%>' size="8" readonly="true"/><%--<a href="javascript:clearField('<%="yyyyQQ_targetYearQuarter_applyToAllPerfQtr"%>');" class="<%=UIWidgetConstants.STYLE_CLEAR_FIELD_ICON_ENABLED%>" title="<%=UIWidgetConstants.TITLE_CLEAR_FIELD%>"></a>--%>
&nbsp;&nbsp;
<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_FD_100_FILE_DASHBOARD_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_APPLY %>" id="<%=UIWidgetConstants.ID_APPLY %>" title="<%=UIWidgetConstants.LABEL_APPLY %>"> onclick="javascript:applyToSelected(this);"</prep:uiWidget> 
</td>

<td width="50%">
<span class="txtBlk_bold" style="font-size:1.1em;">Supplier </span><form:select path="filterSupplierCode" id="filterSupplier">
			<form:option value="" />
			<form:options items="${UsageSuppliersList}" itemLabel="displayName" itemValue="id" />
		</form:select>
&nbsp;&nbsp;
<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_FD_100_FILE_DASHBOARD_SEARCH %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_APPLY_FILTER %>" id="<%=UIWidgetConstants.ID_APPLY_FILTER%>" title="Filter">onclick="javascript:pleaseWait();details('NEW');"</prep:uiWidget> 
</td>
</tr>
</table>



<table class="contenttable alternatecolors" id="mainData" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >     
<thead> 
<tr class="tablerowheader" style="line-height: 2.5em;" >
	<th width="3%"><input type="checkbox" name="selAll" id = "_selAll" value="Y" onclick="javascript:manageSelectAll();"/></th>  	
	<th width="15%">Perf Qtr</th>  	
	<th width="10%">Perf Period</th>  	
	<th width="5%">Supplier</th>
	<th width="5%">File Id</th> 
	<th width="20%">File Name</th> 
	<th width="20%">File Location</th> 
	<th width="10%">Date Received</th>
	<%-- <th width="5%">Dont Pr</th>--%>  
</tr>
</thead>

<tbody>
<c:if test="${not empty eoFileListForm.searchResults}">
<c:forEach items="${eoFileListForm.searchResults}" var="eoFile" varStatus="currentIndexId">
<c:choose>
<c:when test="${currentIndexId.index % 2 == 0}">
<tr class="o20" style="line-height:2.2em;">
</c:when>
<c:otherwise>
<tr style="line-height:2.2em;">
</c:otherwise>
</c:choose>
<td align="center">
<c:if test="${empty eoFile.statusCode}">
<form:checkbox path="selectedIndex" value="${currentIndexId.index}"></form:checkbox>
</c:if>
</td>

<td align="left">
<c:if test="${empty eoFile.statusCode}">
<form:input path="perfQuarters" value="" id='yyyyQQ_targetYearQuarter_${currentIndexId.index}' size="8" readonly="true"/>
<a href="javascript:clearField('yyyyQQ_targetYearQuarter_${currentIndexId.index}');" class="${UIWidgetConstants.STYLE_CLEAR_FIELD_ICON_ENABLED}" title="${UIWidgetConstants.TITLE_CLEAR_FIELD}"></a>
</c:if>
<c:if test="${not empty eoFile.statusCode}">
<c:out value="${eoFile.perfQuarter}"/>
</c:if>
<form:hidden path="donotProcessFlags" value="N"/>
</td>
<td align="center">        

<c:if test="${eoFile.supplierType eq 'CATALOG'}">
<c:if test="${empty eoFile.perfPeriod}">
<c:if test="${eoFile.frequency eq 'N'}">

<form:select path="perfPeriods" value="" id='perfPeriods_${currentIndexId.index}'  >
			<form:option value="" />
			<form:option value="1" />
			<form:option value="2" />
			<form:option value="3" />
			<form:option value="4" />
			<form:option value="5" />
			<form:option value="6" />
			<form:option value="7" />
			<form:option value="8" />
			<form:option value="9" />
			<form:option value="10" />
			<form:option value="11" />
			<form:option value="12" />
		</form:select>   
</c:if> 
<c:if test="${eoFile.frequency eq 'Q'}">
<form:select path="perfPeriods" value="" id="perfPeriods_${currentIndexId.index}"> 
			<form:option value="" />
			<form:option value="3" /> 
			<form:option value="6" /> 
			<form:option value="9" /> 
			<form:option value="12" />
		</form:select> 
		
</c:if> 
</c:if>	
<c:if test="${not empty eoFile.perfPeriod}">
 <c:out value="${eoFile.perfPeriod}"/>  
 <input type="hidden" name="perfPeriods" value='<c:out value="${eoFile.perfPeriod}"/>'/>
</c:if> 
</c:if>
 <c:if test="${eoFile.supplierType ne 'CATALOG'}">
<input type="hidden" name="perfPeriods" value="-1"/>
</c:if>   
</td>
<td align="left"><c:out value="${eoFile.suppCode}"/></td>
<td align="center"><c:out value="${eoFile.fileId}"/>
<input type="hidden" name="fileIds" value='<c:out value="${eoFile.fileId}"/>'/>
<input type="hidden" name="supplierTypes" value='<c:out value="${eoFile.supplierType}"/>'/>
</td>
<td align="left"><c:out value="${eoFile.fileName}"/></td>
<td align="left"><c:out value="${eoFile.fileTo}"/>  </td> 
<td align="center"><c:out value="${eoFile.createDate}"/></td>
</c:forEach>
</c:if>

</tbody>
</table>

<table class="buttonstable">
 <tr  class="searchpagestatus" style="font-weight:800;">
 <td align="left" style="padding-top:5px;">&nbsp;
 </td>
 </tr>
 </table>
<table class="buttonstable" style="width:1000px;">
 <tr>
 <td align="left" width="20%"> 
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_FD_100_FILE_DASHBOARD_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_PROCESS %>" id="<%=UIWidgetConstants.ID_PROCESS%>" title="Update">onclick="javascript:process(this);"</prep:uiWidget>
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_FD_100_FILE_DASHBOARD_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_DO_NOT_PROCESS %>" id="<%=UIWidgetConstants.ID_DO_NOT_PROCESS%>" title="Update">onclick="javascript:donotprocess(this);"</prep:uiWidget>
 </td>	
</tr>
 </table>

</c:if>

<%-- ===================================== CHANNEL FILES =========================================================================================================== --%>
<c:if test="${eoFileListForm.filterFileStatus eq 'CHANNELS'}">
<br/>
<table class="contenttable" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >
<tr>
<td align="left">
<h3>
Channel Review (<c:out value="${eoFileListForm.channelReviewFiles}"/>)
</h3>
</td>
</tr>
</table>


<table class="contenttable alternatecolors" id="mainData" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >     
<thead> 
<tr class="tablerowheader" style="line-height: 2.5em;" >
	<th width="15%">Supplier</th>
	<th width="15%">Unassigned Channels</th>
</tr>
</thead>
<tbody>

<c:if test="${not empty eoFileListForm.searchResults}">
<c:forEach items="${eoFileListForm.searchResults}" var="eoFile" varStatus="currentIndexId">

<c:choose>
<c:when test="${currentIndexId.index % 2 == 0}">
<tr class="o20" style="line-height:1.7em;">
</c:when>
<c:otherwise>
<tr style="line-height:1.7em;">
</c:otherwise>
</c:choose>
<td align="left"><c:out value="${eoFile.suppCode}"/></td>
<td align="center"><c:out value="${eoFile.unassignedChannelCount}"/></td>
</c:forEach>
</c:if>

</tbody>
</table>

<table class="buttonstable">
 <tr  class="searchpagestatus" style="font-weight:800;">
 <td align="left" style="padding-top:5px;">&nbsp;
 </td>
 </tr>
 </table>
 
 <table class="buttonstable" style="width:1000px;">
 <tr>
 <td align="left" width="20%"> 
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_FD_100_FILE_DASHBOARD_SEARCH%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_GO_TO_CHANNEL_LIST %>" id="<%=UIWidgetConstants.ID_GO_TO_CHANNEL_LIST%>" title="Update">onclick="javascript:channelList('CHANNEL_LIST');"</prep:uiWidget>
 </td>	
</tr>
 </table>
 
</c:if>



<%-- ===================================== LOAD 2 APM PEND  ======================================================================================================================================================== --%>
<c:if test="${eoFileListForm.filterFileStatus eq 'LOAD2APM_PEND'}">

<form:hidden path="loadThresholdExceeded"/>

<br/>
<table class="contenttable" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >
<tr>
<td align="left">
<h3>
Load to APM (<c:out value="${eoFileListForm.rolllupReviewFiles}"/>)
</h3>
</td>
</tr>
</table>

<table class="contenttable alternatecolors" id="mainData" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >     
<thead> 
<tr class="tablerowheader" style="line-height:1.5em;" >
	<th width="12%">Dist Qtr</th>
	<th width="15%">File Id</th>
	<th width="39%">File Name</th>
	<th width="10%">Total Records</th>
	<th width="7%">Parse Error Records</th>
	<th width="7%">Validation Error Records</th>
	<th width="10%">Error Status</th>
</tr>
</thead>

<tbody>
<c:if test="${not empty eoFileListForm.searchResults}">
<c:forEach items="${eoFileListForm.searchResults}" var="eoFile" varStatus="currentIndexId">

<c:choose>
<c:when test="${currentIndexId.index % 2 == 0}">
<tr class="o20" style="line-height:1.7em;">
</tr>
</c:when>
<c:otherwise>
<tr style="line-height:1.7em;">
</c:otherwise>
</c:choose>
<c:if test="${empty eoFile.fileId}">
<td class="o40" style="font-weight:bold;line-height:2.5em;" align="left">
<%-- <form:checkbox path="selectedIndex">
<c:out value="${eoFile.suppCode}"/><c:out value="${eoFile.perfQuarter}"/><c:out value="${eoFile.distQuarter}"/><c:out value="${eoFile.perfPeriod}"/>
</form:checkbox>  --%>


<c:if test="${eoFile.supplierType eq 'DISTRIBUTION'}">
<form:input path="distQuarters" value="" id='yyyyDD_targetYearQuarter_${currentIndexId.index}' 
size="6" readonly="true"/>
<a href='javascript:clearField("yyyyQQ_targetYearQuarter_${currentIndexId.index}");' 
class="${UIWidgetConstants.STYLE_CLEAR_FIELD_ICON_ENABLED}" title="${UIWidgetConstants.TITLE_CLEAR_FIELD}">
</a>
</c:if>
<c:if test="${eoFile.supplierType ne 'DISTRIBUTION'}"></c:if>

</td>
<td colspan="6" class="o40" style="font-weight:bold;line-height:2.5em;" align="justify">&nbsp;&nbsp;
<c:out value="${eoFile.suppCode}"/>&nbsp;&nbsp;
<c:out value="${eoFile.perfQuarter}"/>
<c:if test="${not empty eoFile.perfPeriod}">
-<c:out value="${eoFile.perfPeriod}"/>
</c:if>
&nbsp;&nbsp;
/ Last Rollup Cnt: <c:out value="${eoFile.lastRollupCount}"/> 
</td>



<form:hidden path="perfQuarter" id='perfQuarter_${currentIndexId.index}'/> 
<form:hidden path="suppCode" id='suppCode_${currentIndexId.index}'/> 
<form:hidden path="distQuarter" id='distQuarter_${currentIndexId.index}'/>
<form:hidden path="perfPeriod" id='perfPeriod_${currentIndexId.index}'/>

</c:if>
<c:if test="${not empty eoFile.fileId}">
<td></td>
<td align="center"><c:out value="${eoFile.fileId}"/></td>
<td align="left"><c:out value="${eoFile.fileName}"/></td>
<td align="right"><c:out value="${eoFile.fileRowCountFormatted}"/></td>
<td align="right"><c:out value="${eoFile.fileRejectCountNew}"/></td>
<td align="right"><c:out value="${eoFile.fileVldRejectcount}"/></td> 
<td align="center"><c:out value="${eoFile.errorCorrectedStatus}"/></td>
</c:if>
</c:forEach>
</c:if>
</tbody>
</table>


<table class="buttonstable">
 <tr  class="searchpagestatus" style="font-weight:800;">
 <td align="left" style="padding-top:5px;">&nbsp;
 </td>
 </tr>
 </table>
 
<table class="buttonstable" style="width:1000px;">
 <tr>
 <td align="left" width="20%"> 
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_FD_100_FILE_DASHBOARD_UPDATE%>" 
 type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_ROLLUP_AND_LOAD_APM %>" id="<%=UIWidgetConstants.ID_ROLLUP_AND_LOAD_APM%>" title="Update">onclick="javascript:rollup(this);"</prep:uiWidget>
 </td>	
   
 <td align="right" width="20%"> 
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_FD_100_FILE_DASHBOARD_UPDATE%>" 
 type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_UPDATE_FILEINVENTORY %>"   
 id="<%=UIWidgetConstants.ID_UPDATE_FILEINVENTORY%>" title="Update">onclick="javascript:updateFileInventory(this);"</prep:uiWidget>
  
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_FD_100_FILE_DASHBOARD_UPDATE%>" 
 type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_UPDATE_FILEINVENTORY_IGNORE %>"   
 id="<%=UIWidgetConstants.ID_UPDATE_FILEINVENTORY_IGNORE%>" title="Update">onclick="javascript:updateFileInventoryIG(this);"</prep:uiWidget>
 </td>
</tr>
 </table>
</c:if>




<%-- ===================================== LOAD 2 EO PEND  ======================================================================================================================================================== --%>
<c:if test="${eoFileListForm.filterFileStatus eq 'LOAD2EO_PEND'}">
<br/>
<table class="contenttable" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >
<tr>
<td align="left">
<h3>
Load To EO DB (<c:out value="${eoFileListForm.eoLoadFiles}"/>)
</h3>
</td>
</tr>
</table>

<table class="contenttable alternatecolors" id="mainData" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >     
<thead> 
<tr class="tablerowheader" style="line-height: 2.5em;" >
	<th width="10%">Supplier</th>
	<th width="5%">File Id</th>
	<th width="25%">File Name</th>
	<th width="25%">File Location</th>
	<th width="8%">Date Received</th>
	<%-- <th width="8%">ISD Load Flag</th>--%>
	<th width="8%">Supp EO</th>
	<th width="8%">Supp APM</th>
</tr>
</thead>


<tbody>
<c:if test="${not empty eoFileListForm.searchResults}">
<c:forEach items="${eoFileListForm.searchResults}" var="eoFile" varStatus="currentIndexId">
<c:choose>
<c:when test="${currentIndexId.index % 2 == 0}">
<tr class="o20" style="line-height:1.7em;">
</c:when>
<c:otherwise>
<tr style="line-height:1.7em;">
</c:otherwise>
</c:choose>
<td align="left"><c:out value="${eoFile.suppCode}"/></td>
<td align="center"><c:out value="${eoFile.fileId}"/></td>
<td align="left"><c:out value="${eoFile.fileName}"/></td>
<td align="left"><c:out value="${eoFile.fileTo}"/></td>
<td align="center"><c:out value="${eoFile.createDate}"/></td>
<td align="center"><c:out value="${eoFile.eoSuppFormatFlag}"/></td>
<td align="center"><c:out value="${eoFile.apmSuppFormatFlag}"/></td>
</c:forEach>
</c:if>
</tbody>

</table>

<table class="buttonstable">
 <tr  class="searchpagestatus" style="font-weight:800;">
 <td align="left" style="padding-top:5px;">&nbsp;
 </td>
 </tr>
 </table>
</c:if>

<%-- ===================================== LOAD 2 APM PROG  ======================================================================================================================================================== --%>
<c:if test="${eoFileListForm.filterFileStatus eq 'LOAD2APM_PROG'}">
<br/>
<table class="contenttable" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >
<tr>
<td align="left">
<h3>
Load To APM (<c:out value="${eoFileListForm.apmLoadFiles}"/>)
</h3>
</td>
</tr>
</table>


<table class="contenttable alternatecolors" id="mainData" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >     
<thead> 
<tr class="tablerowheader" style="line-height: 2.5em;" >
	<th width="15%">Supplier</th>
	<th width="15%">File Id / Batch Id</th>
	<th width="50%">File Name</th>
	<th width="20%">Status</th>
</tr>
</thead>


<tbody>
<c:if test="${not empty eoFileListForm.searchResults}">
<c:forEach items="${eoFileListForm.searchResults}" var="eoFile" varStatus="currentIndexId">
<c:choose>
<c:when test="${currentIndexId.index % 2 == 0}">
<tr class="o20" style="line-height:1.7em;">
</c:when>
<c:otherwise>
<tr style="line-height:1.7em;">
</c:otherwise>
</c:choose>
<c:if test="${empty eoFile.fileId}">
<td colspan = "4" class="o40" style="font-weight:bold;line-height:2.5em;" >
<span style="display:block;float:left;width:200px;margin-left:10px;"><c:out value="${eoFile.suppCode}"/> <c:out value="${eoFile.perfQuarter}"/>
<c:if test="${not empty eoFile.perfPeriod}">
-<c:out value="${eoFile.perfPeriod}"/>
</c:if>
</span>
<span style="display:block;float:left;width:600px;margin-left:10px;"><c:out value="${eoFile.batchId}"/></span>
<c:out value="${eoFile.statusCode}"/>
</td>
<c:set var="currentIndexId" value="0"/>
</c:if>
<c:if test="${not empty eoFile.fileId}">
<td align="left"></td>
<td align="center"><c:out value="${eoFile.fileId}"/></td>
<td align="left"><c:out value="${eoFile.fileName}"/></td>
<td align="left"></td>
</c:if>
</c:forEach>
</c:if>
</tbody>

</table>

<table class="buttonstable">
 <tr  class="searchpagestatus" style="font-weight:800;">
 <td align="left" style="padding-top:5px;">&nbsp;
 </td>
 </tr>
 </table>
</c:if>



<%-- ===================================== ERROR_FILES  ======================================================================================================================================================== --%>
<c:if test="${eoFileListForm.filterFileStatus eq 'ERROR_FILES'}">

<br/>
<table class="contenttable" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >
<tr>
<td align="left">
<h3>
In Error (<c:out value="${eoFileListForm.errorFiles}"/>)
</h3>
</td>
</tr>
</table>
<table class="contenttable alternatecolors" id="mainData" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >     
<thead> 
<tr class="tablerowheader" style="line-height: 2.5em;" >
	<th width="15%">Supplier</th>
	<th width="15%">File Id</th>
	<th width="15%">Batch Id</th>
	<th width="40%">File Name</th>
	<th width="15%">Status</th>
</tr>
</thead>

<tbody>
<c:if test="${not empty eoFileListForm.searchResults}">
<c:forEach items="${eoFileListForm.searchResults}" var="eoFile" varStatus="currentIndexId">

<c:choose>
<c:when test="${currentIndexId.index % 2 == 0}">
<tr class="o20" style="line-height:1.7em;">
</c:when>
<c:otherwise>
<tr style="line-height:1.7em;">
</c:otherwise>
</c:choose>
<td align="left"><c:out value="${eoFile.suppCode}"/></td>
<td align="center"><c:out value="${eoFile.fileId}"/></td>
<td align="center"><c:out value="${eoFile.batchId}"/></td>
<td align="left"><c:out value="${eoFile.fileName}"/></td>
<td align="center"><c:out value="${eoFile.statusCode}"/></td>
</c:forEach>
</c:if>
</tbody>
</table>

<table class="buttonstable">
 <tr  class="searchpagestatus" style="font-weight:800;">
 <td align="left" style="padding-top:5px;">&nbsp;
 </td>
 </tr>
 </table>
</c:if>




<%-- ===================================== NOT_FOR_PROCESS  ======================================================================================================================================================== --%>
<c:if test="${eoFileListForm.filterFileStatus eq 'NOT_FOR_PROCESS'}">

<br/>
<table class="contenttable" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >
<tr>
<td align="left">
<h3>
Not For Processing (<c:out value="${eoFileListForm.noProcessFiles}"/>)
</h3>
</td>
</tr>
</table>
<table class="contenttable alternatecolors" id="mainData" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >     
<thead> 
<tr class="tablerowheader" style="line-height: 2.5em;" >
	<th width="5%">Supplier</th>
	<th width="5%">File Id</th>
	<th width="35%">File Name</th>
	<th width="35%">File Location</th>
	<th width="10%">Date Received</th>
	<th width="10%">Date Updated</th>
</tr>
</thead>
<tbody>
<c:if test="${not empty eoFileListForm.searchResults}">
<c:forEach items="${eoFileListForm.searchResults}" var="eoFile" varStatus="currentIndexId">

<c:choose>
<c:when test="${currentIndexId.index % 2 == 0}">
<tr class="o20" style="line-height:1.7em;">
</c:when>
<c:otherwise>
<tr style="line-height:1.7em;">
</c:otherwise>
</c:choose>
<td align="left"><c:out value="${eoFile.suppCode}"/></td>
<td align="center"><c:out value="${eoFile.fileId}"/></td>
<td align="left"><c:out value="${eoFile.fileName}"/></td>
<td align="left"><c:out value="${eoFile.fileTo}"/></td>
<td align="center"><c:out value="${eoFile.createDate}"/></td>
<td align="center"><c:out value="${eoFile.updateDate}"/></td>
</c:forEach>
</c:if>

</tbody>
</table>

<table class="buttonstable">
 <tr  class="searchpagestatus" style="font-weight:800;">
 <td align="left" style="padding-top:5px;">&nbsp;
 </td>
 </tr>
 </table>
</c:if>



<%-- ===================================== SUCCESS_FILES ======================================================================================================================================================== --%>
<c:if test="${eoFileListForm.filterFileStatus eq 'SUCCESS_FILES'}">

<br/>
<table class="contenttable" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >
<tr>
<td align="left">
<h3>
Loaded to APM (<c:out value="${eoFileListForm.successFilesByPeriod}"/>)
</h3>
</td>
</tr>
</table>


<table class="buttonstable" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >
<tr>

<td width="20%">
<span class="txtBlk_bold" style="font-size:1.1em;">Perf Qtr </span>
		<form:select path="filterPerformanceQuarter">
			<form:option value="">All</form:option>
			<form:options items="${distirbutionYearQuarter}" itemLabel="displayName" itemValue="id" />
		</form:select>
&nbsp;&nbsp;

<span class="txtBlk_bold" style="font-size:1.1em;">Perf Period </span>
		<form:select path="filterPerfPeriod">
			<form:option value=""></form:option>  
			<form:option value="1" />
			<form:option value="2" />
			<form:option value="3" />
			<form:option value="4" />
			<form:option value="5" />
			<form:option value="6" />
			<form:option value="7" />
			<form:option value="8" />
			<form:option value="9" />
			<form:option value="10" />
			<form:option value="11" />
			<form:option value="12" />
			
		</form:select>     
&nbsp;&nbsp;


<span class="txtBlk_bold" style="font-size:1.1em;">Dist Qtr </span>
		<form:select path="filterDistributionQuarter">
			 <form:option value=""></form:option>
			<form:options items="${distirbutionYearQuarterSpl}" itemLabel="displayName" itemValue="id" />
		</form:select>
&nbsp;&nbsp;
<span class="txtBlk_bold" style="font-size:1.1em;">Supplier </span>
<form:select path="filterCompletedSupplierCode" id="_filterCompletedSupplierCode">
			<form:option value="" >All</form:option>
			<form:options items="${UsageSuppliersList}" itemLabel="displayName" itemValue="id" />
		</form:select>
&nbsp;&nbsp;
<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_FD_100_FILE_DASHBOARD_SEARCH %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_APPLY_FILTER %>" id="<%=UIWidgetConstants.ID_APPLY_FILTER%>" title="Filter">onclick="details('SUCCESS_FILES','FILTER');"</prep:uiWidget> 
</td>
</tr>
</table>

<table class="contenttable alternatecolors" id="mainData" style="width:1000px;margin:0 auto;border:0px #cccccc solid;" >     
<thead> 
<tr class="tablerowheader" style="line-height: 2.5em;" >
	<th width="25%">Supplier</th>
	<th width="15%">Perf Qtr - Perf Period</th>
	<th width="10%">Dist Qtr</th>
	<th width="15%">Date Loaded</th>
	<th width="15%"># of Rollup Records</th>
	<th width="15%">Sum of Plays</th>
	 
</tr>
</thead>

<tbody>
<c:if test="${not empty eoFileListForm.searchResults}">
<c:forEach items="${eoFileListForm.searchResults}" var="eoFile" varStatus="currentIndexId">
<c:choose>
<c:when test="${currentIndexId.index % 2 == 0}">
<tr class="o20" style="line-height:1.7em;">
</c:when>
<c:otherwise>
<tr style="line-height:1.7em;">
</c:otherwise>
</c:choose>
<td align="left"><c:out value="${eoFile.suppCode}"/></td>
<td align="left"><c:out value="${eoFile.perfQuarter}"/> 
<c:if test="${not empty eoFile.perfPeriod}">
    - <c:out value="${eoFile.perfPeriod}"/> 
</c:if>    
</td>
<td align="center"><c:out value="${eoFile.distQuarter}"/></td>
<td align="center"><c:out value="${eoFile.createDate}"/></td>
<td align="right"><c:out value="${eoFile.rollupRowCountFormatted}"/></td>
<td align="right"><c:out value="${eoFile.playCountFormatted}"/></td>
</c:forEach>
</c:if>
</tbody>
</table>

<table class="buttonstable">
 <tr  class="searchpagestatus" style="font-weight:800;">
 <td align="left" style="padding-top:5px;">&nbsp;
 </td>
 </tr>
 </table>
</c:if>
</c:if>
</form:form>
</body>
</html>