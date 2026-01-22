package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BasePage;
import pageObjects.usedCars;
import testBase.BaseClass;

public class TC_003_UsedCars extends BaseClass {

    @Test
    public void usedCars() throws InterruptedException {
        try {
            logger.info("** Started TC_003_UsedCars **");
            usedCars us = new usedCars(driver);

            us.hoverOverMore();
            logger.info("Hovered over More");

            us.clickUsedCars();
            logger.info("Clicked on Used Cars");

            us.clickChennai();
            logger.info("Selected City as Chennai");

            us.allModels();
            logger.info("Retrieved all Popular Models");

            logger.info("** Finished TC_003_UsedCars **");
        }
        catch(Exception e){
            logger.error("Error as occured");
            Assert.fail();
        }
    }

}
