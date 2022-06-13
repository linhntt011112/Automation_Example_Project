package Utility.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static final ExtentReports extentReports = new ExtentReports();

    public synchronized static ExtentReports getExtentReports(String timeStamp) {
        ExtentSparkReporter reporterMain = new ExtentSparkReporter("./Reports/Report" + ".html");
        ExtentSparkReporter reporterTimestamp = new ExtentSparkReporter("./Reports/Report" + timeStamp + ".html");
        reporterMain.config().setReportName("Report");
        reporterTimestamp.config().setReportName("Report Timestamp");
        extentReports.attachReporter(reporterMain, reporterTimestamp);
        extentReports.setSystemInfo("Framework Name", "Selenium Java Framework");
        extentReports.setSystemInfo("Author", "Linh");
        return extentReports;
    }
}
