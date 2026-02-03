package testBase;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.time.Duration;

import Listeners.DriverFactory;

import Listeners.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {

//    public WebDriver driver;
    public Logger logger;
    public Properties p;

    //Thread-safe driver for parallel execution
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    // always use this getter in tests/pages
    public WebDriver getDriver() {
        return tlDriver.get();
    }



    @BeforeClass()
    @Parameters({"browser", "os"})
    public void setup(@Optional("Chrome") String br, @Optional("Windows") String os) throws IOException {

        // Load properties
        FileReader file = new FileReader(".//src//test//resources//config.properties");
        p = new Properties();
        p.load(file);

        logger = LogManager.getLogger(this.getClass());

        String executionEnv = p.getProperty("execution_env").trim().toLowerCase();

        WebDriver driver;


        if (executionEnv.equals("remote")) {

            DesiredCapabilities cap = new DesiredCapabilities();

            // Platform mapping
            if (os.equalsIgnoreCase("Windows")) cap.setPlatform(Platform.WIN11);
            else if (os.equalsIgnoreCase("Linux")) cap.setPlatform(Platform.LINUX);
            else if (os.equalsIgnoreCase("Mac")) cap.setPlatform(Platform.MAC);
            else cap.setPlatform(Platform.ANY);

            // Browser mapping
            switch (br.toLowerCase()) {
                case "chrome":
                    cap.setBrowserName("chrome");

                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--disable-notifications");
                    cap.setCapability(ChromeOptions.CAPABILITY, options);
                    break;

                case "edge":
                    cap.setBrowserName("MicrosoftEdge");
                    break;
                case "firefox":
                    cap.setBrowserName("firefox");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid browser name: " + br);
            }

            // Read grid url from config if available, else default
            String hubUrl = p.getProperty("gridURL") != null
                    ? p.getProperty("gridURL").trim()
                    : "http://localhost:4444/wd/hub";

            driver = new RemoteWebDriver(new URL(hubUrl), cap);

        } else {
            //LOCAL execution (initialize only once)
            switch (br.toLowerCase()) {
                case "chrome":

                    ChromeOptions options = new ChromeOptions();

//                     Disable browser notifications
                    options.addArguments("--disable-notifications");

                    // (Recommended) Additional stability options
                    options.addArguments("--disable-popup-blocking");
                    options.addArguments("--disable-infobars");
             

                    driver = new ChromeDriver();

                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;

                default:
                    throw new IllegalArgumentException("Invalid browser name: " + br);
            }
        }

        // store driver in ThreadLocal
        tlDriver.set(driver);

        // Driver setup
//        getDriver().manage().deleteAllCookies();
//        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        getDriver().manage().window().maximize();
//        getDriver().get(p.getProperty("appURL"));
        driver.manage().window().maximize();
        driver.get(p.getProperty("appURL"));

    }

    public String takeScreenShots(String cap) throws Exception {

        if (getDriver() == null) {
            throw new RuntimeException("Driver is null. Screenshot cannot be captured.");
        }

        TakesScreenshot ts = (TakesScreenshot) getDriver();
        File sourceFile = ts.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir") + "/screenShots/" + cap + ".png";
        File targetFile = new File(targetFilePath);

        FileUtils.copyFile(sourceFile, targetFile);
        return targetFilePath;
    }

    @AfterClass
    public void tearDown() {
        getDriver().quit();
//        DriverFactory.removeDriver();
    }



}
