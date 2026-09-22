package com.abo9kr.jinxclient.module.impl.misc;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.settings.ColorSetting;
import com.abo9kr.jinxclient.module.settings.NumberSetting;

public class FogModel extends Module {

    public final NumberSetting distanceChunks = register(new NumberSetting("Distance", 6, 1, 32, 1));
    public final ColorSetting color = register(new ColorSetting("Color", 0xFFC0D8FF));

    public FogModel() {
        super("FogModel", Category.MISC, "Custom fog distance (in chunks) and color.");
    }
}
