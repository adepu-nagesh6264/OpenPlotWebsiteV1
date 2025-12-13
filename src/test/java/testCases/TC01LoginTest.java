package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;

import java.time.Duration;

public class TC01LoginTest extends BaseClass {

    @Test
    public void VerifyLogin() {
        HomePage hp = new HomePage(driver);
        LoginPage lp = new LoginPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        String uname = p.getProperty("username");
        String pwd = p.getProperty("password");

        // 🔥 FIX 1: Handle Cookie Popup safely (works for Jenkins headless)
        try {
            wait.until(ExpectedConditions.elementToBeClickable(hp.cookiesBtn));
            hp.clickOnCookiesBtn();
        } catch (Exception e) {
            System.out.println("Cookies popup not displayed → continue");
        }

        // 🔥 FIX 2: Click Login button using wait
        wait.until(ExpectedConditions.elementToBeClickable(hp.loginBtn));
        hp.clickOnLoginBtn();

        // 🔥 FIX 3: Enter credentials
        wait.until(ExpectedConditions.visibilityOf(lp.userNameField));
        lp.setUserNameField(uname);

        wait.until(ExpectedConditions.visibilityOf(lp.passwordField));
        lp.setPasswordField(pwd);

        lp.clickOnLoginButton();

        // 🔥 FIX 4: Explicit wait instead of Thread.sleep
        boolean status = hp.waitForProfileImage();

        // 🔥 FIX 5: Assertion with screenshot support
        if (status) {
            captureScreenshot(driver, "LoginTest_Pass");
            Assert.assertTrue(status, "Login Successful");
        } else {
            captureScreenshot(driver, "LoginTest_Fail");
            Assert.fail("Login Failed — Profile image not visible");
        }

        logger.info("Login test executed successfully.");
    }
}
