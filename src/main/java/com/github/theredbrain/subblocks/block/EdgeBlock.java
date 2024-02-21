package com.github.theredbrain.subblocks.block;

import com.github.theredbrain.subblocks.block.enums.Orientation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
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

public class EdgeBlock extends AbstractSubBlock {
    public static DirectionProperty FACING;
    public static EnumProperty<Orientation> ORIENTATION;
    public static final EnumProperty<BlockHalf> HALF;
    // part shapes
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

    public EdgeBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.SOUTH).with(HALF, BlockHalf.BOTTOM).with(ORIENTATION, Orientation.HORIZONTAL).with(WATERLOGGED, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{FACING, HALF, ORIENTATION});
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Orientation orientation = state.get(ORIENTATION);
        Direction facing = state.get(FACING);
        BlockHalf half = state.get(HALF);

        if (orientation == Orientation.HORIZONTAL) {
            return switch (facing) {
                case WEST -> half == BlockHalf.BOTTOM ? HORIZONTAL_BOTTOM_WEST_SHAPE : HORIZONTAL_TOP_WEST_SHAPE;
                case NORTH -> half == BlockHalf.BOTTOM ? HORIZONTAL_BOTTOM_NORTH_SHAPE : HORIZONTAL_TOP_NORTH_SHAPE;
                case EAST -> half == BlockHalf.BOTTOM ? HORIZONTAL_BOTTOM_EAST_SHAPE : HORIZONTAL_TOP_EAST_SHAPE;
                default -> half == BlockHalf.BOTTOM ? HORIZONTAL_BOTTOM_SOUTH_SHAPE : HORIZONTAL_TOP_SOUTH_SHAPE;
            };
        } else {
            return switch (facing) {
                case WEST -> VERTICAL_NORTH_WEST_SHAPE;
                case NORTH -> VERTICAL_NORTH_EAST_SHAPE;
                case EAST -> VERTICAL_SOUTH_EAST_SHAPE;
                default -> VERTICAL_SOUTH_WEST_SHAPE;
            };
        }
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide();
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        Vec3d hitPos = ctx.getHitPos();

        Orientation hitPosToOrientation;
        Direction hitPosToFacing;

//        RedBrainsSurvivalMod.LOGGER.info("X: " + (hitPos.getX() - (double)blockPos.getX()));
//        RedBrainsSurvivalMod.LOGGER.info("Y: " + (hitPos.getY() - (double)blockPos.getY()));
//        RedBrainsSurvivalMod.LOGGER.info("Z: " + (hitPos.getZ() - (double)blockPos.getZ()));

        switch (direction) {
            case UP:
//                RedBrainsSurvivalMod.LOGGER.info("UP");
                if ((hitPos.getX() - (double)blockPos.getX()) < (hitPos.getZ() - (double)blockPos.getZ()) && 1 - (hitPos.getX() - (double)blockPos.getX()) < (hitPos.getZ() - (double)blockPos.getZ())) {
                    hitPosToOrientation = Orientation.HORIZONTAL;
                    hitPosToFacing = Direction.SOUTH;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) > (hitPos.getZ() - (double)blockPos.getZ()) && 1 - (hitPos.getX() - (double)blockPos.getX()) < (hitPos.getZ() - (double)blockPos.getZ())) {
                    hitPosToOrientation = Orientation.HORIZONTAL;
                    hitPosToFacing = Direction.EAST;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) > (hitPos.getZ() - (double)blockPos.getZ()) && 1 - (hitPos.getX() - (double)blockPos.getX()) > (hitPos.getZ() - (double)blockPos.getZ())) {
                    hitPosToOrientation = Orientation.HORIZONTAL;
                    hitPosToFacing = Direction.NORTH;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) < (hitPos.getZ() - (double)blockPos.getZ()) && 1 - (hitPos.getX() - (double)blockPos.getX()) > (hitPos.getZ() - (double)blockPos.getZ())) {
                    hitPosToOrientation = Orientation.HORIZONTAL;
                    hitPosToFacing = Direction.WEST;
                    break;
                }
            case DOWN:
