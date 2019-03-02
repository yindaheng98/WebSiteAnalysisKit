<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@page import="java.sql.*" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="net.sf.json.JSONArray" %>
<html>
<head>
    <title>jhp</title>
    <style>
        body  {

            font-family:Arial,Helvetica,sans-serif;
            font-size:1em;
            vertical-align:middle;
        }
        .blue_btn{
            padding:1em;
            background:#4090c0;
            border:1px solid #dddddd;
            color:#ffffff;
        }
        .blue_btn:hover{
            background:#50c0e0;
        }
        #main{margin-left: 7%;margin-top: 5%;}
        .left-a{ margin-left: 100px; float:left; width:600px; }
        .left-b{ margin-left: 5%;float:left; width:250px; }
        .right{ float:left; width:250px; }
    </style>
    <script src="js/jquery-1.11.0.min.js"></script>
    <script src="js/echarts.min.js"></script>
    <script src="js/china.js"></script>
    <meta charset="UTF-8">
    <script src="js/daohang.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>超酷Bootstrap 3 隐藏滑动侧边栏菜单</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<%
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/moniterdata?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";//URL指向访问的数据库名，jsp_data
    String user = "root";//Mysql配置时的用户名
    String password = "jhp126987";//密码
    int count = 0;
    String caozuoxitong = "";
    JSONObject object = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    List<String> type1=new ArrayList<>();
    try {
        Class.forName(driver);//加载驱动程序
        Connection conn = DriverManager.getConnection(url,user,password);//链接数据库
        Statement ststment = conn.createStatement();//用来执行sql语言
        String sql = "SELECT `操作系统`,COUNT(*) AS COUNT FROM `终端分析` GROUP BY `操作系统` ";
        ResultSet rs = ststment.executeQuery(sql);

        while(rs.next()){
            caozuoxitong = rs.getString("操作系统");
            count = Integer.parseInt(rs.getString("COUNT"));
            object.put("name",caozuoxitong);
            object.put("value",count);
            jsonArray.add(object);
            type1.add(caozuoxitong);
        }
        System.out.println(jsonArray);
        System.out.println(type1.toString());
        rs.close();
        conn.close();
    }catch(ClassNotFoundException e){
        System.out.println("No Drive!");
        e.printStackTrace();
    }catch (SQLException e){
        e.printStackTrace();
    }catch(Exception e){
        e.printStackTrace();
    }
%>
<SCRIPT LANGUAGE = "JavaScript" >
    var jsonArray = "<%=jsonArray %>";
    var type1 = "<%=type1 %>";
</SCRIPT >



<div style="margin-top: 2%;">
    <button class="blue_btn" style="float: right;margin-right: 10%;">导出报表</button>
    <h1 style="color: #000000;margin-left: 10%;">终端分析</h1>
</div>
<div id="main">
    <div style="background: #FFFFFF">
        <h3 style="color: #000000;">地理热力图</h3>
        <div class="left-a">
            <!-- 地图 -->
            <div id="container" style="height: 500px;width:650px;background:white;"></div>
        </div>
    </div>
    <div>
        <div class="left-b" style="background: #FFFFFF">
            <!-- 环状图1，2 -->
            <div id="chart1" style="width: 270px;height: 250px;top: 10px;;"></div>
            <div id="chart2" style="width: 270px;height: 250px;top: 10px;"></div>
        </div>
        <div class="right"  style="background: #FFFFFF">
            <!-- 环状图3，4 -->
            <div id="chart3" style="width: 270px;height: 250px;top: 10px;"></div>
            <div id="chart4" style="width: 270px;height: 250px;top: 10px;"></div>
        </div>
    </div>
</div>

<div id="wrapper">
    <div class="overlay"></div>

    <!-- Sidebar -->
    <nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation">
        <ul class="nav sidebar-nav">
            <li class="sidebar-brand">
                <a href="#">
                    Bootstrap 3
                </a>
            </li>
            <li>
                <a href="index.jsp"><i class="fa fa-fw fa-home"></i> 用户分析</a>
            </li>
            <li>
                <a href="index1.html"><i class="fa fa-fw fa-folder"></i> Page one</a>
            </li>
            <li>
                <a href="index2.jsp"><i class="fa fa-fw fa-file-o"></i> 终端分析</a>
            </li>
            <li>
                <a href="index3.html"><i class="fa fa-fw fa-cog"></i> Third page</a>
            </li>

            <li>
                <a href="index4.html"><i class="fa fa-fw fa-bank"></i> Page four</a>
            </li>
        </ul>
    </nav>
    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
    <div id="page-content-wrapper">
        <button type="button" class="hamburger is-closed animated fadeInLeft" data-toggle="offcanvas">
            <span class="hamb-top"></span>
            <span class="hamb-middle"></span>
            <span class="hamb-bottom"></span>
        </button>
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2">

                </div>
            </div>
        </div>
    </div>
    <!-- /#page-content-wrapper -->

</div>
<!-- /#wrapper -->
</body>
<script src="js/ditu.js" type="text/javascript"></script>
<script src="js/huan.js" type="text/javascript"></script>

</html>
