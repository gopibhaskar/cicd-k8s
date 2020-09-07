package testgradle;  
import java.io.IOException;  
import java.util.List;  
import java.util.concurrent.TimeUnit;  
import org.openqa.selenium.By;  
import org.openqa.selenium.JavascriptExecutor;  
import org.openqa.selenium.WebElement;  
import org.testng.Assert; 
import testgradle.CommonFunctions;
  
public class PageObject extends TestBase{  
    CommonFunctions common;  
      
    {  
        common = new CommonFunctions();  
    }
    public void navigateToUrl(String url) throws InterruptedException {  
        driver.get(url);  
        ReportLog("[PageObject] Launching the URL :: "+url);  
        TimeUnit.SECONDS.sleep(5);      
        WebElement headerText = driver.findElement(By.xpath("(//*[contains(text(),'Spring Music')])[2] "));  
        String headerTextValue = headerText.getText();  
        ReportLog("[PageObject] Header Text is :: "+headerTextValue);  
        Assert.assertEquals(headerTextValue, "Spring Music");  
        compareStringValues(headerTextValue, "Spring Music", "Header Value Validated Successfully");  
    }
    public void addAlbum() throws InterruptedException, IOException {  
        
        WebElement addAlbum = driver.findElement(By.xpath("//*[@ng-click='addAlbum()']"));  
        common.verify_elementClickable(addAlbum, "Add Album", 5);  
        TimeUnit.SECONDS.sleep(2);  
        WebElement dialogHeader = driver.findElement(By.xpath("//*[contains(text(),'Add an album')] "));  
        ReportLog("[PageObject] Header Text is :: "+dialogHeader.getText());  
        Assert.assertEquals(dialogHeader.getText(), "Add an album");  
        compareStringValues(dialogHeader.getText(), "Add an album", "Add Dialog Header Value Validated Successfully");  
        enterAlbumValues(common.getPropertyData("albumName"),  
                common.getPropertyData("artistName"),  
                common.getPropertyData("year"),  
                common.getPropertyData("genre"));
        this.dialogButtonClick("Ok");  
        TimeUnit.SECONDS.sleep(2);  
        popUpMessageValidation(common.getPropertyData("savedMsg"));  
        TimeUnit.SECONDS.sleep(2);  
        validateAlbumValues(common.getPropertyData("albumName"),  
                common.getPropertyData("artistName"),  
                common.getPropertyData("year"),  
                common.getPropertyData("genre"));  
        ReportResults("PASS","New Album has been Added and Validated Successfully");  
    }
    public void editAlbum() throws InterruptedException, IOException {  
        
        this.settingsButtonClick(common.getPropertyData("albumName"));  
        JavascriptExecutor js = (JavascriptExecutor) driver;  
        js.executeScript("window.scrollBy(0,1000)");  
        WebElement editAlbum = driver.findElement(By.xpath("(//*[contains(text(),'"+common.getPropertyData("albumName")+"')]/../../../../../div/ul/li/a)[1]"));  
        common.verify_elementClickable(editAlbum, "Edit Album", 5);          
        TimeUnit.SECONDS.sleep(3);  
          
        WebElement dialogHeader = driver.findElement(By.xpath("//*[contains(text(),'Edit an album')] "));  
        ReportLog("[PageObject] Header Text is :: "+dialogHeader.getText());  
        Assert.assertEquals(dialogHeader.getText(), "Edit an album");
        enterAlbumValues(common.getPropertyData("editAlbumName"),  
                common.getPropertyData("editArtistName"),  
                common.getPropertyData("editYear"),  
                common.getPropertyData("editGenre"));  
        this.dialogButtonClick("Ok");  
        TimeUnit.SECONDS.sleep(2);  
        popUpMessageValidation(common.getPropertyData("savedMsg"));  
              
        validateAlbumValues(common.getPropertyData("editAlbumName"),  
                common.getPropertyData("editArtistName"),  
                common.getPropertyData("editYear"),  
                common.getPropertyData("editGenre"));  
          
        ReportResults("PASS","Album has been Edited and Valdiated Successfully");  
    }
    public void deleteAlbum() throws InterruptedException, IOException {  
        this.settingsButtonClick(common.getPropertyData("editAlbumName"));  
        JavascriptExecutor js = (JavascriptExecutor) driver;  
        js.executeScript("window.scrollBy(0,1000)");  
        WebElement deleteAlbum = driver.findElement(By.xpath("(//*[contains(text(),'"+common.getPropertyData("editAlbumName")+"')]/../../../../../div/ul/li/a)[2]"));  
        common.verify_elementClickable(deleteAlbum, "Delete Album", 5);          
        TimeUnit.SECONDS.sleep(2);  
        popUpMessageValidation(common.getPropertyData("deleteMsg"));  
        ReportResults("PASS","Album has been Deleted and Validated Successfully");  
    }
    private void enterAlbumValues(String albumName,String artistName, String yearValue, String genreName) throws InterruptedException {  
        
        WebElement album = driver.findElement(By.xpath("//*[@name='albumForm']/div[1]/input"));  
        album.clear();  
        common.verify_EnterValue(album, albumName, "Album Name");  
        TimeUnit.SECONDS.sleep(1);  
        WebElement artist = driver.findElement(By.xpath("//*[@name='albumForm']/div[2]/input"));  
        artist.clear();  
        common.verify_EnterValue(artist, artistName, "Artist Name");
        TimeUnit.SECONDS.sleep(1);  
        WebElement year = driver.findElement(By.xpath("//*[@name='albumForm']/div[3]/input"));  
        year.clear();  
        common.verify_EnterValue(year, yearValue, "Year");  
        TimeUnit.SECONDS.sleep(1);  
        WebElement genre = driver.findElement(By.xpath("//*[@name='albumForm']/div[4]/input"));  
        genre.clear();  
        common.verify_EnterValue(genre, genreName, "Genre");  
                      
    }
    private void settingsButtonClick(String albumName) throws InterruptedException {  
        WebElement settingButton = driver.findElement(By.xpath("//*[contains(text(),'"+albumName+"')]/../../../../../div/a "));  
        common.verify_elementClickable(settingButton, "Settings Button", 2);  
    }
    private void popUpMessageValidation(String expectedMessage) {  
        WebElement popUpMessage = driver.findElement(By.xpath("//*[@class='alert alert-success']//p"));  
        String actualPopupMessage = popUpMessage.getText();  
        ReportLog("[PageObject] Pop Up message displayed is :: "+actualPopupMessage);  
        Assert.assertEquals(actualPopupMessage, expectedMessage);  
        compareStringValues(expectedMessage, actualPopupMessage, "PopUp Message Validated Sucessfully");  
    }
    private void validateAlbumValues(String albumName,String artistName, String yearValue, String genreName) {  
        List<WebElement> priceslist = driver.findElements(By.xpath("//*[@src='albumsView']/div"));  
          
        WebElement uiAlbumName = driver.findElement(By.xpath("(//*[@src='albumsView']/div["+priceslist.size()+"]/div/div/h4/div/span/span/span)[1]"));          
        ReportLog("[PageObject] Album Name in application is :: "+uiAlbumName.getText());  
        Assert.assertEquals(uiAlbumName.getText(), albumName);  
        compareStringValues(albumName, uiAlbumName.getText(), "Album Value Validated Successfully");
        WebElement uiArtistName = driver.findElement(By.xpath("(//*[@src='albumsView']/div["+priceslist.size()+"]/div/div/h4/div/span/span/span)[2]"));          
        ReportLog("[PageObject] Artist Name in application is :: "+uiArtistName.getText());  
        Assert.assertEquals(uiArtistName.getText(), artistName);  
        compareStringValues(artistName, uiArtistName.getText(), "Artist Value Validated Successfully");  
          
        WebElement uiYear = driver.findElement(By.xpath("(//*[@src='albumsView']/div["+priceslist.size()+"]/div/div/h5/div/span/span/span)[1]"));          
        ReportLog("[PageObject] Year value in application is :: "+uiYear.getText());  
        Assert.assertEquals(uiYear.getText(), yearValue);  
        compareStringValues(yearValue, uiYear.getText(), "Year Value Validated Successfully");
        WebElement uiGenreName = driver.findElement(By.xpath("(//*[@src='albumsView']/div["+priceslist.size()+"]/div/div/h5/div/span/span/span)[2]"));          
        ReportLog("[PageObject] Album Name is application is :: "+uiGenreName.getText());  
        Assert.assertEquals(uiGenreName.getText(), genreName);  
        compareStringValues(genreName, uiGenreName.getText(), "Genre Value Validated Successfully");  
    }
    private void dialogButtonClick(String buttonName) throws InterruptedException {  
        if (buttonName.equals("Ok")) {  
            WebElement OkButton = driver.findElement(By.xpath("//*[@ng-click='ok()']"));  
            common.verify_elementClickable(OkButton, "OK", 2);  
        } else {  
            WebElement cancelButton = driver.findElement(By.xpath("//*[@ng-click='cancel()']"));  
            common.verify_elementClickable(cancelButton, "Cancel", 2);  
        }  
    }
    private void compareStringValues(String expectedValue, String actualValue, String message) {  
        if(expectedValue.equalsIgnoreCase(actualValue)) {  
            ReportResults("PASS","[PageObject] "+message);      
              
        }else {  
            ReportResults("FAIL","[PageObject] Expected Header Value is :: "+expectedValue+" and Actual Header Value :: "+actualValue+" are not same");      
        }  
    }  
}
    