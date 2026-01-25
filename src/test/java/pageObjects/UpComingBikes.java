package pageObjects;
import java.time.Duration;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UpComingBikes  extends BasePage{

    Actions act;
    JavascriptExecutor js;

    public UpComingBikes(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.act = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
    }

    @FindBy(xpath = "//span[contains(@class,'c-p ml-5') and contains(.,'NEW BI')]")
    WebElement newBikes;

    @FindBy(xpath = "//a[@title='Upcoming Bikes']")
    WebElement upcomingBikes;

    @FindBy(xpath = "//a[@title='upcoming Yamaha bikes']")
    WebElement hondaBikes;

    @FindBy(xpath = "//span[text()='View More Bikes ']")
    WebElement viewMoreBikes;

    @FindBy(xpath = "//div[contains(text(),'Expected')]/parent::div")
    static List<WebElement> bikeDetails;


//    Actions act = new Actions(driver);
//    JavascriptExecutor js = (JavascriptExecutor)driver;

    // hover over new bikes
    public void hoveronnewBikess(){
        act.moveToElement(newBikes).perform();
    }

    // click on upcoming bikes
    public void upcomingBikess(){
        upcomingBikes.click();
    }

    //select honda Bike as model
    public void selectBikeModel() {
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", hondaBikes);
        wait.until(ExpectedConditions.visibilityOf(hondaBikes));
        wait.until(ExpectedConditions.elementToBeClickable(hondaBikes));
        js.executeScript("arguments[0].click();", hondaBikes);
    }

    public boolean viewMore(){
        if(viewMoreBikes.isDisplayed()){
            return true;
        }
        return false;
    }

    public String cleaning(String s){
        String cleaned = s.replace(".","")
                          .replace(" ","")
                          .replace("Rs","");
        if(cleaned.contains("Lakh")){
            cleaned = cleaned.replace("Lakh","000");
        }
        cleaned = cleaned.replace(",","");
        return cleaned;
    }

    // retrieve bikes under specific price range
    public void bikesDetails(){
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", viewMoreBikes);
        wait.until(ExpectedConditions.visibilityOf(viewMoreBikes));
        wait.until(ExpectedConditions.elementToBeClickable(viewMoreBikes));
        if(viewMore()){
            js.executeScript("arguments[0].click();", viewMoreBikes);
        }
        int count = 1;
        for(WebElement detail : bikeDetails){
            String value = detail.getText();
            String[] details = value.split("\n");
            if(Integer.parseInt(cleaning(details[1])) < 600000){
                System.out.println("Bike "+count);
                for(int i=0;i<=2 ;i++){
                    System.out.println(details[i]);
                }
                System.out.println("----------------------------------------");
                count+=1;
            }
        }
    }


}
