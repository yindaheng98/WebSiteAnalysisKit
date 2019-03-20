package Constructors.tools;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * smoothTimeTable以及他的各种重载
 */
public class SmoothTimeTableTools {
    /**
     * 把不连续的时间表变得连续然后合并（统一时间间隔）
     *
     * @param timeTables        需要变连续然后合并的时间表
     * @param dataNum           统一后的表要多长
     * @param time_dif_which    指定时间间隔单位
     * @param df                返回的时间格式是什么
     * @param fill              缺失数据的地方补什么
     * @return 返回连续的时间表
     */
    public static String[][] mergeTimeTable(String[][][] timeTables,
                                            int dataNum,
                                            String time_dif_which,
                                            SimpleDateFormat df,
                                            String fill) {
        Date t_max = new Timestamp(0);
        for (String[][] timeTable : timeTables) {
            try {
                Date t = df.parse(timeTable[0][0]);
                if (t.getTime() > t_max.getTime()) t_max = t;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }//选出最远的时间
        String t_max_str = df.format(t_max);
        for (int i = 0; i < timeTables.length; i++) {
            String[][] timeTable = timeTables[i];
            try {
                Date t = df.parse(timeTable[0][0]);
                if (t.getTime() < t_max.getTime()) {
                    String[][] newTimeTable = new String[2][timeTable.length];
                    System.arraycopy(timeTable[0], 0, newTimeTable[0], 1, timeTable.length - 1);
                    System.arraycopy(timeTable[1], 0, newTimeTable[1], 1, timeTable.length - 1);
                    newTimeTable[0][0] = t_max_str;
                    newTimeTable[1][0] = fill == null ? timeTable[1][0] : fill;
                    timeTable = newTimeTable;
                }//时间补齐
            } catch (ParseException e) {
                e.printStackTrace();
            }
            timeTables[i] = smoothTimeTable(timeTable, dataNum, time_dif_which, df, fill);
        }
        String[][] merged = new String[timeTables.length + 1][dataNum];
        merged[0] = timeTables[0][0];
        for (int i = 0; i < timeTables.length; i++)
            merged[i + 1] = timeTables[i][1];
        return merged;
    }

    /**
     * 把不连续的时间表变得连续（统一时间间隔）
     *
     * @param timeTable         需要变连续的时间表
     * @param dataNum           统一后的表要多长
     * @param time_dif_which    指定时间间隔单位
     * @param time_dif_how_much 指定时间间隔长度
     * @param df                返回的时间格式是什么
     * @param fill              缺失数据的地方补什么，如果为null则在timeTable[i]处补上timeTable[i-1]的值
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
     * 把不连续的时间表变得连续（统一时间间隔，没有数据的地方补前一个时间的数据）
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
     * 把不连续的时间表变得连续（统一时间间隔，没有数据的地方补前一个时间的数据）
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
     * 把不连续的时间表变得连续（统一时间间隔，没有数据的地方补前一个时间的数据）
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

    private static Date addTime(Date cur, String which, int how_much) {
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
