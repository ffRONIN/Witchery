
package net.r0nin_yt.witchery.api;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.r0nin_yt.witchery.WitcheryConfig;
import net.r0nin_yt.witchery.api.component.PledgeComponent;
import net.r0nin_yt.witchery.api.item.PoppetItem;
import net.r0nin_yt.witchery.api.misc.PoppetData;
import net.r0nin_yt.witchery.api.registry.AltarMapEntry;
import net.r0nin_yt.witchery.common.block.entity.PoppetShelfBlockEntity;
import net.r0nin_yt.witchery.common.entity.projectile.SilverArrowEntity;
import net.r0nin_yt.witchery.common.item.AthameItem;
import net.r0nin_yt.witchery.common.item.TaglockItem;
import net.r0nin_yt.witchery.common.item.WitcheryItems;
import net.r0nin_yt.witchery.common.registry.*;
import net.r0nin_yt.witchery.common.world.WitcheryUniversalWorldState;
import net.r0nin_yt.witchery.common.world.WitcheryWorldState;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("ConstantConditions")
public class WitcheryAPI {
	public static final Set<AltarMapEntry> ALTAR_MAP_ENTRIES = new HashSet<>();

	@SuppressWarnings("InstantiationOfUtilityClass")
	public static final EntityGroup DEMON = new EntityGroup();

	public static ServerPlayerEntity fakePlayer = null;

	public static LivingEntity getTaglockOwner(World world, ItemStack taglock) {
		if (world instanceof ServerWorld && (taglock.getItem() instanceof TaglockItem || taglock.getItem() instanceof PoppetItem) && TaglockItem.hasTaglock(taglock)) {
			UUID ownerUUID = TaglockItem.getTaglockUUID(taglock);
			for (ServerWorld serverWorld : world.getServer().getWorlds()) {
				if (serverWorld.getEntity(ownerUUID) instanceof LivingEntity livingEntity && livingEntity.isAlive()) {
					return livingEntity;
				}
			}
		}
		return null;
	}

	public static PoppetData getPoppetFromInventory(World world, PoppetItem item, Entity owner, List<ItemStack> inventory) {
		if (inventory == null) {
			return PoppetData.EMPTY;
		}
		for (int i = 0; i < inventory.size(); i++) {
			ItemStack stack = inventory.get(i);
			if (WitcheryConfig.disabledPoppets.contains(Registry.ITEM.getId(stack.getItem()).toString())) {
				continue;
			}
			if (stack.getItem() == item && TaglockItem.hasTaglock(stack)) {
				UUID uuid = null;
				if (owner != null) {
					uuid = owner.getUuid();
				} else {
					LivingEntity taglockOwner = getTaglockOwner(world, stack);
					if (taglockOwner != null) {
						uuid = taglockOwner.getUuid();
					}
				}
				if (TaglockItem.getTaglockUUID(stack).equals(uuid)) {
					return new PoppetData(stack, null, i);
				}
			}
		}
		return PoppetData.EMPTY;
	}

	public static PoppetData getPoppet(World world, PoppetItem item, Entity owner) {
		if (world.isClient) {
			return PoppetData.EMPTY;
		}
		if (item.worksInShelf) {
			for (Map.Entry<Long, DefaultedList<ItemStack>> entry : WitcheryWorldState.get(world).poppetShelves.entrySet()) {
				PoppetData result = getPoppetFromInventory(world, item, owner, entry.getValue());
				if (result != PoppetData.EMPTY) {
					BlockPos pos = BlockPos.fromLong(entry.getKey());
					if (world.isChunkLoaded(pos) && world.getBlockEntity(pos) instanceof PoppetShelfBlockEntity poppetShelf) {
						poppetShelf.sync();
					}
					return new PoppetData(result.stack, entry.getKey(), result.index);
				}
			}
		}
		for (PlayerEntity player : ((ServerWorld) world).getPlayers()) {
			PoppetData result = getPoppetFromInventory(world, item, owner, Stream.concat(player.getInventory().main.stream(), player.getInventory().offHand.stream()).collect(Collectors.toList()));
			if (result != PoppetData.EMPTY) {
				return result;
			}
		}
		return PoppetData.EMPTY;
	}

//	public static ServerPlayerEntity getFakePlayer(World world) {
//		if (!world.isClient) {
//			if (fakePlayer == null) {
//				fakePlayer = new ServerPlayerEntity(world.getServer(), (ServerWorld) world, new GameProfile(UUID.randomUUID(), "FAKE_PLAYER"), null);
////				fakePlayer.networkHandler = new ServerPlayNetworkHandler(world.getServer(), new ClientConnection(NetworkSide.SERVERBOUND), fakePlayer);
//				ItemStack axe = new ItemStack(Items.WOODEN_AXE);
//				axe.getOrCreateNbt().putBoolean("Unbreakable", true);
//				fakePlayer.setStackInHand(Hand.MAIN_HAND, axe);
//			}
//			return fakePlayer;
//		return null;
//	}

