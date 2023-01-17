
package net.r0nin_yt.witchery.common.item;


import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.mixin.MobEntityMixin;

import java.util.UUID;

@SuppressWarnings("ConstantConditions")
public class TaglockItem extends Item {
	public TaglockItem(Settings settings) {
		super(settings);
	}

	public static UUID getTaglockUUID(ItemStack taglock) {
		return null;
	}

	public static boolean hasTaglock(ItemStack stack) {
		return false;
	}

	public static ItemStack copyTo(ItemStack taglock, ItemStack craftedStack) {
		return null;
	}

	public static ActionResult useTaglock(PlayerEntity player, MobEntityMixin mobEntityMixin, Hand hand, boolean b, boolean b1) {
		return null;
	}

    @Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);
		PlayerEntity player = context.getPlayer();
		boolean client = world.isClient;
		if (state.getBlock() instanceof BedBlock) {
			if (!client && player != null && player.isSneaking()) {
				MinecraftServer server = world.getServer();
				if (server != null) {
					if (state.get(BedBlock.PART) != BedPart.HEAD) {
						pos = pos.offset(state.get(BedBlock.FACING));
					}
					PlayerEntity earliestSleeper = null;
					for (ServerPlayerEntity playerEntity : server.getPlayerManager().getPlayerList()) {
						BlockPos bedPos = playerEntity.getSpawnPointPosition();
						if (bedPos != null && bedPos.equals(pos) && (earliestSleeper == null || playerEntity.getSleepTimer() < earliestSleeper.getSleepTimer())) {
							earliestSleeper = playerEntity;
						}
					}
		//			if (earliestSleeper != null) {
		//				return useTaglock(player, earliestSleeper, context.getHand(), false, true);
						}
				}
			}
			return ActionResult.success(client);
	/*	} else {
			ItemStack stack = context.getStack();
			BlockEntity blockEntity = world.getBlockEntity(state.getBlock() instanceof DoorBlock && state.get(DoorBlock.HALF) == DoubleBlockHalf.UPPER ? pos.down() : pos);
			if (blockEntity instanceof TaglockHolder taglockHolder) {
				if (player.getUuid().equals(taglockHolder.getOwner())) {
					int firstEmpty = taglockHolder.getFirstEmptySlot();
					if (firstEmpty != -1) {
						if (!client) {
							taglockHolder.getTaglockInventory().set(firstEmpty, stack.split(1));
							taglockHolder.syncTaglockHolder(blockEntity);
							blockEntity.markDirty();
						}
						return ActionResult.success(client);
					}
				}
			} else if (blockEntity instanceof Lockable lockable) {
				if (hasTaglock(stack)) {
					if (player.getUuid().equals(lockable.getOwner()) && lockable.getLocked()) {
						UUID uuid = getTaglockUUID(stack);
						if (!lockable.getEntities().contains(uuid)) {
							if (!client) {
								if (lockable.getEntities().isEmpty()) {
									lockable.setModeOnWhitelist(true);
								}
								lockable.getEntities().add(uuid);
								if (!player.isCreative()) {
									stack.decrement(1);
								}
								lockable.syncLockable(blockEntity);
								blockEntity.markDirty();
							}
							return ActionResult.success(client);
						}
					}
				}
//			} else if (blockEntity instanceof SigilHolder sigilHolder) {
	//			if (hasTaglock(stack)) {
	//				if (!client) {
	//					if (sigilHolder.getSigil() != null) {
	//						UUID uuid = getTaglockUUID(stack);
	//						if (!sigilHolder.getEntities().contains(uuid)) {
	///							if (sigilHolder.getEntities().isEmpty()) {
	//								sigilHolder.setModeOnWhitelist(true);
		//						}
		//						sigilHolder.getEntities().add(uuid);
		//						if (!player.isCreative()) {
		//							stack.decrement(1);
	//							}
		//						sigilHolder.syncSigilHolder(blockEntity);
		//						blockEntity.markDirty();
		//					}
			//			}
	//				}
		//			return ActionResult.success(client);
	//			}
		//	}
	//	}
	//	return super.useOnBlock(context);
//	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (entity instanceof PlayerEntity) {
			return useTaglock(user, entity, hand, true, false);
		}
		return super.useOnEntity(stack, user, entity, hand);
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if (hasTaglock(stack)) {
			tooltip.add(Text.literal(getTaglockName(stack)).formatted(Formatting.GRAY));
			NbtCompound nbt = stack.getNbt();
			if (nbt.contains("UsedForScrying")) {
				if (nbt.contains("Failed")) {
					tooltip.add(Text.translatable(Witchery.MOD_ID + ".tooltip.failed").formatted(Formatting.DARK_GRAY));
				} else {
					boolean shifting = Screen.hasShiftDown();
					BlockPos pos = BlockPos.fromLong(stack.getNbt().getLong("LocationPos"));
					tooltip.add(Text.translatable(Witchery.MOD_ID + ".tooltip.location", pos.getX(), pos.getY(), pos.getZ(), stack.getNbt().getString("LocationWorld")).formatted(Formatting.DARK_GRAY));
					tooltip.add(Text.translatable(Witchery.MOD_ID + ".tooltip.level", stack.getNbt().getInt("Level")).formatted(Formatting.DARK_GRAY));
					MutableText curseTooltip = Text.translatable(Witchery.MOD_IDD + ".tooltip.curse");
					NbtList cursesList = nbt.getList("Curses", 10);
					if (cursesList.isEmpty()) {
						curseTooltip.append(Text.translatable(Witchery.MOD_ID + ".tooltip.none"));
					} else if (!shifting) {
						curseTooltip.append(Text.translatable(Witchery.MOD_ID + ".tooltip.press_shift"));
					}
					tooltip.add(curseTooltip.formatted(Formatting.DARK_GRAY));
					if (shifting) {
						for (NbtElement element : cursesList) {
							NbtCompound curseCompound = (NbtCompound) element;
							MutableText curseText = Text.translatable("curse." + curseCompound.getString("Curse").replace(":", "."));
							int duration = curseCompound.getInt("Duration") / 24000;
							tooltip.add(Text.translatable(Bewitchment.MODID + ".tooltip.curse_expanded", curseText, duration).formatted(Formatting.DARK_GRAY));
						}
					}
					MutableText contractTooltip = Text.translatable(Witchery.MOD_ID + ".tooltip.contract");
					NbtList contractList = nbt.getList("Contracts", 10);
					if (contractList.isEmpty()) {
						contractTooltip.append(Text.translatable(Witchery.MOD_ID + ".tooltip.none"));
					} else if (!shifting) {
						contractTooltip.append(Text.translatable(Witchery.MOD_ID + ".tooltip.press_shift"));
					}
					tooltip.add(contractTooltip.formatted(Formatting.DARK_GRAY));
					if (shifting) {
						for (NbtElement element : contractList) {
							NbtCompound contractCompound = (NbtCompound) element;
							MutableText curseText = Text.translatable("contract." + contractCompound.getString("Contract").replace(":", "."));
							int duration = contractCompound.getInt("Duration") / 24000;
							tooltip.add(Text.translatable(Bewitchment.MODID + ".tooltip.curse_expanded", curseText, duration).formatted(Formatting.DARK_GRAY));
						}
					}
					tooltip.add(Text.translatable(Witchery.MOD_ID + ".tooltip.transformation", Text.translatable(stack.getNbt().getString("Transformation"))).formatted(Formatting.DARK_GRAY));
					tooltip.add(Text.translatable(Witchery.MOD_ID + ".tooltip.familiar", Text.translatable("entity." + nbt.getString("Familiar").replace(":", "."))).formatted(Formatting.DARK_GRAY));
					tooltip.add(Text.translatable(Witchery.MOD_ID + ".tooltip.pledge", Text.translatable(stack.getNbt().getString("Pledge"))).formatted(Formatting.DARK_GRAY));
				}
			}
		}
	}

	public static ActionResult useTaglock(PlayerEntity user, LivingEntity entity, Hand hand, boolean checkVisibility, boolean bed) {
		ItemStack stack = user.getStackInHand(hand);
		if (entity.isAlive() && !entity.getType().isIn(BWTags.TAGLOCK_BLACKLIST) && !hasTaglock(stack)) {
			boolean failed = false;
			BlockPos sigilPos = BWUtil.getClosestBlockPos(entity.getBlockPos(), 16, currentPos -> user.world.getBlockEntity(currentPos) instanceof SigilHolder && ((SigilHolder) user.world.getBlockEntity(currentPos)).getSigil() == BWSigils.SLIPPERY);
			if (sigilPos == null && bed) {
				sigilPos = BWUtil.getClosestBlockPos(user.getBlockPos(), 16, currentPos -> user.world.getBlockEntity(currentPos) instanceof SigilHolder && ((SigilHolder) user.world.getBlockEntity(currentPos)).getSigil() == BWSigils.SLIPPERY);
			}
			if (sigilPos != null) {
				BlockEntity blockEntity = user.world.getBlockEntity(sigilPos);
				SigilHolder sigil = (SigilHolder) blockEntity;
				if (sigil.test(entity)) {
					sigil.setUses(sigil.getUses() - 1);
					blockEntity.markDirty();
					failed = true;
				}
			}
			if (!failed && checkVisibility && !entity.isSleeping()) {
				double targetYaw = entity.getHeadYaw() % 360;
				double userYaw = user.getHeadYaw() % 360;
				if (userYaw < 0) {
					userYaw += 360;
				}
				if (targetYaw < 0) {
					targetYaw += 360;
				}
				failed = Math.abs(targetYaw - userYaw) > 120;
			}
			if (failed) {
				if (entity instanceof PlayerEntity player) {
					player.sendMessage(Text.translatable(Bewitchment.MODID + ".message.taglock_fail", user.getDisplayName().getString()), false);
					if (entity.world.isClient) {
						entity.world.playSoundFromEntity(player, user, BWSoundEvents.ENTITY_GENERIC_PLING, SoundCategory.PLAYERS, 1, 1);
					}
				}
				return ActionResult.FAIL;
			}
			boolean client = user.world.isClient;
			if (!client) {
				if (entity instanceof MobEntity mob) {
					mob.setPersistent();
				}
				BWUtil.addItemToInventoryAndConsume(user, hand, putTaglock(new ItemStack(BWObjects.TAGLOCK), entity));
			} else {
				user.world.playSoundFromEntity(user, entity, BWSoundEvents.ENTITY_GENERIC_PLING, SoundCategory.PLAYERS, 1, 1);
			}
			return ActionResult.success(client);
		}
		return ActionResult.FAIL;
	}

	public static ItemStack putTaglock(ItemStack stack, Entity entity) {
		stack.getOrCreateNbt().putUuid("OwnerUUID", entity.getUuid());
		stack.getOrCreateNbt().putString("OwnerName", entity.getDisplayName().getString());
		stack.getOrCreateNbt().putBoolean("FromPlayer", entity instanceof PlayerEntity);
		return stack;
	}

	public static ItemStack copyTo(ItemStack from, ItemStack to) {
		if (hasTaglock(from)) {
			to.getOrCreateNbt().putUuid("OwnerUUID", from.getOrCreateNbt().getUuid("OwnerUUID"));
			to.getOrCreateNbt().putString("OwnerName", from.getOrCreateNbt().getString("OwnerName"));
			to.getOrCreateNbt().putBoolean("FromPlayer", from.getOrCreateNbt().getBoolean("FromPlayer"));
		}
		return to;
	}

	public static boolean hasTaglock(ItemStack stack) {
		return stack.hasNbt() && stack.getOrCreateNbt().contains("OwnerUUID");
	}

	public static void removeTaglock(ItemStack stack) {
		if (stack.hasNbt()) {
			stack.getOrCreateNbt().remove("OwnerUUID");
			stack.getOrCreateNbt().remove("OwnerName");
			stack.getOrCreateNbt().remove("FromPlayer");
		}
	}

	public static UUID getTaglockUUID(ItemStack stack) {
		if (hasTaglock(stack)) {
			return stack.getOrCreateNbt().getUuid("OwnerUUID");
		}
		return null;
	}

	public static String getTaglockName(ItemStack stack) {
		if (hasTaglock(stack)) {
			return stack.getOrCreateNbt().getString("OwnerName");
		}
		return "";
	}

	public static boolean isTaglockFromPlayer(ItemStack stack) {
		return hasTaglock(stack) && stack.getOrCreateNbt().getBoolean("FromPlayer");
	}
}
*/


				}
			}


