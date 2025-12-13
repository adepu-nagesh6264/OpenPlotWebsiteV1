package testCases;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.HomePageSearch;
import testBase.BaseClass;

import java.time.Duration;

public class TC07HomePageNewlyAddedProjectsTest extends BaseClass {

    HomePageSearch hps;
    WebDriverWait wait;

    @BeforeClass
    public void init() {
        hps = new HomePageSearch(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void validateNewlyAddedProjects() throws InterruptedException {

        System.out.println("Starting intelligent carousel navigation...");

        WebElement arrowBtn = hps.arrowRightatNewly;

        // Scroll arrow into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", arrowBtn);
        Thread.sleep(500);

        int clickCount = 0;
        int stillCount = 0;

        // Get initial scroll position of carousel
        long lastPos = (long) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].parentElement.scrollLeft;", arrowBtn);

        while (true) {

            // Stop if arrow has disabled class/attribute
            if (isArrowDisabled(arrowBtn)) {
                System.out.println("Arrow is disabled. Stopping navigation.");
                break;
            }

            // Try clicking arrow
            try {
                wait.until(ExpectedConditions.elementToBeClickable(arrowBtn));
                arrowBtn.click();
            } catch (Exception e) {
                System.out.println("Normal click failed → using JS click");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", arrowBtn);
            }

            Thread.sleep(400);
            clickCount++;

            // Check if carousel moved
            long newPos = (long) ((JavascriptExecutor) driver)
                    .executeScript("return arguments[0].parentElement.scrollLeft;", arrowBtn);

            if (newPos == lastPos) {
                stillCount++;
            } else {
                stillCount = 0; // reset since it moved
            }

            lastPos = newPos;

            // If position didn’t change for 3 consecutive clicks → end
            if (stillCount >= 3) {
                System.out.println("Carousel stopped moving → stopping loop.");
                break;
            }
        }

        System.out.println("Total effective clicks: " + clickCount);
        hps.clickFavOfLastCard(driver);
        hps.clickOnArrowLeftatNewly();
    }


    // Helper method
    private boolean isArrowDisabled(WebElement arrowBtn) {
        try {
            String disabledAttr = arrowBtn.getAttribute("disabled");
            String classAttr = arrowBtn.getAttribute("class");

            return (disabledAttr != null && disabledAttr.equals("true"))
                    || (classAttr != null && classAttr.contains("disabled"));
        } catch (Exception e) {
            return true;
        }
    }


}
