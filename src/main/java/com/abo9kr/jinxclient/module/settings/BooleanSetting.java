package com.abo9kr.jinxclient.module.settings;

import com.abo9kr.jinxclient.module.Setting;

public class BooleanSetting extends Setting<Boolean> {

    public BooleanSetting(String name, boolean defaultValue) {
        super(name, defaultValue);
    }

    public boolean get() {
        return getValue();
    }

    @Override
    public String serialize() {
        return String.valueOf(value);
    }

    @Override
    public void deserialize(String raw) {
        this.value = Boolean.parseBoolean(raw.trim());
    }
}
