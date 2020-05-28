<!DOCTYPE HTML>

<%@page	language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>
<%@ page import="com.ascap.apm.controller.utils.Utils"%>
<%@ page import="com.ascap.apm.controller.utils.HtmlSelectOption"%>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>	
<%pageContext.setAttribute("UsageSuppliersList", HtmlSelectOption.getLookUpTable("CatalogSuppliers"), PageContext.PAGE_SCOPE);
 pageContext.setAttribute("ApmUsersList", HtmlSelectOption.getLookUpTable("ApmUsers"), PageContext.PAGE_SCOPE);
 pageContext.setAttribute("ApmManagerList", HtmlSelectOption.getLookUpTable("ApmManagers"), PageContext.PAGE_SCOPE); %>
<html>
<head>
<title>APM</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 

<%@ include file = "/views/common/uiWidgets1200.jsp"%>
<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css">
<style>
.redError {color:#FF0000; }
</style>

<script type="text/javascript">
var sall=false;
var manageSuppFilter = function () {
};
var manageSelectAll = function () {
	if($('input[name=sA]').is(':checked')) {sall=true; $('a[title=Delete]').addClass('ui-state-disabled');$('input[name=selectedIndex]:not(:disabled)').attr('checked', true);} else {sall=false;$('a[title=Delete]').removeClass('ui-state-disabled');$('input[name=selectedIndex]:not(:disabled)').attr('checked',false);}
};

$(function() {
	$('#mainData tbody tr').css('line-height','1.7em');
	sortTable('WM-100','mainData', [0,1]);
	filterTable('WM-100','mainData' , [0,1]);
	
	$('a[id^=_Assign]').click(function() {
	
		if('Y' === $("input[name=nonMasteruser]").val()) {
		 
        
        var userNotAllowed = false;
        
       			 $("input[name=selectedIndex]:checked").each(function(){
					if (   $(this).closest('tr').find('input[name=assignedToUser]').val()  != '<c:out value="${apmPerformanceBulkRequestList.userId}"></c:out>') {
					   userNotAllowed = true; 
					  } 
				}); 
				if(userNotAllowed) {
				 	$("#serverErrorMessages").html("");
					$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.manualmatch.assign.selection.notvalid"/></span>'); 
					location.href="#";
					return;
				}
		}
				
		if($("table#mainData tr input[name='selectedIndex']:checked").length > 0) {	}
		else {
	   		$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.manualmatch.assign.selection.none"/></span>'); 
			location.href="#";
			return;
		}
		
		if($('select[name="assignUser"]').val() === '') {
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.manualmatch.asgusrid.value.none"/></span>'); 
			location.href="#";
			return;
		}
	
		resetFinalValues();
		disableUnusedFields();
		
		var updateProgressDiv = $(document.createElement('div')); 
		var txt = '<span style="font-size:1.3em;">Please Wait.. </span><br>APM is updating the selected work performances.<br><br><img src="<%=request.getContextPath()%>/images/indicator.gif">';
		updateProgressDiv.html(txt);
		updateProgressDiv.dialog({
		resizable: false,
		height:'auto',
 		minHeight: 'auto',
		modal: true,
		show: "slide",
		hide: "slide"
		});
		$(".ui-dialog-titlebar").hide();
		document.forms[0].operationType.value = 'ASSIGN';
		document.forms[0].submit();
	});
	
	$('a[id^=_Unassign]').click(function() {
	
		if($("table#mainData tr input[name='selectedIndex']:checked").length > 0) {	}
		else {
	   		$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.manualmatch.assign.selection.none"/></span>'); 
			location.href="#";
			return;
		}
	
		resetFinalValues();
		disableUnusedFields();
		
		
		var updateProgressDiv = $(document.createElement('div')); 
		var txt = '<span style="font-size:1.3em;">Please Wait.. </span><br>APM is updating the selected work performances.<br><br><img src="<%=request.getContextPath()%>/images/indicator.gif">';
		
		updateProgressDiv.html(txt);
		updateProgressDiv.dialog({
		resizable: false,
		height:'auto',
 		minHeight: 'auto',
		modal: true,
		show: "slide",
		hide: "slide"
		});
		$(".ui-dialog-titlebar").hide();
		document.forms[0].operationType.value = 'UNASSIGN';
		document.forms[0].submit();
	});
	
	$('a[id^=_Delete]').click(function() {
			if(sall) return;
			resetValues();
			
			var validForDelete = true;
			var userAllowed = true;

			
			if($("table#mainData tr input[name='selectedIndex']:checked").length > 0) {	
				$("#serverErrorMessages").html("");
				$("#uierror").html("");				
		       	$( "#dialog:ui-dialog" ).dialog( "destroy" );
		       	
		       	var newDiv = $(document.createElement('div')); 
		       	var txt = 'Are you sure you want to delete the ';
		       	
		       	txt = txt + $("table#mainData tr input[name='selectedIndex']:checked").length;
		       	txt = txt + ' selected rows? ';
		       	var playCnt = 0;
	   	
		       	$("input[name=selectedIndex]:checked").each(function(){
					var checkedIndex = this.value;
					
					if(! isUserAllowed(checkedIndex)) {
						userAllowed = false;
					}
					
					playCnt= playCnt + Number($("input[name=workPerfCount]:eq("+checkedIndex+")").val());
					 if((($("input[name=originalWorkId]:eq("+checkedIndex+")").val()) != '')) {
						validForDelete = false;
					} 
				});
				
				
				if(! userAllowed) {
					$("#errorMessages").html("");
					$("#serverErrorMessages").html("");
					$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.manualmatch.assign.rows.selection.invalid"/></li></span>');
					unCheckSub($('a[id^=_Unmatch]'));
			 		location.href="#"; 
			 		return;
				}
				
		       	if(!validForDelete) {
					$("#errorMessages").html("");
					$("#serverErrorMessages").html("");
					$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.archives.originalworkid.exists"/></span>');
					unCheckSub($('a[id^=_Delete'));
		 			location.href="#"; 
		 			return;
		       	}
		       	
		       	txt = txt + playCnt;
		       	txt = txt + ' performances will be deleted.';
				newDiv.html(txt);
				newDiv.attr('title','Delete?');
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
		 					deleteGroups(playCnt);
		 				}
		 			}
				});
		    }
	    	else {
	    		$("#serverErrorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.archives.delete.selection"/></span>'); 
				location.href="#";
				return;
	    	}
    	});
    	
    	$('a[id=_Match_to_Multiple_Works]').click(function() {
    		resetValues();
    		if($("table#mainData tr input[name='selectedIndex']:checked").length > 1) {    			
    			$("#serverErrorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.manualmatch.multiplematch.selection.gt1"/></span>'); 
				location.href="#";
				return;
    		}
    		else if($("table#mainData tr input[name='selectedIndex']:checked").length < 1) {    			
    			$("#serverErrorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.manualmatch.multiplematch.selection.lt1"/></span>'); 
				location.href="#";
				return;
    		}
    		else {
    		
    			var allowedUser = true;
    			$("input[name=selectedIndex]:checked").each(function(){    				
					var cIndex = this.value;
    				if(! isUserAllowed(cIndex)) {				
						allowedUser = false;
						errMsgInvalidUser = '<li><spring:message code="error.apm.manualmatch.assign.rows.selection.invalid"/></li>';
					}
				});
    		
    			if(!allowedUser) {
					$("#errorMessages").html("");
					$("#serverErrorMessages").html("");
					$("#uierror").html('<span class=txtRed>'+errMsgInvalidUser+'</span>');
					//unCheckSub(obj);
		 			location.href="#"; 
		 			return;
				}
    			
    			$("input[name=selectedIndex]:checked").each(function(){    				
					var checkedIndex = this.value;
					
					if( $.trim(  $("input[name=multWorkId]:eq("+checkedIndex+")").val()) == '') {
						if( $.trim(  $("input[name=originalWorkId]:eq("+checkedIndex+")").val()) != '') {
							$("#serverErrorMessages").html("");
							$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.multiploematch.singleworkid.exists"/></span>'); 
							location.href="#";
							return;
						}
					}
					
					if( $.trim(  $("input[name=multWorkId]:eq("+checkedIndex+")").val()) != '') {
					pleaseWait();
					document.forms[0].operationType.value = 'MEDLEY_RETRIEVE';
					resetFinalValues();
					disableFields();
					document.forms[0].submit();
					}
					else {
						pleaseWait();
						document.forms[0].operationType.value = 'MEDLEY_NEW';
						resetFinalValues();
						disableFields();
						document.forms[0].submit();
    				}
				});
    			
    		}
    	});
    	
    	$('a[id^=_Unmatch]').click(function() {
			resetValues();
			if($("table#mainData tr input[name='selectedIndex']:checked").length == 1) {	
			
			
			var validForUnmatch = true;
			$("input[name=selectedIndex]:checked").each(function(){
				var checkedIndex = this.value;	
				
				
				if(! isUserAllowed(checkedIndex)) {						
						validForUnmatch = false;					
						$("#errorMessages").html("");
						$("#serverErrorMessages").html("");
						$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.manualmatch.assign.rows.selection.invalid"/></li></span>');
						unCheckSub($('a[id^=_Unmatch]'));
			 			location.href="#"; 
			 			return;
				}
				
				if( $.trim(  $("input[name=multWorkId]:eq("+checkedIndex+")").val()) != '') {
					pleaseWait();
					$("input[name=medleyMultiWorkId]").val($("input[name=multWorkId]:eq("+checkedIndex+")").val());
					document.forms[0].operationType.value = 'MEDLEY_UNMATCH';
					resetFinalValues();
					disableFields();
					document.forms[0].submit();
		 			location.href="#"; 
					validForUnmatch = false;
				}
				
				else {
					if((($("input[name=originalWorkId]:eq("+checkedIndex+")").val()) == '')) {
						validForUnmatch = false;
						$("#errorMessages").html("");
						$("#serverErrorMessages").html("");
						$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.archives.originalworkid.required"/></span>');
						unCheckSub($('a[id^=_Unmatch]'));
			 			location.href="#"; 
			 			return;
					}
				}
			});
			
			
			if(validForUnmatch) {
				$("#serverErrorMessages").html("");
				$("#uierror").html("");				
		       	$( "#dialog:ui-dialog" ).dialog( "destroy" );
		       	
		       	var newDiv = $(document.createElement('div')); 
		       	var txt = 'Are you sure you want to unmatch the ';
		       	
		       	txt = txt + $("table#mainData tr input[name='selectedIndex']:checked").length;
		       	txt = txt + ' selected rows? ';
		       	var playCnt = 0;
		       	$("input[name=selectedIndex]:checked").each(function(){
					var checkedIndex = this.value;
					playCnt= playCnt + Number($("input[name=workPerfCount]:eq("+checkedIndex+")").val());
				});			
				newDiv.html(txt);
				newDiv.attr('title','Unmatch?');
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
		 					unmatch(playCnt);
		 				}
		 			}
				});
				}
		    }
	    	else if($("table#mainData tr input[name='selectedIndex']:checked").length == 0) {
	    		$("#serverErrorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.archives.unmatch.selection.none"/></span>'); 
				location.href="#";
				return;
	    	}
	    	else if($("table#mainData tr input[name='selectedIndex']:checked").length > 1) {
	    		$("#serverErrorMessages").html("");
				$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.archives.unmatch.selection.multiple"/></span>'); 
				location.href="#";
				return;
	    	}
    	});
    	
    	
    	
    	$('a[id^=_Undelete]').click(function() {
    	
		$("#serverErrorMessages").html("");
		$("#errorMessages").html("");
		$("#uierror").html("");
		if($("table#mainData tr input[name=selectedIndex]:checked").length == 0) {	
			$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.file.selection.invalid"/></span>');
			unCheckSub($('id=_Undelete'));
	 		location.href="#"; 
	 		return;
	 	}
	 	
	 			var newDiv = $(document.createElement('div')); 
		       	var txt = ' Are you sure you want to undelete the selected row(s)? ';		       	
				newDiv.html(txt);
				newDiv.attr('title','Undelete?');
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
		 					undeleteReset($('id=_Undelete'));
		 					},		 					
		 				"OK": function() {
		 					$( this ).dialog( "close" );
		 					undeleteSub();
		 				}
		 			}
				});
    	});
    	
    	
}); 


