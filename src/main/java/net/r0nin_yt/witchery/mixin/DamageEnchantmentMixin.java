
package net.r0nin_yt.witchery.mixin;


import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.entity.EntityGroup;
import net.r0nin_yt.witchery.api.WitcheryAPI;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageEnchantment.class)
public abstract class DamageEnchantmentMixin {
	@Shadow
	@Final
	public int typeIndex;

	@Shadow
	public abstract float getAttackDamage(int level, EntityGroup group);

	@Inject(method = "getAttackDamage", at = @At("HEAD"), cancellable = true)
	private void getAttackDamage(int level, EntityGroup group, CallbackInfoReturnable<Float> callbackInfo) {
		if (typeIndex == 1 && group == WitcheryAPI.DEMON) {
			callbackInfo.setReturnValue(getAttackDamage(level, EntityGroup.UNDEAD));
		}
	}
}
