package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.UpComingBikes;
import testBase.BaseClass;

public class TC_002_UpComingBikes extends BaseClass {

    @Test
    public void upComingBikesTest() throws InterruptedException {
        try {
            logger.info("** Starting TC_002_UpComingBikes **");
            UpComingBikes up = new UpComingBikes(driver);

            up.hoveronnewBikess();
            logger.info("Hovered on NewBikes");

            up.upcomingBikess();
            logger.info("Clicked on Upcoming Bikes");

            up.selectBikeModel();
            logger.info("Selected the Bike Model");

           up.bikesDetails();
            logger.info("Retrieved Bike Details");

            logger.info("** Finished TC_002_UpComingBikes **");
        }
        catch(Exception e){
            logger.error("Error occurred: ", e.getMessage());
            Assert.fail(e.getMessage());
        }
    }
}
