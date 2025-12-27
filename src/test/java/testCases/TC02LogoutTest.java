package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LogoutPage;
import testBase.BaseClass;

import java.time.Duration;

public class TC02LogoutTest extends BaseClass {
    private static final Logger logger = (Logger) LogManager.getLogger(TC02LogoutTest.class);

    @Test
    public void VerifyLogout() {

        HomePage hp = new HomePage(driver);
        LogoutPage lg = new LogoutPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for navigation menu & click
        wait.until(ExpectedConditions.elementToBeClickable(lg.navigationMenu));
        lg.clickOnNavigationMenu();


        // Wait for checkout / logout option
        wait.until(ExpectedConditions.elementToBeClickable(lg.checkoutFromWebsite));
        closeChatWidgetIfPresent();
        closeTawkChatIfPresent();
        disableChatWidgetCompletely();
        lg.clickOnCheckoutFromWebsite();

        // Confirm logout alert
        wait.until(ExpectedConditions.elementToBeClickable(lg.logoutConfirmButton));
        lg.clickOnConfirmLogoutAlert();

        // Verify login button appears after logout
        wait.until(ExpectedConditions.visibilityOf(hp.loginBtn));

        Assert.assertTrue(hp.isLoginBtnDisplayed(), "Login button is not displayed → Logout failed");

        logger.info("User logged off successfully");
    }
}
