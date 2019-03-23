package Constructors;

import Constructors.tools.SmoothTimeTableTools;
import Constructors.tools.Tools;
import common.DataConnector;
import common.DataConstructor;
import net.sf.json.JSON;
import net.sf.json.JSONArray;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


/**
 * 获取每小时访问量的构造器
 */
public class TotalAccessDataConstructor implements DataConstructor {
    private static TotalAccessDataConstructor instance;
    private String name = "总访问量";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JSON getData(DataConnector conn) {
        ResultSet rs=conn.query("SELECT count(*) FROM 事件记录 WHERE 事件类型='访问页面'");
        int s=0;
        try {
            rs.next();
            s=rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JSONArray ja=new JSONArray();
        ja.element(s);
        return ja;
    }

    @Override
    public DataConstructor getInstance() {
        if (instance == null) {
            instance = new TotalAccessDataConstructor();
        }
        return instance;
    }
}
