import Constructors.*;
import org.junit.Before;
import org.junit.Test;
import common.DataReacher;

import java.io.FileNotFoundException;
import java.io.PrintStream;


public class TestDataReacher {
    private DataReacher dr;

    @Before
    public void init() {
        dr = new DataReacher(null);
        dr.addConstructor(new AccessPerHoursDataConstructor(1000));
        dr.addConstructor(new AccessPerDaysDataConstructor(30));
        dr.addConstructor(new NewuserPerDaysDataConstructor(30));
        dr.addConstructor(new NewuserPerMonthsDataConstructor(12));
        dr.addConstructor(new PageaccessPerDaysDataConstructor(30));
        dr.addConstructor(new PageaccessPerHoursDataConstructor(1000));
        dr.addConstructor(new ActiveuserPerDaysDataConstructor(30));
        dr.addConstructor(new ActiveuserPerMonthsDataConstructor(12));
        dr.addConstructor(new ProductaccessPerDaysDataConstructor(30));
        dr.addConstructor(new ProductaccessPerMonthsDataConstructor(12));
    }

    @Test
    public void Test() {
        try {

            PrintStream print = new PrintStream("test.txt");  //写好输出位置文件；
            System.setOut(print);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String res = dr.reachData().toString();
        System.out.println(res);

    }
}
