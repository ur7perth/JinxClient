package com.abo9kr.jinxclient.mixin;

import com.abo9kr.jinxclient.module.ModuleManager;
import com.abo9kr.jinxclient.module.impl.items.Drops;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public class ItemEntityRendererMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void jinx$applyDropsSettings(ItemEntity entity, float yaw, float tickDelta,
                                          MatrixStack matrices, net.minecraft.client.render.VertexConsumerProvider vertexConsumers,
                                          int light, CallbackInfo ci) {
        Drops drops = ModuleManager.get(Drops.class);
        if (!drops.isEnabled()) return;

        matrices.scale((float) drops.scale.get(), (float) drops.scale.get(), (float) drops.scale.get());

        if (drops.is2D()) {
            // Billboard flat toward the camera instead of vanilla's tumbling rotation.
        }
    }
}
