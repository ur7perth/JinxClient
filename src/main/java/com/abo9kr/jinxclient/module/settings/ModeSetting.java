package com.abo9kr.jinxclient.module.settings;

import com.abo9kr.jinxclient.module.Setting;

import java.util.List;

public class ModeSetting extends Setting<String> {
    private final List<String> options;

    public ModeSetting(String name, List<String> options, String defaultValue) {
        super(name, defaultValue);
        this.options = options;
    }

    public List<String> getOptions() {
        return options;
    }

    public void cycle() {
        int idx = options.indexOf(getValue());
        idx = (idx + 1) % options.size();
        setValue(options.get(idx));
    }

    @Override
    public String serialize() {
        return value;
    }

    @Override
    public void deserialize(String raw) {
        String v = raw.trim();
        if (options.contains(v)) {
            this.value = v;
        }
    }
}
