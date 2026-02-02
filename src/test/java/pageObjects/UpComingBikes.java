package pageObjects;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ExcelUtility;

public class UpComingBikes  extends BasePage{

    Actions act = new Actions(driver);
    JavascriptExecutor js = (JavascriptExecutor) driver;
    String path = System.getProperty("user.dir")+"//TestData//EmailData.xlsx";
    ExcelUtility ex = new ExcelUtility(path);

    public UpComingBikes(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @FindBy(xpath = "//span[contains(@class,'c-p ml-5') and contains(.,'NEW BI')]")
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
        wait.until(ExpectedConditions.visibilityOf(newBikes));
        act.moveToElement(newBikes).perform();
    }

    // click on upcoming bikes
    public void upcomingBikess(){
        wait.until(ExpectedConditions.visibilityOf(upcomingBikes));
        wait.until(ExpectedConditions.elementToBeClickable(upcomingBikes));
        upcomingBikes.click();
    }

    //select honda Bike as model
    public void selectBikeModel() {
        wait.until(ExpectedConditions.visibilityOf(brandName));
        act.scrollToElement(brandName).pause(Duration.ofSeconds(1)).perform();
        wait.until(ExpectedConditions.visibilityOf(brandName));
        wait.until(ExpectedConditions.elementToBeClickable(brandName));
        brandName.click();
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
        wait.until(ExpectedConditions.visibilityOf(viewMoreBikes));
        act.scrollToElement(viewMoreBikes).pause(Duration.ofSeconds(1)).perform();
        wait.until(ExpectedConditions.elementToBeClickable(viewMoreBikes));
        if(viewMoreBikes.isDisplayed()){
            viewMoreBikes.click();
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
