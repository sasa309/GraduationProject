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
	
	@Test
	(priority = 1)
	public void addNonNumericQuantity() {
		headerSection.clickLink(headerSection.productsLink);
	    assertLinkIsActive(headerSection.productsLink);
	    homePage.scrollBy(500);
	    wait.until(ExpectedConditions.visibilityOf(productsPage.featuresItemsTitle));
	    
	    Assert.assertTrue("ALL PRODUCTS".equalsIgnoreCase(productsPage.featuresItemsTitle.getText()));
	    ProductCard productCard = productsPage.getOneFeaturesProduct();
	    String productId = productCard.getProductId();
	    productCard.viewProductDetails();
	    
	    Assert.assertEquals(productId, productDetailsPage.getProductId());
	    
	    productDetailsPage.setNonNumericQuantity("abc");
	    
	    Assert.assertTrue(productDetailsPage.getQuantityValue().isEmpty());
	}
	
	@Test
	(priority = 2)
	public void addNegativeQuantityProduct() {
		final int QUANTITY = -3;
		headerSection.clickLink(headerSection.productsLink);
	    assertLinkIsActive(headerSection.productsLink);
	    homePage.scrollBy(500);
	    wait.until(ExpectedConditions.visibilityOf(productsPage.featuresItemsTitle));
	    
	    Assert.assertTrue("ALL PRODUCTS".equalsIgnoreCase(productsPage.featuresItemsTitle.getText()));
	    ProductCard productCard = productsPage.getOneFeaturesProduct();
	    String productId = productCard.getProductId();
	    productCard.viewProductDetails();
	    
	    Assert.assertEquals(productId, productDetailsPage.getProductId());
	    
	    productDetailsPage.setNumericQuantity(-3);
	    productDetailsPage.addProductToCart();
	    
	    CartModal cartModal = new CartModal(driver, homePage.getVisibleCartModal());
	    Assert.assertNotEquals("Your product has been added to cart.", cartModal.getMessage());
	}
	
	@Test
	(priority = 3)
	public void addZeroQuantityProduct() {
		headerSection.clickLink(headerSection.productsLink);
	    assertLinkIsActive(headerSection.productsLink);
	    homePage.scrollBy(500);
	    wait.until(ExpectedConditions.visibilityOf(productsPage.featuresItemsTitle));
	    
	    Assert.assertTrue("ALL PRODUCTS".equalsIgnoreCase(productsPage.featuresItemsTitle.getText()));
	    ProductCard productCard = productsPage.getOneFeaturesProduct();
	    String productId = productCard.getProductId();
	    productCard.viewProductDetails();
	    
	    Assert.assertEquals(productId, productDetailsPage.getProductId());
	    
	    productDetailsPage.setNumericQuantity(0);
	    productDetailsPage.addProductToCart();
	    
	    CartModal cartModal = new CartModal(driver, homePage.getVisibleCartModal());
	    Assert.assertNotEquals("Your product has been added to cart.", cartModal.getMessage());
	}
	
	
	@Test
	(priority = 4)
	public void addoverlimitQuantityProduct() {
		final int OVER_LIMIT_QUANTITY = 99;
		
		headerSection.clickLink(headerSection.productsLink);
	    assertLinkIsActive(headerSection.productsLink);
	    homePage.scrollBy(500);
	    wait.until(ExpectedConditions.visibilityOf(productsPage.featuresItemsTitle));
	    
	    Assert.assertTrue("ALL PRODUCTS".equalsIgnoreCase(productsPage.featuresItemsTitle.getText()));
	    ProductCard productCard = productsPage.getOneFeaturesProduct();
	    String productId = productCard.getProductId();
	    productCard.viewProductDetails();
	    
	    Assert.assertEquals(productId, productDetailsPage.getProductId());
	    
	    productDetailsPage.setNumericQuantity(OVER_LIMIT_QUANTITY);
	    productDetailsPage.addProductToCart();
	    
	    CartModal cartModal = new CartModal(driver, homePage.getVisibleCartModal());
	    Assert.assertNotEquals("Your product has been added to cart.", cartModal.getMessage());
	}
	
	
	@Test
	(priority = 5)
	public void addToCart_OutOfStockProduct() {
		headerSection.clickLink(headerSection.productsLink);
	    assertLinkIsActive(headerSection.productsLink);
	    homePage.scrollBy(500);
	    wait.until(ExpectedConditions.visibilityOf(productsPage.featuresItemsTitle));
	    
	    Assert.assertTrue(
	    	    "ALL PRODUCTS".equalsIgnoreCase(productsPage.featuresItemsTitle.getText()),
	    	    "Expected title to be 'ALL PRODUCTS' (case-insensitive)");
	    ProductCard productCard = productsPage.getOneFeaturesProduct();
	    String productId = productCard.getProductId();
	    productCard.viewProductDetails();
	    
	    Assert.assertEquals(productId, productDetailsPage.getProductId());
	    
	    if(productDetailsPage.isProductOutOfStock()) {
	    	productDetailsPage.addProductToCart();
	    	Assert.assertFalse(productDetailsPage.isAddToCartButtonEnabled());
	    }else {
	    	 Assert.fail("nothing to test");
		}
	}
	
	private void verify_EmptyCart() {

	}
}
