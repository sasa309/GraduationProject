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
	
	@FindBy(name = "email")
	List<WebElement> emails;
	
	@FindBy(css = "#form > div > div > div:nth-child(3) > div")
	WebElement registerForm;
	
	@FindBy(xpath = "//*[@id=\"form\"]/div/div/div[3]/div/form/button")
	WebElement registerBtn;
	
	public void userCanRegister(String name , String email) {
		userName.sendKeys(name);
		emails.get(1).sendKeys(email);
		
		registerBtn.click();
	}
	
	public WebElement getUserRegisterMessage() {
		return registerForm.findElement(By.tagName("h2"));
	}
	
}
