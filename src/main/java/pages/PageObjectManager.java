package pages;

import org.openqa.selenium.WebDriver;

public class PageObjectManager {

    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;
    private AccountPage accountPage;
    private AccountSuccessPage accountSuccessPage;
    private ForgotPasswordPage forgotPasswordPage;
    private RegisterPage registerPage;
    private SearchResultsPage searchResultsPage;

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(driver);
        }
        return loginPage;
    }

    public HomePage getHomePage() {
        if (homePage == null) {
            homePage = new HomePage(driver);
        }
        return homePage;
    }

    public AccountPage getAccountPage() {
        if (accountPage == null) {
            accountPage = new AccountPage(driver);
        }
        return accountPage;
    }

    public AccountSuccessPage getAccountSuccessPage() {
        if (accountSuccessPage == null) {
            accountSuccessPage = new AccountSuccessPage(driver);
        }
        return accountSuccessPage;
    }

    public ForgotPasswordPage getForgotPasswordPage() {
        if (forgotPasswordPage == null) {
            forgotPasswordPage = new ForgotPasswordPage(driver);
        }
        return forgotPasswordPage;
    }

    public RegisterPage getRegisterPage() {
        if (registerPage == null) {
            registerPage = new RegisterPage(driver);
        }
        return registerPage;
    }

    public SearchResultsPage getSearchResultsPage() {
        if (searchResultsPage == null) {
            searchResultsPage = new SearchResultsPage(driver);
        }
        return searchResultsPage;
    }
}