function disableFields() {
	$("input[name=selectedIndex]:not(:checked)").each(function(){
		/* console.log($(this).val() + "   " + this.checked);*/
		$(this).closest('tr').find('input[type=hidden]').attr("disabled", "true");
		$(this).closest('tr').find('input[name=workId]').attr("disabled", "true");
	});
}


function disableUnusedFields() {

	$('input[name="workId"]').attr("disabled", "true");
	$('input[name="requestId"]').attr("disabled", "true");
	$('input[name="multWorkId"]').attr("disabled", "true");
	$('input[name="playCount"]').attr("disabled", "true");
	$('input[name="workPerfCount"]').attr("disabled", "true");
	$('input[name="originalWorkId"]').attr("disabled", "true");
	$('input[name="manualMatchUserId"]').attr("disabled", "true");
	$('input[name="priority"]').attr("disabled", "true");
	$('input[name="estimatedDollarAmount"]').attr("disabled", "true");
	$('input[name="estimatedDollarFlag"]').attr("disabled", "true");
	$('input[name="groupingSubpplierCode"]').attr("disabled", "true");
	$('input[name="assignedToUser"]').attr("disabled", "true");
	
	disableFields();
	
}

function setNavigationType(navigation) {
		$('input[name=selectedIndex]:not(:disabled)').attr('checked',false);
		$("input[name=medelyView]").val('');
		document.forms[0].operationType.value = 'SEARCH';
		document.forms[0].navigationType.value = navigation;
		disableFields();
		document.forms[0].submit();
}

