package com.abo9kr.jinxclient.module.impl.hud;

import com.abo9kr.jinxclient.module.Category;
import com.abo9kr.jinxclient.module.Module;
import org.lwjgl.glfw.GLFW;

public class ChatToggle extends Module {

    private int keyCode = GLFW.GLFW_KEY_UNKNOWN;
    private boolean chatHidden = false;

    public ChatToggle() {
        super("ChatToggle", Category.HUD, "Hides the chat box (bind your own key in this GUI) without deleting messages.");
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public boolean isChatHidden() {
        return isEnabled() && chatHidden;
    }

    public void flip() {
        chatHidden = !chatHidden;
    }
}
