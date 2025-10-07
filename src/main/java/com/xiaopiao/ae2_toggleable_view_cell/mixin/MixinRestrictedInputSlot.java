package com.xiaopiao.ae2_toggleable_view_cell.mixin;

import appeng.menu.slot.RestrictedInputSlot;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.xiaopiao.ae2_toggleable_view_cell.ToggleableViewCellItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = RestrictedInputSlot.class)
public abstract class MixinRestrictedInputSlot {
    @ModifyReturnValue(method = "mayPlace", at = @At(value = "RETURN", ordinal = 13))
    private boolean ae2_toggleable_view_cell$mayPlaceAllowToggleableViewCellItem(boolean original, ItemStack stack) {
        return original || stack.getItem() instanceof ToggleableViewCellItem;
    }
}
