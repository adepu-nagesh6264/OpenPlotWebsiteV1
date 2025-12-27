package testBase;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseClass {

    public static WebDriver driver;
    public static Properties p;
    public static Logger logger;
    public String parentWindowID;

    @BeforeSuite
    public void setup() throws Exception {

        // ================= Load config =================
        FileReader file = new FileReader("./src/test/resources/config.properties");
        p = new Properties();
        p.load(file);

        logger = LogManager.getLogger(BaseClass.class);

        boolean isHeadless = Boolean.parseBoolean(
                System.getProperty("HEADLESS", "false")
        );

        ChromeOptions options = new ChromeOptions();

        // ================= Mandatory Stability Options =================
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");

        // ================= Windows Visibility / Focus Fixes =================
        options.addArguments("--disable-features=CalculateNativeWinOcclusion");
        options.addArguments("--disable-backgrounding-occluded-windows");
        options.addArguments("--disable-renderer-backgrounding");
        options.addArguments("--force-device-scale-factor=1");
        options.addArguments("--window-position=0,0");

        // ================= Browser Mode =================
        if (isHeadless) {
            logger.info("🤖 Running in HEADLESS mode (CI)");
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
        } else {
            logger.info("🖥️ Running with VISIBLE browser");

            // 🔥 Best option to avoid taskbar-only issue
            // Comment this if you don't want app mode
            options.addArguments("--app=" + p.getProperty("URL"));

            options.addArguments("--start-maximized");
        }

        driver = new ChromeDriver(options);

        // ================= Force Foreground (Best effort) =================
        try {
            driver.manage().window().setPosition(new Point(0, 0));
            driver.manage().window().setSize(new Dimension(1920, 1080));
            driver.manage().window().maximize();

            ((JavascriptExecutor) driver).executeScript("window.focus();");
        } catch (Exception e) {
            logger.warn("Window focus not allowed by OS (expected on Windows services)");
        }

        // ================= Global Browser Settings =================
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));

        driver.get(p.getProperty("URL"));
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws InterruptedException {
        if (driver != null) {
            Thread.sleep(5000);
            driver.quit();
            logger.info("🛑 Browser closed");
        }
    }

    // ================= Screenshot Utility =================

    public String captureScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String destDir = System.getProperty("user.dir") + "/screenshots/";
        new File(destDir).mkdirs();

        String destPath = destDir + testName + "_" + timestamp + ".png";

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(destPath));
        } catch (Exception e) {
            logger.error("Screenshot capture failed", e);
        }
        return destPath;
    }

    // ================= Window Handling =================

    public void storeParentWindow() {
        parentWindowID = driver.getWindowHandle();
    }

    public void switchToChildWindow() {
        for (String windowID : driver.getWindowHandles()) {
            if (!windowID.equals(parentWindowID)) {
                driver.switchTo().window(windowID);
                break;
            }
        }
    }

    public void switchToParentWindow() {
        driver.switchTo().window(parentWindowID);
    }

    // ================= Cookie Handler =================

    public void handleCookies() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));

            By cookieBtn = By.xpath(
                    "//div[contains(@class,'accept-cookies-btn') or " +
                            "contains(text(),'Accept') or contains(text(),'Agree')]"
            );

            WebElement acceptBtn =
                    wait.until(ExpectedConditions.elementToBeClickable(cookieBtn));

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", acceptBtn);

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", acceptBtn);

            logger.info("✅ Cookies popup accepted");

        } catch (TimeoutException e) {
            logger.info("ℹ️ Cookies popup not displayed");
        } catch (Exception e) {
            logger.warn("⚠️ Cookies popup present but not clickable", e);
        }
    }

    // ================= Safe Click =================

    public void safeClick(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", element);

            Thread.sleep(200);
            element.click();

        } catch (ElementClickInterceptedException e) {
            logger.warn("⚠️ Click intercepted – using JS click");
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            throw new RuntimeException("Unable to click element safely", e);
        }
    }


    //Close chat window
    public void closeChatWidgetIfPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // Switch to chat iframe
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                    By.xpath("//iframe[@title='chat widget' or contains(@src,'tawk')]")
            ));

            // Click close button
            WebElement closeBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//i[contains(@class,'tawk-icon-x')]")
                    )
            );

            closeBtn.click();
            logger.info("❌ Chat widget closed");

            // Switch back to main page
            driver.switchTo().defaultContent();

        } catch (Exception e) {
            driver.switchTo().defaultContent();
            logger.info("ℹ️ Chat widget not present");
        }
    }

    // Close welcome message
    public void closeTawkChatIfPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // 1️⃣ Switch to Tawk chat iframe
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                    By.xpath("//iframe[contains(@src,'tawk') or @title='chat widget']")
            ));

            // 2️⃣ Locate close (X) icon
            WebElement closeIcon = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//i[contains(@class,'tawk-icon-x')]")
                    )
            );

            // 3️⃣ Click using JS (most reliable)
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", closeIcon);

            logger.info("❌ Tawk chat widget closed");

            // 4️⃣ Switch back to main content
            driver.switchTo().defaultContent();

        } catch (Exception e) {
            driver.switchTo().defaultContent();
            logger.info("ℹ️ Tawk chat widget not present");
        }
    }

    public void disableChatWidgetCompletely() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Disable pointer events on all iframes
            js.executeScript(
                    "document.querySelectorAll('iframe').forEach(i => i.style.pointerEvents='none');"
            );

            // Specifically hide Tawk iframe if present
            js.executeScript(
                    "document.querySelectorAll('iframe[title=\"chat widget\"]').forEach(i => i.style.display='none');"
            );

            logger.info("🚫 Chat widget disabled successfully");
        } catch (Exception e) {
            logger.info("ℹ️ Chat widget not present");
        }
    }

}

