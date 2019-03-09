package common;

import java.sql.*;

public class DataConnection {
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/moniterdata?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8";//URL指向访问的数据库名，jsp_data
    private static final String user = "MoniterData";//Mysql配置时的用户名
    private static final String password = "MoniterData";//密码
    private Connection conn;
    private Statement statement;

    public DataConnection() {
        try {
            Class.forName(driver);//加载驱动程序
            conn = DriverManager.getConnection(url, user, password);//链接数据库
            statement = conn.createStatement();//用来执行sql语言
        } catch (ClassNotFoundException e) {
            System.out.println("No Drive!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("No Connection!");
            e.printStackTrace();
        }
    }

    public ResultSet query(String SQL) {
        try {
            return statement.executeQuery(SQL);
        } catch (SQLException e) {
            System.out.println("Error SQL:" + SQL);
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Close Failed!");
            e.printStackTrace();
        }
    }

    public String[][] getTimeTable(String table_name, int limit) {
        String[][] result = new String[2][];
        String SQL = "SELECT * FROM " + table_name + " ORDER BY 时间 DESC LIMIT " + limit;
        ResultSet rs = query(SQL);
        StringBuilder time_str = new StringBuilder();
        StringBuilder numb_str = new StringBuilder();
        while (true) {
            try {
                if (!rs.next()) break;
                try {
                    String time = rs.getTime(1).toString();
                    time_str.append(rs.getDate(1)).append(" ").append(time).append(",");
                } catch (Exception ignored) {
                    time_str.append(rs.getDate(1)).append(",");
                }
                numb_str.append(rs.getInt(2)).append(",");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(time_str);
        System.out.println(numb_str);
        result[0] = time_str.toString().split(",");
        result[1] = numb_str.toString().split(",");
        return result;
    }
}
