package Tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class BaseTest {
	String baseUrl = "https://www.automationexercise.com/";
	WebDriver driver = new ChromeDriver();
//    ChromeOptions options;
	
	 @BeforeTest
	 public void openBrowser() {
        // إعداد خيارات المتصفح
//        options = new ChromeOptions();
//        options.addArguments("--disable-extensions");  // تعطيل الإضافات
//        options.addArguments("--disable-popup-blocking");  // تعطيل حظر النوافذ المنبثقة
//        options.addArguments("disable-infobars");  // تعطيل إشعارات "Chrome is being controlled"
//        options.addArguments("--start-maximized");  // فتح المتصفح بحجم كامل
//
//        // تهيئة WebDriver مع الخيارات
//        driver = new ChromeDriver(options);

        // الانتقال إلى الصفحة الرئيسية
        driver.navigate().to(baseUrl);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	 }
	
	@BeforeMethod
	public void navigateToHome() {
	    driver.get(baseUrl); 
	}
	
	@AfterTest
	public void afterTest() {
		driver.close();
	}
}
