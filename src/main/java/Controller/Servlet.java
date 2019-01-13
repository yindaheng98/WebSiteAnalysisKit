package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Servlet", urlPatterns = {"postdata"}, loadOnStartup = 1)
public class Servlet extends HttpServlet
{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MoniterData?useSSL=false&serverTimezone=UTC";
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

    private JSONArray toJSONArray(double[] array)
    {
        JSONArray ja = new JSONArray();
        for (double anArray : array) ja.element(anArray);
        return ja;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        response.getWriter().print("Hello, World!");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        double[] p = new double[]{0, 0, 0};
        double[] h = new double[]{0, 0, 0};
        double[] vl = new double[]{0, 0, 0};
        double[] vout = new double[]{0, 0};
        double[] vp = new double[]{0, 0, 0};

        double hMax = Double.valueOf(request.getParameter("hMax"));
        double vlMax = Double.valueOf(request.getParameter("vlMax"));
        double vpMax = Double.valueOf(request.getParameter("vpMax"));
        double pMax = Double.valueOf(request.getParameter("pMax"));
        vl[0] = Math.min(Double.valueOf(request.getParameter("liq_in")), vlMax);
        vp[0] = Math.min(Double.valueOf(request.getParameter("gas_in")), vpMax);
        for (int i = 0; i < 2; i++)//??v
        {
            vl[i + 1] = vlNext(vl[i], vlMax);
            vp[i + 1] = vpNext(vp[i], vpMax);
        }
        h[2] = vlToH(vl[0], vlMax, hMax);
        h[0] = vlToH(vl[1], vlMax, hMax);
        h[1] = vlToH(vl[2], vlMax, hMax);
        p[0] = vpToP(vp[0], vpMax, pMax);
        p[1] = vpToP(vp[1], vpMax, pMax);
        p[2] = vpToP(vp[2], vpMax, pMax);

        vout[0] = Math.min(p[1] / pMax, h[1] / hMax) * vlMax;
        vout[1] = Math.abs(p[1] / pMax - h[1] / hMax) * vlMax;

        JSONObject jo = new JSONObject();
        jo.element("p", toJSONArray(p));
        jo.element("h", toJSONArray(h));
        jo.element("vl", toJSONArray(vl));
        jo.element("vout", toJSONArray(vout));
        jo.element("vp", toJSONArray(vp));

        response.getWriter().print(jo.toString());
    }

    private static double vpNext(double vp0, double vpMax)
    {
        return (Math.exp(vp0 / 100) - 1) / (Math.exp(vpMax / 100) - 1) * vpMax;
    }

    private static double vlNext(double vl0, double vlMax)
    {
        return Math.pow(vl0, 2) / Math.pow(vlMax, 2) * vlMax;
    }

    private static double vpToP(double vp, double vpMax, double pMax)
    {
        return vp / vpMax * pMax;
    }

    private static double vlToH(double vl, double vlMax, double hMax)
    {
        return vl / vlMax * hMax;
    }
}
