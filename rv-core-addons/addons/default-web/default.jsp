<%@ page import="com.ravencloud.util.general.App" %>
<%
	String applicationPath = App.INSTANCE.contextPath();
    response.sendRedirect(applicationPath + "rest-ui/");
%>