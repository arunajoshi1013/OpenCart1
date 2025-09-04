package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage1  extends BasePage{

	public LoginPage1(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	@FindBy(id = "input-email")
    private WebElement txtEmailAddress;
	
	@FindBy(id = "input-password")
    private WebElement txtPassword;
	
	
	@FindBy(xpath = "//input[@value='Login']")
    private WebElement btnLogin;
	
	
	  public void setEmail(String email) {
	        txtEmailAddress.sendKeys(email);
	
	  }
	        public void setPassword(String pwd) {
	            txtPassword.sendKeys(pwd);
	        }
	        
	        
	        

	        public void clickLogin() {
	            btnLogin.click();
	        }


}
