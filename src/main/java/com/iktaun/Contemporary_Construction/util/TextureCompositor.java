package com.iktaun.Contemporary_Construction.util;

import com.iktaun.Contemporary_Construction.blocks.Entity.ImageLayer;
import com.iktaun.Contemporary_Construction.blocks.Entity.ShapeElementLayer;
import com.iktaun.Contemporary_Construction.blocks.Entity.SignpostText;
import com.iktaun.Contemporary_Construction.blocks.Entity.TextLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

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

            g2d.drawImage(img, 0, 0, drawW, drawH, null);
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

            DyeColor dye = layer.getColor();
            int color = dye.getTextColor();
            float r = ((color >> 16) & 0xFF) / 255f;
            float g = ((color >> 8) & 0xFF) / 255f;
            float b = (color & 0xFF) / 255f;
            float a = layer.isGlowing() ? 0.9f : 0.7f;

            AffineTransform orig = g2d.getTransform();
            g2d.translate(cx, cy);
            g2d.rotate(Math.toRadians(layer.getRotateZ()));
            g2d.translate(-drawW / 2f, -drawH / 2f);

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
            g2d.drawImage(img, 0, 0, drawW, drawH, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f));
            g2d.setColor(new Color(r, g, b));
            g2d.fillRect(0, 0, drawW, drawH);

            g2d.setTransform(orig);
            g2d.setComposite(AlphaComposite.SrcOver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawTextLayer(Graphics2D g2d, TextLayer layer) {
        String textStr = layer.getText().getString();
        if (textStr.isEmpty()) return;

        int color = layer.getColor().getTextColor();

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

        g2d.setColor(new Color(color, true));
        g2d.drawString(textStr, -textWidth / 2f, -textHeight / 2f + fm.getAscent());

        g2d.setTransform(orig);
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