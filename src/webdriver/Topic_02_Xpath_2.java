package webdriver;

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
		WebElement accountLink = driver.findElement(By.xpath("//span[@class='label' and text()='Account']"));
		accountLink.click();
		WebElement myAccountLink = driver.findElement(By.xpath("//*[@id='header']//*[text()='My Account']"));
		myAccountLink.click();
		WebElement loginButton = driver.findElement(By.xpath("//span[text()='Login']"));
		loginButton.click();
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