function filter() {
		$('input[name=selectedIndex]:not(:disabled)').attr('checked',false);
		$("input[name=medelyView]").val('');
		document.forms[0].operationType.value = 'SEARCH';
		disableFields();
		document.forms[0].submit();
}

function getWorkPerfPopup(val1,val2,val3){
	window.open('<%=request.getContextPath()%>/usage/usageHomeSearch.do?actionSelected=SEARCH_WORK_PERFORMANCES&workTitle='+val1+'&featuredPerformerName='+val2+'&supplierCode='+val3,'', 'width=1100,height=600,toolbar=0,status=1,scrollbars=1,location=0,resizable=0');
}
function update(obj) {

	resetValues();
	//disableFields();
	//return;

var wId;
var errMsg = '';
var errMsgMult = '';
var errMsgInvalidUser = '';
	if($("table#mainData tr input:checked").length == 0) {	
			$("#serverErrorMessages").html("");
			$("#errorMessages").html("");
			$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.archives.update.selection"/></span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
		 }
	else {	 
		var workIdExists = true;		
		var workIdNumeric = true;
		var matchedToMultipe = false;
		var allowedUser = true;
		
		 var i=0;
		$("input[name=selectedIndex]:checked").each(function(){
			var checkedIndex = this.value;	
			
			if($.trim($("input[name=workId]:eq("+checkedIndex+")").val()) == '') {
				workIdExists = false;
			}
			else {
				$("input[name=workId]:eq("+checkedIndex+")").val($.trim($("input[name=workId]:eq("+checkedIndex+")").val()));
				wId = $("input[name=workId]:eq("+checkedIndex+")").val();
				if(isNaN(wId) || Number(wId) <=0 || !allNumeric(wId)) {
					workIdNumeric = false;
					errMsg += '<li><attribute spring:message code="error.apm.archives.workid.nonnumber" arg0="'+wId+'"/></li>';
				}
			}
			
			if( $.trim(  $("input[name=multWorkId]:eq("+checkedIndex+")").val()) != '') {
				matchedToMultipe = true;
				errMsgMult += '<li><spring:message code="error.apm.multiplematch.update.invalid"/></li>';
			}
			
			if(! isUserAllowed(checkedIndex)) {				
				allowedUser = false;
				errMsgInvalidUser = '<li><spring:message code="error.apm.manualmatch.assign.rows.selection.invalid"/></li>';
			}
			
			
		});
		
		if(!allowedUser) {
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed>'+errMsgInvalidUser+'</span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
		}
		
		else if(!workIdExists) {		
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed><li><spring:message code="error.apm.archives.workid.required"/></li></span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
		}
		else if(!workIdNumeric) {
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed>'+errMsg+'</span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
		}
		else if(matchedToMultipe) {
			$("#errorMessages").html("");
			$("#serverErrorMessages").html("");
			$("#uierror").html('<span class=txtRed>'+errMsgMult+'</span>');
			unCheckSub(obj);
		 	location.href="#"; 
		 	return;
		}
		else {
		
				var numPerf = 0;
		       	$("input[name=selectedIndex]:checked").each(function(){
					var checkedIndex = this.value;
					numPerf= numPerf + Number($("input[name=workPerfCount]:eq("+checkedIndex+")").val());
				});	
		
		var updateProgressDiv = $(document.createElement('div')); 
		var txt = '<span style="font-size:1.3em;">Please Wait.. </span><br>APM is updating the selected work performances.<br><br><img src="<%=request.getContextPath()%>/images/indicator.gif">';
		if(numPerf && !isNaN(numPerf)) {
    		txt = '<span style="font-size:1.3em;">Please Wait.. </span><br>APM is updating '+ numPerf +' work performances.<br><br><img src="<%=request.getContextPath()%>/images/indicator.gif">';
    	}		       	
      
		updateProgressDiv.html(txt);
		updateProgressDiv.dialog({
		resizable: false,
		height:'auto',
 		minHeight: 'auto',
		modal: true,
		show: "slide",
		hide: "slide"
		});
		$(".ui-dialog-titlebar").hide();
		
		document.forms[0].operationType.value = 'UPDATE';
		resetFinalValues();
		disableFields();
		
		document.forms[0].submit();
		}
	}
}

