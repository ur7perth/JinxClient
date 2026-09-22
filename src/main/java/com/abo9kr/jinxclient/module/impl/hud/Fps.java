package com.abo9kr.jinxclient.module.impl.hud;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.util.Draggable;

public class Fps extends Module {

    public final Draggable position = new Draggable(0.02, 0.02);

    public Fps() {
        super("Fps", Category.HUD, "Displays your current frames-per-second.");
    }
}
