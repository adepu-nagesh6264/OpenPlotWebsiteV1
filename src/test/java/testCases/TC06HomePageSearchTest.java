package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
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

        city = p.getProperty("searchcity");

        // ✅ Initialize page object FIRST
        hps = new HomePageSearch(driver);

        wait = new WebDriverWait(driver, Duration.ofSeconds(12));

        // ✅ Safe cookie handling
        handleCookies();
        hps.closeCookiePopup();

        // ✅ Store parent window for switching
        storeParentWindow();
    }

    @Test
    public void validateSearchPage() {

        // ================= BUY FLOW =================
        handleCookies();
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

        // ✅ Ensure navigation completed (SEO-safe)
        waitUntilPageLoads();
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/buy"),
                ExpectedConditions.urlContains("/rent")
        ));

        // ✅ STABILIZATION WAIT (UI-based – reliable)
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h6[contains(translate(normalize-space(.)," +
                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'properties for')]")
        ));

        System.out.println("Child Window URL (BUY): " + driver.getCurrentUrl());

        // ✅ SAME method reused (NOT removed)
        Assert.assertTrue(
                hps.validatePropertiesForBuyText(),
                "Properties header not found in BUY search result page"
        );

        // Switch back
        System.out.println("Switching back to parent window...");
        switchToParentWindow();
        waitUntilPageLoads();

        // ================= RENT FLOW =================
        handleCookies();
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

        // ✅ Ensure navigation completed (SEO-safe)
        waitUntilPageLoads();
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/buy"),
                ExpectedConditions.urlContains("/rent")
        ));

        // ✅ SAME stabilization wait reused
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h6[contains(translate(normalize-space(.)," +
                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'properties for')]")
        ));

        System.out.println("Child Window URL (RENT): " + driver.getCurrentUrl());

        // ✅ SAME method reused (method NOT removed)
        Assert.assertTrue(
                hps.validatePropertiesForBuyText(),
                "Properties header not found in RENT search result page"
        );

        // Switch back
        System.out.println("Switching back to parent window...");
        switchToParentWindow();
    }

    // ----------- Utility Wait Method -------------
    public void waitUntilPageLoads() {
        wait.until(ExpectedConditions.jsReturnsValue(
                "return document.readyState === 'complete'"
        ));
    }
}
