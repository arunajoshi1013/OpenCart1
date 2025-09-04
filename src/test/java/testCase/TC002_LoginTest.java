package testCase;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.HomePage1;

import PageObjects.LoginPage1;
import PageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{
	
	
	
	@Test(groups={"Sanity","Master"})
	public void verify_login()
	{
		try {
		
		//homepage
		HomePage1 hp=new HomePage1(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//Loginpage
		LoginPage1 lp=new LoginPage1(driver);
		  lp.setEmail(p.getProperty("email"));
		  lp.setPassword(p.getProperty("password"));
		  lp.clickLogin();
		  
		  //MyAccountpage
		  MyAccountPage macc=new MyAccountPage(driver);
		 boolean targetpage= macc.isMyAccountPageExists();
		 //Assert.assertEquals(targetpage, true,"Login failed");
		 Assert.assertTrue(targetpage);
		}
		
		catch(Exception e)
		{
			Assert.fail();
			
			
		}
		
		
		
		  
		
	}
	
	
}