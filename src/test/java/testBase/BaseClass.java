package testBase;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {

    public WebDriver driver;
    public Logger logger;

    @Parameters("browser")
    @BeforeClass
    public void setup(@Optional("chrome") String browser) {
        logger = LogManager.getLogger(this.getClass());

        switch (browser.toLowerCase()){
            case "chrome":
                ChromeOptions op = new ChromeOptions();
                op.addArguments("--disable-notifications");
                driver = new ChromeDriver(op);
                break;

            case "edge":
                EdgeOptions eo = new EdgeOptions();
                eo.addArguments("--disable-notifications");
                driver = new EdgeDriver(eo);
                break;

            case "firefox":
                FirefoxOptions fo = new FirefoxOptions();
                fo.addArguments("--disable-notifications");
                driver = new FirefoxDriver(fo);
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.zigwheels.com/");
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }



}
