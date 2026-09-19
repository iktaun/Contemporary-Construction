package com.iktaun.Contemporary_Construction.blocks.Entity;

import com.iktaun.Contemporary_Construction.blocks.Modblocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "contemporaryconstruction");

    public static final RegistryObject<BlockEntityType<SignpostBlockEntity>> SIGNPOST_BE =
            BLOCK_ENTITIES.register("signpost_be",
                    () -> BlockEntityType.Builder.of(SignpostBlockEntity::new, Modblocks.SIGNPOST.get()).build(null)
            );

    public static final RegistryObject<BlockEntityType<BitumenBlockEntity>> BITUMEN_BE =
            BLOCK_ENTITIES.register("bitumen_be",
                    () -> BlockEntityType.Builder.of(BitumenBlockEntity::new, Modblocks.BITUMEN_BLOCK.get()).build(null)
            );
}