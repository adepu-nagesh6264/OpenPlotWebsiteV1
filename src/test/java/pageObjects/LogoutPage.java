package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogoutPage extends BasePage {

    public LogoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    //  Correct navigation menu button
    @FindBy(xpath = "//button[contains(@class,'navbar-toggler')]")
    public WebElement navigationMenu;

    // Correct logout / checkout option
    @FindBy(xpath = "//*[contains(text(),'Check out') or contains(text(),'Logout')]")
    public WebElement checkoutFromWebsite;

    // Correct confirm logout button
    @FindBy(xpath = "//div[@class='logout-confirm']")
    public WebElement logoutConfirmButton;

    public void clickOnNavigationMenu() {
        navigationMenu.click();
    }

    public void clickOnCheckoutFromWebsite() {
        checkoutFromWebsite.click();
    }

    public void clickOnConfirmLogoutAlert() {
        logoutConfirmButton.click();
    }
}