function isUserAllowed(checkedIndex) {
	if('N' === $("input[name=canUpdateOthers]").val())   {
		if( $.trim(  $("input[name=assignedToUser]:eq("+checkedIndex+")").val()) != '' ) {
			/* if( $.trim(  $("input[name=assignedToUser]:eq("+checkedIndex+")").val()) != '<c:out value="${apmPerformanceBulkRequestList.userId}"></c:out>) {
				
				return false
			} */
		}	
	}
	return true;		

}

function unmatch(noOfWorkPerf) {
	var progressDiv = $(document.createElement('div')); 
    var txt = '<span style="font-size:1.3em;">Please Wait.. </span><br> APM is unmatching the selected work performances.<br><br><img src="<%=request.getContextPath()%>/images/indicator.gif">';
    if(noOfWorkPerf && !isNaN(noOfWorkPerf)) {
    	
    }		       	
      
	progressDiv.html(txt);
	progressDiv.dialog({
	resizable: false,
	height:'auto',
 	minHeight: 'auto',
	modal: true,
	show: "slide",
	hide: "slide"
	});
	$(".ui-dialog-titlebar").hide();
	
	resetFinalValues();
	disableFields();
	
	document.forms[0].operationType.value = 'UNMATCH';
	document.forms[0].submit();
}

function resetValues() {
	$("input[name=selectedIndex]").each(function(i){
		this.value = i;
	});
}

function resetFinalValues() {
	$("input[name=selectedIndex]:checked").each(function(i){
		this.value = i;
	});
}


