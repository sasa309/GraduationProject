package Tests;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Components.CartModal;
import Components.Header;
import Components.ProductCard;
import Pages.CartPage;
import Pages.HomePage;
import Pages.LoginSignUpPage;
import Pages.OrderPage;
import Pages.PaymentPage;
import Pages.ProductDetailsPage;
import Pages.ProductsPage;
import data.LoadProperties;

public class Payment_HappyScenario extends BaseTest{
	HomePage homePage = new HomePage(driver);
	CartPage cartPage = new CartPage(driver);
	ProductsPage productsPage = new ProductsPage(driver);
	ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
	LoginSignUpPage loginSignUpPage = new LoginSignUpPage(driver);
	PaymentPage paymentPage = new PaymentPage(driver);
	OrderPage orderPage = new OrderPage(driver);
	Header headerSection = new Header(driver);
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	String email = LoadProperties.userLoginData.getProperty("email"); 
	String password = LoadProperties.userLoginData.getProperty("password");
	
    private void assertLinkIsActive(WebElement link) {
        Assert.assertEquals("rgba(255, 165, 0, 1)", link.getCssValue("color"));
    }
    
    @DataProvider(name = "paymentData")
    private Object[][] testQuantityData() {
		Object[][] testData = new Object[][] {
			{"john Doe" , "4242424242424242" ,"123" ,"12" , "2030"},
		};
		
		return testData;
	}

	@Test
	(priority = 1 , dataProvider = "paymentData")
	public void confirmOrder_AndVerifyCartIsEmpty(String name,String number, String cvc, String expMonth, String expYear) throws InterruptedException {
		headerSection.navigateTo(headerSection.signupLoginLink);
		assertLinkIsActive(headerSection.signupLoginLink);
		
		loginSignUpPage.userCanLogin(email, password);
		headerSection.navigateTo(headerSection.cartLink);
		assertLinkIsActive(headerSection.cartLink);
		
		cartPage.processToCheckout();

		orderPage.scrollToElement(orderPage.placeOrderBtn);
		wait.until(ExpectedConditions.visibilityOf(orderPage.placeOrderBtn));
		Thread.sleep(1000);
		orderPage.placeOrder();
		Thread.sleep(1000);
		Assert.assertTrue("payment".equalsIgnoreCase(paymentPage.paymentActivation.getText()));
		
		paymentPage.enterValidPaymentInfo(name, number, cvc, expMonth, expYear);
		paymentPage.confirmOrder();
		assertTrue("Order Placed!".equalsIgnoreCase(paymentPage.placeOrderMessage.getText()));
		
		paymentPage.continueBtn.click();
		assertLinkIsActive(headerSection.homeLink);
		
		headerSection.navigateTo(headerSection.cartLink);
		assertLinkIsActive(headerSection.cartLink);
		
		assertTrue(cartPage.isCartEmpty());
	}
	
	@Test
	(priority = 2 , dataProvider = "paymentData")
	public void confirmOrder_EmptyCart(String name,String number, String cvc, String expMonth, String expYear) throws InterruptedException {
		Thread.sleep(1000);
		headerSection.navigateTo(headerSection.signupLoginLink);
		assertLinkIsActive(headerSection.signupLoginLink);
		
		loginSignUpPage.userCanLogin(email, password);
		headerSection.navigateTo(headerSection.cartLink);
		assertLinkIsActive(headerSection.cartLink);
		
		assertTrue(cartPage.isCartEmpty());
		
		cartPage.navigateToProductsPage();
		assertLinkIsActive(headerSection.productsLink);
		
		ProductCard productCard = productsPage.getOneProduct();
	    productsPage.scrollToElement(productCard.getProductName());
	    wait.until(ExpectedConditions.visibilityOf(productCard.getProductName()));
	    Thread.sleep(1000);
	    productCard.addProductToCart();
	    
	    CartModal cartModal = new CartModal(driver, homePage.getVisibleCartModal());
	    cartModal.clickViewCart();
	    
	    cartPage.processToCheckout();

		orderPage.scrollToElement(orderPage.placeOrderBtn);
		wait.until(ExpectedConditions.visibilityOf(orderPage.placeOrderBtn));
		Thread.sleep(1000);
		orderPage.placeOrder();
		Thread.sleep(1000);
		Assert.assertTrue("payment".equalsIgnoreCase(paymentPage.paymentActivation.getText()));
		
		paymentPage.enterValidPaymentInfo(name, number, cvc, expMonth, expYear);
		paymentPage.confirmOrder();
		assertTrue("Order Placed!".equalsIgnoreCase(paymentPage.placeOrderMessage.getText()));
		
		paymentPage.continueBtn.click();
		assertLinkIsActive(headerSection.homeLink);
		
		headerSection.navigateTo(headerSection.cartLink);
		assertLinkIsActive(headerSection.cartLink);
		
		assertTrue(cartPage.isCartEmpty());
	}
}
