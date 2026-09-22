package com.abo9kr.jinxclient.module.impl.misc;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.settings.NumberSetting;

public class Saturation extends Module {

    public final NumberSetting amount = register(new NumberSetting("Saturation", 100, 0, 200, 1));

    public Saturation() {
        super("Saturation", Category.MISC, "Adjusts overall color saturation of the game.");
    }
}
