

package net.r0nin_yt.witchery.mixin.contract;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.common.registry.WitcheryComponents;
import net.r0nin_yt.witchery.common.registry.WitcheryContracts;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	@Shadow
	public abstract HungerManager getHungerManager();

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@ModifyVariable(method = "addExperience", at = @At("HEAD"), argsOnly = true)
	private int modifyAddExperience(int experience) {
		if (WitcheryComponents.CONTRACTS_COMPONENT.get(this).hasContract(WitcheryContracts.PRIDE)) {
			experience *= 2;
		}
		return experience;
	}

	@Inject(method = "eatFood", at = @At("HEAD"))
	private void eatFood(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> callbackInfo) {
		if (!world.isClient) {
			FoodComponent foodComponent = stack.getItem().getFoodComponent();
			if (foodComponent != null && WitcheryComponents.CONTRACTS_COMPONENT.get(this).hasContract(WitcheryContracts.GLUTTONY)) {
				getHungerManager().add(foodComponent.getHunger(), foodComponent.getSaturationModifier());
			}
		}
	}
}
