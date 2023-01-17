
package net.r0nin_yt.witchery.mixin.pledge;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.api.WitcheryAPI;
import net.r0nin_yt.witchery.api.entity.Pledgeable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@SuppressWarnings("ConstantConditions")
@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {
	protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@ModifyVariable(method = "setTarget", at = @At("HEAD"), argsOnly = true)
	private LivingEntity modifyTarget(LivingEntity target) {
		if (!world.isClient && target != null) {
			if (this instanceof Pledgeable pledgeable) {
				if (target instanceof PlayerEntity player && WitcheryAPI.isPledged(player, pledgeable.getPledgeID())) {
					WitcheryAPI.unpledge(player);
				}
				pledgeable.summonMinions((MobEntity) (Object) this);
			}
		}
		return target;
	}
}
