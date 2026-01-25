package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.UpComingBikes;

import java.time.Duration;
import java.util.List;

public class usedCars extends BasePage{
    public usedCars(WebDriver driver) {
        super(driver);
    }

    Actions act=new Actions(driver);
    JavascriptExecutor js = (JavascriptExecutor)driver;

    //Locating More DropDown
    @FindBy(xpath="//span[text()='MORE']")
    WebElement more;

    //Clicking Used Cars
    @FindBy(xpath="//a[@title='Used Cars' and text()='Used Cars']")
    WebElement usedCars;

    //Clicking on Chennai
    @FindBy(xpath="//a[@title='Chennai' and contains(.,'Chennai') ]")
    WebElement chennaiClick;

    //Loating Popular Models
    @FindBy(xpath="//div[@class='gsc_thin_scroll']/descendant::label")
    List<WebElement> allmodels;

    //Locating Popular Models Text
    @FindBy(xpath = "//div[contains(text(),'Pop')]")
    WebElement popular;

    public void hoverOverMore(){
        act.moveToElement(more).perform();
    }

    public void clickUsedCars(){
        usedCars.click();
    }

    public void clickChennai(){
        chennaiClick.click();
    }

    public void allModels() throws InterruptedException {
        js.executeScript("arguments[0].scrollIntoView(true);",popular);
        for (WebElement i:allmodels){
            System.out.println(i.getText());
        }
    }

}

