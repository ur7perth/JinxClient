package com.abo9kr.jinxclient.mixin;

import com.abo9kr.jinxclient.module.ModuleManager;
import com.abo9kr.jinxclient.module.impl.hud.ChatToggle;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class ChatHudMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void jinx$hideChat(DrawContext context, int currentTick, int mouseX, int mouseY, CallbackInfo ci) {
        if (ModuleManager.get(ChatToggle.class).isChatHidden()) {
            ci.cancel();
        }
    }
}
