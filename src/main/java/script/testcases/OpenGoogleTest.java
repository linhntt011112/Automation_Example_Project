package script.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import script.pages.OpenGoogleFactory;
import Utility.*;

public class OpenGoogleTest extends BaseUtility{
    private WebDriver driver;
    private OpenGoogleFactory openGoogleFactory;
    private JSONUtility jsonUtility;
    private CaptureUtility captureUtility;

    @BeforeClass
    public void setUp() {
        driver = getDriver();
        jsonUtility = new JSONUtility();
    }

    @Test(priority = 1)
    public void openGoogle() throws Exception {
        openGoogleFactory = new OpenGoogleFactory(driver);
    }

    @AfterMethod
    public void checkAfterMethod(ITestResult result) {
    }
}