//                RedBrainsSurvivalMod.LOGGER.info("DOWN");
                if ((hitPos.getX() - (double)blockPos.getX()) < (hitPos.getZ() - (double)blockPos.getZ()) && 1 - (hitPos.getX() - (double)blockPos.getX()) < (hitPos.getZ() - (double)blockPos.getZ())) {
                    hitPosToOrientation = Orientation.HORIZONTAL;
                    hitPosToFacing = Direction.SOUTH;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) > (hitPos.getZ() - (double)blockPos.getZ()) && 1 - (hitPos.getX() - (double)blockPos.getX()) < (hitPos.getZ() - (double)blockPos.getZ())) {
                    hitPosToOrientation = Orientation.HORIZONTAL;
                    hitPosToFacing = Direction.EAST;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) > (hitPos.getZ() - (double)blockPos.getZ()) && 1 - (hitPos.getX() - (double)blockPos.getX()) > (hitPos.getZ() - (double)blockPos.getZ())) {
                    hitPosToOrientation = Orientation.HORIZONTAL;
                    hitPosToFacing = Direction.NORTH;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) < (hitPos.getZ() - (double)blockPos.getZ()) && 1 - (hitPos.getX() - (double)blockPos.getX()) > (hitPos.getZ() - (double)blockPos.getZ())) {
                    hitPosToOrientation = Orientation.HORIZONTAL;
                    hitPosToFacing = Direction.WEST;
                    break;
                }
            case SOUTH:
//                RedBrainsSurvivalMod.LOGGER.info("SOUTH");
                if ((hitPos.getY() - (double)blockPos.getY()) < (hitPos.getX() - (double)blockPos.getX()) && 1 - (hitPos.getY() - (double)blockPos.getY()) < (hitPos.getX() - (double)blockPos.getX())) {
                    hitPosToOrientation = Orientation.VERTICAL;
                    hitPosToFacing = Direction.NORTH;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > (hitPos.getX() - (double)blockPos.getX()) && 1 - (hitPos.getY() - (double)blockPos.getY()) < (hitPos.getX() - (double)blockPos.getX())) {
                    hitPosToOrientation = Orientation.HORIZONTAL;
                    hitPosToFacing = Direction.NORTH;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > (hitPos.getX() - (double)blockPos.getX()) && 1 - (hitPos.getY() - (double)blockPos.getY()) > (hitPos.getX() - (double)blockPos.getX())) {
                    hitPosToOrientation = Orientation.VERTICAL;
                    hitPosToFacing = Direction.WEST;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < (hitPos.getX() - (double)blockPos.getX()) && 1 - (hitPos.getY() - (double)blockPos.getY()) > (hitPos.getX() - (double)blockPos.getX())) {
                    hitPosToOrientation = Orientation.HORIZONTAL;
                    hitPosToFacing = Direction.NORTH;
                    break;
                }
            case WEST:
//                RedBrainsSurvivalMod.LOGGER.info("WEST");
                if ((hitPos.getY() - (double)blockPos.getY()) < (hitPos.getZ() - (double)blockPos.getZ()) && 1 - (hitPos.getY() - (double)blockPos.getY()) < (hitPos.getZ() - (double)blockPos.getZ())) {
                    hitPosToOrientation = Orientation.VERTICAL;
                    hitPosToFacing = Direction.EAST;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > (hitPos.getZ() - (double)blockPos.getZ()) && 1 - (hitPos.getY() - (double)blockPos.getY()) < (hitPos.getZ() - (double)blockPos.getZ())) {
                    hitPosToOrientation = Orientation.HORIZONTAL;
                    hitPosToFacing = Direction.EAST;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > (hitPos.getZ() - (double)blockPos.getZ()) && 1 - (hitPos.getY() - (double)blockPos.getY()) > (hitPos.getZ() - (double)blockPos.getZ())) {
                    hitPosToOrientation = Orientation.VERTICAL;
                    hitPosToFacing = Direction.NORTH;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < (hitPos.getZ() - (double)blockPos.getZ()) && 1 - (hitPos.getY() - (double)blockPos.getY()) > (hitPos.getZ() - (double)blockPos.getZ())) {
                    hitPosToOrientation = Orientation.HORIZONTAL;
                    hitPosToFacing = Direction.EAST;
                    break;
                }
            case NORTH:
