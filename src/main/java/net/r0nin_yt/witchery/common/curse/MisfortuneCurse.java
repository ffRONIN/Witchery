

package net.r0nin_yt.witchery.common.curse;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.r0nin_yt.witchery.api.registry.Curse;
import net.r0nin_yt.witchery.common.registry.WitcheryStatusEffects;

public class MisfortuneCurse extends Curse {
	public MisfortuneCurse(Type type) {
		super(type);
	}

	@Override
	public void tick(LivingEntity target) {
		if (target.age % 20 == 0 && target.getRandom().nextFloat() < 1 / 100f) {
			target.addStatusEffect(getEffect(target.getRandom().nextInt(8)));
		}
	}

	private static StatusEffectInstance getEffect(int value) {
		return new StatusEffectInstance(value == 1 ? StatusEffects.POISON : value == 2 ? StatusEffects.WEAKNESS : value == 3 ? StatusEffects.SLOWNESS : value == 4 ? StatusEffects.BLINDNESS : value == 5 ? StatusEffects.NAUSEA : value == 6 ? StatusEffects.MINING_FATIGUE : value == 7 ? WitcheryStatusEffects.CORROSION : WitcheryStatusEffects.SINKING, 400);
	}
}
