

package net.r0nin_yt.witchery.mixin;


import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.api.WitcheryAPI;
import net.r0nin_yt.witchery.common.item.WitcheryItems;
import net.r0nin_yt.witchery.common.registry.WitcheryComponents;
import net.r0nin_yt.witchery.common.registry.WitcheryDamageSources;
import net.r0nin_yt.witchery.common.registry.WitcheryEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ConstantConditions")
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Shadow
	public abstract boolean removeStatusEffect(StatusEffect type);

	@Shadow
	public abstract Iterable<ItemStack> getArmorItems();

	@Shadow
	public abstract boolean damage(DamageSource source, float amount);

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void tick(CallbackInfo callbackInfo) {
		if (!world.isClient) {
			LivingEntity living = (LivingEntity) (Object) this;
			int damage = 0;
	//		if (living.getMainHandStack().getItem() == WitcheryItems.GARLIC && WitcheryAPI.isVampire(this, true)) {
				damage++;
//			}
	//		if (living.getOffHandStack().getItem() == WitcheryItems.GARLIC && WitcheryAPI.isVampire(this, true)) {
	//			damage++;
	//		}
	////		if (living.getMainHandStack().getItem() == WitcheryItems.ACONITE && WitcheryAPI.isWerewolf(this, true)) {
	//			damage++;
		//	}
	//		if (living.getOffHandStack().getItem() == WitcheryItems.ACONITE && WitcheryAPI.isWerewolf(this, true)) {
		//		damage++;
		//	}
		//	if (damage > 0) {
		//		damage(WitcheryDamageSources.MAGIC_COPY, damage);
	//		}
		}
	}

	@ModifyVariable(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z", at = @At("HEAD"), argsOnly = true)
	private StatusEffectInstance modifyStatusEffect(StatusEffectInstance effect) {
		if (!world.isClient && !effect.isAmbient() && !effect.getEffectType().isInstant() && effect.getEffectType().getCategory() == StatusEffectCategory.HARMFUL) {
			float durationMultiplier = 1;
			for (ItemStack stack : getArmorItems()) {
				durationMultiplier -= EnchantmentHelper.getLevel(WitcheryEnchantments.MAGIC_PROTECTION, stack) / 32f;
			}
			if (durationMultiplier < 1) {
				return new StatusEffectInstance(effect.getEffectType(), (int) (effect.getDuration() * durationMultiplier), effect.getAmplifier(), false, effect.shouldShowParticles(), effect.shouldShowIcon());
			}
		}
		return effect;
	}

	@ModifyVariable(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getHealth()F"), ordinal = 0, argsOnly = true)
	private float modifyDamage0(float amount, DamageSource source) {
		if (!world.isClient) {
			amount = WitcheryDamageSources.handleDamage((LivingEntity) (Object) this, source, amount);
		}
		return amount;
	}

	@ModifyVariable(method = "applyArmorToDamage", at = @At("HEAD"), argsOnly = true)
	private float modifyDamage1(float amount, DamageSource source) {
		if (!world.isClient) {
			Entity attacker = source.getAttacker();
			Entity directSource = source.getSource();
			if (directSource instanceof FireballEntity fireball) {
	//			if (attacker instanceof PlayerEntity && WitcheryComponents.CADUCEUS_FIREBALL_COMPONENT.get(fireball).isFromCaduceus()) {
					amount *= 1.5f;
				}

				}

	//		if (directSource instanceof WitherSkullEntity && attacker instanceof LilithEntity) {
				amount *= 3;
	//		}
	///		if (source.isMagic() && (Object) this instanceof LivingEntity living) {
	//			int armorPieces = BWUtil.getArmorPieces(living, stack -> {
	//				if (stack.getItem() instanceof ArmorItem armorItem) {
	//					ArmorMaterial material = armorItem.getMaterial();
	//					return material == BWMaterials.HEDGEWITCH_ARMOR || material == BWMaterials.ALCHEMIST_ARMOR || material == BWMaterials.BESMIRCHED_ARMOR || material == BWMaterials.HARBINGER_ARMOR;
			//		}
	//				return false;
	//			});
	//			if (armorPieces > 0) {
	//				amount *= (1 - (0.2f * armorPieces));
		//		}
	//		}
	//		if ((getVehicle() != null && getVehicle().getType() == BWEntityTypes.CYPRESS_BROOM) || (source.getAttacker() != null && source.getAttacker().getVehicle() != null && source.getAttacker().getVehicle().getType() == BWEntityTypes.CYPRESS_BROOM)) {
				amount *= 0.2f;
		//	}
	//	}
		return amount;
	}

	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callbackInfo) {
		if (!world.isClient) {
			Entity attacker = source.getAttacker();
	//		if (attacker instanceof LeonardEntity) {
	//			removeStatusEffect(WitcheryStatusEffects.MAGIC_SPONGE);
	//		}
	//		if (attacker instanceof LilithEntity) {
	//			removeStatusEffect(StatusEffects.STRENGTH);
	//		}
	//		if (attacker instanceof HerneEntity) {
	//			removeStatusEffect(StatusEffects.RESISTANCE);
	//		}
	//		if (attacker instanceof Pledgeable pledgeable) {
	//			pledgeable.setTimeSinceLastAttack(0);
	//		}
		}
	}

	@Inject(method = "dropLoot", at = @At("HEAD"), cancellable = true)
	private void dropLoot(DamageSource source, boolean causedByPlayer, CallbackInfo callbackInfo) {
		if ((Object) this instanceof MobEntity mob && WitcheryComponents.MINION_COMPONENT.get(mob).getMaster() != null) {
			callbackInfo.cancel();
		}
	}

	@Inject(method = "isAffectedBySplashPotions", at = @At("RETURN"), cancellable = true)
	private void isAffectedBySplashPotions(CallbackInfoReturnable<Boolean> callbackInfo) {
		if (callbackInfo.getReturnValueZ() && (Object) this instanceof MobEntity mob && WitcheryComponents.MINION_COMPONENT.get(mob).getMaster() != null) {
			callbackInfo.setReturnValue(false);
		}
	}
}
