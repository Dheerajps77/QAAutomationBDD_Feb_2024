package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import testBase.TestBase;
import utils.CommonUtils;

public class AccountPage extends TestBase {

    @FindBy(linkText = "Edit your account information")
    private WebElement editYourAccountInformationOption;

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public boolean displayStatusOfEditYourAccountInformationOption() {
        return elementUtils.displayStatusOfElement(editYourAccountInformationOption, CommonUtils.EXPLICIT_WAIT_BASIC_TIME);
    }
}
