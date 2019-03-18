package Constructors;

import common.DataConnector;
import common.DataConstructor;
import common.Tools;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;


/**
 * 获取每天访问量的构造器
 */
public class PageaccessPerDaysDataConstructor implements DataConstructor {
    private static PageaccessPerDaysDataConstructor instance;
    private int dataNum = 1000;
    private String name = "日页面访问量";

    /**
     * 构造函数
     *
     * @param dataNum 每次想要多少数据？
     */
    public PageaccessPerDaysDataConstructor(int dataNum) {
        this.dataNum = dataNum;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JSON getData(DataConnector conn) {
        Map<String, String[][]> itemTimeTable = Tools.getItemTimeTable(conn, name, "时间", "访问量", "页面", dataNum);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject result = new JSONObject();
        Set<String> items = itemTimeTable.keySet();
        for (String item : items) {
            String[][] timeTable = itemTimeTable.get(item);
            timeTable = Tools.smoothTimeTable(timeTable, dataNum, 24 * 60 * 60 * 1000, df);
            result.element(item, Tools.matrixJSONArray(timeTable));
        }
        return result;
    }

    @Override
    public DataConstructor getInstance() {
        if (instance == null) {
            instance = new PageaccessPerDaysDataConstructor(dataNum);
        }
        return instance;
    }
}
