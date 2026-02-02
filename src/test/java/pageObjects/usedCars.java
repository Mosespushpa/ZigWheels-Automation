package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.UpComingBikes;
import utilities.ExcelUtility;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class usedCars extends BasePage{
    public usedCars(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    Actions act=new Actions(driver);
    JavascriptExecutor js = (JavascriptExecutor)driver;
    String path = System.getProperty("user.dir")+"//TestData//EmailData.xlsx";
    ExcelUtility ex = new ExcelUtility(path);

    //Locating More DropDown
    @FindBy(xpath="//span[text()='MORE']/parent::li")
    WebElement more;

    //Clicking Used Cars
    @FindBy(xpath="//a[@title='Used Cars' and text()='Used Cars']")
    WebElement usedCars;

    //Clicking on Chennai
    @FindBy(xpath="//a[@title='Chennai' and contains(.,'Chennai') ]")
    WebElement chennaiClick;

    //Loating Popular Models
    @FindBy(xpath="//div[@class='gsc_thin_scroll']/descendant::label")
    static List<WebElement> allmodels;

    //Locating Popular Models Text
    @FindBy(xpath = "//div[contains(text(),'Pop')]")
    WebElement popular;

    public void hoverOverMore(){
        wait.until(ExpectedConditions.visibilityOf(more));
        act.moveToElement(more).pause(Duration.ofSeconds(1)).perform();
    }

    public void clickUsedCars(){
        wait.until(ExpectedConditions.visibilityOf(usedCars));
        wait.until(ExpectedConditions.elementToBeClickable(usedCars));
        usedCars.click();
    }

    public void clickChennai(){
        chennaiClick.click();
    }

    public void allModels() throws IOException {
        act.scrollToElement(allmodels.get(0)).pause(Duration.ofSeconds(1)).perform();
        wait.until(ExpectedConditions.visibilityOf(allmodels.get(0)));
        int cellNum = 0;
        int row = 1;
        for (WebElement i:allmodels){
            String data = i.getText();
            ex.setCellValue("Sheet3",row,cellNum,data);
            System.out.println(data);
            row++;
        }
    }

}

