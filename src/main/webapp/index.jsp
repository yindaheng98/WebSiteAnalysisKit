<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="common.DataConnector" %>
<%@ page import="common.DataReacher" %>
<%@ page import="Constructors.AccessPerHoursDataConstructor" %>
<%@ page import="Constructors.AccessPerDaysDataConstructor" %>
<%@ page import="common.DataConstructor" %>
<%@ page import="Constructors.NewuserPerDaysDataConstructor" %>
<%@ page import="Constructors.NewuserPerMonthsDataConstructor" %>
<%
    DataConnector conn = new DataConnector();
    String label, SQL;
    String[][] rs;
    int total = 0;

    DataConstructor[] dcs=new DataConstructor[4];
    dcs[0]=new AccessPerHoursDataConstructor(1000);
    dcs[1]=new AccessPerDaysDataConstructor(1000);
    dcs[2]=new NewuserPerDaysDataConstructor(1000);
    dcs[3]=new NewuserPerMonthsDataConstructor(1000);
    DataReacher dr = new DataReacher(dcs);
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
