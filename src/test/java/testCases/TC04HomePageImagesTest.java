package testCases;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import testBase.BaseClass;

import java.util.List;

public class TC04HomePageImagesTest extends BaseClass {

    @Test
    public void validateHomePageImagesWithPopupHandling() {

        HomePage hp = new HomePage(driver);
        List<WebElement> images = hp.getAllImages();

        System.out.println("Total Images Found: " + images.size());

        int index = 1;

        for (WebElement img : images) {

            System.out.println("\n==============================");
            System.out.println(" Checking Image #" + index);
            System.out.println("==============================");

            //hp.handlePopupQuick();

            // Scroll in view
            hp.scrollToImage(img);

            //  hp.handlePopupQuick();

            boolean isLoaded = false;
            String tag = img.getTagName();
            String html = img.getAttribute("outerHTML");

            /* ==========================
                CASE 1: Standard <img>
            =========================== */
            if ("img".equals(tag)) {
                isLoaded = hp.isImageLoaded(img);
            }

            /* ==========================
                CASE 2: CSS background
            =========================== */
            else if (hp.hasBackgroundImage(img)) {
                isLoaded = hp.isCSSImageURLValid(img);
            }

            /* ==========================
                CASE 3: ICON
            =========================== */
            if (!isLoaded && hp.isIcon(img)) {
                System.out.println("⚠ Skipped Icon/SVG → Valid non-image graphics");
                index++;
                continue;
            }

            if (!isLoaded) {
                Assert.fail("BROKEN IMAGE → " + html);
            }

            // ALT TEXT
            if ("img".equals(tag)) {
                String alt = hp.getAltText(img);
                System.out.println("ALT TEXT → " + (alt.isEmpty() ? "[EMPTY]" : alt));
            }

            System.out.println("✔ Image Validated Successfully");
            index++;
        }
    }
}
