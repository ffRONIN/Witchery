
package net.r0nin_yt.witchery.mixin;


import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.r0nin_yt.witchery.common.block.WitcheryBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
	@Shadow
	public abstract Block getBlock();

	@Inject(method = "getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
	private void getCollisionShape(BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> callbackInfo) {
		if (getBlock() != WitcheryBlocks.SALT_LINE) {
			BlockState down = world.getBlockState(pos.down());
			if (down != null && down.isOf(WitcheryBlocks.SALT_LINE) && down.getCollisionShape(world, pos, context) == VoxelShapes.fullCube()) {
				callbackInfo.setReturnValue(VoxelShapes.fullCube());
			}
		}
	}
}