function deleteGroups(noOfWorkPerf) {
	var progressDiv = $(document.createElement('div')); 
    var txt = '<span style="font-size:1.3em;">Please Wait.. </span><br> APM is deleting the selected work performances.<br><br><img src="<%=request.getContextPath()%>/images/indicator.gif">';
    if(noOfWorkPerf && !isNaN(noOfWorkPerf)) {
    	txt = '<span style="font-size:1.3em;">Please Wait.. </span><br> APM is deleting '+noOfWorkPerf +' work performances.<br><br><img src="<%=request.getContextPath()%>/images/indicator.gif">';
    }		       	
      
	progressDiv.html(txt);
	progressDiv.dialog({
	resizable: false,
	height:'auto',
 	minHeight: 'auto',
	modal: true,
	show: "slide",
	hide: "slide"
	});
	$(".ui-dialog-titlebar").hide();
	
	resetFinalValues();
	disableFields();
	
	document.forms[0].operationType.value = 'DELETE';
	document.forms[0].submit();
}

function unmatchGroups(noOfWorkPerf) {
	var progressDiv = $(document.createElement('div')); 
    var txt = '<span style="font-size:1.3em;">Please Wait.. </span><br> APM is unmatching the selected work performances.<br><br><img src="<%=request.getContextPath()%>/images/indicator.gif">';
    if(noOfWorkPerf && !isNaN(noOfWorkPerf)) {
    	txt = '<span style="font-size:1.3em;">Please Wait.. </span><br> APM is unmatching '+noOfWorkPerf +' work performances.<br><br><img src="<%=request.getContextPath()%>/images/indicator.gif">';
    }		       	
      
	progressDiv.html(txt);
	progressDiv.dialog({
	resizable: false,
	height:'auto',
 	minHeight: 'auto',
	modal: true,
	show: "slide",
	hide: "slide"
	});
	$(".ui-dialog-titlebar").hide();
	document.forms[0].operationType.value = 'DELETE';
	document.forms[0].submit();
}

function addRows()	{
	$("#mdlTbl>tbody").append('<tr align="center"><td align="center"><input type="checkbox" name="abcdef_chk"></input></td><td align="center"><html:text name="apmPerformanceBulkRequestList" property="medleyWorkIds" value="" /></td></tr>');		
}
function deleteRows() {
if($("#mdlTbl>tbody tr input:checked").length == 0) {	
	alert('error no check box selected');
}
$("#mdlTbl>tbody tr input:checked").parent().parent().remove();
}

function cancelMdl(obj) {
	$("#filtertarget111").css({ display : 'none' });
	$('.ui-button').removeClass('ui-state-disabled');
}

function updateMdl(obj) {
	document.forms[0].operationType.value = 'MEDLEY_UPDATE';
	document.forms[0].submit();
}


function undeleteSub() { 
	var progressDiv = $(document.createElement('div')); 
    var txt = '<span style="font-size:1.3em;">Please Wait.. </span><br> APM is undeleting the selected work performances.<br><br><img src="<%=request.getContextPath()%>/images/indicator.gif">';
    	       	 
	progressDiv.html(txt);
	progressDiv.dialog({
	resizable: false,
	height:'auto',
 	minHeight: 'auto',
	modal: true,
	show: "slide",
	hide: "slide"
	});
	$(".ui-dialog-titlebar").hide();
	
	resetFinalValues();
	disableFields(); 
     
	document.forms[0].operationType.value = 'UNDELETE';
	document.forms[0].submit();
}
 

function undeleteReset(obj) {
			unCheckSub(obj);
	 		location.href="#"; 
	 		return;
}

</script>
</head>
<body> 

<div class="menubg"  style="width: 100%; border: 0px; height:90px;"> 
<jsp:include page="/views/menu.jsp"/> 
</div>
<div></div>
 
<table class="titletable">
<tr>
	<td>Catalog Match List [CM-100]</td>
</tr>
 </table>
 
 <table class="errortable">
		<tr>
			<td>
				<div id="serverErrorMessages">
				
				<span class="txtGreen"> <c:out value="${systemmessage}" /></span>
				<span class="txtRed"> <c:out value="${systemerror}" /></span>
				</div>
				<div id="uierror"></div>
				<div id="errorMessages"></div>
			</td>
		</tr>
</table>
 
<form:form action="catalogManualMatchRequest" modelAttribute="apmPerformanceBulkRequestList" method="get"> 
 
 
 
<form:hidden  path="apmPerformanceBulkRequestListType" value="CATALOG"/> 
<form:hidden  path="navigationType" />
<form:hidden path="operationType" />
<form:hidden  path="medelyView" />
<form:hidden  path="writerExists" />


<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_WM_100_UPDATE_ASG_TO_OTH%>" value="N">
		  <input type="hidden" name="canUpdateOthers" value="N">
</prep:hasPermission>
<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_WM_100_UPDATE_ASG_TO_OTH%>" value="Y">
		  <input type="hidden" name="canUpdateOthers" value="Y">
</prep:hasPermission>


<div>
<div style= "">
   
