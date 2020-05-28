<!DOCTYPE HTML>
<%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>

<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>
<%@ page import="com.ascap.apm.controller.utils.Utils"%>
<%@ page import="com.ascap.apm.controller.utils.HtmlSelectOption"%>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.SecurityConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>	
<html>
<head><title>APM</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 

<%@ include file = "/views/common/uiWorkMatchWidges.jsp"%>
<link href="<%=request.getContextPath()%>/themes/stylesheet.css" rel="stylesheet" type="text/css">
<style>
.uppercase {text-transform:uppercase}
.orgTxtField {
box-sizing: border-box;
outline: none;
font-size: 12px;
border: 1px solid #aaaaaa;
line-height: 1.4em;
}
.errTxtField {
box-sizing: border-box;
outline: none;
font-size: 12px;
border: 1px solid red;
line-height: 1.4em;
}
</style>

<script type="text/javascript">
$(function() {
	$("._censusSampleIndicator").change(function(){
		var selectedValue = $(this).find(":selected").val();
		var selectedIndex = $("._censusSampleIndicator").index(this);
		var hfaf = $('input[name=hasForeignAffiliationFlag]').val();
		if(selectedValue == 'C') {
			$("select._censusSampleIndicator").each(function() { $(this).val(''); });
			$("select._censusSampleIndicator:eq("+selectedIndex+")").val('C');			
			resetTotalWPCount(selectedIndex,hfaf);
			resetCenWPCount(selectedIndex,hfaf);
		}
		else {		
			$('#_sumtotwp').html('');
			$('input[name=sumtotwpval]').val('');			
			$('#_sumcenwp').html('');
			$('input[name=sumcenwpval]').val('');
		}
	});	
	$("._includeForeignAffFlag").change(function(){	
		var hfaf = $('input[name=hasForeignAffiliationFlag]').val();
		var si = -1;
		$("select._censusSampleIndicator").each(function() {			 
			if("C"==$(this).val()) {
				si =  $("._censusSampleIndicator").index(this);
			} 
		});	
		if(si>=0){
		resetTotalWPCount(si,hfaf);
		resetCenWPCount(si,hfaf);
		}		
	});	
	$('a[id^=_Apply_Sample]').click(function() {
		var errorsFound =  c2l3();
		if(!errorsFound) {
			$("#serverErrorMessages").html("");
			$("#uierror").html("");				
			$( "#dialog:ui-dialog" ).dialog( "destroy" );
			var newDiv = $(document.createElement('div')); 
			var txt = 'The Census and Sample values will be applied and cannot be undone.  Are you sure you want to continue?';      	
		       			       	
			newDiv.html(txt);
			newDiv.attr('title','Apply Sample?');
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
	 					document.forms[0].operationType.value='ADD3';
						document.forms[0].submit();
	 				}
	 			}
			});
		}		
	});	
	$('a[id^=_Cancel_Sampling]').click(function() {			
		$("#serverErrorMessages").html("");
		$("#uierror").html("");				
		$( "#dialog:ui-dialog" ).dialog( "destroy" );
		       	
		var newDiv = $(document.createElement('div')); 
		var txt = 'Canceling the request will discard the sampling grids for this Music User.  Are you sure you want to continue?';      	
		       			       	
		newDiv.html(txt);
		newDiv.attr('title','Cancel?');  
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
 					cancelSampling();
 				}
 			}
		});
    });
    $('a[id^=_Bypass_Sampling]').click(function() {			
		$("#serverErrorMessages").html("");
		$("#uierror").html("");				
		$( "#dialog:ui-dialog" ).dialog( "destroy" );
		       	
		var newDiv = $(document.createElement('div')); 
		var txt = 'Are you sure you want to Bypass Sampling?';      	
		  
		newDiv.html(txt);
		newDiv.attr('title','Cancel?');  
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
 					bypassSampling();
 				}
 			}
		});
    });
});

function bypassSampling() { 
	document.forms[0].operationType.value='BYPASSSAMP';
	document.forms[0].submit();
}		
		
