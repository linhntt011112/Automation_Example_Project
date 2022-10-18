package Utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaseUtility {
    private static WebDriver driver;
    public static String browserType_;
    public static String driverPath_;
    public static List<String> URLitems = new ArrayList<>();

    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriver getDriverPerURL(String appURL) {
        WebDriver localDriver;
        switch (browserType_) {
            case "chrome":
                localDriver = initChromeDriver(appURL, driverPath_);
                break;
            case "firefox":
                localDriver = initFirefoxDriver(appURL, driverPath_);
                break;
            default:
                System.out.println("Browser: " + browserType_ + " is invalid, Launching Chrome as browser of choice...");
                localDriver = initChromeDriver(appURL, driverPath_);
        }

        return localDriver;
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
        String os = System.getProperty("os.name");
        System.out.println(os);
        if (os.contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver-106.exe");
            System.out.println("appURL: " + appURL);
            driver = new ChromeDriver();
        }
        else {
            System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver");
//             ChromeOptions options = new ChromeOptions();   //chrome binary location
//             options.addArguments("--headless");
//             options.addArguments("--no-sandbox");
//             options.addArguments("--disable-dev-shm-usage");
            driver = new ChromeDriver();
        }
//        System.out.println("appURL: " + appURL);
        driver.manage().window().maximize();

        driver.navigate().to(appURL);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver;
    }

    public List<String> getAllURLs() {
        return URLitems;
    }

    // Run initializeTestBaseSetup function first when this class is called
    @Parameters({ "browserType", "BaseURL", "driverPath", "TH", "SG", "MY"})
    @BeforeClass
    public void initializeTestBaseSetup(String browserType, String BaseURL, String driverPath, String TH, String SG, String MY) {
        if (TH.isEmpty() == false) URLitems.add(BaseURL + TH);
        if (SG.isEmpty() == false) URLitems.add(BaseURL + SG);
        if (MY.isEmpty() == false) URLitems.add(BaseURL + MY);
        for (int i = 0; i < URLitems.size(); i++) {
            System.out.println(URLitems.get(i));
        }
//        try {
//            // Khởi tạo driver và browser
//            setDriver(browserType, URLitems.get(0), driverPath);
//        } catch (Exception e) {
//            System.out.println("Error..." + e.getStackTrace());
//        }
    }

    @AfterClass
    public void tearDown() throws Exception {
        Thread.sleep(2000);
        driver.quit();
    }
}