package com.abo9kr.jinxclient.module.settings;

import com.abo9kr.jinxclient.module.Setting;

public class NumberSetting extends Setting<Double> {
    private final double min;
    private final double max;
    private final double step;

    public NumberSetting(String name, double defaultValue, double min, double max, double step) {
        super(name, defaultValue);
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public double get() {
        return getValue();
    }

    public int getAsInt() {
        return (int) Math.round(getValue());
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getStep() {
        return step;
    }

    @Override
    public void setValue(Double value) {
        this.value = Math.max(min, Math.min(max, value));
    }

    @Override
    public String serialize() {
        return String.valueOf(value);
    }

    @Override
    public void deserialize(String raw) {
        try {
            setValue(Double.parseDouble(raw.trim()));
        } catch (NumberFormatException ignored) {
        }
    }
}
