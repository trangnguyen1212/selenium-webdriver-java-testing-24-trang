package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_Xpath_2 {
	WebDriver driver;
	private int randomInt;
	private String emailAddess;
	String projectPath = System.getProperty("user.dir");
	// Khai bao locator, element
	// login form
	By emailTextbox = By.id("email");
	By passTextbox = By.id("pass");
	//RegisterForm
	By emailAddressCreate = By.id("email_address");
	By passCreate = By.id("password");
	By confirmpassTextBox = By.id("confirmation");
	By firstnameTextBox = By.id("firstname");
	By middlenameTextBox = By.id("middlename");
	By lastnameTextBox = By.id("lastname");
	By emptyEmailMessage = By.id("advice-required-entry-email");
	By emptyPassMessage = By.id("advice-required-entry-pass");
	By accountLink = By.xpath("//span[@class='label' and text()='Account']");
	By myAccountLink = By.xpath("//*[@id='header']//*[text()='My Account']");
	By createAccountLink = By.xpath("//*[text()='Create an Account']");
	By loginButton = By.xpath("//span[text()='Login']");
	By registerButton = By.xpath("//form[@id='form-validate']//button[@title='Register']");

	// Chỉ dẫn cho hàm
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://live.techpanda.org/");
	}

	// Testcase 1
	@Test
	public void TC_01_emptyEmailPassword() {
		// click button
		driver.findElement(accountLink).click();
		driver.findElement(myAccountLink).click();
		driver.findElement(loginButton).click();
		// Check empty email
		Assert.assertEquals(driver.findElement(emptyEmailMessage).getText(), "This is a required field.");
		// Check empty password
		Assert.assertEquals(driver.findElement(emptyPassMessage).getText(), "This is a required field.");
		sleepSecond(3);

	}

	// Testcase 2
	@Test
	public void TC_02_InvalidEmail() {
		// Login invalid email
		driver.findElement(emailTextbox).sendKeys("123434234@12312.123123");
		driver.findElement(passTextbox).sendKeys("123456");
		// click button
		driver.findElement(loginButton).click();
		// Check message
		Assert.assertTrue(driver.findElement(By.id("advice-validate-email-email")).isDisplayed());
		sleepSecond(3);

	}

	// Testcase 3
	@Test
	public void TC_03_InvalidPass() {
		// Clear data
		driver.findElement(emailTextbox).clear();
		driver.findElement(passTextbox).clear();
		// Login invalid pass
		driver.findElement(emailTextbox).sendKeys("automation@gmail.com");
		driver.findElement(passTextbox).sendKeys("123");
		// click button
		driver.findElement(loginButton).click();
		// Check message
		Assert.assertTrue(driver.findElement(By.id("advice-validate-password-pass")).isDisplayed());
		sleepSecond(3);
	}

	// Testcase 4
	@Test
	public void TC_04_IncorrectEmailPass() {
		// Clear data
		driver.findElement(passTextbox).clear();
		// Login invalid pass
		driver.findElement(passTextbox).sendKeys("123123123");
		// click button
		driver.findElement(loginButton).click();
		// Check message
		Assert.assertTrue(driver.findElement(By.xpath("//*[text()='Invalid login or password.']")).isDisplayed());
		sleepSecond(3);
	}

	// Testcase 5
	@Test
	public void TC_05_CreateAccount() {
		// click button
		driver.findElement(createAccountLink).click();
		// Input data
		driver.findElement(firstnameTextBox).sendKeys("trang");
		driver.findElement(middlenameTextBox).sendKeys("thi");
		driver.findElement(lastnameTextBox).sendKeys("nguyen");
		// create random email
		Random rd = new Random();
		for (int idx = 1000; idx <= 100000; ++idx) {
			emailAddess = "evildemon" + rd.nextInt(100000) + "@gmail.com";
		}

		driver.findElement(emailAddressCreate).clear();
		// create email
		driver.findElement(emailAddressCreate).sendKeys(emailAddess);
		driver.findElement(passCreate).sendKeys("trang@123456");
		driver.findElement(confirmpassTextBox).sendKeys("trang@123456");
		// click button
		driver.findElement(registerButton).click();
		// Check message
		Assert.assertTrue(
				driver.findElement(By.xpath("//*[text()='Thank you for registering with Main Website Store.']"))
						.isDisplayed());
		sleepSecond(3);
	}

	// Test case 6
	@Test
	public void TC_06_Login() {
		// click button
		driver.findElement(accountLink).click();
		driver.findElement(By.xpath("//*[@title='Log Out']")).click();
		driver.findElement(accountLink).click();
		driver.findElement(myAccountLink).click();
		// Login valid fields
		driver.findElement(emailTextbox).sendKeys(emailAddess);
		driver.findElement(passTextbox).sendKeys("trang@123456");
		// click button
		driver.findElement(loginButton).click();
		// Check message
		Assert.assertTrue(driver.findElement(By.xpath("//*[text() = 'My Dashboard']")).isDisplayed());
		sleepSecond(3);
	}

	// sleepSecond
	public void sleepSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public void afterClass() {

		driver.quit();
	}
}
