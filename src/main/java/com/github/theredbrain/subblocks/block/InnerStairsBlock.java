package com.github.theredbrain.subblocks.block;

import com.github.theredbrain.subblocks.block.enums.Orientation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class InnerStairsBlock extends AbstractSubBlock {
    public static final DirectionProperty FACING;
    public static final EnumProperty<BlockHalf> HALF;
    // part shapes
    protected static final VoxelShape BOTTOM_SLAB_SHAPE;
    protected static final VoxelShape TOP_SLAB_SHAPE;
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
    protected static final VoxelShape BOTTOM_NORTH_EAST_SHAPE;
    protected static final VoxelShape BOTTOM_NORTH_WEST_SHAPE;
    protected static final VoxelShape BOTTOM_SOUTH_EAST_SHAPE;
    protected static final VoxelShape BOTTOM_SOUTH_WEST_SHAPE;
    protected static final VoxelShape TOP_NORTH_EAST_SHAPE;
    protected static final VoxelShape TOP_NORTH_WEST_SHAPE;
    protected static final VoxelShape TOP_SOUTH_EAST_SHAPE;
    protected static final VoxelShape TOP_SOUTH_WEST_SHAPE;

    public InnerStairsBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HALF, BlockHalf.BOTTOM).with(FACING, Direction.SOUTH).with(WATERLOGGED, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{FACING, HALF});
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);
        BlockHalf half = state.get(HALF);

        return switch (facing) {
            case WEST -> half == BlockHalf.BOTTOM ? BOTTOM_NORTH_WEST_SHAPE : TOP_NORTH_WEST_SHAPE;
            case NORTH -> half == BlockHalf.BOTTOM ? BOTTOM_NORTH_EAST_SHAPE : TOP_NORTH_EAST_SHAPE;
            case EAST -> half == BlockHalf.BOTTOM ? BOTTOM_SOUTH_EAST_SHAPE : TOP_SOUTH_EAST_SHAPE;
            default -> half == BlockHalf.BOTTOM ? BOTTOM_SOUTH_WEST_SHAPE : TOP_SOUTH_WEST_SHAPE;
        };
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide();
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        Vec3d hitPos = ctx.getHitPos();

        Direction hitPosToFacing;

        switch (direction) {
            case UP:
                if ((hitPos.getX() - (double)blockPos.getX()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.EAST;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.NORTH;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.WEST;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.SOUTH;
                    break;
                }
            case DOWN:
                if ((hitPos.getX() - (double)blockPos.getX()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.EAST;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.NORTH;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.WEST;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.SOUTH;
                    break;
                }
            case SOUTH:
                if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getX() - (double)blockPos.getX()) > 0.5) {
                    hitPosToFacing = Direction.NORTH;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getX() - (double)blockPos.getX()) < 0.5) {
                    hitPosToFacing = Direction.WEST;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getX() - (double)blockPos.getX()) < 0.5) {
                    hitPosToFacing = Direction.WEST;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getX() - (double)blockPos.getX()) > 0.5) {
                    hitPosToFacing = Direction.NORTH;
                    break;
                }
            case WEST:
                if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.EAST;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.NORTH;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.NORTH;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.EAST;
                    break;
                }
            case NORTH:
                if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getX() - (double)blockPos.getX()) > 0.5) {
                    hitPosToFacing = Direction.EAST;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getX() - (double)blockPos.getX()) < 0.5) {
                    hitPosToFacing = Direction.SOUTH;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getX() - (double)blockPos.getX()) < 0.5) {
                    hitPosToFacing = Direction.SOUTH;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getX() - (double)blockPos.getX()) > 0.5) {
                    hitPosToFacing = Direction.EAST;
                    break;
                }
            case EAST:
                if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.SOUTH;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.WEST;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.WEST;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.SOUTH;
                    break;
                }
            default:
                hitPosToFacing = Direction.SOUTH;
        }

        BlockHalf newHalf = direction != Direction.DOWN && (direction == Direction.UP || !(hitPos.y - (double)blockPos.getY() > 0.5D)) ? BlockHalf.BOTTOM : BlockHalf.TOP;
        return this.getDefaultState().with(FACING, hitPosToFacing).with(HALF, newHalf).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
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
        return (mirror == BlockMirror.FRONT_BACK && facing.getAxis() == Direction.Axis.X) || (mirror == BlockMirror.LEFT_RIGHT && facing.getAxis() == Direction.Axis.Z) ? state.with(FACING, facing.rotateYClockwise()) : state.with(FACING, facing.rotateYCounterclockwise());
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        //TODO
        // can replace corners of same base block
        return false;
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
        HALF = Properties.BLOCK_HALF;
        WATERLOGGED = Properties.WATERLOGGED;

        BOTTOM_SLAB_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
        TOP_SLAB_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        
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

        BOTTOM_NORTH_EAST_SHAPE = VoxelShapes.union(BOTTOM_SLAB_SHAPE, TOP_NORTH_EDGE_SHAPE, TOP_SOUTH_EAST_CORNER_SHAPE);
        BOTTOM_NORTH_WEST_SHAPE = VoxelShapes.union(BOTTOM_SLAB_SHAPE, TOP_NORTH_EDGE_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE);
        BOTTOM_SOUTH_EAST_SHAPE = VoxelShapes.union(BOTTOM_SLAB_SHAPE, TOP_SOUTH_EDGE_SHAPE, TOP_NORTH_EAST_CORNER_SHAPE);
        BOTTOM_SOUTH_WEST_SHAPE = VoxelShapes.union(BOTTOM_SLAB_SHAPE, TOP_SOUTH_EDGE_SHAPE, TOP_NORTH_WEST_CORNER_SHAPE);
        TOP_NORTH_EAST_SHAPE = VoxelShapes.union(TOP_SLAB_SHAPE, BOTTOM_NORTH_EDGE_SHAPE, BOTTOM_SOUTH_EAST_CORNER_SHAPE);
        TOP_NORTH_WEST_SHAPE = VoxelShapes.union(TOP_SLAB_SHAPE, BOTTOM_NORTH_EDGE_SHAPE, BOTTOM_SOUTH_WEST_CORNER_SHAPE);
        TOP_SOUTH_EAST_SHAPE = VoxelShapes.union(TOP_SLAB_SHAPE, BOTTOM_SOUTH_EDGE_SHAPE, BOTTOM_NORTH_EAST_CORNER_SHAPE);
        TOP_SOUTH_WEST_SHAPE = VoxelShapes.union(TOP_SLAB_SHAPE, BOTTOM_SOUTH_EDGE_SHAPE, BOTTOM_NORTH_WEST_CORNER_SHAPE);
    }
}
