package com.github.theredbrain.subblocks.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;

public abstract class AbstractSubBlock extends Block implements Waterloggable {
    public static BooleanProperty WATERLOGGED;
    private final Block baseBlock;
    private final BlockState baseBlockState;

    public AbstractSubBlock(BlockState baseBlockState, Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false));
        this.baseBlock = baseBlockState.getBlock();
        this.baseBlockState = baseBlockState;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{WATERLOGGED});
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public abstract VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context);

    public abstract BlockState getPlacementState(ItemPlacementContext ctx);

    public abstract boolean canReplace(BlockState state, ItemPlacementContext context);

    //-----------------------waterlogging stuff-----------------------

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        switch(type) {
            case LAND:
                return false;
            case WATER:
                return world.getFluidState(pos).isIn(FluidTags.WATER);
            case AIR:
                return false;
            default:
                return false;
        }
    }

    //-----------------------inheriting base block stats-----------------------

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        this.baseBlock.randomDisplayTick(state, world, pos, random);
    }

    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        this.baseBlockState.onBlockBreakStart(world, pos, player);
    }

    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        this.baseBlock.onBroken(world, pos, state);
    }

    public float getBlastResistance() {
        return this.baseBlock.getBlastResistance();
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!state.isOf(state.getBlock())) {
            world.updateNeighbor(this.baseBlockState, pos, Blocks.AIR, pos, false);
            this.baseBlock.onBlockAdded(this.baseBlockState, world, pos, oldState, false);
        }
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            this.baseBlockState.onStateReplaced(world, pos, newState, moved);
        }
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        this.baseBlock.onSteppedOn(world, pos, state, entity);
    }

    public boolean hasRandomTicks(BlockState state) {
        return this.baseBlock.hasRandomTicks(state);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.baseBlock.randomTick(state, world, pos, random);
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.baseBlock.scheduledTick(state, world, pos, random);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return this.baseBlockState.onUse(world, player, hand, hit);
    }

    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        this.baseBlock.onDestroyedByExplosion(world, pos, explosion);
    }
}
