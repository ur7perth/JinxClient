package com.abo9kr.jinxclient.module.impl.viewmodel;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.settings.NumberSetting;

public class ViewModelPosition extends Module {

    public final NumberSetting x = register(new NumberSetting("X", 0, -2, 2, 0.01));
    public final NumberSetting y = register(new NumberSetting("Y", 0, -2, 2, 0.01));
    public final NumberSetting z = register(new NumberSetting("Z", 0, -2, 2, 0.01));

    public ViewModelPosition() {
        super("Position", Category.VIEWMODEL, "Offsets your first-person hand/item position on X/Y/Z.");
    }
}
