package Constructors;

import common.DataConnector;
import common.DataConstructor;
import common.Tools;
import net.sf.json.JSON;

import java.text.SimpleDateFormat;


/**
 * 获取每小时访问量的构造器
 */
public class AccessPerHoursDataConstructor implements DataConstructor {
    private static AccessPerHoursDataConstructor instance;
    private int dataNum = 1000;
    private String name = "小时访问量";

    /**
     * 构造函数
     *
     * @param dataNum 每次想要多少数据？
     */
    public AccessPerHoursDataConstructor(int dataNum) {
        this.dataNum = dataNum;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JSON getData(DataConnector conn) {
        String[][] timeTable = Tools.getTimeTable(conn, name, "时间", "访问量", dataNum);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return Tools.matrixJSONArray(Tools.smoothTimeTable(timeTable, dataNum, 60 * 60 * 1000, df));
    }

    @Override
    public DataConstructor getInstance() {
        if (instance == null) {
            instance = new AccessPerHoursDataConstructor(dataNum);
        }
        return instance;
    }
}
