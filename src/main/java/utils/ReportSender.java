package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReportSender {

    private static final String BASE_PATH = System.getProperty("user.dir") + "/ExtentReports/";
    private static final String LOG_PATH = System.getProperty("user.dir") + "/Logs/";

    public void sendTestReportEmail() {
        try {
            String baseReportDir = BASE_PATH;
            String baseFolderNamePattern = "SparkReport_";

            File latestReportDir = findLatestReportDirectory(baseReportDir, baseFolderNamePattern);
            if (latestReportDir == null) {
            	LogUtils.error("No report directory found with pattern: " + baseFolderNamePattern);
                return;
            }

            // Retry logic to check if files are present
            int retryCount = 0;
            File extentReportFile = null;
            File pdfReportFile = null;
            File cucumberLogFile = null;
            while (retryCount < 5) {
                extentReportFile = new File(latestReportDir.getAbsolutePath() + "/HtmlReport/ExtentHtml.html");
                pdfReportFile = new File(latestReportDir.getAbsolutePath() + "/PdfReport/ExtentPdf.pdf");
                cucumberLogFile = new File(LOG_PATH + "cucumber.log");

                if (extentReportFile.exists() && pdfReportFile.exists() && cucumberLogFile.exists()) {
                    break;
                }

                retryCount++;
                TimeUnit.SECONDS.sleep(5); // Wait before retrying
            }

            List<String> attachmentsList = new ArrayList<>();
            if (extentReportFile.exists()) {
                attachmentsList.add(extentReportFile.getAbsolutePath());
            } else {
            	LogUtils.warn("Extent HTML report not found at: " + extentReportFile.getAbsolutePath());
            }

            if (pdfReportFile.exists()) {
                attachmentsList.add(pdfReportFile.getAbsolutePath());
            } else {
            	LogUtils.warn("Extent PDF report not found at: " + pdfReportFile.getAbsolutePath());
            }

            if (cucumberLogFile.exists()) {
                attachmentsList.add(cucumberLogFile.getAbsolutePath());
            } else {
            	LogUtils.warn("Cucumber log file not found at: " + cucumberLogFile.getAbsolutePath());
            }

            File screenshotDir = new File(latestReportDir.getAbsolutePath() + "/Screenshots");
            String[] screenshotFiles = screenshotDir.list((dir, name) -> name.endsWith(".png"));

            if (screenshotFiles != null && screenshotFiles.length > 0) {
                for (String screenshot : screenshotFiles) {
                    attachmentsList.add(screenshotDir.getAbsolutePath() + "/" + screenshot);
                }
            } else {
            	LogUtils.warn("No screenshots found in the directory: " + screenshotDir.getAbsolutePath());
            }

            String[] attachments = attachmentsList.toArray(new String[0]);

            // Send the email
            EmailUtil.sendEmailWithAttachments("Test Automation Execution Report",
                    "Please find attached the Extent Report, PDF report, and cucumber.log along with screenshots from the latest test execution.",
                    "recipient@example.com", attachments);

        } catch (Exception e) {
            LogUtils.error("Failed to send test report email", e);
        }
    }

    private File findLatestReportDirectory(String baseDir, String pattern) {
        File dir = new File(baseDir);
        File[] matchingDirs = dir.listFiles((file, name) -> name.startsWith(pattern) && file.isDirectory());

        if (matchingDirs == null || matchingDirs.length == 0) {
            return null;
        }

        Arrays.sort(matchingDirs, Comparator.comparingLong(File::lastModified).reversed());
        return matchingDirs[0];
    }
}
