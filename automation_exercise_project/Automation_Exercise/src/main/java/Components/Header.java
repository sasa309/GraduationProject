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
    
    @FindBy(partialLinkText = "Logged in as")
    public WebElement loggedInMessage;
    
    public void clickLink(WebElement linkText) {
        linkText.click();
    }
}
