package Tests;

import static org.testng.Assert.assertFalse;
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

public class Payment_NegativeScenario extends BaseTest{
	HomePage homePage = new HomePage(driver);
	CartPage cartPage = new CartPage(driver);
	ProductsPage productsPage = new ProductsPage(driver);
	LoginSignUpPage loginSignUpPage = new LoginSignUpPage(driver);
	PaymentPage paymentPage = new PaymentPage(driver);
	OrderPage orderPage = new OrderPage(driver);
	Header headerSection = new Header(driver);
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
    private void assertLinkIsActive(WebElement link) {
        Assert.assertEquals("rgba(255, 165, 0, 1)", link.getCssValue("color"));
    }
    
    
    @DataProvider(name = "paymentData")
    private Object[][] testQuantityData() {
		Object[][] testData = new Object[][] {
			{"john Doe" , "4242424" ,"123" ,"12" , "2030"},
			{"john Doe" , "4242424242424242" ,"123" ,"12" , "2020"},
			{"john Doe" , "4242424242424242" ,"123" ,"3" , "2025"},
		};
		
		return testData;
	}
    @Test
    (priority = 1)
	public void enter_BlankFields() throws InterruptedException {
		headerSection.navigateTo(headerSection.signupLoginLink);
		assertLinkIsActive(headerSection.signupLoginLink);
		
		loginSignUpPage.userCanLogin("khaledsameh158@gmail.com" , "kh09874563");
		headerSection.navigateTo(headerSection.cartLink);
		assertLinkIsActive(headerSection.cartLink);
		
		cartPage.processToCheckout();

		orderPage.scrollToElement(orderPage.placeOrderBtn);
		wait.until(ExpectedConditions.visibilityOf(orderPage.placeOrderBtn));
		Thread.sleep(1000);
		orderPage.placeOrder();
		Thread.sleep(1000);
		Assert.assertTrue("payment".equalsIgnoreCase(paymentPage.paymentActivation.getText()));
		
		paymentPage.paymentCardName.sendKeys(" ");
		paymentPage.confirmOrder();
		assertTrue(paymentPage.isFieldBlank(paymentPage.paymentCardName));
	}
    
    @Test(priority = 2 ,dependsOnMethods = "enter_BlankFields", dataProvider = "paymentData")
	public void enter_InvalidInfo(String name,String number, String cvc, String expMonth, String expYear) throws InterruptedException {	
		paymentPage.enterValidPaymentInfo(name, number, cvc, expMonth, expYear);
		paymentPage.confirmOrder();
		assertFalse("Order Placed!".equalsIgnoreCase(paymentPage.placeOrderMessage.getText()));
	}
}
