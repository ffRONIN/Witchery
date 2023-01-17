/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package net.r0nin_yt.witchery.common.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.r0nin_yt.witchery.Witchery;
import net.r0nin_yt.witchery.common.registry.WitcheryComponents;

public class PolymorphStatusEffect extends StatusEffect {
	public static final Identifier IMPERSONATE_IDENTIFIER = new Identifier(Witchery.MOD_ID, "polymorph");

	public PolymorphStatusEffect(StatusEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		if (entity instanceof ServerPlayerEntity player) {
			WitcheryComponents.POLYMORPH_COMPONENT.maybeGet(player).ifPresent(polymorphComponent -> {
				if (polymorphComponent.getUuid() != null) {
			//		Impersonator.get(player).impersonate(IMPERSONATE_IDENTIFIER, new GameProfile(polymorphComponent.getUuid(), polymorphComponent.getName()));
				}
			});
		}
	}
}
