<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@page import="java.sql.*" %>
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
    </style>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>test</title>
    <script src="js/jquery-1.11.0.min.js"></script>
    <script src="js/echarts.min.js"></script>
    <script src="js/daohang.js"></script>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<%
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/moniterdata?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";//URL指向访问的数据库名，jsp_data
    String user = "root";//Mysql配置时的用户名
    String password = "jhp126987";//密码
    int i=1;
    try {
        Class.forName(driver);//加载驱动程序
        Connection conn = DriverManager.getConnection(url,user,password);//链接数据库
        Statement ststment = conn.createStatement();//用来执行sql语言
        String sql = "SELECT `用户` FROM `访问记录` WHERE 1 ";
        ResultSet rs = ststment.executeQuery(sql);

        while(rs.next()){
            //count = rs.getString("用户");
            i=i+1;
        }
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
    var count = "<%=i %>";
</SCRIPT >




<div style="margin-top: 2%;">
    <button class="blue_btn" style="float: right;margin-right: 10%;">导出报表</button>
    <h1 style="color: #000000;margin-left: 8%;">终端分析</h1>
</div>
<div id="main">
    <div style="float: left;background:#fff;height: 550px;width: 750px">
        <div class="head1" style="width: 600px;height: 60px;font-size: 18px;display: inline;background: #fff">
            <p style="color: #000000;float: left;margin-left: 20%;margin-top: 10px">新增用户变化图</p>
            <div style="padding-left: 78%;">
                <p style="color: #000000;margin-top: 10px">新增用户变化图</p>
            </div>
            <div style="padding-left: 80%;">
                <p style="color: #66AEDE;font-size: 40px;">
                    <script type="text/javascript" language="javascript">
                        document.write(count);
                    </script>
                </p>
            </div>

        </div>
        <div class="left-a">
            <div id="box" style="width: 600px; height:400px; background-color: pink;"></div>
        </div>
    </div>
    <div class="left-b">
        <div style="width: 500px;height: 120px;background: #FFFFFF">
            <div>
                <p style="color: #000000;font-size: 18px;">总人数</p>
            </div>
            <div >
                <p style="color: #66AEDE;font-size: 40px;"><script type="text/javascript" language="javascript">
                    document.write(count+100);
                </script></p>

            </div>
        </div>
        <div style="width: 500px;height: 370px;margin-top: 40px; background: #FFFFFF">
            <p style="color: #000000;font-size: 18px;">用户留存图</p>
            <div id="duibi" style="width: 350px; height:250px;"></div>
            <div style="float: left">
                <p style="color: #000000;font-size: 18px;">活跃用户：</p>
                <p style="color: #000000;font-size: 18px;">沉默用户：</p>
            </div>

            <p style="color: #66AEDE;font-size: 16px;">1234</p>
            <p style="color: #000000;font-size: 16px;">321</p>
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
                    test
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
                <div class="col-lg-8 col-lg-offset-2 col-md-9">

                </div>
            </div>
        </div>
    </div>
    <!-- /#page-content-wrapper -->

</div>
<!-- /#wrapper -->

<script>
    // 获取到这个DOM节点，然后初始化

    var myChart = echarts.init(document.getElementById("box"));

    // option 里面的内容基本涵盖你要画的图表的所有内容
    var option = {
        // 定义样式和数据
        backgroundColor: '#FBFBFB',
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['充值', '消费']
        },

        calculable: true,


        xAxis: [{
            axisLabel: {
                rotate: 30,
                interval: 0
            },
            axisLine: {
                lineStyle: {
                    color: 'black'
                }
            },
            type: 'category',
            boundaryGap: false,
            data: function() {
                var list = [];
                for (var i = 10; i < 21; i++) {
                    if (i <= 12) {
                        list.push('2016-' + i + '-01');
                    } else {
                        list.push('2017-' + (i - 12) + '-01');
                    }
                }
                return list;
            }()
        }],
        yAxis: [{

            type: 'value',
            axisLine: {
                lineStyle: {
                    color: '#CECECE'
                }
            }
        }],
        series: [{
            name: '人数',
            type: 'line',
            symbol: 'none',
            smooth: 0.3,
            color: ['#66AEDE'],
            data: [800, 300, 500, 800, 300, 600, 500, 600,700,800,700]
        }]
    };


    // 一定不要忘了这个，具体是干啥的我忘了，官网是这样写的使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
<script>
    var myChart = echarts.init(document.getElementById('duibi'));

    // 指定图表的配置项和数据
    option = {
        backgroundColor: "#ffffff",
        color: ["#37A2DA", "#FF9F7F"],
        legend: {
            data: ['活跃', '沉默'],
            x:"43%",
        },
        grid: {
            containLabel: true
        },
        xAxis: [{
            type: 'value'
        }],
        yAxis: [{
            type: 'category',
            axisTick: {
                show: false
            },
            data: [ '2017', '2018', '2019']
        }],
        series: [{
            name: '沉默',
            type: 'bar',
            stack: '总量',
            label: {
                normal: {
                    show: true,
                    position: 'right'
                }
            },
            data: [ 202, 241, 274]
        }, {
            name: '活跃',
            type: 'bar',
            stack: '总量',
            label: {
                normal: {
                    show: true,
                    position: 'left'
                }
            },
            data: [ -132, -101, -134]
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
</body>
</html>
