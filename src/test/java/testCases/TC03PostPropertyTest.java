package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.PostPropertyPage;
import testBase.BaseClass;

import java.time.Duration;

public class TC03PostPropertyTest extends BaseClass {

    private static final Logger logger =
            (Logger) LogManager.getLogger(TC03PostPropertyTest.class);

    @Test
    public void VerifyPostProperty() throws Exception {

        HomePage hp = new HomePage(driver);
        PostPropertyPage pp = new PostPropertyPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 🔥 FIX 1: Initial page load + cookies
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        handleCookies();

        // 🔥 FIX 2: WAIT for Post Property button
        wait.until(ExpectedConditions.elementToBeClickable(pp.postPropertyBtn));
        pp.clickPostProperty();

        // 🔥 FIX 3: Safety cookie handling after navigation
        handleCookies();

        pp.clickTab("Sell");
        pp.clickTab("Residential");
        pp.clickTab("Independent House");

        pp.enterHouseNumber();
        pp.enterArea();
        pp.enterBuildingArea();
        pp.enterCarpetArea();

        pp.clickButton("TotalFloors", "2");
        pp.clickButton("BedRooms", "2");
        pp.clickButton("BathRooms", "2");
        pp.clickButton("Balconies", "2");
        pp.selectYBtnOption( "Unfurnished");
        pp.selectYBtnOption("Tiles");
        pp.clickAmenity("CCTV");
        pp.clickAmenity("Children Park");
        pp.clickAmenity("Security");
        pp.selectYBtnOption( "Ready To Move");
        pp.enterAge();
        pp.selectYBtnOption("East");
        pp.selectYBtnOption( "New");


//        // ================= READY TO MOVE (CRITICAL FIX) =================
//        try {
//            wait.until(ExpectedConditions.elementToBeClickable(
//                    By.xpath("//button[@type='button' and text()='Ready To Move']")
//            ));
//            pp.clickOnReadyToMove(); // existing method (kept)
//        } catch (ElementClickInterceptedException e) {
//            logger.warn("⚠️ Ready To Move click intercepted – using safeClick fallback");
//            safeClick(driver.findElement(
//                    By.xpath("//button[@type='button' and text()='Ready To Move']")
//            ));
//        }

        // =================================================================

          pp.enterRoadWidth();
        pp.enterPrice();
        pp.enterBookingAmount();
        pp.enterRegNo();
        pp.enterDocOwnerName();
        pp.enterPortalName();

        pp.clickButton("OpenParking", "2");
        pp.clickButton("ClosedParking", "1");
        pp.selectFacility("Temple");

        pp.enterPincode();
        pp.selectLocality(2);
        pp.enterAddress();
        pp.enterDescription();

        pp.clickSubmit();

        // 🔥 FIX 4: Cookies sometimes reappear before image modal
        handleCookies();

        pp.clickOnSkipImagesButton();

        Assert.assertTrue(
                pp.isBackToHomePageBtnDisplayed(),
                "Back To Home Page button displayed"
        );

        logger.info("Your Property Listed Successfully.");

        Thread.sleep(3000);
        pp.clickOnBackToHomePageBtn();
    }
}
