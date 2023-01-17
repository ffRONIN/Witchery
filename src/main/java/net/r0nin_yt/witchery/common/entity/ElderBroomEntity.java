

package net.r0nin_yt.witchery.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.api.entity.BroomEntity;
import net.r0nin_yt.witchery.common.misc.WitcheryUtil;

public class ElderBroomEntity extends BroomEntity {
	private BlockPos originalPos = null;
	private String originalWorld = null;

	public ElderBroomEntity(EntityType<?> type, World world) {
		super(type, world);
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("OriginalPos")) {
			originalPos = BlockPos.fromLong(nbt.getLong("OriginalPos"));
			originalWorld = nbt.getString("OriginalWorld");
		}
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		if (originalPos != null) {
			nbt.putLong("OriginalPos", originalPos.asLong());
			nbt.putString("OriginalWorld", originalWorld);
		}
	}

	@Override
	protected void addPassenger(Entity passenger) {
		super.addPassenger(passenger);
		originalPos = getBlockPos();
		originalWorld = world.getRegistryKey().toString();
	}

	@Override
	public Vec3d updatePassengerForDismount(LivingEntity passenger) {
		Vec3d value = super.updatePassengerForDismount(passenger);
		if (originalPos != null && world.getRegistryKey().toString().equals(originalWorld)) {
			double x = originalPos.getX() + 0.5;
			double y = originalPos.getY();
			double z = originalPos.getZ() + 0.5;
			value = new Vec3d(x, y, z);
			WitcheryUtil.teleport(this, x, y, z, false);
			originalPos = null;
			originalWorld = null;
		}
		return value;
	}
}
