package Constructors.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * smoothTimeTable以及他的各种重载
 */
public class SmoothTimeTableTools {
    /**
     * 把上面那个函数返回的不连续的时间表变得连续（统一时间间隔）
     *
     * @param timeTable         需要变连续的时间表
     * @param dataNum           统一后的表要多长
     * @param time_dif_which    指定时间间隔单位
     * @param time_dif_how_much 指定时间间隔长度
     * @param df                返回的时间格式是什么
     * @param fill              缺失数据的地方补什么
     * @return 返回连续的时间表
     */
    public static String[][] smoothTimeTable(String[][] timeTable,
                                             int dataNum,
                                             String time_dif_which,
                                             int time_dif_how_much,
                                             SimpleDateFormat df,
                                             String fill) {
        Date[] datetimes = new Date[dataNum];//一定长度的时间列表
        String[] datas = new String[dataNum];//一定长度的数据列表
        try {
            datetimes[0] = df.parse(timeTable[0][0]);//先初始化第一个
            datas[0] = timeTable[1][0];
            Date this_time = addTime(datetimes[0], time_dif_which, time_dif_how_much);
            //当前时间为datetimes上一个时间的前一个小时

            int datetimesi = 1;
            int timeTablei = 1;
            while (datetimesi < dataNum) {
                datetimes[datetimesi] = this_time;
                if (fill == null)//fill=null模式为补前一个数
                    datas[datetimesi] = timeTablei < timeTable[1].length ? timeTable[1][timeTablei] : "0";
                else//fill！=null模式为补fill
                    datas[datetimesi] = fill;
                //写入当前时间和前一个时间的数据

                if (timeTablei < timeTable[0].length) {//如果没有越界
                    Date timeTable_t = df.parse(timeTable[0][timeTablei]);
                    if (timeTable_t.getTime() >= this_time.getTime()) {
                        //且时间表中的下一个时间在当前时间之后
                        datetimes[datetimesi] = timeTable_t;//则要写入时间和数据
                        datas[datetimesi] = timeTable[1][timeTablei];
                        timeTablei++;//然后时间表指向下一位
                    }
                }

                this_time = addTime(datetimes[datetimesi], time_dif_which, time_dif_how_much);
                datetimesi++;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //重新构造timeTable
        String[][] result = new String[2][dataNum];
        for (int i = 0; i < dataNum; i++) {
            result[0][i] = df.format(datetimes[i]);
            result[1][i] = datas[i];
        }
        return result;
    }

    /**
     * 把上面那个函数返回的不连续的时间表变得连续（统一时间间隔，没有数据的地方补前一个时间的数据）
     *
     * @param timeTable      需要变连续的时间表
     * @param dataNum        统一后的表要多长
     * @param time_dif_which 指定时间间隔单位
     * @param df             返回的时间格式是什么
     * @return 返回连续的时间表
     */
    public static String[][] smoothTimeTable(String[][] timeTable,
                                             int dataNum,
                                             String time_dif_which,
                                             SimpleDateFormat df,
                                             String fill) {
        return smoothTimeTable(timeTable, dataNum, time_dif_which, -1, df, fill);
    }

    /**
     * 把上面那个函数返回的不连续的时间表变得连续（统一时间间隔，没有数据的地方补前一个时间的数据）
     *
     * @param timeTable      需要变连续的时间表
     * @param dataNum        统一后的表要多长
     * @param time_dif_which 指定时间间隔单位
     * @param df             返回的时间格式是什么
     * @return 返回连续的时间表
     */
    public static String[][] smoothTimeTable(String[][] timeTable,
                                             int dataNum,
                                             String time_dif_which,
                                             SimpleDateFormat df) {
        return smoothTimeTable(timeTable, dataNum, time_dif_which, -1, df, null);
    }

    /**
     * 把上面那个函数返回的不连续的时间表变得连续（统一时间间隔，没有数据的地方补前一个时间的数据）
     *
     * @param timeTable         需要变连续的时间表
     * @param dataNum           统一后的表要多长
     * @param time_dif_which    指定时间间隔单位
     * @param time_dif_how_much 指定时间间隔长度
     * @param df                返回的时间格式是什么
     * @return 返回连续的时间表
     */
    public static String[][] smoothTimeTable(String[][] timeTable,
                                             int dataNum,
                                             String time_dif_which,
                                             int time_dif_how_much,
                                             SimpleDateFormat df) {
        return smoothTimeTable(timeTable, dataNum, time_dif_which, time_dif_how_much, df, null);
    }

    public static Date addTime(Date cur, String which, int how_much) {
        assert which.equals("year") ||
                which.equals("month") ||
                which.equals("day") ||
                which.equals("hour") ||
                which.equals("minute") ||
                which.equals("second") : "请正确指定要增减的时间单位";
        Calendar c = Calendar.getInstance();
        c.setTime(cur);
        switch (which) {
            case "year":
                c.add(Calendar.YEAR, how_much);
                break;
            case "month":
                c.add(Calendar.MONTH, how_much);
                break;
            case "day":
                c.add(Calendar.DATE, how_much);
                break;
            case "hour":
                c.add(Calendar.HOUR, how_much);
                break;
            case "minute":
                c.add(Calendar.MINUTE, how_much);
                break;
            case "second":
                c.add(Calendar.SECOND, how_much);
                break;
        }
        return c.getTime();
    }
}
