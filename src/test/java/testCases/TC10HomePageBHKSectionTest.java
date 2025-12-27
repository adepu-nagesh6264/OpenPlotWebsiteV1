package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.HomePageBHK;
import pageObjects.HomePageSearch;
import testBase.BaseClass;

public class TC10HomePageBHKSectionTest extends BaseClass {
    HomePageBHK bhk;
    HomePageSearch hps;

    @BeforeClass
    public void init() {

        // ✅ Initialize page objects FIRST
        hps = new HomePageSearch(driver);
        bhk = new HomePageBHK(driver);

        // ✅ Handle cookies safely
        handleCookies();
        hps.closeCookiePopup();

        // ✅ Store parent window if navigation happens
        storeParentWindow();
    }


    @Test(priority = 1)
    public void scrollToBhkSection() {
        bhk.scrollToBhkSection();
        sleep(1500);
        Assert.assertTrue(bhk.getSectionHeading().contains("Perfect Home"));
    }

    @Test(priority = 2)
    public void verifyBhkCardsCount() {
        bhk.scrollToBhkSection();
        Assert.assertEquals(bhk.getTotalBhkCards(), 5);
    }

    @Test(priority = 3)
    public void verifyEachBhkCardDisplayed() {
        bhk.scrollToBhkSection();
        for (int i = 0; i < bhk.getTotalBhkCards(); i++) {
            Assert.assertTrue(bhk.isBhkCardDisplayed(i));
        }
    }

    @Test(priority = 4)
    public void verifyBhkNumbers() {
        bhk.scrollToBhkSection();
        String[] expected = {"1", "2", "3", "4", "4+"};

        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(bhk.getBhkNumber(i), expected[i]);
        }
    }

    @Test(priority = 5)
    public void verifyPropertyCountText() {
        bhk.scrollToBhkSection();
        for (int i = 0; i < bhk.getTotalBhkCards(); i++) {
            Assert.assertTrue(bhk.getPropertyCountText(i).contains("Properties"));
        }
    }

    @Test(priority = 6)
    public void clickEachBhkCardAndVerifyNavigation() throws InterruptedException {
        bhk.scrollToBhkSection();
        bhk.closeChatWidgetIfPresent();  // VERY IMPORTANT

        for (int i = 0; i < bhk.getTotalBhkCards(); i++) {

            bhk.clickBhkCard(i);
            Thread.sleep(1500);

            Assert.assertTrue(driver.getCurrentUrl().contains("bhk"));

            driver.navigate().back();
            Thread.sleep(1000);

            bhk.scrollToBhkSection();
            bhk.closeChatWidgetIfPresent();
            Thread.sleep(5000);// ensure popup not reappearing
        }
    }


    // Small helper method
    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
        }
    }
}