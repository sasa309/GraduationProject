package Tests;

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

public class ViewProduct_HappyScenario extends BaseTest{
	
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
	public void verify_Valid_ProductDetails() throws InterruptedException {
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
	    
	    Assert.assertEquals(productId, productDetailsPage.getProductId());
	    Assert.assertEquals(productName, productDetailsPage.getProductName());
	    Assert.assertEquals(Integer.parseInt(productPrice),Integer.parseInt(productDetailsPage.getProductPrice()));
	    Assert.assertEquals(1,Integer.parseInt(productDetailsPage.getQuantityValue()));
	    Assert.assertTrue(validAvailabilityValues.contains(
	    		productDetailsPage.getProductAvailability().toLowerCase()));
	    Assert.assertTrue(validMainCategories.contains(
	    		productDetailsPage.getProductCategory().get(0).toLowerCase()));
	    Assert.assertTrue(validSubCategories.contains(
	    		productDetailsPage.getProductCategory().get(1).toLowerCase()));
	    Assert.assertTrue(validBrands.contains(
	    		productDetailsPage.getProductBrand()));
	    Assert.assertTrue(productDetailsPage.isAddToCartButtonEnabled());
	}
	
	@Test
	(priority = 2)
	public void valid_Review_OnProduct() {
	   productDetailsPage.scrollToElement(productDetailsPage.reviewFormMessage);
	   productDetailsPage.enterValidInfo();
	   productDetailsPage.reviewOnProduct();
	   wait.until(ExpectedConditions.visibilityOf(productDetailsPage.reviewMessag));
	   assertTrue("Thank you for your review.".equalsIgnoreCase(productDetailsPage.getReviewMessage()));
	}
}
