package Pages;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetailsPage extends BasePage{

	public ProductDetailsPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(css = "body > section > div > div > div.col-sm-9.padding-right > div.product-details > div.col-sm-7 > div")
	WebElement productInfo;
	
	@FindBy(id = "product_id")
	WebElement productId;
	
	@FindBy(id = "quantity")
	public WebElement quantity;
	
	@FindBy(css = "body > section > div > div > div.col-sm-9.padding-right > div.product-details > div.col-sm-7 > div > span > button")
	WebElement addToCartBtn;
	
	@FindBy(id="cartModal")
	public WebElement cartModal;
	
	@FindBy(css = "body > section > div > div > div.col-sm-9.padding-right > div.category-tab.shop-details-tab > div.col-sm-12 > ul > li > a")
	public WebElement reviewFormMessage;
	
	@FindBy(id = "name")
	WebElement name;
	
	@FindBy(id = "email")
	WebElement email;
	
	@FindBy(name = "review")
	WebElement review;
	
	@FindBy(id = "button-review")
	WebElement reviewBtn;
	
	@FindBy(xpath  = "//*[@id=\"review-section\"]/div/div/span")
	public WebElement reviewMessag;
	
	public void scrollToElement(WebElement element) {
	    ((JavascriptExecutor) driver).executeScript("window.scrollTo(arguments[0], arguments[1]);", element.getLocation().getX(), element.getLocation().getY());
 }
	
	public String getProductId() {
		return productId.getDomAttribute("Value");
	}
	
	public String getProductName() {
		return productInfo.findElement(By.tagName("h2")).getText();
	}
	
	public String getProductPrice() {
		String allText =  productInfo.findElement(By.cssSelector("span > span")).getText();
		return allText.replace("Rs.", "").trim();
	}
	
	public List<String> getProductCategory() {
		WebElement categoryElement = productInfo.findElement(By.tagName("p"));
		String categoryText = categoryElement.getText();

		String[] parts = categoryText.replace("Category:", "").trim().split(">");
		
		String mainCategory = parts[0].trim();     
		String subCategory = parts[1].trim();

		return Arrays.asList(mainCategory, subCategory);
	}
	
	public String getProductBrand() {
	    WebElement brandElement = productInfo.findElement(By.xpath(".//p[contains(text(),'Brand:')]"));
	    String brandText = brandElement.getText();
	    return brandText.replace("Brand:", "").trim(); 
	}

	public String getProductAvailability() {
	    WebElement availabilityElement = productInfo.findElement(By.xpath(".//p[contains(text(),'Availability:')]"));
	    String availabilityText = availabilityElement.getText(); 
	    return availabilityText.replace("Availability:", "").trim();
	}
	
	public boolean isProductOutOfStock() {
	    String availability = getProductAvailability();
	    return availability.equalsIgnoreCase("Out of Stock");
	}

	
	public String getQuantityValue() {
	    return quantity.getDomAttribute("value");
	}

	
	public void setNumericQuantity(int value) {
		quantity.clear();
	    quantity.sendKeys(String.valueOf(value));
	}
	
	public void setNonNumericQuantity(String value) {
	    quantity.sendKeys("");
	    quantity.sendKeys(value);
	}
	
	public void increaseQuantityUsingArrow(int times) {
	    for (int i = 0; i < times; i++) {
	        quantity.sendKeys(Keys.ARROW_UP);
	    }
	}

	public void decreaseQuantityUsingArrow(int times) {
	    for (int i = 0; i < times; i++) {
	        quantity.sendKeys(Keys.ARROW_DOWN);
	    }
	}
	
	public void addProductToCart() {
		addToCartBtn.click();
	}
	
	public boolean isAddToCartButtonEnabled() {
	    return addToCartBtn.isEnabled() && addToCartBtn.isDisplayed();
	}
	
	public WebElement getVisibleCartModal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOf(cartModal));
    }
	
	public void enterValidInfo() {
		name.sendKeys("khaled323");
		email.sendKeys("khaledsameh158@gmail.com");
		review.sendKeys("hey");
	}

	
	public void enterInvalidName() {
		name.sendKeys("khaled323");
		email.sendKeys("khaledsameh158@gmail.com");
		review.sendKeys("hey");
	}
	
	public void enterInvalidEmail() {
		name.sendKeys("khaled");
		email.sendKeys("khaledsameh15348@gmail.com");
		review.sendKeys("hey");
	}
	
	public void reviewOnProduct() {
		reviewBtn.click();
	}
	
	public String getReviewMessage() {
	 return reviewMessag.getText();
	}
}
