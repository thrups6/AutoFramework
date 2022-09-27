package script;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import generic.BaseTest;
import generic.Utility;
import page.HomePage;

public class Test1 extends BaseTest
{
	@Test(priority = 1)
	public void testA() {
		
		
		String v=Utility.getXlData(XLPATH,"test1", 0, 0);
		test.info("From xl:"+v);
		
		String title = driver.getTitle();
		test.info(title);
		
		HomePage homePage=new HomePage(driver);
		String label = homePage.getLabel();
		test.info(label);
		
		Assert.assertEquals(label,"CONTACT US");
		
	}
}
