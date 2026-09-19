package com.iktaun.Contemporary_Construction.items.tools;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.network.chat.Component;
import com.iktaun.Contemporary_Construction.blocks.custom.light.ModLightBlock;

/**
 * 光照调节器 —— 用于调整路灯亮度的专用工具。
 * 右键路灯：亮度 +1；潜行 + 右键：亮度 -1。
 */
public class LightAdjusterItem extends Item {

    public LightAdjusterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        var pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        // 只对 StreetLightBlock 生效
        if (!(state.getBlock() instanceof ModLightBlock)) {
            return InteractionResult.PASS;
        }

        // 获取 LEVEL 属性（父类 LightBlock 已定义）
        IntegerProperty levelProp = BlockStateProperties.LEVEL;
        int currentLevel = state.getValue(levelProp);

        // 判断是否潜行（Shift）：潜行减亮度，否则加亮度
        boolean shift = context.getPlayer() != null && context.getPlayer().isShiftKeyDown();
        int newLevel = shift ? currentLevel - 1 : currentLevel + 1;

        // 限定在 0~15
        newLevel = Math.max(0, Math.min(15, newLevel));

        // 如果亮度无变化则失败
        if (newLevel == currentLevel) {
            return InteractionResult.FAIL;
        }

        // 服务端执行更新
        if (!level.isClientSide) {
            level.setBlock(pos, state.setValue(levelProp, newLevel), 3); // 3 = 更新并同步客户端
            if (context.getPlayer() != null) {
                context.getPlayer().displayClientMessage(
                        Component.literal("☀ 光照等级已设为: " + newLevel),
                        true // 显示在 action bar
                );
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}