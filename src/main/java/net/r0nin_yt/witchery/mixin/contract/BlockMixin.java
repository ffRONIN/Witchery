
package net.r0nin_yt.witchery.mixin.contract;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.r0nin_yt.witchery.common.registry.WitcheryComponents;
import net.r0nin_yt.witchery.common.registry.WitcheryContracts;
import net.r0nin_yt.witchery.common.registry.WitcheryTags;
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
		if (entity instanceof PlayerEntity player) {
			WitcheryComponents.CONTRACTS_COMPONENT.maybeGet(player).ifPresent(contractsComponent -> {
				List<ItemStack> drops = callbackInfo.getReturnValue();
				if (!drops.isEmpty() && !EnchantmentHelper.get(stack).containsKey(Enchantments.SILK_TOUCH)) {
					if (contractsComponent.hasContract(WitcheryContracts.GREED)) {
						for (int i = 0; i < drops.size(); i++) {
							if (state.isIn(WitcheryTags.ORES)) {
								for (Recipe<?> smeltingRecipe : world.getRecipeManager().listAllOfType(RecipeType.SMELTING)) {
									for (Ingredient ingredient : smeltingRecipe.getIngredients()) {
										if (ingredient.test(drops.get(i))) {
											drops.set(i, new ItemStack(smeltingRecipe.getOutput().getItem(), smeltingRecipe.getOutput().getCount() * drops.get(i).getCount()));
										}
									}
								}
								drops.set(i, new ItemStack(drops.get(i).getItem(), drops.get(i).getCount() * 2));
							}
						}
					}
				}
			});
		}
	}
}
