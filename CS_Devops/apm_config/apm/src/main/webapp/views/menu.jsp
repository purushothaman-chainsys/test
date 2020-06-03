<!DOCTYPE HTML>
<%@ page import="com.ascap.apm.common.utils.constants.UIWidgetConstants" %>
<%@ page import="com.ascap.apm.common.utils.constants.ProtectedResourcesConstants" %>
<%@ page import="com.ascap.apm.common.context.UserProfile"%>

<%@ page import="com.ascap.apm.common.context.UserSessionState" %>
<%@ page import="com.ascap.apm.common.context.UserPreference" %>  
  
<%@ page import="com.ascap.apm.common.utils.constants.Constants" %>   


<%@ taglib uri="/WEB-INF/tlds/prep.tld" prefix="prep"%>
  
<html>

<head>

<title>APM</title>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery-1.5.2.min.js"></script>
<script type="text/javascript">

function logOut(){ 
		contextRootPathTemp = window.location.pathname;
		contextRootPathTemp1 = contextRootPathTemp.substring(0, contextRootPathTemp.lastIndexOf("/"));
		contextRootPath = contextRootPathTemp1;

		top.location.href = '<%=request.getContextPath()%>/toModule.do?prefix=&page=forceAudit.do&forceTargetLink=logout&junction=' + contextRootPath;
     
 	}


</script> 


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

<%	
	UserProfile userProfile = (UserProfile) request.getSession().getAttribute(UserProfile.HTTP_SESSION_KEY);
	boolean isUserValid = true;
	
	if(userProfile == null || userProfile.getUserId() == null || "".equals(userProfile.getUserId())) {
		isUserValid = true;
	}	
	
	String lastName = userProfile.getLastName(); 
	String firstName = userProfile.getFirstName(); 
	String middleName = userProfile.getMiddleName();
	String fullName = lastName;
	String loggedUserId = userProfile.getUserId();
	if(firstName != null && !firstName.trim().equals("")){
		fullName = lastName + ", " + firstName;
	}
	if(middleName != null && !middleName.trim().equals("")){
		fullName = fullName + " " + middleName;
	}

%>

<style type="text/css">
.nav {min-width:1000px;width:1000px;float:left }
.nav>ul {min-width:1000px;width:1000px;float:left }
.nav li {float:left;list-style:none;position:relative; padding: 1px; font-size: 24px; line-height: 38px;}
.uitext {font-family:  Lucinda, Arial, sans-serif; font-size: 10pt; font-weight:bold; }
.nav li a { text-decoration: none; border:none; display:block; padding:5px 25px 5px; font-size: 16px; line-height: 22px;}
.nav {display:block;margin:0;padding:5px 0 2px;position:absolute;z-index:100;}
.nav > li > a:hover { padding:5px 25px 5px;}
.ui-corner-tr {
-moz-border-radius-topright: 15px;
-webkit-border-top-right-radius: 15px;
border-top-right-radius: 15px;
}
.ui-corner-tl {
-moz-border-radius-topleft: 15px;
-webkit-border-top-left-radius: 15px;
	border-top-left-radius: 15px;
}

*, *:after, *:before {
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}
[class*='col-'] {
   
}
.grid {
  width: 1200px;
  /*margin-top: 10px;*/
  margin-top: 0px;
  padding-top: 10px;
  border:none;
  margin: 0px auto;
  white-space: nowrap;
}
.col-2-3 {
  /*width: 80%;*/
  float: left;
  white-space: nowrap;
}
.col-2-3:hover {
  width: 80%;
  float: left;
  white-space: nowrap;
}
.col-1-3 {
  width: 100px;
  float: left;
  white-space: nowrap;
}
[class*='col-'] {
  padding-right: 20px;
  padding-left: 50px;
}
[class*='col-']:last-of-type {
  padding-right: 0;
}
.toptext {
 	padding-top:55px; 
	padding-left: 100px;
	font-size: 14px;
	font-family: Lucinda, Arial, sans-serif;
	font-weight: normal; 
	float:left;
}
.disabledLink  {
   color: #999;
text-shadow: 0px 2px 3px #AAA;
font-family:  Arial, sans-serif; font-size: 9pt;
}

