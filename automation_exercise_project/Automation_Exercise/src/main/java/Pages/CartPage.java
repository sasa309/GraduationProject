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
	
	@FindBy(id = "cartModal")
    public WebElement cartModal;
	
	public void scrollToElement(CartProduct element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth'});", element.getCartProductName());
    }
	
	public WebElement getVisibleCartModal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOf(cartModal));
    }

    public void getCartProducts() {
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
        return null;  // لو المنتج مش موجود
    }

    // دالة للبحث عن منتج معين بواسطة اسم المنتج
    public CartProduct findCartProductByName(String productName) {
        for (CartProduct product : cartProducts) {
            if (product.getCartProductName().getText().equals(productName)) {
                return product;
            }
        }
        return null;  // لو المنتج مش موجود
    }
    
    public CartProduct getLastCartProduct() {
    	return cartProducts.getLast();
    }
    
    public boolean isCartEmpty() {
    	return cartProducts.size() == 0;
    }
    
    public void processToCheckout() {
		processedToCheckoutBtn.click();
	}

}
