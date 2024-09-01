package hooks;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.ReportSender;

public class MyHooks {

    private WebDriver driver;
    private Properties prop;
    private static final Logger logger = LoggerFactory.getLogger(MyHooks.class);
    private static final String BASE_PATH = System.getProperty("user.dir") + "/ExtentReports/Screenshots/";

    @Before
    public void setup(Scenario scenario) {
        logger.info("Initializing properties from config file");
        prop = new ConfigReader().initializeProperties();

        String browser = prop.getProperty("browser", "chrome");
        logger.info("Browser selected: {}", browser);

        driver = DriverFactory.initializeBrowser(browser);

        String url = prop.getProperty("url");
        logger.info("Navigating to: {}", url);
        driver.get(url);

        logger.info("Scenario '{}' started.", scenario.getName());
    }

   // @AfterStep
    public void takeScreenshotAfterStep(Scenario scenario) {
        String stepName = scenario.getName().replaceAll(" ", "_") + "_" + System.currentTimeMillis();
        String screenshotPath = BASE_PATH + "Screenshot_" + stepName + ".png";
        logger.info("Capturing screenshot after step: {}", stepName);
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File(screenshotPath));
            byte[] screenshotBytes = FileUtils.readFileToByteArray(screenshot);
            logger.info("Screenshot saved to: {}", screenshotPath); // Debug log
            scenario.attach(screenshotBytes, "image/png", "Screenshot_" + stepName);
        } catch (IOException e) {
            logger.error("Failed to save screenshot", e);
        }
    }

    @BeforeStep
    public void logBeforeStep(Scenario scenario) {
        logger.info("Starting next step of scenario: '{}'", scenario.getName());
    }

    @AfterStep
    public void logAfterStep(Scenario scenario) {
        logger.info("Completed step of scenario: '{}'", scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        String scenarioName = scenario.getName().replaceAll(" ", "_");
        logger.info("Scenario '{}' execution completed. Checking if it failed.", scenarioName);

        if (scenario.isFailed()) {
            logger.error("Scenario '{}' failed. Capturing screenshot.", scenarioName);
            String screenshotPath = BASE_PATH + "Failed_Screenshot_" + scenarioName + "_" + System.currentTimeMillis() + ".png";
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(screenshot, new File(screenshotPath));
                byte[] screenshotBytes = FileUtils.readFileToByteArray(screenshot);
                scenario.attach(screenshotBytes, "image/png", "Failed_Screenshot_" + scenarioName);
            } catch (IOException e) {
                logger.error("Failed to save screenshot", e);
            }
        } else {
            logger.info("Scenario '{}' passed.", scenarioName);
        }

        logger.info("Closing the browser.");
        driver.quit();
    }
    
    @After("@sendReport") // Add a tag to trigger email sending after all scenarios
    public void sendReport() {
        ReportSender reportSender = new ReportSender();
        reportSender.sendTestReportEmail();
    }
}
