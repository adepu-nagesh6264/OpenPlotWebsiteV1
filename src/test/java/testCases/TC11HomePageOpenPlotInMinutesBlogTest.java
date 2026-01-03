package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePageOpenplotinMinutes;
import testBase.BaseClass;

import java.time.Duration;
public class TC11HomePageOpenPlotInMinutesBlogTest extends BaseClass {

    @Test
    public void verifyBlogAndServicesSection() {

        HomePageOpenplotinMinutes blog = new HomePageOpenplotinMinutes(driver);
        WebElement heading = driver.findElement(
                By.xpath("//h3[normalize-space()='Find Your Dream Home with Openplot in Minutes']")

        );
        scrollToElement(heading);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // ===== Wait for section =====
        wait.until(ExpectedConditions.visibilityOfElementLocated(blog.blogSection));

        // ===== Assertions =====
        Assert.assertTrue(blog.isBlogSectionDisplayed(),
                "Blog section is not displayed");

        Assert.assertTrue(blog.getMainHeadingText()
                        .contains("Find Your Dream Home"),
                "Main heading text mismatch");

               System.out.println("✅ Blog & Services section validated successfully");
    }
}
