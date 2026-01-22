package testCases;

import org.testng.annotations.Test;
import pageObjects.InvalidLogin;
import testBase.BaseClass;

public class TC_004_InvalidUserDetails extends BaseClass {

    @Test
    public void invalidUserDetails() throws Exception {
        InvalidLogin il = new InvalidLogin(driver);
        il.clickLogin();
        il.clickGoogleSignin();
        il.switchingToSigninPage();
        il.givingUserInput("1@##@gmailcom.com");
        il.clickNextButton();
        il.takeScreenShots();
    }
}
