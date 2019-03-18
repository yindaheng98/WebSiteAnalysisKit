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
        dr.addConstructor(new AccessPerDaysDataConstructor(1000));
        dr.addConstructor(new NewuserPerDaysDataConstructor(1000));
        dr.addConstructor(new NewuserPerMonthsDataConstructor(1000));
        dr.addConstructor(new PageaccessPerDaysDataConstructor(1000));
        dr.addConstructor(new PageaccessPerHoursDataConstructor(1000));
    }

    @Test
    public void Test() {
        try {

            PrintStream print=new PrintStream("test.txt");  //写好输出位置文件；
            System.setOut(print);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String res = dr.reachData().toString();
        System.out.println(res);

    }
}
