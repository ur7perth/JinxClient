package com.abo9kr.jinxclient.module.impl.misc;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.settings.ColorSetting;

public class BlockOverlay extends Module {

    public final ColorSetting color = register(new ColorSetting("Color", 0xFF000000));

    public BlockOverlay() {
        super("BlockOverlay", Category.MISC, "Custom color for the targeted-block outline.");
    }
}
