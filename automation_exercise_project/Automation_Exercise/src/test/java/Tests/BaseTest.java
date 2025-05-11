package Tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class BaseTest {
	String baseUrl = "https://www.automationexercise.com/";
	WebDriver driver = new ChromeDriver();
	
	 @BeforeTest
	 public void openBrowser() {
		 driver.manage().window().maximize();
        driver.navigate().to(baseUrl);
	 }
	
//	@BeforeMethod
//	public void navigateToHome() {
//	    driver.get(baseUrl); 
//	}
	
	@AfterTest
	public void afterTest() {
		driver.quit();
	}
}
