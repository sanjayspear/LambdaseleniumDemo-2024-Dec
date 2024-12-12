import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Demo {
	
	@Test
	public void test() {
		//WebDriver driver = WebDriverManager.chromedriver().driverVersion("131.0.6778.86").create();
		WebDriver driver = WebDriverManager.firefoxdriver().create();
		driver.get("https://www.amazon.in");
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, "Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in");
	}
}
