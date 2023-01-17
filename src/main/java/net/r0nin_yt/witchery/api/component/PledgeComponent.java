
package net.r0nin_yt.witchery.api.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.r0nin_yt.witchery.common.registry.WitcheryPledges;

public class PledgeComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final PlayerEntity obj;
	private String pledge = WitcheryPledges.NONE, pledgeNextTick = "";

	public PledgeComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		String pledge = tag.getString("Pledge");
		if (pledge.isEmpty()) {
			pledge = WitcheryPledges.NONE;
		}
		setPledge(pledge);
		setPledgeNextTick(tag.getString("PledgeNextTick"));
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putString("Pledge", getPledge());
		tag.putString("PledgeNextTick", getPledgeNextTick());
	}

	@Override
	public void serverTick() {
		if (!getPledgeNextTick().isEmpty()) {
			setPledge(getPledgeNextTick());
			setPledgeNextTick("");
		}
	}

	public String getPledge() {
		return pledge;
	}

	//public void setPledge(String pledge) {
	//	this.pledge = pledge;
	//	WitcheryComponents.PLEDGE_COMPONENT.sync(obj);
	//	WitcheryComponents.TRANSFORMATION_COMPONENT.get(obj).updateAttributes();
//	}

	public String getPledgeNextTick() {
		return pledgeNextTick;
	}

	public void setPledgeNextTick(String pledgeNextTick) {
		this.pledgeNextTick = pledgeNextTick;
	}

	public void setPledge(String none) {

	}
}
