

package net.r0nin_yt.witchery.common.component.entity;

import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.r0nin_yt.witchery.common.registry.WitcheryComponents;

import java.util.UUID;

public class MinionComponent implements ServerTickingComponent {
	private final MobEntity obj;
	private UUID master = null;

	public MinionComponent(MobEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		setMaster(tag.getString("MasterUUID").isEmpty() ? null : UUID.fromString(tag.getString("MasterUUID")));
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putString("MasterUUID", getMaster() == null ? "" : getMaster().toString());
	}

	@Override
	public void serverTick() {
		if (getMaster() != null) {
			Entity master = ((ServerWorld) obj.world).getEntity(getMaster());
			if (master instanceof MobEntity mob && !mob.isDead() && mob.getTarget() != null) {
				obj.setTarget(mob.getTarget());
			} else {
	//			PlayerLookup.tracking(obj).forEach(trackingPlayer -> SpawnSmokeParticlesPacket.send(trackingPlayer, obj));
	//			obj.remove(Entity.RemovalReason.DISCARDED);
			}
		}
	}

	public UUID getMaster() {
		return master;
	}

	public void setMaster(UUID master) {
		this.master = master;
		WitcheryComponents.MINION_COMPONENT.sync(obj);
	}
}