.nav li {float:left;list-style:none;position:relative; padding: 1px;}
.uitext {font-family:  Arial, sans-serif; font-size: 10pt; font-weight:bold; }
.sub {font-family:  Arial, sans-serif; font-size: 9pt;}

.nav {display:block;margin:0;padding:5px 0 2px;position:absolute;z-index:100;}
.nav li ul {
    border-bottom-left-radius:3px;
    border-bottom-right-radius:3px;
    border-top-right-radius:3px;
    /*box-shadow:5px 5px 5px #555;*/
    display:none;
    margin:0;
    padding:0 0 0 0;
    position:absolute;
    width:100px;
    background-color: black;
    border-radius: 10px;
}
.nav li ul li { width:100%;  padding:0 0 0 0; border-bottom: 1px white; border-bottom-style: groove; border-bottom-color: rgb(14, 129, 92);}
.nav li ul li(:last-child) { width:100%;  padding:0 0 0 0; border-bottom: 2px white; border-bottom-style: groove; border-bottom-color: rgb(14, 129, 92);}
.nav li ul li a { border:none;  line-height:20px; margin:0; padding:2px 0 2px 5px; font-size:12px; float:left;}
.nav li:hover > ul.child {display:block;}
.nav_icons {float: left;  padding:10px 10px 0 0;}
#lefttext {float:left;	padding: 0 0 0 10px; font-family:Arial;font-size:14px;}
#righttext {float:right; padding: 0 30px 0 0; font-family:Arial;font-size:14px;}

</style>


<script type="text/javascript">
$(document).ready(function(){

	    $('.nav li').has('ul').hover(function(){
	        $(this).addClass('current').children('ul').show();
	    }, function() {
	        $(this).removeClass('current').children('ul').hide();
	    });
	    $('.nav>li>a').addClass('ui-state-default ui-corner-tl ui-corner-tr uitext');
	    
		//$('.nav li a:not([href])').removeClass('ui-state-default');
		$('.nav>li>a:not([href])').addClass('ui-state-disabled');
	    
	    $('.nav>li>a').hover(
			function(){ $(this).addClass('ui-state-hover'); }, function(){ $(this).removeClass('ui-state-hover'); }
		);
	    $('.nav>li>ul>li').hover(
			function(){  }, function(){  }
		);
		$('.nav li ul li a').addClass('topnavsubmenu');
		 $('.nav>li>ul>li>a').click(function () {
		 	var index = $(this).parent().parent().parent().index();
		 	$('.nav>li>a').removeClass('ui-state-error');
		 	$('.nav>li>a').addClass('ui-state-default ui-corner-tl ui-corner-tr uitext');
		 	$('.nav>li>a').eq(index).removeClass('ui-state-default').addClass('ui-state-error');
		 	$('.nav li').has('ul').children('ul').hide();
		 });
});
</script>

</head>

