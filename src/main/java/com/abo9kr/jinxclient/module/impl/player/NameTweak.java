package com.abo9kr.jinxclient.module.impl.player;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.settings.BooleanSetting;

public class NameTweak extends Module {

    public final BooleanSetting nameTagPing = registerToggle("Name Tag Ping", true);
    public final BooleanSetting nameTagInF3 = registerToggle("Name Tag in F3", true);

    public NameTweak() {
        super("NameTweak", Category.PLAYER, "Shows player ping above their nametag and in the F3 debug screen.");
    }
}
