package Tests;

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
import Pages.ProductDetailsPage;
import Pages.ProductsPage;

public class AddToCart_NegativeScenario extends BaseTest{
	HomePage homePage = new HomePage(driver);
	CartPage cartPage = new CartPage(driver);
	ProductsPage productsPage = new ProductsPage(driver);
	ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
	Header headerSection = new Header(driver);
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
    private void assertLinkIsActive(WebElement link) {
        Assert.assertEquals("rgba(255, 165, 0, 1)", link.getCssValue("color"));
    }
    
    @DataProvider(name = "quantityData")
    private Object[][] testQuantityData() {
		Object[][] testData = new Object[][] {
			{-3},
			{0},
			{999},
		};
		
		return testData;
	}
	
    
	@Test
	(priority = 1)
	public void addNonNumericQuantity() throws InterruptedException {
		headerSection.navigateTo(headerSection.productsLink);
	    assertLinkIsActive(headerSection.productsLink);
	    Assert.assertTrue("ALL PRODUCTS".equalsIgnoreCase(productsPage.featuresItemsTitle.getText()));
	    
	    ProductCard productCard = productsPage.getOneProduct();
	    productsPage.scrollToElement(productCard.getProductName());
	    wait.until(ExpectedConditions.visibilityOf(productCard.getProductName()));
	    Thread.sleep(1000);
	    String productId = productCard.getProductId();
	    productCard.viewProductDetails();
	    Assert.assertEquals(productId, productDetailsPage.getProductId());
	    
	    productDetailsPage.setNonNumericQuantity("abc");
	    Assert.assertNotEquals("abc",productDetailsPage.getQuantityValue());
	}
	
	@Test
	(priority = 2 ,dependsOnMethods = "addNonNumericQuantity" , dataProvider = "quantityData")
	public void addInvalidQuantityProduct(int quantity) throws InterruptedException {	    
	    productDetailsPage.setNumericQuantity(quantity);
	    productDetailsPage.addProductToCart();
	    
	    CartModal cartModal = new CartModal(driver, productDetailsPage.getVisibleCartModal());
	    String message = cartModal.getMessage();
	    cartModal.clickContinueShopping();
	    Assert.assertNotEquals("Your product has been added to cart.", message);
	}
}
