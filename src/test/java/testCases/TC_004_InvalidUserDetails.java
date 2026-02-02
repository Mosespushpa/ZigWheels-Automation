package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.InvalidLogin;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC_004_InvalidUserDetails extends BaseClass {

    @Test(dataProvider = "EmailLogin",dataProviderClass = DataProviders.class)
    public void invalidUserDetails(String email) throws Exception {
        try {
            logger.info("** Started TC_004_InvalidUserDetails **");
            InvalidLogin il = new InvalidLogin(getDriver());

            il.clickLogin();
            logger.info("Clicked on Login icon");

            il.clickGoogleSignin();
            logger.info("Selected Google as Signin Option");

            il.switchingToSigninPage();
            logger.info("Switched to Login Page");

            il.givingUserInput(email);
            logger.info("Invalid input is entered");

            il.clickNextButton();
            logger.info("Clicked on Next Button");

            il.waitForErrorMsg();
            logger.info("Captured a ScreenShot");

            logger.info("** Finished TC_004_InvalidUserDetails **");
        }
        catch(Exception e){
            logger.error("Error occurred: ", e.getMessage());
            Assert.fail(e.getMessage());
        }
    }
}
