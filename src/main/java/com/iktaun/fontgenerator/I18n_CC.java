package com.iktaun.fontgenerator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class I18n_CC {
    private static Map<String, String> translations = new HashMap<>();

    public static void load(String locale) {
        // 使用独立路径，避免与模组本体冲突
        String path = "/assets/fontgenerator/lang/" + locale + ".json";
        try (InputStream is = I18n_CC.class.getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("[I18n_CC] 找不到: " + path + "，回退到英文");
                loadFallback();
                return;
            }
            byte[] bytes = is.readAllBytes();
            String content = new String(bytes, StandardCharsets.UTF_8);
            // 移除 BOM
            if (bytes.length >= 3 && bytes[0] == (byte)0xEF && bytes[1] == (byte)0xBB && bytes[2] == (byte)0xBF) {
                content = content.substring(1);
                System.out.println("[I18n_CC] 检测并移除了 BOM 头");
            }
            translations = parseJson(content);
            if (translations.isEmpty()) {
                System.err.println("[I18n_CC] JSON 解析失败，回退到英文");
                loadFallback();
            } else {
                System.out.println("[I18n_CC] 加载成功: " + locale + " (" + translations.size() + " 条)");
            }
        } catch (Exception e) {
            System.err.println("[I18n_CC] 加载失败: " + e.getMessage());
            loadFallback();
        }
    }

    private static void loadFallback() {
        try (InputStream is = I18n_CC.class.getResourceAsStream("/assets/fontgenerator/lang/en_us.json")) {
            if (is == null) {
                translations = new HashMap<>();
                return;
            }
            byte[] bytes = is.readAllBytes();
            String content = new String(bytes, StandardCharsets.UTF_8);
            if (bytes.length >= 3 && bytes[0] == (byte)0xEF && bytes[1] == (byte)0xBB && bytes[2] == (byte)0xBF) {
                content = content.substring(1);
            }
            translations = parseJson(content);
            System.out.println("[I18n_CC] 回退到英文 (" + translations.size() + " 条)");
        } catch (Exception e) {
            translations = new HashMap<>();
        }
    }

    private static Map<String, String> parseJson(String json) {
        Map<String, String> map = new HashMap<>();
        try {
            json = json.trim();
            if (json.startsWith("{") && json.endsWith("}")) {
                json = json.substring(1, json.length() - 1);
            }
            StringBuilder key = new StringBuilder();
            StringBuilder value = new StringBuilder();
            boolean inString = false;
            boolean escape = false;
            boolean readingKey = true;

            for (int i = 0; i < json.length(); i++) {
                char c = json.charAt(i);
                if (escape) {
                    if (readingKey) key.append(c);
                    else value.append(c);
                    escape = false;
                    continue;
                }
                if (c == '\\') {
                    escape = true;
                    if (readingKey) key.append(c);
                    else value.append(c);
                    continue;
                }
                if (c == '"') {
                    inString = !inString;
                    if (readingKey) key.append(c);
                    else value.append(c);
                    continue;
                }
                if (!inString) {
                    if (c == ':' && readingKey) {
                        readingKey = false;
                        continue;
                    }
                    if (c == ',' && !readingKey) {
                        String k = clean(key.toString());
                        String v = clean(value.toString());
                        if (!k.isEmpty() && !v.isEmpty()) map.put(k, v);
                        key.setLength(0);
                        value.setLength(0);
                        readingKey = true;
                        continue;
                    }
                }
                if (readingKey) key.append(c);
                else value.append(c);
            }
            if (!readingKey && key.length() > 0) {
                String k = clean(key.toString());
                String v = clean(value.toString());
                if (!k.isEmpty() && !v.isEmpty()) map.put(k, v);
            }
        } catch (Exception e) {
            System.err.println("[I18n_CC] JSON 解析异常: " + e.getMessage());
        }
        return map;
    }

    private static String clean(String s) {
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length() - 1);
        }
        if (s.startsWith("\uFEFF")) {
            s = s.substring(1);
        }
        s = s.replace("\\\"", "\"")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\\\", "\\");
        return s;
    }

    public static String get(String key) {
        String value = translations.get(key);
        if (value == null) {
            System.err.println("[I18n_CC] 缺少翻译键: " + key);
            return key;
        }
        return value;
    }
}