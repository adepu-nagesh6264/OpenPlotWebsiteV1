package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class HomePageSearch extends BasePage {

    private final JavascriptExecutor js;
    private final Actions actions;
    private final WebDriverWait wait;

    public HomePageSearch(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    // -------------------------------------------------------------------------
    // REUSABLE UTILITIES (Scroll + Safe Click + Retry + Wait)
    // -------------------------------------------------------------------------

    /** Scroll element into center */
    public void scrollToElement(WebElement element) {
        try {
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
            Thread.sleep(150);
        } catch (Exception ignored) {}
    }

    /** Safe click with scroll + wait + JS fallback */
    public void safeClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            scrollToElement(element);

            try {
                wait.until(ExpectedConditions.elementToBeClickable(element)).click();
            } catch (Exception clickErr) {
                js.executeScript("arguments[0].click();", element);
            }
        } catch (Throwable finalErr) {
            throw new RuntimeException("Failed to click element: " + element, finalErr);
        }
    }

    /** Safe typing (clear + sendKeys + fallback) */
    public void safeType(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        scrollToElement(element);

        try {
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            js.executeScript("arguments[0].value='" + text + "';", element);
        }
    }

    /** Generic reusable method for ANY property checkbox */
    public void clickPropertyType(String propertyName) {
        String xpath = "//div[contains(@class,'property-type-checkbox') and contains(.,'"
                + propertyName + "')]/input";
        WebElement checkbox = driver.findElement(By.xpath(xpath));
        safeClick(checkbox);
    }

    /** Wait until element visible */
    public void waitVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /** Wait until page JS is fully loaded */
    public void waitPageLoaded() {
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState=='complete'"));
    }

    // -------------------------------------------------------------------------
    // ALL ORIGINAL WEBELEMENTS (UNCHANGED)
    // -------------------------------------------------------------------------

    @FindBy(xpath = "//span[contains(@class,'tab-option') and contains(text(),'BUY')]")
    WebElement buyRadiobtn;

    @FindBy(xpath = "//span[contains(@class,'tab-option') and contains(text(),'RENT')]")
    WebElement rentRadiobtn;

    @FindBy(xpath = "//span[contains(@class,'tab-option') and contains(text(),'All Properties')]")
    WebElement allPropertiesDrpdwnbtn;

    @FindBy(xpath="//span[@class='suggestion-type' and text()='City']")
    WebElement CitySearchDropdown;

    @FindBy(xpath = "//button[@class='search-btn']")
    WebElement SearchButton;

    @FindBy(xpath = "//div[@class='search-bar-input-area']//input")
    WebElement inputSearchCityTxt;

    @FindBy(xpath = "//h6[contains(normalize-space(), 'Properties for') and contains(normalize-space(), 'BUY') and contains(normalize-space(), 'in')]")
    WebElement headerTxtBUY;

    @FindBy(xpath = "//h6[contains(normalize-space(), 'Properties for') and contains(normalize-space(), 'RENT') and contains(normalize-space(), 'in')]")
    WebElement headerTxtRENT;

    @FindBy(xpath = "//span[contains(@class,'clear_search_all_f')]")
    WebElement clearButton;

    // All checkbox elements (unchanged)
    @FindBy(xpath = "//div[contains(@class,'property-type-checkbox') and contains(.,'Independent House')]/input")
    WebElement independentHouseCheckbox;

    @FindBy(xpath = "//div[contains(@class,'property-type-checkbox') and contains(.,'Apartment')]/input")
    WebElement apartmentCheckbox;

    @FindBy(xpath = "//div[contains(@class,'property-type-checkbox') and contains(.,'Independent Floor')]/input")
    WebElement independentFloorCheckbox;

    @FindBy(xpath = "//div[contains(@class,'property-type-checkbox') and contains(.,'Plot')]/input")
    WebElement plotCheckbox;

    @FindBy(xpath = "//div[contains(@class,'property-type-checkbox') and contains(.,'1RK')]/input")
    WebElement oneRKCheckbox;

    @FindBy(xpath = "//div[contains(@class,'property-type-checkbox') and contains(.,'Villa')]/input")
    WebElement villaCheckbox;

    @FindBy(xpath = "//div[contains(@class,'property-type-checkbox') and contains(.,'Farm House')]/input")
    WebElement farmHouseCheckbox;

    @FindBy(xpath = "//div[contains(@class,'property-type-checkbox') and contains(.,'Office')]/input")
    WebElement officeCheckbox;

    @FindBy(xpath = "//div[contains(@class,'property-type-checkbox') and contains(.,'Store/Retail')]/input")
    WebElement storeRetailCheckbox;

    @FindBy(xpath = "//div[contains(@class,'property-type-checkbox') and contains(.,'Storage')]/input")
    WebElement storageCheckbox;

    @FindBy(xpath = "//div[contains(@class,'property-type-checkbox') and contains(.,'Industry')]/input")
    WebElement industryCheckbox;

    @FindBy(xpath = "//div[contains(@class,'property-type-checkbox') and contains(.,'Venture')]/input")
    WebElement ventureCheckbox;

    @FindBy(xpath = "//div[contains(@class,'property-type-checkbox') and contains(.,'Project')]/input")
    WebElement projectCheckbox;

    @FindBy(xpath = "//div[contains(@class,'property-type-checkbox') and contains(.,'Land')]/input")
    WebElement landCheckbox;

    @FindBy(xpath = "//div[contains(@class,'property-type-checkbox') and contains(.,'Other')]/input")
    WebElement otherCheckbox;

    // -------------------------------------------------------------------------
    // ALL PUBLIC METHODS (UNCHANGED NAMES, optimized internally)
    // -------------------------------------------------------------------------

    public void clickOnBuyRadioButton() { safeClick(buyRadiobtn); }
    public void clickOnRentRadioButton() { safeClick(rentRadiobtn); }
    public void clickOnAllPropertiesDrpdwnbtn() { safeClick(allPropertiesDrpdwnbtn); }
    public void clickOnCitySearchDropdown() { safeClick(CitySearchDropdown); }
    public void clickOnSearchButton() { safeClick(SearchButton); }

    public void enterSearchCity(String searchcity) { safeType(inputSearchCityTxt, searchcity); }

    public String getPropertiesForBuyText() {
        waitVisible(headerTxtBUY);
        return headerTxtBUY.getText();
    }

    public boolean validatePropertiesForBuyText() {
        return getPropertiesForBuyText().contains("Properties for BUY in");
    }

    public String getPropertiesForRentText() {
        waitVisible(headerTxtRENT);
        return headerTxtRENT.getText();
    }

    public boolean validatePropertiesForRentText() {
        return getPropertiesForRentText().contains("Properties for RENT in");
    }

    // ---------- Optimized Checkbox Methods (Not removed, only improved) ----------
    public void clickIndependentHouse() { safeClick(independentHouseCheckbox); }
    public void clickApartment() { safeClick(apartmentCheckbox); }
    public void clickIndependentFloor() { safeClick(independentFloorCheckbox); }
    public void clickPlot() { safeClick(plotCheckbox); }
    public void click1RK() { safeClick(oneRKCheckbox); }
    public void clickVilla() { safeClick(villaCheckbox); }
    public void clickFarmHouse() { safeClick(farmHouseCheckbox); }
    public void clickOffice() { safeClick(officeCheckbox); }
    public void clickStoreRetail() { safeClick(storeRetailCheckbox); }
    public void clickStorage() { safeClick(storageCheckbox); }
    public void clickIndustry() { safeClick(industryCheckbox); }
    public void clickVenture() { safeClick(ventureCheckbox); }
    public void clickProject() { safeClick(projectCheckbox); }
    public void clickLand() { safeClick(landCheckbox); }
    public void clickOther() { safeClick(otherCheckbox); }
    public void clickClear() { safeClick(clearButton); }

    //Newly Added Properties
    @FindBy(xpath = "//span[@class='material-symbols-sharp' and text()='arrow_left_alt']")
    WebElement arrowLeftatNewly;
    public void clickOnArrowLeftatNewly(){
        arrowLeftatNewly.click();
    }
    @FindBy(xpath = "//span[@class='material-symbols-sharp' and text()='arrow_right_alt']")
    public WebElement arrowRightatNewly;
    public void clickOnArrowRightatNewly(){
        arrowRightatNewly.click();
    }
    public List<WebElement> getAllCards(WebDriver driver) {
        return driver.findElements(By.cssSelector(".single-card-carousel-track .single-card-carousel-card"));
    }

    public void clickFavOfLastCard(WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until at least one card exists (locator-level wait)
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".single-card-carousel-track .single-card-carousel-card")
        ));

        // Fetch cards fresh each time (fix for React DOM updates)
        List<WebElement> cards = driver.findElements(
                By.cssSelector(".single-card-carousel-track .single-card-carousel-card")
        );

        if (cards.isEmpty()) {
            throw new RuntimeException("No cards found inside the carousel!");
        }

        // Take LAST card
        WebElement lastCard = cards.get(cards.size() - 1);

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lastCard);

        // FAV icon inside last card
        WebElement favIcon = lastCard.findElement(By.cssSelector(".bg-fav-icon img.project-fav-img"));

        // Click with fallback
        try {
            wait.until(ExpectedConditions.elementToBeClickable(favIcon)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", favIcon);
        }

        System.out.println("Clicked FAV icon of LAST card.");
    }
