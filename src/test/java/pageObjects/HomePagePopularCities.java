package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePagePopularCities {

    WebDriver driver;

    public HomePagePopularCities(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Correct XPath to fetch ALL popular city cards
    @FindBy(xpath = "//div[contains(@class,'recent_prop_view_d')]//a[contains(@class,'no-style-link')]")
    List<WebElement> cityCards;

    public List<WebElement> getAllCityCards() {
        return cityCards;
    }

    public int getCityCardsCount() {
        return cityCards.size();
    }

    // Scroll until section appears
    public void scrollToPopularCitiesSection() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (int i = 0; i < 25; i++) {
            js.executeScript("window.scrollBy(0, 400);");
            Thread.sleep(300);

            if (cityCards.size() > 0) {
                break;
            }
        }
    }
}
