package com.abo9kr.jinxclient.config;

import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.ModuleManager;
import com.abo9kr.jinxclient.module.Setting;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Handles JinxClient config persistence and the "Export"/"Import" code feature in the
 * Config tab: Export serializes every module's enabled state + every setting into a
 * compact, versioned, Base64 text blob the user can copy and send to someone else.
 * Import parses that blob back and applies it, so a second person gets the same setup.
 */
public final class ConfigManager {
    private static final String HEADER = "JINX1;";
    private static final Path DIR = FabricLoader.getInstance().getConfigDir().resolve("jinxclient");
    private static final Path AUTOSAVE_FILE = DIR.resolve("config.txt");

    private ConfigManager() {
    }

    /** Builds the plain-text config representation (before compression/encoding). */
    private static String buildPlainConfig() {
        StringBuilder sb = new StringBuilder();
        for (Module module : ModuleManager.getModules()) {
            sb.append("M|").append(module.getName()).append('|').append(module.isEnabled()).append('\n');
            for (Setting<?> setting : module.getSettings()) {
                sb.append("S|").append(module.getName()).append('|').append(setting.getName())
                        .append('|').append(setting.serialize()).append('\n');
            }
        }
        return sb.toString();
    }

    private static void applyPlainConfig(String plain) {
        for (String line : plain.split("\n")) {
            if (line.isBlank()) continue;
            String[] parts = line.split("\\|", 4);
            if (parts.length < 3) continue;
            Module module = findModule(parts[1]);
            if (module == null) continue;

            if (parts[0].equals("M") && parts.length == 3) {
                module.setEnabled(Boolean.parseBoolean(parts[2]));
            } else if (parts[0].equals("S") && parts.length == 4) {
                for (Setting<?> setting : module.getSettings()) {
                    if (setting.getName().equals(parts[2])) {
                        setting.deserialize(parts[3]);
                    }
                }
            }
        }
    }

    private static Module findModule(String name) {
        for (Module m : ModuleManager.getModules()) {
            if (m.getName().equals(name)) return m;
        }
        return null;
    }

    /** Export the current setup to a shareable code. */
    public static String exportCode() {
        try {
            String plain = HEADER + buildPlainConfig();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (DeflaterOutputStream dos = new DeflaterOutputStream(baos, new Deflater(Deflater.BEST_COMPRESSION))) {
                dos.write(plain.getBytes(StandardCharsets.UTF_8));
            }
            return Base64.getUrlEncoder().withoutPadding().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to export config", e);
        }
    }

    /** Import a code produced by exportCode(). Returns true on success. */
    public static boolean importCode(String code) {
        try {
            byte[] compressed = Base64.getUrlDecoder().decode(code.trim());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(compressed))) {
                iis.transferTo(baos);
            }
            String plain = baos.toString(StandardCharsets.UTF_8);
            if (!plain.startsWith(HEADER)) return false;
            applyPlainConfig(plain.substring(HEADER.length()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Called on shutdown / periodically to persist settings locally between sessions. */
    public static void saveToDisk() {
        try {
            Files.createDirectories(DIR);
            Files.writeString(AUTOSAVE_FILE, buildPlainConfig(), StandardCharsets.UTF_8);
        } catch (IOException ignored) {
        }
    }

    public static void loadFromDisk() {
        try {
            if (Files.exists(AUTOSAVE_FILE)) {
                applyPlainConfig(Files.readString(AUTOSAVE_FILE, StandardCharsets.UTF_8));
            }
        } catch (IOException ignored) {
        }
    }
}
