package com.abo9kr.jinxclient.module;

public enum Category {
    ITEMS("Items"),
    PLAYER("Player"),
    HUD("Hud"),
    MISC("Misc"),
    VIEWMODEL("ViewModel"),
    FONT("Font"),
    CONFIG("Config");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
