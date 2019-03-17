import Constructors.AccessPerDaysDataConstructor;
import Constructors.NewuserPerDaysDataConstructor;
import Constructors.NewuserPerMonthsDataConstructor;
import common.DataConstructor;
import org.junit.Before;
import org.junit.Test;
import common.DataReacher;
import Constructors.AccessPerHoursDataConstructor;

public class TestDataReacher {
    private DataConstructor[]dcs;
    @Before
    public void init(){
        dcs=new DataConstructor[4];
        dcs[0]=new AccessPerHoursDataConstructor(1000);
        dcs[1]=new AccessPerDaysDataConstructor(1000);
        dcs[2]=new NewuserPerDaysDataConstructor(1000);
        dcs[3]=new NewuserPerMonthsDataConstructor(1000);

    }

    @Test
    public void Test() {
        DataReacher dr=new DataReacher(dcs);
        String res=dr.reachData().toString();
        System.out.println(res);
    }
}
