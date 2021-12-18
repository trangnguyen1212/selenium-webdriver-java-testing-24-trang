package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic06_TestBrowser {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	// Chỉ dẫn cho hàm
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://live.techpanda.org/");
	}

	@Test
	public void TC_01_ValidateCurrentUrl() {
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/");
	}

	@Test
	public void TC_01_ValidatePageUrl() {
		// Login Page
		driver.findElement(By.xpath("//div[@class='footer']//child::div[4]//ul//*[text() = 'My Account']")).click();
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");
		// Register page
		driver.findElement(By.xpath("//*[text()='Create an Account']")).click();
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");
	}

	@Test
	public void TC_02_ValidateTitle() {
		// Title Customer Login
		driver.findElement(By.xpath("//div[@class='footer']//child::div[4]//ul//*[text() = 'My Account']")).click();
		Assert.assertEquals(driver.getTitle(), "Customer Login");
		// Title Create New Customer
		driver.findElement(By.xpath("//*[text()='Create an Account']")).click();
		;
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");

	}

	@Test
	public void TC_03_Navigate() {
		// Verify Register Page
		driver.findElement(By.xpath("//div[@class='footer']//child::div[4]//ul//*[text() = 'My Account']")).click();
		driver.findElement(By.xpath("//*[text()='Create an Account']")).click();
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");
		// Navigate - back to Login Page
		driver.navigate().back();
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");
		// Navigate - forward to Register Page
		driver.navigate().forward();
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");

	}

	@Test
	public void TC_04_PageSource() {
		// Verify text Login Page
		driver.findElement(By.xpath("//div[@class='footer']//child::div[4]//ul//*[text() = 'My Account']")).click();
		Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));
		// Verify text Register Page
		driver.findElement(By.xpath("//*[text()='Create an Account']")).click();
		Assert.assertTrue(driver.getPageSource().contains("Create an Account"));

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
