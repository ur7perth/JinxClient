package com.abo9kr.jinxclient.module.impl.player;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.settings.ColorSetting;

public class Hitbox extends Module {

    public final ColorSetting color = register(new ColorSetting("Color", 0x80FFFFFF));
    public final ColorSetting targetColor = register(new ColorSetting("Target Color", 0x80FF0000));

    public Hitbox() {
        super("Hitbox", Category.PLAYER, "Colored outline around entities, changing color when they're in attack range.");
    }
}
