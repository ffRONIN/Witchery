

package net.r0nin_yt.witchery.common.curse;


import net.minecraft.entity.LivingEntity;
import net.r0nin_yt.witchery.api.registry.Curse;

public class WeakLungsCurse extends Curse {
	public WeakLungsCurse(Type type) {
		super(type);
	}

	@Override
	public void tick(LivingEntity target) {
		if (target.getAir() > -15) {
			target.setAir(-15);
		}
	}
}
