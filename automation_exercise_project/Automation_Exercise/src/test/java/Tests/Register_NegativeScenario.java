package Tests;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Components.Header;
import Pages.CartPage;
import Pages.HomePage;
import Pages.LoginSignUpPage;
import Pages.RegisterPage;
import data.LoadProperties;

public class Register_NegativeScenario extends BaseTest{
	
	HomePage homePage = new HomePage(driver);
	LoginSignUpPage loginSignUpPage = new LoginSignUpPage(driver);
	RegisterPage registerPage = new RegisterPage(driver);
	Header headerSection = new Header(driver);
	
	String name = LoadProperties.userRegisterData.getProperty("name");
	String email = LoadProperties.userLoginData.getProperty("email");
	
	 private void assertLinkIsActive(WebElement link) {
	     Assert.assertEquals("rgba(255, 165, 0, 1)", link.getCssValue("color"));
	 }
	
	@Test
	public void register_WithExistEmail() {
		assertLinkIsActive(headerSection.homeLink); 
		headerSection.navigateTo(headerSection.signupLoginLink);
		Assert.assertTrue("New User Signup!".equalsIgnoreCase(loginSignUpPage.getUserRegisterMessage().getText()));
		 
		loginSignUpPage.userCanRegister(name,email);
		Assert.assertEquals("Email Address already exist!", loginSignUpPage.userExistMessage.getText());
	}
}
