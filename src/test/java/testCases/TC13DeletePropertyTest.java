package testCases;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.*;
import testBase.BaseClass;

import java.time.Duration;

public class TC13DeletePropertyTest extends BaseClass {
    @BeforeClass
    public void setup() {

        // ✅ Store parent window for switching
        storeParentWindow();
    }


    @Test
    public void DeleteProperty() throws InterruptedException {

        HomePage hp = new HomePage(driver);
        ViewPropertyPage vp = new ViewPropertyPage(driver);
        PostPropertyPage pp = new PostPropertyPage(driver);
        LogoutPage lp = new LogoutPage(driver);


        lp.clickOnNavigationMenu();
        Thread.sleep(2000);
        lp.clickOnMyPropertiesMenu();
        Thread.sleep(2000);

        vp.clickOnViewPropertybtn();
        //pp.clickVisitYourProperty();
        Thread.sleep(2000);
        // Switch to new window
        System.out.println("Switching to child window...");
        switchToChildWindow();


        vp.clickDeleteProperty();
        Thread.sleep(2000);

        vp.clickYesDelete();
        Thread.sleep(2000);

        vp.clicksucess_ok();

        // Switch back
        // ✅ Close child window after validation
        System.out.println("Closing child window (BUY)...");
        driver.close();

// ✅ Switch back to parent window
        System.out.println("Switching back to parent window...");
        switchToParentWindow();
    }
}