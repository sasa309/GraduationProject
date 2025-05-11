package Components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Pages.BasePage;

public class Header extends BasePage{

    public Header(WebDriver driver) {
        super(driver);
    }
    
    @FindBy(id = "header")
    public WebElement root;
    
    @FindBy(linkText = "Home")
    public WebElement homeLink;
    
    @FindBy(partialLinkText = "Products")
    public WebElement productsLink;
    
    @FindBy(linkText = "Cart")
    public WebElement cartLink;
    
    @FindBy(linkText = "Signup / Login")
    public WebElement signupLoginLink;
    
    @FindBy(linkText = "Contact Us")
    public WebElement contactUsLink;
    
    @FindBy(partialLinkText = "Logout")
    public WebElement logoutBtn;
    
    @FindBy(css = "#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(10) > a > b")
    public WebElement loggedInUser;
    
    public void navigateTo(WebElement linkText) {
        linkText.click();
    }
    
    public void userLogout() {
    	logoutBtn.click();
	}
}