function resetTotalWPCount(selectedIndex,fa) {
		var totUnMatchedCount=0;var totUnMatchedFACount=0;		
		$('input[name=tupc]').each(function() {
			var tupcIndex = $("input[name=tupc]").index(this);
			if(tupcIndex < selectedIndex) {if(!isNaN($(this).val())) {totUnMatchedCount += Number($(this).val());}}
		});	
		if('Y'==fa){
		$('input[name=tufpc]').each(function() {		
			var tufpcIndex = $("input[name=tufpc]").index(this);									
			var includeFAFlag = 	$("select._includeForeignAffFlag:eq("+tufpcIndex+")").val();
			if(tufpcIndex < selectedIndex){if(!isNaN($(this).val())  && 'Y'==includeFAFlag) {totUnMatchedFACount += Number($(this).val());}}
		});}
		$('#_sumtotwp').html((totUnMatchedCount - totUnMatchedFACount));
		$('input[name=sumtotwpval]').val((totUnMatchedCount - totUnMatchedFACount));
}
function resetCenWPCount(selectedIndex, fa) {
		var totUnMatchedCount=0;	
		if('N'==fa){
			$('input[name=tupc]').each(function() {	var tupcIndex = $("input[name=tupc]").index(this);
				if(tupcIndex >= selectedIndex) {if(!isNaN($(this).val())) {totUnMatchedCount += Number($(this).val());}}});}
		else if('Y'==fa){
			$('input[name=tupc]').each(function() {
				var tupcIndex = $("input[name=tupc]").index(this);
				var ci = (tupcIndex >= selectedIndex)?'C':'S';				
				var fi = $("select._includeForeignAffFlag:eq("+tupcIndex+")").val();
				var fpc=$("input[name=tufpc]:eq("+tupcIndex+")").val();
				if(isNaN(fpc)){fpc=0;}
				if('C'==ci&&'Y'==fi) {if(!isNaN($(this).val())) {totUnMatchedCount += Number($(this).val());}}
				else if('C'==ci&&'N'==fi) {if(!isNaN($(this).val())) {totUnMatchedCount += (Number($(this).val() - Number(fpc)));}}
				else if('S'==ci&&'Y'==fi) {totUnMatchedCount += Number(fpc);}				
			});
		}
		$('#_sumcenwp').html((totUnMatchedCount));
		$('input[name=sumcenwpval]').val((totUnMatchedCount));
}

function cancel() {
	document.forms[0].operationType.value='SUMMARY';
	document.forms[0].submit();
}

function cancelSampling() {
	document.forms[0].operationType.value='CANCEL';
	document.forms[0].submit();
}

