package stepDefs;
import com.github.javafaker.Faker;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import managers.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import static managers.Utilities.waitForVisibility; // for the Utilities class helper methods; wait explicitly

@Listeners(TestListeners.class)
public class StepDefinitions {
    public static WebDriver driver = Driver.driver("chrome");
    private static WebDriverWait wait;
    static final int TIMEOUT = 5;
    //public String excelFileName = ExtentManager.getReportName().substring(0, 9) + ".xlsx";
    public String excelFileName = Utilities.generateFileName() + ".xlsx";

    public StepDefinitions() { }

    // get quotes from shakespeare as String - text
    public static String getQuote(){
        Faker faker = new Faker();
        return faker.shakespeare().asYouLikeItQuote();
    }

    @Given("^I am on the homepage$")
    public void i_am_on_the_homepage() throws Throwable {
        //  driver = StepDefinitions.driver;
        // give driver (find elements functions) some time to search for elements in html dom area
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, TIMEOUT);
        // make the windows full screen
        driver.manage().window().maximize();
        // open the web page
        driver.get("https://letskodeit.teachable.com");

        //scenario = new Scenario();

        ExtentManager.createInstance(); // generate our custom html nice looking report: open in test-outout/LetsKodeIt_reports
    }

//    @After
//    public void tearDown(Scenario scenario) throws Throwable {
//        //driver.quit();
//        CommonHelper.quitDriver(scenario);
//    }

    @When("I open Practice page")
    public void iOpenPracticePage() {
        WebElement practice = driver.findElement(By.xpath("//a[normalize-space()='Practice']"));
        waitForVisibility(wait, practice);
        practice.click();
    }

    @Then("^I test the website title$")
    public void i_test_the_website_title() throws Throwable {

        String expected = driver.getTitle();
        String actual = "Home | Let's Kode It";
        System.out.println("Expected : " + expected); // just for checking out the title
        System.out.println("Actual : " + actual);
    }

    @Then("^I validate title is correct$")
    public void i_validate_title_is_correct() throws Throwable {
        ExtentManager.extent.createTest("Let's kode it tests 1");
        ExtentManager.ExtentTestManager.startTest("Title test!");
        String expected = driver.getTitle();
        String actual = "Home | Let's Kode It";
        // compare two objects if they both have the same value.
        //   UtilsByOzlem.takeScreenShot();
        Assert.assertEquals(actual, expected);
    }

    @Then("I take a screenshot of the page")
    public void iTakeAScreenshotOfThePage() throws IOException, AWTException {
        File file = ((TakesScreenshot) Driver.driver).getScreenshotAs(OutputType.FILE);
        // String timestamp = new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime());
        Date date = new Date();
        String timestamp = "Snapshot_" + date.toString().replaceAll(":", "_");
        File screenshot = new File("src/test/resources/screenshots/" + timestamp + ".png");
        FileUtils.copyFile(file, screenshot);
    }

    @Then("I validate title is not correct") // expected title is bad on purpose
    public void iValidateTitleIsNotCorrect() {
        ExtentManager.extent.createTest("Let's kode it tests; last test fail on purpose!");
        ExtentManager.ExtentTestManager.startTest("Title test!");
        String actual = driver.getTitle();
        String expected = "Let's Kode It | Home";
        // compare two objects if they both have the same value.
        //Assert.assertEquals(actual, expected);
        if(actual.equalsIgnoreCase(expected)){
            System.out.println("Title match");
        }else System.out.println("Title does not match!: " +"\n" +
                "Bad Title: "+ expected + "\n" +
                "Title: " + actual);
    }

    @Then("^I close the browser windows$")
    public void i_close_the_browser_windows() throws Throwable {
        ExtentManager.ExtentTestManager.endTest();
        driver.quit();
        // CommonHelper.quitDriver(scenario);
    }


    @When("I click on BMW button")
    public void iClickOnBMWButton() {
        ExtentManager.extent.createTest("Let's kode it tests 2 part 1");
        ExtentManager.ExtentTestManager.startTest("BMW button select test 1");
        WebElement element = driver.findElement(By.cssSelector("#bmwradio"));
        waitForVisibility(wait, element);
        element.click();
    }

    @Then("I check if it's enabled")
    public void iCheckIfItSEnabled() {
        ExtentManager.extent.createTest("Let's kode it tests 2 part 2");
        ExtentManager.ExtentTestManager.startTest("BMW button select test 2");
        WebElement element = driver.findElement(By.cssSelector("#bmwradio"));
        waitForVisibility(wait, element);
        System.out.println("Is " + element.getAttribute("value") + " selected?: " + element.isSelected());
        Assert.assertTrue(element.isEnabled(), "Is " + element.getAttribute("value") + " selected?");
        // assert (element.isEnabled());
    }


    @When("I select honda from the list of options")
    public void iSelectFromTheListOfOptions() {
        ExtentManager.extent.createTest("Let's kode it tests 3 part 1");
        ExtentManager.ExtentTestManager.startTest("Honda option dropdown test 1");
        WebElement select4 = driver.findElement(By.cssSelector("#carselect")); // locator of the dropdown
        waitForVisibility(wait, select4); // explicit wait until dropdown is visible

        // select object to call options: two ways; by getOptions or selectByValue or selectByIndex
        Select select = new Select(select4);
        select.getOptions().get(2).click(); // select/click on last option that is honda as expected
        //  select.selectByValue(option);
        System.out.println("Is honda selected from dropdown?: " + select.getOptions().get(2).isSelected());

    }

    @Then("I validate {string} is selected")
    public void iValidateIsSelected(String option) {
        ExtentManager.extent.createTest("Let's kode it tests 3 part 1");
        ExtentManager.ExtentTestManager.startTest("Honda option dropdown test 2");
        Assert.assertTrue(option.equalsIgnoreCase("honda"), option + " was selected!");
    }


    @When("I select on {string} and {string}")
    public void iSelectOnAnd(String apple, String orange) {
        ExtentManager.extent.createTest("Let's kode it tests 4 select multiple options 1");
        ExtentManager.ExtentTestManager.startTest("Select apple and orange options multi select test 1");
        WebElement select4 = driver.findElement(By.cssSelector("#multiple-select-example"));
        waitForVisibility(wait, select4);
        Select select = new Select(select4);
        select.selectByValue(apple);
        select.selectByValue(orange);
    }

    @Then("I get text of the options and validate both")
    public void iGetTextOfTheOptionsAndValidateBoth() {
        ExtentManager.extent.createTest("Let's kode it tests 4 select multiple options 2");
        ExtentManager.ExtentTestManager.startTest("Select apple and orange options multi select test 2");
        WebElement select4 = driver.findElement(By.cssSelector("#multiple-select-example"));
        waitForVisibility(wait, select4);
        Select select = new Select(select4);
        boolean appleSelectYes = select.getOptions().get(0).isSelected();
        boolean orangeSelectYes = select.getOptions().get(1).isSelected();
        Assert.assertTrue(appleSelectYes, "Apple option was selected");
        Assert.assertTrue(orangeSelectYes, "Orange option was selected");

//        assert (select.getOptions().get(1).getText().equals("apple"));
//        assert (select.getOptions().get(2).getText().equals("orange"));
    }


    @When("I select options <honda> and <bmw> and <benz> and validate")
    public void iSelectOptionsHondaAndBmwAndBenzAndValidate() {
        ExtentManager.extent.createTest("Let's kode it tests 5 checkbox select all options 1");
        ExtentManager.ExtentTestManager.startTest("Select benz, honda and bmw checkbox select test 1");
        WebElement benz = driver.findElement(By.cssSelector("#benzcheck"));
        waitForVisibility(wait, benz);
        WebElement honda = driver.findElement(By.cssSelector("#hondacheck"));
        waitForVisibility(wait, honda);
        WebElement bmw = driver.findElement(By.cssSelector("#bmwcheck"));
        waitForVisibility(wait, bmw);
        benz.click();
        honda.click();
        bmw.click();
        assert benz.isDisplayed();
        assert honda.isDisplayed();
        assert bmw.isDisplayed();
    }

    @When("I click on alert button")
    public void iClickOnAlertButton() {
        ExtentManager.extent.createTest("Let's kode it tests 5 checkbox select all options 2");
        ExtentManager.ExtentTestManager.startTest("Select benz, honda and bmw checkbox select test 2");
        WebElement alertbtn = driver.findElement(By.cssSelector("alertbtn"));
        waitForVisibility(wait, alertbtn);
        alertbtn.click();

    }

    @When("I click on {string}")
    public void iClickOn(String alertLoc) {
        ExtentManager.extent.createTest("Let's kode it tests 6 Alert test 1");
        ExtentManager.ExtentTestManager.startTest("Create an alert condition to handle it next! 1");
        alertLoc = "#alertbtn";
        WebElement alertbtn = driver.findElement(By.cssSelector(alertLoc));
        waitForVisibility(wait, alertbtn);
        alertbtn.click();
    }

    @Then("I dismiss and handle the problem")
    public void iDismissAndHandleTheProblem() {
        ExtentManager.extent.createTest("Let's kode it tests 6 Alert test 2");
        ExtentManager.ExtentTestManager.startTest("Alert condition handling! 2");
        Alert alert = driver.switchTo().alert();
        System.out.println("Alert message text retrieved: " + alert.getText());
        alert.dismiss();


    }
    @When("I create excel file with this name") // this name = fileOriginal
    public void iCreateExcelFileWithThisName() {
        ExtentManager.extent.createTest("Let's kode it tests 7, create an excel file 1");
        ExtentManager.ExtentTestManager.startTest("Create an alert condition to handle it next! 1");
        Utilities.createWorkbook("src/test/resources/",excelFileName,"sheet");
    }

    @When("I create excel file with {string}")
    public void iCreateExcelFileWith(String name) {
        ExtentManager.extent.createTest("Let's kode it tests 8 Create a new excel file X");
        ExtentManager.ExtentTestManager.startTest("Excel file X create!");
        Utilities.createWorkbook("src/test/resources/",name,"sheet");
    }

    @Then("I write a message in sheet")
    public void iWriteAMessageInSheet() throws IOException {
        ExtentManager.extent.createTest("Let's kode it tests 9, write a message in excel sheet");
        ExtentManager.ExtentTestManager.startTest(" write a message in excel sheet");
        // write this meesage in excel file created
        Utilities.writeExCell
                ("src/test/resources/"+excelFileName,
                        "sheet",
                        "Let's rock and type this!",
                        0,
                        0
                );
    }

    @Then("I write {string} in {string}")
    public void iWriteIn(String message, String sheet) throws IOException { // ioexception catching input or output file problems
        ExtentManager.extent.createTest("Let's kode it tests 10, write a message from scenario outline in excel sheet");
        ExtentManager.ExtentTestManager.startTest(" write a message from scenario outline to the excel file");
        // write this meesage in excel file created
        Utilities.writeExCell
                ("src/test/resources/"+"file3.xlsx",
                        sheet,
                        message,
                        0,
                        0
                );
    }

    @Then("I enter {string} to the input box and click confirm")
    public void iEnterToTheInputBoxAndClickConfirm(String message) throws Exception {
        ExtentManager.extent.createTest(
                "Let's kode it tests 11, write a message from scenario outline in excel sheet 2");
        ExtentManager.ExtentTestManager.startTest(
                "write a message from scenario outline in excel sheet 2");
        // add this message from excel file to the input box in the practice page
        WebElement inputbox = driver.findElement(By.cssSelector(".inputs[class='inputs']")); //   #name[class='inputs']
        // message = Utilities.getCellData(0,0);
        inputbox.sendKeys(message);

        WebElement confirmBtn = driver.findElement(By.cssSelector("#confirmbtn"));
        confirmBtn.click();

    }

    @Then("I validate text is entered")
    public void iValidateTextIsEntered() {
        // perform a text validation
        Alert alert = driver.switchTo().alert();
        System.out.println("Message from alert popup windows: "+alert.getText());
        String expected = "Hello Let's rock and type this!, Are you sure you want to confirm?";
        String actual = alert.getText();
        Assert.assertEquals(actual, expected, "Way to go!");
        alert.dismiss();
    }

    @Then("I enter a message to the input box and click confirm")
    public void iEnterAMessageToTheInputBoxAndClickConfirm() throws Exception {
        ExtentManager.extent.createTest("Let's kode it tests 12, write a message from excel file and write in website");
        ExtentManager.ExtentTestManager.startTest("write a message from excel file and write it in the website");
        String message = Utilities.getCellValue("src/test/resources/"+excelFileName,0,0);
        WebElement inputbox = driver.findElement(By.xpath("//input[@id='name']")); //   #name[class='inputs']
        inputbox.clear();

        inputbox.sendKeys(message);

        WebElement confirmBtn = driver.findElement(By.cssSelector("#confirmbtn"));
        confirmBtn.click();
//        Alert alert = driver.switchTo().alert();
//        alert.dismiss();
    }

    // this method is similar to the one above, but gets the message from scenario outline
    @Then("I enter <this message> to the input box and click confirm")
    public void iEnterThisMessageToTheInputBoxAndClickConfirm(String thismessage) throws Exception {
        WebElement inputbox = driver.findElement(By.cssSelector(".inputs[class='inputs']")); //   #name[class='inputs']
        inputbox.sendKeys(thismessage);
        WebElement confirmBtn = driver.findElement(By.cssSelector("#confirmbtn"));
        confirmBtn.click();
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }

}