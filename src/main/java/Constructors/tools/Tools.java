package Constructors.tools;

import common.DataConnector;
import net.sf.json.JSONArray;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 相关的各种工具函数
 */
public class Tools {


    /**
     * 查询时间表一类的数据
     *
     * @param conn 输入数据连接
     * @param SQL  输入语句
     * @return 返回一个字符串列表形式的数据集合
     */
    static String[][] queryTimeTable(DataConnector conn, String SQL) {
        String[][] result = new String[2][];
        ResultSet rs = conn.query(SQL);
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
        result[0] = time_str.toString().split(",");
        result[1] = numb_str.toString().split(",");
        return result;
    }

    /**
     * 获取时间表一类的数据
     *
     * @param conn       输入数据连接
     * @param table_name 输入表名
     * @param time_col   输入存时间的列名
     * @param data_col   输入存数据的列名
     * @param limit      选出多少条数据
     * @return 返回一个字符串列表形式的数据集合
     */
    public static String[][] getTimeTable(DataConnector conn,
                                          String table_name,
                                          String time_col,
                                          String data_col,
                                          int limit) {
        String SQL = "SELECT " + time_col + "," + data_col + " FROM " + table_name;
        SQL += " ORDER BY 时间 DESC LIMIT " + limit;
        return queryTimeTable(conn, SQL);
    }

    /**
     * 把字符串矩阵转化为JSONArray
     *
     * @param matrix 输入一个字符串矩阵
     * @return 输出转换完的JSONArray
     */
    public static JSONArray matrixJSONArray(String[][] matrix) {
        JSONArray jm = new JSONArray();
        for (String[] array : matrix) {
            JSONArray ja = new JSONArray();
            for (String s : array) ja.element(s);
            jm.element(ja);
        }
        return jm;
    }

    /**
     * 一个求和返回结果的函数
     *
     * @param conn 数据连接
     * @param SQL  求和语句
     * @return 整数结果
     */
    public static int getSum(DataConnector conn, String SQL) {
        ResultSet rs1 = conn.query(SQL);
        int total = 0;
        try {
            rs1.next();
            total = rs1.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }


}
