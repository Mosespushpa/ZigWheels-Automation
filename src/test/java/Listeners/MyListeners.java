package Listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseClass;

public class MyListeners implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    public void onStart(ITestContext context){
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/reports/zigWheelsExtentReport.html");

        sparkReporter.config().setDocumentTitle("ZigWheels Automation Report");
        sparkReporter.config().setReportName("ZigWheels Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Computer Name", "localhost");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester Name", "ZigWheels-testingTeam");
        extent.setSystemInfo("os", "Windows11");

        String browser = context.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser name", browser);

    }

    public void onTestSuccess(ITestResult result){
        test = extent.createTest(result.getName());
        if(result.getName().equals("invalidUserDetails")) {
            try {
                String imgPath = new BaseClass().takeScreenShots(result.getName());
                test.addScreenCaptureFromPath(imgPath);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        test.log(Status.PASS, "Test case PASSED is: " +result.getName());
    }
    public void onTestFailure(ITestResult result){
        test = extent.createTest(result.getName());
        try {
            String imgPath = new BaseClass().takeScreenShots(result.getName());
            test.addScreenCaptureFromPath(imgPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        test.log(Status.FAIL, "Test case FAILED is: " +result.getName());
        test.log(Status.FAIL, "Test case FAILED cause is: " +result.getThrowable());
    }

    public void onTestSkipped(ITestResult result){
        test = extent.createTest(result.getName());
        test.log(Status.SKIP, "Test case SKIPPED is: " +result.getName());
    }

    public void onFinish(ITestContext context) {
        extent.flush();
//        try {
//            String reportPath = System.getProperty("user.dir") + "./reports/zigWheelsExtentReport.html";
//            java.awt.Desktop.getDesktop().browse(new java.io.File(reportPath).toURI());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
