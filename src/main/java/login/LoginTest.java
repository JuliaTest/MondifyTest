package login;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LoginTest {
	
	WebDriver driver;
	
	@Before
	public void prepareWebDriver() {
		System.setProperty("webdriver.chrome.driver",
				"c:/temp/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();		
	}
	
	@After
	public void shutdownBrowser() throws InterruptedException {
		Thread.sleep(5000);
		driver.quit();
	}

	private void goToLoginEntry() throws InterruptedException {	
		driver.get("http://mondify.com/");
		Thread.sleep(5000);
        WebDriver popupClose = driver.switchTo().frame("targetedPopupFrame");
		WebElement buttonDismiss = popupClose.findElement(By.id("closePopup"));
		buttonDismiss.click();
		Thread.sleep(1000);
		WebElement loginLink = driver.findElement(By.xpath("//a[@title='Log In']")); 
		loginLink.click();
		Thread.sleep(1000);
	}
		
	//authorization of registered user, input correct parameters
		
	private void inputRegisteredEmail() {
		WebElement inputEmail = driver.findElement(By.name("login[username]"));
		inputEmail.sendKeys("juliyavt85@gmail.com");
	}
		
	private void inputCorrectPassword() throws InterruptedException {
		WebElement inputPassword = driver.findElement(By.name("login[password]")); 
		inputPassword.sendKeys("asdfzxcv");
		Thread.sleep(1000);
	}
	
	
	private void clickLoginButton() {
		WebElement clickLogin = driver.findElement(By.name("send"));
		clickLogin.click();
	}
	
	//incorrect e-mail
	private void inputUnregisteredEmail() {
		WebElement inputEmail = driver.findElement(By.name("login[username]"));
		inputEmail.sendKeys("email1@gmail.com");
	}
	
	//incorrect password
	private void inputIncorrectPassword() throws InterruptedException {
		WebElement inputPassword = driver.findElement(By.name("login[password]")); 
		inputPassword.sendKeys("123456");
		Thread.sleep(1000);
	}
	
	//login with correct e-mail
	@Test
	public void loginWithCorrectParamethers() throws InterruptedException {
		 System.out.println("verifying login with correct paramethers "); 
		 goToLoginEntry();
		 inputRegisteredEmail();
		 inputCorrectPassword();
		 clickLoginButton();
		 assertEquals("Dashboard page was expected.", "My Account", driver.getTitle());
	}
	
	//login with incorrect e-mail
	@Test
	public void loginWithIncorrectEmail() throws InterruptedException {
		System.out.println("verifying login with incorrect email "); 
		goToLoginEntry();
		inputUnregisteredEmail();
		inputCorrectPassword();
		clickLoginButton();
		driver.findElement(By.xpath("//span[text()='Invalid login or password.']"));
	}
	
	//login with incorrect password
	@Test
	public void loginWithIncorrectPassword() throws InterruptedException {
		System.out.println("verifying login with incorrect password ");
		goToLoginEntry();
		inputRegisteredEmail();
		inputIncorrectPassword(); 
		clickLoginButton();
		driver.findElement(By.xpath("//span[text()='Invalid login or password.']"));
	}	
	
	//login without password
	@Test
	public void loginWithoutPassword() throws InterruptedException {
		System.out.println("verifying login without enter password ");
		goToLoginEntry();
		inputRegisteredEmail();
		clickLoginButton();
		driver.findElement(By.id("advice-required-entry-pass"));
	}
	
	//login without input email
	@Test
	public void loginWithoutEmail() throws InterruptedException {
		System.out.println("verifying login without enter email ");
		goToLoginEntry();
		inputCorrectPassword();
		clickLoginButton();
		driver.findElement(By.id("advice-required-entry-email"));
	}
}

