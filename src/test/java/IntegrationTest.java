package testgradle;
import java.io.IOException;  
import org.testng.annotations.Test;  
 
public class IntegrationTest extends TestBase{  
    PageObject pageObject = new PageObject();  
    CommonFunctions common = new CommonFunctions();
    @Test  
    public void springMusicScenarios() throws InterruptedException, IOException {  
                  
        pageObject.navigateToUrl(common.getPropertyData("url"));  
        pageObject.addAlbum();  
        pageObject.editAlbum();  
        pageObject.deleteAlbum();  
          
    }  
      
}