package pageObjects;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class InvalidLogin extends BasePage {
    WebDriver driver;
    public InvalidLogin(WebDriver driver){
        super(driver);
        this.driver=driver;
    }
    //Locators
    @FindBy(xpath="//div[@id='des_lIcon']")
    WebElement loginIcon;
    @FindBy(xpath="//div[@class='lgn-sc c-p txt-l pl-30 pr-30 googleSignIn']")
    WebElement googleSignin;
    @FindBy(xpath="//input[@type='email']")
    WebElement userInput;
    @FindBy(xpath="//span[text()='Next']")
    WebElement nextButton;

    //Actions
    public void clickLogin(){
        loginIcon.click();
    }
    public void clickGoogleSignin() throws InterruptedException {
        Thread.sleep(1000);
        googleSignin.click();
    }

    public void switchingToSigninPage(){
        String parentID = driver.getWindowHandle();
        Set<String> windowIDs = driver.getWindowHandles();
        List<String> windowList = new ArrayList<>(windowIDs);
        driver.switchTo().window(windowList.get(1));
        System.out.println("Switched to child tab");
        System.out.println("Child tab title: " + driver.getTitle());
    }

    public void givingUserInput(String email){
        userInput.sendKeys(email);
    }

    public void clickNextButton(){
        nextButton.click();
    }

    public void takeScreenShots() throws Exception {
        Thread.sleep(2000);
        TakesScreenshot ts = (TakesScreenshot)driver;
        File sourcefile = ts.getScreenshotAs(OutputType.FILE);
        File targetfile = new File(System.getProperty("user.dir") + "/ScreenShots/ErrorMessage.png");
        //sourcefile.renameTo(targetfile);
        FileUtils.copyFile(sourcefile, targetfile);
    }



}
