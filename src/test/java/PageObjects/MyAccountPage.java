package PageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath = "//h2[text()='My Account']")   //My Account page heading
    private WebElement msgHeading;
	
	//@FindBy(xpath = "//a[text()='Logout']")
	@FindBy(xpath = "//a[@class='list-group-item' and text()='Logout']")
	private WebElement lnkLogout;
	
	//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	//WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Logout")));
	
	
	
	
	public boolean isMyAccountPageExists()
	{
		try {
		return(msgHeading.isDisplayed());
		}
		catch(Exception e)
		{
			
			return false;
			
			
		}
		
		
	}
	
	
	//public void clickLogout()
	//{
		//lnkLogout.click();
		
	//}
	
	
	
	private WebElement dropdownToggle;

	public void clickLogout() {
	    // Open the dropdown first if not already open
	   // if (!lnkLogout.isDisplayed()) {
	        //dropdownToggle.click();
	        // Wait until logout is visible (simple example using explicit wait)
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        wait.until(ExpectedConditions.visibilityOf(lnkLogout));
	  //  }
	    
	    // Now click the logout link
	    lnkLogout.click();
	}

}
