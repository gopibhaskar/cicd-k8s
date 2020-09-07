package testgradle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SpringAppTest {
	
	@Test
	public void appTitleTest() {
		
		System.setProperty("webdriver.chrome.driver", "C:/Users/SG0304153/OneDrive - Sabre/Desktop/Kumar/chromedriver_win32/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://34.123.6.156:8383/spring-music/");
		String title= driver.getTitle();
		System.out.println("App Title is : " +title);
		Assert.assertEquals(title, "Spring Music");
		driver.quit();
	}

}
