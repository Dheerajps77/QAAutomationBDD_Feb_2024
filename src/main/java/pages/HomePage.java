package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import testBase.TestBase;
import utils.CommonUtils;
import utils.ElementUtils;

public class HomePage extends TestBase {
	
	public HomePage(WebDriver driver) {
        super(driver); // Call the constructor of the BasePage
    }
	
	@FindBy(xpath="//span[text()='My Account']")
	private WebElement myAccountDropMenu;
	
	@FindBy(linkText="Login")
	private WebElement loginOption;
	
	@FindBy(linkText="Register")
	private WebElement registerOption;
	
	@FindBy(name="search")
	private WebElement searchBoxField;
	
	@FindBy(xpath="//button[contains(@class,'btn-default')]")
	private WebElement searchButton;
	
	public void clickOnMyAccount() {
		
		elementUtils.clickOnElement(myAccountDropMenu,CommonUtils.EXPLICIT_WAIT_BASIC_TIME);
		
	}
	
	public LoginPage selectLoginOption() {
		
		elementUtils.clickOnElement(loginOption,CommonUtils.EXPLICIT_WAIT_BASIC_TIME);
		return new LoginPage(driver);
		
	}
	
	public RegisterPage selectRegisterOption() {
		
		elementUtils.clickOnElement(registerOption,CommonUtils.EXPLICIT_WAIT_BASIC_TIME);
		return new RegisterPage(driver);
		
	}
	
	public void enterProductIntoSearchBox(String productText) {
		
		elementUtils.typeTextIntoElement(searchBoxField,productText,CommonUtils.EXPLICIT_WAIT_BASIC_TIME);
		
	}
	
	public SearchResultsPage clickOnSearchButton() {
		
		elementUtils.clickOnElement(searchButton,CommonUtils.EXPLICIT_WAIT_BASIC_TIME);
		return new SearchResultsPage(driver);
		
	}
	

}
