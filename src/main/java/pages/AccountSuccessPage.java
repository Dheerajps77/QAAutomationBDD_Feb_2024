package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import testBase.TestBase;
import utils.CommonUtils;

public class AccountSuccessPage extends TestBase {

    @FindBy(xpath="//div[@id='content']/h1")
    private WebElement pageHeading;

    public AccountSuccessPage(WebDriver driver) {
        super(driver);
    }

    public String getPageHeading() {
        return elementUtils.getTextFromElement(pageHeading, CommonUtils.EXPLICIT_WAIT_BASIC_TIME);
    }
}
