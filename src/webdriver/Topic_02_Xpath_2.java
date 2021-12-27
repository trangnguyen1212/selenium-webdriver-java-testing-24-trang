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
	private String firstname, lastname, middlename, emailAddess, passwword, nonExistedEmail;
	String projectPath = System.getProperty("user.dir");
	// Khai bao locator, element
	// login form
	By emailTextbox = By.id("email");
	By passTextbox = By.id("pass");
	// RegisterForm
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
	public void TC_04_CreateAccount() {
		// click button
		driver.findElement(createAccountLink).click();
		firstname = "trang";
		middlename = "thi";
		lastname = "nguyen";
		// create random email
		emailAddess = "evildemon" + getRandomNumber() + "@gmail.com";
		nonExistedEmail = "evildemon" + getRandomNumber() + "@hotmail.net";
		passwword = "trang@123456";
			// Input data
		driver.findElement(firstnameTextBox).sendKeys(firstname);
		driver.findElement(middlenameTextBox).sendKeys(middlename);
		driver.findElement(lastnameTextBox).sendKeys(lastname);

		driver.findElement(emailAddressCreate).clear();
		// create email
		driver.findElement(emailAddressCreate).sendKeys(emailAddess);
		driver.findElement(passCreate).sendKeys(passwword);
		driver.findElement(confirmpassTextBox).sendKeys(passwword);
		// click button
		driver.findElement(registerButton).click();
		// verify message
		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(),
				"Thank you for registering with Main Website Store.");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.welcome-msg strong")).getText(),
				"Hello, " + firstname + " " + middlename + " " + lastname + "!");
		String contactInformation = driver
				.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p"))
				.getText();
		Assert.assertTrue(contactInformation.contains(firstname + " " + middlename + " " + lastname));
		Assert.assertTrue(contactInformation.contains(emailAddess));

		// click button LogOut
		driver.findElement(accountLink).click();
		driver.findElement(By.xpath("//*[@title='Log Out']")).click();
		Assert.assertTrue(driver.findElement(By.cssSelector("div.page-title img[src$='logo.png']")).isDisplayed());
		sleepSecond(2);

	}

	// Testcase 5
	@Test
	public void TC_05_IncorrectEmailPass() {
		driver.findElement(accountLink).click();
		driver.findElement(By.xpath("//*[@title='Log In']")).click();
		// Existed email + Incorrect Password -> unsuccessfull
		driver.findElement(emailTextbox).sendKeys(emailAddess);
		driver.findElement(passTextbox).sendKeys("123456");
		driver.findElement(loginButton).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("li.error-msg span")).getText(),
				"Invalid login or password.");
		// Clear data
		driver.findElement(emailTextbox).clear();
		driver.findElement(passTextbox).clear();
		// Non existed email + correct passwword -> unsuccessfull
		driver.findElement(emailTextbox).sendKeys(nonExistedEmail);
		driver.findElement(passTextbox).sendKeys(passwword);
		driver.findElement(loginButton).click();
		// Check message
		Assert.assertEquals(driver.findElement(By.cssSelector("li.error-msg span")).getText(),
				"Invalid login or password.");
		sleepSecond(2);
	}

	// Test case 6
	@Test
	public void TC_06_Login_ValidEmailandPass() {
		driver.findElement(emailTextbox).clear();
		driver.findElement(passTextbox).clear();
		// Login valid fields
		driver.findElement(emailTextbox).sendKeys(emailAddess);
		driver.findElement(passTextbox).sendKeys(passwword);
		// click button
		driver.findElement(loginButton).click();
		// Verify display after login success
		Assert.assertEquals(driver.findElement(By.cssSelector("div.page-title>h1")).getText(), "MY DASHBOARD");
		Assert.assertEquals(driver.findElement(By.cssSelector("div.welcome-msg strong")).getText(),
				"Hello, " + firstname + " " + middlename + " " + lastname + "!");
		String contactInformation = driver
				.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p"))
				.getText();
		Assert.assertTrue(contactInformation.contains(firstname + " " + middlename + " " + lastname));
		Assert.assertTrue(contactInformation.contains(emailAddess));
	}

	public int getRandomNumber() {
		Random rd = new Random();
		return rd.nextInt(100000);
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
