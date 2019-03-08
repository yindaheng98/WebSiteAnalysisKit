package Analyser;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "Servlet", urlPatterns = {"/postdata"}, loadOnStartup = -1)
public class Servlet extends HttpServlet
{
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
        String remoteAddr = request.getRemoteAddr();
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        if (session.isNew()) {
            response.getWriter().print("suceess!session id = "+sessionId);
            System.out.println(sessionId);
        }else {
            response.getWriter().print("isexist,id="+sessionId);
            System.out.println(sessionId);
        }
        out.write("ip_address:"+remoteAddr+"<br/>");
        /////////////////////////////////////////////////////前端传递的参数获取处理
        String date = request.getParameter("date");
        String element = request.getParameter("baseURI");
        String device = request.getParameter("device");
        String event_type = request.getParameter("event_type");
        String event_description = "访问页面";
        if(!request.getParameter("event_description").equals(null)){
            event_description = request.getParameter("event_description").replace("\'","\\'");
        }
        System.out.println(date +element+" "+device+" "+event_type+" "+event_description);
        out.write(date+"<br/>");
        out.write(element+"<br/>");
        //////////////////////////////////////////////////////////////////////////////////////////////////////数据库处理，有中文乱码
        Connection conn;
        Statement stmt = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String password = "jhp126987";
            String user = "root";
            String url = "jdbc:mysql://localhost:3306/moniterdata?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8";
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            String sql1 = "INSERT INTO `事件记录` (`时间`, `IP地址`, `设备型号`, `SESSION编号`, `所在页面`, `用户`, `事件类型`, `事件描述`) VALUES ('"+date+"', '"+remoteAddr+"', '"+device+"', '"+sessionId+"', '"+element+"', 'jhp', '"+event_type+"', '"+event_description+"')";
            System.out.println(sql1);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        doGet(request, response);

    }
}