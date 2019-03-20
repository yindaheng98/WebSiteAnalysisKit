package Constructors.tools;

import common.DataConnector;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ItemTimeTableTools {
    /**
     * 获取和item相关的时间表一类的数据
     *
     * @param conn       输入数据连接
     * @param table_name 输入表名
     * @param time_col   输入存时间的列名
     * @param data_col   输入存数据的列名
     * @param item_col   输入存着item名的那一列叫什么
     * @param limit      选出多少条数据
     * @return 返回一个(item名称 - > 数据)的键值对，数据的形式同getTimeTable
     */
    public static Map<String, String[][]> getItemTimeTable(DataConnector conn,
                                                           String table_name,
                                                           String time_col,
                                                           String data_col,
                                                           String item_col,
                                                           int limit) {
        Map<String, String[][]> result = new HashMap<>();
        String SQL = "SELECT DISTINCT " + item_col + " FROM " + table_name;
        ResultSet items = conn.query(SQL);
        while (true) {
            try {
                if (!items.next()) break;
                String item = items.getString(1);
                SQL = "SELECT " + time_col + "," + data_col + " FROM " + table_name;
                SQL += " WHERE " + item_col + "='" + item + "' ORDER BY 时间 DESC LIMIT " + limit;
                String[][] data = Tools.queryTimeTable(conn, SQL);
                result.put(item, data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 将上面那个函数获取到的和item相关的时间表一类的数据smooth后转成JSON
     *
     * @param conn           输入数据连接
     * @param table_name     输入表名
     * @param time_col       输入存时间的列名
     * @param data_col       输入存数据的列名
     * @param item_col       输入存着item名的那一列叫什么
     * @param time_dif_which 指定时间间隔单位
     * @param df             返回的时间格式是什么
     * @param fill           缺失数据的地方补什么
     * @param limit          选出多少条数据
     * @return JSON:[[时间],{item名称1:[数据1],...]
     */
    public static JSON getSmoothedJSONItemTimeTable(DataConnector conn,
                                                    String table_name,
                                                    String time_col,
                                                    String data_col,
                                                    String item_col,
                                                    String time_dif_which,
                                                    SimpleDateFormat df,
                                                    String fill,
                                                    int limit) {
        Map<String, String[][]> itemTimeTable = getItemTimeTable(conn, table_name, time_col, data_col, item_col, limit);
        String[] items = new String[itemTimeTable.size()];
        String[][][] itemTimeTables = new String[itemTimeTable.size()][][];
        {
            int i = 0;
            for (String item : itemTimeTable.keySet()) {
                items[i] = item;
                itemTimeTables[i] = itemTimeTable.get(item);
                i++;
            }
        }
        String[][] smoothedItemTables = SmoothTimeTableTools.mergeTimeTable(itemTimeTables, limit, time_dif_which, df, fill);
        JSONArray result = new JSONArray();
        result.element(smoothedItemTables[0]);
        JSONObject itemData = new JSONObject();
        for (int i = 0; i < items.length; i++)
            itemData.element(items[i], smoothedItemTables[i + 1]);
        result.element(itemData);
        return result;
    }
}
