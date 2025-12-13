package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePageRentalPropertiesPage extends BasePage {
    WebDriver driver;

    public HomePageRentalPropertiesPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ===============================
    // WebElements
    // ===============================

    @FindBy(xpath = "//section[contains(@class,'rent-prop')]")
    private WebElement rentalSection;

    @FindBy(xpath = "//div[contains(@class,'carousel_item')]")
    private List<WebElement> rentalCards;

    @FindBy(xpath = "//button[contains(@class,'prev-button_recently')]")
    private WebElement prevBtn;

    @FindBy(xpath = "//button[contains(@class,'next-button_recently')]")
    private WebElement nextBtn;

    @FindBy(xpath = "//div[@class='accept-cookies-btn']")
    public WebElement cookiesBtn;
    // Each card elements (relative to rentalCards)
    @FindBy(xpath = "//div[contains(@class,'carousel_item') and contains(@class,'active')]")
    private WebElement activeCard;

    private By title = By.xpath(".//h6[@class='s-head']");
    private By price = By.xpath(".//p[contains(@class,'property-price')]");
    private By location = By.xpath(".//p[contains(@class,'long-loc')]");
    private By exploreBtn = By.xpath(".//span[text()='Explore']");


    // ====================================
    // Methods
    // ====================================

    public boolean isRentalSectionDisplayed() {
        return rentalSection.isDisplayed();
    }

    public int getTotalRentalCards() {
        return rentalCards.size();
    }

    public String getActiveTitle() {
        return activeCard.findElement(title).getText().trim();
    }

    public String getActivePrice() {
        return activeCard.findElement(price).getText().trim();
    }

    public String getActiveLocation() {
        return activeCard.findElement(location).getText().trim();
    }



    public void clickExplore(int index) {
        rentalCards.get(index).findElement(exploreBtn).click();
    }
    public void clickOnCookiesBtn() {
        cookiesBtn.click();
    }
    public void clickNext() {
        try {
            // Ensure the button is in view
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});",
                    nextBtn);

            // Wait until button is clickable
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(nextBtn));

            // Try normal click
            nextBtn.click();

        } catch (Exception e) {
            // Fallback: JS click to avoid interception
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextBtn);
        }
    }

    public void clickPrev() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});",
                    prevBtn);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(prevBtn));

            prevBtn.click();

        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", prevBtn);
        }
    }
    // Explore button inside active card
    public boolean isActiveExplorePresent() {
        return activeCard.findElement(exploreBtn).isDisplayed();
    }

    public void clickActiveExplore() {
        WebElement btn = activeCard.findElement(exploreBtn);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});",
                btn);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(btn));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

}
