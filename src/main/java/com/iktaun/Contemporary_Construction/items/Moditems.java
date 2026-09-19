package com.iktaun.Contemporary_Construction.items;

import com.iktaun.Contemporary_Construction.contemporaryconstruction;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Moditems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, contemporaryconstruction.MOD_ID);

    public static final RegistryObject<Item> SIGNAL_BOARD =
            ITEMS.register("signal_board",
                    () -> new Item(new Item.Properties()
                   ));
    /*public static final RegistryObject<Item> LIGHT_ADJUSTER =
            ITEMS.register("light_adjuster",() ->
                    new LightAdjusterItem(new Item.Properties()
                            .stacksTo(1)));
     */
    public static final RegistryObject<Item> BRUSH =
            ITEMS.register("brush",() ->
                    new Item(new Item.Properties()
                            .stacksTo(1)));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
