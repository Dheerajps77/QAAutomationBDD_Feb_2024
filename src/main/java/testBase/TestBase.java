package testBase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import utils.ElementUtils;
import utils.LogUtils;

public abstract class TestBase {
    protected WebDriver driver;
    protected ElementUtils elementUtils;
 
    public TestBase(WebDriver driver) {
        this.driver = driver;
        this.elementUtils = new ElementUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public static void startTest(String testName, String description) {
        LogUtils.info("Test [" + testName + "] - " + description);
    }

    public static void logInfo(String message) {
        LogUtils.info(message);
    }

    public static void logError(String message) {
        LogUtils.error(message);
    }

    public static void endTest(String testName, String description) {
        LogUtils.info("Test [" + testName + "] - " + description);
    }
}
