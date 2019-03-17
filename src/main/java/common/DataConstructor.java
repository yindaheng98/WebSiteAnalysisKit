package common;

import net.sf.json.JSONArray;

/**
 * 数据构造器接口，用于从数据库中读数据并构造成JSON
 */
public interface DataConstructor {
    /**
     * @return 返回数据的名称
     */
    public String getName();//获取字段名


    /**
     * @param conn 输入数据库连接
     * @return 返回数据正文
     */
    public JSONArray getData(DataConnector conn);//获取字段数据

    public static DataConstructor instance = null;

    public DataConstructor getInstance();
}
