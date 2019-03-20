<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="common.DataConnector" %>
<%@ page import="common.DataReacher" %>
<%@ page import="Constructors.tools.Tools" %>
<%@ page import="Constructors.*" %>
<%
    DataConnector conn = new DataConnector();
    String label, SQL;
    String[][] rs;
    int total = 0;

    DataReacher dr = new DataReacher(null);
    dr.addConstructor(new AccessPerHoursDataConstructor(24 * 30));
    dr.addConstructor(new AccessPerDaysDataConstructor(30));
    dr.addConstructor(new NewuserPerDaysDataConstructor(30));
    dr.addConstructor(new NewuserPerMonthsDataConstructor(12));
    dr.addConstructor(new PageaccessPerDaysDataConstructor(30));
    dr.addConstructor(new PageaccessPerHoursDataConstructor(24 * 30));
    dr.addConstructor(new ActiveuserPerDaysDataConstructor(30));
    dr.addConstructor(new ActiveuserPerMonthsDataConstructor(12));
    dr.addConstructor(new ProductaccessPerDaysDataConstructor(30));
    dr.addConstructor(new ProductaccessPerMonthsDataConstructor(12));
    String totalData = dr.reachData().toString();
%>
<SCRIPT LANGUAGE="JavaScript">
    var data = [];
    var d = [];
    var totalData =<%=totalData%>;
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    <%
    label="总用户数量";
    total=Tools.getSum(conn,"SELECT sum(数量) FROM 日新增用户数量");
    %>
    data['<%=label%>'] =<%=total%>;
</SCRIPT>
<%@ include file="main.html" %>
