package com.xiaopiao.terminal_view_cell_fix.mixin;

import appeng.client.Point;
import appeng.client.gui.style.Blitter;
import appeng.client.gui.widgets.UpgradesPanel;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(UpgradesPanel.class)
public abstract class UpgradesPanelMixin {
    @Shadow @Final private static int SLOT_SIZE;

    @Shadow protected abstract int getUpgradeSlotCount();

    @Shadow @Final private static int PADDING;
    @Shadow private Point screenOrigin;
    @Shadow @Final private static int MAX_ROWS;



    @Shadow private int x;
    @Shadow private int y;
    private static final Blitter LEFT_BACKGROUND = Blitter.texture("guis/left_extra_panels.png", 128, 128);

    private static final Blitter LEFT_INNER_CORNER = LEFT_BACKGROUND.copy().src(12, 33, SLOT_SIZE, SLOT_SIZE);

    /**
     * @author
     * @reason
     */
    @Overwrite
    private static void drawSlot(GuiGraphics guiGraphics, int x, int y,
                                 boolean borderLeft, boolean borderTop, boolean borderRight, boolean borderBottom) {
        int srcX = PADDING;
        int srcY = PADDING;
        int srcWidth = SLOT_SIZE;
        int srcHeight = SLOT_SIZE;

        if (borderLeft) {
            x -= PADDING;
            srcX = 0;
            srcWidth += PADDING;
        }
        if (borderRight) {
            srcWidth += PADDING;
        }
        if (borderTop) {
            y -= PADDING;
            srcY = 0;
            srcHeight += PADDING;
        }
        if (borderBottom) {
            srcHeight += PADDING + 2;
        }

        LEFT_BACKGROUND.src(srcX, srcY, srcWidth, srcHeight)
                .dest(x-4, y)
                .blit(guiGraphics);
    }
}
