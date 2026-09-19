package com.iktaun.Contemporary_Construction.client;

import com.iktaun.Contemporary_Construction.blocks.Entity.ModBlockEntities;
import com.iktaun.Contemporary_Construction.client.Renderer.SignpostBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = "contemporaryconstruction", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvent {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            BlockEntityRenderers.register(
                    ModBlockEntities.SIGNPOST_BE.get(),
                    SignpostBlockRenderer::new
            );
            BlockEntityRenderers.register(
                    ModBlockEntities.BITUMEN_BE.get(),
                    SignpostBlockRenderer::new
            );
        });
    }
}