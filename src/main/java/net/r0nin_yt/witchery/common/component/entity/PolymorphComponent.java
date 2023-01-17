
package net.r0nin_yt.witchery.common.component.entity;

import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.r0nin_yt.witchery.common.registry.WitcheryStatusEffects;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PolymorphComponent implements ServerTickingComponent {
	private final Entity obj;
	private UUID uuid;
	private String name;

	public PolymorphComponent(Entity obj) {
		this.obj = obj;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		if (tag.contains("UUID")) {
			uuid = tag.getUuid("UUID");
			name = tag.getString("Name");
		}
	}

	@Override
	public void writeToNbt(@NotNull NbtCompound tag) {
		if (getUuid() != null) {
			tag.putUuid("UUID", uuid);
			tag.putString("Name", name);
		}
	}

	@Override
	public void serverTick() {
		if (obj instanceof PlayerEntity player && getUuid() != null && !player.hasStatusEffect(WitcheryStatusEffects.POLYMORPH)) {
			setUuid(null);
			setName(null);
		//	Impersonator.get(player).stopImpersonation(PolymorphStatusEffect.IMPERSONATE_IDENTIFIER);
		}
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
