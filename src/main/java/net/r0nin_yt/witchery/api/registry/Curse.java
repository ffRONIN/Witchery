/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package net.r0nin_yt.witchery.api.registry;

import net.minecraft.entity.LivingEntity;

public class Curse {
	public final Type type;

	public Curse(Type type) {
		this.type = type;
	}

	public void tick(LivingEntity target) {
	}

	public static class Instance {
		public final Curse curse;
		public int duration;

		public Instance(Curse curse, int duration) {
			this.curse = curse;
			this.duration = duration;
		}
	}

	public enum Type {
		LESSER, GREATER
	}
}
