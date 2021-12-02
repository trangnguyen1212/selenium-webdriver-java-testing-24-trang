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
		driver.findElement(By.xpath("//span[@class='label' and text()='Account']")).click();
		driver.findElement(By.xpath("//*[@id='header']//*[text()='My Account']")).click();
		driver.findElement(By.xpath("//span[text()='Login']")).click();
		// Check empty email
		Assert.assertTrue(driver.findElement(By.id("advice-required-entry-email")).isDisplayed());
		// Check empty password
		Assert.assertTrue(driver.findElement(By.id("advice-required-entry-pass")).isDisplayed());
		sleepSecond(3);

	}

	// Testcase 2
	@Test
	public void TC_02_InvalidEmail() {
		// Login invalid email
		driver.findElement(By.id("email")).sendKeys("123434234@12312.123123");
		driver.findElement(By.id("pass")).sendKeys("123456");
		// click button
		WebElement loginButton = driver.findElement(By.xpath("//span[text()='Login']"));
		loginButton.click();
		// Check message
		Assert.assertTrue(driver.findElement(By.id("advice-validate-email-email")).isDisplayed());
		sleepSecond(3);

	}

	// Testcase 3
	@Test
	public void TC_03_InvalidPass() {
		// Clear data
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("pass")).clear();
		// Login invalid pass
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123");
		// click button
		WebElement loginButton = driver.findElement(By.xpath("//span[text()='Login']"));
		loginButton.click();
		// Check message
		Assert.assertTrue(driver.findElement(By.id("advice-validate-password-pass")).isDisplayed());
		sleepSecond(3);
	}

	// Testcase 4
	@Test
	public void TC_04_IncorrectEmailPass() {
		// Clear data
		driver.findElement(By.id("pass")).clear();
		// Login invalid pass
		driver.findElement(By.id("pass")).sendKeys("123123123");
		// click button
		WebElement loginButton = driver.findElement(By.xpath("//span[text()='Login']"));
		loginButton.click();
		// Check message
		Assert.assertTrue(driver.findElement(By.xpath("//*[text()='Invalid login or password.']")).isDisplayed());
		sleepSecond(3);
	}

	// Testcase 5
	@Test
	public void TC_05_CreateAccount() {
		// click button
		driver.findElement(By.xpath("//*[text()='Create an Account']")).click();
		// Input data
		driver.findElement(By.id("firstname")).sendKeys("trang");
		driver.findElement(By.id("middlename")).sendKeys("thi");
		driver.findElement(By.id("lastname")).sendKeys("nguyen");
		// create random email
		Random rd = new Random();
		for (int idx = 1000; idx <= 100000; ++idx) {
			emailAddess = "evildemon" + rd.nextInt(100000) + "@gmail.com"; 
		}
		
		driver.findElement(By.id("email_address")).clear();
		// create email
		driver.findElement(By.id("email_address")).sendKeys(emailAddess);
		driver.findElement(By.id("password")).sendKeys("trang@123456");
		driver.findElement(By.id("confirmation")).sendKeys("trang@123456");
		// click button
		driver.findElement(By.xpath("//*[@class = 'button' and @title='Register']")).click();
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
		driver.findElement(By.xpath("//span[@class='label' and text()='Account']")).click();
		driver.findElement(By.xpath("//*[@title='Log Out']")).click();
		driver.findElement(By.xpath("//span[@class='label' and text()='Account']")).click();
		driver.findElement(By.xpath("//*[@id='header']//*[text()='My Account']")).click();
		// Login valid fields
		driver.findElement(By.id("email")).sendKeys(emailAddess);
		driver.findElement(By.id("pass")).sendKeys("trang@123456");
		// click button
		driver.findElement(By.xpath("//span[text()='Login']")).click();
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
