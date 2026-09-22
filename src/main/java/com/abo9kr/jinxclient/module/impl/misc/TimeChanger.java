package com.abo9kr.jinxclient.module.impl.misc;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.settings.ModeSetting;

import java.util.List;

public class TimeChanger extends Module {

    public final ModeSetting time = register(new ModeSetting(
            "Time", List.of("Normal", "Day", "Noon", "Sunset", "Night", "Midnight"), "Normal"));

    public TimeChanger() {
        super("TimeChanger", Category.MISC, "Overrides the rendered time of day on your screen only.");
    }
}
