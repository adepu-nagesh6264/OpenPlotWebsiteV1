package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testBase.BaseClass;

import java.time.Duration;
import java.util.List;

public class HomePageBHK  {

    WebDriver driver;

    public HomePageBHK(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // SECTION ROOT ELEMENT
    @FindBy(css = ".bhk-bg")
    private WebElement bhkSection;

    @FindBy(css = ".bhk-bg h3")
    private WebElement bhkSectionHeading;

    @FindBy(css = ".bhk-bg p.text-center")
    private WebElement bhkSectionDescription;

    @FindBy(css = ".bhk-container")
    private List<WebElement> bhkCards;

    @FindBy(css = ".bhk-container .cat_head")
    private List<WebElement> bhkNumbers;

    @FindBy(css = ".bhk-container p.bhk-para")
    private List<WebElement> propertyCounts;

    // ***** METHODS *****

    // Scroll into BHK section
    public void scrollToBhkSection() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", bhkSection);
    }

    public String getSectionHeading() {
        return bhkSectionHeading.getText().trim();
    }

    public String getSectionDescription() {
        return bhkSectionDescription.getText().trim();
    }

    public int getTotalBhkCards() {
        return bhkCards.size();
    }

    public String getBhkNumber(int index) {
        return bhkNumbers.get(index).getText().trim();
    }

    public String getPropertyCountText(int index) {
        return propertyCounts.get(index).getText().trim();
    }

    public void clickBhkCard(int index) {
        closeChatWidgetIfPresent();

        WebElement element = bhkCards.get(index);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);

        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(element))
                    .click();

        } catch (Exception e) {
            js.executeScript("arguments[0].click();", element);  // Force click
        }
    }



    public boolean isBhkCardDisplayed(int index) {
        return bhkCards.get(index).isDisplayed();
    }
    // Closes or hides the chat widget if it overlaps the BHK cards
    public void closeChatWidgetIfPresent() {
        try {
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));

            for (WebElement iframe : iframes) {
                String title = iframe.getAttribute("title");
                if (title != null && title.toLowerCase().contains("chat")) {

                    JavascriptExecutor js = (JavascriptExecutor) driver;

                    // Hide the iframe using JS
                    js.executeScript("arguments[0].style.display='none';", iframe);
                    System.out.println("Chat widget hidden successfully.");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("No chat widget found or unable to hide.");
        }
    }

}
