

package net.r0nin_yt.witchery.mixin;

import net.minecraft.world.gen.feature.OrePlacedFeatures;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(OrePlacedFeatures.class)
public interface OrePlacedFeaturesAccessor {
	@Invoker
	static List<PlacementModifier> callModifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
		throw new UnsupportedOperationException();
	}

	@Invoker
	static List<PlacementModifier> callModifiersWithCount(int count, PlacementModifier heightModfier) {
		throw new UnsupportedOperationException();
	}
}
