

package net.r0nin_yt.witchery.mixin.brew;


import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.api.WitcheryAPI;
import net.r0nin_yt.witchery.common.registry.WitcheryComponents;
import net.r0nin_yt.witchery.common.registry.WitcheryDamageSources;
import net.r0nin_yt.witchery.common.registry.WitcheryStatusEffects;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ConstantConditions")
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Shadow
	@Nullable
	public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);

	@Shadow
	public abstract boolean hasStatusEffect(StatusEffect effect);

	@Shadow
	public abstract boolean removeStatusEffect(StatusEffect type);

	@Shadow
	public abstract void heal(float amount);

	@Shadow
	public int hurtTime;

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
	private DamageSource modifyDamage0(DamageSource source) {
		if (!world.isClient) {
			Entity attacker = source.getSource();
			if (attacker instanceof LivingEntity livingAttacker && livingAttacker.hasStatusEffect(WitcheryStatusEffects.ENCHANTED)) {
				return attacker instanceof PlayerEntity ? new WitcheryDamageSources.MagicPlayer(attacker) : new WitcheryDamageSources.MagicMob(attacker);
			}
		}
		return source;
	}

	@ModifyVariable(method = "applyArmorToDamage", at = @At("HEAD"), argsOnly = true)
	private float modifyDamage1(float amount, DamageSource source) {
		if (!world.isClient) {
			if (!source.isOutOfWorld() && (hasStatusEffect(WitcheryStatusEffects.ETHEREAL) || (source.getAttacker() instanceof LivingEntity livingAttacker && livingAttacker.hasStatusEffect(WitcheryStatusEffects.ETHEREAL)))) {
				return 0;
			}
			if (source.getSource() instanceof LivingEntity livingSource && livingSource.hasStatusEffect(WitcheryStatusEffects.ENCHANTED)) {
				amount /= 4;
				amount += livingSource.getStatusEffect(WitcheryStatusEffects.ENCHANTED).getAmplifier();
			}
			if (hasStatusEffect(WitcheryStatusEffects.MAGIC_SPONGE) && source.isMagic()) {
				float magicAmount = (0.3f + (0.1f * getStatusEffect(WitcheryStatusEffects.MAGIC_SPONGE).getAmplifier()));
				amount *= (1 - magicAmount);
				if ((Object) this instanceof PlayerEntity player) {
					WitcheryAPI.fillMagic(player, (int) (amount * magicAmount), false);
				}
			}
		}
		return amount;
	}

	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callbackInfo) {
		if (!world.isClient) {
			Entity directSource = source.getSource();
			if (hasStatusEffect(WitcheryStatusEffects.DEFLECTION) && directSource != null && directSource.getType().isIn(EntityTypeTags.ARROWS)) {
				int amplifier = getStatusEffect(WitcheryStatusEffects.DEFLECTION).getAmplifier() + 1;
				Vec3d velocity = directSource.getVelocity();
				directSource.setVelocity(velocity.getX() * 2 * amplifier, velocity.getY() * 2 * amplifier, velocity.getZ() * 2 * amplifier);
				callbackInfo.setReturnValue(false);
			} else if (amount > 0 && hurtTime == 0) {
				if (!hasStatusEffect(StatusEffects.STRENGTH) && !hasStatusEffect(StatusEffects.REGENERATION) && !hasStatusEffect(StatusEffects.RESISTANCE) && directSource instanceof LivingEntity livingSource && livingSource.hasStatusEffect(WitcheryStatusEffects.LEECHING)) {
					livingSource.heal(amount * (livingSource.getStatusEffect(WitcheryStatusEffects.LEECHING).getAmplifier() + 1) / 8);
				}
				if (directSource != null && hasStatusEffect(WitcheryStatusEffects.THORNS) && !(source instanceof EntityDamageSource entitySource && entitySource.isThorns())) {
					directSource.damage(DamageSource.thorns(directSource), 2 * (getStatusEffect(WitcheryStatusEffects.THORNS).getAmplifier() + 1));
				}
				if (hasStatusEffect(WitcheryStatusEffects.VOLATILITY) && !source.isExplosive()) {
					for (LivingEntity entity : world.getEntitiesByClass(LivingEntity.class, getBoundingBox().expand(3), foundEntity -> foundEntity.isAlive() && !foundEntity.getUuid().equals(getUuid()))) {
						entity.damage(DamageSource.explosion(((LivingEntity) (Object) this)), 4 * (getStatusEffect(WitcheryStatusEffects.VOLATILITY).getAmplifier() + 1));
					}
					world.playSound(null, getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1, 1);
			//		PlayerLookup.tracking(this).forEach(trackingPlayer -> SpawnExplosionParticlesPacket.send(trackingPlayer, this));
			//		if (((Object) this) instanceof PlayerEntity player) {
			//			SpawnExplosionParticlesPacket.send(player, this);
					}
					removeStatusEffect(WitcheryStatusEffects.VOLATILITY);
				}
			}
		}


	@Inject(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z", at = @At(value = "HEAD"))
	private void addStatusEffect(StatusEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir) {
		if (effect.getEffectType() == WitcheryStatusEffects.POLYMORPH && source instanceof AreaEffectCloudEntity) {
			WitcheryComponents.POLYMORPH_COMPONENT.maybeGet(source).ifPresent(sourcePolymorphComponent -> {
				if (sourcePolymorphComponent.getUuid() != null) {
					WitcheryComponents.POLYMORPH_COMPONENT.maybeGet(LivingEntity.class.cast(this)).ifPresent(entityPolymorphComponent -> {
						entityPolymorphComponent.setUuid(sourcePolymorphComponent.getUuid());
						entityPolymorphComponent.setName(sourcePolymorphComponent.getName());
					});
				}
			});
		}
	}

	@Inject(method = "canBreatheInWater", at = @At("RETURN"), cancellable = true)
	private void canBreatheInWater(CallbackInfoReturnable<Boolean> callbackInfo) {
		if (!callbackInfo.getReturnValueZ() && !world.isClient && hasStatusEffect(WitcheryStatusEffects.GILLS)) {
			callbackInfo.setReturnValue(true);
		}
	}

	@Inject(method = "isClimbing", at = @At("RETURN"), cancellable = true)
	private void isClimbing(CallbackInfoReturnable<Boolean> callbackInfo) {
		if (!callbackInfo.getReturnValueZ() && hasStatusEffect(WitcheryStatusEffects.CLIMBING) && horizontalCollision) {
			callbackInfo.setReturnValue(true);
		}
	}
}
