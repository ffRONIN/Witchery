/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package net.r0nin_yt.witchery.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.minecraft.entity.LivingEntity;

import static net.fabricmc.fabric.api.event.EventFactory.createArrayBacked;

public final class BloodSetEvents {
	public static final Event<OnFillBlood> ON_BLOOD_FILL = createArrayBacked(OnFillBlood.class, listeners -> (entity, amount, simulate) -> {
		for (OnFillBlood listener : listeners) {
			listener.onFillBlood(entity, amount, simulate);
		}
	});

	public static final Event<OnDrainBlood> ON_BLOOD_DRAIN = createArrayBacked(OnDrainBlood.class, listeners -> (entity, amount, simulate) -> {
		for (OnDrainBlood listener : listeners) {
			listener.onDrainBlood(entity, amount, simulate);
		}
	});

	public static final Event<OnSetBlood> ON_BLOOD_SET = createArrayBacked(OnSetBlood.class, listeners -> (entity, amount) -> {
		for (OnSetBlood listener : listeners) {
			listener.onSetBlood(entity, amount);
		}
	});

	public interface OnFillBlood {
		void onFillBlood(LivingEntity entity, int amount, boolean simulate);
	}

	public interface OnDrainBlood {
		void onDrainBlood(LivingEntity entity, int amount, boolean simulate);
	}

	public interface OnSetBlood {
		void onSetBlood(LivingEntity entity, int amount);
	}
}