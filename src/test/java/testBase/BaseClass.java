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

        boolean isJenkins = System.getenv("JENKINS_HOME") != null;
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("HEADLESS", "false"));

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");

        // 🔥 VERY IMPORTANT (Fix for taskbar issue)
        options.addArguments("--disable-features=CalculateNativeWinOcclusion");
        options.addArguments("--force-device-scale-factor=1");
        options.addArguments("--window-position=0,0");

        if (isJenkins || isHeadless) {
            // 🔥 CI MODE
            System.out.println("🧪 Running in CI mode → HEADLESS");
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
        } else {
            // 🧑‍💻 LOCAL MODE
            System.out.println("🖥️ Running locally → VISIBLE BROWSER");
            options.addArguments("--start-maximized");
        }

        driver = new ChromeDriver(options);

        // 🔥 FORCE maximize (this is critical)
        driver.manage().window().maximize();

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        driver.get(p.getProperty("URL"));
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ================= Utilities =================

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

        return destPath;
    }

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

}
