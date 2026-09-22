package com.abo9kr.jinxclient.mixin;

import com.abo9kr.jinxclient.module.ModuleManager;
import com.abo9kr.jinxclient.module.impl.items.NoEatAnimation;
import com.abo9kr.jinxclient.module.impl.items.OldSwing;
import com.abo9kr.jinxclient.module.impl.items.SwingSpeed;
import com.abo9kr.jinxclient.module.impl.viewmodel.ViewModelPosition;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

    @ModifyVariable(method = "renderFirstPersonItem", at = @At("HEAD"), argsOnly = true)
    private MatrixStack jinx$translateMatrices(MatrixStack matrices) {
        ViewModelPosition pos = ModuleManager.get(ViewModelPosition.class);
        if (pos.isEnabled()) {
            matrices.translate(pos.x.get(), pos.y.get(), pos.z.get());
        }
        return matrices;
    }

    @ModifyVariable(method = "renderFirstPersonItem", at = @At("HEAD"), argsOnly = true)
    private float jinx$modifySwingProgress(float swingProgress) {
        SwingSpeed swingSpeed = ModuleManager.get(SwingSpeed.class);
        OldSwing oldSwing = ModuleManager.get(OldSwing.class);

        float progress = swingProgress;
        if (swingSpeed.isEnabled()) {
            progress = (float) Math.min(1.0, progress * swingSpeed.speed.get());
        }
        if (oldSwing.isEnabled()) {
            progress = 1 - (1 - progress) * (1 - progress);
        }
        return progress;
    }

    @ModifyVariable(method = "renderFirstPersonItem", at = @At("HEAD"), argsOnly = true)
    private float jinx$modifyEquipProgress(float equipProgress) {
        NoEatAnimation noEat = ModuleManager.get(NoEatAnimation.class);
        if (noEat.isEnabled()) {
            return 0.0F;
        }
        return equipProgress;
    }
}
