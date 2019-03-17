<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="common.Tools" %>
<SCRIPT LANGUAGE="JavaScript">
    d = [];
    label = "日新增用户数量";
    d['时间'] = totalData[label][0];
    d['数量'] = totalData[label][1];
    data[label] = d;
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    d = [];
    label = "月新增用户数量";
    d['时间'] = totalData[label][0];
    d['数量'] = totalData[label][1];
    data[label] = d;
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    <%
    label="总用户数量";
    total=Tools.getSum(conn,"SELECT sum(数量) FROM 日新增用户数量");
    %>
    data['<%=label%>'] =<%=total%>;
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    <%
    label="活跃用户数量";
    total=Tools.getSum(conn,"SELECT sum(数量) FROM 日新增用户数量");
    %>
    data['<%=label%>'] =<%=total%>;
</SCRIPT>

