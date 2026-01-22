package testCases;

import org.testng.annotations.Test;
import pageObjects.BasePage;
import pageObjects.usedCars;
import testBase.BaseClass;

public class TC_003_UsedCars extends BaseClass {

    @Test
    public void usedCars() throws InterruptedException {
        usedCars us=new usedCars(driver);
        us.hoverOverMore();
        us.clickUsedCars();



        us.clickChennai();
        us.allModels();
    }

}
