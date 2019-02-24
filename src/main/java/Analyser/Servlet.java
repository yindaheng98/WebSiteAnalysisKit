package Analyser;

import net.sf.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@WebServlet(name = "Servlet", urlPatterns = {"/postdata"}, loadOnStartup = -1)
public class Servlet extends HttpServlet
{
    Connection conn;
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/moniterdata?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
    String user = "root";
    String password = "jhp126987";
    Statement stmt = null;

    public void init()
    {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");//设置将字符以"UTF-8"编码输出到客户端浏览器
        response.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ////////////////////////////////////////////////////////////////////////处理表单
        String name = request.getParameter("name");
        String uuu = request.getParameter("url");
        out.write("<ul>\n"
                + name + "\n"
                + uuu + "\n" +
                "</ul>\n" );
        ////////////////////////////////////////////////////////////////////////一些请求信息
        Enumeration<String> reqHeadInfos = request.getHeaderNames(); //获取所有的请求头
        Map<String, String> map = new HashMap<>();
        reqHeadInfos.nextElement();
        while (reqHeadInfos.hasMoreElements()) {
            String headName = reqHeadInfos.nextElement();
            String headValue = request.getHeader(headName);//根据请求头的名字获取对应的请求头的值
            map.put(headName,headValue);
        }
        out.write(map.get("User-Agent"));
        out.write("<br/>");
        out.write(map.get("Host"));
        out.write("<br/>");
        System.out.println(map.get("User-Agent"));
        /////////////////////////////////////////////////////////一些参数
        //String requestUrl = request.getRequestURL().toString();
        String requestUri = request.getRequestURI();
        String queryString = request.getQueryString();
        String remoteAddr = request.getRemoteAddr();
        //String remoteHost = request.getRemoteHost();
        //int remotePort = request.getRemotePort();
        //String method = request.getMethod();
        HttpSession session = request.getSession();
        //session.setAttribute("data", "jhp");
        String sessionId = session.getId();
        if (session.isNew()) {
            response.getWriter().print("suceess!session id = "+sessionId);
        }else {
            response.getWriter().print("isexist,id="+sessionId);
        }
        out.write("<br/>");
        //out.write("url:"+requestUrl+"<br/>");
        out.write("uri:"+requestUri+"<br/>");
        out.write("url_parameter:"+queryString+"<br/>");
        out.write("ip_address:"+remoteAddr+"<br/>");
        //out.write("host_name:"+remoteHost+"<br/>");
        //out.write("post:"+remotePort+"<br/>");
        //out.write("method:"+method+"<br/>");
        ////////////////////////////////////////////////////////////////ip查地址
        String url1 = "https://ip.tianqiapi.com/?ip=36.149.206.10";
        String json = loadJSON(url1);
        JSONObject obj = JSONObject.fromObject(json);
        String country = (String) obj.get("country");
        String province = (String)obj.get("province");
        String city = (String)obj.get("city");
        String isp = (String)obj.get("isp");
        out.write(country+" "+province+" "+city+" "+isp);
        out.write("<br/>");
        /////////////////////////////////////////////////////前端传递的参数获取处理
        String date = request.getParameter("date");
        String element = request.getParameter("element");
        out.write(date+"<br/>");
        out.write(element+"<br/>");
        //////////////////////////////////////////////////////////////////////////////////////////////////////数据库处理，有中文乱码
        Connection conn;
        Statement stmt = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,password);

            stmt = conn.createStatement();
            String sql1 = "INSERT INTO `fangwenjilu` (`time`, `ip`, `device`, `session`, `page`, `user`, `type`, `message`) VALUES ('2019-02-05 00:00:00', '1', '1', '1', '1', '1', '1', '1')";
            stmt.executeUpdate(sql1);
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e1){
            e1.printStackTrace();
        }finally{
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    private static String loadJSON(String url) {
        //处理字符串
        StringBuilder json = new StringBuilder();
        try {
            //通过一个表示URL地址的字符串可以构造一个URL对象。
            //url构造函数需要的参数。
            URL oracle = new URL(url);
            //yc是URLConnection对象，oracle.openConnection()返回的是URLConnection对象，赋值给yc。
            URLConnection yc = oracle.openConnection();

            //BufferedReader缓冲方式文本读取
            //InputStreamReader是字节流与字符流之间的桥梁，能将字节流输出为字符流，
            //并且能为字节流指定字符集，可输出一个个的字符
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream(), StandardCharsets.UTF_8));// 防止乱码
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
        }
        return json.toString();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        request.setCharacterEncoding("UTF-8");

    }
}
