package factory;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import utils.CommonUtils;

public class DriverFactory {

	static WebDriver driver = null;

	public static WebDriver initializeBrowser(String browserName) {
		
		String path=System.getProperty("user.dir")+"\\src\\test\\resources\\Drivers\\chromedriver.exe";
		
		if (browserName.equals("chrome")) {
			System.setProperty("Webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();

		} else if (browserName.equals("firefox")) {
			System.setProperty("Webdriver.gecko.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();

		} else if (browserName.equals("edge")) {

			driver = new EdgeDriver();

		} else if (browserName.equals("safari")) {

			driver = new SafariDriver();

		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(CommonUtils.PAGE_LOAD_TIME));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonUtils.IMPLICIT_WAIT_TIME));

		return driver;

	}

	public static WebDriver getDriver() {

		return driver;

	}

}
