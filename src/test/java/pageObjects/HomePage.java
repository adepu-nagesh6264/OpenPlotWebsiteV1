package pageObjects;

import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage extends BasePage {

    private final JavascriptExecutor js;
    private final Actions actions;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
    }

    /* ========================================
                WEB ELEMENTS
    ========================================== */

    @FindBy(xpath = "//div[@title='Signup or Login']")
    public WebElement loginBtn;

    @FindBy(xpath = "//div[@class='accept-cookies-btn']")
    public WebElement cookiesBtn;

    @FindBy(xpath = "//a[@class='sign_initial_logo']")
    public WebElement profileImage;

    @FindBy(id = "tawk-mpreview-close")
    WebElement welcomePopupClose;

    @FindBy(xpath = "//div[@class='tawk-text-regular-3']")
    WebElement welcomePopupText;

    @Getter
    @FindBy(tagName = "img")
    List<WebElement> allImages;

    private List<WebElement> cachedImages = null;


    /* ========================================
                LOGIN SECTION
    ========================================== */

    public void clickOnLoginBtn() {
        safeClick(loginBtn);
    }

    public void clickOnCookiesBtn() {
        safeClick(cookiesBtn);
    }

    public boolean waitForProfileImage() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.visibilityOf(profileImage));
            return profileImage.isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isLoginBtnDisplayed() {
        return isDisplayed(loginBtn);
    }


    /* ========================================
                SAFE OPERATIONS
    ========================================== */

    private void safeClick(WebElement element) {
        try {
            if (element != null && element.isDisplayed()) {
                element.click();
            }
        } catch (Exception ignored) {
        }
    }

    private boolean isDisplayed(WebElement element) {
        try {
            return element != null && element.isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }


    /* ========================================
                POPUP HANDLING
    ========================================== */

    public void handlePopupQuick() {
        try {
            if (welcomePopupClose != null && welcomePopupClose.isDisplayed()) {
                welcomePopupClose.click();
            }
        } catch (Exception ignored) {
        }
    }


    /* ========================================
                IMAGE VALIDATION UTILITIES
    ========================================== */

    public void scrollToImage(WebElement img) {
        try {
            js.executeScript(
                    "arguments[0].scrollIntoView({behavior:'instant', block:'center'});",
                    img
            );
        } catch (Exception ignored) {
        }
    }

    public boolean isImageLoaded(WebElement img) {
        try {
            Long width = (Long) js.executeScript("return arguments[0].naturalWidth;", img);
            return width != null && width > 1;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean hasBackgroundImage(WebElement e) {
        try {
            String bg = e.getCssValue("background-image");
            return bg != null && !bg.equals("none");
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isCSSImageURLValid(WebElement e) {
        try {
            String bg = e.getCssValue("background-image");
            return bg != null && bg.contains("http");
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isIcon(WebElement e) {
        try {
            String html = e.getAttribute("outerHTML");
            return html.contains("svg")
                    || html.contains("icon")
                    || html.contains("support-phone-call")
                    || e.getCssValue("mask-image").contains("url");
        } catch (Exception ex) {
            return false;
        }
    }

    public String getAltText(WebElement img) {
        try {
            String alt = img.getAttribute("alt");
            return alt == null ? "" : alt.trim();
        } catch (Exception ex) {
            return "";
        }
    }

    public List<WebElement> getAllImages() {
        if (cachedImages == null) {
            cachedImages = allImages;
            System.out.println("📸 Cached " + cachedImages.size() + " images");
        }
        return cachedImages;
    }
}
