package Pages;

import java.time.LocalDate;
import java.time.temporal.IsoFields;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaymentPage extends BasePage{

	public PaymentPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath = "//*[@id=\"cart_items\"]/div/div[2]/h2")
	public WebElement paymentMessage;
	
	@FindBy(xpath = "//*[@id=\"cart_items\"]/div/div[1]/ol/li[2]")
	public WebElement paymentActivation;
	
	@FindBy(name = "name_on_card")
	public WebElement paymentCardName;
	
	@FindBy(name = "card_number")
	public WebElement paymentCardNumber;
	
	@FindBy(name = "cvc")
	public WebElement paymentCardCVC;
	
	@FindBy(name = "expiry_month")
	public WebElement paymentCardExpMonth;
	
	@FindBy(name = "expiry_year")
	public WebElement paymentCardExpYear;
	
	@FindBy(id = "submit")
	public WebElement confirmOrderBtn;
	
	@FindBy(xpath = "//*[@id=\"form\"]/div/div/div/h2/b")
	public WebElement placeOrderMessage;
	
	@FindBy(xpath = "//*[@id=\"form\"]/div/div/div/div/a")
	public WebElement continueBtn;
	
	
	public void enterValidPaymentInfo(String name ,String number, String cvc, String expMonth ,String expYear) {
		paymentCardName.sendKeys(name);
	    paymentCardNumber.sendKeys(number);
	    paymentCardCVC.sendKeys(cvc);
	    paymentCardExpMonth.sendKeys(expMonth);
	    paymentCardExpYear.sendKeys(expYear);
	}
	
	public boolean isCardExpired(String month ,String year) {
		LocalDate currentDate = LocalDate.now();
	    int currentMonth = currentDate.getMonthValue();
	    int currentYear = currentDate.getYear();

	    int cardExpMonth = Integer.parseInt(month);
	    int cardExpYear = Integer.parseInt(year);
	    
	    if (cardExpYear > currentYear) {
	        return false; 
	    } else if (cardExpYear == currentYear) {
	        if (cardExpMonth >= currentMonth) {
	            return false; 
	        }
	    }
	    return true;
	}
	
	public boolean isFieldBlank(WebElement ele) {
	    String value = ele.getDomAttribute("value");
	    return value == null || value.trim().isEmpty();
	}

	public void confirmOrder() {
		confirmOrderBtn.click();
	}
	

}
