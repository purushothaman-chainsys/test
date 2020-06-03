<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<TITLE></TITLE>
<script language="javascript">
contextRootPathTemp = window.location.pathname;
contextRootPathTemp1 = contextRootPathTemp.substring(0, contextRootPathTemp.lastIndexOf("/"));
contextRootPath = contextRootPathTemp1;
top.location.href ='<%=request.getContextPath()%>/logout?junction='+contextRootPath; 
</script>
</HEAD>
<BODY>
</BODY>
</html>
