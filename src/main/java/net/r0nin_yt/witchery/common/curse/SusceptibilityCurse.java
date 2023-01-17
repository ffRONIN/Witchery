

package net.r0nin_yt.witchery.common.curse;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.r0nin_yt.witchery.api.registry.Curse;
import net.r0nin_yt.witchery.common.registry.WitcheryStatusEffects;

public class SusceptibilityCurse extends Curse {
	public SusceptibilityCurse(Type type) {
		super(type);
	}

	@Override
	public void tick(LivingEntity target) {
		target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 19, 1, true, false));
		target.addStatusEffect(new StatusEffectInstance(WitcheryStatusEffects.SINKING, 19, 0, true, false));
	}
}
