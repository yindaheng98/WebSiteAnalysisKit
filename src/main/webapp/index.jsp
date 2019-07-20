<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="common.DataConnector" %>
<%@ page import="common.DataReacher" %>
<%@ page import="Constructors.*" %>
<%
    DataConnector conn = new DataConnector();
    conn.connect();
    String label, SQL;
    String[][] rs;
    int total = 0;

    DataReacher dr = new DataReacher(null);
    dr.addConstructor(new AccessPerHoursDataConstructor(1000));
    dr.addConstructor(new AccessPerDaysDataConstructor(30));
    dr.addConstructor(new NewuserPerDaysDataConstructor(30));
    dr.addConstructor(new NewuserPerMonthsDataConstructor(12));
    dr.addConstructor(new PageaccessPerMonthsDataConstructor(30));
    dr.addConstructor(new PageaccessPerDaysDataConstructor(30));
    dr.addConstructor(new PageaccessPerHoursDataConstructor(1000));
    dr.addConstructor(new ActiveuserPerDaysDataConstructor(30));
    dr.addConstructor(new ActiveuserPerMonthsDataConstructor(12));
    dr.addConstructor(new ProductaccessPerDaysDataConstructor(30));
    dr.addConstructor(new ProductaccessPerMonthsDataConstructor(12));
    dr.addConstructor(new TotaluserPerDaysDataConstructor(30));
    dr.addConstructor(new TotaluserPerMonthsDataConstructor(12));
    dr.addConstructor(new TotaluserDataConstructor());
    dr.addConstructor(new TotalAccessDataConstructor());
    String totalData = dr.reachData().toString();
    conn.close();
%>
<SCRIPT LANGUAGE="JavaScript">
    var data = [];
    var d = [];
    var totalData =<%=totalData%>;
</SCRIPT>
<%@ include file="main.html" %>
