package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EnterTimeTrackPage {

	@FindBy(id="logoutLink")
	private WebElement logoutLink;
	
	public EnterTimeTrackPage(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	
	public boolean verifyLogoutLinkIsDisplayed(WebDriverWait wait) {
		
		try 
		{
			wait.until(ExpectedConditions.visibilityOf(logoutLink));
			return true;
		}
		catch (Exception e) 
		{
			return false;
		}
		
	}
	
	
	
}
