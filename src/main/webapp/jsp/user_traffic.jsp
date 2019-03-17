<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<SCRIPT LANGUAGE="JavaScript">
    d = [];
    label = "小时访问量";
    d['时间'] = totalData[label][0];
    d['访问量'] = totalData[label][1];
    data[label] = d;
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    d = [];
    label="日访问量";
    d['时间'] = totalData[label][0];
    d['访问量'] = totalData[label][1];
    data[label] = d;
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    <%
    label="总访问量";
    total=Tools.getSum(conn,"SELECT count(*) FROM 事件记录 where 事件类型='访问页面'");
    %>
    data['<%=label%>'] =<%=total%>;
</SCRIPT>
