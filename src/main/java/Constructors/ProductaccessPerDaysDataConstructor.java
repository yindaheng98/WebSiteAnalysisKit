package Constructors;

import Constructors.tools.ItemTimeTableTools;
import common.DataConnector;
import common.DataConstructor;
import net.sf.json.JSON;

import java.text.SimpleDateFormat;


/**
 * 获取每天访问量的构造器
 */
public class ProductaccessPerDaysDataConstructor implements DataConstructor {
    private static ProductaccessPerDaysDataConstructor instance;
    private int dataNum = 1000;
    private String name = "日产品访问量";

    /**
     * 构造函数
     *
     * @param dataNum 每次想要多少数据？
     */
    public ProductaccessPerDaysDataConstructor(int dataNum) {
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
                "产品",
                "day",
                df,
                "0",
                dataNum);
    }

    @Override
    public DataConstructor getInstance() {
        if (instance == null) {
            instance = new ProductaccessPerDaysDataConstructor(dataNum);
        }
        return instance;
    }
}
