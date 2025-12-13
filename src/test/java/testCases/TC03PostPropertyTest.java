package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.PostPropertyPage;
import testBase.BaseClass;

import java.time.Duration;

import static pageObjects.BasePage.uploadUsingRobot;

public class TC03PostPropertyTest extends BaseClass {
    private static final Logger logger = (Logger) LogManager.getLogger(TC03PostPropertyTest.class);
    @Test
    public void VerifyPostProperty() throws Exception {

        HomePage hp = new HomePage(driver);
        PostPropertyPage pp = new PostPropertyPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 🔥🔥 WAIT for Post Property button
        wait.until(ExpectedConditions.elementToBeClickable(pp.postPropertyBtn));
        pp.clickPostProperty();

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

        pp.clickButton("FurnitureStatus", "Unfurnished");
        pp.clickButton("FloorType", "Tiles");

        pp.clickAmenity("CCTV");
        pp.clickAmenity("Children Park");
        pp.clickAmenity("Security");

        pp.clickOnReadyToMove();
        pp.enterAge();
        pp.clickButton("Facing", "East");
        pp.clickButton("TransactionStatus", "New");

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

        // 🔥 Fix for slow modal loading
//        wait.until(ExpectedConditions.elementToBeClickable(pp.selectImagesBtn));
//        pp.clickOnSelectImagesBtn();
//
//        uploadUsingRobot("C:\\Users\\adepu\\OneDrive\\Pictures\\TestImage.JPG");
//
//        wait.until(ExpectedConditions.elementToBeClickable(pp.saveAndContinueBtn));
        pp.clickOnSkipImagesButton();
       // pp.clickOnSaveAndContinueBtn();

        Assert.assertTrue(pp.isBackToHomePageBtnDisplayed(),
                "Back To Home Page button displayed");

        logger.info("Your Property Listed Successfully.");
        Thread.sleep(3000);
        pp.clickOnBackToHomePageBtn();
    }
}
