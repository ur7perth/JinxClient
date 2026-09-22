package com.abo9kr.jinxclient.module.impl.hud;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.util.Draggable;

public class Keystrokes extends Module {

    public final Draggable position = new Draggable(0.85, 0.75);

    public Keystrokes() {
        super("Keystrokes", Category.HUD, "Shows a WASD + click keystrokes display.");
    }
}
