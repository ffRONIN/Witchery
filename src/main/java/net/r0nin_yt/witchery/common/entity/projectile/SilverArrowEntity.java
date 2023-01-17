

package net.r0nin_yt.witchery.common.entity.projectile;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.common.item.WitcheryItems;
import net.r0nin_yt.witchery.common.registry.WitcheryEntityTypes;

public class SilverArrowEntity extends PersistentProjectileEntity {
	public SilverArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	public SilverArrowEntity(EntityType<? extends PersistentProjectileEntity> type, LivingEntity owner, World world) {
		super(type, owner, world);
	}

	public SilverArrowEntity(World world, double x, double y, double z) {
		super(WitcheryEntityTypes.SILVER_ARROW, x, y, z, world);
	}



	@Override
	protected ItemStack asItemStack() {
		return new ItemStack(WitcheryItems.SILVER_ARROW);
	}
}
