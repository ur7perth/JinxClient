package com.abo9kr.jinxclient.module.impl.player;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.settings.ColorSetting;

public class HitColor extends Module {

    public final ColorSetting color = register(new ColorSetting("Color", 0xAAFF5555));

    public HitColor() {
        super("HitColor", Category.PLAYER, "Custom color for the damage flash entities get when hit.");
    }
}
