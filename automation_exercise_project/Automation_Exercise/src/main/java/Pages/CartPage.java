package Pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Components.CartProduct;

public class CartPage extends BasePage{

	public ArrayList<CartProduct> cartProducts = new ArrayList<CartProduct>(); 
	
	public CartPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(id = "cart_info_table")
	WebElement cartTabl;
	
	@FindBy(xpath = "//*[@id=\"do_action\"]/div[1]/div/div/a")
	public WebElement processedToCheckoutBtn;
	
	@FindBy(id = "do_action")
	public WebElement processedCheckOutSection;
	
	@FindBy(id = "checkoutModal")
    public WebElement cartModal;
	
	@FindBy(id = "empty_cart")
	public WebElement emptyCart;
	
	@FindBy(xpath = "//*[@id=\"empty_cart\"]/p/a")
	public WebElement productsPageLink;
	
	
	public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth'});", element);
    }
	
	public WebElement getVisibleCartModal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOf(cartModal));
    }

	public void getCartProducts() {
	    cartProducts.clear(); 
	    List<WebElement> products =  cartTabl.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
	    for (WebElement cartProduct : products) {
	        cartProducts.add(new CartProduct(driver, cartProduct));
	    }
	}
    
    public CartProduct findCartProductById(String productId) {
        for (CartProduct product : cartProducts) {
            if (product.getCartProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public CartProduct findCartProductByName(String productName) {
        for (CartProduct product : cartProducts) {
            if (product.getCartProductName().getText().equals(productName)) {
                return product;
            }
        }
        return null;
    }
    
    public void removeProduct(String productId) {
        for (CartProduct product : cartProducts) {
            if (product.getCartProductId().equals(productId)) {
                cartProducts.remove(product);
            }
        }
    }
    
    public boolean isCartEmpty() {
    	String status = emptyCart.getCssValue("display");
    	if(status.equalsIgnoreCase("none"))
    		return false;
    	return true;
    }
    
    public void processToCheckout() {
		processedToCheckoutBtn.click();
	}
    
    public void removeAllProducts() {
    	for (CartProduct product : cartProducts) {
            product.removeCartProduct();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	    wait.until(ExpectedConditions.stalenessOf(product.getCartProductName()));
        }
	}
    
    public WebElement getCartModalMessage() {
		return driver.findElement(By.xpath("//*[@id=\"checkoutModal\"]/div/div/div[2]/p[1]"));
	}
    
    public void navigateToProductsPage() {
    	productsPageLink.click();
	}
}
