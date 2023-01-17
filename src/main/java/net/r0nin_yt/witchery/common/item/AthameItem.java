

package net.r0nin_yt.witchery.common.item;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.common.recipe.AthameStrippingRecipe;
import net.r0nin_yt.witchery.common.registry.WitcheryProperties;
import net.r0nin_yt.witchery.common.registry.WitcheryRecipeTypes;
import net.r0nin_yt.witchery.common.registry.WitcherySoundEvents;

import java.util.UUID;

public class AthameItem extends SwordItem {
	private static final EntityAttributeModifier REACH_MODIFIER = new EntityAttributeModifier(UUID.fromString("1f362972-c5c5-4e9d-b69f-1fd13bd269e3"), "Weapon modifier", -0.5, EntityAttributeModifier.Operation.ADDITION);

	private static final DispenserBehavior DISPENSER_BEHAVIOR = new FallibleItemDispenserBehavior() {
		@Override
		protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
			World world = pointer.getWorld();
			BlockPos pos = pointer.getPos().offset(pointer.getBlockState().get(Properties.FACING));
			if (cutLog(world, pos, stack)) {
				setSuccess(true);
	//		} else if (world.getBlockState(pos).getBlock() == WitcheryBlocks.GOLDEN_GLYPH) {
	//			WitcheryBlocks.GOLDEN_GLYPH.onUse(world.getBlockState(pos), world, pos, WitcheryAPI.getFakePlayer(world), Hand.MAIN_HAND, BlockHitResult.createMissed(new Vec3d(pos.getX(), pos.getY(), pos.getZ()), Direction.DOWN, pos));
	//			setSuccess(true);
			}
			return stack;
		}
	};

	public AthameItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
		DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);
		PlayerEntity player = context.getPlayer();
		boolean client = world.isClient;
		AthameStrippingRecipe entry = world.getRecipeManager().listAllOfType(WitcheryRecipeTypes.ATHAME_STRIPPING_RECIPE_TYPE).stream().filter(recipe -> recipe.log == state.getBlock()).findFirst().orElse(null);
		if (entry != null) {
			world.playSound(player, pos, WitcherySoundEvents.ITEM_ATHAME_STRIP, SoundCategory.BLOCKS, 1, 1);
			if (!client) {
				world.setBlockState(pos, entry.strippedLog.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)), 11);
				if (player != null) {
					context.getStack().damage(1, player, stackUser -> stackUser.sendToolBreakStatus(context.getHand()));
					if (world.random.nextFloat() < 2 / 3f) {
						ItemStack bark = entry.getOutput().copy();
						if (!player.getInventory().insertStack(bark)) {
							player.dropStack(bark);
						}
					}
				}
			}
			return ActionResult.success(client);
		}
//		BlockEntity blockEntity = world.getBlockEntity(state.getBlock() instanceof DoorBlock && state.get(DoorBlock.HALF) == DoubleBlockHalf.UPPER ? pos.down() : pos);
//		if (blockEntity instanceof SigilHolder sigilHolder) {
//			if (player != null && player.getUuid().equals(sigilHolder.getOwner())) {
//				if (!client && !sigilHolder.getEntities().isEmpty()) {
//					boolean whitelist = sigilHolder.getModeOnWhitelist();
//					world.playSound(null, pos, BWSoundEvents.BLOCK_SIGIL_PLING, SoundCategory.BLOCKS, 1, whitelist ? 0.5f : 1);
//					player.sendMessage(Text.translatable(Bewitchment.MODID + ".message.toggle_" + (!whitelist ? "whitelist" : "blacklist")), true);
//					sigilHolder.setModeOnWhitelist(!whitelist);
	//				blockEntity.markDirty();
	//			}
//				return ActionResult.success(client);
//			}
//		} else if (blockEntity instanceof TaglockHolder taglockHolder) {
//			if (player != null && player.getUuid().equals(taglockHolder.getOwner()) && taglockHolder.getFirstEmptySlot() != 0) {
//				if (!client) {
	//				ItemScatterer.spawn(world, pos, taglockHolder.getTaglockInventory());
//					taglockHolder.syncTaglockHolder(blockEntity);
	//				blockEntity.markDirty();
	////			}
	//			return ActionResult.success(client);
	//		}
//	} else if (blockEntity instanceof Lockable lockable) {
	//		if (player != null && player.getUuid().equals(lockable.getOwner()) && !lockable.getEntities().isEmpty()) {
	//			if (!client) {
	//				boolean whitelist = lockable.getModeOnWhitelist();
	//				world.playSound(null, pos, WitcherySoundEvents.BLOCK_SIGIL_PLING, SoundCategory.BLOCKS, 1, whitelist ? 0.5f : 1);
	//				player.sendMessage(Text.translatable(Witchery.MOD_ID + ".message.toggle_" + (!whitelist ? "whitelist" : "blacklist")), true);
	//				lockable.setModeOnWhitelist(!whitelist);
	//				blockEntity.markDirty();
	//			}
				return ActionResult.success(client);
			}
//		}
	//	return super.useOnBlock(context);
	//}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		Multimap<EntityAttribute, EntityAttributeModifier> map = LinkedHashMultimap.create(super.getAttributeModifiers(slot));
		if (slot == EquipmentSlot.MAINHAND) {
		//	map.put(ReachEntityAttributes.ATTACK_RANGE, REACH_MODIFIER);
		}
		return map;
	}

	private static boolean cutLog(World world, BlockPos pos, ItemStack stack) {
		BlockState state = world.getBlockState(pos);
	//	if (state.getBlock() instanceof DragonsBloodLogBlock && state.get(WitcheryProperties.NATURAL) && !state.get(WitcheryProperties.CUT)) {
			world.playSound(null, pos, WitcherySoundEvents.ITEM_ATHAME_STRIP, SoundCategory.BLOCKS, 1, 1);
			world.setBlockState(pos, state.with(WitcheryProperties.CUT, true));
			if (stack.damage(1, world.random, null) && stack.getDamage() >= stack.getMaxDamage()) {
				stack.decrement(1);
			}
			return true;
		}
	//	return false;
	}
//}
