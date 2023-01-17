

package net.r0nin_yt.witchery.mixin;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.SpawnHelper;
import net.r0nin_yt.witchery.common.world.WitcheryWorldState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnHelper.class)
public abstract class SpawnHelperMixin {
	@Inject(method = "isValidSpawn", at = @At("RETURN"), cancellable = true)
	private static void isValidSpawn(ServerWorld world, MobEntity entity, double squaredDistance, CallbackInfoReturnable<Boolean> callbackInfo) {
		if (callbackInfo.getReturnValueZ() && entity instanceof Monster) {
			WitcheryWorldState worldState = WitcheryWorldState.get(world);
			for (Long longPos : worldState.glowingBrambles) {
				if (new Box(entity.getBlockPos()).expand(16).intersects(new Box(BlockPos.fromLong(longPos)))) {
					callbackInfo.setReturnValue(false);
				}
			}
		}
	}
}
