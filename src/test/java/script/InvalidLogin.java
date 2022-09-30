package script;

import org.testng.Assert;
import org.testng.annotations.Test;

import generic.BaseTest;
import generic.Utility;
import page.LoginPage;

public class InvalidLogin extends BaseTest
{
	@Test(priority = 2)
	public void testInvalidLogin() {
//		get the data from excel file
		String un=Utility.getXlData(XLPATH, "InvalidLogin",1, 0);
		String pw=Utility.getXlData(XLPATH, "InvalidLogin",1, 1);
		
//		1. Enter invalid user name
		LoginPage loginPage=new LoginPage(driver);
		loginPage.setUserName(un);
		test.info("Enter invalid user name:"+un);
		
//		2. Enter invalid password
		loginPage.setPassword(pw);
		test.info("Enter invalid password"+pw);
		
//		3. click on login button
		loginPage.clickLoginButton();
		test.info("click on login button");
		
//		4. Verify that error message is displayed
		test.info("Verify that error message is displayed");
		boolean result = loginPage.verifErrMsgIsDisplayed(wait);
		Assert.assertTrue(result,"Err Msg is not displayed");
		test.pass("Err Msg is Displayed");
	}
}
