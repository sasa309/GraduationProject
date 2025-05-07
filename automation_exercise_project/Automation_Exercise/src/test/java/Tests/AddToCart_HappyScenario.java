package Tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Components.CartModal;
import Components.CartProduct;
import Components.Header;
import Components.ProductCard;
import Pages.CartPage;
import Pages.HomePage;
import Pages.ProductDetailsPage;
import Pages.ProductsPage;

public class AddToCart_HappyScenario extends BaseTest{
	
	HomePage homePage = new HomePage(driver);
	CartPage cartPage = new CartPage(driver);
	ProductsPage productsPage = new ProductsPage(driver);
	ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
	Header headerSection = new Header(driver);
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
    private void assertLinkIsActive(WebElement link) {
        Assert.assertEquals("rgba(255, 165, 0, 1)", link.getCssValue("color"));
    }
	
	private void assertProductAddedToCart(String expectedProductId) {
	    cartPage.getCartProducts();
	    CartProduct product = cartPage.findCartProductById(expectedProductId);
	    cartPage.scrollToElement(product);
	    Assert.assertNotNull(product, "Product not found in cart.");
//	    Assert.assertEquals(product.getCartProductId(), expectedProductId);
	}
	
	private void hideAllFrames() {
		List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
		for (WebElement iframe : iframes) {
		    ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", iframe);
		}
	}
	
	
	@Test
	(priority = 1)
	public void userCanAddProductToCart_FromHomePage() {
	    assertLinkIsActive(headerSection.homeLink);
	    homePage.scrollToElement(homePage.featuresSection);
	    wait.until(ExpectedConditions.visibilityOf(homePage.featuresItemsTitle));
	    
	    hideAllFrames();
	
	    Assert.assertEquals("FEATURES ITEMS", homePage.featuresItemsTitle.getText());
	    ProductCard productCard = homePage.getOneFeaturesProduct();
	    String productId = productCard.getProductId();
	    productCard.addProductToCart();
	
	    CartModal cartModal = new CartModal(driver, homePage.getVisibleCartModal());
	    Assert.assertEquals("Your product has been added to cart.", cartModal.getMessage());
	    cartModal.clickViewCart();
	
	    assertLinkIsActive(headerSection.cartLink);
	    assertProductAddedToCart(productId);
	}
	
	
	@Test
	(priority = 2)
	public void userCanAddProductToCart_FromDetailsPage_WithDefaultQuantity() {
	    assertLinkIsActive(headerSection.homeLink);
	    homePage.scrollToElement(homePage.featuresSection);
	    wait.until(ExpectedConditions.visibilityOf(homePage.featuresItemsTitle));
	
	    Assert.assertEquals("FEATURES ITEMS", homePage.featuresItemsTitle.getText());
	    ProductCard productCard = homePage.getOneFeaturesProduct();
	    String productId = productCard.getProductId();
	    productCard.viewProductDetails();
	
	    assertLinkIsActive(headerSection.productsLink);
	    Assert.assertEquals(productId, productDetailsPage.getProductId());
	
	    productDetailsPage.addProductToCart();
	
		CartModal cartModal = new CartModal(driver, homePage.getVisibleCartModal());
	    Assert.assertEquals("Your product has been added to cart.", cartModal.getMessage());
	    cartModal.clickViewCart();
	
	    assertLinkIsActive(headerSection.cartLink);
	    assertProductAddedToCart(productId);
	}
	
