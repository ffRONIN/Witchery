
package net.r0nin_yt.witchery.mixin;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.r0nin_yt.witchery.api.WitcheryAPI;
import net.r0nin_yt.witchery.common.block.WitcheryBlocks;
import net.r0nin_yt.witchery.common.misc.WitcheryUtil;
import net.r0nin_yt.witchery.common.registry.WitcheryMaterials;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Block.class)
public abstract class BlockMixin {
	@Inject(method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;", at = @At("RETURN"), cancellable = true)
	private static void getDroppedStacks(BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack stack, CallbackInfoReturnable<List<ItemStack>> callbackInfo) {
		if (entity instanceof LivingEntity living) {
	//		boolean damage = false;
	//		if (state.getBlock() instanceof SaltLineBlock && WitcheryAPI.isWeakToSilver(living)) {
	//			damage = true;
	//		} else if (state.getBlock() == WitcheryBlocks.GARLIC_CROP && WitcheryAPI.isVampire(entity, true)) {
	//			damage = true;
	//		} else if (state.getBlock() == WitcheryBlocks.ACONITE_CROP && WitcheryAPI.isWerewolf(entity, true)) {
	//			damage = true;
	//		}
	//		if (damage) {
	//			entity.damage(DamageSource.MAGIC, living.getMaxHealth() * 1 / 2f);
			}
			List<ItemStack> drops = callbackInfo.getReturnValue();
		LivingEntity living = null;
		if (!drops.isEmpty() && !EnchantmentHelper.get(stack).containsKey(Enchantments.SILK_TOUCH) && state.getBlock() instanceof CropBlock crop && state.get(crop.getAgeProperty()) == crop.getMaxAge() && WitcheryUtil.getArmorPieces(living, armorStack -> armorStack.getItem() instanceof ArmorItem armorItem && armorItem.getMaterial() == WitcheryMaterials.HEDGEWITCH_ARMOR) >= 3) {
				for (int i = 0; i < drops.size(); i++) {
					drops.set(i, new ItemStack(drops.get(i).getItem(), drops.get(i).getCount() + 1));
				}
			}
		}
	}

