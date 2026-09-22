package com.abo9kr.jinxclient.module.impl.items;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.settings.NumberSetting;

public class SwingSpeed extends Module {

    public final NumberSetting speed = register(new NumberSetting("Speed", 1.0, 0.5, 3.0, 0.1));

    public SwingSpeed() {
        super("SwingSpeed", Category.ITEMS, "Changes how fast your own first-person swing animation plays.");
    }
}
