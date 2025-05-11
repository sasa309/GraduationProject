package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginSignUpPage extends BasePage{

	public LoginSignUpPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(name = "name")
	WebElement userName;
	
	@FindBy(name = "password")
	WebElement password;
	
	@FindBy(name = "email")
	List<WebElement> emails;
	
	@FindBy(css = "#form > div > div > div:nth-child(3) > div")
	WebElement registerForm;
	
	@FindBy(xpath = "//*[@id=\"form\"]/div/div/div[3]/div/form/button")
	WebElement registerBtn;
	
	@FindBy(xpath = "//*[@id=\"form\"]/div/div/div[1]/div/form/button")
	WebElement loginBtn;
	
	@FindBy(xpath = "//*[@id=\"form\"]/div/div/div[1]/div/h2")
	public WebElement loginMessag;
	
	@FindBy(xpath = "//*[@id=\"form\"]/div/div/div[1]/div/form/p")
	public WebElement invalidLoginMessag;
	
	@FindBy(xpath = "//*[@id=\"form\"]/div/div/div[3]/div/form/p")
	public WebElement userExistMessage;
	
	public void userCanRegister(String name , String email) {
		userName.sendKeys(name);
		emails.get(1).sendKeys(email);
		
		registerBtn.click();
	}
	
	public void userCanLogin(String userEmail , String userPassword) {
		emails.get(0).sendKeys(userEmail);
		password.sendKeys(userPassword);
		
		loginBtn.click();
	}
	
	public WebElement getUserRegisterMessage() {
		return registerForm.findElement(By.tagName("h2"));
	}
	
}
