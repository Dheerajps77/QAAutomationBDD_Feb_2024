package stepdefinitions;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.PageObjectManager;
import utils.CommonUtils;
import utils.DriverFactory;
import utils.TestSetUp;

public class LoginSteps {

    private WebDriver driver;
    private PageObjectManager pageObjectManager;
    private LoginPage loginPage;
    private AccountPage accountPage;
    private CommonUtils commonUtils;

    public LoginSteps() {
        // Initialize Driver and PageObjectManager here
        this.driver = DriverFactory.getDriver();
        this.pageObjectManager = new PageObjectManager(driver);
        this.loginPage = pageObjectManager.getLoginPage();
        this.accountPage = pageObjectManager.getAccountPage();
        this.commonUtils = new CommonUtils();
    }

    @Given("User navigates to login page")
    public void user_navigates_to_login_page() {
        HomePage homePage = pageObjectManager.getHomePage();
        homePage.clickOnMyAccount();
        this.loginPage = homePage.selectLoginOption();
    }

    @When("^User enters valid email address (.+) into email field$")
    public void user_enters_valid_email_address_into_email_field(String emailText) {
        loginPage.enterEmailAddress(emailText);
    }

    @And("^User enters valid password (.+) into password field$")
    public void user_enters_valid_password_into_password_field(String passwordText) {
        loginPage.enterPassword(passwordText);
    }

    @And("User clicks on Login button")
    public void user_clicks_on_login_button() {
        this.accountPage = loginPage.clickOnLoginButton();
    }

    @Then("User should get successfully logged in")
    public void user_should_get_successfully_logged_in() {
        Assert.assertTrue(accountPage.displayStatusOfEditYourAccountInformationOption());
    }

    @When("User enters invalid email address into email field")
    public void user_enters_invalid_email_address_into_email_field() {
        loginPage.enterEmailAddress(commonUtils.getEmailWithTimeStamp());
    }

    @When("User enters invalid password {string} into password field")
    public void user_enters_invalid_password_into_password_field(String invalidPasswordText) {
        loginPage.enterPassword(invalidPasswordText);
    }

    @Then("User should get a proper warning message about credentials mismatch")
    public void user_should_get_a_proper_warning_message_about_credentials_mismatch() {
        Assert.assertTrue(loginPage.getWarningMessageText().contains("Warning: No match for E-Mail Address and/or Password."));
    }

    @When("User does not enter email address into email field")
    public void user_does_not_enter_email_address_into_email_field() {
        loginPage.enterEmailAddress("");
    }

    @When("User does not enter password into password field")
    public void user_does_not_enter_password_into_password_field() {
        loginPage.enterPassword("");
    }
}
