
package net.r0nin_yt.witchery.common.curse;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.r0nin_yt.witchery.api.registry.Curse;
import net.r0nin_yt.witchery.common.registry.WitcheryComponents;

public class ApathyCurse extends Curse {
	public ApathyCurse(Type type) {
		super(type);
	}

	@Override
	public void tick(LivingEntity target) {
		if (target instanceof PlayerEntity player) {
			WitcheryComponents.MAGIC_COMPONENT.maybeGet(player).ifPresent(magicComponent -> {
				if (magicComponent.getMagic() > 0) {
					magicComponent.setMagic(0);
				}
			});
		}
	}
}
