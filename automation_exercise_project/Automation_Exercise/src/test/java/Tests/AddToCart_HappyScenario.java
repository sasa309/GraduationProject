package Tests;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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
	    CartProduct product = cartPage.findCartProductById(expectedProductId);
	    cartPage.scrollToElement(product.getCartProductName());
	    Assert.assertEquals(product.getCartProductId(), expectedProductId);
	}
	
	private void assertProductRemovedFromCart(String expectedProductId) {
		CartProduct product = cartPage.findCartProductById(expectedProductId);
		cartPage.scrollToElement(product.getCartProductName());
		product.removeCartProduct();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.stalenessOf(product.getCartProductName()));
		if(cartPage.isCartEmpty()) {
			Assert.assertTrue(true , "product is removed and cart is empty");
			System.out.println("cart is empy");
		}
		else {
			cartPage.getCartProducts();
			Assert.assertNull(cartPage.findCartProductById(expectedProductId));
		}
	}
	
	
	@Test
	(priority = 1)
	public void addProductToCart_FromHomePage() throws InterruptedException {
	    assertLinkIsActive(headerSection.homeLink);
	    
	    homePage.scrollToElement(homePage.featuresItemsTitle);
	    wait.until(ExpectedConditions.visibilityOf(homePage.featuresItemsTitle));
	    Assert.assertEquals("FEATURES ITEMS", homePage.featuresItemsTitle.getText());
	    
	    
	    ProductCard productCard = homePage.getOneFeaturesProduct();
	    homePage.scrollToElement(productCard.getProductName());
	    wait.until(ExpectedConditions.visibilityOf(productCard.getProductName()));
	    Thread.sleep(1000);
	    String productId = productCard.getProductId();
	    productCard.addProductToCart();
	
	    CartModal cartModal = new CartModal(driver, homePage.getVisibleCartModal());
	    Assert.assertEquals("Your product has been added to cart.", cartModal.getMessage());
	    cartModal.clickViewCart();
	
	    assertLinkIsActive(headerSection.cartLink);
	    
	    cartPage.getCartProducts();
	    assertProductAddedToCart(productId);
	    
	    assertProductRemovedFromCart(productId);
	}
	
	
	@Test
	(priority = 2)
	public void addTwoDifferntProductToCart_WithContinueShopping() throws InterruptedException {
		headerSection.navigateTo(headerSection.productsLink);
	    assertLinkIsActive(headerSection.productsLink);
	    
	    productsPage.scrollToElement(productsPage.featuresSection);
	    wait.until(ExpectedConditions.visibilityOf(productsPage.featuresItemsTitle));
	    Assert.assertEquals("ALL PRODUCTS", productsPage.featuresItemsTitle.getText());
	    
	    ProductCard firstProductCard = productsPage.getOneProduct();
	    productsPage.scrollToElement(firstProductCard.getProductName());
	    wait.until(ExpectedConditions.visibilityOf(firstProductCard.getProductName()));
	    Thread.sleep(1000);
	    String firstProductId = firstProductCard.getProductId();
	    firstProductCard.addProductToCart();
	    CartModal firstCartModal = new CartModal(driver, homePage.getVisibleCartModal());
	    Assert.assertEquals("Your product has been added to cart.", firstCartModal.getMessage());
	    firstCartModal.clickContinueShopping();
	    
	    
	    ProductCard secondProductCard = productsPage.getOneProduct();
	    productsPage.scrollToElement(secondProductCard.getProductName());
	    wait.until(ExpectedConditions.visibilityOf(secondProductCard.getProductName()));
	    Thread.sleep(1000);
	    if(secondProductCard.getProductId() == firstProductId) {
	    	secondProductCard = homePage.getOneFeaturesProduct();
	    }
	    String secondProductId = secondProductCard.getProductId();
	    secondProductCard.addProductToCart();
	    CartModal secondCartModal = new CartModal(driver, homePage.getVisibleCartModal());
	    Assert.assertEquals("Your product has been added to cart.", secondCartModal.getMessage());
	    secondCartModal.clickViewCart();
	    
	    assertLinkIsActive(headerSection.cartLink);
	    
	    cartPage.getCartProducts();
	    assertProductAddedToCart(firstProductId);
	    assertProductAddedToCart(secondProductId);
	    
	    cartPage.removeAllProducts();
	    assertTrue(cartPage.isCartEmpty());
	}
	
	@Test
	(priority = 3)
	public void adjustQuantity_ThenAddToCart() throws InterruptedException {
		final int QUANTITY = 3;
		
		headerSection.navigateTo(headerSection.productsLink);
	    assertLinkIsActive(headerSection.productsLink);
	    
	    ProductCard productCard = productsPage.getOneProduct();
	    productsPage.scrollToElement(productCard.getProductName());
	    wait.until(ExpectedConditions.visibilityOf(productCard.getProductName()));
	    Thread.sleep(1000);
	    String productId = productCard.getProductId();
	    String productCardName = productCard.getProductName().getText();
	    String productCardPrice = productCard.getProductPrice();
	    productCard.viewProductDetails();
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
	    
	    Integer productPrice = product.getCartPrice();
	    Assert.assertTrue(productCardName.equalsIgnoreCase(product.getCartProductName().getText()));
	    Assert.assertEquals(productPrice, Integer.parseInt(productCardPrice));
	    Assert.assertEquals(QUANTITY, Integer.parseInt(product.getCartQuantity()));
	    Assert.assertEquals(productPrice * QUANTITY, product.getCartTotal());
	}

}
