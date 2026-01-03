package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static testBase.BaseClass.logger;

public class PostPropertyPage extends BasePage {

    WebDriverWait wait;

    public PostPropertyPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // -------------------------
    // FIXED FIELDS
    // -------------------------

    @FindBy(xpath = "//button[contains(@class,'post-property-btn')]")
    public WebElement postPropertyBtn;

    @FindBy(id = "HouseNumber") private WebElement houseNumberTxt;
    @FindBy(id = "Area") private WebElement areaTxt;
    @FindBy(id = "BuildupArea") private WebElement buildupAreaTxt;
    @FindBy(id = "CarpetArea") private WebElement carpetAreaTxt;

    @FindBy(xpath = "//button[@type='button' and text()='Ready To Move']")
    private WebElement readyToMoveBtn;

    @FindBy(id = "Age") private WebElement ageTxt;
    @FindBy(id = "RoadWidth") private WebElement roadWidthTxt;
    @FindBy(id = "Price") private WebElement priceTxt;
    @FindBy(id = "BookingAmount") private WebElement bookingAmountTxt;

    @FindBy(id = "RegistrationNo") private WebElement registrationNoTxt;
    @FindBy(id = "DocOwnerName") private WebElement docOwnerNameTxt;
    @FindBy(id = "PortalName") private WebElement portalNameTxt;

    @FindBy(id = "Pincode") private WebElement pincodeTxt;
    @FindBy(id = "Locality") private WebElement localityDropdown;
    @FindBy(id = "Address") private WebElement addressTxt;

    @FindBy(xpath = "//textarea[@class='form_input teaxt_area_list']")
    private WebElement descriptionTxt;

    @FindBy(id = "btnSubmit")
    private WebElement PostPropertySubmitBtn;

    @FindBy(xpath = "//button[contains(text(),'Select Images')]")
    public WebElement selectImagesBtn;

    @FindBy(xpath = "//button[contains(text(),'Save') and contains(text(),'Continue')]")
    public WebElement saveAndContinueBtn;

    @FindBy(xpath = "//div[@class='prop-list-success-home-btn']")
    public WebElement backToHomePageBtn;

    @FindBy(xpath="//button[normalize-space()='Skip Images']")
    WebElement SkipImagesBtn;

    // -------------------------
    // STABILIZED CLICK METHODS
    // -------------------------

    public void clickButton(String dataId, String text) {

        By locator = By.xpath(
                // Case 1: button itself has data-id
                "//button[@data-id='" + dataId + "' and normalize-space(.)='" + text + "']" +

                        // Case 2: button inside container with data-id
                        " | //*[@data-id='" + dataId + "']//button[normalize-space(.)='" + text + "']" +

                        // Case 3: hidden input name maps to buttons (FurnitureStatus, FloorType, etc.)
                        " | //input[@name='" + dataId + "']/preceding-sibling::*//button[normalize-space(.)='" + text + "']" +
                        " | //input[@name='" + dataId + "']/following-sibling::*//button[normalize-space(.)='" + text + "']"
        );

        WebElement ele = wait.until(
                ExpectedConditions.presenceOfElementLocated(locator)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", ele
        );

        try {
            wait.until(ExpectedConditions.elementToBeClickable(ele));
            ele.click();
        } catch (Exception e) {
            jsClick(ele);
        }
    }




    public void clickTab(String tabName) {
        By locator = By.xpath("//button[contains(text(),'" + tabName + "')]");
        WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollToElement(ele);
        try {
            ele.click();
        } catch (Exception e) {
            jsClick(ele);
        }
    }

    // -------------------------
    // AMENITIES
    // -------------------------

