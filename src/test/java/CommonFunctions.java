package testgradle;  
import java.util.concurrent.TimeUnit;  
import org.openqa.selenium.WebDriver;  
import org.openqa.selenium.WebElement;  
import org.openqa.selenium.support.ui.ExpectedConditions;  
import org.openqa.selenium.support.ui.WebDriverWait;  
public class CommonFunctions extends TestBase{  
	
    public void waitForElement(WebDriver driver, int timeOutInSeconds, WebElement element) {  
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);  
        wait.until(ExpectedConditions.visibilityOf(element));  
    }
    public void verify_EnterValue(WebElement element,String value,String elementName) {  
        if(element.isDisplayed()) {  
            ReportLog("[CommonFunctions] "+elementName+" is displayed in the browser");  
            if (element.isEnabled()) {  
                ReportLog("[CommonFunctions] "+elementName+" is enabled in the browser");  
                element.sendKeys(value);  
                ReportLog("[CommonFunctions] "+value +" is entered in the "+elementName+" field");  
            } else {  
                ReportResults("FAIL","[CommonFunctions] "+elementName+" is enabled in the browser");  
            }  
        }else {  
            ReportResults("FAIL","[CommonFunctions] "+elementName+" is displayed in the browser");  
        }  
    }
    public void verify_elementClickable(WebElement element,String elementName,int waitTime) throws InterruptedException {  
        if(element.isDisplayed()) {  
            ReportLog("[CommonFunctions] "+elementName+" is displayed in the browser");  
            if (element.isEnabled()) {  
                ReportLog("[CommonFunctions] "+elementName+" is enabled in the browser");  
                element.click();  
                TimeUnit.SECONDS.sleep(waitTime);  
                ReportLog("[CommonFunctions] Clicked on th "+elementName+" field");  
            } else {  
                ReportResults("FAIL","[CommonFunctions] "+elementName+" is not enabled in the browser");  
            }  
        }else {  
            ReportResults("FAIL","[CommonFunctions] "+elementName+" is not displayed in the browser");  
        }  
    }  
}
