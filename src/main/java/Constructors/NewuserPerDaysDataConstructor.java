package Constructors;

import common.DataConnector;
import common.DataConstructor;
import common.Tools;
import net.sf.json.JSONArray;
import java.text.SimpleDateFormat;


/**
 * 获取每小时访问量的构造器
 */
public class NewuserPerDaysDataConstructor implements DataConstructor {
    private static NewuserPerDaysDataConstructor instance;
    private int dataNum = 1000;
    private String name="日新增用户数量";

    /**
     * 构造函数
     *
     * @param dataNum 每次想要多少数据？
     */
    public NewuserPerDaysDataConstructor(int dataNum) {
        this.dataNum = dataNum;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JSONArray getData(DataConnector conn) {
        String[][] timeTable = Tools.getTimeTable(conn, name, dataNum);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return Tools.matrixJSONArray(Tools.smoothTimeTable(timeTable,dataNum,60*60*1000,df));
    }

    @Override
    public DataConstructor getInstance() {
        if (instance == null) {
            instance = new NewuserPerDaysDataConstructor(dataNum);
        }
        return instance;
    }
}