<table class="detailstable1000" style="width:1200px; font-size: 11px; font-weight: bold; color: #000000; line-height: 11px; margin:0 auto;">
	 
	<tr>	
		<td>Supplier<br><form:select path="filterSupplierCode" Id="filterSupplier">
			<form:option value="" />
			<form:options items="${UsageSuppliersList}" itemLabel="displayName" itemValue="id" />
		</form:select></td>
		<td>Work Title<br><form:input path="filterWorkTitle"/></td>
		<td>Title Search Type<br>
		    <form:select path="workTitleSearchType">
				<form:option value="BGNS">Begins</form:option>
				<form:option value="CNTS">Contains-Begins</form:option>
			</form:select></td>
		<td>Performer<br><form:input path="filterPerformerName"/></td>
		<td>Perfomer Search Type<br>
		    <form:select path="performerSearchType">
				<form:option value="BGNS">Begins</form:option>
				<form:option value="CNTS">Contains-Begins</form:option>
			</form:select></td>
		<td>Match Status<br>
			<form:select  path="filterMatchStatusCode">
				<form:option value=""></form:option> 
				<form:option value="DEL">Deleted</form:option>
				<form:option value="MATCH">Matched</form:option>
				<form:option value="NMATCH">Not Matched</form:option>  
			</form:select>
		</td>
		<td>Group By Supplier<br><form:select path="filterResultViewType" style="width:120px;" Id="groupByFilter">
		<form:option value="SPLR">Yes</form:option><form:option value="NSPLR">No</form:option></form:select></td>
		<td>Assigned To<br/>
			<form:select path="filterAssignedToUser" style="width:120px;">
				<form:option value="" />
				<form:option value="NONE">None</form:option>
				<form:options items="${ApmUsersList}" itemLabel="displayName" itemValue="id" />
			</form:select>
		</td>	
		<td>Matched By<br/>
		<form:select path="filterMatchedByUser" style="width:120px;">
				<form:option value="" />
				<form:options items="${ApmUsersList}" itemLabel="displayName" itemValue="id" />
			</form:select> 
			</td>
	</tr>
</table> 
</div>
</div> 

<div style="width:1200px;margin:0 auto;">
  
<%@ include file = "/views/common/coPageFilters.jsp"%> 
<table class="buttonstable" style="padding-left:0px;padding-right:0px;">
 <tr>
 <td align="left" width="8%">
 <c:if test="${apmPerformanceBulkRequestList.filterMatchStatusCode ne 'DEL' }">
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_UPDATE %>" id="<%=UIWidgetConstants.ID_UPDATE%>" title="Update">onclick="javascript:update(this);"</prep:uiWidget>
 </c:if>
  </td>	
<td align="left" valign="bottom" width="8%" style="padding-left:50px;">	
<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_SEARCH %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_APPLY_FILTER %>" id="<%=UIWidgetConstants.ID_APPLY_FILTER%>" title="Filter">onclick="javascript:pleaseWait();filter();"</prep:uiWidget>
</td>

 <td width="40%" align="left"><%@ include file = "/views/common/navigationIncludeWithNoBSC.jsp"%></td>
 <td width="17%" valign="bottom" align="right">
 
<c:if test="${apmPerformanceBulkRequestList.filterMatchStatusCode ne 'DEL' }">
<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_MATCH_MULTIPLE_WORKS %>" id="<%=UIWidgetConstants.ID_MATCH_MULTIPLE_WORKS %>" title="Match to Multiple Works"> onclick="javascript:void(0);"</prep:uiWidget> 
  </c:if>
 </td>
  
 <prep:hasPermission name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_ASSIGN%>" value="Y"> 
 <td width="27%" valign="bottom" align="right">
 
<c:if test="${filterMatchStatusCode ne 'DEL' }">  
 <form:select path="assignUser" style="width:120px;">
				<form:option value="" />
				<form:options items="${ApmUsersList}" itemLabel="displayName" itemValue="id" />
			</form:select>
<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_ASSIGN%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_ASSIGN %>" id="<%=UIWidgetConstants.ID_ASSIGN %>" title="<%=UIWidgetConstants.LABEL_ASSIGN %>"> onclick="javascript:void(0);"</prep:uiWidget>
<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_UNASSIGN%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_UNASSIGN %>" id="<%=UIWidgetConstants.ID_UNASSIGN %>" title="<%=UIWidgetConstants.LABEL_UNASSIGN %>"> onclick="javascript:void(0);"</prep:uiWidget>
</c:if>
 </td> 
 </prep:hasPermission>
 
<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_ASSIGN%>" value="N">
<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_ASSN_TO_MGR%>" value="Y"> 
<td width="27%" valign="bottom" align="right">


<c:if test="${filterMatchStatusCode ne 'DEL' }">  
<form:select path="assignUser" style="width:120px;">
				<form:option value="" />
				<form:options items="${ApmManagerList}" itemLabel="displayName" itemValue="id" />
			</form:select>	 
<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_ASSN_TO_MGR%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_ASSIGN %>" id="<%=UIWidgetConstants.ID_ASSIGN %>" title="<%=UIWidgetConstants.LABEL_ASSIGN %>"> onclick="javascript:void(0);"</prep:uiWidget>
</c:if>
</td> 
<input type="hidden" name="nonMasteruser" value="Y"/>
</prep:hasPermission>
</prep:hasPermission>
  
  
 <prep:hasPermission name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_ASSIGN%>" value="N">
 <td width="27%" valign="bottom" align="right"></td>
 </prep:hasPermission>
 
 
