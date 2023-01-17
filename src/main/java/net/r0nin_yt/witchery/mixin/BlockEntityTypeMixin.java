

package net.r0nin_yt.witchery.mixin;


import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.r0nin_yt.witchery.common.block.CoffinBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ConstantConditions")
@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {
	@Inject(method = "supports", at = @At("HEAD"), cancellable = true)
	private void supports(BlockState state, CallbackInfoReturnable<Boolean> callbackInfo) {
		if ((Object) this instanceof BedBlockEntity && state.getBlock() instanceof CoffinBlock) {
			callbackInfo.setReturnValue(true);
		}
	}
}
