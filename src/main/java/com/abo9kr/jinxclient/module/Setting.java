package com.abo9kr.jinxclient.module;

public abstract class Setting<T> {
    private final String name;
    protected T value;
    private final T defaultValue;

    private VisibilityCheck visibilityCheck = () -> true;

    public Setting(String name, T defaultValue) {
        this.name = name;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void reset() {
        this.value = defaultValue;
    }

    public boolean isVisible() {
        return visibilityCheck.isVisible();
    }

    public Setting<T> visibleIf(VisibilityCheck check) {
        this.visibilityCheck = check;
        return this;
    }

    public abstract String serialize();

    public abstract void deserialize(String raw);

    @FunctionalInterface
    public interface VisibilityCheck {
        boolean isVisible();
    }
}
