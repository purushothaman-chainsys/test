<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>
<%@ page import="com.ascap.apm.common.context.UserSessionState" %>
<%@ page import="com.ascap.apm.common.context.UserPreference" %>  
<%@ page import="com.ascap.apm.common.utils.constants.Constants" %>  
<%	
String theme = UIWidgetConstants.DEFAULT_THEME_TYPE_CODE;
String bgThemeFlag = UIWidgetConstants.DEFAULT_BACKGROUND_THEME_FLAG;
UserSessionState userSessionState = (UserSessionState) session.getAttribute(UserSessionState.HTTP_SESSION_KEY);
if(userSessionState != null) {
	UserPreference userPreference = userSessionState.getUserPreference();
	if(userPreference != null) {					
		theme = userPreference.getThemeTypeCode();
		bgThemeFlag = userPreference.getBackgroundThemeFlag();
		
		if(theme == null || "".equals(theme)) {
			theme = UIWidgetConstants.DEFAULT_THEME_TYPE_CODE;
		}				
		if(com.ascap.apm.common.utils.constants.PrepConstants.THEME_RANDOM.equals(theme)){
			theme = userPreference.getRandomThemeTypeCode();	
		}
	}
}

String themePath = com.ascap.apm.controller.utils.HtmlSelectOption.getLookUpTable("ThemePaths", theme);
if(themePath == null || "".equals(themePath)) {
	themePath = UIWidgetConstants.DEFAULT_THEME_TYPE_DES;
}
if(bgThemeFlag == null || "".equals(bgThemeFlag)) {
	bgThemeFlag = UIWidgetConstants.DEFAULT_BACKGROUND_THEME_FLAG;
}

%>

<link rel="stylesheet" href="<%=request.getContextPath()%>/themes/<%=themePath%>/jquery-ui.css">
<style>
<!--
#loader {
	position: absolute; top: 225px; left: 500px; width: 210px; height: 140px; background-color: #eeeeee; font-weight:normal; font-color:black;
	padding:10px; 	font-size:1em; border-radius:10px; border-style:solid; border:1px solid red; z-index:9999;
	-moz-box-shadow:5px 5px 5px #ccc;
    -webkit-box-shadow:5px 5px 5px #ccc;
    box-shadow:5px 5px 5px #ccc;}

#loader table {
	padding:1px; border-style:solid; border-color: #eeeeee; background-color: #eeeeee;  }
#loader tr {
	font-weight:normal; font-color:black; }	
#loader .title {
	font-weight:bold; font-size:1.1em;}
#pos_center { position:relative; left:100px; }
#cancel_link:a {font-weight:normal; font-color:blue;font-size:1em;}
.loadertext {
font-size:12px;
}

.ui-dialog .ui-dialog-title {
float: left;
margin: .1em 16px .1em 0;
font-size: 15px;
}
.ui-dialog .ui-dialog-content {
position: relative;
border: 0;
padding: .5em 1em;
background: none;
overflow: auto;
zoom: 1;
font-size: 14px;
}

.opacity1Element {
    filter:alpha(opacity=80);
    -moz-opacity:0.8;  
    -khtml-opacity: 0.8;       
    opacity: 0.8;       
    color: #000000;  
}

