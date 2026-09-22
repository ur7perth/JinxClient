package com.abo9kr.jinxclient.module.impl.misc;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.settings.BooleanSetting;
import com.abo9kr.jinxclient.module.settings.NumberSetting;

public class TotemDisplay extends Module {

    public final BooleanSetting hide = registerToggle("Hide", false);
    public final NumberSetting size = register(new NumberSetting("Size", 1.0, 0.25, 3.0, 0.05))
            .visibleIf(() -> !TotemDisplay.this.hide.get());

    public TotemDisplay() {
        super("TotemDisplay", Category.MISC, "Hide or resize the totem-of-undying popup animation.");
    }
}
