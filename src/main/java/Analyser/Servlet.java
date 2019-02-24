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
        response.setCharacterEncoding("UTF-8");//���ý��ַ���"UTF-8"����������ͻ��������
        response.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ////////////////////////////////////////////////////////////////////////�����
        String name = request.getParameter("name");
        String uuu = request.getParameter("url");
        out.write("<ul>\n"
                + name + "\n"
                + uuu + "\n" +
                "</ul>\n" );
    	////////////////////////////////////////////////////////////////////////һЩ������Ϣ
        Enumeration<String> reqHeadInfos = request.getHeaderNames(); //��ȡ���е�����ͷ
        Map<String, String> map = new HashMap<>();
        reqHeadInfos.nextElement();
        while (reqHeadInfos.hasMoreElements()) {
            String headName = reqHeadInfos.nextElement();
            String headValue = request.getHeader(headName);//��������ͷ�����ֻ�ȡ��Ӧ������ͷ��ֵ
            map.put(headName,headValue);
        }
        out.write(map.get("User-Agent"));
        out.write("<br/>");
        out.write(map.get("Host"));
        out.write("<br/>");
        System.out.println(map.get("User-Agent"));
    	/////////////////////////////////////////////////////////һЩ����
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
        ////////////////////////////////////////////////////////////////ip���ַ
        String url1 = "https://ip.tianqiapi.com/?ip=36.149.206.10";
		String json = loadJSON(url1);
        JSONObject obj = JSONObject.fromObject(json);
        String country = (String) obj.get("country");
        String province = (String)obj.get("province");
        String city = (String)obj.get("city");
        String isp = (String)obj.get("isp");
        out.write(country+" "+province+" "+city+" "+isp);
        out.write("<br/>");
    	/////////////////////////////////////////////////////ǰ�˴��ݵĲ�����ȡ����
    	String date = request.getParameter("date");
    	String element = request.getParameter("element");
    	out.write(date+"<br/>");
    	out.write(element+"<br/>");
    	//////////////////////////////////////////////////////////////////////////////////////////////////////���ݿ⴦������������
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
		//�����ַ���
		StringBuilder json = new StringBuilder();
		try {
			//ͨ��һ����ʾURL��ַ���ַ������Թ���һ��URL����
			//url���캯����Ҫ�Ĳ�����
			URL oracle = new URL(url);
			//yc��URLConnection����oracle.openConnection()���ص���URLConnection���󣬸�ֵ��yc��
			URLConnection yc = oracle.openConnection();
			
			//BufferedReader���巽ʽ�ı���ȡ  
			//InputStreamReader���ֽ������ַ���֮����������ܽ��ֽ������Ϊ�ַ�����
			//������Ϊ�ֽ���ָ���ַ����������һ�������ַ�
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream(), StandardCharsets.UTF_8));// ��ֹ����
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
