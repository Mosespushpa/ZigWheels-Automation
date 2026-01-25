package testCases;

import Listeners.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC_001_HomePage extends BaseClass{

    @Test
    public void verify_pageTitle() {
        HomePage hp = new HomePage(DriverFactory.getDriver());

        String title = hp.getTitle(DriverFactory.getDriver());
        Assert.assertEquals(title, "New Cars, Bikes & Scooters, Used Cars, News & Reviews - ZigWheels");

    }


}








