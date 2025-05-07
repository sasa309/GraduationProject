package Components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Pages.BasePage;

public class CartModal extends BasePage{
	private WebElement root;
	
	
    public CartModal(WebDriver driver , WebElement root) {
		super(driver);
		this.root = root;
    }

    public String getMessage() {
        return root.findElement(By.cssSelector(".modal-body > p")).getText();
    }

    public void clickContinueShopping() {
        root.findElement(By.cssSelector(".btn.btn-success")).click();
    }

    public void clickViewCart() {
    	root.findElement(By.partialLinkText("View Cart")).click();
    }
    
    public void navigateToLoginSignupPage() {
    	root.findElement(By.partialLinkText("Register / Login")).click();
    }

    public boolean isVisible() {
        return root.isDisplayed();
    }
    
}
