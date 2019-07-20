package common;

import net.sf.json.JSONObject;

import java.util.Arrays;

/**
 * 存储数据构造器，并用数据构造器构造JSON
 */
public class DataReacher {
    private static final DataConnector conn = new DataConnector();
    private DataConstructor[] constructors;


    /**
     * 构造函数
     *
     * @param dcs 输入一系列数据构造器，存着
     */
    public DataReacher(DataConstructor[] dcs) {
        if (dcs == null) dcs = new DataConstructor[0];
        constructors = new DataConstructor[dcs.length];
        for (int i = 0; i < dcs.length; i++)
            constructors[i] = dcs[i].getInstance();
    }

    /**
     * 加一个数据构造器
     *
     * @param dc 要加的构造器
     */
    public void addConstructor(DataConstructor dc) {
        constructors = Arrays.copyOf(constructors, constructors.length + 1);
        constructors[constructors.length - 1] = dc.getInstance();
    }

    /**
     * 加几个数据构造器
     *
     * @param dcs 要加的构造器表
     */
    public void addConstructors(DataConstructor[] dcs) {
        constructors = Arrays.copyOf(constructors, constructors.length + dcs.length);
        for (int i = 0; i < dcs.length; i++)
            constructors[constructors.length - 1 - i] = dcs[i].getInstance();
    }

    /**
     * 调用存着的构造器构造数据
     *
     * @return 构造出的json结构为{getName()的返回值:getData()的返回值,...}
     */
    public JSONObject reachData() {
        conn.connect();
        JSONObject jo = new JSONObject();
        for (DataConstructor dc : constructors) {
            jo.element(dc.getName(), dc.getData(conn));
        }
        conn.close();
        return jo;
    }

}
