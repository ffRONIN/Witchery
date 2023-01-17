
package net.r0nin_yt.witchery.mixin.curse;

;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.common.registry.WitcheryComponents;
import net.r0nin_yt.witchery.common.registry.WitcheryCurses;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(CropBlock.class)
public class CropBlockMixin {
	@Inject(method = "canGrow", at = @At("RETURN"), cancellable = true)
	private void canGrow(World world, Random random, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> callbackInfo) {
		if (callbackInfo.getReturnValueZ() && !world.isClient) {
			for (int i = 0; i < world.getEntitiesByClass(LivingEntity.class, new Box(pos).expand(16), living -> living.isAlive() && WitcheryComponents.CURSES_COMPONENT.get(living).hasCurse(WitcheryCurses.ARMY_OF_WORMS)).size(); i++) {
				if (random.nextFloat() < 2 / 3f) {
					callbackInfo.setReturnValue(false);
				}
			}
		}
	}

	@Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
	private void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo callbackInfo) {
		for (int i = 0; i < world.getEntitiesByClass(LivingEntity.class, new Box(pos).expand(16), living -> living.isAlive() && WitcheryComponents.CURSES_COMPONENT.get(living).hasCurse(WitcheryCurses.ARMY_OF_WORMS)).size(); i++) {
			if (random.nextFloat() < 2 / 3f) {
				callbackInfo.cancel();
			}
		}
	}
}
