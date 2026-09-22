package com.abo9kr.jinxclient.module.impl.items;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.settings.BooleanSetting;
import com.abo9kr.jinxclient.module.settings.ColorSetting;
import com.abo9kr.jinxclient.module.settings.NumberSetting;

public class Glint extends Module {

    public final BooleanSetting useCustomColor = registerToggle("Custom Color", true);
    public final ColorSetting color = register(new ColorSetting("Color", 0xFFDA70D6))
            .visibleIf(() -> useCustomColor.get());
    public final BooleanSetting fx = registerToggle("Fx (rainbow)", false);

    public final NumberSetting itemStrength = register(new NumberSetting("Item Strength", 5, 0, 10, 1));
    public final NumberSetting itemSpeed = register(new NumberSetting("Item Speed", 5, 0, 10, 1));
    public final NumberSetting armorStrength = register(new NumberSetting("Armor Strength", 5, 0, 10, 1));
    public final NumberSetting armorSpeed = register(new NumberSetting("Armor Speed", 5, 0, 10, 1));

    public Glint() {
        super("Glint", Category.ITEMS,
                "Custom color / strength / speed for the enchantment glint on items and armor.");
    }

    public float[] getCurrentColor(long timeMillis) {
        if (fx.get()) {
            float hue = (timeMillis % 3000L) / 3000f;
            return java.awt.Color.HSBtoRGB(hue, 0.8f, 1f) == 0 ? new float[]{1, 1, 1}
                    : hsbToFloatArray(hue);
        }
        return color.toFloatRGB();
    }

    private float[] hsbToFloatArray(float hue) {
        int rgb = java.awt.Color.HSBtoRGB(hue, 0.8f, 1f);
        return new float[]{
                ((rgb >> 16) & 0xFF) / 255f,
                ((rgb >> 8) & 0xFF) / 255f,
                (rgb & 0xFF) / 255f
        };
    }
}
