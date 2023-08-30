package utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageFix {

    public static BufferedImage getRotatedImage(BufferedImage image, int rotationAngle) {
        int w = image.getWidth();
        int h = image.getHeight();

        BufferedImage newImage = new BufferedImage(w, h, image.getType());
        Graphics2D graphics2D = newImage.createGraphics();

        graphics2D.rotate(Math.toRadians(rotationAngle), w / 2, h / 2);
        graphics2D.drawImage(image, 0, 0, null);
        graphics2D.dispose();

        return newImage;
    }
    //image layer build

    public static BufferedImage buildImage(BufferedImage[] images) {
        int w = images[0].getWidth();
        int h = images[0].getHeight();

        BufferedImage newImage = new BufferedImage(w, h, images[0].getType());
        Graphics2D graphics2D = newImage.createGraphics();

        for (BufferedImage img : images) {
            graphics2D.drawImage(img, 0, 0, null);
        }
        graphics2D.dispose();

        return newImage;
    }

    //rotate only second image
    public static BufferedImage getBuildRotateImage(BufferedImage[] images, int rotationAngle, int rotationAtIndex) {
        int w = images[0].getWidth();
        int h = images[0].getHeight();
        BufferedImage newImage = new BufferedImage(w, h, images[0].getType());
        Graphics2D graphics2D = newImage.createGraphics();

        for (int i = 0; i < images.length; i++) {
            if (rotationAtIndex == i) graphics2D.rotate(Math.toRadians(rotationAngle),w/2,h/2);
            graphics2D.drawImage(images[i], 0, 0, null);
            if (rotationAtIndex == i) graphics2D.rotate(Math.toRadians(-rotationAngle),w/2,h/2);

        }
        graphics2D.dispose();

        return newImage;
    }

    // Rotate Second img only + animation
    public static BufferedImage[] getBuildRotateImage(BufferedImage[] imgs, BufferedImage secondImage, int rotAngle) {
        int w = imgs[0].getWidth();
        int h = imgs[0].getHeight();

        BufferedImage[] arr = new BufferedImage[imgs.length];

        for (int i = 0; i < imgs.length; i++) {
            BufferedImage newImg = new BufferedImage(w, h, imgs[0].getType());
            Graphics2D g2d = newImg.createGraphics();

            g2d.drawImage(imgs[i], 0, 0, null);
            g2d.rotate(Math.toRadians(rotAngle), w / 2, h / 2);
            g2d.drawImage(secondImage, 0, 0, null);
            g2d.dispose();

            arr[i] = newImg;
        }

        return arr;

    }
}
