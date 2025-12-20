package testBase;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

public class BaseClass {

    public static WebDriver driver;
    public static Properties p;
    public Logger logger;
    public String parentWindowID;

    @BeforeSuite
    public void setup() throws Exception {

        // Load config
        FileReader file = new FileReader("./src/test/resources/config.properties");
        p = new Properties();
        p.load(file);

        logger = LogManager.getLogger(this.getClass());

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");


        // FORCE VISIBLE BROWSER (even in Jenkins)
        options.addArguments("--start-maximized");

        System.out.println("🚀 Launching VISIBLE Chrome browser");

        driver = new ChromeDriver(options);

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        driver.get(p.getProperty("URL"));
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            // driver.quit();
        }
    }

    // Screenshot utility
    public String captureScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String destDir = System.getProperty("user.dir") + "/screenshots/";
        new File(destDir).mkdirs();

        String destPath = destDir + testName + "_" + timestamp + ".png";

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(destPath));
        } catch (Exception e) {
            System.out.println("Screenshot error: " + e.getMessage());
        }

        return "./screenshots/" + testName + "_" + timestamp + ".png";
    }

    // Window utilities
    public void storeParentWindow() {
        parentWindowID = driver.getWindowHandle();
    }

    public void switchToChildWindow() {
        Set<String> allWindows = driver.getWindowHandles();
        for (String windowID : allWindows) {
            if (!windowID.equals(parentWindowID)) {
                driver.switchTo().window(windowID);
                break;
            }
        }
    }

    public void switchToParentWindow() {
        driver.switchTo().window(parentWindowID);
    }

    public void closeCookiePopup() {
        try {
            WebElement close = driver.findElement(
                    By.xpath("//div[@class='cookies-modal-overlay']//button"));
            close.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Cookie popup not displayed.");
        }
    }
}
