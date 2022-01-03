package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_TextBox_TextArea {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String firstName, lastName, editFirstname, editLastname, immigrationNumber, immigrationComment;

	// Chỉ dẫn cho hàm
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		firstName = "James";
		lastName = "Doctor";
		editFirstname = "Kim";
		editLastname = "Kasandan";
		immigrationNumber = "31195853";
		immigrationComment = "Steven's\nPassport\nID";

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://opensource-demo.orangehrmlive.com/");
	}

	@Test
	public void TC_01_Add_Employee() {
		// textbox
		driver.findElement(By.id("txtUsername")).sendKeys("Admin");
		driver.findElement(By.id("txtPassword")).sendKeys("admin123");
		// button
		driver.findElement(By.id("btnLogin")).click();
		sleepSecond(3);

		// Open add Employee page or Hover
		// driver.get("https://opensource-demo.orangehrmlive.com/index.php/pim/addEmployee");
		// Hover and click target
		Actions actions = new Actions(driver);

		actions.moveToElement(driver.findElement(By.cssSelector("a#menu_pim_viewPimModule")))
				.click(driver.findElement(By.cssSelector("a[id='menu_pim_addEmployee']"))).build().perform();
		sleepSecond(5);
		// At Add Employee page: 'Add Employee' sub-menu link is displayed
		Assert.assertTrue(driver.findElement(By.cssSelector("a#menu_pim_addEmployee")).isDisplayed());

		// Enter to Firstname / Lastname
		driver.findElement(By.id("firstName")).sendKeys(firstName);
		driver.findElement(By.id("lastName")).sendKeys(lastName);

		String employeeId = driver.findElement(By.id("employeeId")).getAttribute("value");

		// click Save
		driver.findElement(By.id("btnSave")).click();
		sleepSecond(3);

		By firstNnameTextbox = By.id("personal_txtEmpFirstName");
		By lastNnameTextbox = By.id("personal_txtEmpLastName");
		By employeeIDTextbox = By.id("personal_txtEmployeeId");

		// Verify Firstname/ Lastname/ EmployeeID textbox disabled (is not enabled)
		Assert.assertFalse(driver.findElement(firstNnameTextbox).isEnabled());
		Assert.assertFalse(driver.findElement(lastNnameTextbox).isEnabled());
		Assert.assertFalse(driver.findElement(employeeIDTextbox).isEnabled());

		// Verify Firstname/ Lastname/ EmployeeID value matching with input value
		Assert.assertEquals(driver.findElement(firstNnameTextbox).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(lastNnameTextbox).getAttribute("value"), lastName);
		Assert.assertEquals(driver.findElement(employeeIDTextbox).getAttribute("value"), employeeId);

		// Click edit button
		driver.findElement(By.cssSelector("input#btnSave")).click();

		// Verify Firstname/ Lastname/ EmployeeID textbox enabled
		Assert.assertTrue(driver.findElement(firstNnameTextbox).isEnabled());
		Assert.assertTrue(driver.findElement(lastNnameTextbox).isEnabled());
		Assert.assertTrue(driver.findElement(employeeIDTextbox).isEnabled());

		// Edit 'Firstname/ Lastname'
		driver.findElement(firstNnameTextbox).clear();
		driver.findElement(firstNnameTextbox).sendKeys(editFirstname);
		driver.findElement(lastNnameTextbox).clear();
		driver.findElement(lastNnameTextbox).sendKeys(editLastname);

		// click Save button
		driver.findElement(By.cssSelector("input#btnSave")).click();
		sleepSecond(3);

		// Verify Firstname/ Lastname/ EmployeeID textbox disabled (is not enabled)
		Assert.assertFalse(driver.findElement(firstNnameTextbox).isEnabled());
		Assert.assertFalse(driver.findElement(lastNnameTextbox).isEnabled());
		Assert.assertFalse(driver.findElement(employeeIDTextbox).isEnabled());

		// Click to Immigration tab
		driver.findElement(By.xpath("//a[text()='Immigration']")).click();

		// Click to 'Add' button
		driver.findElement(By.cssSelector("input#btnAdd")).click();

		// Enter to Immigration number textbox
		driver.findElement(By.id("immigration_number")).sendKeys(immigrationNumber);
		driver.findElement(By.cssSelector("textarea#immigration_comments")).sendKeys(immigrationComment);
		sleepSecond(3);

		driver.findElement(By.id("btnSave")).click();
		sleepSecond(3);

		driver.findElement(By.xpath("//td[@class='document']/a[text()='Passport']")).click();

		// Verify
		Assert.assertEquals(driver.findElement(By.id("immigration_number")).getAttribute("value"), immigrationNumber);
		Assert.assertEquals(driver.findElement(By.cssSelector("textarea#immigration_comments")).getAttribute("value"),
				immigrationComment);

	}

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