

package net.r0nin_yt.witchery.api.component;

import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.r0nin_yt.witchery.api.registry.Fortune;
import net.r0nin_yt.witchery.common.registry.WitcheryRegistries;

public class FortuneComponent implements ServerTickingComponent {
	private final PlayerEntity obj;
	private Fortune.Instance fortune = null;

	public FortuneComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		if (tag.contains("Fortune")) {
			setFortune(new Fortune.Instance(WitcheryRegistries.FORTUNES.get(new Identifier(tag.getString("Fortune"))), tag.getInt("FortuneDuration")));
		}
	}

	@SuppressWarnings({"ConstantConditions", "NullableProblems"})
	@Override
	public void writeToNbt(NbtCompound tag) {
		if (getFortune() != null) {
			tag.putString("Fortune", WitcheryRegistries.FORTUNES.getId(getFortune().fortune).toString());
			tag.putInt("FortuneDuration", getFortune().duration);
		}
	}

	@Override
	public void serverTick() {
		if (getFortune() != null) {
			if (getFortune().fortune.tick((ServerWorld) obj.world, obj)) {
				getFortune().duration = 0;
			} else {
				getFortune().duration--;
			}
			if (getFortune().duration <= 0) {
				if (getFortune().fortune.finish((ServerWorld) obj.world, obj)) {
					setFortune(null);
				} else {
					getFortune().duration = obj.getRandom().nextInt(120000);
				}
			}
		}
	}

	public Fortune.Instance getFortune() {
		return fortune;
	}

	public void setFortune(Fortune.Instance fortune) {
		this.fortune = fortune;
	}
}
