package Utility.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static final ExtentReports extentReports = new ExtentReports();

    public synchronized static ExtentReports getExtentReports(String timeStamp) {
        ExtentSparkReporter reporterMain = new ExtentSparkReporter("./Reports/OnitsukaTigerReport" + ".html");
        ExtentSparkReporter reporterTimestamp = new ExtentSparkReporter("./Reports/OnitsukaTigerReport" + timeStamp + ".html");
        reporterMain.config().setReportName("OnitsukaTiger Report");
        reporterTimestamp.config().setReportName("OnitsukaTiger Report Timestamp");
        extentReports.attachReporter(reporterMain, reporterTimestamp);
        extentReports.setSystemInfo("Framework Name", "Selenium Java Framework");
        extentReports.setSystemInfo("Author", "OnitsukaTiger Team");
        return extentReports;
    }
}
