package com.abo9kr.jinxclient.module.impl.misc;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.settings.BooleanSetting;

public class AutoText extends Module {

    public final BooleanSetting onKill = registerToggle("On Kill", true);
    public final BooleanSetting onDeath = registerToggle("On Death", false);
    private String killText = "gg";
    private String deathText = "gg";

    public AutoText() {
        super("AutoText", Category.MISC, "Sends a custom chat message automatically on kill and/or on death.");
    }

    public String getKillText() {
        return killText;
    }

    public void setKillText(String killText) {
        this.killText = killText;
    }

    public String getDeathText() {
        return deathText;
    }

    public void setDeathText(String deathText) {
        this.deathText = deathText;
    }
}
