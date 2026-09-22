package com.abo9kr.jinxclient.mixin;

import com.abo9kr.jinxclient.module.ModuleManager;
import com.abo9kr.jinxclient.module.impl.hud.NoBlur;
import com.abo9kr.jinxclient.module.impl.misc.Fullbright;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "getBrightness", at = @At("RETURN"), cancellable = true)
    private void jinx$fullbright(double d, CallbackInfoReturnable<Double> cir) {
        Fullbright fullbright = ModuleManager.get(Fullbright.class);
        if (fullbright.isEnabled()) {
            cir.setReturnValue(1.0);
        }
    }

    @Inject(method = "loadPostProcessor", at = @At("HEAD"), cancellable = true)
    private void jinx$noBlur(Identifier id, CallbackInfo ci) {
        NoBlur noBlur = ModuleManager.get(NoBlur.class);
        if (noBlur.isEnabled()) {
            ci.cancel();
        }
    }
}
