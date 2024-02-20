package com.github.theredbrain.subblocks.block;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class PillarBlock extends AbstractSubBlock {
    public static EnumProperty<Direction.Axis> AXIS;
    protected static final VoxelShape X_SHAPE;
    protected static final VoxelShape Y_SHAPE;
    protected static final VoxelShape Z_SHAPE;

    public PillarBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AXIS, Direction.Axis.Y).with(WATERLOGGED, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{AXIS});
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(AXIS) == Direction.Axis.X) {
            return X_SHAPE;
        } else if (state.get(AXIS) == Direction.Axis.Y) {
            return Y_SHAPE;
        } else {
            return Z_SHAPE;
        }
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        return this.getDefaultState().with(AXIS, ctx.getSide().getAxis()).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Deprecated
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state; // TODO
    }

    @Deprecated
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state; // TODO
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return false;
    }

    static {
        AXIS = Properties.AXIS;
        WATERLOGGED = Properties.WATERLOGGED;

        X_SHAPE = Block.createCuboidShape(0.0D, 3.0D, 3.0D, 16.0D, 13.0D, 13.0D);
        Y_SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
        Z_SHAPE = Block.createCuboidShape(3.0D, 3.0D, 0.0D, 13.0D, 13.0D, 16.0D);
    }
}
