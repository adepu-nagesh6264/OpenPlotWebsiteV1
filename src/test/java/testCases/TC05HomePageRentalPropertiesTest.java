package testCases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePageRentalPropertiesPage;
import testBase.BaseClass;

import java.time.Duration;

public class TC05HomePageRentalPropertiesTest extends BaseClass {
    @Test
    public void verifyRentalPropertiesCarousel() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        String parent = driver.getWindowHandle();

        HomePageRentalPropertiesPage rp = new HomePageRentalPropertiesPage(driver);
        // FIX 1: Handle Cookie Popup safely (works for Jenkins headless)
        try {
            wait.until(ExpectedConditions.elementToBeClickable(rp.cookiesBtn));
           rp.clickOnCookiesBtn();
        } catch (Exception e) {
            System.out.println("Cookies popup not displayed → continue");
        }
        Assert.assertTrue(rp.isRentalSectionDisplayed(),
                "Rental Properties section is not displayed!");

        int totalCards = rp.getTotalRentalCards();
        System.out.println("Total Rental Cards: " + totalCards);

        Assert.assertTrue(totalCards > 0, "No rental cards found");

        for (int i = 0; i < totalCards; i++) {

            System.out.println("Validating card: " + (i + 1));

            Assert.assertFalse(rp.getActiveTitle().isEmpty(), "Card title is missing");
            Assert.assertFalse(rp.getActivePrice().isEmpty(), "Card price is missing");
            Assert.assertFalse(rp.getActiveLocation().isEmpty(), "Card location is missing");
            Assert.assertTrue(rp.isActiveExplorePresent(), "Explore button missing");

            // Click explore inside visible (active) card
            rp.clickActiveExplore();

            if (i < totalCards - 1) {
                rp.clickNext();
                Thread.sleep(600);
            }
        }


        // Validate prev
        rp.clickPrev();
        Thread.sleep(500);
        driver.switchTo().window(parent);
    }

}
