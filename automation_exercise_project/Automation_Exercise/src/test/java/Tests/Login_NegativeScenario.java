package Tests;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Components.Header;
import Pages.CartPage;
import Pages.HomePage;
import Pages.LoginSignUpPage;
import Pages.ProductDetailsPage;
import Pages.ProductsPage;
import data.LoadProperties;

public class Login_NegativeScenario extends BaseTest{
	HomePage homePage = new HomePage(driver);
	LoginSignUpPage loginSignUpPage = new LoginSignUpPage(driver);
	CartPage cartPage = new CartPage(driver);
	ProductsPage productsPage = new ProductsPage(driver);
	ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
	Header headerSection = new Header(driver);
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
    private void assertLinkIsActive(WebElement link) {
        Assert.assertEquals("rgba(255, 165, 0, 1)", link.getCssValue("color"));
    }
	
	@DataProvider(name = "userData")
    private Object[][] testQuantityData() {
		Object[][] testData = new Object[][] {
			{"khaledsameh158@gmail.com" , "kh09874"},
			{"khaledsameh1584834@gmail.com" , "kh09874563"},
		};
		
		return testData;
	}
	
	@Test
	(priority = 1 , dataProvider = "userData")
	private void inCurrectUsernameAndPassword(String email, String password) {
		headerSection.navigateTo(headerSection.signupLoginLink);
		assertLinkIsActive(headerSection.signupLoginLink);
		
		Assert.assertEquals("Login to your account", loginSignUpPage.loginMessag.getText());
		
		loginSignUpPage.userCanLogin(email, password);
		assertTrue("Your email or password is incorrect!"
				.equalsIgnoreCase(loginSignUpPage.invalidLoginMessag.getText()));
	}
}
