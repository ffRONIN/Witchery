/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package net.r0nin_yt.witchery.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.minecraft.entity.player.PlayerEntity;

import static net.fabricmc.fabric.api.event.EventFactory.createArrayBacked;

public interface AllowVampireBurn {
	Event<AllowVampireBurn> EVENT = createArrayBacked(AllowVampireBurn.class, listeners -> player -> {
		for (AllowVampireBurn listener : listeners) {
			if (!listener.allowBurn(player)) {
				return false;
			}
		}
		return true;
	});

	boolean allowBurn(PlayerEntity player);
}
