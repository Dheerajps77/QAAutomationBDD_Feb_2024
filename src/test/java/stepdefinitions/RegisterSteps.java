package stepdefinitions;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AccountSuccessPage;
import pages.HomePage;
import pages.PageObjectManager;
import pages.RegisterPage;
import utils.CommonUtils;
import utils.DriverFactory;
import utils.LogUtils;

public class RegisterSteps {

    private WebDriver driver;
    public PageObjectManager pageObjectManager;
    private RegisterPage registerPage;    
    private AccountSuccessPage accountSuccessPage;
    private CommonUtils commonUtils;
    public LogUtils logUtils;

    public RegisterSteps() {
        this.driver = DriverFactory.getDriver();
        this.pageObjectManager = new PageObjectManager(driver);
        registerPage = pageObjectManager.getRegisterPage();
        accountSuccessPage = pageObjectManager.getAccountSuccessPage();
        this.commonUtils = new CommonUtils();
    }

    @Given("User navigates to Register Account page")
    public void user_navigates_to_register_account_page() {
        try {
            driver = DriverFactory.getDriver();
            HomePage homePage = new HomePage(driver);
            homePage.clickOnMyAccount();
            registerPage = homePage.selectRegisterOption();
            LogUtils.info("User navigates to Register Account page");
        } catch (Exception e) {
            LogUtils.error("Error navigating to Register Account page", e);
            throw e;
        }
    }

    @When("User enters the details into below fields")
    public void user_enters_the_details_into_below_fields(DataTable dataTable) {
        try {
            Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
            registerPage.enterFirstName(dataMap.get("firstName"));
            registerPage.enterLastName(dataMap.get("lastName"));
            registerPage.enterEmailAddress(commonUtils.getEmailWithTimeStamp());
            registerPage.enterTelephoneNumber(dataMap.get("telephone"));
            registerPage.enterPassword(dataMap.get("password"));
            registerPage.enterConfirmPassword(dataMap.get("password"));
            LogUtils.info("User enters the details into below fields");
        } catch (Exception e) {
            LogUtils.error("Error entering details into fields", e);
            throw e;
        }
    }

    @When("User enters the details into below fields with duplicate email")
    public void user_enters_the_details_into_below_fields_with_duplicate_email(DataTable dataTable) {
        try {
            Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
            registerPage.enterFirstName(dataMap.get("firstName"));
            registerPage.enterLastName(dataMap.get("lastName"));
            registerPage.enterEmailAddress(dataMap.get("email"));
            registerPage.enterTelephoneNumber(dataMap.get("telephone"));
            registerPage.enterPassword(dataMap.get("password"));
            registerPage.enterConfirmPassword(dataMap.get("password"));
            LogUtils.info("User enters the details into below fields with duplicate email");
        } catch (Exception e) {
            LogUtils.error("Error entering details with duplicate email", e);
            throw e;
        }
    }

    @When("User selects Privacy Policy")
    public void user_selects_privacy_policy() {
        try {
            registerPage.selectPrivacyPolicy();
            LogUtils.info("User selects Privacy Policy");
        } catch (Exception e) {
            LogUtils.error("Error selecting Privacy Policy", e);
            throw e;
        }
    }

    @When("User clicks on Continue button")
    public void user_clicks_on_continue_button() {
        try {
            accountSuccessPage = registerPage.clickOnContinueButton();
            LogUtils.info("User clicks on Continue button");
        } catch (Exception e) {
            LogUtils.error("Error clicking on Continue button", e);
            throw e;
        }
    }

    @Then("User account should get created successfully")
    public void user_account_should_get_created_successfully() {
        try {
            Assert.assertEquals("Your Account Has Been Created!", accountSuccessPage.getPageHeading());
            LogUtils.info("User account should get created successfully");
        } catch (AssertionError e) {
            LogUtils.error("Account creation failed", e);
            throw e;
        }
    }

    @When("User selects Yes for Newsletter")
    public void user_selects_yes_for_newsletter() {
        try {
            registerPage.selectYesNewsletterOption();
            LogUtils.info("User selects Yes for Newsletter");
        } catch (Exception e) {
            LogUtils.error("Error selecting Yes for Newsletter", e);
            throw e;
        }
    }

    @Then("User should get a proper warning about duplicate email")
    public void user_should_get_a_proper_warning_about_duplicate_email() {
        try {
            Assert.assertTrue(registerPage.getWarningMessageText().contains("Warning: E-Mail Address is already registered!"));
            LogUtils.info("User should get a proper warning about duplicate email");
        } catch (AssertionError e) {
            LogUtils.error("No proper warning for duplicate email", e);
            throw e;
        }
    }

    @When("User dont enter any details into fields")
    public void user_dont_enter_any_details_into_fields() {
        try {
            registerPage.enterFirstName("");
            registerPage.enterLastName("");
            registerPage.enterEmailAddress("");
            registerPage.enterTelephoneNumber("");
            registerPage.enterPassword("");
            registerPage.enterConfirmPassword("");
            LogUtils.info("User don’t enter any details into fields");
        } catch (Exception e) {
            LogUtils.error("Error entering empty details", e);
            throw e;
        }
    }

    @Then("User should get proper warning messages for every mandatory field")
    public void user_should_get_proper_warning_messages_for_every_mandatory_field() {
        try {
            Assert.assertTrue(registerPage.getWarningMessageText().contains("Warning: You must agree to the Privacy Policy!"));
            Assert.assertEquals("First Name must be between 1 and 32 characters!", registerPage.getFirstNameWarning());
            Assert.assertEquals("Last Name must be between 1 and 32 characters!", registerPage.getLastNameWarning());
            Assert.assertEquals("E-Mail Address does not appear to be valid!", registerPage.getEmailWarning());
            Assert.assertEquals("Telephone must be between 3 and 32 characters!", registerPage.getTelephoneWarning());
            Assert.assertEquals("Password must be between 4 and 20 characters!", registerPage.getPasswordWarning());
            LogUtils.info("User should get proper warning messages for every mandatory field");
        } catch (AssertionError e) {
            LogUtils.error("Warnings for mandatory fields missing or incorrect", e);
            throw e;
        }
    }
}
