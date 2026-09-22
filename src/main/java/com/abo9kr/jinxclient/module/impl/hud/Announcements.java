package com.abo9kr.jinxclient.module.impl.hud;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.settings.BooleanSetting;
import com.abo9kr.jinxclient.util.Draggable;

public class Announcements extends Module {

    public final BooleanSetting hide = registerToggle("Hide Titles", false);
    public final Draggable position = new Draggable(0.5, 0.4);

    public Announcements() {
        super("Announcements", Category.HUD, "Hide server titles/subtitles, or move them to a custom position.");
    }
}
