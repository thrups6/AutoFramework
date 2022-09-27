
package generic;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	public WebDriver driver;
	public WebDriverWait wait;
	public static final String XLPATH="./data/input.xlsx";
	public static final String REPORTPATH="./target/MyReport.html";
	public static ExtentReports eReport;
	public ExtentTest test;
	@BeforeSuite
	public void initReport() {
		eReport=new ExtentReports();
		ExtentSparkReporter reportType=new ExtentSparkReporter(REPORTPATH);
		eReport.attachReporter(reportType);
	}
	
	@AfterSuite
	public void publishReport() {
		eReport.flush();
	}
	@Parameters("property")
	@BeforeMethod
	public void openApp(@Optional("config.properties") String propertyFile,Method testMethod) throws Exception
	{
		String testName = testMethod.getName();
		test = eReport.createTest(testName);
		
		String useGrid=Utility.getPropertyValue(propertyFile,"USEGRID");
		test.info("useGrid:"+useGrid);
		String gridURL=Utility.getPropertyValue(propertyFile,"GRIDURL");
		test.info("gridURL:"+gridURL);
		String browser=Utility.getPropertyValue(propertyFile,"BROWSER");
		test.info("browser:"+browser);
		String appURL=Utility.getPropertyValue(propertyFile,"APP_URL");
		test.info("appURL:"+appURL);
		
		String strITO=Utility.getPropertyValue(propertyFile,"ITO");
		test.info("strITO:"+strITO);
		long lITO=Long.parseLong(strITO);
		
		String strETO=Utility.getPropertyValue(propertyFile,"ETO");
		test.info("strETO:"+strETO);
		long lETO=Long.parseLong(strETO);
				
		if(useGrid.equalsIgnoreCase("Yes"))
		{
			test.info("Using gird");
			URL remoteURL=new URL(gridURL);
			DesiredCapabilities capabilities=new DesiredCapabilities();
			capabilities.setBrowserName(browser);
			driver=new RemoteWebDriver(remoteURL,capabilities);
		}
		else
		{
			test.info("Not using gird,running locally");
			switch (browser.toLowerCase()) 
			{
				case "chrome":
								test.info("Running on Chrome browser");
								WebDriverManager.chromedriver().setup();
								driver=new ChromeDriver();
								break;
								
				default:
								test.warning("Running on default browser");
					
					
				case "firefox":
								test.info("Running on Firefox browser");
								WebDriverManager.firefoxdriver().setup();
								driver=new FirefoxDriver();

			}
		}
		test.info("Entering the url");
		driver.get(appURL);
		test.info("Setting ITO");
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(lITO));
		test.info("Setting ETO");
		wait=new WebDriverWait(driver, Duration.ofSeconds(lETO));
	}
	
	@AfterMethod
	public void closeApp(ITestResult result) throws Exception
	{
		String testName = result.getName();
		int status = result.getStatus();
		if(status==2)
		{
			
			TakesScreenshot tDriver=(TakesScreenshot)driver;
			File scrFile = tDriver.getScreenshotAs(OutputType.FILE);
			File dstFile = new File("./screenshots/"+testName+".png");
			FileUtils.copyFile(scrFile, dstFile);
			test.info("Test:"+testName +" Failed and ScreenShot has been taken:"+dstFile);
			test.addScreenCaptureFromPath("./../screenshots/"+testName+".png");
			String msg=result.getThrowable().getMessage();
			test.fail("Test:"+testName+" is failed due to:"+msg);
			
		}
		test.info("Closing the browser");
		driver.quit();
	}
}
