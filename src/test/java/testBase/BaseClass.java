package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {

    public Logger logger;
    public Properties p;

    // Thread-safe driver for parallel execution
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    // Always use this getter in tests/pages
    public WebDriver getDriver() {
        return tlDriver.get();
    }

    @BeforeClass
    @Parameters({"browser", "os"})
    public void setup(@Optional("chrome") String br, @Optional("Windows") String os) throws IOException {

        // Load properties
        FileReader file = new FileReader(".//src//test//resources//config.properties");
        p = new Properties();
        p.load(file);

        logger = LogManager.getLogger(this.getClass());

        String executionEnv = p.getProperty("execution_env").trim().toLowerCase();

        WebDriver driver;

        // =========================
        // REMOTE EXECUTION (GRID)
        // =========================
        if (executionEnv.equals("remote")) {

            // Read grid url from config if available, else default
            String hubUrl = (p.getProperty("gridURL") != null)
                    ? p.getProperty("gridURL").trim()
                    : "http://localhost:4444/wd/hub";

            switch (br.toLowerCase()) {

                case "chrome": {
                    ChromeOptions options = getChromeOptionsWithDisabledNotifications();
                    options.setPlatformName(mapPlatform(os));
                    driver = new RemoteWebDriver(new URL(hubUrl), options);
                    break;
                }

                case "edge": {
                    EdgeOptions options = new EdgeOptions();
                    options.setPlatformName(mapPlatform(os));
                    // If you also want notification blocking for Edge (Chromium), you can reuse prefs:
                    HashMap<String, Object> prefs = new HashMap<>();
                    prefs.put("profile.default_content_setting_values.notifications", 2);
                    options.setExperimentalOption("prefs", prefs);

                    driver = new RemoteWebDriver(new URL(hubUrl), options);
                    break;
                }

                case "firefox": {
                    FirefoxOptions options = new FirefoxOptions();
                    options.setPlatformName(mapPlatform(os));
                    driver = new RemoteWebDriver(new URL(hubUrl), options);
                    break;
                }

                default:
                    throw new IllegalArgumentException("Invalid browser name: " + br);
            }

        } else {
            // =========================
            // LOCAL EXECUTION
            // =========================
            switch (br.toLowerCase()) {

                case "chrome":
                    driver = new ChromeDriver(getChromeOptionsWithDisabledNotifications());
                    break;

                case "edge": {
                    EdgeOptions options = new EdgeOptions();
                    // Edge is Chromium-based; same notification pref works
                    HashMap<String, Object> prefs = new HashMap<>();
                    prefs.put("profile.default_content_setting_values.notifications", 2);
                    options.setExperimentalOption("prefs", prefs);

                    driver = new EdgeDriver(options);
                    break;
                }

                case "firefox":
                    driver = new FirefoxDriver();
                    break;

                default:
                    throw new IllegalArgumentException("Invalid browser name: " + br);
            }
        }

        // Store driver in ThreadLocal
        tlDriver.set(driver);

        // Driver setup
        getDriver().manage().deleteAllCookies();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().get(p.getProperty("appURL"));
        getDriver().manage().window().maximize();
    }

    // ✅ ChromeOptions with notifications disabled
    private ChromeOptions getChromeOptionsWithDisabledNotifications() {
        ChromeOptions options = new ChromeOptions();

        // Disable browser notifications (recommended approach)
        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);

        // Optional: disable password manager popups too
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        options.setExperimentalOption("prefs", prefs);

        // Optional stability args
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");

        return options;
    }

    // ✅ Map OS string to W3C platformName values (works with Selenium 4 Grid)
    private String mapPlatform(String os) {
        if (os == null) return Platform.ANY.name();

        if (os.equalsIgnoreCase("Windows")) return Platform.WIN11.name();
        if (os.equalsIgnoreCase("Linux")) return Platform.LINUX.name();
        if (os.equalsIgnoreCase("Mac")) return Platform.MAC.name();

        return Platform.ANY.name();
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
        try {
            if (getDriver() != null) {
                getDriver().quit();
            }
        } finally {
            // Important to avoid memory leaks in parallel runs
            tlDriver.remove();
        }
    }
}