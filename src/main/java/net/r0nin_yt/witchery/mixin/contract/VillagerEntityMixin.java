

package net.r0nin_yt.witchery.mixin.contract;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.ItemScatterer;
import net.minecraft.village.TradeOffer;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.common.registry.WitcheryComponents;
import net.r0nin_yt.witchery.common.registry.WitcheryContracts;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {
	public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "onDeath", at = @At("HEAD"))
	private void onDeath(CallbackInfo callbackInfo) {
		if (!world.isClient && attackingPlayer != null) {
			if (WitcheryComponents.CONTRACTS_COMPONENT.get(attackingPlayer).hasContract(WitcheryContracts.ENVY) && !getOffers().isEmpty()) {
				for (TradeOffer offer : getOffers()) {
					if (!offer.isDisabled()) {
						ItemScatterer.spawn(world, getX() + 0.5, getY() + 0.5, getZ() + 0.5, offer.getSellItem());
					}
				}
			}
		}
	}
}
