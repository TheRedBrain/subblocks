package com.github.theredbrain.subblocks.registry;

import com.github.theredbrain.subblocks.SubBlocks;
import com.github.theredbrain.subblocks.block.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class BlockRegistry {
    public static void init() {
//        registerSubBlocks("acacia", Blocks.ACACIA_PLANKS, ItemGroupRegistry.SUB_BLOCKS);
//        registerSubBlocks("birch", Blocks.BIRCH_PLANKS, ItemGroupRegistry.SUB_BLOCKS);
//        registerSubBlocks("cherry", Blocks.CHERRY_PLANKS, ItemGroupRegistry.SUB_BLOCKS);
//        registerSubBlocks("cobblestone", Blocks.COBBLESTONE, ItemGroupRegistry.SUB_BLOCKS);
//        registerSubBlocks("dark_oak", Blocks.DARK_OAK_PLANKS, ItemGroupRegistry.SUB_BLOCKS);
//        registerSubBlocks("jungle", Blocks.JUNGLE_PLANKS, ItemGroupRegistry.SUB_BLOCKS);
//        registerSubBlocks("mangrove", Blocks.MANGROVE_PLANKS, ItemGroupRegistry.SUB_BLOCKS);
//        registerSubBlocks("oak", Blocks.OAK_PLANKS, ItemGroupRegistry.SUB_BLOCKS);
//        registerSubBlocks("spruce", Blocks.SPRUCE_PLANKS, ItemGroupRegistry.SUB_BLOCKS);
        registerSubBlocks("stone", Blocks.STONE, ItemGroupRegistry.SUB_BLOCKS);
    }
    private static void registerSubBlocks(String name, Block baseBlock, RegistryKey<ItemGroup> itemGroup) {
        Identifier identifier;

        // corner
        identifier = SubBlocks.identifier(name + "_corner");
        Block cornerBlock = new CornerBlock(baseBlock.getDefaultState(), FabricBlockSettings.copy(baseBlock));
        Registry.register(Registries.ITEM, identifier, new BlockItem(cornerBlock, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(cornerBlock));
        Registry.register(Registries.BLOCK, identifier, cornerBlock);

        // edge
        identifier = SubBlocks.identifier(name + "_edge");
        Block edgeBlock = new EdgeBlock(baseBlock.getDefaultState(), FabricBlockSettings.copy(baseBlock));
        Registry.register(Registries.ITEM, identifier, new BlockItem(edgeBlock, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(edgeBlock));
        Registry.register(Registries.BLOCK, identifier, edgeBlock);

        // inner stairs
        identifier = SubBlocks.identifier(name + "_inner_stairs");
        Block innerStairsBlock = new InnerStairsBlock(baseBlock.getDefaultState(), FabricBlockSettings.copy(baseBlock));
        Registry.register(Registries.ITEM, identifier, new BlockItem(innerStairsBlock, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(innerStairsBlock));
        Registry.register(Registries.BLOCK, identifier, innerStairsBlock);

        // outer stairs
        identifier = SubBlocks.identifier(name + "_outer_stairs");
        Block outerStairsBlock = new OuterStairsBlock(baseBlock.getDefaultState(), FabricBlockSettings.copy(baseBlock));
        Registry.register(Registries.ITEM, identifier, new BlockItem(outerStairsBlock, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(outerStairsBlock));
        Registry.register(Registries.BLOCK, identifier, outerStairsBlock);

        // pillar
        identifier = SubBlocks.identifier(name + "_pillar");
        Block pillarBlock = new PillarBlock(baseBlock.getDefaultState(), FabricBlockSettings.copy(baseBlock));
        Registry.register(Registries.ITEM, identifier, new BlockItem(pillarBlock, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(pillarBlock));
        Registry.register(Registries.BLOCK, identifier, pillarBlock);

        // pillar cap
        identifier = SubBlocks.identifier(name + "_pillar_cap");
        Block pillarCapBlock = new PillarCapBlock(baseBlock.getDefaultState(), FabricBlockSettings.copy(baseBlock));
        Registry.register(Registries.ITEM, identifier, new BlockItem(pillarCapBlock, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(pillarCapBlock));
        Registry.register(Registries.BLOCK, identifier, pillarCapBlock);

        // siding
        identifier = SubBlocks.identifier(name + "_siding");
        Block sidingBlock = new SidingBlock(baseBlock.getDefaultState(), FabricBlockSettings.copy(baseBlock));
        Registry.register(Registries.ITEM, identifier, new BlockItem(sidingBlock, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(sidingBlock));
        Registry.register(Registries.BLOCK, identifier, sidingBlock);

        // stairs
        identifier = SubBlocks.identifier(name + "_stairs");
        Block stairsBlock = new StairsBlock(baseBlock.getDefaultState(), FabricBlockSettings.copy(baseBlock));
        Registry.register(Registries.ITEM, identifier, new BlockItem(stairsBlock, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(stairsBlock));
        Registry.register(Registries.BLOCK, identifier, stairsBlock);

        // three way edge
        identifier = SubBlocks.identifier(name + "_three_way_edge");
        Block threeWayEdgeBlock = new ThreeWayEdgeBlock(baseBlock.getDefaultState(), FabricBlockSettings.copy(baseBlock));
        Registry.register(Registries.ITEM, identifier, new BlockItem(threeWayEdgeBlock, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(threeWayEdgeBlock));
        Registry.register(Registries.BLOCK, identifier, threeWayEdgeBlock);

        // two way edge
        identifier = SubBlocks.identifier(name + "_two_way_edge");
        Block twoWayEdgeBlock = new TwoWayEdgeBlock(baseBlock.getDefaultState(), FabricBlockSettings.copy(baseBlock));
        Registry.register(Registries.ITEM, identifier, new BlockItem(twoWayEdgeBlock, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(twoWayEdgeBlock));
        Registry.register(Registries.BLOCK, identifier, twoWayEdgeBlock);
    }
}
