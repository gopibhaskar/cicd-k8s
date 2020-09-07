package testgradle;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Properties;  
import org.openqa.selenium.WebDriver;  
import org.openqa.selenium.chrome.ChromeDriver;  
import org.openqa.selenium.firefox.FirefoxDriver;  
import org.testng.Assert;  
import org.testng.Reporter;  
import org.testng.annotations.AfterClass;  
import org.testng.annotations.BeforeClass;  
import org.testng.annotations.Parameters; 


public class TestBase {  
    public static WebDriver driver;
    //@Parameters("browser") 
    @Parameters("browser")
    @BeforeClass  
    // Passing Browser parameter from seleniumAssignment xml  
    public void beforeClass(String browser) {  
        if (browser.equalsIgnoreCase("firefox")) {  
            System.setProperty("webdriver.gecko.driver",  
                     "C:/UsersSG0304153/OneDrive - Sabre/Desktop/Kumar/geckodriver-v0.27.0-win64/geckodriver.exe");  
            driver = new FirefoxDriver();  
            ReportLog("[TestBase] Firefox Driver Initialized");  
          
        } else if (browser.equalsIgnoreCase("chrome")) { 
            System.setProperty("webdriver.chrome.driver",  
                    System.getProperty("user.dir") + "C:/Users/SG0304153/OneDrive - Sabre/Desktop/Kumar/chromedriver_win32/chromedriver.exe");  
            driver = new ChromeDriver();  
            driver.manage().window().maximize();  
            System.out.println("driver ::"+driver);  
            System.out.println("Chrome driver initialized");  
            ReportLog("[TestBase] Chrome Driver Initialized");  
        }  
    }
    @AfterClass  
    public void afterClass() {  
        driver.quit();  
        ReportLog("[TestBase] Closing the Driver");  
    }  
      
    public static void ReportLog(String Message) {  
        Date Date = new Date();  
        SimpleDateFormat startDate = new SimpleDateFormat("MM-dd-YYYY HH:mm:ss");  
        String time= startDate.format(Date);  
        Reporter.log("["+time+"]"+" "+Message, true);  
    }
    public void ReportResults(String result, String message) {  
        int iresult = 0;  
        if(result.equalsIgnoreCase("PASS")) {  
            iresult=1;  
        }else if(result.equalsIgnoreCase("FAIL")) {  
            iresult=2;  
        }  
        switch(iresult) {  
            case 1: ReportLog("PASS : "+ message+"\n");  
                    break;  
            case 2: ReportLog("FAIL : "+ message+"\n");      
                    Assert.fail("FAIL : "+message);                      
                    break;  
        }  
    }  
      
    public String getPropertyData(String key) throws IOException {  
        Properties property = new Properties();  
        FileInputStream input = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/config.properties");          
        property.load(input);  
        return property.getProperty(key);  
    }  
}