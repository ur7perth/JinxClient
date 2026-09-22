package com.abo9kr.jinxclient.mixin;

import com.abo9kr.jinxclient.module.ModuleManager;
import com.abo9kr.jinxclient.module.impl.misc.Fullbright;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameOptions.class)
public class GameOptionsMixin {

    @Inject(method = "getGamma", at = @At("HEAD"), cancellable = true)
    private void jinx$fullbright(CallbackInfoReturnable<Double> cir) {
        Fullbright fullbright = ModuleManager.get(Fullbright.class);
        if (fullbright.isEnabled()) {
            cir.setReturnValue(15.0);
        }
    }
}
