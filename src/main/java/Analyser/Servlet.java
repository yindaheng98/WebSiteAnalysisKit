package Analyser;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Servlet", urlPatterns = {"postdata"}, loadOnStartup = 1)
public class Servlet extends HttpServlet
{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MoniterData?serverTimezone=UTC";
    private static final String USER = "MoniterData";
    private static final String PASSWORD = "MoniterData";
    private Connection conn;

    public void init()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn= DriverManager.getConnection(DB_URL,USER,PASSWORD);
        }
        catch (Exception e)
        {
            System.out.println("Database connecting failed");
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        response.getWriter().print(request.getParameter("baseURI"));
        System.out.println(request.getInputStream());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        response.getWriter().print(request.getParameter("baseURI"));
        System.out.println(request.getInputStream());
    }
}
