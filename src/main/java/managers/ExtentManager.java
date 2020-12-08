package managers;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExtentManager {
    public static ExtentReports extent;

    // This method starts extent report html report collection with the help of htmlReporter
    public static ExtentReports createInstance(){
        // This is just the report name with the current date. Method is below name:
        String fileName=getReportName();

        // This is where we decided to put the reports under.
        // You can see that it creates a folder inside test-output
        String directory=System.getProperty("user.dir")+"/test-output/LetsKodeIt_reports/";

        new File(directory).mkdirs(); // creating the directory as shown in the previous line

        String path=directory+fileName; // Create the path using directory and file name, generate the file directory.

        // the html reporter looks at path and knows where to add the html file including the configurations shown below.
        ExtentHtmlReporter htmlReporter=new ExtentHtmlReporter(path);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setDocumentTitle("Reports");
        htmlReporter.config().setReportName("Test Results");
        htmlReporter.config().setTheme(Theme.STANDARD);

        // ExtentReport object extent registers the key system information
        // and finalize the htmlReporter object to written on the disk
        extent=new ExtentReports();
        extent.setSystemInfo("Let's Kode","Coding intensively");
        extent.setSystemInfo("Browser","Chrome");
        extent.attachReporter(htmlReporter);
        return extent;
    }

    // This method creates the html report file name with the date object contains day-month-hour-minute-seconds and year.
    // String replace method uses regular expression to replace old characters with new ones.
    public static String getReportName(){
        Date date=new Date();
        String fileName="Report_"+date.toString().replace(":","_").
                replace(":","_")+".html";
        // we can also write it as String replaceAll method just like the one below
        // fileName2 is just an example of how replaceAll method works in the similar way.
        String fileName2= date.toString().replaceAll(":","_"); // but, we are using the other fileName,
        return fileName;
    }

    //if extent instance did not start, this method starts it
    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }

    // inner class to keep start and end report collection before and after the tests/scenarios
    public static class ExtentTestManager {
        static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
        static ExtentReports extent = ExtentManager.getInstance();

        public static synchronized ExtentTest getTest() {
            return extentTestMap.get((int) Thread.currentThread().getId());
        }

        public static synchronized void endTest() {
            extent.flush();
        }

        public static synchronized ExtentTest startTest(String testName) {
            ExtentTest test = extent.createTest(testName);
            extentTestMap.put((int) Thread.currentThread().getId(), test);
            return test;
        }
    }

}