	public static LivingEntity getTransformedPlayerEntity(PlayerEntity player) {
		if (WitcheryAPI.isVampire(player, false)) {
			BatEntity entity = EntityType.BAT.create(player.world);
			entity.setRoosting(false);
			return entity;
//		} else if (WitcheryAPI.isWerewolf(player, false)) {
		//	WerewolfEntity entity = WitcheryEntityTypes.WEREWOLF.create(player.world);
	//		entity.getDataTracker().set(WitcheryHostileEntity.VARIANT, WitcheryComponents.ADDITIONAL_WEREWOLF_DATA_COMPONENT.get(player).getVariant());
		//	return entity;
		}
		return null;
	}

	public static EntityType<?> getFamiliar(PlayerEntity player) {
		if (!player.world.isClient) {
			WitcheryUniversalWorldState universalWorldState = WitcheryUniversalWorldState.get(player.world);
			for (Pair<UUID, NbtCompound> pair : universalWorldState.familiars) {
				if (player.getUuid().equals(pair.getLeft())) {
					return Registry.ENTITY_TYPE.get(new Identifier(pair.getRight().getString("id")));
				}
			}
		}
		return null;
	}

	public static boolean fillMagic(PlayerEntity player, int amount, boolean simulate) {
		if (player.world.isClient) {
			return false;
		}
		return WitcheryComponents.MAGIC_COMPONENT.get(player).fillMagic(amount, simulate);
	}

	public static boolean drainMagic(PlayerEntity player, int amount, boolean simulate) {
		if (player.world.isClient) {
			return false;
		}
		if (player.isCreative()) {
			return true;
		}
		if (player.hasStatusEffect(WitcheryStatusEffects.INHIBITED)) {
			return false;
		}
		return WitcheryComponents.MAGIC_COMPONENT.get(player).drainMagic(amount, simulate);
	}

//	public static boolean isVampire(Entity entity, boolean includeHumanForm) {
//		if (entity instanceof PlayerEntity player) {
//			TransformationComponent transformationComponent = BWComponents.TRANSFORMATION_COMPONENT.get(player);
//			if (transformationComponent.getTransformation() == BWTransformations.VAMPIRE) {
//				return includeHumanForm || transformationComponent.isAlternateForm();
//			}
//		}
//		return entity instanceof VampireEntity;
//	}

//	public static boolean isWerewolf(Entity entity, boolean includeHumanForm) {
/////		if (entity instanceof PlayerEntity player) {
		//	TransformationComponent transformationComponent = BWComponents.TRANSFORMATION_COMPONENT.get(player);
	//		if (transformationComponent.getTransformation() == BWTransformations.WEREWOLF) {
//				return includeHumanForm || transformationComponent.isAlternateForm();
//			}
//		}
//		return entity instanceof WerewolfEntity;
//	}

	public static boolean isSourceFromSilver(DamageSource source) {
		if (source.getSource() instanceof LivingEntity livingEntity && livingEntity.getMainHandStack().getItem() instanceof AthameItem) {
			return true;
		}
		return source.getSource() instanceof SilverArrowEntity;
	}

	public static boolean isWeakToSilver(LivingEntity livingEntity) {
		if (livingEntity.getType().isIn(WitcheryTags.IMMUNE_TO_SILVER)) {
			return false;
		}
		return livingEntity.isUndead() || livingEntity.getGroup() == DEMON || livingEntity.getType().isIn(WitcheryTags.VULNERABLE_TO_SILVER);
	}