// Recently Added properties

    //  All Recently Added property cards
    @FindBy(css = ".recently-properties-card")
    private List<WebElement> recentlyAddedCards;

    //  Getter method
    public List<WebElement> getRecentlyAddedCards() {
        return recentlyAddedCards;
    }

    //  Click specific card by index
    public void clickRecentlyAddedCard(int index) {
        wait.until(ExpectedConditions.visibilityOfAllElements(recentlyAddedCards));
        recentlyAddedCards.get(index).click();
    }

    //  Click all cards one-by-one (open in same window)
    public void clickAllRecentlyAddedCards() {
        wait.until(ExpectedConditions.visibilityOfAllElements(recentlyAddedCards));
        for (WebElement card : recentlyAddedCards) {
            wait.until(ExpectedConditions.elementToBeClickable(card)).click();
            driver.navigate().back(); // return to home after each click
        }
    }

    // Get all card titles
    public String getCardTitle(int index) {
        WebElement card = recentlyAddedCards.get(index);
        return card.findElement(By.cssSelector(".recently-properties-card-text h6.mb-1")).getText();
    }

    //  Get all card prices
    public String getCardPrice(int index) {
        WebElement card = recentlyAddedCards.get(index);
        return card.findElement(By.cssSelector(".recently-properties-card-text h6.text-red")).getText();
    }
    @FindBy(xpath = "//div[contains(@class,'accept-cookies-btn') and normalize-space()='Accept All Cookies']")
    WebElement acceptCookiesBtn;
    public void closeCookiePopup() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesBtn));
            safeClick(acceptCookiesBtn);
        } catch (Exception e) {
            System.out.println("Cookie popup not displayed → continue");
        }
    }




}