function c2l3() {
	var errMessage = '<span class=txtRed>';
	var errorsFound = false;

	var sPercent = '';
	var sCount = '';
	var sSkip = '';
	var sStart = '';
	var tupc = '';
	var tufpc = '';
	var iFi = '';
	
	var orginalsPercent = '';
	var orginalsCount = '';
	var orginalsSkip = '';
	var orginalsStart = '';
	
	var sPercentError = false;
	var sCountError = false;
	var sSkipError = false;
	var sStartError = false;
	var combinationError = false;
	var hfaf = $('input[name=hasForeignAffiliationFlag]').val();
	$('input[name=selectPercent]').each(function() { 
		var myIndex = $("input[name=selectPercent]").index(this);
		sPercent = $.trim($(this).val());
		sCount = $.trim($('input[name=selectCount]:eq('+myIndex+')').val());
		sSkip = $.trim($('input[name=selectSkip]:eq('+myIndex+')').val());
		sStart = $.trim($('input[name=selectStart]:eq('+myIndex+')').val());				
		tupc = $.trim($('input[name=tupc]:eq('+myIndex+')').val());
		tufpc = $.trim($('input[name=tufpc]:eq('+myIndex+')').val());
		iFi = $.trim($('input[name=iFi]:eq('+myIndex+')').val());	
		if(isNaN(tupc)){tupc=0;}else{tupc=Number(tupc);}if(isNaN(tufpc)){tufpc=0;}else{tufpc=Number(tufpc);}			
		orginalsPercent = $.trim($('input[name=originalSelectPercent]:eq('+myIndex+')').val());
		orginalsCount = $.trim($('input[name=originalSelectCount]:eq('+myIndex+')').val());
		orginalsSkip = $.trim($('input[name=originalSelectSkip]:eq('+myIndex+')').val());
		orginalsStart = $.trim($('input[name=originalSelectStart]:eq('+myIndex+')').val());	
		var totCount = Number(tupc);		
		if(hfaf=='Y' && iFi=='Y'){totCount = (totCount-Number(tufpc));}	
		if(sPercent != '') {
			if(isNaN(sPercent) || sPercent < 0 || Number(sPercent) <= 0) {
				errorsFound = true;	
				if(!sPercentError)	{	
					errMessage += '<li><spring:message code="us.error.apm.sampling.startskipselect.percent.numeric"/>';
				}	
				sPercentError = true;	
			}
			else if(Number(sPercent) > 100) {
				errorsFound = true;	
				if(!sPercentError)	{	
					errMessage += '<li><spring:message code="us.error.apm.sampling.startskipselect.percent.gt100"/>';
				}	
				sPercentError = true;
			}
		}		
		if(sCount != '') {
			    if(isNaN(sCount) || !allNumeric(sCount) ) {
				errorsFound = true;
				if(!sCountError) {			
					errMessage += '<li><spring:message code="us.error.apm.sampling.startskipselect.count.numeric"/>';
				}	
				sCountError = true;		
			}
			else {
				if(sCount > totCount) {
					errorsFound = true;
					if(!sCountError) {			
						errMessage += '<li><spring:message code="us.error.apm.sampling.numberOfPerformancesToBeSampled.gt"/>';
					} sCountError = true;
				}
			}
		}		
		if(sStart != '') {
			if(isNaN(sStart) || !allNumeric(sStart) || Number(sStart) <= 0) {
				errorsFound = true;
				if(!sStartError) {			
					errMessage += '<li><spring:message code="us.error.apm.sampling.startskipselect.start.numeric"/>';
				} sStartError = true;		
			}
			else {
				if(sStart > totCount) {
					errorsFound = true;
					if(!sCountError) {			
						errMessage += '<li><spring:message code="us.error.apm.sampling.startskipselect.start.gt"/>';
					} sCountError = true;
				}
			}
		}
		if(sSkip != '') {
			if(isNaN(sSkip) || !allNumeric(sSkip) || Number(sSkip) <= 0) {
				errorsFound = true;
				if(!sSkipError) {			
					errMessage += '<li><spring:message code="us.error.apm.sampling.startskipselect.skip.numeric"/>';
				}	
				sSkipError = true;		
			}
		}
		var valuesChanged = false;	
		if(orginalsPercent != sPercent || orginalsCount != sCount || orginalsSkip != sSkip || orginalsStart != sStart) {
			valuesChanged = true;
		}
		if(valuesChanged) {
		if(sPercent == '' && sCount == '' && sStart == '' && sSkip == '')  {
			errorsFound = true;				
			if(!combinationError) {
				errMessage += '<li><spring:message code="us.error.apm.sampling.startskipselect.invalid" arguments="'+(myIndex+1)+'"/>';
			}
			combinationError = true;
		}		
		else if(sPercent != '' && (sCount != '' || sStart != '' || sSkip != '') ) {
			errorsFound = true;				
			if(!combinationError) {
				errMessage += '<li><spring:message code="us.error.apm.sampling.startskipselect.invalid" arguments="'+(myIndex+1)+'"/>';
			}
			combinationError = true;
		}
		else if(sCount != '' && (sPercent != '' || sStart != '' || sSkip != '') ) {	
			errorsFound = true;				
			if(!combinationError) {
				errMessage += '<li><spring:message code="us.error.apm.sampling.startskipselect.invalid" arguments="'+(myIndex+1)+'"/>';
			}			
			combinationError = true;
		}
		else if(sStart != '' && sSkip == '') {	
			errorsFound = true;			
			if(!combinationError) {
				errMessage += '<li><spring:message code="us.error.apm.sampling.startskipselect.invalid" arguments="'+(myIndex+1)+'"/>';
			}
			combinationError = true;
		}
		else if(sSkip != '' && sStart == '') {	
			errorsFound = true;			
			if(!combinationError) {
				errMessage += '<li><spring:message code="us.error.apm.sampling.startskipselect.invalid" arguments="'+(myIndex+1)+'"/>';
			}
			combinationError = true;
		}
		}
	});
		
	if(errorsFound) {
		$("#serverErrorMessages").html("");
		$("#uierror").html(''+errMessage+'</span>');
	}
	return errorsFound;	
}

