package Pages;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Components.ProductCard;

public class ProductsPage extends BasePage{

	public ProductsPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(css = "div.features_items h2.title")
    public WebElement featuresItemsTitle;
	
	@FindBy(css= "div.features_items")
	public WebElement featuresSection;
	
	@FindBy(id = "cartModal")
    public WebElement cartModal;
    
	 public void scrollToElement(WebElement element) {
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth'});", element);
	 }
    
    public ProductCard getOneProduct() {
    	int randomIndex = new Random().nextInt(getFeaturesProducts().size());
    	WebElement productCard = getFeaturesProducts().get(randomIndex);
    	return new ProductCard(driver,productCard);
    }
    
    public List<WebElement> getFeaturesProducts() {
    	return featuresSection.findElements(By.className("product-image-wrapper"));
    }

}
