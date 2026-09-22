package com.abo9kr.jinxclient;

import com.abo9kr.jinxclient.config.ConfigManager;
import com.abo9kr.jinxclient.gui.JinxScreen;
import com.abo9kr.jinxclient.module.ModuleManager;
import com.abo9kr.jinxclient.module.impl.hud.ChatToggle;
import com.abo9kr.jinxclient.module.impl.hud.Cps;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JinxClient implements ClientModInitializer {
    public static final String MOD_ID = "jinxclient";
    public static final Logger LOGGER = LoggerFactory.getLogger("JinxClient");

    private static KeyBinding openGuiKey;
    private boolean chatKeyWasDown = false;

    @Override
    public void onInitializeClient() {
        LOGGER.info("JinxClient initializing...");

        ModuleManager.init();
        ConfigManager.loadFromDisk();

        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.jinxclient.open_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                "category.jinxclient.main"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);

        Runtime.getRuntime().addShutdownHook(new Thread(ConfigManager::saveToDisk));

        LOGGER.info("JinxClient loaded.");
    }

    private void onTick(MinecraftClient client) {
        while (openGuiKey.wasPressed()) {
            if (client.currentScreen == null) {
                client.setScreen(new JinxScreen());
            }
        }

        // ChatToggle custom in-GUI keybind (independent of vanilla Controls menu)
        ChatToggle chatToggle = ModuleManager.get(ChatToggle.class);
        if (chatToggle.isEnabled() && chatToggle.getKeyCode() != GLFW.GLFW_KEY_UNKNOWN
                && client.currentScreen == null) {
            long handle = client.getWindow().getHandle();
            boolean down = GLFW.glfwGetKey(handle, chatToggle.getKeyCode()) == GLFW.GLFW_PRESS;
            handleChatToggleKey(down);
        }
    }

    private void handleChatToggleKey(boolean down) {
        if (down && !chatKeyWasDown) {
            ModuleManager.get(ChatToggle.class).flip();
        }
        chatKeyWasDown = down;
    }

    /** Called from a mixin on mouse click handling to feed the Cps counter. */
    public static void onMouseClick(int button) {
        Cps cps = ModuleManager.get(Cps.class);
        if (button == 0) cps.onLeftClick();
        else if (button == 1) cps.onRightClick();
    }
}
