package Tests;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Components.Header;
import Components.ProductCard;
import Pages.HomePage;
import Pages.ProductDetailsPage;
import Pages.ProductsPage;

public class ViewProduct_NegativeScenario extends BaseTest{

	HomePage homePage = new HomePage(driver);
	ProductsPage productsPage = new ProductsPage(driver);
	ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
	Header headerSection = new Header(driver);
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	private void assertLinkIsActive(WebElement link) {
        Assert.assertEquals("rgba(255, 165, 0, 1)", link.getCssValue("color"));
    }

	@Test
	(priority = 1)
	public void verify_InValid_ProductDetails() throws InterruptedException {
		assertLinkIsActive(headerSection.homeLink);
		
		headerSection.navigateTo(headerSection.productsLink);
		assertLinkIsActive(headerSection.productsLink);
		
	    ProductCard productCard = productsPage.getOneProduct();
	    productDetailsPage.scrollToElement(productCard.getProductName());
	    wait.until(ExpectedConditions.visibilityOf(productCard.getProductName()));
	    Thread.sleep(1000);
	    String productId = productCard.getProductId();
	    String productName = productCard.getProductName().getText();
	    String productPrice = productCard.getProductPrice(); 
	    productCard.viewProductDetails();
	    
	    List<String> validAvailabilityValues = Arrays.asList("stock In", "stock out");
	    List<String> validMainCategories = Arrays.asList("men", "women", "kids");
	    List<String> validSubCategories = Arrays.asList("tshirts", "jeans", "shoes");
	    List<String> validBrands = Arrays.asList("Polo", "Nike", "H&M", "Adidas");
	    
	    Assert.assertNotEquals(productId, productDetailsPage.getProductId());
	    Assert.assertNotEquals(productName, productDetailsPage.getProductName());
	    Assert.assertNotEquals(Integer.parseInt(productPrice),Integer.parseInt(productDetailsPage.getProductPrice()));
	    Assert.assertNotEquals(1,Integer.parseInt(productDetailsPage.getQuantityValue()));
	    Assert.assertFalse(validAvailabilityValues.contains(
	    		productDetailsPage.getProductAvailability().toLowerCase()));
	    Assert.assertFalse(validMainCategories.contains(
	    		productDetailsPage.getProductCategory().get(0).toLowerCase()));
	    Assert.assertFalse(validSubCategories.contains(
	    		productDetailsPage.getProductCategory().get(1).toLowerCase()));
	    Assert.assertFalse(validBrands.contains(
	    		productDetailsPage.getProductBrand()));
	}
	
	@Test
	(priority = 2)
	public void inValid_Name_Review_OnProduct() {
	    productDetailsPage.scrollToElement(productDetailsPage.reviewFormMessage);
	    productDetailsPage.enterInvalidName();
	    productDetailsPage.reviewOnProduct();
	    wait.until(ExpectedConditions.visibilityOf(productDetailsPage.reviewMessag));
		assertFalse("Thank you for your review.".equalsIgnoreCase(productDetailsPage.getReviewMessage()));
	}
	
	@Test
	(priority = 3)
	public void inValid_Email_Review_OnProduct() {
		productDetailsPage.enterInvalidEmail();
	    productDetailsPage.reviewOnProduct();
	    wait.until(ExpectedConditions.visibilityOf(productDetailsPage.reviewMessag));
		assertFalse("Thank you for your review.".equalsIgnoreCase(productDetailsPage.getReviewMessage()));
	}
}
