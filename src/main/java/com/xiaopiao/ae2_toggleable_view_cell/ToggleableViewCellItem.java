package com.xiaopiao.ae2_toggleable_view_cell;

import appeng.items.storage.ViewCellItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public class ToggleableViewCellItem extends ViewCellItem {
    public ToggleableViewCellItem() {
        super(new Item.Properties().stacksTo(1));
    }
    public static boolean isEnabled(ItemStack stack) {
        if (stack.getItem() instanceof ToggleableViewCellItem item) {
            return item.getEnabled(stack);
        }
        return true;
    }

    public boolean getEnabled(ItemStack stack) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            return customData.copyTag().getBoolean("Enabled");
        }
        return true;
    }

    public void setEnabled(ItemStack stack, boolean enabled) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
                    tag.putBoolean("Enabled", enabled);
                });
    }

    public void toggle(ItemStack stack) {
        setEnabled(stack, !getEnabled(stack));
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack first, ItemStack other, Slot slot, ClickAction clickAction, Player player, SlotAccess access) {
        if (!(clickAction == ClickAction.SECONDARY && first.getItem() instanceof ToggleableViewCellItem item)) {
            return false;
        }

        item.toggle(first);
        player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 0.2f, 1);
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
