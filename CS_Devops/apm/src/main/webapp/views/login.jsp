<HTML>
<HEAD>
<TITLE>login.jsp</TITLE>
<link href="<%=request.getContextPath()%>/css/stylesheet.css" rel="stylesheet" type="text/css">
</HEAD>
<BODY>
<%@page import="com.ascap.apm.controller.utils.ApplicationAuthenticator"%>

<%
ApplicationAuthenticator helper = new ApplicationAuthenticator();
if (request.getParameter("code") == null
|| request.getParameter("azstate") == null) {

session.setAttribute("azstate", helper.getStateToken());
response.sendRedirect(helper.getAmazonAuthUrl());
}

%>

</BODY>
</HTML>
