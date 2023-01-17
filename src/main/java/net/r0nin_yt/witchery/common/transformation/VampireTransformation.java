

package net.r0nin_yt.witchery.common.transformation;


import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.Registry;
import net.r0nin_yt.witchery.api.registry.Transformation;

public class VampireTransformation extends Transformation {
	@Override
	public void onAdded(PlayerEntity player) {
		Registry.STATUS_EFFECT.stream().forEach(effect -> {
			StatusEffectInstance effectInstance = player.getStatusEffect(effect);
			if (effectInstance != null && !player.canHaveStatusEffect(effectInstance)) {
				player.removeStatusEffect(effect);
			}
		});
	}

	@Override
	public void onRemoved(PlayerEntity player) {
		player.removeStatusEffect(StatusEffects.NIGHT_VISION);
	}
}