function c2l2(thisObj) {
	var errMessage = '<span class=txtRed>';
	var errorsFound = false;	
	$('input[name=numberOfPerformancesToBeSampled]').val($.trim($('input[name=numberOfPerformancesToBeSampled]').val()));
	var wrkPerfCount=  $("input[name=numberOfPerformancesToBeSampled]").val();	
	if(wrkPerfCount == '') {
		errorsFound = true;
		errMessage += '<li><spring:message code="us.error.apm.sampling.numberOfPerformancesToBeSampled.required"/>';
	}
	else {		
		    //if(!allNumeric(wrkPerfCount) ||  isNaN(wrkPerfCount) || Number(wrkPerfCount) <= 0) {		 
			if(!allNumeric(wrkPerfCount) ||  isNaN(wrkPerfCount) ) {		
			errorsFound = true;
			errMessage += '<li><spring:message code="us.error.apm.sampling.numberOfPerformancesToBeSampled.numeric"/>';
		}
		else  {		
		
			var sumWPCount = $('input[name=sumtotwpval]').val();
			if(isNaN(sumWPCount)) {
				sumWPCount = 0;
			}
			if( Number(wrkPerfCount) >  Number(sumWPCount)) {
				errorsFound = true;
				errMessage += '<li><spring:message code="us.error.apm.sampling.numberOfPerformancesToBeSampled.gt"/>';
			}
		}
	}
	var countCensus = 0;
	$("select._censusSampleIndicator").each(function() { 
		if('C' == $(this).val()) {
			countCensus++;
		} 
	});
	var censusIndicatorArray = new Array();
	var censusIdx = 0; 
	if(countCensus != 1) {
		errorsFound = true;
		errMessage += '<li><spring:message code="us.error.apm.sampling.censusIndicator.required"/>';
	}
	else if(countCensus == 1) {
	$("select._censusSampleIndicator").each(function() { 
		if('C' == $(this).val()) {
			censusIdx = $("select._censusSampleIndicator").index(this);
		} 
	});
	}
	$("select._censusSampleIndicator").each(function() {
		var tempIndex = $("select._censusSampleIndicator").index(this);
		if(tempIndex < censusIdx) {
			censusIndicatorArray[tempIndex]= 'S';
		}
		else {
		censusIndicatorArray[tempIndex]= 'C';
		}
	});
	$("input._mergeIndicator").each(function() {$(this).val($.trim($(this).val().toUpperCase()));});	
	var mergeArray = new Array();var censusIndArray = new Array();var frnFlagArray = new Array();var mergeValArray = new Array();	
	var mergeValIndex=0;var mergeErrorRows = false;var mergeErrorSurvInd = false;var mergeErrorFrnFlag = false;
		$("input._mergeIndicator").each(function() {var rowNum = $("._mergeIndicator").index(this);
		var currRowVal = $(this).val();var prevRowVal = $('input._mergeIndicator:eq('+Number(rowNum-1)+')').val();var nextRowVal = $('input._mergeIndicator:eq('+Number(rowNum+1)+')').val();		
		if(currRowVal != '' && currRowVal != 'undefined') {
			mergeArray[mergeValIndex] = rowNum;			
			censusIndArray[mergeValIndex] = rowNum;			
			frnFlagArray[mergeValIndex] = $('select._includeForeignAffFlag('+rowNum+')').val();			
			mergeValArray[mergeValIndex] = currRowVal;			
			mergeValIndex++;
			if(currRowVal != prevRowVal && currRowVal != nextRowVal) {errorsFound = true;
				if(!mergeErrorRows){errMessage += '<li><spring:message code="us.error.apm.sampling.mergeIndicator.invalid" arguments="'+currRowVal+'"/>';}mergeErrorRows = true;
			}
		}		
	});
	for (var i = 0; i < mergeArray.length; i++) {
    	if (mergeValArray[i + 1] == mergeValArray[i]) {
        	if (  $('select._includeForeignAffFlag:eq('+mergeArray[i]+')').val() !=  $('select._includeForeignAffFlag:eq('+mergeArray[i+1]+')').val()) {        	
        		errorsFound = true;
        		if(!mergeErrorFrnFlag) {
					errMessage += '<li><spring:message code="us.error.apm.sampling.mergeIndicator.frn.invalid"/>';
				}
				mergeErrorFrnFlag = true;
        	} 
        	if(censusIndicatorArray[mergeArray[i]] !=  censusIndicatorArray[mergeArray[i+1]]) {
        		errorsFound = true;
        		if(!mergeErrorSurvInd) {
					errMessage += '<li><spring:message code="us.error.apm.sampling.mergeIndicator.census.invalid"/>';
				}
				mergeErrorSurvInd = true;
        	}
   	 	}
	}
	if(errorsFound) {
		$("#serverErrorMessages").html("");
		$("#uierror").html(''+errMessage+'</span>');
		unCheckSub(thisObj);
		return;
	}
	else {
		document.forms[0].operationType.value='ADD2';
		document.forms[0].submit();
	}
}
</script>
</head>
<body> 

