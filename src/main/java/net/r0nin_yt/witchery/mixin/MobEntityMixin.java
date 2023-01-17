

package net.r0nin_yt.witchery.mixin;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.common.item.TaglockItem;
import net.r0nin_yt.witchery.common.misc.WitcheryUtil;
import net.r0nin_yt.witchery.common.registry.WitcheryComponents;
import net.r0nin_yt.witchery.common.registry.WitcheryTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {
	protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@ModifyVariable(method = "setTarget", at = @At("HEAD"), argsOnly = true)
	private LivingEntity modifyTarget(LivingEntity target) {
		if (!world.isClient && target != null) {
	//		if (target instanceof GhostEntity) {
	//			return null;
			}
			if (target instanceof MobEntity mob && getUuid().equals(WitcheryComponents.MINION_COMPONENT.get(mob).getMaster())) {
				return null;
			}
			if (isUndead() && !WitcheryUtil.getBlockPoses(target.getBlockPos(), 2, foundPos -> world.getBlockState(foundPos).isIn(WitcheryTags.UNDEAD_MASK)).isEmpty()) {
				return null;
			}

		return target;
	}

	@Inject(method = "interactWithItem", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/player/PlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void interactWithItem(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir, ItemStack heldStack) {
		if (heldStack.getItem() instanceof TaglockItem) {
			cir.setReturnValue(TaglockItem.useTaglock(player, this, hand, true, false));
		}
	}
}
