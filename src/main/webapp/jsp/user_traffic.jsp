<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="common.DataConnection" %>
<%@ page import="java.sql.SQLException" %>
<SCRIPT LANGUAGE="JavaScript">
    d = [];
    d['时间'] = [];
    d['访问量'] = [];
    <%
    label="小时访问量";
    rs=conn.getTimeTable(label,24);
    for(int i=0;i<rs[0].length;i++){
        %>
    d['时间'].push('<%=rs[0][i]%>');
    d['访问量'].push('<%=rs[1][i]%>');
    <%}%>
    data['<%=label%>'] = d;
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    d = [];
    d['时间'] = [];
    d['访问量'] = [];
    <%
    label="日访问量";
    rs=conn.getTimeTable(label,24);
    for(int i=0;i<rs[0].length;i++){
        %>
    d['时间'].push('<%=rs[0][i]%>');
    d['访问量'].push('<%=rs[1][i]%>');
    <%}%>
    data['<%=label%>'] = d;
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    <%
    label="总访问量";
    SQL = "SELECT count(*) FROM moniterdata.事件记录 where 事件类型='访问页面'";
    rs1= conn.query(SQL);
    try {
        rs1.next();
        total=rs1.getInt(1);
    } catch (SQLException e) {e.printStackTrace();}
    %>
    data['<%=label%>'] =<%=total%>;
</SCRIPT>
