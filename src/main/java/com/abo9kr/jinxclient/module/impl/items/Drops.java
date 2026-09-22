package com.abo9kr.jinxclient.module.impl.items;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.settings.ModeSetting;
import com.abo9kr.jinxclient.module.settings.NumberSetting;

import java.util.List;

public class Drops extends Module {

    public final ModeSetting renderMode = register(
            new ModeSetting("Render Mode", List.of("2D", "3D"), "3D"));
    public final NumberSetting scale = register(new NumberSetting("Scale", 1.0, 0.25, 3.0, 0.05));

    public Drops() {
        super("Drops", Category.ITEMS, "Changes how dropped items render on the ground (2D/3D) and their size.");
    }

    public boolean is2D() {
        return renderMode.getValue().equals("2D");
    }
}
