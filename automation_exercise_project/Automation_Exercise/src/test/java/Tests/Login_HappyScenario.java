package Tests;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Components.Header;
import Pages.CartPage;
import Pages.HomePage;
import Pages.LoginSignUpPage;
import Pages.ProductDetailsPage;
import Pages.ProductsPage;
import data.LoadProperties;

public class Login_HappyScenario extends BaseTest{
	HomePage homePage = new HomePage(driver);
	LoginSignUpPage loginSignUpPage = new LoginSignUpPage(driver);
	Header headerSection = new Header(driver);
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	String email = LoadProperties.userLoginData.getProperty("email"); 
	String password = LoadProperties.userLoginData.getProperty("password");
	String firstName = LoadProperties.userLoginData.getProperty("firstname");
	String lastName = LoadProperties.userLoginData.getProperty("lastname");
	
    private void assertLinkIsActive(WebElement link) {
        Assert.assertEquals("rgba(255, 165, 0, 1)", link.getCssValue("color"));
    }
	
	
	@Test(priority = 1)
	public void currectUsernameAndPassword() throws InterruptedException
	{
		headerSection.navigateTo(headerSection.signupLoginLink);
		assertLinkIsActive(headerSection.signupLoginLink);
		
		Assert.assertEquals("Login to your account", loginSignUpPage.loginMessag.getText());
		
		loginSignUpPage.userCanLogin(email, password);
		assertTrue((firstName + " " + lastName).equalsIgnoreCase(headerSection.loggedInUser.getText()));

		Assert.assertEquals("Logout", headerSection.logoutBtn.getText());
		
		headerSection.userLogout();
		Assert.assertEquals("Login to your account", loginSignUpPage.loginMessag.getText());
	}
}
