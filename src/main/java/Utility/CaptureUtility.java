package Utility;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Reporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureUtility {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

    public static void captureScreenshot(WebDriver driver, String screenName) {
        try {
            Reporter.log("Driver for Screenshot: " + driver);
            // Create an object reference of TakesScreenshot with the current driver
            TakesScreenshot ts = (TakesScreenshot) driver;
            // Call the getScreenshotAs function to convert the image to FILE
            File source = ts.getScreenshotAs(OutputType.FILE);
            // Check folder if not exist then create folder
            File theDir = new File("./Screenshots/");
            if (!theDir.exists()){
                boolean wasSuccessful = theDir.mkdirs();
            }
            FileHandler.copy(source, new File("./Screenshots/" + screenName + "_" + dateFormat.format(new Date()) + ".png"));
            System.out.println("Screenshot taken: " + screenName);
            Reporter.log("Screenshot taken current URL: " + driver.getCurrentUrl(), true);
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }

    }
}
