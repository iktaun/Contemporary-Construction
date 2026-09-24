package com.iktaun.Contemporary_Construction.util;

import com.iktaun.Contemporary_Construction.blocks.Entity.ImageLayer;
import com.iktaun.Contemporary_Construction.blocks.Entity.ShapeElementLayer;
import com.iktaun.Contemporary_Construction.blocks.Entity.SignpostText;
import com.iktaun.Contemporary_Construction.blocks.Entity.TextLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class TextureCompositor {

    private final SignpostText text;
    private final int targetWidth;
    private final int targetHeight;

    public TextureCompositor(SignpostText text, int targetWidth, int targetHeight) {
        this.text = text;
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
    }

    public BufferedImage compose() {
        BufferedImage image = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, targetWidth, targetHeight);
        g2d.setComposite(AlphaComposite.SrcOver);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        for (ImageLayer layer : text.getImageLayers()) {
            if (!layer.isVisible() || !layer.hasTexture()) continue;
            drawImageLayer(g2d, layer);
        }

        for (ShapeElementLayer layer : text.getShapeLayers()) {
            if (!layer.isVisible() || !layer.hasTexture()) continue;
            drawShapeLayer(g2d, layer);
        }

        for (TextLayer layer : text.getTextLayers()) {
            if (layer.isEmpty()) continue;
            drawTextLayer(g2d, layer);
        }

        g2d.dispose();
        return image;
    }

    private void drawImageLayer(Graphics2D g2d, ImageLayer layer) {
        try {
            ResourceLocation texture = layer.getTextureLocation();
            if (texture == null) return;

            BufferedImage img = loadTexture(texture);
            if (img == null) return;

            int imgW = img.getWidth();
            int imgH = img.getHeight();

            float finalScaleX = layer.getScale() * layer.getScaleX();
            float finalScaleY = layer.getScale() * layer.getScaleY();
            int drawW = (int) (imgW * finalScaleX);
            int drawH = (int) (imgH * finalScaleY);

            float cx = targetWidth / 2f + layer.getOffsetX();
            float cy = targetHeight / 2f + layer.getOffsetY();

            // 先应用 ARGB 着色到图像
            int argb = layer.getColorARGB();
            BufferedImage tinted = applyTint(img, argb);

            AffineTransform orig = g2d.getTransform();
            g2d.translate(cx, cy);
            g2d.rotate(Math.toRadians(layer.getRotateZ()));
            double scaleX = 1.0;
            double scaleY = 1.0;
            if (Math.abs(layer.getRotateY()) > 0.1) {
                scaleX = Math.cos(Math.toRadians(layer.getRotateY()));
            }
            if (Math.abs(layer.getRotateX()) > 0.1) {
                scaleY = Math.cos(Math.toRadians(layer.getRotateX()));
            }
            g2d.scale(scaleX, scaleY);
            g2d.translate(-drawW / 2f, -drawH / 2f);

            g2d.drawImage(tinted, 0, 0, drawW, drawH, null);
            g2d.setTransform(orig);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawShapeLayer(Graphics2D g2d, ShapeElementLayer layer) {
        try {
            ResourceLocation texture = layer.getTexture();
            if (texture == null) return;

            BufferedImage img = loadTexture(texture);
            if (img == null) return;

            int imgW = img.getWidth();
            int imgH = img.getHeight();

            float finalScaleX = layer.getScale() * layer.getScaleX();
            float finalScaleY = layer.getScale() * layer.getScaleY();
            int drawW = (int) (imgW * finalScaleX);
            int drawH = (int) (imgH * finalScaleY);

            float cx = targetWidth / 2f + layer.getOffsetX();
            float cy = targetHeight / 2f + layer.getOffsetY();

            // ARGB
            int argb = layer.getColorARGB();
            int a = (argb >> 24) & 0xFF;
            int r = (argb >> 16) & 0xFF;
            int g = (argb >> 8) & 0xFF;
            int b = argb & 0xFF;

            // 发光时提亮
            float alphaMul = layer.isGlowing() ? 0.9f : 0.7f;
            int finalAlpha = Math.min(255, (int)(a * alphaMul));
            Color tintColor = new Color(r, g, b, finalAlpha);

            BufferedImage tinted = applyTintWithColor(img, tintColor);

            AffineTransform orig = g2d.getTransform();
            g2d.translate(cx, cy);
            g2d.rotate(Math.toRadians(layer.getRotateZ()));
            g2d.translate(-drawW / 2f, -drawH / 2f);

            g2d.drawImage(tinted, 0, 0, drawW, drawH, null);
            g2d.setTransform(orig);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawTextLayer(Graphics2D g2d, TextLayer layer) {
        String textStr = layer.getText().getString();
        if (textStr.isEmpty()) return;

        int argb = layer.getColorARGB();
        int a = (argb >> 24) & 0xFF;
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;
        Color color = new Color(r, g, b, a);

        java.awt.Font awtFont = new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 20);
        g2d.setFont(awtFont);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(textStr);
        int textHeight = fm.getHeight();

        float cx = targetWidth / 2f + layer.getOffsetX();
        float cy = targetHeight / 2f + layer.getOffsetY();

        AffineTransform orig = g2d.getTransform();
        g2d.translate(cx, cy);
        g2d.rotate(Math.toRadians(layer.getRotateZ()));
        g2d.scale(layer.getScaleX(), layer.getScaleY());

        g2d.setColor(color);
        g2d.drawString(textStr, -textWidth / 2f, -textHeight / 2f + fm.getAscent());

        g2d.setTransform(orig);
    }

    /**
     * 用 ARGB 整数为图像着色（保留原图亮度，用颜色调制）
     */
    private BufferedImage applyTint(BufferedImage src, int argb) {
        int a = (argb >> 24) & 0xFF;
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;
        return applyTintWithColor(src, new Color(r, g, b, a));
    }

    /**
     * 用给定 Color 为图像着色（保留 alpha）
     */
    private BufferedImage applyTintWithColor(BufferedImage src, Color tint) {
        int w = src.getWidth();
        int h = src.getHeight();
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        int tr = tint.getRed();
        int tg = tint.getGreen();
        int tb = tint.getBlue();
        float ta = tint.getAlpha() / 255f;

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int pixel = src.getRGB(x, y);
                int sa = (pixel >> 24) & 0xFF;
                int sr = (pixel >> 16) & 0xFF;
                int sg = (pixel >> 8) & 0xFF;
                int sb = pixel & 0xFF;

                // 混合：原图 * 色调 * 透明度
                int nr = (int)(sr * tr / 255f);
                int ng = (int)(sg * tg / 255f);
                int nb = (int)(sb * tb / 255f);
                int na = (int)(sa * ta);

                out.setRGB(x, y, (na << 24) | (nr << 16) | (ng << 8) | nb);
            }
        }
        return out;
    }

    private BufferedImage loadTexture(ResourceLocation location) {
        try {
            var resource = Minecraft.getInstance().getResourceManager().getResource(location);
            if (resource.isPresent()) {
                try (InputStream is = resource.get().open()) {
                    return ImageIO.read(is);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}