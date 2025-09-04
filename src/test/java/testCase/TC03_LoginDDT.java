/*   data is valid -login success-test pass- logout 
 *    ata is valid -login fail-test test fail
 * 
 * 
 *    data is invalid -login success-test fail-logout
 *    data is invalid -login fail-test pass
 *   
 *   */




package testCase;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageObjects.HomePage1;
import PageObjects.LoginPage1;
import PageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC03_LoginDDT extends BaseClass{
	
	

	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="Datadriven")   //getting dataprovider from different class
	public void verify_loginDDT(String email, String pwd,String exp)
	{
		try {
		
		//Homepage
		HomePage1 hp=new HomePage1(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//Loginpage
	
		LoginPage1 lp=new LoginPage1(driver);
	
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		
		
		//MyAccount
		
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetpage=macc.isMyAccountPageExists();
		
		
		if(exp.equalsIgnoreCase("valid"))
    {
	    if(targetpage==true)   //login successful and test pass
   
		{
			Assert.assertTrue(true,"valid data, test pass");
			macc.clickLogout();
			
		}
		
		else
		{
			
			Assert.assertTrue(false,"valid data, test fail");
			
		}
		
				
	}
	
		
		if(exp.equalsIgnoreCase("invalid"))
    {
	    if(targetpage==true)   //login successful and test fail
   
		{
			Assert.assertTrue(false,"invalid data, test fail");
			macc.clickLogout();
			
		}
		
		else
		{
			
			Assert.assertTrue(true,"invalid data , test pass");
			
		}
		
    }
		
	}
		catch(Exception e)
		{
			 e.printStackTrace();  // show exception in console
			Assert.fail();
			
			
		}
}
}