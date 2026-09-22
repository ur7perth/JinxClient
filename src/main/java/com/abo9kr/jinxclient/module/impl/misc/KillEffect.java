package com.abo9kr.jinxclient.module.impl.misc;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.settings.ModeSetting;
import com.abo9kr.jinxclient.module.settings.NumberSetting;

import java.util.List;

public class KillEffect extends Module {

    public final ModeSetting effect = register(new ModeSetting(
            "Effect", List.of("None", "Totem", "Anvil", "TNT", "Lightning"), "None"));
    public final NumberSetting tntSpeed = register(new NumberSetting("TNT Speed", 1.0, 0.25, 3.0, 0.25))
            .visibleIf(() -> effect.getValue().equals("TNT"));

    public KillEffect() {
        super("KillEffect", Category.MISC, "Plays a cosmetic particle effect where a player you killed died.");
    }
}
