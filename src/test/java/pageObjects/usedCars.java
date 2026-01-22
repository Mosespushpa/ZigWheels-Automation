package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import pageObjects.UpComingBikes;
import java.util.List;

public class usedCars extends BasePage{
    public usedCars(WebDriver driver) {
        super(driver);
    }

    Actions act=new Actions(driver);
    UpComingBikes up=new UpComingBikes(driver);

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
        up.scrollvertical("0","500");
        Thread.sleep(1000);
        for (WebElement i:allmodels){
            System.out.println(i.getText());
        }
    }

}

