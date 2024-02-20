package com.github.theredbrain.subblocks.registry;

import com.github.theredbrain.subblocks.SubBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

public class ItemGroupRegistry {
    public static final RegistryKey<ItemGroup> SUB_BLOCKS = RegistryKey.of(RegistryKeys.ITEM_GROUP, SubBlocks.identifier("sub_blocks"));

    public static void init() {
        Registry.register(Registries.ITEM_GROUP, SUB_BLOCKS, FabricItemGroup.builder()
                .icon(() -> new ItemStack(Blocks.STONE_STAIRS))
                .displayName(Text.translatable("itemGroup.subblocks.sub_blocks"))
                .build());
    }
}
