package Tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Components.CartProduct;
import Components.Header;
import Pages.CartPage;
import Pages.HomePage;
import Pages.LoginSignUpPage;
import Pages.OrderPage;
import Pages.PaymentPage;
import Pages.ProductDetailsPage;
import Pages.ProductsPage;
import data.LoadProperties;

public class Checkout_HappyScenario extends BaseTest{
	CartPage cartPage = new CartPage(driver);
	LoginSignUpPage loginSignUpPage = new LoginSignUpPage(driver);
	PaymentPage paymentPage = new PaymentPage(driver);
	OrderPage orderPage = new OrderPage(driver);
	Header headerSection = new Header(driver);
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	String email = LoadProperties.userLoginData.getProperty("email"); 
	String password = LoadProperties.userLoginData.getProperty("password");
	String firstName = LoadProperties.userLoginData.getProperty("firstname");
	String lastName = LoadProperties.userLoginData.getProperty("lastname");
	
    private void assertLinkIsActive(WebElement link) {
        Assert.assertEquals("rgba(255, 165, 0, 1)", link.getCssValue("color"));
    }
	
	
	@Test
	public void verifyAddress_Checkout_AfterLogin() throws InterruptedException {
		headerSection.navigateTo(headerSection.signupLoginLink);
		assertLinkIsActive(headerSection.signupLoginLink);
		
		loginSignUpPage.userCanLogin(email, password);
		assertTrue((firstName + " " + lastName).equalsIgnoreCase(headerSection.loggedInUser.getText()));
		
		headerSection.navigateTo(headerSection.cartLink);
		assertLinkIsActive(headerSection.cartLink);
		
		cartPage.processToCheckout();
		Assert.assertTrue("Address Details".equalsIgnoreCase(orderPage.reviewAddressMessage.getText()));
		Assert.assertTrue("Review Your Order".equalsIgnoreCase(orderPage.reviewOrderMessage.getText()));
		
		orderPage.scrollToElement(orderPage.addressDeliverBox);
		wait.until(ExpectedConditions.visibilityOf(orderPage.addressDeliverBox));
		Assert.assertTrue(". khaled sameh".equalsIgnoreCase(orderPage.getAddressName()));
		Assert.assertTrue("India".equalsIgnoreCase(orderPage.getAddressCounryName()));
		Assert.assertTrue("01276439766".equalsIgnoreCase(orderPage.getAddressPhone()));
		
		orderPage.scrollToElement(orderPage.reviewOrderMessage);
		wait.until(ExpectedConditions.visibilityOf(orderPage.reviewOrderMessage));
		orderPage.getCartProducts();
		Assert.assertEquals(orderPage.getTotalProductsPrice(), orderPage.getOrderPrice());

		orderPage.scrollToElement(orderPage.placeOrderBtn);
		wait.until(ExpectedConditions.visibilityOf(orderPage.placeOrderBtn));
		Thread.sleep(1000);
		orderPage.placeOrder();
		Thread.sleep(1000);
		Assert.assertTrue("payment".equalsIgnoreCase(paymentPage.paymentActivation.getText()));
	}
}
