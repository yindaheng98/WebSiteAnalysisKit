<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="common.DataConnection" %>
<SCRIPT LANGUAGE="JavaScript">
    var data = [];
    var d = [];
</SCRIPT>
<%
    DataConnection conn = new DataConnection();
    String label, SQL;
    String[][] rs;
    ResultSet rs1;
    int total=0;
%>
<%@ include file="jsp/user_center.jsp"%>
<%@ include file="jsp/user_account.jsp"%>
<%@ include file="jsp/user_traffic.jsp"%>
<%@ include file="main.html"%>
