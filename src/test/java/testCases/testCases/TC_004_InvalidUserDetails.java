package testCases.testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.InvalidLogin;
import testBase.BaseClass;

public class TC_004_InvalidUserDetails extends BaseClass {

    @Test
    public void invalidUserDetails() throws Exception {
        try {
            logger.info("** Started TC_004_InvalidUserDetails **");
            InvalidLogin il = new InvalidLogin(driver);

            il.clickLogin();
            logger.info("Clicked on Login icon");

            il.clickGoogleSignin();
            logger.info("Selected Google as Signin Option");

            il.switchingToSigninPage();
            logger.info("Switched to Login Page");

            il.givingUserInput("1@##@gmailcom.com");
            logger.info("Invalid input is entered");

            il.clickNextButton();
            logger.info("Clicked on Next Button");

            il.takeScreenShots();
            logger.info("Captured a ScreenShot");

            logger.info("** Finished TC_004_InvalidUserDetails **");
        }
        catch (Exception e){
            logger.error("Error as Occured");
            Assert.fail();
        }
    }
}
