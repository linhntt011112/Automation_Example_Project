package Utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

public class BaseUtility {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    private void setDriver(String browserType, String appURL, String driverPath) {
        switch (browserType) {
            case "chrome":
                driver = initChromeDriver(appURL, driverPath);
                break;
            case "firefox":
                driver = initFirefoxDriver(appURL, driverPath);
                break;
            default:
                System.out.println("Browser: " + browserType + " is invalid, Launching Chrome as browser of choice...");
                driver = initChromeDriver(appURL, driverPath);
        }
    }

    private static WebDriver initFirefoxDriver(String appURL, String driverPath) {
        System.out.println("Launching Firefox browser...");
        System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver");
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver;
    }

    private static WebDriver initChromeDriver(String appURL, String driverPath) {
        System.out.println("Launching Chrome browser...");
        System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver");
        String a = System.getProperty("webdriver.chrome.driver");
        System.out.println(a);
        options.setBinary('/usr/bin/google-chrome');    //chrome binary location
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        System.out.println("vao1");
        WebDriver driver = new ChromeDriver(options);
        System.out.println("vao2");
        driver.get("https://www.google.com/");
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver;
    }

    // Run initializeTestBaseSetup function first when this class is called
    @Parameters({ "browserType", "appURL", "driverPath" })
    @BeforeClass
    public void initializeTestBaseSetup(String browserType, String appURL, String driverPath) {
        try {
            // Khởi tạo driver và browser
            setDriver(browserType, appURL, driverPath);
        } catch (Exception e) {
            System.out.println("Error..." + e.getStackTrace());
        }
    }

    @AfterClass
    public void tearDown() throws Exception {
        Thread.sleep(2000);
        driver.quit();
    }
}
