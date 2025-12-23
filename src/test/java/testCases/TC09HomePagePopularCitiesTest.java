package testCases;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.Assert;
import testBase.BaseClass;
import pageObjects.HomePagePopularCities;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TC09HomePagePopularCitiesTest extends BaseClass {
    @Test
    public void verifyClickEachPopularCityCard() throws Exception {
        handleCookies();
        HomePagePopularCities cities = new HomePagePopularCities(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll until cards appear
        cities.scrollToPopularCitiesSection();
        Thread.sleep(1000);

        int count = cities.getCityCardsCount();
        System.out.println("Total Cards Found: " + count);

        Assert.assertTrue(count > 0, "No city cards found on homepage.");

        String parent = driver.getWindowHandle();

        for (int i = 0; i < count; i++) {

            // Re-find to avoid stale elements
            List<WebElement> cards = cities.getAllCityCards();
            WebElement card = cards.get(i);

            js.executeScript("arguments[0].scrollIntoView({block:'center'});", card);
            Thread.sleep(600);

            String name = card.getText();
            System.out.println("Clicking: " + name);

            js.executeScript("arguments[0].click();", card);
            Thread.sleep(1500);

            // Switch tab
            for (String win : driver.getWindowHandles()) {
                if (!win.equals(parent)) {
                    driver.switchTo().window(win);
                    break;
                }
            }

            Assert.assertTrue(driver.getCurrentUrl().contains("search"),
                    "City page did NOT open: " + name);

            driver.close();
            driver.switchTo().window(parent);
            Thread.sleep(800);
        }
    }



}
