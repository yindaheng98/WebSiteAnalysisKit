package Constructors;

import Constructors.tools.SmoothTimeTableTools;
import Constructors.tools.Tools;
import common.DataConnector;
import common.DataConstructor;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;


/**
 * 获取每小时访问量的构造器
 */
public class TotaluserPerMonthsDataConstructor implements DataConstructor {
    private static TotaluserPerMonthsDataConstructor instance;
    private int dataNum = 1000;
    private String name = "月用户总量";

    /**
     * 构造函数
     *
     * @param dataNum 每次想要多少数据？
     */
    public TotaluserPerMonthsDataConstructor(int dataNum) {
        this.dataNum = dataNum;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JSON getData(DataConnector conn) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        String[][] timeTableTotal = Tools.getTimeTable(conn, name, "时间", "数量", dataNum);
        timeTableTotal = SmoothTimeTableTools.smoothTimeTable(timeTableTotal, dataNum, "month", df);
        return Tools.matrixJSONArray(timeTableTotal);
    }

    @Override
    public DataConstructor getInstance() {
        if (instance == null) {
            instance = new TotaluserPerMonthsDataConstructor(dataNum);
        }
        return instance;
    }
}
