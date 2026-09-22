package com.abo9kr.jinxclient.mixin;

import com.abo9kr.jinxclient.module.ModuleManager;
import com.abo9kr.jinxclient.module.impl.hud.*;
import com.abo9kr.jinxclient.module.impl.player.DamageTint;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void jinx$drawHudModules(DrawContext context, float tickDelta, CallbackInfo ci) {
        InGameHud self = (InGameHud) (Object) this;
        var client = net.minecraft.client.MinecraftClient.getInstance();
        var textRenderer = client.textRenderer;

        Fps fps = ModuleManager.get(Fps.class);
        if (fps.isEnabled()) {
            String text = "Fps: " + client.getCurrentFps();
            context.drawText(textRenderer, text, fps.position.getX(context.getScaledWindowWidth()),
                    fps.position.getY(context.getScaledWindowHeight()), 0xFFFFFFFF, true);
        }

        Cps cps = ModuleManager.get(Cps.class);
        if (cps.isEnabled()) {
            String text = "Cps: " + cps.getLeftCps() + " | " + cps.getRightCps();
            context.drawText(textRenderer, text, cps.position.getX(context.getScaledWindowWidth()),
                    cps.position.getY(context.getScaledWindowHeight()), 0xFFFFFFFF, true);
        }

        Keystrokes keystrokes = ModuleManager.get(Keystrokes.class);
        if (keystrokes.isEnabled()) {
            int x = keystrokes.position.getX(context.getScaledWindowWidth());
            int y = keystrokes.position.getY(context.getScaledWindowHeight());
            var options = client.options;
            drawKey(context, textRenderer, "W", x + 20, y, options.forwardKey.isPressed());
            drawKey(context, textRenderer, "A", x, y + 18, options.leftKey.isPressed());
            drawKey(context, textRenderer, "S", x + 20, y + 18, options.backKey.isPressed());
            drawKey(context, textRenderer, "D", x + 40, y + 18, options.rightKey.isPressed());
        }

        ArmorHud armorHud = ModuleManager.get(ArmorHud.class);
        if (armorHud.isEnabled() && client.player != null) {
            int x = armorHud.position.getX(context.getScaledWindowWidth());
            int y = armorHud.position.getY(context.getScaledWindowHeight());
            int i = 0;
            for (var stack : client.player.getArmorItems()) {
                if (!stack.isEmpty()) {
                    context.drawItem(stack, x, y + i * 20);
                    i++;
                }
            }
        }

        DamageTint damageTint = ModuleManager.get(DamageTint.class);
        if (damageTint.isEnabled() && client.player != null) {
            float health = client.player.getHealth();
            if (health <= damageTint.threshold.get()) {
                int w = context.getScaledWindowWidth();
                int h = context.getScaledWindowHeight();
                int color = 0x55FF0000;
                context.fill(0, 0, w, 20, color);
                context.fill(0, h - 20, w, h, color);
                context.fill(0, 0, 20, h, color);
                context.fill(w - 20, 0, w, h, color);
            }
        }
    }

    private void drawKey(DrawContext context, net.minecraft.client.font.TextRenderer tr, String label, int x, int y, boolean pressed) {
        context.fill(x, y, x + 16, y + 16, pressed ? 0xAAFFFFFF : 0x66000000);
        context.drawText(tr, label, x + 5, y + 4, pressed ? 0xFF000000 : 0xFFFFFFFF, false);
    }

    @Inject(method = "renderScoreboardSidebar", at = @At("HEAD"), cancellable = true)
    private void jinx$hideScoreboard(DrawContext context, net.minecraft.scoreboard.ScoreboardObjective objective, CallbackInfo ci) {
        if (ModuleManager.get(ScoreboardHider.class).isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderChat", at = @At("HEAD"), cancellable = true)
    private void jinx$hideChat(DrawContext context, int tickDelta, CallbackInfo ci) {
        if (ModuleManager.get(ChatToggle.class).isChatHidden()) {
            ci.cancel();
        }
    }
}