<div class="menubg"  style="width: 100%; border: 0px; height:90px;"> 
<jsp:include page="/views/menu.jsp"/>
</div>
<div></div>

<form:form action="samplingSummary"  modelAttribute="samplingSummary">  
<form:hidden  path="operationType" />
<form:hidden  path="callLetter" />
<form:hidden  path="targetSurveyYearQuarter" />
<form:hidden  path="stepNumber" />
<form:hidden  path="hasForeignAffiliationFlag" />
<br/>
<div class="o50" style="font-size:1.3em;font-weight:bold;width:1000px;margin:0 auto;border:0px #cccccc solid;">
Music User: <c:out value="${samplingSummary.callLetter}"></c:out>  <br/>
Target Distribution: <c:out value="${samplingSummary.targetSurveyYearQuarter}"></c:out><br/>
Grid Level: <c:out value="${samplingSummary.stepNumber}"></c:out><br/>
</div>
 <table style="font-size:1.0em;width:1000px;margin:0 auto;border:0px #cccccc solid;">
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
<table class="contenttable alternatecolors" id="mainData" style="font-size:1.0em;width:1000px;margin:0 auto;border:0px #cccccc solid;" >     
<thead> 
<tr class="tablerowheader" >    
	<th width="8%">Play Count Range Start</th>
	<th width="8%">Play Count Range End</th>
	<th width="8%">Total Matched WP</th> 
	<th width="8%">Total Matched Play Count</th>
	<th width="8%">Total Unmatched WP</th>  
	<th width="8%">Total Unmatched Play Count</th>
	
    <c:if test="${samplingSummary.hasForeignAffiliationFlag eq 'Y' }">
	<th width="8%">Total Unmatched FA WP</th>  
	<th width="8%">Total Unmatched FA Play Count</th>
	<th width="5%">Include Foreign Affiliates</th>
	</c:if >
	<th width="5%">Census Sample Indicator</th>
    <c:if test="${samplingSummary.stepNumber ne 'L2' }">
	<th width="5%">Merge</th>
	</c:if>
    <c:if test="${samplingSummary.stepNumber eq 'L2' }">
	<th width="5%">Sample %</th>
	<th width="5%">Sample Count</th>
	<th width="5%">Start</th>
	<th width="5%">Skip</th>
	</c:if>
</tr>
</thead>

<tbody>
<% int totNumRows = 0; %>  	

<c:if test="${not empty samplingSummary.searchResults}"> 
 <c:forEach var="sampleResult" items= "${samplingSummary.searchResults}" varStatus="currentIndexId">  
<% totNumRows++; %>	
<c:choose>
   <c:when test="${currentIndexId.index % 2 == 0 }">
 <tr class="o20" style="line-height:1.7em;">
 </c:when>
 <c:otherwise>
 <tr style="line-height:1.7em;">
 </c:otherwise>
         </c:choose>
<td align="right"><c:out value="${sampleResult.rangeStart}"></c:out></td>
<td align="right"><c:out value="${sampleResult.rangeEnd}"></c:out></td>
<td align="right"><c:out value="${sampleResult.totalMatchedPerfCount}"></c:out></td> 
<td align="right"><c:out value="${sampleResult.totalMatchedPlayCount}"></c:out></td>
<td align="right"><c:out value="${sampleResult.totalUnMatchedPerfCount}"></c:out>
<input type="hidden" name="tupc" value="<c:out value="${sampleResult.totalUnMatchedPerfCount}"></c:out>"/></td>  
<td align="right"><c:out value="${sampleResult.totalUnMatchedPlayCount}"></c:out></td>

