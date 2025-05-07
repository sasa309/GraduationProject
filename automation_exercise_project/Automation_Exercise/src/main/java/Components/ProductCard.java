package Components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import Pages.BasePage;

public class ProductCard extends BasePage{
	private WebElement root;

	public ProductCard(WebDriver driver , WebElement  root) {
		super(driver);
		this.root = root;
	}
	
//	@FindBy(partialLinkText = "Add to cart")
//	public WebElement addToCartBtn;
	
	public WebElement getProductName() {
		return root.findElement(By.cssSelector("div.productinfo")).findElement(By.tagName("p"));
	}
	
	public WebElement getProductPrice() {
		return root.findElement(By.cssSelector("div.productinfo")).findElement(By.tagName("h2"));
	}
	
	public WebElement getProductImage() {
		return root.findElement(By.cssSelector("div.productinfo")).findElement(By.tagName("img"));
	}

	public String getProductId() {
		return root.findElement(By.cssSelector("div.productinfo")).findElement(By.tagName("a")).getAttribute("data-product-id");
	}
	
	public void viewProductDetails() {
		root.findElement(By.cssSelector("div.choose")).findElement(By.partialLinkText("View Product")).click();
	}
	
	public void addProductToCart() {
		root.findElement(By.partialLinkText("Add to cart")).click();
//		WebElement addToCartButton = root.findElement(By.partialLinkText("Add to cart"));
//	    Actions actions = new Actions(driver);  // تأكد إن driver متاح هنا
//	    actions.moveToElement(addToCartButton).click().perform();
	}
	
}
