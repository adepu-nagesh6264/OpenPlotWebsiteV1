package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePagePropertyByTypePerfectMatch;
import testBase.BaseClass;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class TC12HomePagePropertyByTypePerfectMatchTest extends BaseClass {

    @Test
    public void verifyPropertyByTypeSection() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        HomePagePropertyByTypePerfectMatch pt =
                new HomePagePropertyByTypePerfectMatch(driver);

        // ===== Scroll =====
        WebElement perfectMatch = driver.findElement(
                By.xpath("//span[normalize-space()='Perfect Match']"));
        scrollToElement(perfectMatch);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//section[contains(@class,'category-type')]")));
pt.clickNext(driver);
Thread.sleep(2000);
pt.clickPrevious(driver);
        Thread.sleep(2000);
        pt.clickOnActiveCard();
        Thread.sleep(4000);
        driver.navigate().back();
        Thread.sleep(6000);
               // pt.clickNext(driver);
    }

}
