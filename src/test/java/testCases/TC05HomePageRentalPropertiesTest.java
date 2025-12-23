package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePageRentalPropertiesPage;
import testBase.BaseClass;

import java.time.Duration;
import java.util.Set;

public class TC05HomePageRentalPropertiesTest extends BaseClass {

    @Test
    public void verifyRentalPropertiesCarousel() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 🔥 Page load + cookies
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        handleCookies();

        String parent = driver.getWindowHandle();
        HomePageRentalPropertiesPage rp = new HomePageRentalPropertiesPage(driver);

        // 🔥 Existing cookie handling (kept)
        try {
            wait.until(ExpectedConditions.elementToBeClickable(rp.cookiesBtn));
            rp.clickOnCookiesBtn();
        } catch (Exception e) {
            System.out.println("Cookies popup not displayed → continue");
        }

        Assert.assertTrue(
                rp.isRentalSectionDisplayed(),
                "Rental Properties section is not displayed!"
        );

        int totalCards = rp.getTotalRentalCards();
        System.out.println("Total Rental Cards: " + totalCards);
        Assert.assertTrue(totalCards > 0, "No rental cards found");

        for (int i = 0; i < totalCards; i++) {

            System.out.println("Validating card: " + (i + 1));

            String title = rp.getActiveTitle().trim();
            String price = rp.getActivePrice().trim();
            String location = rp.getActiveLocation().trim();

            // 🔥 Skip non-property cards
            if (title.isEmpty() && price.isEmpty() && location.isEmpty()) {
                System.out.println("⚠ Skipping non-property card (promo / placeholder)");

            } else {

                Assert.assertFalse(title.isEmpty(), "Card title is missing");
                Assert.assertFalse(price.isEmpty(), "Card price is missing");
                Assert.assertFalse(location.isEmpty(), "Card location is missing");
                Assert.assertTrue(rp.isActiveExplorePresent(), "Explore button missing");

                // 🔥 Click Explore
                try {
                    rp.clickActiveExplore();
                } catch (Exception e) {
                    safeClick(
                            driver.findElement(
                                    By.xpath("//div[contains(@class,'active')]//button[contains(text(),'Explore')]")
                            )
                    );
                }

                // ================= NEW TAB HANDLING =================
                wait.until(driver -> driver.getWindowHandles().size() > 1);

                Set<String> windows = driver.getWindowHandles();
                for (String win : windows) {
                    if (!win.equals(parent)) {
                        driver.switchTo().window(win);

                        // Optional validation
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
                        System.out.println("Opened new tab → Closing it");

                        driver.close(); // ✅ Close new tab
                    }
                }

                driver.switchTo().window(parent); // ✅ Back to parent
                // ====================================================
            }

            // 🔥 Safe Next
            if (i < totalCards - 1) {
                try {
                    rp.clickNext();
                } catch (Exception e) {
                    safeClick(
                            driver.findElement(
                                    By.xpath("//button[contains(@class,'next')]")
                            )
                    );
                }
                Thread.sleep(700);
            }
        }

        // 🔥 Safe Prev
        try {
            rp.clickPrev();
        } catch (ElementClickInterceptedException e) {
            safeClick(
                    driver.findElement(
                            By.xpath("//button[contains(@class,'prev')]")
                    )
            );
        }

        Thread.sleep(500);
        driver.switchTo().window(parent);
    }
}
