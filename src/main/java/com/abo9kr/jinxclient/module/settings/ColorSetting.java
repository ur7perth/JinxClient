package com.abo9kr.jinxclient.module.settings;

import com.abo9kr.jinxclient.module.Setting;

public class ColorSetting extends Setting<Integer> {

    public ColorSetting(String name, int defaultArgb) {
        super(name, defaultArgb);
    }

    public int get() {
        return getValue();
    }

    public int getAlpha() {
        return (value >> 24) & 0xFF;
    }

    public int getRed() {
        return (value >> 16) & 0xFF;
    }

    public int getGreen() {
        return (value >> 8) & 0xFF;
    }

    public int getBlue() {
        return value & 0xFF;
    }

    public float[] toFloatRGB() {
        return new float[]{getRed() / 255f, getGreen() / 255f, getBlue() / 255f};
    }

    public void setARGB(int a, int r, int g, int b) {
        setValue(((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF));
    }

    @Override
    public String serialize() {
        return "#" + Integer.toHexString(value);
    }

    @Override
    public void deserialize(String raw) {
        try {
            String s = raw.trim().replace("#", "");
            this.value = (int) Long.parseLong(s, 16);
        } catch (NumberFormatException ignored) {
        }
    }
}
