package Utility.listeners;

import Utility.BaseUtility;
import Utility.extentreports.ExtentTestManager;
import Utility.logs.LogUtility;
import com.aventstack.extentreports.Status;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static Utility.extentreports.ExtentManager.getExtentReports;

public class ReportListenerUtility extends BaseUtility implements ITestListener {
    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());

    public String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName()
                : result.getMethod().getConstructorOrMethod().getName();
    }

    public String getTestDescription(ITestResult result) {
        return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
    }

    private void takeScreenshot(WebDriver driver, String message) {
        Allure.addAttachment(message, new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES))); }

    //Text attachments for Allure
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    //HTML attachments for Allure
    @Attachment(value = "{0}", type = "text/html")
    public static String attachHtml(String html) {
        return html;
    }

    //Text attachments for Allure
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        WebDriver driver = BaseUtility.getDriver();
        LogUtility.info("Start testing " + iTestContext.getName());
        iTestContext.setAttribute("WebDriver", driver);
        // Call function startRecord video
//        try {
//            RecordVideoUtility.startRecord(iTestContext.getName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        LogUtility.info("End testing " + iTestContext.getName());
        // Finish and execute Extents Report
        getExtentReports(timeStamp).flush();
        // Call function stopRecord video
//        try {
//            RecordVideoUtility.stopRecord();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        LogUtility.info(getTestName(iTestResult) + " test is starting...");
        ExtentTestManager.saveToReport(iTestResult.getName(), iTestResult.getTestName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        LogUtility.info(getTestName(iTestResult) + " test is passed.");
        //ExtentReports log operation for passed tests.
        ExtentTestManager.logMessage(Status.PASS, getTestDescription(iTestResult));
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        Object testClass = iTestResult.getInstance();
        WebDriver driver = ((BaseUtility)testClass).getDriver();
        LogUtility.error(getTestName(iTestResult) + " test is failed.");

        ExtentTestManager.addScreenShot(Status.FAIL, getTestName(iTestResult));
        ExtentTestManager.logMessage(Status.FAIL, getTestDescription(iTestResult));

        LogUtility.error("Screenshot captured for test case: " + getTestName(iTestResult));
//        saveScreenshotPNG(driver);
        if (driver instanceof WebDriver) {
            takeScreenshot(driver, "Screenshot of failed step");
        }
        //Save a log on Allure report.
        saveTextLog(getTestName(iTestResult) + " failed and screenshot taken!");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        LogUtility.warn(getTestName(iTestResult) + " test is skipped.");
        ExtentTestManager.logMessage(Status.SKIP, getTestName(iTestResult) + " test is skipped.");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        LogUtility.error("Test failed but it is in defined success ratio " + getTestName(iTestResult));
        ExtentTestManager.logMessage("Test failed but it is in defined success ratio " + getTestName(iTestResult));
    }
}
