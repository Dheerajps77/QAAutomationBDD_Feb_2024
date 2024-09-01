package utils;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    private static WebDriver driver = null;
    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    public static WebDriver initializeBrowser(String browserName) {
        logger.info("Initializing browser: {}", browserName);

        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            case "safari":
                // No WebDriverManager support needed for Safari, built-in on macOS
                driver = new SafariDriver();
                break;

            default:
                logger.error("Invalid browser name provided: {}", browserName);
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }

        configureBrowser();
        logger.info("{} browser initialized successfully.", browserName);
        return driver;
    }

    private static void configureBrowser() {
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(CommonUtils.PAGE_LOAD_TIME));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonUtils.IMPLICIT_WAIT_TIME));
        logger.info("Browser configured with default settings: maximize window, delete cookies, and set timeouts.");
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver not initialized. Call initializeBrowser() first.");
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            logger.info("Browser closed and driver instance set to null.");
        }
    }
}
