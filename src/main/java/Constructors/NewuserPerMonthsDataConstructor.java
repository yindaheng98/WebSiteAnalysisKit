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
public class NewuserPerMonthsDataConstructor implements DataConstructor {
    private static NewuserPerMonthsDataConstructor instance;
    private int dataNum = 1000;
    private String name = "月新增用户数量";

    /**
     * 构造函数
     *
     * @param dataNum 每次想要多少数据？
     */
    public NewuserPerMonthsDataConstructor(int dataNum) {
        this.dataNum = dataNum;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JSON getData(DataConnector conn) {
        String[][] timeTable = Tools.getTimeTable(conn, name, "时间", "数量", dataNum);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        return Tools.matrixJSONArray(SmoothTimeTableTools.smoothTimeTable(timeTable, dataNum, "month", df, "0"));
    }

    @Override
    public DataConstructor getInstance() {
        if (instance == null) {
            instance = new NewuserPerMonthsDataConstructor(dataNum);
        }
        return instance;
    }
}
