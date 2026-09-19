package com.iktaun.Contemporary_Construction.util;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {

    private static final int MAX_SIZE = 512;

    public static class ImageResult {
        public final ResourceLocation texture;
        public final int width;
        public final int height;

        public ImageResult(ResourceLocation texture, int width, int height) {
            this.texture = texture;
            this.width = width;
            this.height = height;
        }
    }

    public static ImageResult loadTextureFromFile(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        if (image == null) {
            throw new IOException("Unsupported image format or corrupted file");
        }

        int width = image.getWidth();
        int height = image.getHeight();

        if (width > MAX_SIZE || height > MAX_SIZE) {
            double scale = Math.min((double) MAX_SIZE / width, (double) MAX_SIZE / height);
            width = (int) (width * scale);
            height = (int) (height * scale);
            java.awt.Image scaled = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            java.awt.Graphics2D g = newImage.createGraphics();
            g.drawImage(scaled, 0, 0, null);
            g.dispose();
            image = newImage;
        }

        NativeImage nativeImage = new NativeImage(width, height, true);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = image.getRGB(x, y);
                nativeImage.setPixelRGBA(x, y, argb);
            }
        }

        DynamicTexture dynamicTexture = new DynamicTexture(nativeImage);
        ResourceLocation location = new ResourceLocation("contemporaryconstruction", "image_" + System.currentTimeMillis());
        Minecraft.getInstance().getTextureManager().register(location, dynamicTexture);

        return new ImageResult(location, width, height);
    }
}