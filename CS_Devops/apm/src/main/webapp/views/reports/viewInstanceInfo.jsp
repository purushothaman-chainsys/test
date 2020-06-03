
<!DOCTYPE HTML>     
<html>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%@ page import="com.crystaldecisions.report.web.viewer.CrystalReportViewer,
                 com.crystaldecisions.sdk.framework.IEnterpriseSession,
                 com.crystaldecisions.sdk.occa.report.reportsource.IReportSource,
                 java.net.URLEncoder,
                 com.ascap.apm.common.helpers.PrepExtPropertyHelper,
                 com.ascap.apm.common.utils.constants.PrepPropertiesConstants"%>
<%@ page import="com.crystaldecisions.sdk.framework.CrystalEnterprise"%>
<%@ page import="java.util.Calendar"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="../jsp/theme/Master.css" rel="stylesheet" type="text/css">
<TITLE></TITLE>
</HEAD>

<BODY>       

<%
IEnterpriseSession boEnterpriseSession = (IEnterpriseSession)request.getAttribute("boEnterpriseSession");
String instanceId = (String)request.getAttribute("instanceId");
String sessionid = URLEncoder.encode(boEnterpriseSession.getSerializedSession(),"UTF-8");
String token = URLEncoder.encode(boEnterpriseSession.getLogonTokenMgr().createLogonToken("",120,1),"UTF-8");
String hostname = PrepExtPropertyHelper.getInstance().getPropertyValue(PrepPropertiesConstants.CE_VIEW_HOST);
String baseUrl = "https://"+hostname+"/BOE/OpenDocument/opendoc/openDocument.jsp?iDocID=";
String url = baseUrl+instanceId+"&token="+token;
response.setStatus(response.SC_MOVED_TEMPORARILY);
response.setHeader("Location", url);
%>
</BODY>
</html>
