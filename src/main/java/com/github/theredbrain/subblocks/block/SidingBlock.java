package com.github.theredbrain.subblocks.block;

import com.github.theredbrain.subblocks.block.enums.Orientation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class SidingBlock extends AbstractSubBlock {

    public static DirectionProperty FACING;
    public static EnumProperty<Orientation> ORIENTATION;
    public static final EnumProperty<SlabType> TYPE;
    protected static final VoxelShape BOTTOM_SHAPE;
    protected static final VoxelShape TOP_SHAPE;
    protected static final VoxelShape VERTICAL_SHAPE_NORTH;
    protected static final VoxelShape VERTICAL_SHAPE_EAST;
    protected static final VoxelShape VERTICAL_SHAPE_SOUTH;
    protected static final VoxelShape VERTICAL_SHAPE_WEST;

    public SidingBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(ORIENTATION, Orientation.HORIZONTAL).with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{FACING, ORIENTATION, TYPE});
    }

    public boolean hasSidedTransparency(BlockState state) {
        return state.get(TYPE) != SlabType.DOUBLE;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        SlabType slabType = (SlabType)state.get(TYPE);
        Orientation direction = state.get(ORIENTATION);
        Direction facing = state.get(FACING);
        if (slabType == SlabType.DOUBLE) {
            return VoxelShapes.fullCube();
        } else if (direction == Orientation.HORIZONTAL) {
            if (slabType == SlabType.TOP) {
                return TOP_SHAPE;
            }
            return BOTTOM_SHAPE;
        } else {
            return switch (facing) {
                case EAST -> VERTICAL_SHAPE_EAST;
                case SOUTH -> VERTICAL_SHAPE_SOUTH;
                case WEST -> VERTICAL_SHAPE_WEST;
                default -> VERTICAL_SHAPE_NORTH;
            };
        }
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);
        if (blockState.isOf(this)) {
            return (BlockState)((BlockState)blockState.with(TYPE, SlabType.DOUBLE)).with(WATERLOGGED, false);
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
            BlockState blockState2 = (BlockState)((BlockState)this.getDefaultState().with(ORIENTATION, Orientation.HORIZONTAL).with(FACING, Direction.NORTH).with(TYPE, SlabType.BOTTOM)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
            Direction direction = ctx.getSide();
            return direction == Direction.UP ? blockState2 : direction == Direction.EAST ? blockState2.with(ORIENTATION, Orientation.VERTICAL).with(FACING, Direction.WEST) : direction == Direction.WEST ? blockState2.with(ORIENTATION, Orientation.VERTICAL).with(FACING, Direction.EAST) : direction == Direction.SOUTH ? blockState2.with(ORIENTATION, Orientation.VERTICAL).with(FACING, Direction.NORTH) : direction == Direction.NORTH ? blockState2.with(ORIENTATION, Orientation.VERTICAL).with(FACING, Direction.SOUTH) : (BlockState)blockState2.with(TYPE, SlabType.TOP);
        }
    }

    @Deprecated
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        if (rotation == BlockRotation.NONE) {
            return state;
        }
        state = state.with(FACING, state.get(FACING).rotateYClockwise());
        if (rotation == BlockRotation.CLOCKWISE_90) {
            return state;
        }
        state = state.with(FACING, state.get(FACING).rotateYClockwise());
        if (rotation == BlockRotation.CLOCKWISE_180) {
            return state;
        }
        return state.with(FACING, state.get(FACING).rotateYClockwise());
    }

    @Deprecated
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        Direction facing = state.get(FACING);
        return (mirror == BlockMirror.FRONT_BACK && facing.getAxis() == Direction.Axis.X) || (mirror == BlockMirror.LEFT_RIGHT && facing.getAxis() == Direction.Axis.Z) ? state.with(FACING, facing.getOpposite()) : state;
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        ItemStack itemStack = context.getStack();
        SlabType slabType = (SlabType)state.get(TYPE);
        Orientation orientation = state.get(ORIENTATION);
        Direction facing = state.get(FACING);
        if (slabType != SlabType.DOUBLE && itemStack.isOf(this.asItem())) {
            if (context.canReplaceExisting()) {
                Direction placingDirection = context.getSide();
                if (orientation == Orientation.HORIZONTAL) {
                    if (slabType == SlabType.BOTTOM) {
                        return placingDirection == Direction.UP;
                    } else {
                        return placingDirection == Direction.DOWN;
                    }
                } else {
                    if (facing == Direction.EAST) {
                        return placingDirection == Direction.WEST;
                    } else if (facing == Direction.SOUTH) {
                        return placingDirection == Direction.NORTH;
                    } else if (facing == Direction.WEST) {
                        return placingDirection == Direction.EAST;
                    } else {
                        return placingDirection == Direction.SOUTH;
                    }
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    //-----------------------waterlogging stuff-----------------------

    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        return state.get(TYPE) != SlabType.DOUBLE && super.tryFillWithFluid(world, pos, state, fluidState);
    }

    public boolean canFillWithFluid(@Nullable PlayerEntity player, BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return state.get(TYPE) != SlabType.DOUBLE && super.canFillWithFluid(player, world, pos, state, fluid);
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
        ORIENTATION = EnumProperty.of("orientation", Orientation.class);
        TYPE = Properties.SLAB_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
        BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
        TOP_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        VERTICAL_SHAPE_NORTH = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        VERTICAL_SHAPE_EAST = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        VERTICAL_SHAPE_SOUTH = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        VERTICAL_SHAPE_WEST = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
    }
}