<body style="margin:auto;border:0px;min-width:1200px;">  
<div class="grid" style="white-space:nowrap;">
  <div class="col-1-3">
     <img src="<%=request.getContextPath()%>/images/ASCAP.png"/>
		<div style="font-size: 14px;font-family: Verdana; font-weight:bold;" class="topnavsubmenu">
		APM
		</div>
  </div>
  <% if(isUserValid) { %>
  <div class="col-2-3">
  	<div>
     <ul class="nav" id="1" style="margin:0px auto;">
    	  <prep:hasPermission name="<%= ProtectedResourcesConstants.APM_PS_100_SEARCH%>" value="Y">
		  <li><a target="_parent" href="<%=request.getContextPath()%>/usageHomeSearch?performanceSearchType=CRITERIA">Search</a></li>
		  </prep:hasPermission>
    	  <prep:hasPermission name="<%= ProtectedResourcesConstants.APM_PS_100_SEARCH%>" value="N">
		  <li><a>Search</a></li>
		  </prep:hasPermission>
		  
		  <prep:hasPermission name="<%= ProtectedResourcesConstants.APM_PH_200_CREATE_PERF_HDR%>" value="Y">
		  <li><a target="_parent" href="<%=request.getContextPath()%>/usageHomeSearch?actionSelected=ADD_NEW_PROGRAM_PERFORMANCE">Add Perf Header</a></li>
		  </prep:hasPermission>
    	  <prep:hasPermission name="<%= ProtectedResourcesConstants.APM_PH_200_CREATE_PERF_HDR%>" value="N">
		  <li><a>Add Perf Header</a></li>
		  </prep:hasPermission>
		            
    	  <prep:hasPermission name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_SEARCH%>" value="Y">
		  <li><a target="_parent" href="<%=request.getContextPath()%>/catalogManualMatchRequest">Catalog Match</a></li>
		  </prep:hasPermission>
    	  <prep:hasPermission name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_SEARCH%>" value="N">
		  <li><a>Catalog Match</a></li>
		  </prep:hasPermission>
		            
    	  <prep:hasPermission name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_SEARCH%>" value="Y">
		  <li><a target="_parent" href="<%=request.getContextPath()%>/apmPerfBulkRequest">Work Match</a></li>
		  </prep:hasPermission>		  
    	  <prep:hasPermission name="<%= ProtectedResourcesConstants.APM_WM_100_WRK_MATCH_SEARCH%>" value="N">
		  <li><a>Work Match</a></li>
		  </prep:hasPermission>
		            
		  <li><a href="#">Admin</a>
		  		<ul class="sub">
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_FL_100_SEARCH_APM_FILE%>" value="Y">
						<li class="o80"><a href="<%=request.getContextPath()%>/apmFiles" style="color:#fff;text-decoration:underline;">APM Files</a></li>
					</prep:hasPermission> 
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_FL_100_SEARCH_APM_FILE%>" value="N">
 						<li class="o80" style="line-height:24px;"><span style="color:#aaa;float:left;font-size:9pt;">&nbsp;&nbsp;APM Files</span></li>
					</prep:hasPermission> 
					
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_CH_100_CHANNEL_SEARCH%>" value="Y">
						<li class="o80"><a href="<%=request.getContextPath()%>/channelList" style="color:#fff;text-decoration:underline;">Channels</a></li>
					</prep:hasPermission> 
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_CH_100_CHANNEL_SEARCH%>" value="N">
 						<li class="o80" style="line-height:24px;"><span style="color:#aaa;float:left;font-size:9pt;">&nbsp;&nbsp;Channels</span></li>
					</prep:hasPermission> 
					 
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_FD_100_FILE_DASHBOARD_SEARCH%>" value="Y">
						<li class="o80"><a href="<%=request.getContextPath()%>/usage/eoFiles.do" style="color:#fff;text-decoration:underline;">EO Files</a></li>
					</prep:hasPermission> 
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_FD_100_FILE_DASHBOARD_SEARCH%>" value="N">
 						<li class="o80" style="line-height:24px;"><span style="color:#aaa;float:left;font-size:9pt;">&nbsp;&nbsp;EO Files</span></li>
					</prep:hasPermission> 
					
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_SM_100_VIEW_SAMPLING_DETAILS%>" value="Y">
 						<li class="o80"><a target="_parent" href="<%=request.getContextPath()%>/samplingSummary" style="color:#fff;text-decoration:underline;">Sampling</a></li>
					</prep:hasPermission> 
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_SM_100_VIEW_SAMPLING_DETAILS%>" value="N">
 						<li class="o80" style="line-height:24px;"><span style="color:#aaa;float:left;font-size:9pt;">&nbsp;&nbsp;Sampling</span></li>
					</prep:hasPermission>
					
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_SM_100_VIEW_SAMPLING_DETAILS%>" value="Y">
					<li class="o80"><a target="_parent" href="<%=request.getContextPath()%>/catalogSamplingSummary" style="color:#fff;text-decoration:underline; font-size:9pt;">Catalog</a></li>
					</prep:hasPermission>
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_SM_100_VIEW_SAMPLING_DETAILS%>" value="N">
					<li class="o80" style="line-height:24px;"><span style="color:#aaa;float:left;font-size:9pt;">&nbsp;&nbsp;Catalog</span></li>
					</prep:hasPermission> 
					
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_EX_100_EXEMPTION_SEARCH%>" value="Y">
						<li class="o80"><a href="<%=request.getContextPath()%>/exemptionList" style="color:#fff;text-decoration:underline;">Exemptions</a></li>
					</prep:hasPermission> 
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_EX_100_EXEMPTION_SEARCH%>" value="N">
 						<li class="o80" style="line-height:24px;"><span style="color:#aaa;float:left;font-size:9pt;">&nbsp;&nbsp;Exemptions</span></li>
					</prep:hasPermission> 
					
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_SM_100_VIEW_SAMPLING_DETAILS%>" value="Y">
					<li class="o80"><a target="_parent" href="<%=request.getContextPath()%>/usage/logrequestSummary.do" style="color:#fff;text-decoration:underline; font-size:9pt;">Log Request</a></li>
					</prep:hasPermission>
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_SM_100_VIEW_SAMPLING_DETAILS%>" value="N">
					<li class="o80" style="line-height:24px;"><span style="color:#aaa;float:left;font-size:9pt;">&nbsp;&nbsp;Log Request</span></li>
					</prep:hasPermission> 
				</ul>
		  </li>
		  
		  <li><a href="#">Learned Match</a>
		  		<ul class="sub">
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_FL_100_SEARCH_APM_FILE%>" value="Y">
						<li class="o80"><a href="<%=request.getContextPath()%>/usageapmLearnedMatch.do" style="color:#fff;text-decoration:underline;">&nbsp;By Title&nbsp;&nbsp;</a></li>
					</prep:hasPermission> 
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_FL_100_SEARCH_APM_FILE%>" value="N">
 						<li class="o80" style="line-height:24px;"><span style="color:#aaa;float:left;font-size:9pt;">&nbsp;&nbsp;By Title</span></li>
					</prep:hasPermission> 
					
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_CH_100_CHANNEL_SEARCH%>" value="Y">
						<li class="o80"><a href="<%=request.getContextPath()%>/apmLearnedMatchEO.do" style="color:#fff;text-decoration:underline;">&nbsp;By ID&nbsp;&nbsp;</a></li>
					</prep:hasPermission> 
					<prep:hasPermission name="<%= ProtectedResourcesConstants.APM_CH_100_CHANNEL_SEARCH%>" value="N">
 						<li class="o80" style="line-height:24px;"><span style="color:#aaa;float:left;font-size:9pt;">&nbsp;&nbsp;By ID</span></li>
					</prep:hasPermission>
				</ul>
		  </li>
		  
		   <prep:hasPermission name="<%= ProtectedResourcesConstants.APM_RP_100_REPORT_SEARCH%>" value="Y">
		  <li><a target="_parent" href="<%=request.getContextPath()%>/toModule.do?prefix=&amp;page=/reportList.do?module=USAGE">Reports</a></li>
		  </prep:hasPermission>
		  <prep:hasPermission name="<%= ProtectedResourcesConstants.APM_RP_100_REPORT_SEARCH%>" value="N">
		  <li><a>Reports</a></li>
		  </prep:hasPermission>
 	</ul>
 	</div> 	
 	<div class="toptext topnavsubmenu">
 			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			You are logged in as:&nbsp;&nbsp; <%= fullName%>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a target="_parent" href="<%=request.getContextPath()%>/getPreferences" class="topnavsubmenu"><span class="topnavsubmenu">Preferences</span></a>
			&nbsp;&nbsp;&nbsp;<a href="#" onclick="javascript:logOut();" class="topnavsubmenu"><span class="topnavsubmenu">Logout</span></a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Rel 18.06.16
 	</div>
  </div>
  <% } else { %>
   <div class="col-2-3 toptext">You are not authorized to access APM in this environment, Please contact APM Support.</div>
  <% }  %>
</div>
</body>
</html>
