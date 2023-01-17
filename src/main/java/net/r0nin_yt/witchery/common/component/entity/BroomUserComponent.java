
package net.r0nin_yt.witchery.common.component.entity;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.r0nin_yt.witchery.common.registry.WitcheryComponents;

public class BroomUserComponent implements AutoSyncedComponent {
	private final PlayerEntity obj;
	private boolean pressingForward = false;

	public BroomUserComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		setPressingForward(tag.getBoolean("PressingForward"));
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putBoolean("PressingForward", isPressingForward());
	}

	public boolean isPressingForward() {
		return pressingForward;
	}

	public void setPressingForward(boolean pressingForward) {
		this.pressingForward = pressingForward;
		WitcheryComponents.BROOM_USER_COMPONENT.sync(obj);
	}
}