</tr>
 </table>
<table class="contenttable alternatecolors" id="mainData" style="width:1200px;margin:0 auto;border:0px #cccccc solid;">     
<thead> 
<tr class="tablerowheader" >
	<th width="2%"><input type="checkbox" name="sA" id="_sA" value="SA" onclick="javascript:manageSelectAll();"></th> 
	<th width="3%">Work ID</th>
	
	
 <c:if test="${apmPerformanceBulkRequestList.filterResultViewType eq 'SPLR' }">    
	<th width="7%">Supplier</th>
	<th width="3%">Provider ID</th>
	</c:if> 
	
<c:if test="${apmPerformanceBulkRequestList.writerExists eq 'Y' }">
	<th width="24%">Work Title</th>
	<th width="24%">Performer</th>
	<th width="18%">Writer</th>
	</c:if>
<c:if test="${apmPerformanceBulkRequestList.writerExists eq 'N' }">
	<th width="31%">Work Title</th>
	<th width="31%">Performer</th>
	<th width="4%">Writer</th>
	</c:if>
	<th width="3%">Play Count</th>   
	<th width="2%">Rec Count</th>
	<th width="5%">Mult Work ID</th>
	<th width="5%">Priority</th>
	<th width="7%">Matched By</th>
	<th width="7%">Assigned To</th> 
</tr>
</thead>


<tbody>
 <c:if test="${not empty apmPerformanceBulkRequestList.searchResults}"> 
 <c:forEach var="apmPerformanceBulkRequest" items= "${apmPerformanceBulkRequestList.searchResults}" varStatus="currentIndexId">

 <c:choose>
<c:when test="${currentIndexId.index %2 ==0 }">
<tr class="o20">
</c:when>
<c:otherwise>
<tr>
</c:otherwise>

</c:choose> 
<td align="center">

<c:if test="${apmPerformanceBulkRequest.deleteFlag eq 'Y' }">
<c:if test="${apmPerformanceBulkRequestList.filterMatchStatusCode ne 'DEL' }">
 <form:checkbox path="selectedIndex" style="padding-top:0px;"  value="${currentIndexId.index}" ></form:checkbox> 
	   
   </c:if>
</c:if>
<c:if test="${apmPerformanceBulkRequest.deleteFlag eq 'Y' }">
<c:if test="${apmPerformanceBulkRequestList.filterMatchStatusCode eq 'DEL' }">
 <form:checkbox path="selectedIndex" style="padding-top:0px;"   value="${currentIndexId.index}"></form:checkbox> 
   </c:if>
</c:if>
<c:if test="${apmPerformanceBulkRequest.deleteFlag ne 'Y'}">
 <form:checkbox path="selectedIndex" style="padding-top:0px;"   value="${currentIndexId.index}"></form:checkbox> 
</c:if>		
	<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].requestId"></form:hidden>
</td>

<td valign="middle" align="center">
<c:if test="${apmPerformanceBulkRequest.invalidWorkId eq 'Y' }">
<input type="text" name="workId" value="${apmPerformanceBulkRequest.workId}" maxlength="11" size="10" styleClass="redError" Id='"wId_"+${currentIndexId.index}'/>
</c:if>

<c:if test="${apmPerformanceBulkRequest.invalidWorkId ne 'Y' }">
<input type="text" name="workId" value="${apmPerformanceBulkRequest.workId}" maxlength="11" size="10" styleClass="redError" Id='"wId_"+${currentIndexId.index}'/>
</c:if>
</td>
<c:if test="${apmPerformanceBulkRequestList.filterResultViewType eq 'SPLR' }">
	<td align="left"><c:out value="${apmPerformanceBulkRequest.supplierCode}" /></td>
	<td align="left"><c:out value="${apmPerformanceBulkRequest.providerId}"/></td>
</c:if>
<td align="left"><c:out value="${apmPerformanceBulkRequest.workTitle}" /></td> 
<td align="left"><c:out value="${apmPerformanceBulkRequest.performerName}" /></td>
<td align="left"><c:out value="${apmPerformanceBulkRequest.writerName}" /></td>
<td align="right"><c:out value="${apmPerformanceBulkRequest.playCount}" /></td>
<td align="right"><c:out value="${apmPerformanceBulkRequest.workPerfCount}" />&nbsp;&nbsp;&nbsp;&nbsp;<%-- <a href="#" onclick="javascript:getWorkPerfPopup('<bean:write name="apmPerformanceBulkRequest" property="workTitle" />','<bean:write name="apmPerformanceBulkRequest" property="performerName" />','<bean:write name="apmPerformanceBulkRequest" property="supplierCode" />')" style="text-decoration:none;"></a>--%></td>
<td align="right"><c:out value="${apmPerformanceBulkRequest.multWorkId}" />&nbsp;&nbsp;&nbsp;&nbsp;
<td align="right"><c:out value="${apmPerformanceBulkRequest.priority}" /></td>
<td align="left"><c:out value="${apmPerformanceBulkRequest.manualMatchUserId}" /></td>
<td align="left"><c:out value="${apmPerformanceBulkRequest.assignedToUser}" /></td>	

