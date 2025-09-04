package testCase;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;



import PageObjects.AccountRegistraionPage;
import PageObjects.HomePage1;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
	
	
	
	
	@Test(groups={"Regression","Master"})
	public void verify_account_registration()
	{
		HomePage1 hp=new HomePage1(driver);
		hp.clickMyAccount();
		hp.clickRegister();
		
		
		AccountRegistraionPage  regpage=new AccountRegistraionPage(driver);
		
		regpage.setFirstName(randomeString().toUpperCase());
		regpage.setLastName(randomeString().toUpperCase());
		regpage.setEmail(randomeString()+"@gmail.com");
		regpage.setTelephone(randomeNumber());
		
		
		String password=randomeAlphaNumeric();
		
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		
		String confmsg=regpage.getConfirmationMsg();
		
		AssertJUnit.assertEquals(confmsg,"Your Account Has Been Created!");
		
		
		
	}
	
	
	
}
