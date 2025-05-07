package Components;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Pages.BasePage;

public class CartProduct extends BasePage{
	private WebElement root;

	public CartProduct(WebDriver driver, WebElement root) {
		super(driver);
		this.root = root;
	}

	public String getCartProductId() {
		WebElement productLink = root.findElement(By.cssSelector("td.cart_delete a"));
		return productLink.getAttribute("data-product-id");
	}

	public WebElement getCartProductName() {
		return root.findElement(By.cssSelector("td.cart_description > h4 > a"));
	}

	public String getCartQuantity() {
		WebElement quantityBtn = root.findElement(By.cssSelector("td.cart_quantity button"));
		return quantityBtn.getText();
	}

	public String getCartPrice() {
		// Assuming this is the unit price column
		WebElement priceElement = root.findElement(By.cssSelector("td.cart_price p"));
		return priceElement.getText();
	}

	public String getCartTotal() {
		// Total per product (price * quantity)
		WebElement totalElement = root.findElement(By.cssSelector("td.cart_total p"));
		return totalElement.getText();
	}

//	public void removeCartProduct() {
//		WebElement deleteButton = root.findElement(By.cssSelector("td.cart_delete a"));
//		deleteButton.click();
//	}
	
	public void removeCartProduct() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		// أعد العثور على العنصر كل مرة عند الحاجة إليه
		WebElement deleteButton = wait.until(ExpectedConditions.refreshed(
			ExpectedConditions.elementToBeClickable(
				root.findElement(By.cssSelector("td.cart_delete a"))
			)
		));
		
		deleteButton.click();
	}

}
