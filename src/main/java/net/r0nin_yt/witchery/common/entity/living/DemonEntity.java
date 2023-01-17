/*

package net.r0nin_yt.witchery.entity.living;


import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.api.WitcheryAPI;
import net.r0nin_yt.witchery.entity.DemonMerchant;
import net.r0nin_yt.witchery.entity.living.util.WitcheryHostileEntity;
import net.r0nin_yt.witchery.misc.WitcheryUtil;
import net.r0nin_yt.witchery.registry.WitcherySoundEvents;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class DemonEntity extends WitcheryHostileEntity implements DemonMerchant {
	public static final TrackedData<Boolean> MALE = DataTracker.registerData(DemonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	private final List<DemonTradeOffer> offers = new ArrayList<>();
	private PlayerEntity customer = null;
	private int tradeResetTimer = 0;

	public DemonEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0);
		setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0);
		experiencePoints = 20;
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 200).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6).add(EntityAttributes.GENERIC_ARMOR, 4).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.75);
	}

	@Override
	public void tick() {
		super.tick();
		if (!world.isClient) {
			tradeResetTimer++;
			if (tradeResetTimer >= 168000) {
				tradeResetTimer = 0;
				offers.clear();
			}
			LivingEntity target = getTarget();
			if (target != null) {
				lookAtEntity(target, 360, 360);
				if ((age + getId()) % 40 == 0) {
					SmallFireballEntity fireball = new SmallFireballEntity(world, this, target.getX() - getX(), target.getBodyY(0.5) - getBodyY(0.5), target.getZ() - getZ());
					fireball.updatePosition(fireball.getX(), getBodyY(0.5), fireball.getZ());
					world.playSound(null, getBlockPos(), WitcherySoundEvents.ENTITY_GENERIC_SHOOT, getSoundCategory(), getSoundVolume(), getSoundPitch());
					world.spawnEntity(fireball);
					swingHand(Hand.MAIN_HAND);
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
		return 5;
	}

	@Override
	public EntityGroup getGroup() {
		return WitcheryAPI.DEMON;
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() { return WitcherySoundEvents.ENTITY_DEMON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return WitcherySoundEvents.ENTITY_DEMON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return WitcherySoundEvents.ENTITY_DEMON_DEATH;
	}

	@Override
	protected ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (!world.isClient && isAlive() && getTarget() == null) {
			if (WitcheryUtil.rejectTrades(this)) {
				return ActionResult.FAIL;
			}
			if (getCurrentCustomer() == null) {
				setCurrentCustomer(player);
			}
			if (!getOffers().isEmpty()) {
				SyncContractsPacket.send(player);
				player.openHandledScreen(new SimpleNamedScreenHandlerFactory((id, playerInventory, customer) -> new DemonScreenHandler(id, this), getDisplayName())).ifPresent(syncId -> SyncDemonTradesPacket.send(player, this, syncId));
			} else {
				setCurrentCustomer(null);
			}
		}
		return ActionResult.success(world.isClient);
	}

	@Override
	public boolean canBeLeashedBy(PlayerEntity player) {
		return false;
	}

	@Override
	public boolean cannotDespawn() {
		return true;
	}

	@Override
	public boolean tryAttack(Entity target) {
		boolean flag = super.tryAttack(target);
		if (flag && target instanceof LivingEntity) {
			target.setOnFireFor(6);
			target.addVelocity(0, 0.2, 0);
			swingHand(Hand.MAIN_HAND);
		}
		return flag;
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
		dataTracker.set(MALE, random.nextBoolean());
		return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		dataTracker.set(MALE, nbt.getBoolean("Male"));
		if (nbt.contains("Offers")) {
			offers.clear();
			NbtList offersList = nbt.getList("Offers", NbtType.COMPOUND);
			for (NbtElement offerTag : offersList) {
				offers.add(new DemonTradeOffer((NbtCompound) offerTag));
			}
		}
		tradeResetTimer = nbt.getInt("TradeResetTimer");
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putBoolean("Male", dataTracker.get(MALE));
		if (!offers.isEmpty()) {
			NbtList offersList = new NbtList();
			for (DemonTradeOffer offer : offers) {
				offersList.add(offer.toTag());
			}
			nbt.put("Offers", offersList);
		}
		nbt.putInt("TradeResetTimer", tradeResetTimer);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		dataTracker.startTracking(MALE, false);
	}

	@Override
	protected void initGoals() {
		goalSelector.add(0, new SwimGoal(this));
		goalSelector.add(1, new LookAtCustomerGoal<>(this));
		goalSelector.add(2, new MeleeAttackGoal(this, 1, true));
		goalSelector.add(3, new WanderAroundFarGoal(this, 1));
		goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8));
		goalSelector.add(5, new LookAroundGoal(this));
		targetSelector.add(0, new RevengeGoal(this));
		targetSelector.add(1, new ActiveTargetGoal<>(this, LivingEntity.class, 10, true, false, entity -> !(entity instanceof ArmorStandEntity) && BWUtil.getArmorPieces(entity, stack -> stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getMaterial() == BWMaterials.BESMIRCHED_ARMOR) < 3 && (entity.getGroup() != BewitchmentAPI.DEMON || entity instanceof PlayerEntity)));
	}

	@Override
	public void onDeath(DamageSource source) {
		super.onDeath(source);
		setCurrentCustomer(null);
	}

	@Override
	public void setTarget(@Nullable LivingEntity target) {
		super.setTarget(target);
		if (target != null) {
			setCurrentCustomer(null);
		}
	}

	@Override
	public List<DemonTradeOffer> getOffers() {
		if (offers.isEmpty()) {
			List<Contract> availableContracts = BWRegistries.CONTRACTS.stream().collect(Collectors.toList());
			for (int i = 0; i < 3; i++) {
				Contract contract = availableContracts.get(random.nextInt(availableContracts.size()));
				offers.add(new DemonTradeOffer(contract, 168000, MathHelper.nextInt(random, 3, 6)));
				availableContracts.remove(contract);
			}
		}
		return offers;
	}

	@Override
	public LivingEntity getDemonTrader() {
		return this;
	}

	@Override
	public void onSell(DemonTradeOffer offer) {
		if (!world.isClient) {
			world.playSound(null, getBlockPos(), BWSoundEvents.ITEM_CONTRACT_USE, getSoundCategory(), getSoundVolume(), getSoundPitch());
			world.playSound(null, getBlockPos(), getAmbientSound(), getSoundCategory(), getSoundVolume(), getSoundPitch());
		}
	}

	@Override
	public void setCurrentCustomer(PlayerEntity customer) {
		this.customer = customer;
	}

	@Override
	public @Nullable PlayerEntity getCurrentCustomer() {
		return customer;
	}

	@SuppressWarnings("ConstantConditions")
	public static class DemonTradeOffer {
		private final Contract contract;
		private final int duration, cost;

		public DemonTradeOffer(NbtCompound tag) {
			this(BWRegistries.CONTRACTS.get(new Identifier(tag.getString("Contract"))), tag.getInt("Duration"), tag.getInt("Cost"));
		}

		public DemonTradeOffer(Contract contract, int duration, int cost) {
			this.contract = contract;
			this.duration = duration;
			this.cost = cost;
		}

		public NbtCompound toTag() {
			NbtCompound tag = new NbtCompound();
			tag.putString("Contract", BWRegistries.CONTRACTS.getId(contract).toString());
			tag.putInt("Duration", duration);
			tag.putInt("Cost", cost);
			return tag;
		}

		public static void toPacket(List<DemonTradeOffer> offers, PacketByteBuf buf) {
			buf.writeInt(offers.size());
			for (DemonTradeOffer offer : offers) {
				buf.writeIdentifier(BWRegistries.CONTRACTS.getId(offer.getContract()));
				buf.writeInt(offer.duration);
				buf.writeInt(offer.cost);
			}
		}

		public static List<DemonTradeOffer> fromPacket(PacketByteBuf buf) {
			int count = buf.readInt();
			List<DemonTradeOffer> offers = new ArrayList<>(count);
			for (int i = 0; i < count; i++) {
				offers.add(new DemonTradeOffer(BWRegistries.CONTRACTS.get(buf.readIdentifier()), buf.readInt(), buf.readInt()));
			}
			return offers;
		}

		public void apply(DemonMerchant merchant) {
			if (merchant.getCurrentCustomer() != null) {
				BWComponents.CONTRACTS_COMPONENT.get(merchant.getCurrentCustomer()).addContract(new Contract.Instance(contract, getDuration(), getCost(merchant)));
			}
		}

		public Contract getContract() {
			return contract;
		}

		public int getDuration() {
			return duration;
		}

		public int getCost(DemonMerchant merchant) {
			return merchant.isDiscount() ? 1 : cost;
		}
	}

	public static class LookAtCustomerGoal<T extends MobEntity & DemonMerchant> extends LookAtEntityGoal {
		private final T merchant;

		public LookAtCustomerGoal(T merchant) {
			super(merchant, PlayerEntity.class, 8);
			this.merchant = merchant;
			setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		public boolean canStart() {
			if (this.merchant.getCurrentCustomer() != null) {
				this.target = this.merchant.getCurrentCustomer();
				return true;
			} else {
				return false;
			}
		}
	}
}
*/