	public static boolean isPledged(PlayerEntity player, String pledge) {
		PledgeComponent pledgeComponent = WitcheryComponents.PLEDGE_COMPONENT.get(player);
		if (!player.world.isClient) {
			WitcheryUniversalWorldState universalWorldState = WitcheryUniversalWorldState.get(player.world);
			for (int i = universalWorldState.pledgesToRemove.size() - 1; i >= 0; i--) {
				if (universalWorldState.pledgesToRemove.get(i).equals(player.getUuid())) {
					pledgeComponent.setPledgeNextTick(WitcheryPledges.NONE);
					universalWorldState.pledgesToRemove.remove(i);
					return false;
				}
			}
		}
		return pledgeComponent.getPledge().equals(pledge);
	}

	public static boolean hasVoodooProtection(LivingEntity living, int damage) {
		PoppetData poppetData = WitcheryAPI.getPoppet(living.world, (PoppetItem) WitcheryItems.VOODOO_PROTECTION_POPPET, living);
		if (!poppetData.stack.isEmpty()) {
			boolean sync = false;
			if (poppetData.stack.damage(damage, living.getRandom(), null) && poppetData.stack.getDamage() >= poppetData.stack.getMaxDamage()) {
				poppetData.stack.decrement(1);
				sync = true;
			}
			poppetData.update(living.world, sync);
			return true;
		}
		return false;
	}

	public static void unpledge(PlayerEntity player) {
		WitcheryComponents.PLEDGE_COMPONENT.get(player).setPledge(WitcheryPledges.NONE);
	}

	public static int getMoonPhase(WorldAccess world) {
		return world.getDimension().getMoonPhase(world.getLunarTime());
	}

	public static void registerAltarMapEntries(Block[]... altarArray) {
		for (Block[] altars : altarArray) {
			ALTAR_MAP_ENTRIES.add(new AltarMapEntry(altars[0], altars[1], Blocks.MOSS_CARPET.asItem()));
		}
		for (int i = 0; i < DyeColor.values().length; i++) {
			Item carpet = switch (DyeColor.byId(i)) {
				case WHITE -> Items.WHITE_CARPET;
				case ORANGE -> Items.ORANGE_CARPET;
				case MAGENTA -> Items.MAGENTA_CARPET;
				case LIGHT_BLUE -> Items.LIGHT_BLUE_CARPET;
				case YELLOW -> Items.YELLOW_CARPET;
				case LIME -> Items.LIME_CARPET;
				case PINK -> Items.PINK_CARPET;
				case GRAY -> Items.GRAY_CARPET;
				case LIGHT_GRAY -> Items.LIGHT_GRAY_CARPET;
				case CYAN -> Items.CYAN_CARPET;
				case PURPLE -> Items.PURPLE_CARPET;
				case BLUE -> Items.BLUE_CARPET;
				case BROWN -> Items.BROWN_CARPET;
				case GREEN -> Items.GREEN_CARPET;
				case RED -> Items.RED_CARPET;
				case BLACK -> Items.BLACK_CARPET;
			};
			for (Block[] altars : altarArray) {
				ALTAR_MAP_ENTRIES.add(new AltarMapEntry(altars[0], altars[i + 2], carpet));
			}
		}
		for (Block[] altars : altarArray) {
	//		ALTAR_MAP_ENTRIES.add(new AltarMapEntry(altars[0], altars[18], WitcheryBlocks.HEDGEWITCH_CARPET.asItem()));
	//		ALTAR_MAP_ENTRIES.add(new AltarMapEntry(altars[0], altars[19], WitcheryBlocks.ALCHEMIST_CARPET.asItem()));
	//		ALTAR_MAP_ENTRIES.add(new AltarMapEntry(altars[0], altars[20], WitcheryBlocks.BESMIRCHED_CARPET.asItem()));
		}
	}

	public static boolean isVampire(PlayerEntity player, boolean b) {
		return false;
	}

	public static boolean isWerewolf(Entity entity, boolean b) {
		return false;
	}

	public static boolean isVampire(net.r0nin_yt.witchery.mixin.pledge.LivingEntityMixin livingEntityMixin, boolean b) {
		return false;
	}
}
