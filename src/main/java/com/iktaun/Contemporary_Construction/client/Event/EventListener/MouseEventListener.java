package com.iktaun.Contemporary_Construction.client.Event.EventListener;

import com.iktaun.Contemporary_Construction.api.IEditableWithBrush;
import com.iktaun.Contemporary_Construction.blocks.custom.ModblocksTags;
import com.iktaun.Contemporary_Construction.client.gui.SignpostEditScreen;
import com.iktaun.Contemporary_Construction.items.Moditems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "contemporaryconstruction")
public class MouseEventListener {



    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (!level.isClientSide()) return;

        Player player = event.getEntity();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        BlockEntity be = level.getBlockEntity(pos);

        if (!isHoldingBrush(player)) return;

        if (be instanceof IEditableWithBrush editable) {
            handleEditableClick(player, editable, pos);
        } else if (state.is(ModblocksTags.EDITABLE_WITH_BRUSH)) {
            if (be instanceof IEditableWithBrush editable) {
                handleEditableClick(player, editable, pos);
            } else {
                player.displayClientMessage(
                        Component.translatable("message.contemporaryconstruction.not_editable"),
                        true
                );
            }
        }
    }

    private static boolean isHoldingBrush(Player player) {
        return player.getMainHandItem().getItem() == Moditems.BRUSH.get() ||
                player.getOffhandItem().getItem() == Moditems.BRUSH.get();
    }

    private static void handleEditableClick(Player player, IEditableWithBrush editable, BlockPos pos) {
        if (editable.getEditorUUID() != null && !editable.getEditorUUID().equals(player.getUUID())) {
            player.displayClientMessage(Component.translatable("message.contemporaryconstruction.editing_by_other"), true);
            return;
        }

        if (editable.isWaxed()) {
            player.displayClientMessage(Component.translatable("message.contemporaryconstruction.waxed"), true);
            return;
        }

        editable.setEditorUUID(player.getUUID());
        Minecraft.getInstance().setScreen(new SignpostEditScreen(editable, pos));
    }
}