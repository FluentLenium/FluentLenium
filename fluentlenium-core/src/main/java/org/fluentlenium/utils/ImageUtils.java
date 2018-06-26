package org.fluentlenium.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ImageUtils {

    public static final String ERROR_WHILE_CONVERTING_IMAGE = "Error while converting image";
    private final WebDriver driver;

    public ImageUtils(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public byte[] handleAlertAndTakeScreenshot() {
        String alertText = getDriver().switchTo().alert().getText();
        getDriver().switchTo().alert().accept();

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            BufferedImage screenshotImage = ImageIO.read(scrFile);
            BufferedImage alertImage = generateAlertImageWithLogo(alertText, screenshotImage.getWidth());
            FileUtils.deleteQuietly(scrFile);
            return toByteArray(stitchImages(screenshotImage, alertImage, false));
        } catch (IOException e) {
            throw new RuntimeException("Error while reading screenshot file.", e);
        }
    }

    private BufferedImage toBufferedImage(String fileName) {
        InputStream is = this.getClass().getResourceAsStream(fileName);
        try {
            BufferedImage image = ImageIO.read(is);
            is.close();
            return image;
        } catch (IOException e) {
            throw new RuntimeException(ERROR_WHILE_CONVERTING_IMAGE, e);
        }
    }

    private byte[] toByteArray(BufferedImage image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(ERROR_WHILE_CONVERTING_IMAGE, e);
        }
    }

    private BufferedImage stitchImages(BufferedImage image1, BufferedImage image2, boolean asOverlay) {
        if (asOverlay) {
            int x = Math.max(image1.getWidth(), image2.getWidth());
            int y = Math.max(image1.getHeight(), image2.getHeight());
            BufferedImage stitchedImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
            Graphics g = stitchedImage.getGraphics();
            g.drawImage(image1, 0, 0, null);
            g.drawImage(image2, image1.getWidth() - image2.getWidth(), image1.getHeight() - image2.getHeight(), null);
            return stitchedImage;
        } else {
            BufferedImage stitchedImage = new BufferedImage(image1.getWidth(), image1.getHeight() + image2.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics graphics = stitchedImage.getGraphics();
            graphics.drawImage(image1, 0, 0, null);
            graphics.drawImage(image2, 0, image1.getHeight(), null);
            graphics.setColor(Color.BLACK);
            graphics.drawLine(0, image1.getHeight(), image1.getWidth(), image1.getHeight());
            return stitchedImage;
        }
    }

    private BufferedImage generateAlertImageWithLogo(String alertText, int screenshotWidth) {
        BufferedImage alertImage = generateImageWithText(alertText, screenshotWidth, 200);
        BufferedImage logo = toBufferedImage(new File("fl_logo.png").getAbsolutePath());
        return stitchImages(alertImage, logo, true);
    }

    private BufferedImage generateImageWithText(String text, int width, int height) {
        BufferedImage alertImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = alertImage.getGraphics();
        graphics.setColor(new Color(220, 218, 218));
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        drawStringMultiLine(graphics, text, (width - 200) - 10);
        return alertImage;
    }

    private void drawStringMultiLine(Graphics g, String text, int lineWidth) {
        FontMetrics m = g.getFontMetrics();
        int xPosition = 10;
        int yPosition = 25;
        String[] words = text.trim().split("\\b");

        for (String word : words) {
            if (xPosition + m.stringWidth(word) < lineWidth) {
                g.drawString(word, xPosition, yPosition);
                xPosition += m.stringWidth(word);
            } else {
                xPosition = 10;
                yPosition += m.getHeight();
                g.drawString(word, xPosition, yPosition);
                xPosition += m.stringWidth(word);
            }
        }
    }
}
