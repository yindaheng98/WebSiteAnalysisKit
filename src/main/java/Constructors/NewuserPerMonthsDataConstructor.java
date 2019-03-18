package Constructors;

import common.DataConnector;
import common.DataConstructor;
import common.Tools;
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
        return Tools.matrixJSONArray(Tools.smoothTimeTable(timeTable, dataNum, 30 * 60 * 60 * 1000, df));
    }

    @Override
    public DataConstructor getInstance() {
        if (instance == null) {
            instance = new NewuserPerMonthsDataConstructor(dataNum);
        }
        return instance;
    }
}
