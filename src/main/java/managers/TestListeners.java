package managers;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import static managers.Driver.driver;

public class TestListeners implements ITestListener {

    // The test listener uses extent object to record test fail-pass information
    // along with the details of failed tests such as exceptions raised

    private static ExtentReports extent = ExtentManager.createInstance();
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getTestClass().getName() + "::" +
                result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String logText = "<b>Test Method" + result.getMethod().getMethodName() + " Successful </b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.LIME);
        extentTest.get().log(Status.PASS, m);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
        extentTest.get().fail("<details><summary><b><font color=red>" +
                "Exception Occurred, click to see details: " + "</font></b></summary>" +
                exceptionMessage.replaceAll(",", "<br>") + "</details> \n");
       // WebDriver driver=((Driver)result.getInstance()).driver;
        String path = takeScreenShot(driver, result.getMethod().getMethodName());
        try {
            extentTest.get().fail("<b><font color=orange>" + "Screenshot of failure" + "</font></b>",
                    MediaEntityBuilder.createScreenCaptureFromPath(path).build());

        } catch (IOException ex) {
            extentTest.get().fail("Test Failed, cannot attach screenshot");
        }
        String logText="<b>Test Method"+methodName+"Failed</b>";
        Markup m=MarkupHelper.createLabel(logText,ExtentColor.RED);
        extentTest.get().log(Status.FAIL,m);
    }
    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test skipped: "+ getTestMethodName(result) );
        extentTest.get().log(Status.INFO, "test skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Test failed but it is within success percentage: "+ getTestMethodName(result));
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        extentTest.get().log(Status.FAIL, getTestMethodName(result));
    }

    @Override
    public void onStart(ITestContext context) {
        context.setAttribute("WebDriver", driver("chrome"));
        System.out.println("Test started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        if(extent!=null){
            extent.flush();
        }
    }
    public String takeScreenShot(WebDriver driver,  String methodName){
        String fileName=getScreenshotName(methodName);
       // String directory=System.getProperty("user.dir")+"/screenshots/";
        String directory=System.getProperty("src/test/resources/screenshots/");
        new File(directory).mkdirs();
        String path=directory+fileName;
        try{
            File screenshot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot,new File(path));
            System.out.println("******");
            System.out.println("Screenshot saved at :"+path);
            System.out.println("******");
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return path;

    }

    public  String getScreenshotName(String methodName){
        Date date=new Date();
        String fileName=methodName+"_"+date.toString().
                replace(":","").replace(":","")+".png";
        return fileName;

    }
}