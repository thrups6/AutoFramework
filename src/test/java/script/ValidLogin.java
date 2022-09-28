package script;

import org.testng.Assert;
import org.testng.annotations.Test;

import generic.BaseTest;
import page.EnterTimeTrackPage;
import page.LoginPage;

public class ValidLogin extends BaseTest{

	@Test(priority = 1)
	public void testValidLogin() {
//		1. Enter valid user name
		LoginPage loginPage=new LoginPage(driver);
		loginPage.setUserName("admin");
		test.info("Enter valid user name");
		
//		2. Enter Valid password
		loginPage.setPassword("manager");
		test.info("Enter Valid password");
		
//		3. click on login button
		loginPage.clickLoginButton();
		test.info("click on login button");
		
//		4. Verify that home page should be displayed
		EnterTimeTrackPage ettPage=new EnterTimeTrackPage(driver);
		boolean result = ettPage.verifyLogoutLinkIsDisplayed(wait);
		Assert.assertEquals(result, true,"Home page is not displayed");
		test.pass("Home page is displayed");
	}
}