    public void clickAmenity(String amenityName) {
        By locator = By.xpath("//span[normalize-space()='" + amenityName + "']/preceding-sibling::input");
        WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        scrollToElement(ele);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(ele));
            ele.click();
        } catch (Exception e) {
            jsClick(ele);
        }
    }

    public void selectFacility(String facilityName) {
        By locator = By.xpath("//span[@class='checkbox_text' and normalize-space()='" + facilityName + "']/preceding-sibling::input");
        WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        scrollToElement(ele);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(ele));
            ele.click();
        } catch (Exception e) {
            jsClick(ele);
        }
    }

    // -------------------------
    // ENTER TEXT
    // -------------------------

    public void enter(WebElement ele, String text) {
        wait.until(ExpectedConditions.visibilityOf(ele));
        scrollToElement(ele);
        ele.clear();
        ele.sendKeys(text);
    }

    // -------------------------
    // ACTION METHODS
    // -------------------------

    public void clickPostProperty() {
        wait.until(ExpectedConditions.elementToBeClickable(postPropertyBtn));
        scrollToElement(postPropertyBtn);
        jsClick(postPropertyBtn);
    }

    public void enterHouseNumber() { enter(houseNumberTxt, randomeNumber()); }
    public void enterArea() { enter(areaTxt, randomeNumber()); }
    public void enterBuildingArea() { enter(buildupAreaTxt, randomeNumber()); }
    public void enterCarpetArea() { enter(carpetAreaTxt, randomeNumber()); }

    public void clickOnReadyToMove() {
        wait.until(ExpectedConditions.elementToBeClickable(readyToMoveBtn));
        scrollToElement(readyToMoveBtn);
        jsClick(readyToMoveBtn);
    }

    public void enterAge() { enter(ageTxt, "2"); }
    public void enterRoadWidth() { enter(roadWidthTxt, "30"); }
    public void enterPrice() { enter(priceTxt, "10000000"); }
    public void enterBookingAmount() { enter(bookingAmountTxt, "1000000"); }
    public void enterRegNo() { enter(registrationNoTxt, randomeNumber()); }
    public void enterDocOwnerName() { enter(docOwnerNameTxt, randomeString() + " Owner"); }
    public void enterPortalName() { enter(portalNameTxt, "Bhubharathi"); }
    public void enterPincode() { enter(pincodeTxt, "500088"); }

    public void selectLocality(int index) {
        scrollToElement(localityDropdown);
        new Select(localityDropdown).selectByIndex(index);
    }

    public void enterAddress() { enter(addressTxt, "Narapally"); }
    public void enterDescription() { enter(descriptionTxt, "Auto Description " + randomeString()); }

    public void clickSubmit() {
        scrollToElement(PostPropertySubmitBtn);
        wait.until(ExpectedConditions.elementToBeClickable(PostPropertySubmitBtn));
        jsClick(PostPropertySubmitBtn);
    }

    public void clickOnSelectImagesBtn() {
        scrollToElement(selectImagesBtn);
        jsClick(selectImagesBtn);
    }

    public void clickOnSaveAndContinueBtn() {
        scrollToElement(saveAndContinueBtn);
        jsClick(saveAndContinueBtn);
    }

    public void clickOnBackToHomePageBtn() throws InterruptedException {
        Thread.sleep(4000);
        scrollToElement(backToHomePageBtn);
        jsClick(backToHomePageBtn);
    }

    public boolean isBackToHomePageBtnDisplayed() {
        try {
            return backToHomePageBtn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickOnSkipImagesButton(){
        scrollToElement(SkipImagesBtn);
        jsClick(SkipImagesBtn);
    }
//Furniture Status
By unfurnishedBtn = By.xpath("//button[normalize-space()='Unfurnished']");
    By semiFurnishedBtn = By.xpath("//button[normalize-space()='Semi Furnished']");
    By fullyFurnishedBtn = By.xpath("//button[normalize-space()='Fully Furnished']");
    By furnitureStatusInput = By.name("FurnitureStatus");
    public void selectUnfurnished() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(unfurnishedBtn));
        scrollToElement(btn);
        btn.click();
    }

    public void selectSemiFurnished() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(semiFurnishedBtn));
        scrollToElement(btn);
        btn.click();
    }

    public void selectFullyFurnished() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(fullyFurnishedBtn));
        scrollToElement(btn);
        btn.click();
    }

//floor type
By floorTypeInput = By.name("FloorType");
    public void selectFloorType(String floorType) {

        By floorTypeBtn = By.xpath(
                "//input[@name='FloorType']/preceding-sibling::button[normalize-space()='" + floorType + "']" +
                        " | //input[@name='FloorType']/following-sibling::button[normalize-space()='" + floorType + "']"
        );

        WebElement btn = wait.until(
                ExpectedConditions.presenceOfElementLocated(floorTypeBtn)
        );

        scrollToElement(btn);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(btn));
            btn.click();
        } catch (Exception e) {
            jsClick(btn);
        }
    }
//Select button
public void selectYBtnOption(String optionText) {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    // 1️⃣ Locate the button directly (this is the stable element)
    WebElement optionBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[contains(@class,'y_btn') and normalize-space()='" + optionText + "']")
    ));

    // 2️⃣ Scroll safely
    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", optionBtn
    );

    try { Thread.sleep(300); } catch (InterruptedException ignored) {}

    // 3️⃣ Ensure clickable
    wait.until(ExpectedConditions.elementToBeClickable(optionBtn));

    // 4️⃣ JS click (no interception issues)
    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].click();", optionBtn
    );

    logger.info("✅ Selected option: " + optionText);
}



}


