package pageObjects;
import java.util.*;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class UpComingBikes  extends BasePage{


    public UpComingBikes(WebDriver driver) {
        super(driver);
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
    List<WebElement> bikeDetails;


    Actions act = new Actions(driver);
    JavascriptExecutor js = (JavascriptExecutor)driver;

    // hover over new bikes
    public void hoveronnewBikess(){
        act.moveToElement(newBikes).perform();
    }

    // click on upcoming bikes
    public void upcomingBikess(){
        upcomingBikes.click();
    }

    public void scrollvertical(String horizontalPixels,String verticalPixels){
        js.executeScript("window.scrollBy("+horizontalPixels+","+verticalPixels+")");
    }

    //select honda Bike as model
    public void selectBikeModel() throws InterruptedException {
        scrollvertical("0","1000");
        Thread.sleep(1000);
        hondaBikes.click();

    }

    public boolean viewMore(){
        if(viewMoreBikes.isDisplayed()){
            return true;
        }
        return false;
    }

    public String cleaning(String s){
        String cleaned = s.replace(".","").replace("Lakh","000").replace(" ","").replace("Rs","");
        //System.out.println(cleaned);
        return cleaned;
    }
    // retrieve bikes under specific price range
    public void bikesDetails() throws InterruptedException {
        scrollvertical("0","1000");
        Thread.sleep(1000);
        if(viewMore()){
            viewMoreBikes.click();
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
                count+=1;
            }
        }
    }


}
