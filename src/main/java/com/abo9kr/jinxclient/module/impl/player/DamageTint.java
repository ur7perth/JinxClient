package com.abo9kr.jinxclient.module.impl.player;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.settings.NumberSetting;

public class DamageTint extends Module {

    public final NumberSetting threshold = register(new NumberSetting("Start", 5, 0, 10, 1));

    public DamageTint() {
        super("DamageTint", Category.PLAYER, "Reddens the edges of your screen when your health drops below a threshold.");
    }
}