//                RedBrainsSurvivalMod.LOGGER.info("NORTH");
                if ((hitPos.getY() - (double)blockPos.getY()) < (hitPos.getX() - (double)blockPos.getX()) && 1 - (hitPos.getY() - (double)blockPos.getY()) < (hitPos.getX() - (double)blockPos.getX())) {
                    hitPosToOrientation = Orientation.VERTICAL;
                    hitPosToFacing = Direction.EAST;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > (hitPos.getX() - (double)blockPos.getX()) && 1 - (hitPos.getY() - (double)blockPos.getY()) < (hitPos.getX() - (double)blockPos.getX())) {
                    hitPosToOrientation = Orientation.HORIZONTAL;
                    hitPosToFacing = Direction.SOUTH;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > (hitPos.getX() - (double)blockPos.getX()) && 1 - (hitPos.getY() - (double)blockPos.getY()) > (hitPos.getX() - (double)blockPos.getX())) {
                    hitPosToOrientation = Orientation.VERTICAL;
                    hitPosToFacing = Direction.SOUTH;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < (hitPos.getX() - (double)blockPos.getX()) && 1 - (hitPos.getY() - (double)blockPos.getY()) > (hitPos.getX() - (double)blockPos.getX())) {
                    hitPosToOrientation = Orientation.HORIZONTAL;
                    hitPosToFacing = Direction.SOUTH;
                    break;
                }
            case EAST:
//                RedBrainsSurvivalMod.LOGGER.info("EAST");
                if ((hitPos.getY() - (double)blockPos.getY()) < (hitPos.getZ() - (double)blockPos.getZ()) && 1 - (hitPos.getY() - (double)blockPos.getY()) < (hitPos.getZ() - (double)blockPos.getZ())) {
                    hitPosToOrientation = Orientation.VERTICAL;
                    hitPosToFacing = Direction.SOUTH;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > (hitPos.getZ() - (double)blockPos.getZ()) && 1 - (hitPos.getY() - (double)blockPos.getY()) < (hitPos.getZ() - (double)blockPos.getZ())) {
                    hitPosToOrientation = Orientation.HORIZONTAL;
                    hitPosToFacing = Direction.WEST;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > (hitPos.getZ() - (double)blockPos.getZ()) && 1 - (hitPos.getY() - (double)blockPos.getY()) > (hitPos.getZ() - (double)blockPos.getZ())) {
                    hitPosToOrientation = Orientation.VERTICAL;
                    hitPosToFacing = Direction.WEST;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < (hitPos.getZ() - (double)blockPos.getZ()) && 1 - (hitPos.getY() - (double)blockPos.getY()) > (hitPos.getZ() - (double)blockPos.getZ())) {
                    hitPosToOrientation = Orientation.HORIZONTAL;
                    hitPosToFacing = Direction.WEST;
                    break;
                }
            default:
                hitPosToOrientation = Orientation.HORIZONTAL;
                hitPosToFacing = Direction.SOUTH;
        }

        BlockHalf newHalf = direction != Direction.DOWN && (direction == Direction.UP || !(hitPos.y - (double)blockPos.getY() > 0.5D)) ? BlockHalf.BOTTOM : BlockHalf.TOP;
        return (BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(FACING, hitPosToFacing)).with(HALF, newHalf)).with(ORIENTATION, hitPosToOrientation)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
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
        if (state.get(ORIENTATION) == Orientation.HORIZONTAL) {
            return (mirror == BlockMirror.FRONT_BACK && facing.getAxis() == Direction.Axis.X) || (mirror == BlockMirror.LEFT_RIGHT && facing.getAxis() == Direction.Axis.Z) ? state.with(FACING, facing.getOpposite()) : state;
        } else {
            return (mirror == BlockMirror.FRONT_BACK && facing.getAxis() == Direction.Axis.X) || (mirror == BlockMirror.LEFT_RIGHT && facing.getAxis() == Direction.Axis.Z) ? state.with(FACING, facing.rotateYClockwise()) : state.with(FACING, facing.rotateYCounterclockwise());
        }
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        //TODO
        // can replace other sub blocks
        return false;
    }

    //-----------------------waterlogging stuff-----------------------

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

    static {
        FACING = HorizontalFacingBlock.FACING;
        HALF = Properties.BLOCK_HALF;
        ORIENTATION = EnumProperty.of("orientation", Orientation.class);
        WATERLOGGED = Properties.WATERLOGGED;

        BOTTOM_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        BOTTOM_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
        BOTTOM_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        BOTTOM_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
        TOP_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        TOP_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        TOP_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        TOP_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);

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
