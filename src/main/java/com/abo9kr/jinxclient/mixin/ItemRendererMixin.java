package com.abo9kr.jinxclient.mixin;

import net.minecraft.client.render.item.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    // Wiring point for recoloring the enchantment glint layer (see Glint module).
}
