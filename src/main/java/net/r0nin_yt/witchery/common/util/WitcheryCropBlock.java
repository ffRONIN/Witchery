
package net.r0nin_yt.witchery.common.util;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.api.WitcheryAPI;
import net.r0nin_yt.witchery.common.block.WitcheryBlocks;
import net.r0nin_yt.witchery.common.registry.WitcheryDamageSources;

public class WitcheryCropBlock extends CropBlock {
	private static final VoxelShape[] AGE_TO_SHAPE = {Block.createCuboidShape(0, 0, 0, 16, 2, 16), Block.createCuboidShape(0, 0, 0, 16, 4, 16), Block.createCuboidShape(0, 0, 0, 16, 6, 16), Block.createCuboidShape(0, 0, 0, 16, 8, 16), Block.createCuboidShape(0, 0, 0, 16, 10, 16), Block.createCuboidShape(0, 0, 0, 16, 12, 16), Block.createCuboidShape(0, 0, 0, 16, 14, 16), Block.createCuboidShape(0, 0, 0, 16, 16, 16)};

	public WitcheryCropBlock(Settings settings) {
		super(settings);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		int age = getAge(state);
		int modelAge = 0;
		if (this == WitcheryBlocks.ACONITE_CROP) {
			modelAge = age == 0 ? 1 : age == 1 ? 3 : age == 2 ? 5 : 7;
		}
		if (this == WitcheryBlocks.BELLADONNA_CROP) {
			modelAge = age == 0 ? 1 : age == 1 ? 3 : age == 2 ? 5 : 6;
		}
		if (this == WitcheryBlocks.GARLIC_CROP) {
			modelAge = age == 0 ? 2 : age == 1 ? 3 : age == 2 ? 5 : 6;
		}
		if (this == WitcheryBlocks.MANDRAKE_CROP) {
			modelAge = age == 0 ? 1 : age == 1 ? 2 : 3;
		}
		return AGE_TO_SHAPE[modelAge];
	}

	@Environment(EnvType.CLIENT)
	@Override
	protected ItemConvertible getSeedsItem() {
		return this == WitcheryBlocks.ACONITE_CROP ? WitcheryBlocks.ACONITE_SEEDS : this == WitcheryBlocks.BELLADONNA_CROP ? WitcheryBlocks.BELLADONNA_SEEDS : this == WitcheryBlocks.GARLIC_CROP ? WitcheryBlocks.GARLIC : WitcheryBlocks.MANDRAKE_SEEDS;
	}

	@Override
	public IntProperty getAgeProperty() {
		return Properties.AGE_3;
	}

	@Override
	public int getMaxAge() {
		return 3;
	}

	@Override
	protected int getGrowthAmount(World world) {
		return MathHelper.nextInt(world.random, 1, 2);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		super.onEntityCollision(state, world, pos, entity);
		if (entity instanceof LivingEntity livingEntity) {
			boolean damage = false;
			if (this == WitcheryBlocks.GARLIC_CROP && WitcheryAPI.isVampire((PlayerEntity) entity, true)) {
				damage = true;
			} else if (this == WitcheryBlocks.ACONITE_CROP && WitcheryAPI.isWerewolf(entity, true)) {
				damage = true;
			}
			if (damage) {
				entity.damage(WitcheryDamageSources.MAGIC_COPY, livingEntity.getMaxHealth() * 1 / 4f);
			}
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(getAgeProperty());
	}
}
