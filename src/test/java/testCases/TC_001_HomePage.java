package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC_001_HomePage extends BaseClass{

    @Test
    public void verify_pageTitle() {
        HomePage hp = new HomePage(driver);

        String title = hp.getTitle(driver);
        Assert.assertEquals(title, "New Cars, Bikes & Scooters, Used Cars, News & Reviews - ZigWheels");

    }


}








