
package net.r0nin_yt.witchery.mixin.pledge;


import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.api.entity.Pledgeable;
import net.r0nin_yt.witchery.common.world.WitcheryUniversalWorldState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Shadow
	public World world;

	@Inject(method = "remove", at = @At("TAIL"))
	private void remove(CallbackInfo callbackInfo) {
		if (!world.isClient && this instanceof Pledgeable pledgeable) {
			WitcheryUniversalWorldState universalWorldState = WitcheryUniversalWorldState.get(world);
			universalWorldState.pledgesToRemove.addAll(pledgeable.getPledgedPlayerUUIDs());
			universalWorldState.markDirty();
		}
	}
}
