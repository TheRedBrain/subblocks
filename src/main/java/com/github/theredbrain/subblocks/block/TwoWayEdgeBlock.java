package com.github.theredbrain.subblocks.block;

import com.github.theredbrain.subblocks.block.enums.Orientation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class TwoWayEdgeBlock extends AbstractSubBlock {

    public static final DirectionProperty FACING;
    public static final EnumProperty<BlockHalf> HALF;
    public static final BooleanProperty MIRRORED;
    public static final EnumProperty<Orientation> ORIENTATION;
    // part shapes
    protected static final VoxelShape BOTTOM_SOUTH_EDGE_SHAPE;
    protected static final VoxelShape BOTTOM_WEST_EDGE_SHAPE;
    protected static final VoxelShape BOTTOM_NORTH_EDGE_SHAPE;
    protected static final VoxelShape BOTTOM_EAST_EDGE_SHAPE;
    protected static final VoxelShape TOP_SOUTH_EDGE_SHAPE;
    protected static final VoxelShape TOP_WEST_EDGE_SHAPE;
    protected static final VoxelShape TOP_NORTH_EDGE_SHAPE;
    protected static final VoxelShape TOP_EAST_EDGE_SHAPE;
    protected static final VoxelShape BOTTOM_NORTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_NORTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_SOUTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_SOUTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape TOP_NORTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape TOP_NORTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape TOP_SOUTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape TOP_SOUTH_WEST_CORNER_SHAPE;
    // final shapes
    protected static final VoxelShape HORIZONTAL_BOTTOM_SOUTH_SHAPE;
    protected static final VoxelShape HORIZONTAL_BOTTOM_WEST_SHAPE;
    protected static final VoxelShape HORIZONTAL_BOTTOM_NORTH_SHAPE;
    protected static final VoxelShape HORIZONTAL_BOTTOM_EAST_SHAPE;
    protected static final VoxelShape HORIZONTAL_TOP_SOUTH_SHAPE;
    protected static final VoxelShape HORIZONTAL_TOP_WEST_SHAPE;
    protected static final VoxelShape HORIZONTAL_TOP_NORTH_SHAPE;
    protected static final VoxelShape HORIZONTAL_TOP_EAST_SHAPE;
    protected static final VoxelShape VERTICAL_NORTH_EAST_SHAPE;
    protected static final VoxelShape VERTICAL_NORTH_WEST_SHAPE;
    protected static final VoxelShape VERTICAL_SOUTH_EAST_SHAPE;
    protected static final VoxelShape VERTICAL_SOUTH_WEST_SHAPE;

    public TwoWayEdgeBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HALF, BlockHalf.BOTTOM).with(FACING, Direction.SOUTH).with(MIRRORED, false).with(ORIENTATION, Orientation.HORIZONTAL).with(WATERLOGGED, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{FACING, HALF, MIRRORED, ORIENTATION});
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState();
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return false;
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
        HALF = Properties.BLOCK_HALF;
        MIRRORED = BooleanProperty.of("mirrored");
        ORIENTATION = EnumProperty.of("orientation", Orientation.class);
        WATERLOGGED = Properties.WATERLOGGED;

        BOTTOM_SOUTH_EDGE_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        BOTTOM_WEST_EDGE_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 16.0D);
        BOTTOM_NORTH_EDGE_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        BOTTOM_EAST_EDGE_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
        TOP_SOUTH_EDGE_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        TOP_WEST_EDGE_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 16.0D);
        TOP_NORTH_EDGE_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        TOP_EAST_EDGE_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);

        BOTTOM_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        BOTTOM_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
        BOTTOM_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        BOTTOM_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
        TOP_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        TOP_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        TOP_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        TOP_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);

        // TODO
        HORIZONTAL_BOTTOM_SOUTH_SHAPE = VoxelShapes.union(BOTTOM_SOUTH_EAST_CORNER_SHAPE, BOTTOM_SOUTH_WEST_CORNER_SHAPE);
        HORIZONTAL_BOTTOM_WEST_SHAPE = VoxelShapes.union(BOTTOM_NORTH_WEST_CORNER_SHAPE, BOTTOM_SOUTH_WEST_CORNER_SHAPE);
        HORIZONTAL_BOTTOM_NORTH_SHAPE = VoxelShapes.union(BOTTOM_NORTH_EAST_CORNER_SHAPE, BOTTOM_NORTH_WEST_CORNER_SHAPE);
        HORIZONTAL_BOTTOM_EAST_SHAPE = VoxelShapes.union(BOTTOM_NORTH_EAST_CORNER_SHAPE, BOTTOM_SOUTH_EAST_CORNER_SHAPE);
        HORIZONTAL_TOP_SOUTH_SHAPE = VoxelShapes.union(TOP_SOUTH_EAST_CORNER_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE);
        HORIZONTAL_TOP_WEST_SHAPE = VoxelShapes.union(TOP_NORTH_WEST_CORNER_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE);
        HORIZONTAL_TOP_NORTH_SHAPE = VoxelShapes.union(TOP_NORTH_EAST_CORNER_SHAPE, TOP_NORTH_WEST_CORNER_SHAPE);
        HORIZONTAL_TOP_EAST_SHAPE = VoxelShapes.union(TOP_NORTH_EAST_CORNER_SHAPE, TOP_SOUTH_EAST_CORNER_SHAPE);
        VERTICAL_NORTH_EAST_SHAPE = VoxelShapes.union(BOTTOM_NORTH_EAST_CORNER_SHAPE, TOP_NORTH_EAST_CORNER_SHAPE);
        VERTICAL_NORTH_WEST_SHAPE = VoxelShapes.union(BOTTOM_NORTH_WEST_CORNER_SHAPE, TOP_NORTH_WEST_CORNER_SHAPE);
        VERTICAL_SOUTH_EAST_SHAPE = VoxelShapes.union(BOTTOM_SOUTH_EAST_CORNER_SHAPE, TOP_SOUTH_EAST_CORNER_SHAPE);
        VERTICAL_SOUTH_WEST_SHAPE = VoxelShapes.union(BOTTOM_SOUTH_WEST_CORNER_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE);
    }
}
