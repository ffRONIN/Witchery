
package net.r0nin_yt.witchery.common.misc;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.TradeOfferList;
import net.r0nin_yt.witchery.api.WitcheryAPI;
import net.r0nin_yt.witchery.api.entity.Pledgeable;
import net.r0nin_yt.witchery.common.registry.WitcheryComponents;
import net.r0nin_yt.witchery.common.registry.WitcheryCurses;
import net.r0nin_yt.witchery.common.registry.WitcheryMaterials;
import net.r0nin_yt.witchery.common.registry.WitcherySoundEvents;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class WitcheryUtil {
	public static final TradeOfferList EMPTY_TRADES = new TradeOfferList();

	public static Set<BlockPos> getBlockPoses(BlockPos origin, int radius, Predicate<BlockPos> provider) {
		Set<BlockPos> blockPoses = new HashSet<>();
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				for (int z = -radius; z <= radius; z++) {
					if (provider.test(mutable.set(origin.getX() + x, origin.getY() + y, origin.getZ() + z))) {
						blockPoses.add(mutable.toImmutable());
					}
				}
			}
		}
		return blockPoses;
	}

	public static BlockPos getClosestBlockPos(BlockPos origin, int radius, Predicate<BlockPos> provider) {
		BlockPos pos = null;
		for (BlockPos foundPos : getBlockPoses(origin, radius, provider)) {
			if (pos == null || foundPos.getSquaredDistance(origin) < pos.getSquaredDistance(origin)) {
				pos = foundPos;
			}
		}
		return pos;
	}

	public static ActiveTargetGoal<LivingEntity> createGenericPledgeableTargetGoal(MobEntity entity) {
		return new ActiveTargetGoal<>(entity, LivingEntity.class, 10, true, false, foundEntity -> {
			if (foundEntity instanceof ArmorStandEntity) {
				return false;
			}
			if (foundEntity instanceof PlayerEntity player) {
				if (WitcheryAPI.isPledged(player, ((Pledgeable) entity).getPledgeID())) {
					return false;
				}
			} else if (foundEntity.getGroup() == WitcheryAPI.DEMON) {
				return false;
			}
			return WitcheryUtil.getArmorPieces(foundEntity, stack -> stack.getItem() instanceof ArmorItem armorItem && armorItem.getMaterial() == WitcheryMaterials.BESMIRCHED_ARMOR) < 3;
		});
	}

	public static boolean isTool(ItemStack stack) {
		Item item = stack.getItem();
		return item instanceof ToolItem || item instanceof RangedWeaponItem || item instanceof ShieldItem || item instanceof TridentItem;
	}

	public static boolean rejectTrades(LivingEntity merchant) {
		return !merchant.world.getEntitiesByClass(PlayerEntity.class, new Box(merchant.getBlockPos()).expand(8), foundEntity -> merchant.canSee(foundEntity) && foundEntity.isAlive() && WitcheryComponents.CURSES_COMPONENT.get(foundEntity).hasCurse(WitcheryCurses.APATHY)).isEmpty();
	}

	public static int getArmorPieces(LivingEntity livingEntity, Predicate<ItemStack> predicate) {
		int amount = 0;
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (slot.getType() == EquipmentSlot.Type.ARMOR && predicate.test(livingEntity.getEquippedStack(slot))) {
				amount++;
			}
		}
		return amount;
	}

	public static void addItemToInventoryAndConsume(PlayerEntity player, Hand hand, ItemStack toAdd) {
		boolean shouldAdd = false;
		ItemStack stack = player.getStackInHand(hand);
		if (stack.getCount() == 1) {
			if (player.isCreative()) {
				shouldAdd = true;
			} else {
				player.setStackInHand(hand, toAdd);
			}
		} else {
			stack.decrement(1);
			shouldAdd = true;
		}
		if (shouldAdd) {
			if (!player.getInventory().insertStack(toAdd)) {
				player.dropItem(toAdd, false, true);
			}
		}
	}

	public static void attemptTeleport(Entity entity, BlockPos origin, int distance, boolean hasEffects) {
		for (int i = 0; i < 32; i++) {
			BlockPos.Mutable mutable = new BlockPos.Mutable(origin.getX() + MathHelper.nextDouble(entity.world.random, -distance, distance), origin.getY() + MathHelper.nextDouble(entity.world.random, -distance / 2f, distance / 2f), origin.getZ() + MathHelper.nextDouble(entity.world.random, -distance, distance));
			if (!entity.world.getBlockState(mutable).getMaterial().isSolid()) {
				while (mutable.getY() > 0 && !entity.world.getBlockState(mutable).getMaterial().isSolid()) {
					mutable.move(Direction.DOWN);
				}
				if (entity.world.getBlockState(mutable).getMaterial().blocksMovement()) {
					teleport(entity, mutable.getX() + 0.5, mutable.getY() + 0.5, mutable.getZ() + 0.5, hasEffects);
					break;
				}
			}
		}
	}

	public static void teleport(Entity entity, double x, double y, double z, boolean hasEffects) {
		if (hasEffects) {
			if (!entity.isSilent()) {
				entity.world.playSound(null, entity.getBlockPos(), WitcherySoundEvents.ENTITY_GENERIC_TELEPORT, entity.getSoundCategory(), 1, 1);
			}
	//		PlayerLookup.tracking(entity).forEach(trackingPlayer -> SpawnPortalParticlesPacket.send(trackingPlayer, entity));
			if (entity instanceof PlayerEntity player) {
	//			SpawnPortalParticlesPacket.send(player, entity);
			}
		}
		if (entity instanceof LivingEntity livingEntity) {
			if (entity instanceof PlayerEntity player) {
				player.wakeUp(true, false);
			} else {
				livingEntity.wakeUp();
			}
		}
		entity.teleport(x, y + 0.5, z);
		if (hasEffects) {
			if (!entity.isSilent()) {
				entity.world.playSound(null, entity.getBlockPos(), WitcherySoundEvents.ENTITY_GENERIC_TELEPORT, entity.getSoundCategory(), 1, 1);
			}
	//		PlayerLookup.tracking(entity).forEach(trackingPlayer -> SpawnPortalParticlesPacket.send(trackingPlayer, entity));
	//		if (entity instanceof PlayerEntity player) {
	//			SpawnPortalParticlesPacket.send(player, entity);
//			}
		}
	}
}
