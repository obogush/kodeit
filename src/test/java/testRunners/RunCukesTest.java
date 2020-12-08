package testRunners;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Test;

@Test
@CucumberOptions(
        features = "classpath:features/test.feature",
        glue = {"stepDefs"},
        plugin = {"pretty", "html:target/reporthtml/HtmlReports.html", "json:target/mycucumber.json"}, strict = true
        // plugin = {"pretty", "json:target/cucumber.json"},
        , tags = {"@All"}
)
public class RunCukesTest extends AbstractTestNGCucumberTests {
}
// plugin="com.example.MyTestListener"     "src/main/java/managers/TestListeners.java"
//     "html:target/HtmlReports.html"

/*
to run multiple tags
mvn clean verify -Dcucumber.filter.tags="@bmw or @honda"

quit method is disables in individual tests and only added to the last test.
We can use the following command to run all tests
mvn clean verify -Dcucumber.filter.tags="@All"
 */