.opacity2Element {
    filter:alpha(opacity=60);
    -moz-opacity:0.6;  
    -khtml-opacity: 0.6;       
    opacity: 0.6; 
    color: #000000;        
}
.info1 {width:100%; font-family:  Arial, sans-serif; font-size: 14px; line-height: 16px;  color: #000000;}
.info2 {width:100%; font-family:  Arial, sans-serif; font-size: 14px; line-height: 16px;  color: #000000;}

.buttonstable600 { width:600px; border:solid 0px #99DDF7; border-color: #99DDF7; font-family: Arial, sans-serif, Tahoma Verdana, Helvetica, sans-serif; font-size: 11px; margin: 0px 0px 0px 0px; padding-left:2px; padding-right:2px;padding-top: 5px; padding-bottom: 5px;}

--> 
</style> 

<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.tablesorter.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.columnmanager.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.contextmenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui-common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/userInterfaceOptions.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.ui.core.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.ui.position.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.ui.widget.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.ui.monthpicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.ui.yearquarterpicker.js"></script>



<script type="text/javascript">    
var _clx = '';
function clearField(fieldName) {
	$('input[id="'+fieldName+'"]').val('');
}


function allNumeric(inputtxt)  { 
   if(! inputtxt) return false;	
   
   inputtxt = $.trim(inputtxt);
   var numbers = /^[0-9]+$/;  
   if(inputtxt.match(numbers)) {    
	   return true;  
   }  
   else  {  
	   return false;  
   }
}   

 function rs() {
     var x = $(window).width();
     if(x < 1200) { 	
     	$('#mainData').css('padding-left','0px');
     	$('#mainData').css('padding-right','0px'); 	
     	$('#mainData').css('width','1200px');          	
     	$('#mainData').css('margin','0 auto');	
     	$('.buttonstable').css('width','1200px');
     	$('.buttonstable').css('margin','0 auto');
     	$('.searchpagestatus').css('width','1200px');
     	$('.detailstable').css('width','600px');
     	$('.detailstable').css('margin','0 auto');	     	
     	$('.info1').css('width','1200px');
     	$('.info2').css('width','1200px');
     }
     else {
     	$('body').css('width','100%');   
     	if(x > 1010) {  	
     		$('#mainData').css('padding-left','0px');
     		$('#mainData').css('padding-right','0px'); 	
     		$('#mainData').css('width','1200px');     	
     		$('#mainData').css('margin','0 auto');     	
     		$('.buttonstable').css('width','1200px');   
     		$('.buttonstable').css('margin','0 auto');
     		$('.detailstable').css('width','600px');
     		$('.detailstable').css('margin','0 auto');
     		$('.searchpagestatus').css('width','1200px');	
     	}
     	$('.info1').css('width','100%');
     	$('.info2').css('width','100%');
     }
 } 
 /*
 function shadeColor(color, shade) {
    var colorInt = parseInt(color.substring(1),16);

    var R = (colorInt & 0xFF0000) >> 16;
    var G = (colorInt & 0x00FF00) >> 8;
    var B = (colorInt & 0x0000FF) >> 0;

    R = R + Math.floor((shade/255)*R);
    G = G + Math.floor((shade/255)*G);
    B = B + Math.floor((shade/255)*B);

    var newColorInt = (R<<16) + (G<<8) + (B);
    var newColorStr = "#"+newColorInt.toString(16);

    return newColorStr;
}
*/

$(function(){

	rs();
	$(window).resize(function() {
  		rs();
	});

	if ($.browser.msie) {		
  	}
  	else {
  	}	 
  	
  	$('body').addClass('wrapper');

	$('#topIFrame').css('width','1200px');
	$('.titletable').css('width','100%');
	
	var color = $(".titletable").css("background-color");	
	var widgetcolor = $(".ui-widget-content").css("background-color");
	if (!  $.browser.msie) {		 
		$("fieldset[class^='fieldset']").css('background-color',widgetcolor);
	}
	
	if(color != undefined && color != 'undefined') {
		var cl = color.replace(/(\s+)?.$/, '');
		var cl1 = cl + ', 0.4)';
		var cl2 = cl + ', 0.6)';
		var cl3 = cl + ', 0.3)';
		var cl5 = cl + ', 0.2)';
		var cl_legend = cl + ', 0.8)';
		cl1 = cl1.replace(/b/gi, 'ba');
		cl2 = cl2.replace(/b/gi, 'ba');
		cl3 = cl3.replace(/b/gi, 'ba');
		cl5 = cl5.replace(/b/gi, 'ba');
		cl_legend = cl_legend.replace(/b/gi, 'ba');
	    $('.infotable').css("background-color", cl1);
	    $('.info2').css("background-color", cl2);
	    $('legend.legendfull').css("background-color", cl_legend);
		$('body.innerbody1200').css("background-color", cl3);
		//$('.tableroweven').css("background-color", cl5);
		$('.tableroweven').addClass('o20');
		$("fieldset[class^='fieldset']").css('background-color',cl5);
		
		if ($.browser.msie || 'N' == '<%=bgThemeFlag%>') {
			//$('fieldset.fieldsetfull').css('background-color','#FFFFFF');
			if ($.browser.msie) {
				//$('.tablerowodd').css("background-color", '#ffffff');
				//$('.tableroweven').css("background-color", '#dddddd');
			}		
	  	}
	  	_clx = cl5;
	}
	if ($.browser.msie) { 
		//$('fieldset.fieldsetfull').css('background-color', '#E4DBBF');
		$("fieldset[class^='fieldset']").addClass('o20');
	    $('legend.legendfull').addClass('o80');
	    $('.infotable').addClass('o60');
	    $('.info2').addClass('o40');	    
		$('.tableroweven').addClass('o20');
		_clx = 'o20';
	}
	
	if ('N' == '<%=bgThemeFlag%>') {
			$("fieldset[class^='fieldset']").css('background-color','#FFFFFF');
			if ($.browser.msie) {
				$('.tablerowodd').css("background-color", '#ffffff');
				$('.tableroweven').css("background-color", '#dddddd');
			}
	 }

	setButtonHover(); 
	$('.titletable').css('border','0px');
	
	
	
	$('input[id^="datepicker"]').datepicker({
			showOn: "both",
			buttonImage: "<%=request.getContextPath()%>/struts/js/calendar.gif",
			buttonImageOnly: true,
			changeMonth: true, 
			changeYear: true,
			dateFormat: "yy/mm/dd",
			showButtonPanel: true,
			buttonText: null,
			beforeShow: function(input, inst) {
				$('button.ui-datepicker-current').removeClass('ui-priority-secondary').addClass('ui-priority-primary');
            }
		})    
		
		
		
		$('input[id^="yearmonthpicker_"]').monthpicker({
			showOn:     "both",
			buttonImage: "<%=request.getContextPath()%>/struts/js/calendar.gif",
			buttonImageOnly: true,
			dateFormat: 'yymm',
			buttonText:null						
		});
		
		
		$('input[id^="yearquarterpicker_"]').yearquarterpicker({
			showOn:     "both",
			buttonImage: "<%=request.getContextPath()%>/struts/js/calendar.gif",
			buttonImageOnly: true,
			dateFormat: 'yymm',
			buttonText:null						
		});
		
		$('input[id^="yyyyQQ_"]').yearquarterpicker({
			showOn:     "button",
			buttonImage: "<%=request.getContextPath()%>/struts/js/calendar.gif",
			buttonImageOnly: true, 
			dateFormat: 'yyQ',
			buttonText:null						
		});
		
		$('button.ui-datepicker-current').live('click', function() {
		    $.datepicker._curInst.input.datepicker('setDate', new Date()).datepicker('hide').blur();
		});	
});

function setButtonHover() {
	$('.ui-state-default:not(.ui-state-disabled)').hover(
		function(){ $(this).addClass('ui-state-hover'); },  
		function(){ $(this).removeClass('ui-state-hover'); }
	);
}


function sortTable(screenNumber, tableId, columnsToDisable) {		
		var objString;		
		if(columnsToDisable) {
			if(columnsToDisable.length > 0 ) {     
				objString = ' { ';
				for(var i=0;i<columnsToDisable.length;i++) {
					if(i > 0) {	objString += ' , '; }
					objString +=columnsToDisable[i] + ' : {sorter : false} ';
				}
				objString += ' } '; 
			}
		}
		var excludeObj;
		if(objString != undefined) { excludeObj = eval('(' + objString + ')'); }    
		else { excludeObj = new Object();	}
		 
		$("#"+tableId).addClass('tablesorter');			
		$("#"+tableId).tablesorter({widgets: ['zebra']
			,headers: excludeObj 
        });
        
        $('.tablesorter>thead>tr').removeClass().addClass('ui-widget-header');
        $('.tablesorter>thead>tr>th').css('padding-bottom','10px');
        //$('.tablerowodd').css("background-color", _clx);
        
        
	if ($.browser.msie) {
	//$('.tablerowodd').addClass(_clx);//.css("background-color", '#ffffff');
	//$('.tableroweven').css("background-color", '#ffffff');
	}
        
        
}

function filterTable(screenNumber, tableId, columnsToDisable) { 
/*
	var idStr = $("#"+tableId).prevAll('div#filtertarget').eq(0).attr('id');
	var idSelectStr = $("#"+tableId).prevAll('div#filtertarget').eq(0).find('select#mySelect').attr('id'); 
	alert('select length '+ $("#"+tableId).prevAll('div#filtertarget').eq(0).find('select#mySelect').length);
	idStr += '_'+tableId;
	idSelectStr += 	'_'+tableId;
	alert('preceding id::::: ' +  idStr + ' <br>::: idSelectStr ' +  idSelectStr);
	$("#"+tableId).prevAll('div#filtertarget').eq(0).attr('id', idStr);
	$("#"+tableId).prevAll('div#'+idStr).eq(0).find('select#mySelect').attr('id', idSelectStr);
*/		
	$("#"+tableId).columnManager({listTargetID:'targetone', onClass: 'simpleon', offClass: 'simpleoff', hideInFilterList: columnsToDisable });
}

function toggleSelectAll(val, fieldNameToCheckAll) {
	$("input[name='"+fieldNameToCheckAll+"']").each( function() {
		$(this).attr("checked",val);
	})
}

function toggleSelectAllEnabled(val, fieldNameToCheckAll) {
	$("input[name='"+fieldNameToCheckAll+"']").each( function() {
		if($(this).attr("disabled") == false){
			$(this).attr("checked",val);
		}
	})
}

function toggleAnchorActiveState(anchorId, status) { 
	var elem = $('#'+anchorId)[0];
	if(status==true) {
	if(!($('#'+anchorId).hasClass('ui-state-disabled'))) {
		$('#'+anchorId).addClass('ui-state-disabled');
		elem.setAttribute('org_disabled_click',elem.getAttribute('onclick'));
		$('#'+anchorId).removeAttr('onclick');
		$('#'+anchorId).removeAttr('href');
		}
	}
	else {
		if($('#'+anchorId).hasClass('ui-state-disabled')) {
			$('#'+anchorId).removeClass('ui-state-disabled');
			elem.setAttribute('onclick',elem.getAttribute('org_disabled_click'));
			elem.setAttribute('href','javascript:void(0)');
			$('#'+anchorId).removeAttr('org_disabled_click');
		}
	}	
}

function toggleAnchorByName(anchorname, status) { 
	var elem = $('a[name='+anchorname+']')[0];
	if(status=='disable') {  
		if(!($('a[name='+anchorname+']').hasClass('ui-state-disabled'))) {
			$('a[name='+anchorname+']').addClass('ui-state-disabled');
			elem.setAttribute('org_disabled_click',elem.getAttribute('onclick'));
			$('a[name='+anchorname+']').removeAttr('onclick');
			$('a[name='+anchorname+']').removeAttr('href');
			}
		}
	else if(status=='enable') {  
		if($('a[name='+anchorname+']').hasClass('ui-state-disabled')) {
			$('a[name='+anchorname+']').removeClass('ui-state-disabled');
			elem.setAttribute('onclick',elem.getAttribute('org_disabled_click'));
			elem.setAttribute('href','javascript:void(0)');
			$('a[name='+anchorname+']').removeAttr('org_disabled_click');
		}
	}	
}

function preventDefaultClick(e){
    e.preventDefault;
    return false;
}

function hideLoader() {
	$('#loader').hide();
	//window.location=window.location; 
} 

function pleaseWait() {	
	$('#loader').show();
}
</script>

<div id="loader" style="display:none">
<table><tr><td align="center"><span class="title">Please Wait</span></td></tr>
<tr><td align="center" class="loadertext"> APM is gathering the data you requested.</td></tr>
<tr><td>&nbsp;</td></tr>
<tr><td align="center"><img src="<%=request.getContextPath()%>/images/indicator.gif"></td></tr>
<tr><td>&nbsp;</td></tr>
</table>

</div>