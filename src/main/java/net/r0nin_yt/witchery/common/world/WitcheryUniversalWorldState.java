

package net.r0nin_yt.witchery.common.world;


import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Pair;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.Witchery;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WitcheryUniversalWorldState extends PersistentState {
	public final List<UUID> pledgesToRemove = new ArrayList<>();
	public final List<Pair<UUID, NbtCompound>> familiars = new ArrayList<>();

	public static WitcheryUniversalWorldState readNbt(NbtCompound nbt) {
		WitcheryUniversalWorldState universalWorldState = new WitcheryUniversalWorldState();
		NbtList pledgesToRemoveList = nbt.getList("PledgesToRemove", NbtType.COMPOUND);
		for (int i = 0; i < pledgesToRemoveList.size(); i++) {
			universalWorldState.pledgesToRemove.add(pledgesToRemoveList.getCompound(i).getUuid("UUID"));
		}
		NbtList familiarsList = nbt.getList("Familiars", NbtType.COMPOUND);
		for (int i = 0; i < familiarsList.size(); i++) {
			NbtCompound familiarCompound = familiarsList.getCompound(i);
			universalWorldState.familiars.add(new Pair<>(familiarCompound.getUuid("Player"), familiarCompound.getCompound("Familiar")));
		}
		return universalWorldState;
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		NbtList pledgesToRemoveList = new NbtList();
		for (UUID uuid : this.pledgesToRemove) {
			NbtCompound pledgeCompound = new NbtCompound();
			pledgeCompound.putUuid("UUID", uuid);
			pledgesToRemoveList.add(pledgeCompound);
		}
		nbt.put("PledgesToRemove", pledgesToRemoveList);
		NbtList familiarsList = new NbtList();
		for (Pair<UUID, NbtCompound> pair : this.familiars) {
			NbtCompound familiarCompound = new NbtCompound();
			familiarCompound.putUuid("Player", pair.getLeft());
			familiarCompound.put("Familiar", pair.getRight());
			familiarsList.add(familiarCompound);
		}
		nbt.put("Familiars", familiarsList);
		return nbt;
	}

	@SuppressWarnings("ConstantConditions")
	public static WitcheryUniversalWorldState get(World world) {
		return world.getServer().getOverworld().getPersistentStateManager().getOrCreate(WitcheryUniversalWorldState::readNbt, WitcheryUniversalWorldState::new, Witchery.MOD_ID);
	}
}
