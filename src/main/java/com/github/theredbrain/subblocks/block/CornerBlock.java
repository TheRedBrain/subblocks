package com.github.theredbrain.subblocks.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;

public class CornerBlock extends AbstractSubBlock {
    public static DirectionProperty FACING;
    public static final EnumProperty<BlockHalf> HALF;
    protected static final VoxelShape BOTTOM_NORTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_NORTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_SOUTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_SOUTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape TOP_NORTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape TOP_NORTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape TOP_SOUTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape TOP_SOUTH_WEST_CORNER_SHAPE;

    public CornerBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.SOUTH).with(HALF, BlockHalf.BOTTOM).with(WATERLOGGED, false));
}

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{FACING, HALF});
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);
        BlockHalf half = state.get(HALF);

        return switch (facing) {
            case WEST -> half == BlockHalf.BOTTOM ? BOTTOM_NORTH_WEST_CORNER_SHAPE : TOP_NORTH_WEST_CORNER_SHAPE;
            case NORTH -> half == BlockHalf.BOTTOM ? BOTTOM_NORTH_EAST_CORNER_SHAPE : TOP_NORTH_EAST_CORNER_SHAPE;
            case EAST -> half == BlockHalf.BOTTOM ? BOTTOM_SOUTH_EAST_CORNER_SHAPE : TOP_SOUTH_EAST_CORNER_SHAPE;
            default -> half == BlockHalf.BOTTOM ? BOTTOM_SOUTH_WEST_CORNER_SHAPE : TOP_SOUTH_WEST_CORNER_SHAPE;
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

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        //TODO
        // can replace corners and mouldings of same base block
        return false;
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
        HALF = Properties.BLOCK_HALF;
        WATERLOGGED = Properties.WATERLOGGED;

        BOTTOM_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        BOTTOM_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
        BOTTOM_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        BOTTOM_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
        TOP_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        TOP_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        TOP_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        TOP_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);
    }
}
