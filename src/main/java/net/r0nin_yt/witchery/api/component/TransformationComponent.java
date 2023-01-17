

package net.r0nin_yt.witchery.api.component;


import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.r0nin_yt.witchery.api.WitcheryAPI;
import net.r0nin_yt.witchery.api.event.AllowVampireBurn;
import net.r0nin_yt.witchery.api.event.AllowVampireHeal;
import net.r0nin_yt.witchery.api.event.OnTransformationSet;
import net.r0nin_yt.witchery.api.registry.Transformation;
import net.r0nin_yt.witchery.common.component.entity.AdditionalWerewolfDataComponent;
import net.r0nin_yt.witchery.common.misc.WitcheryUtil;
import net.r0nin_yt.witchery.common.registry.WitcheryComponents;
import net.r0nin_yt.witchery.common.registry.WitcheryPledges;
import net.r0nin_yt.witchery.common.registry.WitcheryRegistries;
import net.r0nin_yt.witchery.common.registry.WitcheryTransformations;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TransformationComponent implements AutoSyncedComponent, ServerTickingComponent {
	private static final EntityAttributeModifier VAMPIRE_ATTACK_DAMAGE_MODIFIER_0 = new EntityAttributeModifier(UUID.fromString("066862f6-989c-4f35-ac6d-2696b91a1a5b"), "Transformation modifier", 2, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier VAMPIRE_ATTACK_DAMAGE_MODIFIER_1 = new EntityAttributeModifier(UUID.fromString("d2be3564-97e7-42c9-88c5-6b753472e37f"), "Transformation modifier", 4, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier VAMPIRE_MOVEMENT_SPEED_MODIFIER_0 = new EntityAttributeModifier(UUID.fromString("a782c03d-af7b-4eb7-b997-dd396bfdc7a0"), "Transformation modifier", 0.04, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier VAMPIRE_MOVEMENT_SPEED_MODIFIER_1 = new EntityAttributeModifier(UUID.fromString("7c7a61eb-83e8-4e85-94d6-a410a4153d6d"), "Transformation modifier", 0.08, EntityAttributeModifier.Operation.ADDITION);

	private static final EntityAttributeModifier WEREWOLF_ATTACK_SPEED_MODIFIER = new EntityAttributeModifier(UUID.fromString("db2512a4-655d-4843-8b06-619748a33954"), "Transformation modifier", -2, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier WEREWOLF_ARMOR_MODIFIER = new EntityAttributeModifier(UUID.fromString("f00b0b0f-8ad6-4a2f-bdf5-6c337ffee56c"), "Transformation modifier", 16, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier WEREWOLF_ATTACK_RANGE_MODIFIER = new EntityAttributeModifier(UUID.fromString("ae0e4c0a-971f-4629-99ad-60c115112c1d"), "Transformation modifier", 1, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier WEREWOLF_REACH_MODIFIER = new EntityAttributeModifier(UUID.fromString("4c6d90ab-41ad-4d8a-b77a-7329361d3a7b"), "Transformation modifier", 1, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier WEREWOLF_STEP_HEIGHT_MODIFIER = new EntityAttributeModifier(UUID.fromString("af386c1c-b4fc-429d-97b6-b2559826fa9d"), "Transformation modifier", 0.4, EntityAttributeModifier.Operation.ADDITION);

	private static final EntityAttributeModifier WEREWOLF_ATTACK_DAMAGE_MODIFIER_0 = new EntityAttributeModifier(UUID.fromString("06861902-ebc4-4e6e-956c-59c1ae3085c7"), "Transformation modifier", 15, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier WEREWOLF_ATTACK_DAMAGE_MODIFIER_1 = new EntityAttributeModifier(UUID.fromString("10c0bedf-bde5-4cae-8acc-90b1204731dd"), "Transformation modifier", 30, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier WEREWOLF_ARMOR_TOUGHNESS_MODIFIER_0 = new EntityAttributeModifier(UUID.fromString("44f17821-1e30-426f-81d7-cd1da88fa584"), "Transformation modifier", 8, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier WEREWOLF_ARMOR_TOUGHNESS_MODIFIER_1 = new EntityAttributeModifier(UUID.fromString("edfd078d-e25c-4e27-ad91-c2b32037c8be"), "Transformation modifier", 16, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier WEREWOLF_MOVEMENT_SPEED_MODIFIER_0 = new EntityAttributeModifier(UUID.fromString("e26a276a-86cd-44db-9091-acd42fc00d95"), "Transformation modifier", 0.08, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier WEREWOLF_MOVEMENT_SPEED_MODIFIER_1 = new EntityAttributeModifier(UUID.fromString("718104a6-aa19-4b53-bad9-1f9edd46d38a"), "Transformation modifier", 0.16, EntityAttributeModifier.Operation.ADDITION);

	private final PlayerEntity obj;
	private Transformation transformation = WitcheryTransformations.HUMAN;
	private boolean alternateForm = false;

	public TransformationComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		if (tag.contains("Transformation")) {
			setTransformation(WitcheryRegistries.TRANSFORMATIONS.get(new Identifier(tag.getString("Transformation"))));
		}
		setAlternateForm(tag.getBoolean("AlternateForm"));
	}

	@SuppressWarnings({"ConstantConditions"})
	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putString("Transformation", WitcheryRegistries.TRANSFORMATIONS.getId(getTransformation()).toString());
		tag.putBoolean("AlternateForm", isAlternateForm());
	}

	@Override
	public void serverTick() {
    boolean vampire = WitcheryAPI.isVampire(obj, true);
		if (vampire) {
			boolean pledgedToLilith = WitcheryAPI.isPledged(obj, WitcheryPledges.LILITH);
			obj.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
		if (WitcheryComponents.RESPAWN_TIMER_COMPONENT.get(obj).getRespawnTimer() <= 0 && obj.world.isDay() && !obj.world.isRaining() && obj.world.isSkyVisible(obj.getBlockPos()) && AllowVampireBurn.EVENT.invoker().allowBurn(obj)) {
				obj.setOnFireFor(8);
			}
			HungerManager hungerManager = obj.getHungerManager();
			if (WitcheryComponents.BLOOD_COMPONENT.get(obj).getBlood() > 0) {
				if (AllowVampireHeal.EVENT.invoker().allowHeal(obj, pledgedToLilith)) {
					if (obj.age % (pledgedToLilith ? 30 : 40) == 0) {
						if (obj.getHealth() < obj.getMaxHealth()) {
							obj.heal(1);
							hungerManager.addExhaustion(3);
						}
						if ((hungerManager.isNotFull() || hungerManager.getSaturationLevel() < 10) && WitcheryComponents.BLOOD_COMPONENT.get(obj).drainBlood(1, false)) {
							hungerManager.add(1, 20);
						}
					}
				}
			} else {
				if (isAlternateForm()) {
		//			TransformationAbilityPacket.useAbility(obj, true);
				}
				hungerManager.addExhaustion(Float.MAX_VALUE);
			}
			if (isAlternateForm()) {
				hungerManager.addExhaustion(0.5f);
				if (!pledgedToLilith) {
//					TransformationAbilityPacket.useAbility(obj, true);
				}
			}
		}
		if (WitcheryAPI.isWerewolf(obj, true)) {
			AdditionalWerewolfDataComponent additionalWerewolfDataComponent = WitcheryComponents.ADDITIONAL_WEREWOLF_DATA_COMPONENT.get(obj);
			boolean forced = additionalWerewolfDataComponent.isForcedTransformation();
			if (!obj.isCreative() && !obj.isSpectator() && !isAlternateForm() && obj.world.isNight() && WitcheryAPI.getMoonPhase(obj.world) == 0 && obj.world.isSkyVisible(obj.getBlockPos())) {
	//			TransformationAbilityPacket.useAbility(obj, true);
				additionalWerewolfDataComponent.setForcedTransformation(true);
			} else if (isAlternateForm() && forced && (obj.world.isDay() || WitcheryAPI.getMoonPhase(obj.world) != 0)) {
	//			TransformationAbilityPacket.useAbility(obj, true);
				additionalWerewolfDataComponent.setForcedTransformation(false);
			}
			if (isAlternateForm()) {
				obj.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
				Set<ItemStack> toDrop = new HashSet<>();
				obj.getArmorItems().forEach(stack -> {
					if (!stack.isEmpty()) {
						toDrop.add(stack);
					}
				});
				if (WitcheryUtil.isTool(obj.getMainHandStack())) {
					toDrop.add(obj.getMainHandStack());
				}
				if (WitcheryUtil.isTool(obj.getOffHandStack())) {
					toDrop.add(obj.getOffHandStack());
				}
				toDrop.forEach(stack -> {
					int firstEmptyInventorySlot = getFirstEmptyInventorySlot();
					if (firstEmptyInventorySlot != -1) {
						obj.getInventory().setStack(firstEmptyInventorySlot, stack.split(stack.getCount()));
					} else {
						obj.dropStack(stack.split(stack.getCount()));
					}
				});
				if (!forced && !WitcheryAPI.isPledged(obj, WitcheryPledges.HERNE)) {
		//			TransformationAbilityPacket.useAbility(obj, true);
				}
			}
		}
	}

	public Transformation getTransformation() {
		return transformation;
	}

	public void setTransformation(Transformation transformation) {
		OnTransformationSet.EVENT.invoker().onTransformationSet(obj, transformation);
		this.transformation = transformation;
		WitcheryComponents.TRANSFORMATION_COMPONENT.sync(obj);
		updateAttributes();
	}

	public boolean isAlternateForm() {
		return alternateForm;
	}

	public void setAlternateForm(boolean alternateForm) {
		this.alternateForm = alternateForm;
		WitcheryComponents.TRANSFORMATION_COMPONENT.sync(obj);
		updateAttributes();
	}

	@SuppressWarnings("ConstantConditions")
	public void updateAttributes() {
		boolean vampire = WitcheryAPI.isVampire(obj, true);
		boolean werewolfBeast = WitcheryAPI.isWerewolf(obj, false);
		EntityAttributeInstance attackDamageAttribute = obj.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
		EntityAttributeInstance attackSpeedAttribute = obj.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
		EntityAttributeInstance armorAttribute = obj.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
		EntityAttributeInstance armorToughnessAttribute = obj.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
		EntityAttributeInstance movementSpeedAttribute = obj.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
//		EntityAttributeInstance attackRange = obj.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE);
//		EntityAttributeInstance reach = obj.getAttributeInstance(ReachEntityAttributes.REACH);
//		EntityAttributeInstance stepHeight = obj.getAttributeInstance(StepHeightEntityAttributeMain.STEP_HEIGHT);
		boolean shouldHave = vampire && !WitcheryAPI.isPledged(obj, WitcheryPledges.LILITH);
		if (shouldHave && !attackDamageAttribute.hasModifier(VAMPIRE_ATTACK_DAMAGE_MODIFIER_0)) {
			attackDamageAttribute.addPersistentModifier(VAMPIRE_ATTACK_DAMAGE_MODIFIER_0);
			movementSpeedAttribute.addPersistentModifier(VAMPIRE_MOVEMENT_SPEED_MODIFIER_0);
		} else if (!shouldHave && attackDamageAttribute.hasModifier(VAMPIRE_ATTACK_DAMAGE_MODIFIER_0)) {
			attackDamageAttribute.removeModifier(VAMPIRE_ATTACK_DAMAGE_MODIFIER_0);
			movementSpeedAttribute.removeModifier(VAMPIRE_MOVEMENT_SPEED_MODIFIER_0);
		}
		shouldHave = vampire && WitcheryAPI.isPledged(obj, WitcheryPledges.LILITH);
		if (shouldHave && !attackDamageAttribute.hasModifier(VAMPIRE_ATTACK_DAMAGE_MODIFIER_1)) {
			attackDamageAttribute.addPersistentModifier(VAMPIRE_ATTACK_DAMAGE_MODIFIER_1);
			movementSpeedAttribute.addPersistentModifier(VAMPIRE_MOVEMENT_SPEED_MODIFIER_1);
		} else if (!shouldHave && attackDamageAttribute.hasModifier(VAMPIRE_ATTACK_DAMAGE_MODIFIER_1)) {
			attackDamageAttribute.removeModifier(VAMPIRE_ATTACK_DAMAGE_MODIFIER_1);
			movementSpeedAttribute.removeModifier(VAMPIRE_MOVEMENT_SPEED_MODIFIER_1);
		}
		shouldHave = werewolfBeast;
		if (shouldHave && !attackSpeedAttribute.hasModifier(WEREWOLF_ATTACK_SPEED_MODIFIER)) {
			attackSpeedAttribute.addPersistentModifier(WEREWOLF_ATTACK_SPEED_MODIFIER);
			armorAttribute.addPersistentModifier(WEREWOLF_ARMOR_MODIFIER);
	//		attackRange.addPersistentModifier(WEREWOLF_ATTACK_RANGE_MODIFIER);
	//		reach.addPersistentModifier(WEREWOLF_REACH_MODIFIER);
	//		stepHeight.addPersistentModifier(WEREWOLF_STEP_HEIGHT_MODIFIER);
		} else if (!shouldHave && attackSpeedAttribute.hasModifier(WEREWOLF_ATTACK_SPEED_MODIFIER)) {
			attackSpeedAttribute.removeModifier(WEREWOLF_ATTACK_SPEED_MODIFIER);
			armorAttribute.removeModifier(WEREWOLF_ARMOR_MODIFIER);
	//		attackRange.removeModifier(WEREWOLF_ATTACK_RANGE_MODIFIER);
//			reach.removeModifier(WEREWOLF_REACH_MODIFIER);
	//		stepHeight.removeModifier(WEREWOLF_STEP_HEIGHT_MODIFIER);
		}
		shouldHave = werewolfBeast && !WitcheryAPI.isPledged(obj, WitcheryPledges.HERNE);
		if (shouldHave && !attackDamageAttribute.hasModifier(WEREWOLF_ATTACK_DAMAGE_MODIFIER_0)) {
			attackDamageAttribute.addPersistentModifier(WEREWOLF_ATTACK_DAMAGE_MODIFIER_0);
			armorToughnessAttribute.addPersistentModifier(WEREWOLF_ARMOR_TOUGHNESS_MODIFIER_0);
			movementSpeedAttribute.addPersistentModifier(WEREWOLF_MOVEMENT_SPEED_MODIFIER_0);
		} else if (!shouldHave && attackDamageAttribute.hasModifier(WEREWOLF_ATTACK_DAMAGE_MODIFIER_0)) {
			attackDamageAttribute.removeModifier(WEREWOLF_ATTACK_DAMAGE_MODIFIER_0);
			armorToughnessAttribute.removeModifier(WEREWOLF_ARMOR_TOUGHNESS_MODIFIER_0);
			movementSpeedAttribute.removeModifier(WEREWOLF_MOVEMENT_SPEED_MODIFIER_0);
		}
		shouldHave = werewolfBeast && WitcheryAPI.isPledged(obj, WitcheryPledges.HERNE);
		if (shouldHave && !attackDamageAttribute.hasModifier(WEREWOLF_ATTACK_DAMAGE_MODIFIER_1)) {
			attackDamageAttribute.addPersistentModifier(WEREWOLF_ATTACK_DAMAGE_MODIFIER_1);
			armorToughnessAttribute.addPersistentModifier(WEREWOLF_ARMOR_TOUGHNESS_MODIFIER_1);
			movementSpeedAttribute.addPersistentModifier(WEREWOLF_MOVEMENT_SPEED_MODIFIER_1);
		} else if (!shouldHave && attackDamageAttribute.hasModifier(WEREWOLF_ATTACK_DAMAGE_MODIFIER_1)) {
			attackDamageAttribute.removeModifier(WEREWOLF_ATTACK_DAMAGE_MODIFIER_1);
			armorToughnessAttribute.removeModifier(WEREWOLF_ARMOR_TOUGHNESS_MODIFIER_1);
			movementSpeedAttribute.removeModifier(WEREWOLF_MOVEMENT_SPEED_MODIFIER_1);
		}
	}

	private int getFirstEmptyInventorySlot() {
		for (int i = 0; i < obj.getInventory().main.size(); i++) {
			if (!PlayerInventory.isValidHotbarIndex(i) && obj.getInventory().main.get(i).isEmpty()) {
				return i;
			}
		}
		return -1;
	}
}
