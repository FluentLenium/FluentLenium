package org.fluentlenium.e2e.test;

import static org.fluentlenium.utils.ImageUtils.toBufferedImage;
import static org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile;
import static org.testng.AssertJUnit.assertTrue;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.testng.annotations.Test;

public class AlertScreenshotTest extends E2ETest {

    public static final String MY_TEST_SCREENSHOT_PNG = "myTestScreenshot.png";

    @Test
    public void checkPolling() throws IOException {
        goTo(getAbsoluteUrlFromFile("alert.html"));
        await().until(el("#alertBox")).displayed();
        el("#alertBox").click();

        takeScreenshot(MY_TEST_SCREENSHOT_PNG);

        assertTrue("The screenshots are not the same",
                    compareImages(toBufferedImage(this.getClass().getResource("/basescreenshot.png").getPath()),
                        toBufferedImage("myTestScreenshot.png"))
        );
    }

    private static boolean compareImages(BufferedImage originalScreenshot, BufferedImage currentScreenshot) throws IOException {
        ByteArrayOutputStream baosOriginal = new ByteArrayOutputStream();
        ByteArrayOutputStream baosCurrent = new ByteArrayOutputStream();
        ImageIO.write(originalScreenshot, "png", baosOriginal);
        baosOriginal.flush();
        byte[] imageInByteOriginal = baosOriginal.toByteArray();
        baosOriginal.close();
        ImageIO.write(currentScreenshot, "png", baosCurrent);
        baosCurrent.flush();
        byte[] imageInByteCurrent = baosCurrent.toByteArray();
        baosCurrent.close();
        boolean difference = Arrays.equals(imageInByteOriginal, imageInByteCurrent);
        return difference;
    }
}