	@Test
	(priority = 3)
	public void adjustQuantity_ThenAddToCart() {
		final int QUANTITY = 3;
		
		headerSection.clickLink(headerSection.productsLink);
	    assertLinkIsActive(headerSection.productsLink);
	    productsPage.scrollToElement(productsPage.featuresSection);
	    wait.until(ExpectedConditions.visibilityOf(productsPage.featuresItemsTitle));

	    Assert.assertTrue("ALL PRODUCTS".equalsIgnoreCase(productsPage.featuresItemsTitle.getText()));
	    ProductCard productCard = productsPage.getOneProduct();
	    String productId = productCard.getProductId();
	    productCard.viewProductDetails();

	    assertLinkIsActive(headerSection.productsLink);
	    Assert.assertEquals(productId, productDetailsPage.getProductId());

	    productDetailsPage.increaseQuantityUsingArrow(QUANTITY - 1);
	    productDetailsPage.addProductToCart();

	    CartModal cartModal = new CartModal(driver, homePage.getVisibleCartModal());
	    Assert.assertEquals("Your product has been added to cart.", cartModal.getMessage());
	    cartModal.clickViewCart();

	    assertLinkIsActive(headerSection.cartLink);
	    cartPage.getCartProducts();
	    CartProduct product = cartPage.findCartProductById(productId);
	    assertProductAddedToCart(productId);
	    Assert.assertEquals(QUANTITY, Integer.parseInt(product.getCartQuantity()));
	}

	
	@Test
	(priority = 4)
	public void adjustQuantityTwice_ThenAddToCart() {
	    final int FIRST_QUANTITY = 3;
	    final int SECOND_QUANTITY = 2;

	    headerSection.clickLink(headerSection.productsLink);
	    assertLinkIsActive(headerSection.productsLink);
	    productsPage.scrollToElement(productsPage.featuresSection);
	    wait.until(ExpectedConditions.visibilityOf(productsPage.featuresItemsTitle));

	    Assert.assertTrue("ALL PRODUCTS".equalsIgnoreCase(productsPage.featuresItemsTitle.getText()));
	    ProductCard productCard = productsPage.getOneProduct();
	    String productId = productCard.getProductId();
	    productCard.addProductToCart();
	    
	    CartModal cartModal1 = new CartModal(driver, homePage.getVisibleCartModal());
	    Assert.assertEquals("Your product has been added to cart.", cartModal1.getMessage());
	    cartModal1.clickContinueShopping();

	    assertLinkIsActive(headerSection.productsLink);
	    Assert.assertEquals(productId, productDetailsPage.getProductId());

	    
	    productDetailsPage.increaseQuantityUsingArrow(FIRST_QUANTITY - 1);
	    productDetailsPage.addProductToCart();
	    CartModal cartModal2 = new CartModal(driver, homePage.getVisibleCartModal());
	    Assert.assertEquals("Your product has been added to cart.", cartModal2.getMessage());
	    cartModal2.clickContinueShopping();

	    
	    productDetailsPage.decreaseQuantityUsingArrow(SECOND_QUANTITY - 1);
	    productDetailsPage.addProductToCart();
	    CartModal cartModal3 = new CartModal(driver, homePage.getVisibleCartModal());
	    Assert.assertEquals("Your product has been added to cart.", cartModal3.getMessage());
	    cartModal3.clickViewCart();

	    assertLinkIsActive(headerSection.cartLink);
	    cartPage.getCartProducts();
	    CartProduct product = cartPage.findCartProductById(productId);
	    assertProductAddedToCart(productId);

	    Assert.assertEquals(SECOND_QUANTITY, Integer.parseInt(product.getCartQuantity()));
	}
	
	@Test
	(priority = 5)
	public void addProductToCart_ThenAdjustTwice() {
	    final int FIRST_QUANTITY = 3;
	    final int SECOND_QUANTITY = 2;

	    headerSection.clickLink(headerSection.productsLink);
	    assertLinkIsActive(headerSection.productsLink);
	    productsPage.scrollToElement(productsPage.featuresSection);
	    wait.until(ExpectedConditions.visibilityOf(productsPage.featuresItemsTitle));

	    Assert.assertTrue("ALL PRODUCTS".equalsIgnoreCase(productsPage.featuresItemsTitle.getText()));
	    ProductCard productCard = productsPage.getOneProduct();
	    String productId = productCard.getProductId();
	    productCard.viewProductDetails();

	    assertLinkIsActive(headerSection.productsLink);
	    Assert.assertEquals(productId, productDetailsPage.getProductId());

	    
	    productDetailsPage.increaseQuantityUsingArrow(FIRST_QUANTITY - 1);
	    productDetailsPage.addProductToCart();
	    CartModal cartModal1 = new CartModal(driver, homePage.getVisibleCartModal());
	    Assert.assertEquals("Your product has been added to cart.", cartModal1.getMessage());
	    cartModal1.clickContinueShopping();

	    
	    productDetailsPage.decreaseQuantityUsingArrow(SECOND_QUANTITY - 1);
	    productDetailsPage.addProductToCart();
	    CartModal cartModal2 = new CartModal(driver, homePage.getVisibleCartModal());
	    Assert.assertEquals("Your product has been added to cart.", cartModal2.getMessage());
	    cartModal2.clickViewCart();

	    assertLinkIsActive(headerSection.cartLink);
	    cartPage.getCartProducts();
	    CartProduct product = cartPage.findCartProductById(productId);
	    assertProductAddedToCart(productId);

	    Assert.assertEquals(SECOND_QUANTITY, Integer.parseInt(product.getCartQuantity()));
	}

}