<c:if test="${samplingSummary.hasForeignAffiliationFlag eq 'Y'}" >
<td align="right"><c:out value="${sampleResult.totalUnMatchedForeignPerfCount}"></c:out>
<input type="hidden" value="<c:out value="${sampleResult.totalUnMatchedForeignPerfCount}"></c:out>"/>
</td>  
<td align="right"><c:out value="${sampleResult.totalUnMatchedForeignPlayCount}"></c:out></td>
<td align="center">
<c:if test="${samplingSummary.stepNumber eq 'L2'}" >
<c:out value="${sampleResult.includeForeignAffFlag}"/>
<input type="hidden" name="iFi" value="<c:out value="${sampleResult.includeForeignAffFlag}"></c:out>"/>
</c:if>
<c:if test="${samplingSummary.stepNumber ne 'L2'}" >
<form:select path="includeForeignAffFlag" cssClass="_includeForeignAffFlag">
<form:option value="Y">Y</form:option>
<form:option value="N">N</form:option>
</form:select>
</c:if>
</td>
</c:if>
<c:if test="${samplingSummary.hasForeignAffiliationFlag eq 'N'}" >
<form:hidden  path="includeForeignAffFlag"/>
</c:if>

<c:if test="${samplingSummary.stepNumber ne 'L2'}" >
<td align="center">
<form:select path="censusSampleIndicator" cssClass="_censusSampleIndicator">
<form:option value=""></form:option>
<form:option value="C"></form:option>>
</form:select>
</td>
</c:if>
<c:if test="${samplingSummary.stepNumber eq 'L2'}" >
<td align="center"><c:out value="${censusSampleIndicator}"/><form:hidden path="censusSampleIndicator"/></td>
</c:if>

<c:if test="${samplingSummary.stepNumber ne 'L2'}" >
<td align="center"><form:input path="mergeIndicator" size="1" maxlength="2" cssClass="_mergeIndicator uppercase"/></td>
</c:if>
<c:if test="${samplingSummary.stepNumber eq 'L2'}" >
<td>
<c:if test="${sampleResult.censusSampleIndicator eq 'C'}">
<c:out value="${sampleResult.selectPercent}"/>
</c:if>

<c:if test="${sampleResult.censusSampleIndicator eq 'S'}">
	<form:input path="selectPercent" size="5" maxlength="9"/>
	<input type="hidden" name="originalSelectPercent" value="<c:out value="${sampleResult.selectPercent}"/>"/>
</c:if>	
</td>
	
	<td>
<c:if test="${sampleResult.censusSampleIndicator eq 'C'}">
	<c:out value="${sampleResult.selectCount}"></c:out>
</c:if>
<c:if test="${sampleResult.censusSampleIndicator eq 'S'}">
	<form:input  path="selectCount" size="5" maxlength="9"/>
	<input type="hidden" name="originalSelectCount" value="<c:out value="${sampleResult.selectPercent}"/>"/>
</c:if>	
	</td>
	<td>
	
<c:if test="${sampleResult.censusSampleIndicator eq 'C'}">	
<c:out value="${sampleResult.selectStart}"></c:out>
</c:if>
<c:if test="${sampleResult.censusSampleIndicator eq 'S'}">
	<form:input name="sampleResult" path="selectStart" size="3"  maxlength="9"/>
	<input type="hidden"  value="<c:out value="${sampleResult.selectStart}"/>"/>
</c:if>	
</td>

	<td>
	<c:if test="${sampleResult.censusSampleIndicator eq 'C'}">
	<c:out value="${sampleResult.selectSkip}"></c:out>
</c:if>
<c:if test="${sampleResult.censusSampleIndicator eq 'S'}">
	<form:input  path="selectSkip" size="3" maxlength="9"/>
	<input type="hidden" name="originalSelectSkip" value="<c:out value="${sampleResult.selectSkip}"></c:out>"/>
</c:if>
	</td>
</c:if>
<form:hidden  path="playCountRangeId"/>
</tr>
</c:forEach>
</c:if>
</tbody>
</table>

