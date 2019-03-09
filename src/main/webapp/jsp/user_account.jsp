<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="java.sql.SQLException" %>
<SCRIPT LANGUAGE="JavaScript">
    d = [];
    d['时间'] = [];
    d['数量'] = [];
    <%
    label="日新增用户数量";
    rs=conn.getTimeTable(label,30);
    for(int i=0;i<rs[0].length;i++){
        %>
    d['时间'].push('<%=rs[0][i]%>');
    d['数量'].push('<%=rs[1][i]%>');
    <%}%>
    data['<%=label%>'] = d;
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    d = [];
    d['时间'] = [];
    d['数量'] = [];
    <%
    label="月新增用户数量";
    rs=conn.getTimeTable(label,30);
    for(int i=0;i<rs[0].length;i++){
        %>
    d['时间'].push('<%=rs[0][i]%>');
    d['数量'].push('<%=rs[1][i]%>');
    <%}%>
    data['<%=label%>'] = d;
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    <%
    label="总用户数量";
    SQL = "SELECT sum(数量) FROM 日新增用户数量";
    rs1= conn.query(SQL);
    try {
        rs1.next();
        total=rs1.getInt(1);
    } catch (SQLException e) {e.printStackTrace();}
    %>
    data['<%=label%>'] =<%=total%>;
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    <%
    label="活跃用户数量";
    SQL = "SELECT sum(数量) FROM 日新增用户数量";
    rs1= conn.query(SQL);
    try {
        rs1.next();
        total=rs1.getInt(1);
    } catch (SQLException e) {e.printStackTrace();}
    %>
    data['<%=label%>'] =<%=total%>;
</SCRIPT>

