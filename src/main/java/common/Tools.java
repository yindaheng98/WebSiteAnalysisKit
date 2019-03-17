package common;

import net.sf.json.JSONArray;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 相关的各种工具函数
 */
public class Tools {

    /**
     * 获取时间表一类的数据
     *
     * @param conn       输入数据连接
     * @param table_name 输入表名
     * @param limit      选出多少条数据
     * @return 返回一个字符串列表形式的数据集合
     */
    public static String[][] getTimeTable(DataConnector conn, String table_name, int limit) {
        String[][] result = new String[2][];
        String SQL = "SELECT * FROM " + table_name + " ORDER BY 时间 DESC LIMIT " + limit;
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
     * 把上面那个函数返回的不连续的时间表变得连续（统一时间间隔，没有数据的地方补0）
     *
     * @param timeTable 需要变连续的时间表
     * @param dataNum   统一后的表要多长
     * @param time_dif  时间间隔统一为多少，以毫秒记
     * @param df        返回的时间格式是什么
     * @return 返回连续的时间表
     */
    public static String[][] smoothTimeTable(String[][] timeTable, int dataNum, int time_dif, SimpleDateFormat df) {
        Date[] datetimes = new Date[dataNum];//一定长度的时间列表
        String[] datas = new String[dataNum];//一定长度的数据列表
        try {
            datetimes[0] = df.parse(timeTable[0][0]);//先初始化第一个
            datas[0] = timeTable[1][0];
            Date this_time = new Date(datetimes[0].getTime() - time_dif);
            //当前时间为datetimes上一个时间的前一个小时

            int datetimesi = 1;
            int timeTablei = 1;
            while (datetimesi < dataNum) {
                datetimes[datetimesi] = this_time;
                datas[datetimesi] = "0";//写入当前时间和0数据

                if (timeTablei < timeTable[0].length) {//如果没有越界
                    Date timeTable_t = df.parse(timeTable[0][timeTablei]);
                    if (timeTable_t.getTime() >= this_time.getTime()) {
                        //且时间表中的下一个时间在当前时间之后
                        datetimes[datetimesi] = timeTable_t;//则要写入时间和数据
                        datas[datetimesi] = timeTable[1][timeTablei];
                        timeTablei++;//然后时间表指向下一位
                    }
                }

                this_time = new Date(datetimes[datetimesi].getTime() - time_dif);
                datetimesi++;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //重新构造timeTable
        String[][] result = new String[2][dataNum];
        for (int i = 0; i < dataNum; i++) {
            result[0][i] = df.format(datetimes[i]);
            result[1][i] = datas[i];
        }
        return result;
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
