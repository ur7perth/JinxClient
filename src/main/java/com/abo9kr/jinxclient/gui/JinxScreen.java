package com.abo9kr.jinxclient.gui;

import com.abo9kr.jinxclient.config.ConfigManager;
import com.abo9kr.jinxclient.gui.screens.ModuleSettingsScreen;
import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import com.abo9kr.jinxclient.module.ModuleManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.util.List;

/**
 * The main JinxClient GUI, opened with the "J" key.
 * Left sidebar: the 7 categories (Items, Player, Hud, Misc, ViewModel, Font, Config).
 * Main panel: modules in the selected category. Left click toggles a module on/off,
 * right click opens that module's settings popup (ModuleSettingsScreen).
 */
public class JinxScreen extends Screen {
    private static final int SIDEBAR_WIDTH = 90;
    private static final int ROW_HEIGHT = 20;

    private Category selectedCategory = Category.ITEMS;

    // Config tab widgets
    private TextFieldWidget configField;
    private Text configStatus = Text.literal("");

    public JinxScreen() {
        super(Text.literal("JinxClient"));
    }

    @Override
    protected void init() {
        super.init();
        rebuildConfigTabIfNeeded();
    }

    private void rebuildConfigTabIfNeeded() {
        this.clearChildren();
        if (selectedCategory == Category.CONFIG) {
            int fieldX = SIDEBAR_WIDTH + 20;
            int fieldY = 60;
            configField = new TextFieldWidget(this.textRenderer, fieldX, fieldY, this.width - fieldX - 20, 20,
                    Text.literal("Config Code"));
            configField.setMaxLength(100_000);
            this.addDrawableChild(configField);

            this.addDrawableChild(ButtonWidget.builder(Text.literal("Export"), b -> {
                        String code = ConfigManager.exportCode();
                        configField.setText(code);
                        configField.setCursorToStart(false);
                        configStatus = Text.literal("Exported! Code copied into the box above - copy it out.");
                    })
                    .dimensions(fieldX, fieldY + 30, 100, 20).build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("Import"), b -> {
                        boolean ok = ConfigManager.importCode(configField.getText());
                        configStatus = ok
                                ? Text.literal("Imported successfully!")
                                : Text.literal("Invalid code.");
                    })
                    .dimensions(fieldX + 110, fieldY + 30, 100, 20).build());
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        // Sidebar background
        context.fill(0, 0, SIDEBAR_WIDTH, this.height, 0xCC101014);
        context.fill(SIDEBAR_WIDTH, 0, this.width, this.height, 0x99101014);

        int y = 8;
        for (Category category : Category.values()) {
            boolean selected = category == selectedCategory;
            int color = selected ? 0xFFFFFFFF : 0xFFAAAAAA;
            context.fill(2, y - 2, SIDEBAR_WIDTH - 2, y + 12, selected ? 0x66FFFFFF : 0x00000000);
            context.drawText(this.textRenderer, category.getDisplayName(), 8, y, color, false);
            y += ROW_HEIGHT;
        }

        context.drawText(this.textRenderer, "JinxClient", 8, this.height - 14, 0xFF666666, false);

        if (selectedCategory == Category.CONFIG) {
            context.drawText(this.textRenderer, "Config", SIDEBAR_WIDTH + 20, 30, 0xFFFFFFFF, false);
            context.drawText(this.textRenderer, configStatus, SIDEBAR_WIDTH + 20, 100, 0xFFAAAAAA, false);
        } else if (selectedCategory == Category.FONT) {
            context.drawText(this.textRenderer, "Font", SIDEBAR_WIDTH + 20, 30, 0xFFFFFFFF, false);
            context.drawText(this.textRenderer, "Coming soon.", SIDEBAR_WIDTH + 20, 60, 0xFFAAAAAA, false);
        } else {
            renderModuleList(context, mouseX, mouseY);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    private void renderModuleList(DrawContext context, int mouseX, int mouseY) {
        List<Module> modules = ModuleManager.getModules(selectedCategory);
        int y = 30;
        for (Module module : modules) {
            int rowY = y;
            boolean hovered = mouseX > SIDEBAR_WIDTH + 10 && mouseX < this.width - 10
                    && mouseY > rowY && mouseY < rowY + ROW_HEIGHT - 2;

            context.fill(SIDEBAR_WIDTH + 10, rowY, this.width - 10, rowY + ROW_HEIGHT - 2,
                    hovered ? 0x33FFFFFF : 0x22000000);

            int textColor = module.isEnabled() ? 0xFF55FF55 : 0xFFDDDDDD;
            context.drawText(this.textRenderer, module.getName(), SIDEBAR_WIDTH + 16, rowY + 4, textColor, false);

            String state = module.isEnabled() ? "ON" : "OFF";
            int stateWidth = this.textRenderer.getWidth(state);
            context.drawText(this.textRenderer, state, this.width - 20 - stateWidth, rowY + 4,
                    module.isEnabled() ? 0xFF55FF55 : 0xFF999999, false);

            y += ROW_HEIGHT;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // Sidebar category selection
        if (mouseX < SIDEBAR_WIDTH) {
            int y = 8;
            for (Category category : Category.values()) {
                if (mouseY > y - 2 && mouseY < y + 12) {
                    selectedCategory = category;
                    rebuildConfigTabIfNeeded();
                    return true;
                }
                y += ROW_HEIGHT;
            }
        } else if (selectedCategory != Category.CONFIG && selectedCategory != Category.FONT) {
            List<Module> modules = ModuleManager.getModules(selectedCategory);
            int y = 30;
            for (Module module : modules) {
                boolean inRow = mouseX > SIDEBAR_WIDTH + 10 && mouseX < this.width - 10
                        && mouseY > y && mouseY < y + ROW_HEIGHT - 2;
                if (inRow) {
                    if (button == 0) {
                        module.toggle();
                    } else if (button == 1 && !module.getSettings().isEmpty()) {
                        this.client.setScreen(new ModuleSettingsScreen(this, module));
                    }
                    return true;
                }
                y += ROW_HEIGHT;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
