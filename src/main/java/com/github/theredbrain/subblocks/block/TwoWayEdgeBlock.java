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
import net.minecraft.state.property.*;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
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
    protected static final VoxelShape VERTICAL_NORTH_EAST_EDGE_SHAPE;
    protected static final VoxelShape VERTICAL_NORTH_WEST_EDGE_SHAPE;
    protected static final VoxelShape VERTICAL_SOUTH_EAST_EDGE_SHAPE;
    protected static final VoxelShape VERTICAL_SOUTH_WEST_EDGE_SHAPE;
    
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
    
    protected static final VoxelShape VERTICAL_BOTTOM_NORTH_SHAPE;
    protected static final VoxelShape VERTICAL_BOTTOM_NORTH_MIRRORED_SHAPE;
    protected static final VoxelShape VERTICAL_BOTTOM_SOUTH_SHAPE;
    protected static final VoxelShape VERTICAL_BOTTOM_SOUTH_MIRRORED_SHAPE;
    protected static final VoxelShape VERTICAL_BOTTOM_EAST_SHAPE;
    protected static final VoxelShape VERTICAL_BOTTOM_EAST_MIRRORED_SHAPE;
    protected static final VoxelShape VERTICAL_BOTTOM_WEST_SHAPE;
    protected static final VoxelShape VERTICAL_BOTTOM_WEST_MIRRORED_SHAPE;
    protected static final VoxelShape VERTICAL_TOP_NORTH_SHAPE;
    protected static final VoxelShape VERTICAL_TOP_NORTH_MIRRORED_SHAPE;
    protected static final VoxelShape VERTICAL_TOP_SOUTH_SHAPE;
    protected static final VoxelShape VERTICAL_TOP_SOUTH_MIRRORED_SHAPE;
    protected static final VoxelShape VERTICAL_TOP_EAST_SHAPE;
    protected static final VoxelShape VERTICAL_TOP_EAST_MIRRORED_SHAPE;
    protected static final VoxelShape VERTICAL_TOP_WEST_SHAPE;
    protected static final VoxelShape VERTICAL_TOP_WEST_MIRRORED_SHAPE;

    public TwoWayEdgeBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HALF, BlockHalf.BOTTOM).with(FACING, Direction.SOUTH).with(MIRRORED, false).with(ORIENTATION, Orientation.HORIZONTAL).with(WATERLOGGED, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{FACING, HALF, MIRRORED, ORIENTATION});
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);
        BlockHalf half = state.get(HALF);
        boolean mirrored = state.get(MIRRORED);
        Orientation orientation = state.get(ORIENTATION);

        if (orientation == Orientation.HORIZONTAL) {
            return switch (facing) {
                case WEST -> half == BlockHalf.BOTTOM ? (mirrored ? HORIZONTAL_BOTTOM_SOUTH_SHAPE : HORIZONTAL_BOTTOM_WEST_SHAPE) : mirrored ? HORIZONTAL_TOP_SOUTH_SHAPE : HORIZONTAL_TOP_WEST_SHAPE;
                case NORTH -> half == BlockHalf.BOTTOM ? (mirrored ? HORIZONTAL_BOTTOM_WEST_SHAPE : HORIZONTAL_BOTTOM_NORTH_SHAPE) : mirrored ? HORIZONTAL_TOP_WEST_SHAPE : HORIZONTAL_TOP_NORTH_SHAPE;
                case EAST -> half == BlockHalf.BOTTOM ? (mirrored ? HORIZONTAL_BOTTOM_NORTH_SHAPE : HORIZONTAL_BOTTOM_EAST_SHAPE) : mirrored ? HORIZONTAL_TOP_NORTH_SHAPE : HORIZONTAL_TOP_EAST_SHAPE;
                default -> half == BlockHalf.BOTTOM ? (mirrored ? HORIZONTAL_BOTTOM_EAST_SHAPE : HORIZONTAL_BOTTOM_SOUTH_SHAPE) : mirrored ? HORIZONTAL_TOP_EAST_SHAPE : HORIZONTAL_TOP_SOUTH_SHAPE;
            };
        } else {
            return switch (facing) {
                case WEST -> half == BlockHalf.BOTTOM ? (mirrored ? VERTICAL_BOTTOM_WEST_MIRRORED_SHAPE : VERTICAL_BOTTOM_WEST_SHAPE) : mirrored ? VERTICAL_TOP_WEST_MIRRORED_SHAPE : VERTICAL_TOP_WEST_SHAPE;
                case NORTH -> half == BlockHalf.BOTTOM ? (mirrored ? VERTICAL_BOTTOM_NORTH_MIRRORED_SHAPE : VERTICAL_BOTTOM_NORTH_SHAPE) : mirrored ? VERTICAL_TOP_NORTH_MIRRORED_SHAPE : VERTICAL_TOP_NORTH_SHAPE;
                case EAST -> half == BlockHalf.BOTTOM ? (mirrored ? VERTICAL_BOTTOM_EAST_MIRRORED_SHAPE : VERTICAL_BOTTOM_EAST_SHAPE) : mirrored ? VERTICAL_TOP_EAST_MIRRORED_SHAPE : VERTICAL_TOP_EAST_SHAPE;
                default -> half == BlockHalf.BOTTOM ? (mirrored ? VERTICAL_BOTTOM_SOUTH_MIRRORED_SHAPE : VERTICAL_BOTTOM_SOUTH_SHAPE) : mirrored ? VERTICAL_TOP_SOUTH_MIRRORED_SHAPE : VERTICAL_TOP_SOUTH_SHAPE;
            };
        }
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide();
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        Vec3d hitPos = ctx.getHitPos();

        Direction hitPosToFacing;
        boolean mirrored;
        Orientation orientation;

        switch (direction) {
            case UP:
                orientation = Orientation.HORIZONTAL;
                if ((hitPos.getX() - (double)blockPos.getX()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.EAST;
                    mirrored = false;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.NORTH;
                    mirrored = false;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.WEST;
                    mirrored = false;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.SOUTH;
                    mirrored = false;
                    break;
                }
            case DOWN:
                orientation = Orientation.HORIZONTAL;
                if ((hitPos.getX() - (double)blockPos.getX()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.EAST;
                    mirrored = false;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.NORTH;
                    mirrored = false;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.WEST;
                    mirrored = false;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.SOUTH;
                    mirrored = false;
                    break;
                }
            case SOUTH:
                orientation = Orientation.VERTICAL;
                if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getX() - (double)blockPos.getX()) > 0.5) {
                    hitPosToFacing = Direction.NORTH;
                    mirrored = false;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getX() - (double)blockPos.getX()) < 0.5) {
                    hitPosToFacing = Direction.NORTH;
                    mirrored = true;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getX() - (double)blockPos.getX()) < 0.5) {
                    hitPosToFacing = Direction.NORTH;
                    mirrored = true;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getX() - (double)blockPos.getX()) > 0.5) {
                    hitPosToFacing = Direction.NORTH;
                    mirrored = false;
                    break;
                }
            case WEST:
                orientation = Orientation.VERTICAL;
                if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.EAST;
                    mirrored = false;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.EAST;
                    mirrored = true;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.EAST;
                    mirrored = true;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.EAST;
                    mirrored = false;
                    break;
                }
            case NORTH:
                orientation = Orientation.VERTICAL;
                if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getX() - (double)blockPos.getX()) > 0.5) {
                    hitPosToFacing = Direction.SOUTH;
                    mirrored = true;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getX() - (double)blockPos.getX()) < 0.5) {
                    hitPosToFacing = Direction.SOUTH;
                    mirrored = false;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getX() - (double)blockPos.getX()) < 0.5) {
                    hitPosToFacing = Direction.SOUTH;
                    mirrored = false;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getX() - (double)blockPos.getX()) > 0.5) {
                    hitPosToFacing = Direction.SOUTH;
                    mirrored = true;
                    break;
                }
            case EAST:
                orientation = Orientation.VERTICAL;
                if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.WEST;
                    mirrored = true;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.WEST;
                    mirrored = false;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.WEST;
                    mirrored = false;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.WEST;
                    mirrored = true;
                    break;
                }
            default:
                hitPosToFacing = Direction.SOUTH;
                mirrored = false;
                orientation = Orientation.HORIZONTAL;
        }

        BlockHalf newHalf = direction != Direction.DOWN && (direction == Direction.UP || !(hitPos.y - (double)blockPos.getY() > 0.5D)) ? BlockHalf.BOTTOM : BlockHalf.TOP;
        return this.getDefaultState().with(FACING, hitPosToFacing).with(HALF, newHalf).with(MIRRORED, mirrored). with(ORIENTATION, orientation).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
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
        boolean mirrored = state.get(MIRRORED);
        Orientation orientation = state.get(ORIENTATION);
        if (orientation == Orientation.HORIZONTAL) {
            return (mirror == BlockMirror.FRONT_BACK && facing.getAxis() == Direction.Axis.X) || (mirror == BlockMirror.LEFT_RIGHT && facing.getAxis() == Direction.Axis.Z) ? state.with(FACING, facing.rotateYClockwise()) : state.with(FACING, facing.rotateYCounterclockwise());
        } else {
            return (mirror == BlockMirror.FRONT_BACK && facing.getAxis() == Direction.Axis.X) || (mirror == BlockMirror.LEFT_RIGHT && facing.getAxis() == Direction.Axis.Z) ? state : state.with(MIRRORED, !mirrored);
        }
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
        VERTICAL_NORTH_EAST_EDGE_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        VERTICAL_NORTH_WEST_EDGE_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        VERTICAL_SOUTH_EAST_EDGE_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        VERTICAL_SOUTH_WEST_EDGE_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 16.0D, 16.0D);

        BOTTOM_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        BOTTOM_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
        BOTTOM_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        BOTTOM_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
        TOP_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        TOP_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        TOP_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        TOP_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);

        // final shapes
        HORIZONTAL_BOTTOM_SOUTH_SHAPE = VoxelShapes.union(BOTTOM_SOUTH_EDGE_SHAPE, BOTTOM_NORTH_WEST_CORNER_SHAPE);
        HORIZONTAL_BOTTOM_WEST_SHAPE = VoxelShapes.union(BOTTOM_WEST_EDGE_SHAPE, BOTTOM_NORTH_EAST_CORNER_SHAPE);
        HORIZONTAL_BOTTOM_NORTH_SHAPE = VoxelShapes.union(BOTTOM_NORTH_EDGE_SHAPE, BOTTOM_SOUTH_EAST_CORNER_SHAPE);
        HORIZONTAL_BOTTOM_EAST_SHAPE = VoxelShapes.union(BOTTOM_EAST_EDGE_SHAPE, BOTTOM_SOUTH_WEST_CORNER_SHAPE);
        HORIZONTAL_TOP_SOUTH_SHAPE = VoxelShapes.union(TOP_SOUTH_EDGE_SHAPE, TOP_NORTH_WEST_CORNER_SHAPE);
        HORIZONTAL_TOP_WEST_SHAPE = VoxelShapes.union(TOP_WEST_EDGE_SHAPE, TOP_NORTH_EAST_CORNER_SHAPE);
        HORIZONTAL_TOP_NORTH_SHAPE = VoxelShapes.union(TOP_NORTH_EDGE_SHAPE, TOP_SOUTH_EAST_CORNER_SHAPE);
        HORIZONTAL_TOP_EAST_SHAPE = VoxelShapes.union(TOP_EAST_EDGE_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE);

        VERTICAL_BOTTOM_NORTH_SHAPE = VoxelShapes.union(VERTICAL_NORTH_EAST_EDGE_SHAPE, BOTTOM_NORTH_WEST_CORNER_SHAPE);
        VERTICAL_BOTTOM_NORTH_MIRRORED_SHAPE = VoxelShapes.union(VERTICAL_NORTH_WEST_EDGE_SHAPE, BOTTOM_NORTH_EAST_CORNER_SHAPE);
        VERTICAL_BOTTOM_SOUTH_SHAPE = VoxelShapes.union(VERTICAL_SOUTH_WEST_EDGE_SHAPE, BOTTOM_SOUTH_EAST_CORNER_SHAPE);
        VERTICAL_BOTTOM_SOUTH_MIRRORED_SHAPE = VoxelShapes.union(VERTICAL_SOUTH_EAST_EDGE_SHAPE, BOTTOM_SOUTH_WEST_CORNER_SHAPE);
        VERTICAL_BOTTOM_EAST_SHAPE = VoxelShapes.union(VERTICAL_SOUTH_EAST_EDGE_SHAPE, BOTTOM_NORTH_EAST_CORNER_SHAPE);
        VERTICAL_BOTTOM_EAST_MIRRORED_SHAPE = VoxelShapes.union(VERTICAL_NORTH_EAST_EDGE_SHAPE, BOTTOM_SOUTH_EAST_CORNER_SHAPE);
        VERTICAL_BOTTOM_WEST_SHAPE = VoxelShapes.union(VERTICAL_NORTH_WEST_EDGE_SHAPE, BOTTOM_SOUTH_WEST_CORNER_SHAPE);
        VERTICAL_BOTTOM_WEST_MIRRORED_SHAPE = VoxelShapes.union(VERTICAL_SOUTH_WEST_EDGE_SHAPE, BOTTOM_NORTH_WEST_CORNER_SHAPE);
        VERTICAL_TOP_NORTH_SHAPE = VoxelShapes.union(VERTICAL_NORTH_EAST_EDGE_SHAPE, TOP_NORTH_WEST_CORNER_SHAPE);
        VERTICAL_TOP_NORTH_MIRRORED_SHAPE = VoxelShapes.union(VERTICAL_NORTH_WEST_EDGE_SHAPE, TOP_NORTH_EAST_CORNER_SHAPE);
        VERTICAL_TOP_SOUTH_SHAPE = VoxelShapes.union(VERTICAL_SOUTH_WEST_EDGE_SHAPE, TOP_SOUTH_EAST_CORNER_SHAPE);
        VERTICAL_TOP_SOUTH_MIRRORED_SHAPE = VoxelShapes.union(VERTICAL_SOUTH_EAST_EDGE_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE);
        VERTICAL_TOP_EAST_SHAPE = VoxelShapes.union(VERTICAL_SOUTH_EAST_EDGE_SHAPE, TOP_NORTH_EAST_CORNER_SHAPE);
        VERTICAL_TOP_EAST_MIRRORED_SHAPE = VoxelShapes.union(VERTICAL_NORTH_EAST_EDGE_SHAPE, TOP_SOUTH_EAST_CORNER_SHAPE);
        VERTICAL_TOP_WEST_SHAPE = VoxelShapes.union(VERTICAL_NORTH_WEST_EDGE_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE);
        VERTICAL_TOP_WEST_MIRRORED_SHAPE = VoxelShapes.union(VERTICAL_SOUTH_WEST_EDGE_SHAPE, TOP_NORTH_WEST_CORNER_SHAPE);
    }
}
