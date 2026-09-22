package com.abo9kr.jinxclient.module;

import com.abo9kr.jinxclient.module.settings.BooleanSetting;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    private final String name;
    private final Category category;
    private final String description;
    private final List<Setting<?>> settings = new ArrayList<>();
    private boolean enabled;

    protected Module(String name, Category category, String description, boolean defaultEnabled) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.enabled = defaultEnabled;
    }

    protected Module(String name, Category category, String description) {
        this(name, category, description, false);
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        boolean was = this.enabled;
        this.enabled = enabled;
        if (!was && enabled) onEnable();
        if (was && !enabled) onDisable();
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    /** Called once when the module transitions from off -> on. */
    protected void onEnable() {
    }

    /** Called once when the module transitions from on -> off. */
    protected void onDisable() {
    }

    public List<Setting<?>> getSettings() {
        return settings;
    }

    protected <T extends Setting<?>> T register(T setting) {
        settings.add(setting);
        return setting;
    }

    protected BooleanSetting registerToggle(String name, boolean def) {
        return register(new BooleanSetting(name, def));
    }
}
