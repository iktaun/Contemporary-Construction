package com.iktaun.Contemporary_Construction.datagen;


import com.iktaun.Contemporary_Construction.contemporaryconstruction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = contemporaryconstruction.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // 获取包输出路径
        String modId = contemporaryconstruction.MOD_ID;

        // 添加方块标签提供器（服务端）
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTags);

        // 添加物品标签提供器（服务端）
        generator.addProvider(event.includeServer(),
                new ModItemTagsProvider(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));

        // 添加战利品表提供器（服务端）
        generator.addProvider(event.includeServer(), ModLootTableProvider.create(packOutput));

        // 添加方块状态和模型提供器（客户端）
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));

        // 添加物品模型提供器（客户端）
        generator.addProvider(event.includeClient(), new ModItemModelsProvider(packOutput, existingFileHelper));

        // 添加语言提供器（客户端）
        // ===== 语言提供器（客户端） =====
        generator.addProvider(event.includeClient(), new ModEnUsLangProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModZhCnLangProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModZhHkLangProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModJaJpLangProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModKoKrLangProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModDeDeLangProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModRuRuLangProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModFrFrLangProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModEsEsLangProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModItItLangProvider(packOutput));

        // 如果需要生成配方，可以添加RecipeProvider
        // generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));
    }
}