<form:hidden  path="multWorkId" value ="${apmPerformanceBulkRequest.multWorkId}" Id='"multWorkId_"+${currentIndexId.index}'/> 
<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].supplierCode" value ="${apmPerformanceBulkRequest.supplierCode}" Id='"supplierCode_"+${currentIndexId.index}'/> 
<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].performerName" value ="${apmPerformanceBulkRequest.performerName}" Id='"performerName_"+${currentIndexId.index}'/>
<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].workTitle" value ="${apmPerformanceBulkRequest.workTitle}" Id='"workTitle_"+${currentIndexId.index}'/>
<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].writerName" value ="${apmPerformanceBulkRequest.writerName}" Id='"writerName_"+${currentIndexId.index}'/>
<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].playCount" value ="${apmPerformanceBulkRequest.playCount}" Id='"playCount_"+${currentIndexId.index} '/>
<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].workPerfCount"  value ="${apmPerformanceBulkRequest.workPerfCount}" Id='"workPerfCount_"+${currentIndexId.index}'/>
<form:hidden  path="originalWorkId" value ="${apmPerformanceBulkRequest.originalWorkId}" Id='"originalWorkId_"+${currentIndexId.index}'/>
<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].manualMatchUserId" value ="${apmPerformanceBulkRequest.manualMatchUserId}" Id='"manualMatchUserId_"+${currentIndexId.index} '/>
<form:hidden  path="apmPerformanceBulkRequest[${currentIndexId.index}].priority"  value ="${apmPerformanceBulkRequest.priority}"  Id='"priority"+${currentIndexId.index} '/>
<form:hidden  path="assignedToUser"  value ="${apmPerformanceBulkRequest.assignedToUser}" Id='"assignedToUser_"+${currentIndexId.index} '/>
<input type="hidden" name="groupingSubpplierCode" value="<c:out value="${apmPerformanceBulkRequestList.filterSupplierCode}" /> "/>

</tr> 
 </c:forEach>
</c:if> 

</tbody>
</table>

<c:out value="${numberOfRecordsFound}"/> 

 <table class="searchpagestatus">
	<tr class="searchpagestatus">
		<td align="right">Results found so far <b><c:out value="${numberOfRecordsFound}"/></b></td>
	</tr>
</table>
<table class="buttonstable" style="width:1000px;">
 <tr>
 <td align="left" width="8%"> 
<%--  <%pageContext.setAttribute("filterMatchStatusCode", "DEL", PageContext.PAGE_SCOPE);%> --%>
<c:if test="${filterMatchStatusCode ne 'DEL' }">
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_UPDATE %>" id="<%=UIWidgetConstants.ID_UPDATE%>" title="Update">onclick="javascript:update(this);"</prep:uiWidget>
 </c:if>
  </td>	
<td align="left" valign="bottom" width="8%" style="padding-left:30px;">	
<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_SEARCH %>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_APPLY_FILTER %>" id="<%=UIWidgetConstants.ID_APPLY_FILTER%>" title="Filter">onclick="javascript:pleaseWait();filter();"</prep:uiWidget>
</td>

 <td width="40%" align="left"><%@include file ="/views/common/navigationIncludeWithNoBSC.jsp"%></td>
 <td width="13%" valign="bottom" align="right">
 

<c:if test="${filterMatchStatusCode ne 'DEL' }">
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_DELETE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_DELETE %>" id="<%=UIWidgetConstants.ID_DELETE%>" title="Delete">onclick="javascript:void(0);"</prep:uiWidget>
 </c:if>
 </td>
 <td width="15%" valign="bottom" align="right">
<c:if test="${filterMatchStatusCode ne 'DEL' }">
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_UNMATCH %>" id="<%=UIWidgetConstants.ID_UNMATCH%>" title="Update">onclick="javascript:void(0);"</prep:uiWidget>
 </c:if> 
<c:if test="${apmPerformanceBulkRequestList.filterMatchStatusCode eq 'DEL' }">
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_UPDATE%>" type="<%= SecurityConstants.ANCHOR_TYPE %>"
  label="<%=UIWidgetConstants.LABEL_UNDELETE %>" id="<%=UIWidgetConstants.ID_UNDELETE%>" title="Undelete">onclick="javascript:checkSub(this);javascript:void(0)"</prep:uiWidget>
   </c:if> 
   
   
 </td>
</tr>
 </table>
 <input type="hidden" name="tempNoOfResults" value="<c:out value="${apmPerformanceBulkRequestList.numberOfRecordsFound}"/>"/>
 <input type="hidden" name="tempCurPageNr" value="<c:out value="${apmPerformanceBulkRequestList.currentPageNumber}" />"/>

 </form:form>
 </div>
	<div style="float:left; width:20%;"></div>
</div>
 <div id="dialog_Delete" title="Delete?" style="display:none">Are you sure you want to delete the selected performances?</div>
 
</body>
</html>