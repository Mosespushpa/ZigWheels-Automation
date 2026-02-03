package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class InvalidLogin extends BasePage {

    public InvalidLogin(WebDriver driver){
        super(driver);
        wait = new WebDriverWait(driver,Duration.ofSeconds(10));
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
    @FindBy(xpath = "//div[contains(text(),'Enter a ')]")
    WebElement errormsg;

    //Actions
    public void clickLogin(){
        loginIcon.click();
    }

    public void clickGoogleSignin() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(googleSignin));
        wait.until(ExpectedConditions.elementToBeClickable(googleSignin));
        googleSignin.click();
    }

    public void switchingToSigninPage() {
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> windowIDs = driver.getWindowHandles();
        List<String> windowList = new ArrayList<>(windowIDs);
        driver.switchTo().window(windowList.get(1));
    }

    public void givingUserInput(String email){
        userInput.sendKeys(email);
    }

    public void clickNextButton(){
        nextButton.click();
    }

    public void waitForErrorMsg(){
        wait.until(ExpectedConditions.visibilityOf(errormsg));
    }


}