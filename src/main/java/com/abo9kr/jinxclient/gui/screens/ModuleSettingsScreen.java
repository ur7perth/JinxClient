package com.abo9kr.jinxclient.gui.screens;

import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.Setting;
import com.abo9kr.jinxclient.module.settings.BooleanSetting;
import com.abo9kr.jinxclient.module.settings.ColorSetting;
import com.abo9kr.jinxclient.module.settings.ModeSetting;
import com.abo9kr.jinxclient.module.settings.NumberSetting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class ModuleSettingsScreen extends Screen {
    private final Screen parent;
    private final Module module;

    public ModuleSettingsScreen(Screen parent, Module module) {
        super(Text.literal(module.getName() + " Settings"));
        this.parent = parent;
        this.module = module;
    }

    @Override
    protected void init() {
        int panelWidth = 260;
        int x = (this.width - panelWidth) / 2;
        int y = 40;

        for (Setting<?> setting : module.getSettings()) {
            if (!setting.isVisible()) continue;
            y = addSettingWidget(setting, x, y, panelWidth) + 6;
        }

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), b -> this.close())
                .dimensions((this.width - 100) / 2, this.height - 30, 100, 20).build());
    }

    private int addSettingWidget(Setting<?> setting, int x, int y, int width) {
        if (setting instanceof BooleanSetting bool) {
            this.addDrawableChild(ButtonWidget.builder(
                            Text.literal(setting.getName() + ": " + (bool.get() ? "ON" : "OFF")),
                            b -> {
                                bool.setValue(!bool.get());
                                this.clearAndReinit();
                            })
                    .dimensions(x, y, width, 20).build());
            return y + 20;
        }

        if (setting instanceof NumberSetting num) {
            double range = num.getMax() - num.getMin();
            double progress = range == 0 ? 0 : (num.get() - num.getMin()) / range;
            this.addDrawableChild(new SliderWidget(x, y, width, 20,
                    Text.literal(setting.getName() + ": " + trim(num.get())), progress) {
                @Override
                protected void updateMessage() {
                    setMessage(Text.literal(setting.getName() + ": " + trim(num.get())));
                }

                @Override
                protected void applyValue() {
                    double stepped = num.getMin() + this.value * range;
                    if (num.getStep() > 0) {
                        stepped = Math.round(stepped / num.getStep()) * num.getStep();
                    }
                    num.setValue(stepped);
                }
            });
            return y + 20;
        }

        if (setting instanceof ColorSetting color) {
            this.addDrawableChild(ButtonWidget.builder(
                            Text.literal(setting.getName() + " (edit color)"),
                            b -> this.client.setScreen(new ColorPickerScreen(this, color)))
                    .dimensions(x, y, width, 20).build());
            return y + 20;
        }

        if (setting instanceof ModeSetting mode) {
            this.addDrawableChild(ButtonWidget.builder(
                            Text.literal(setting.getName() + ": " + mode.getValue()),
                            b -> {
                                mode.cycle();
                                this.clearAndReinit();
                            })
                    .dimensions(x, y, width, 20).build());
            return y + 20;
        }

        return y;
    }

    private void clearAndReinit() {
        this.clearChildren();
        this.init();
    }

    private static String trim(double d) {
        if (d == Math.floor(d)) return String.valueOf((int) d);
        return String.format("%.2f", d);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 16, 0xFFFFFFFF);
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
