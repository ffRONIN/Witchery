

package net.r0nin_yt.witchery.common.curse;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.r0nin_yt.witchery.api.registry.Curse;
import net.r0nin_yt.witchery.common.registry.WitcheryComponents;

public class SolarHatredCurse extends Curse {
	public SolarHatredCurse(Type type) {
		super(type);
	}

	@Override
	public void tick(LivingEntity target) {
		if (target instanceof PlayerEntity player && WitcheryComponents.RESPAWN_TIMER_COMPONENT.get(player).getRespawnTimer() > 0) {
			return;
		}
		if (target.age % 400 == 0 && target.world.isDay() && target.world.isSkyVisible(target.getBlockPos())) {
			target.setOnFireFor(8);
		}
	}
}
