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
//        System.out.println(getAllURLs());
    }

    @Test(priority = 1)
    public void openGoogle() throws Exception {
        openGoogleFactory = new OpenGoogleFactory(driver);
        for (int i = 0; i < getAllURLs().size();  i++) {
            String urlSite = getAllURLs().get(i);
            System.out.println(urlSite);
        }
        Thread.sleep(2000);
    }

    @AfterMethod
    public void checkAfterMethod(ITestResult result) throws InterruptedException {
    }
}