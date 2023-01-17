

package net.r0nin_yt.witchery.mixin.curse;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.r0nin_yt.witchery.common.registry.WitcheryComponents;
import net.r0nin_yt.witchery.common.registry.WitcheryCurses;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(ActiveTargetGoal.class)
public abstract class ActiveTargetGoalMixin<T extends LivingEntity> {
	@Shadow
	protected TargetPredicate targetPredicate;

	@Inject(method = "<init>(Lnet/minecraft/entity/mob/MobEntity;Ljava/lang/Class;IZZLjava/util/function/Predicate;)V", at = @At("TAIL"))
	private void init(MobEntity mob, Class<T> targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, @Nullable Predicate<LivingEntity> predicate, CallbackInfo callbackInfo) {
		if (predicate != null) {
			targetPredicate.setPredicate(predicate.or(livingEntity -> WitcheryComponents.CURSES_COMPONENT.get(livingEntity).hasCurse(WitcheryCurses.OUTRAGE)));
		}
	}
}
