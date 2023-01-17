
package net.r0nin_yt.witchery.common.component.entity;

import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.nbt.NbtCompound;

public class TeleportTimerComponent implements ServerTickingComponent {
	private int teleportTimer = 0;

	@Override
	public void readFromNbt(NbtCompound tag) {
		setTeleportTimer(tag.getInt("TeleportTimer"));
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putInt("TeleportTimer", getTeleportTimer());
	}

	@Override
	public void serverTick() {
		if (getTeleportTimer() > 0) {
			setTeleportTimer(getTeleportTimer() - 1);
		}
	}

	public int getTeleportTimer() {
		return teleportTimer;
	}

	public void setTeleportTimer(int teleportTimer) {
		this.teleportTimer = teleportTimer;
	}
}
