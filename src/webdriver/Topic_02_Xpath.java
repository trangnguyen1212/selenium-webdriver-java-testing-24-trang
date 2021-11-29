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

public class Topic_02_Xpath {
	private WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	// Chỉ dẫn cho hàm
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
	}

	// Testcase 1
	@Test
	public void TC_01_ErrorMessage() {
		// click button
		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
		loginButton.click();
		// Check error message firstname
		Assert.assertTrue(driver.findElement(By.id("txtFirstname-error")).isDisplayed());
		// Check error message email
		Assert.assertTrue(driver.findElement(By.id("txtEmail-error")).isDisplayed());
		// Check error message confirm email
		Assert.assertTrue(driver.findElement(By.id("txtCEmail-error")).isDisplayed());
		// Check error message password
		Assert.assertTrue(driver.findElement(By.id("txtPassword-error")).isDisplayed());
		// Check error message confirm password
		Assert.assertTrue(driver.findElement(By.id("txtCPassword-error")).isDisplayed());
		// Check error message phone
		Assert.assertTrue(driver.findElement(By.id("txtPhone-error")).isDisplayed());
		sleepSecond(3);

	}

	// Testcase 2 , 3
	@Test
	public void TC_02_InvalidEmail() {
		// Login valid fields
		driver.findElement(By.id("txtFirstname")).sendKeys("Trang Nguyen");
		// Login invalid email
		driver.findElement(By.id("txtEmail")).sendKeys("345@.flf!");
		driver.findElement(By.id("txtCEmail")).sendKeys("345@.flf!");
		// Login valid fields
		driver.findElement(By.id("txtPassword")).sendKeys("12345Trang@");
		driver.findElement(By.id("txtCPassword")).sendKeys("12345Trang@");
		driver.findElement(By.id("txtPhone")).sendKeys("0932748303");
		// click button
		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
		loginButton.click();
		// Check error message email
		Assert.assertTrue(driver.findElement(By.id("txtEmail-error")).isDisplayed());
		// Check error message confirm email
		Assert.assertTrue(driver.findElement(By.id("txtCEmail-error")).isDisplayed());
		sleepSecond(3);

	}

	// Testcase 4
	@Test
	public void TC_04_InvalidPassword() {
		// Login valid fields
		driver.findElement(By.id("txtEmail")).clear();
		driver.findElement(By.id("txtEmail")).sendKeys("trangntt121293@gmail.com");
		driver.findElement(By.id("txtCEmail")).clear();
		driver.findElement(By.id("txtCEmail")).sendKeys("trangntt121293@gmail.com");
		// Login valid fields
		driver.findElement(By.id("txtPassword")).clear();
		driver.findElement(By.id("txtPassword")).sendKeys("12345");
		driver.findElement(By.id("txtCPassword")).clear();
		driver.findElement(By.id("txtCPassword")).sendKeys("12345");
		// click button
		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
		loginButton.click();
		// Check error message password
		Assert.assertTrue(driver.findElement(By.id("txtPassword-error")).isDisplayed());
		sleepSecond(3);
	}

	// Testcase 5
	@Test
	public void TC_05_InvalidConfirmPassword() {
		// Login valid fields
		driver.findElement(By.id("txtPassword")).clear();
		driver.findElement(By.id("txtPassword")).sendKeys("12345Trang@");
		driver.findElement(By.id("txtCPassword")).clear();
		driver.findElement(By.id("txtCPassword")).sendKeys("12345");
		// click button
		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
		loginButton.click();
		// Check error message confirm password
		Assert.assertTrue(driver.findElement(By.id("txtCPassword-error")).isDisplayed());
		sleepSecond(3);
	}

	// Testcase 6
	@Test
	public void TC_06_InvalidTelephone() {
		// Login valid fields
		driver.findElement(By.id("txtCPassword")).clear();
		driver.findElement(By.id("txtCPassword")).sendKeys("12345Trang@");
		driver.findElement(By.id("txtPhone")).clear();
		driver.findElement(By.id("txtPhone")).sendKeys("09327");
		// click button
		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
		loginButton.click();
		// Check error message confirm password
		Assert.assertTrue(driver.findElement(By.id("txtPhone-error")).isDisplayed());
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