<table class="buttonstable" style="width:500px;">
 <tr>
 <td align="left" width="60%">
 	<prep:uiWidget type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_RETURN_TO_SUMMARY %>" id="<%=UIWidgetConstants.ID_RETURN_TO_SUMMARY%>" title="Return to Summary">onclick="javascript:cancel();"</prep:uiWidget>
 	<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L1_REQUEST%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_CANCEL_SAMPLING %>" id="<%=UIWidgetConstants.ID_CANCEL_SAMPLING%>" title="Cancel Sampling">onclick="javascript:void(0);"</prep:uiWidget>
 <c:if test="${samplingSummary.stepNumber ne 'L2' }">
 
<% if(totNumRows > 0) { %>
 	<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L2_REQUEST%>"  type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_CONTINUE_TO_L2GRID %>" id="<%=UIWidgetConstants.ID_CONTINUE_TO_L2GRID%>" title="Continue">onclick="javascript:checkSub(this);c2l2(this);"</prep:uiWidget>
 	<% } else { %>
 	<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L2_REQUEST%>"  type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_CONTINUE_TO_L2GRID %>" id="<%=UIWidgetConstants.ID_CONTINUE_TO_L2GRID%>" title="Continue" disabled="true">onclick="javascript:c2l2(this);"</prep:uiWidget>
 	<% } %>
</c:if>
 <c:if test="${samplingSummary.stepNumber eq 'L2' }">
<% if(totNumRows > 0) { %> 	
 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L2_REQUEST%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_APPLY_SAMPLE %>" id="<%=UIWidgetConstants.ID_APPLY_SAMPLE%>" title="Apply Sample" >onclick="javascript:void(0);"</prep:uiWidget>
<% } else { %> 
<prep:uiWidget name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L2_REQUEST%>" type="<%= SecurityConstants.ANCHOR_TYPE %>" label="<%=UIWidgetConstants.LABEL_APPLY_SAMPLE %>" id="<%=UIWidgetConstants.ID_APPLY_SAMPLE%>" title="Apply Sample" disabled="true">onclick="javascript:void(0);"</prep:uiWidget>
<% } %>

</c:if>
  </td>
  
  <td width="40%">
  <c:if test="${samplingSummary.stepNumber ne 'L2' }">
 
  <table class="contenttable" style="font-size:1.2em;font-weight:normal;padding-top:10px;text-align:left;">
  	<tr>
  		<td width="65%">Number of Performances to be Sampled: </td>
  		<td width="15%"><form:input path="numberOfPerformancesToBeSampled" size="6" maxlength="6"/></td>
  		<td width="20%"></td>
  	</tr>
  	<tr>
  		<td>Unmatched WP Available for Sampling: </td>
  		<td align="left"><span id="_sumtotwp"></span>
			<input type="hidden" name="sumtotwpval" value="0" id="_sumtotwpval"/>
		</td>
		<td></td>
  	</tr>
  	<tr>
  		<td>Unmatched WP Selected for Census: </td>
  		<td align="left"><span id="_sumcenwp"></span>
			<input type="hidden" name="sumcenwpval" value="0" id="_sumcenwpval"/>
		</td>
		<td></td>
  	</tr>
  </table>
</c:if> 
  </td>
</tr>
<tr> </tr>
<tr> </tr>
<tr> </tr>
<tr> </tr>
<tr align="left">
  	    <td align="left" >  
			 <c:if test="${samplingSummary.stepNumber eq 'L1' }">
			<% if(totNumRows > 0) { %> 	
			 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L2_REQUEST%>"  type="<%= SecurityConstants.ANCHOR_TYPE %>"  
		 	label="<%=UIWidgetConstants.LABEL_BYPASS_SAMPLING %>" id="<%=UIWidgetConstants.ID_BYPASS_SAMPLING%>" title="Bypass">onclick="javascript:void(0);"</prep:uiWidget>
			<% } else { %> 
			 <prep:uiWidget name="<%= ProtectedResourcesConstants.APM_SM_200_SAMPLING_L2_REQUEST%>"  type="<%= SecurityConstants.ANCHOR_TYPE %>"  
		 	label="<%=UIWidgetConstants.LABEL_BYPASS_SAMPLING %>" id="<%=UIWidgetConstants.ID_BYPASS_SAMPLING%>" title="Bypass" disabled="true">onclick="javascript:void(0);"</prep:uiWidget>
			<% } %>
			</c:if>
  		</td>
   </tr>
 </table>
</form:form>
</body>
</html>