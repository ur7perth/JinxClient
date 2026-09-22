package com.abo9kr.jinxclient.module.impl.hud;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.util.Draggable;

public class ArmorHud extends Module {

    public final Draggable position = new Draggable(0.02, 0.4);

    public ArmorHud() {
        super("ArmorHud", Category.HUD, "Shows your worn armor pieces on the HUD; drag to reposition.");
    }
}
