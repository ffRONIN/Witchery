

package net.r0nin_yt.witchery.api.event;


import net.fabricmc.fabric.api.event.Event;
import net.minecraft.entity.player.PlayerEntity;
import net.r0nin_yt.witchery.api.registry.Transformation;

import static net.fabricmc.fabric.api.event.EventFactory.createArrayBacked;

public interface OnTransformationSet {
	Event<OnTransformationSet> EVENT = createArrayBacked(OnTransformationSet.class, listeners -> (player, transformation) -> {
		for (OnTransformationSet listener : listeners) {
			listener.onTransformationSet(player, transformation);
		}
	});

	void onTransformationSet(PlayerEntity player, Transformation transformation);

}