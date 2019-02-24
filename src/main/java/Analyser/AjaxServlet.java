package Analyser;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/AjaxServlet")
public class AjaxServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        // 模拟读取数据库
            System.out.println(".........");
        if(name.equals("test")) {
            response.getWriter().print("ok"); // 将结果返回到前端
        }else {
            response.getWriter().print("bad"); // 将结果返回到前端
        } }
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}



