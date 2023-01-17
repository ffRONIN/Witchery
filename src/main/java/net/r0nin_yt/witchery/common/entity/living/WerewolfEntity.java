

package net.r0nin_yt.witchery.common.entity.living;

;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.r0nin_yt.witchery.api.WitcheryAPI;
import net.r0nin_yt.witchery.common.entity.living.util.WitcheryHostileEntity;
import net.r0nin_yt.witchery.common.misc.WitcheryUtil;
import net.r0nin_yt.witchery.common.registry.WitcheryComponents;
import net.r0nin_yt.witchery.common.registry.WitcherySoundEvents;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class WerewolfEntity extends WitcheryHostileEntity {
	public NbtCompound storedVillager;

	public WerewolfEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	//public static DefaultAttributeContainer.Builder createAttributes() {
	//	return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 15).add(EntityAttributes.GENERIC_ARMOR, 20).add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 8).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4).add(ReachEntityAttributes.ATTACK_RANGE, 1).add(StepHeightEntityAttributeMain.STEP_HEIGHT, 1);
	//}

	public static boolean canSpawn(EntityType<WerewolfEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
		return world.toServerWorld().isNight() && WitcheryAPI.getMoonPhase(world.toServerWorld().toServerWorld()) == 0 && HostileEntity.canSpawnInDark(type, world, spawnReason, pos, random);
	}

	public static Object createAttributes() {
		return null;
	}


	@Override
	public void tick() {
		super.tick();
		if (!world.isClient) {
			getArmorItems().forEach(stack -> dropStack(stack.split(1)));
			if (WitcheryUtil.isTool(getMainHandStack())) {
				dropStack(getMainHandStack().split(1));
			}
			if (WitcheryUtil.isTool(getOffHandStack())) {
				dropStack(getOffHandStack().split(1));
			}
			if (storedVillager != null && age % 20 == 0 && (world.isDay() || WitcheryAPI.getMoonPhase(world) != 0)) {
				VillagerEntity entity = EntityType.VILLAGER.create(world);
				if (entity != null) {
		//			PlayerLookup.tracking(this).forEach(trackingPlayer -> SpawnSmokeParticlesPacket.send(trackingPlayer, this));
					world.playSound(null, getX(), getY(), getZ(), WitcherySoundEvents.ENTITY_GENERIC_TRANSFORM, getSoundCategory(), getSoundVolume(), getSoundPitch());
					entity.readNbt(storedVillager);
					entity.updatePositionAndAngles(getX(), getY(), getZ(), random.nextFloat() * 360, 0);
					entity.setHealth(entity.getMaxHealth() * (getHealth() / getMaxHealth()));
					entity.setFireTicks(getFireTicks());
					entity.clearStatusEffects();
					getStatusEffects().forEach(entity::addStatusEffect);
					WitcheryComponents.CURSES_COMPONENT.maybeGet(entity).ifPresent(entityCursesComponent -> WitcheryComponents.CURSES_COMPONENT.maybeGet(this).ifPresent(thisCursesComponent -> {
						entityCursesComponent.getCurses().clear();
						thisCursesComponent.getCurses().forEach(entityCursesComponent::addCurse);
					}));
		//			WitcheryComponents.WEREWOLF_VILLAGER_COMPONENT.get(entity).setStoredWerewolf(writeNbt(new NbtCompound()));
					world.spawnEntity(entity);
					remove(RemovalReason.DISCARDED);
				}
			}
		}
	}

	@Override
	protected boolean hasShiny() {
		return true;
	}

	@Override
	public int getVariants() {
		return getVariantsStatic();
	}

	@Override
	public EntityGroup getGroup() {
		return WitcheryAPI.DEMON;
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return WitcherySoundEvents.ENTITY_WEREWOLF_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return WitcherySoundEvents.ENTITY_WEREWOLF_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return WitcherySoundEvents.ENTITY_WEREWOLF_DEATH;
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
		EntityData data = super.initialize(world, difficulty, spawnReason, entityData, entityTag);
		if (dataTracker.get(VARIANT) != 0) {
			RegistryEntry<Biome> biome = world.getBiome(getBlockPos());
			if (biome.isIn(ConventionalBiomeTags.FOREST)) {
				dataTracker.set(VARIANT, random.nextBoolean() ? 1 : 2);
			} else if (biome.isIn(ConventionalBiomeTags.TAIGA)) {
				dataTracker.set(VARIANT, random.nextBoolean() ? 3 : 4);
			}
			if (biome.isIn(ConventionalBiomeTags.ICY)) {
				dataTracker.set(VARIANT, random.nextBoolean() ? 5 : 6);
			} else {
				dataTracker.set(VARIANT, random.nextInt(getVariants() - 1) + 1);
			}
		}
		if (spawnReason == SpawnReason.NATURAL) {
			storedVillager = EntityType.VILLAGER.create((World) world).writeNbt(new NbtCompound());
		}
		return data;
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("StoredVillager")) {
			storedVillager = nbt.getCompound("StoredVillager");
		}
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		if (storedVillager != null) {
			nbt.put("StoredVillager", storedVillager);
		}
	}

	@Override
	protected void initGoals() {
		goalSelector.add(0, new SwimGoal(this));
		goalSelector.add(1, new MeleeAttackGoal(this, 1, true));
		goalSelector.add(2, new WanderAroundFarGoal(this, 1));
		goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8));
		goalSelector.add(3, new LookAroundGoal(this));
		targetSelector.add(0, new RevengeGoal(this));
		targetSelector.add(1, new ActiveTargetGoal<>(this, LivingEntity.class, 10, true, false, entity -> entity instanceof PlayerEntity || entity instanceof SheepEntity || entity instanceof MerchantEntity || entity.getGroup() == EntityGroup.ILLAGER));
	}

	public static int getVariantsStatic() {
		return 7;
	}
}
