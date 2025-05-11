package Components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import Pages.BasePage;

public class CartModal extends BasePage{
	private Actions actions = new Actions(driver);
	private WebElement root;
	
	
    public CartModal(WebDriver driver , WebElement root) {
		super(driver);
		this.root = root;
    }

    public String getMessage() {
        return root.findElement(By.cssSelector(".modal-body > p")).getText();
    }

    public void clickContinueShopping() {
        WebElement continueButton = root.findElement(By.cssSelector("div.modal-footer > button"));
        actions.moveToElement(continueButton).click().perform();
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
