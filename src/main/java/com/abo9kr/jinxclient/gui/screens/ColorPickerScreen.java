package com.abo9kr.jinxclient.gui.screens;

import com.abo9kr.jinxclient.module.settings.ColorSetting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

/**
 * Fully custom color picker: independent A/R/G/B sliders (0-255 each) plus a hex text
 * field, so the user is never limited to a fixed palette - any color/opacity combination
 * is possible.
 */
public class ColorPickerScreen extends Screen {
    private final Screen parent;
    private final ColorSetting setting;
    private TextFieldWidget hexField;

    public ColorPickerScreen(Screen parent, ColorSetting setting) {
        super(Text.literal(setting.getName()));
        this.parent = parent;
        this.setting = setting;
    }

    @Override
    protected void init() {
        int width = 240;
        int x = (this.width - width) / 2;
        int y = 40;

        y = addChannelSlider(x, y, width, "Alpha", setting.getAlpha(),
                v -> setting.setARGB(v, setting.getRed(), setting.getGreen(), setting.getBlue()));
        y = addChannelSlider(x, y, width, "Red", setting.getRed(),
                v -> setting.setARGB(setting.getAlpha(), v, setting.getGreen(), setting.getBlue()));
        y = addChannelSlider(x, y, width, "Green", setting.getGreen(),
                v -> setting.setARGB(setting.getAlpha(), setting.getRed(), v, setting.getBlue()));
        y = addChannelSlider(x, y, width, "Blue", setting.getBlue(),
                v -> setting.setARGB(setting.getAlpha(), setting.getRed(), setting.getGreen(), v));

        y += 10;
        hexField = new TextFieldWidget(this.textRenderer, x, y, width, 20, Text.literal("Hex"));
        hexField.setText(setting.serialize());
        hexField.setChangedListener(text -> setting.deserialize(text));
        this.addDrawableChild(hexField);

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> this.close())
                .dimensions(x, this.height - 30, width, 20).build());
    }

    private interface ChannelSetter {
        void set(int value);
    }

    private int addChannelSlider(int x, int y, int width, String label, int current, ChannelSetter setter) {
        this.addDrawableChild(new SliderWidget(x, y, width, 20,
                Text.literal(label + ": " + current), current / 255.0) {
            @Override
            protected void updateMessage() {
                setMessage(Text.literal(label + ": " + Math.round(this.value * 255)));
            }

            @Override
            protected void applyValue() {
                setter.set((int) Math.round(this.value * 255));
                if (hexField != null) hexField.setText(setting.serialize());
            }
        });
        return y + 22;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 16, 0xFFFFFFFF);

        // Preview swatch
        int swatchSize = 20;
        int sx = this.width - swatchSize - 20;
        int sy = 16;
        context.fill(sx, sy, sx + swatchSize, sy + swatchSize, setting.get());
        context.drawBorder(sx, sy, swatchSize, swatchSize, 0xFFFFFFFF);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
