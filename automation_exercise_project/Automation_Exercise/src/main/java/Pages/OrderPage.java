package Pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Components.CartProduct;

public class OrderPage extends BasePage{
	public ArrayList<CartProduct> cartProducts = new ArrayList<CartProduct>(); 


	public OrderPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(id = "cart_info")
	WebElement cartTabl;
	
	@FindBy(xpath = "//*[@id=\"cart_items\"]/div/div[4]/h2")
	public WebElement reviewOrderMessage;
	
	@FindBy(xpath = "//*[@id=\"cart_items\"]/div/div[2]/h2")
	public WebElement reviewAddressMessage;
	
	@FindBy(id = "address_delivery")
	public WebElement addressDeliverBox;
	
	@FindBy(css = "#cart_info > table > tbody > tr:last-of-type > td:nth-child(4) > p")
	WebElement totalOrderPrice;
	
	@FindBy(xpath  = "//*[@id=\"cart_items\"]/div/div[7]/a")
	public WebElement placeOrderBtn;
	
	public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth'});", element);
    }
	
	public void getCartProducts() {
	    cartProducts.clear(); 
	    List<WebElement> products =  cartTabl.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
	    for (WebElement cartProduct : products) {
	        cartProducts.add(new CartProduct(driver, cartProduct));
	    }
	}
	
	 public String getAddressName() {
		return addressDeliverBox.findElement(By.cssSelector("li.address_firstname.address_lastname")).getText();
	}
    
    public String getAddressCounryName() {
    	return addressDeliverBox.findElement(By.cssSelector("li.address_country_name")).getText();
	}
    
    public String getAddressPhone() {
    	return addressDeliverBox.findElement(By.cssSelector("li.address_phone")).getText();
	}
    
    public int getOrderPrice() {
    	String orderPriceString = totalOrderPrice.getText();
    	return Integer.parseInt( orderPriceString.replace("Rs.", " ").trim());
	}
    
    public int getTotalProductsPrice() {
		int total = 0;
	    int size = cartProducts.size();

	    for (int i = 0; i < size - 1; i++) {
	        total += cartProducts.get(i).getCartPrice();
	    }

	    return total;
	}
    
    public void placeOrder() {
		placeOrderBtn.click();
	}

}
