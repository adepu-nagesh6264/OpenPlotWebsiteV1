package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
public class HomePageOpenplotinMinutes{

    WebDriver driver;

    public HomePageOpenplotinMinutes(WebDriver driver) {
        this.driver = driver;
    }

    // ===== Section =====
    public By blogSection =
            By.cssSelector("section.blog-cont");

    // ===== Headings =====
    public By mainHeading =
            By.xpath("//h3[normalize-space()='Find Your Dream Home with Openplot in Minutes']");


    public By serviceTitles =
            By.cssSelector(".blog-text-over .b-head");

    // ================== METHODS ==================

    public boolean isBlogSectionDisplayed() {
        return driver.findElement(blogSection).isDisplayed();
    }

    public String getMainHeadingText() {
        return driver.findElement(mainHeading).getText();
    }


    public boolean areServiceTitlesDisplayed() {
        List<WebElement> titles = driver.findElements(serviceTitles);
        for (WebElement title : titles) {
            if (!title.isDisplayed()) {
                return false;
            }
        }
        return true;
    }
}