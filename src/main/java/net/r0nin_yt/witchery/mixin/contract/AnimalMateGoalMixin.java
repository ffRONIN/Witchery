

package net.r0nin_yt.witchery.mixin.contract;


import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.r0nin_yt.witchery.common.registry.WitcheryComponents;
import net.r0nin_yt.witchery.common.registry.WitcheryContracts;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimalMateGoal.class)
public abstract class AnimalMateGoalMixin extends Goal {
	@Shadow
	@Final
	protected AnimalEntity animal;

	@Shadow
	protected AnimalEntity mate;

	@Inject(method = "breed", at = @At("HEAD"))
	private void breed(CallbackInfo callbackInfo) {
		ServerPlayerEntity player = animal.getLovingPlayer();
		if (player != null && WitcheryComponents.CONTRACTS_COMPONENT.get(player).hasContract(WitcheryContracts.LUST)) {
			animal.breed(player.getWorld(), mate);
			animal.breed(player.getWorld(), mate);
		}
	}
}
