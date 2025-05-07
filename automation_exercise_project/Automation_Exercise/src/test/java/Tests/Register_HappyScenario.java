package Tests;

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

public class Register_HappyScenario extends BaseTest{
	HomePage homePage = new HomePage(driver);
	LoginSignUpPage loginSignUpPage = new LoginSignUpPage(driver);
	RegisterPage registerPage = new RegisterPage(driver);
	Header headerSection = new Header(driver);
	CartPage cartPage = new CartPage(driver);
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	 private void assertLinkIsActive(WebElement link) {
	        Assert.assertEquals("rgba(255, 165, 0, 1)", link.getCssValue("color"));
	 }
	
	 @Test (priority = 1)
	 public void tetRegister_NewUserName_MandatoryAndOptional() throws InterruptedException {
		 assertLinkIsActive(headerSection.homeLink); 
		 headerSection.clickLink(headerSection.signupLoginLink);
		 Assert.assertTrue("New User Signup!".equalsIgnoreCase(loginSignUpPage.getUserRegisterMessage().getText()));
		 
		 loginSignUpPage.userCanRegister("khaledsameh","khaled234@gmail.com");
		 Assert.assertTrue("ENTER ACCOUNT INFORMATION".equalsIgnoreCase(registerPage.enterAccountMessage.getText()));
		  
		 registerPage.enterAccountInformation("123456789",25,"12","2002","Abdelrahman","Osama","Itworx","Fostat City","United States","aaaa","bbbb","2164","01012465987");
		 Assert.assertEquals("Account Created!".toUpperCase(), registerPage.successMesssage.getText());
		  
		 registerPage.continueAccount();
		 Assert.assertTrue("Logged in as khaled sameh".equalsIgnoreCase(headerSection.loggedInMessage.getText()));
		  
		 registerPage.deleteAccount();
		 Assert.assertTrue(registerPage.deleteMessage.getText().equalsIgnoreCase("Account Deleted!"));
		  
		 registerPage.continueAccount();
		 Assert.assertEquals("rgba(255, 165, 0, 1)", headerSection.homeLink.getCssValue("color"));   
	 }
	 
//	 @Test
//	 (priority = 2)
//	 public void register_DuringCheckout() {
//		 assertLinkIsActive(headerSection.homeLink);
//		 homePage.scrollToElement(homePage.featuresSection);
//		 wait.until(ExpectedConditions.visibilityOf(homePage.featuresItemsTitle));
//		 
//		 Assert.assertEquals("FEATURES ITEMS", homePage.featuresItemsTitle.getText());
//		 ProductCard productCard = homePage.getOneFeaturesProduct();
//		 String productId = productCard.getProductId();
//		 productCard.addProductToCart();
//		 
//		 wait.until(ExpectedConditions.visibilityOf(cartPage.processedToCheckoutBtn));
//		 cartPage.processToCheckout();
//		 
//		 CartModal cartModal = new CartModal(driver, cartPage.getVisibleCartModal());
//		 Assert.assertEquals("Register / Login account to proceed on checkout.", cartModal.getMessage());
//		 
//		 cartModal.navigateToLoginSignupPage();
//		 Assert.assertTrue("New User Signup!".equalsIgnoreCase(loginSignUpPage.getUserRegisterMessage().getText()));
//	 }
	 
	 
}
