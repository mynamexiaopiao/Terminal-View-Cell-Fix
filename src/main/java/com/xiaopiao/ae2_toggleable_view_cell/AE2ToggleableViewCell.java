package com.xiaopiao.ae2_toggleable_view_cell;

import appeng.api.ids.AECreativeTabIds;
import appeng.api.upgrades.Upgrades;
import appeng.core.definitions.AEItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.registries.*;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(AE2ToggleableViewCell.MOD_ID)
public class AE2ToggleableViewCell {
    public static final String MOD_ID = "ae2_toggleable_view_cell";


    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredItem<ToggleableViewCellItem> TOGGLEABLE_VIEW_CELL_ITEM = ITEMS.register("toggleable_view_cell", ToggleableViewCellItem::new);


    public AE2ToggleableViewCell(IEventBus modEventBus, ModContainer modContainer) {
        ITEMS.register(modEventBus);

        modEventBus.addListener(AE2ToggleableViewCell::commonSetup);
        modEventBus.addListener(AE2ToggleableViewCell::addCreativeTab);

        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(AE2ToggleableViewCell::clientSetup);
        }
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(TOGGLEABLE_VIEW_CELL_ITEM.get(), id("enabled"), (stack, level, entity, seed) -> ToggleableViewCellItem.isEnabled(stack) ? 1 : 0);
        });
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Upgrades.add(AEItems.FUZZY_CARD, TOGGLEABLE_VIEW_CELL_ITEM.get(), 1);
            Upgrades.add(AEItems.INVERTER_CARD, TOGGLEABLE_VIEW_CELL_ITEM.get(), 1);
        });
    }

    public static void addCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (!event.getTabKey().equals(AECreativeTabIds.MAIN)) {
            return;
        }

        event.accept(new ItemStack(TOGGLEABLE_VIEW_CELL_ITEM.get()));
 }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
