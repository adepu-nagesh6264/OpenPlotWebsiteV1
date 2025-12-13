package testCases;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.HomePageSearch;
import testBase.BaseClass;

import java.time.Duration;

public class TC06HomePageSearchTest extends BaseClass {

    String city;
    HomePageSearch hps;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
       closeCookiePopup();
        city = p.getProperty("searchcity");
        hps = new HomePageSearch(driver);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // ⭐ IMPORTANT: store parent window for correct switching
        storeParentWindow();
    }

    @Test
    public void validateSearchPage() {

        System.out.println("Selecting Buy radio button...");
        hps.clickOnBuyRadioButton();
        waitUntilPageLoads();

        System.out.println("Entering search city...");
        hps.enterSearchCity(city);
        waitUntilPageLoads();

        System.out.println("Selecting city from dropdown...");
        hps.clickOnCitySearchDropdown();
        waitUntilPageLoads();

        System.out.println("Selecting filter value...");
        hps.clickOnAllPropertiesDrpdwnbtn();
        waitUntilPageLoads();
        hps.clickIndependentHouse();
        hps.clickApartment();
        hps.clickFarmHouse();
        hps.clickLand();

        System.out.println("Clicking on search button...");
        hps.clickOnSearchButton();

        // Switch to new window
        System.out.println("Switching to child window...");
        switchToChildWindow();

        // ⭐ better page load wait after switching
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));

        System.out.println("Child Window URL: " + driver.getCurrentUrl());

        System.out.println("Validating text on result page...");
        Assert.assertTrue(
                hps.validatePropertiesForBuyText(),
                "Text 'Properties for BUY in' not found in new window!"
        );

        // Switch back
        System.out.println("Switching back to parent window...");
        switchToParentWindow();

        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
        System.out.println("Returned to URL: " + driver.getCurrentUrl());

        System.out.println("Selecting Rent radio button...");
        hps.clickOnRentRadioButton();
        waitUntilPageLoads();

        hps.clickOnAllPropertiesDrpdwnbtn();
        hps.clickClear();
        hps.clickIndependentHouse();
        hps.clickApartment();
        hps.clickIndustry();
        hps.clickOffice();
        System.out.println("Clicking on search button...");
        hps.clickOnSearchButton();

        // Switch to new window
        System.out.println("Switching to child window...");
        switchToChildWindow();

        // ⭐ better page load wait after switching
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
        System.out.println("Validating text on result page...");
        Assert.assertTrue(
                hps.validatePropertiesForBuyText(),
                "Text 'Properties for RENT in' not found in new window!"
        );

        // Switch back
        System.out.println("Switching back to parent window...");
        switchToParentWindow();

    }

    // ----------- Utility Wait Method -------------
    public void waitUntilPageLoads() {
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState=='complete'"));
    }
}
