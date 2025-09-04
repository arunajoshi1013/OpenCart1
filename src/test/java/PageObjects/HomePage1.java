package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage1 extends BasePage {

    // Constructor
    public HomePage1(WebDriver driver) {
        super(driver);
         
    }

    // Web Elements
    @FindBy(xpath = "//span[normalize-space()='My Account']")
    private WebElement lnkMyAccount;

    @FindBy(linkText = "Register") // Slightly more stable than XPath
    private WebElement lnkRegister;
    
    
    @FindBy(linkText = "Login") // Slightly more stable than XPath
    private WebElement linkLogin;

    // Action Methods
    public void clickMyAccount() {
        lnkMyAccount.click();
    }

    public void clickRegister() {
        lnkRegister.click();
    }
    
    public void clickLogin()
    {
    	
    	linkLogin.click();
    	
    }
}


