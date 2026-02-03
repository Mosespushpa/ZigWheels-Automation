package pageObjects;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ExcelUtility;

public class UpComingBikes  extends BasePage{

    public UpComingBikes(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
        this.act = new Actions(driver);
    }

    String path = System.getProperty("user.dir")+"//TestData//EmailData.xlsx";
    ExcelUtility ex = new ExcelUtility(path);

    @FindBy(xpath = "//nav[@class='headerNav']/ul/li[3]")
    WebElement newBikes;

    @FindBy(xpath = "//a[@title='Upcoming Bikes']")
    WebElement upcomingBikes;

    @FindBy(xpath = "//a[@title='upcoming Yamaha bikes']")
    WebElement brandName;

    @FindBy(xpath = "//span[text()='View More Bikes ']")
    WebElement viewMoreBikes;

    @FindBy(xpath = "//div[contains(text(),'Expected')]/parent::div")
    public static List<WebElement> bikeDetails;


    // hover over new bikes
    public void hoveronnewBikess(){
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//nav[@class='headerNav']")
        ));
        wait.until(ExpectedConditions.visibilityOf(newBikes));
        act.moveToElement(newBikes).pause(Duration.ofMillis(500)).perform();
        wait.until(ExpectedConditions.visibilityOf(upcomingBikes));
    }

    // click on upcoming bikes
    public void upcomingBikess(){
        wait.until(ExpectedConditions.elementToBeClickable(upcomingBikes));
        upcomingBikes.click();
    }


    public void scrollToElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void safeClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", element);
        }
    }

    //select honda Bike as model
    public void selectBikeModel() {
        scrollToElement(brandName);
        wait.until(ExpectedConditions.elementToBeClickable(brandName));
        safeClick(brandName);
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
    public void bikesDetails() throws IOException {
        scrollToElement(viewMoreBikes);
        wait.until(ExpectedConditions.elementToBeClickable(viewMoreBikes));
        if(viewMoreBikes.isDisplayed()){
            safeClick(viewMoreBikes);
        }
        int rowNum = 1;
        for(WebElement detail : bikeDetails){
            String value = detail.getText();
            String[] details = value.split("\n");
            if(Integer.parseInt(cleaning(details[1])) < 600000){
                //System.out.println("Bike "+count);
                for(int i=0;i<=2 ;i++){
                    ex.setCellValue("Sheet2",rowNum,i,details[i]);
                }
                rowNum+=1;
            }
        }
    }


}