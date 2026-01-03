package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomePagePropertyByTypePerfectMatch {

    WebDriver driver;
    JavascriptExecutor js;
    WebDriverWait wait;

    public HomePagePropertyByTypePerfectMatch(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // ================== Section Header ==================

    @FindBy(xpath = "//section[contains(@class,'category-type')]")
    WebElement propertyByTypeSection;

    @FindBy(xpath = "//section[contains(@class,'category-type')]//h3")
    WebElement sectionHeading;

    @FindBy(xpath = "//section[contains(@class,'category-type')]//p[contains(@class,'mb-2')]")
    WebElement sectionDescription;

    // ================== Carousel Items ==================

    @FindBy(xpath = ".//div[contains(@class,'category-type-box')]")
    List<WebElement> propertyTypeCards;

    @FindBy(xpath = "//div[contains(@class,'carousel_item')]//div[contains(@class,'category-type-text')]")
    List<WebElement> propertyTypeNames;

    @FindBy(xpath = "//span[@class='villa_count']")
    List<WebElement> propertyCounts;

    @FindBy(xpath = "//div[contains(@class,'category-type-box')]//img")
    List<WebElement> propertyImages;

    @FindBy(xpath = "//div[contains(@class,'carousel_item')]")
    List<WebElement> carouselItems;

    // ================== Carousel Navigation ==================

    @FindBy(css = "button.pbtb-next")
    WebElement nextBtn;

    @FindBy(css = "button.pbtb-prev")
    WebElement prevBtn;

    @FindBy(xpath ="//div[contains(@class,'carousel_item') and contains(@class,'active')]//div[contains(@class,'category-type-box')]")
    WebElement activeCard;
    public void clickOnActiveCard(){
        activeCard.click();
    }
    // ================== Methods ==================

    public boolean isSectionDisplayed() {
        return propertyByTypeSection.isDisplayed();
    }

    public String getSectionHeadingText() {
        return sectionHeading.getText();
    }

    public String getSectionDescription() {
        return sectionDescription.getText();
    }

    public int getTotalPropertyTypes() {
        return propertyTypeCards.size();
    }

    public boolean arePropertyImagesDisplayed() {
        for (WebElement img : propertyImages) {
            if (!img.isDisplayed()) return false;
        }
        return true;
    }

    public boolean arePropertyCountsDisplayed() {
        for (WebElement count : propertyCounts) {
            if (count.getText().trim().isEmpty()) return false;
        }
        return true;
    }

    // ================== Carousel Controls (JS Click) ==================

    public void clickNext(WebDriver driver) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By nextBtnBy = By.cssSelector("button.pbtb-next");

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                WebElement next = wait.until(
                        ExpectedConditions.elementToBeClickable(nextBtnBy));

                js.executeScript("arguments[0].click();", next);
                return;

            } catch (StaleElementReferenceException e) {
                if (attempt == 3) {
                    throw e;
                }
            }
        }
    }



    public void clickPrevious(WebDriver driver) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By prevBtnBy = By.cssSelector("button.pbtb-prev");

        wait.until(ExpectedConditions.presenceOfElementLocated(prevBtnBy));

        WebElement prev = driver.findElement(prevBtnBy);

        WebElement section = driver.findElement(
                By.xpath("//section[contains(@class,'category-type')]"));

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", section);

        try { Thread.sleep(300); } catch (InterruptedException ignored) {}

        js.executeScript("arguments[0].click();", prev);
    }


    // ================== 🔥 KEY FIX: Card Click ==================

    public void clickPropertyTypeCard(WebDriver driver, int index) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By cardsLocator = By.xpath("//div[contains(@class,'category-type-box')]");

        // ✅ ALWAYS re-locate elements
        List<WebElement> cards =
                wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(cardsLocator, index));

        WebElement card = cards.get(index);

        // ✅ Scroll + click atomically
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", card);

        wait.until(ExpectedConditions.elementToBeClickable(card));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", card);
    }



    // ⚠️ METHOD KEPT (but made safe)
    public void clickPropertyTypeCard(int index) {
        WebElement card = propertyTypeCards.get(index);
        js.executeScript("arguments[0].click();", card);
    }

    public String getPropertyTypeName(int index) {

        By nameLocator = By.xpath(
                "(//div[contains(@class,'category-type-box')]//div[contains(@class,'category-type-text')])");

        return driver.findElements(nameLocator)
                .get(index)
                .getText()
                .trim();
    }


    // ================== Step 4 Support ==================

    public void waitForCarouselToLoad() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class,'category-type-box')]")));
    }

    public int getPropertyCardCount() {
        return carouselItems.size();
    }
    By resultCards = By.xpath("//div[contains(@class,'property-card')]");
    public List<String> getAllPropertyTypeNames() {
        return propertyTypeCards.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void clickPropertyTypeCardByName(WebDriver driver, String name) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        By cardLocator = By.xpath(
                "//div[contains(@class,'category-type-text') " +
                        "and contains(normalize-space(.),'" +
                        name.replaceAll("\\d+\\s*", "") +
                        "')]/ancestor::div[contains(@class,'category-type-box')]"
        );

        int maxAttempts = 5; // prevents infinite loop

        for (int i = 0; i < maxAttempts; i++) {

            List<WebElement> cards = driver.findElements(cardLocator);

            if (!cards.isEmpty()) {
                WebElement card = cards.get(0);

                js.executeScript("arguments[0].scrollIntoView({block:'center'});", card);
                js.executeScript("arguments[0].click();", card);
                return;
            }

            // 👉 Card not visible yet → move carousel
            clickNext(driver);

            // small wait for animation
            try {
                Thread.sleep(700);
            } catch (InterruptedException ignored) {}
        }

        throw new NoSuchElementException(
                "Property card not found in carousel after scrolling: " + name
        );
    }


    public void clickPropertyTypeCardByVisibleText(WebDriver driver, String name) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement card = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(
                        "//div[contains(@class,'category-type-box')]//div[contains(@class,'category-type-text')]" +
                                "[contains(normalize-space(.),'" + name.replaceAll("\\d+\\s*", "") + "')]/ancestor::div[contains(@class,'category-type-box')]"
                )
        ));

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", card);
        js.executeScript("arguments[0].click();", card);
    }
    public List<String> getAllPropertyTypeNames(WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//section[contains(@class,'category-type')]")));

        List<WebElement> nameElements = driver.findElements(
                By.xpath("//div[contains(@class,'category-type-text')]"));

        List<String> names = new ArrayList<>();

        for (WebElement el : nameElements) {
            names.add(el.getText().trim());
        }

        return names;


    }

}
