package org.cw.ebg.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.BlockView;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;

@Mixin(TransparentBlock.class)
public class TransparentBlockMixin extends TranslucentBlock {
	public TransparentBlockMixin(AbstractBlock.Settings settings) {
		super(settings);
	}

	// @TODO: put in config file, not here.
	@Unique private static final boolean DEBUG = false;
	@Unique private static final double MIN_VELOCITY = 0.115;

	@Unique
	// speed is magnitude of velocity, this function gets that
	public double getEntitySpeed(Entity entity) {
		Vec3d velocity = entity.getVelocity();
        return Math.sqrt(
				Math.pow(Math.abs(velocity.x), 2) +
				Math.pow(Math.abs(velocity.y), 2) +
				Math.pow(Math.abs(velocity.z), 2)
		);
	}

	@Unique
	public boolean isFallFlying(LivingEntity entity) {
		return entity.isFallFlying();
	}

	@Unique
	@Deprecated
	// @TODO: find a way of getting around doing this.
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);
	}

	@Override
	@Deprecated
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {

		if (!world.isClient
				&& entity.isLiving()
				&& this.isFallFlying((LivingEntity) entity)
		) {
			// @TODO: find better way of doing this, maybe?
			if (DEBUG) {
				entity.sendMessage(Text.literal(
						"[ebg] Your speed is " + this.getEntitySpeed(entity)
				));
			}
			// @TODO: get normal from which player is hitting block from,
			//        and if under certain threshold; break the block.
			if (this.getEntitySpeed(entity) >= MIN_VELOCITY) {
				world.breakBlock(pos, true);
			}
		}
		super.onEntityCollision(state, world, pos, entity);
	}
}
