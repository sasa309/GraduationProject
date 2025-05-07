package Pages;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Components.ProductCard;

public class HomePage extends BasePage{

	public HomePage(WebDriver driver) {
		super(driver);
		
	}
	
	@FindBy(css = "div.features_items h2.title")
    public WebElement featuresItemsTitle;
	
	@FindBy(css= "div.features_items")
	public WebElement featuresSection;

    @FindBy(css = "div.recommended_items h2.title")
    public WebElement recomendedItemsTitle;
    
    @FindBy(css = "div.recommended_items")
    public WebElement recomendedSection;
    
    @FindBy(id = "cartModal")
    public WebElement cartModal;
    
    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth'});", element);
    }
    
    public ProductCard getOneFeaturesProduct() {
    	int randomIndex = new Random().nextInt(getFeaturesProducts().size());
    	WebElement productCard = getFeaturesProducts().get(randomIndex);
    	return new ProductCard(driver,productCard);
    }
    
    public List<WebElement> getFeaturesProducts() {
    	return featuresSection.findElements(By.className("product-image-wrapper"));
    }
    
    
    
    public void viewCart() {
    	cartModal.findElement(By.partialLinkText("View Cart")).click();
    }
    
    public WebElement getVisibleCartModal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOf(cartModal));
    }

	
}
