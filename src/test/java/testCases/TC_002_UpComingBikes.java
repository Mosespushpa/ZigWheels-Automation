package testCases;

import org.testng.annotations.Test;
import pageObjects.UpComingBikes;
import testBase.BaseClass;

public class TC_002_UpComingBikes extends BaseClass {

    @Test
    public void upComingBikesTest() throws InterruptedException {
        UpComingBikes up  = new UpComingBikes(driver);
        up.hoveronnewBikess();

        up.upcomingBikess();

        up.selectBikeModel();

        up.bikesDetails();
    }
}
