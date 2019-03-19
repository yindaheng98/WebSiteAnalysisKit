package Constructors;

import Constructors.tools.SmoothTimeTableTools;
import Constructors.tools.Tools;
import common.DataConnector;
import common.DataConstructor;
import net.sf.json.JSON;

import java.text.SimpleDateFormat;


/**
 * 获取每小时访问量的构造器
 */
public class AccessPerDaysDataConstructor implements DataConstructor {
    private static AccessPerDaysDataConstructor instance;
    private int dataNum = 1000;
    private String name = "日访问量";

    /**
     * 构造函数
     *
     * @param dataNum 每次想要多少数据？
     */
    public AccessPerDaysDataConstructor(int dataNum) {
        this.dataNum = dataNum;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JSON getData(DataConnector conn) {
        String[][] timeTable = Tools.getTimeTable(conn, name, "时间", "访问量", dataNum);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return Tools.matrixJSONArray(SmoothTimeTableTools.smoothTimeTable(timeTable, dataNum, "day", df, "0"));
    }

    @Override
    public DataConstructor getInstance() {
        if (instance == null) {
            instance = new AccessPerDaysDataConstructor(dataNum);
        }
        return instance;
    }
}
