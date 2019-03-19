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
public class ActiveuserPerMonthsDataConstructor implements DataConstructor {
    private static ActiveuserPerMonthsDataConstructor instance;
    private int dataNum = 1000;
    private String name = "月活跃用户量";

    /**
     * 构造函数
     *
     * @param dataNum 每次想要多少数据？
     */
    public ActiveuserPerMonthsDataConstructor(int dataNum) {
        this.dataNum = dataNum;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JSON getData(DataConnector conn) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        String[][] timeTable = Tools.getTimeTable(conn, name, "时间", "数量", dataNum);
        timeTable = SmoothTimeTableTools.smoothTimeTable(timeTable, dataNum, "month", df);
        return Tools.matrixJSONArray(timeTable);
    }

    @Override
    public DataConstructor getInstance() {
        if (instance == null) {
            instance = new ActiveuserPerMonthsDataConstructor(dataNum);
        }
        return instance;
    }
}
