package Tests;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Components.CartModal;
import Components.Header;
import Components.ProductCard;
import Pages.CartPage;
import Pages.HomePage;
import Pages.LoginSignUpPage;
import Pages.RegisterPage;
import data.LoadProperties;

public class Register_HappyScenario extends BaseTest{
	HomePage homePage = new HomePage(driver);
	LoginSignUpPage loginSignUpPage = new LoginSignUpPage(driver);
	RegisterPage registerPage = new RegisterPage(driver);
	Header headerSection = new Header(driver);
	CartPage cartPage = new CartPage(driver);
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	String name = LoadProperties.userRegisterData.getProperty("name");
	String email = LoadProperties.userRegisterData.getProperty("email");
	String password = LoadProperties.userRegisterData.getProperty("password");
	String day = LoadProperties.userRegisterData.getProperty("day");
	String month = LoadProperties.userRegisterData.getProperty("month");
	String year = LoadProperties.userRegisterData.getProperty("year");
	String firstname = LoadProperties.userRegisterData.getProperty("firstname");
	String lastname = LoadProperties.userRegisterData.getProperty("lastname");
	String company = LoadProperties.userRegisterData.getProperty("company");
	String address = LoadProperties.userRegisterData.getProperty("address");
	String country = LoadProperties.userRegisterData.getProperty("country");
	String state = LoadProperties.userRegisterData.getProperty("state");
	String city = LoadProperties.userRegisterData.getProperty("city");
	String zipCode = LoadProperties.userRegisterData.getProperty("zipCode");
	String mobileNumber = LoadProperties.userRegisterData.getProperty("mobileNumber");
	
	 private void assertLinkIsActive(WebElement link) {
	        Assert.assertEquals("rgba(255, 165, 0, 1)", link.getCssValue("color"));
	 }
	
	 @Test (priority = 1)
	 public void tetRegister_NewUserName_MandatoryAndOptional() throws InterruptedException {
		 assertLinkIsActive(headerSection.homeLink); 
		 headerSection.navigateTo(headerSection.signupLoginLink);
		 Assert.assertTrue("New User Signup!".equalsIgnoreCase(loginSignUpPage.getUserRegisterMessage().getText()));
		 
		 loginSignUpPage.userCanRegister(name,email);
		 Assert.assertTrue("ENTER ACCOUNT INFORMATION".equalsIgnoreCase(registerPage.enterAccountMessage.getText()));
		  
		 registerPage.enterAccountInformation(password,Integer.parseInt(day),month,year,firstname,lastname,company,address,country,state,city,zipCode,mobileNumber);
		 Assert.assertEquals("Account Created!".toUpperCase(), registerPage.successMesssage.getText());
		  
		 registerPage.continueAccount();
			assertTrue((firstname + " " + lastname).equalsIgnoreCase(headerSection.loggedInUser.getText()));
		  
		 registerPage.deleteAccount();
		 Assert.assertTrue(registerPage.deleteMessage.getText().equalsIgnoreCase("Account Deleted!"));
		  
		 registerPage.continueAccount();
		 assertLinkIsActive(headerSection.homeLink);   
	 }
	 
	 @Test
	 (priority = 2)
	 public void register_DuringCheckout() throws InterruptedException {
		 homePage.scrollToElement(homePage.featuresSection);
		 wait.until(ExpectedConditions.visibilityOf(homePage.featuresItemsTitle));
		 Assert.assertEquals("FEATURES ITEMS", homePage.featuresItemsTitle.getText());
		 
		 ProductCard productCard = homePage.getOneFeaturesProduct();
		 Thread.sleep(1000);
		 productCard.addProductToCart();
		 
		 wait.until(ExpectedConditions.visibilityOf(cartPage.processedToCheckoutBtn));
		 cartPage.processToCheckout();
		 
		 CartModal cartModal = new CartModal(driver, cartPage.getVisibleCartModal());
		 Assert.assertEquals("Register / Login account to proceed on checkout.", cartModal.getMessage());
		 
		 cartModal.navigateToLoginSignupPage();
		 Assert.assertTrue("New User Signup!".equalsIgnoreCase(loginSignUpPage.getUserRegisterMessage().getText()));
	 }
}
