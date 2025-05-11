package Tests;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

public class Checkout_NegativeScenario extends BaseTest{
	
	HomePage homePage = new HomePage(driver);
	CartPage cartPage = new CartPage(driver);
	ProductsPage productsPage = new ProductsPage(driver);
	Header headerSection = new Header(driver);
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
    private void assertLinkIsActive(WebElement link) {
        Assert.assertEquals("rgba(255, 165, 0, 1)", link.getCssValue("color"));
    }
	
	@Test
	(priority =1)
	public void checkout_emptyCart() {
		headerSection.navigateTo(headerSection.cartLink);
	    assertLinkIsActive(headerSection.cartLink);
	    
	    assertTrue(cartPage.isCartEmpty());
	    
	    cartPage.emptyCart.findElement(By.partialLinkText("here")).click();
	    assertLinkIsActive(headerSection.productsLink);
	}
	
	@Test
	(priority = 2 , dependsOnMethods = "checkout_emptyCart")
	public void checkout_WithOutLogin() throws InterruptedException {
	    productsPage.scrollToElement(productsPage.featuresSection);
	    wait.until(ExpectedConditions.visibilityOf(productsPage.featuresItemsTitle));
	    Assert.assertEquals("ALL PRODUCTS", productsPage.featuresItemsTitle.getText());
	    
	    ProductCard productCard = productsPage.getOneProduct();
	    productsPage.scrollToElement(productCard.getProductName());
	    wait.until(ExpectedConditions.visibilityOf(productCard.getProductName()));
	    Thread.sleep(1000);
	    productCard.addProductToCart();
	    
	    CartModal cartModal = new CartModal(driver, homePage.getVisibleCartModal());
	    Assert.assertEquals("Your product has been added to cart.", cartModal.getMessage());
	    cartModal.clickViewCart();
	    
	    cartPage.processToCheckout();
	    CartModal cartModalLogin = new CartModal(driver, cartPage.getVisibleCartModal());
	    Assert.assertEquals("Register / Login account to proceed on checkout.".toLowerCase(), cartModalLogin.getMessage().toLowerCase());
	    
	    cartModalLogin.navigateToLoginSignupPage();
	    assertLinkIsActive(headerSection.signupLoginLink);
	}
	
}
