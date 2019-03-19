package Constructors;

import Constructors.tools.ItemTimeTableTools;
import Constructors.tools.Tools;
import common.DataConnector;
import common.DataConstructor;
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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return ItemTimeTableTools.getSmoothedJSONItemTimeTable(
                conn,
                name,
                "时间",
                "访问量",
                "页面",
                (long) 24 * 60 * 60 * 1000,
                df,
                "0",
                dataNum);
    }

    @Override
    public DataConstructor getInstance() {
        if (instance == null) {
            instance = new PageaccessPerDaysDataConstructor(dataNum);
        }
        return instance;
    }
}