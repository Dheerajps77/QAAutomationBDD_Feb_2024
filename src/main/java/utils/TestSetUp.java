package utils;

import org.openqa.selenium.WebDriver;

import pages.PageObjectManager;

public class TestSetUp {
    public WebDriver driver;
    public PageObjectManager pageObjectManager;

    public TestSetUp() {
        this.driver = DriverFactory.getDriver(); // Assuming DriverFactory handles WebDriver instance
        this.pageObjectManager = new PageObjectManager(driver);
    }
}
