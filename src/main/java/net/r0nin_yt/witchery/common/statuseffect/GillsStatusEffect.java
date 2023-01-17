
package net.r0nin_yt.witchery.common.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class GillsStatusEffect extends StatusEffect {
	public GillsStatusEffect(StatusEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		boolean damage = false;
	//	if (!entity.isSubmergedInWater() && !entity.world.hasRain(entity.getBlockPos().up())) {
	//		entity.setAir(((LivingEntityAccessor) entity).bw_getNextAirUnderwater(entity.getAir() - ((LivingEntityAccessor) entity).bw_getNextAirOnLand(0)));
	//		if (entity.getAir() == -20) {
	//			damage = true;
	//		}
	//	} else if (entity.getAir() < entity.getMaxAir()) {
	//		entity.setAir(((LivingEntityAccessor) entity).bw_getNextAirOnLand(entity.getAir()));
	//		return;
	//	}
	//	if (!damage) {
	//		damage = entity.getAir() < -20;
		}
	//	if (damage) {
	//		entity.damage(DamageSource.GENERIC, 2);
	//		entity.setAir(0);
	//	}
//	}
}
