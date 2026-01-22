package testBase;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseClass {

    public WebDriver driver;

    @BeforeClass
    public void setup() {
        ChromeOptions op = new ChromeOptions();
        op.addArguments("--disable-notifications");
        driver = new ChromeDriver(op);
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.zigwheels.com/");
        driver.manage().window().maximize();
    }

//    @AfterClass
//    public void tearDown() {
//
//        driver.close();
//    }



}
