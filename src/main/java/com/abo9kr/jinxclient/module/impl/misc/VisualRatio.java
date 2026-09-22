package com.abo9kr.jinxclient.module.impl.misc;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.settings.NumberSetting;

public class VisualRatio extends Module {

    public final NumberSetting stretch = register(new NumberSetting("Stretch", 100, 0, 200, 1));

    public VisualRatio() {
        super("VisualRatio", Category.MISC, "Cosmetically stretches/warps the rendered view.");
    }
}
