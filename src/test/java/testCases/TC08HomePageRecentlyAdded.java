package testCases;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.HomePageSearch;
import testBase.BaseClass;

import java.time.Duration;
import java.util.List;

public class TC08HomePageRecentlyAdded extends BaseClass {

    HomePageSearch hps;
    WebDriverWait wait;
    JavascriptExecutor js;

    @BeforeClass
    public void init() {
        hps = new HomePageSearch(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void validateRecentlyAdded() throws InterruptedException {
        handleCookies();
        // Get list of recently added cards
        List<WebElement> cards = hps.getRecentlyAddedCards();

        System.out.println("Total Recently Added Cards Found: " + cards.size());

        for (int i = 0; i < cards.size(); i++) {

            WebElement card = cards.get(i);

            // Scroll each card into view
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", card);
            Thread.sleep(800);

            System.out.println("Clicking card index: " + i);

            card.click();
            Thread.sleep(1500);

            // Navigate back to Home Page
            driver.navigate().back();
            Thread.sleep(1500);

            // Re-capture cards after navigation (DOM refresh)
            cards = hps.getRecentlyAddedCards();
        }
    }
}
