package Listeners;

import org.openqa.selenium.WebDriver;

public class DriverFactory {
    //ThreadLocal is used for safe browsing in parallel testing
    private static ThreadLocal<WebDriver> tDriver = new ThreadLocal<>();

    public static  WebDriver getDriver(){
        return tDriver.get();
    }

    public static void setDriver(WebDriver driver){
        tDriver.set(driver);
    }

    public static void removeDriver(){
        tDriver.remove();
    }
}
//when we want to execute parallelly we need this driverfactory.