package script.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import script.pages.OpenGoogleFactory;
import Utility.*;

import java.util.List;

public class OpenGoogleTest extends BaseUtility{
    private WebDriver driver;
    private List<WebDriver> allDrivers;
    private OpenGoogleFactory openGoogleFactory;
    private JSONUtility jsonUtility;

    @BeforeClass
    public void setUp() {
        driver = getDriver();
        jsonUtility = new JSONUtility();
        System.out.println(getAllURLs());
    }

    @DataProvider(name = "testDriver")
    public Object[][] createDrivers(){
        Object[][] drivers = new Object[URLitems.size()][1];
        for (int i=0; i < URLitems.size();  i++){
            drivers[i][0] = getDriverPerURL(URLitems.get(i));
        }

        return drivers;
    }


    @Test(priority = 1, dataProvider = "testDriver")
    public void openGoogle(WebDriver driver_) throws Exception {
        openGoogleFactory = new OpenGoogleFactory(driver_);
        Thread.sleep(2000);
        driver_.quit();
    }

    @AfterMethod
    public void checkAfterMethod(ITestResult result) throws InterruptedException {
    }
}