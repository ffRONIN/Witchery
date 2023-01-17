
package net.r0nin_yt.witchery.common.transformation;


import net.minecraft.entity.player.PlayerEntity;
import net.r0nin_yt.witchery.api.registry.Transformation;

public class WerewolfTransformation extends Transformation {
	@Override
	public void onAdded(PlayerEntity player) {
//		WitcheryComponents.ADDITIONAL_WEREWOLF_DATA_COMPONENT.get(player).setVariant(player.getRandom().nextInt(WerewolfEntity.getVariantsStatic()));
	}
}
