

package net.r0nin_yt.witchery.common.entity;


import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.api.entity.BroomEntity;

public class JuniperBroomEntity extends BroomEntity {
	public JuniperBroomEntity(EntityType<?> type, World world) {
		super(type, world);
	}

	@Override
	protected float getSpeed() {
		return 1.5f;
	}
}
