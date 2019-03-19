<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="common.DataConnector" %>
<%@ page import="common.DataReacher" %>
<%@ page import="common.DataConstructor" %>
<%@ page import="Constructors.*" %>
<%
    DataConnector conn = new DataConnector();
    String label, SQL;
    String[][] rs;
    int total = 0;

    DataReacher dr=new DataReacher(null);
    dr.addConstructor(new AccessPerHoursDataConstructor(1000));
    dr.addConstructor(new AccessPerDaysDataConstructor(30));
    dr.addConstructor(new NewuserPerDaysDataConstructor(30));
    dr.addConstructor(new NewuserPerMonthsDataConstructor(12));
    dr.addConstructor(new PageaccessPerDaysDataConstructor(30));
    dr.addConstructor(new PageaccessPerHoursDataConstructor(1000));
    String totalData = dr.reachData().toString();
%>
<SCRIPT LANGUAGE="JavaScript">
    var data = [];
    var d = [];
    var totalData =<%=totalData%>;
</SCRIPT>
<%@ include file="jsp/user_center.jsp" %>
<%@ include file="jsp/user_account.jsp" %>
<%@ include file="jsp/user_traffic.jsp" %>
<%@ include file="main.html" %>
