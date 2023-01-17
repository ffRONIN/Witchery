

package net.r0nin_yt.witchery.common.curse;


import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.biome.SpawnSettings;
import net.r0nin_yt.witchery.api.registry.Curse;
import net.r0nin_yt.witchery.common.misc.WitcheryUtil;
import net.r0nin_yt.witchery.common.registry.WitcheryTags;

import java.util.List;

public class InsanityCurse extends Curse {
	public InsanityCurse(Type type) {
		super(type);
	}

	@Override
	public void tick(LivingEntity target) {
		if (target.age % 20 == 0 && target.getRandom().nextFloat() < 1 / 100f) {
			List<SpawnSettings.SpawnEntry> entries = target.world.getBiome(target.getBlockPos()).value().getSpawnSettings().getSpawnEntries(SpawnGroup.MONSTER).getEntries();
			if (entries.isEmpty()) {
				return;
			}
			Entity entity = null;
			int tries = 0;
			while (tries < 16) {
				Entity potentialSpawn = entries.get(target.getRandom().nextInt(entries.size())).type.create(target.world);
				if (potentialSpawn != null && !potentialSpawn.getType().isIn(WitcheryTags.INSANITY_BLACKLIST)) {
					entity = potentialSpawn;
					break;
				}
				tries++;
			}
			if (entity instanceof MobEntity mob) {
				WitcheryUtil.attemptTeleport(entity, target.getBlockPos(), 24, false);
				mob.initialize((ServerWorldAccess) target.world, target.world.getLocalDifficulty(target.getBlockPos()), SpawnReason.EVENT, null, null);
	//			WitcheryComponents.FAKE_MOB_COMPONENT.get(mob).setTarget(target.getUuid());
				entity.setSilent(true);
				mob.setCanPickUpLoot(false);
				target.world.spawnEntity(entity);
			}
		}
	}
}
