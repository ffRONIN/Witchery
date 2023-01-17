

package net.r0nin_yt.witchery.mixin;


import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeMaker;
import net.r0nin_yt.witchery.common.block.WitchCauldronBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LandPathNodeMaker.class)
public abstract class LandPathNodeMakerMixin extends PathNodeMaker {
	@Inject(method = "inflictsFireDamage", at = @At("RETURN"), cancellable = true)
	private static void inflictsFireDamage(BlockState blockState, CallbackInfoReturnable<Boolean> callbackInfo) {
		if (!callbackInfo.getReturnValueZ() && blockState.getBlock() instanceof WitchCauldronBlock) {
			callbackInfo.setReturnValue(true);
		}
	}
}
