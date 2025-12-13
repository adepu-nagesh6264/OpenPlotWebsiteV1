package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);   // REQUIRED FIX
    }

    @FindBy(xpath = "//input[@class='form-control agent_account_pge']")
    public WebElement userNameField;

    @FindBy(xpath = "//input[@class='pass_pad_tag form-control']")
    public WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement loginButton;

    public void setUserNameField(String username) {
        userNameField.clear();
        userNameField.sendKeys(username);
    }

    public void setPasswordField(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickOnLoginButton() {
        loginButton.click();
    }
}
