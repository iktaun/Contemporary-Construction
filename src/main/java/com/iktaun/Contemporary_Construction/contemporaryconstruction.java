package com.iktaun.Contemporary_Construction;

import com.iktaun.Contemporary_Construction.blocks.Entity.ModBlockEntities;
import com.iktaun.Contemporary_Construction.blocks.Modblocks;
import com.iktaun.Contemporary_Construction.items.Moditems;
import com.iktaun.Contemporary_Construction.mapcolor.custom.CustomColorManager;
import com.iktaun.Contemporary_Construction.network.ModMessages;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(contemporaryconstruction.MOD_ID)
public class contemporaryconstruction {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "contemporaryconstruction";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // 颜色系统计时器
    private static int colorSystemTickCounter = 0;

    public contemporaryconstruction(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        Moditems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        Modblocks.register(modEventBus);
        ModMessages.register();
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        // 在方块注册后分配颜色
        event.enqueueWork(() -> {
            LOGGER.info("初始化自定义颜色系统...");
            CustomColorManager.init();

            LOGGER.info("为方块分配自定义颜色...");

            // 为方块分配颜色
            assignBlockColors();

            // 输出颜色配置信息
            LOGGER.info("颜色系统配置完成，共注册 {} 种颜色",
                    CustomColorManager.getColorCount());

            // 输出详细配置信息
            LOGGER.info("颜色配置信息:");
            LOGGER.info(CustomColorManager.exportColorConfig());
        });

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    /**
     * 为所有方块分配颜色
     */
    private void assignBlockColors() {
        try {
            // 为已定义的方块分配颜色
            CustomColorManager.registerBlockColor(
                    Modblocks.BITUMEN_BLOCK.get(),
                    "asphalt_black"
            );

            CustomColorManager.registerBlockColor(
                    Modblocks.WHITE_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get(),
                    "traffic_white"
            );

            CustomColorManager.registerBlockColor(
                    Modblocks.YELLOW_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get(),
                    "traffic_yellow"
            );

            LOGGER.info("成功为 {} 个方块分配了颜色", 3);

        } catch (Exception e) {
            LOGGER.error("为方块分配颜色时出错: {}", e.getMessage());
        }
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // 可以在这里添加方块到创意标签
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    /**
     * 游戏刻事件 - 用于颜色系统的定期更新
     */
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            colorSystemTickCounter++;

            // 按配置的速率更新颜色系统（每100ticks）
            if (colorSystemTickCounter >= 100) {
                colorSystemTickCounter = 0;

                // 可以在这里添加颜色系统的定期更新逻辑
                updateDynamicColors();
            }
        }
    }

    /**
     * 更新动态颜色
     */
    private void updateDynamicColors() {
        // 这里可以实现动态颜色调整逻辑
        // 例如：根据游戏时间、天气、生物群系等调整颜色
        // 这是一个示例，实际实现需要更复杂的逻辑
    }
    @Mod.EventBusSubscriber(modid = contemporaryconstruction.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // 原有客户端日志
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
