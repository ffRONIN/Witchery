package net.r0nin_yt.witchery;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;


import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.r0nin_yt.witchery.api.WitcheryAPI;
import net.r0nin_yt.witchery.common.block.WitcheryBlocks;
import net.r0nin_yt.witchery.common.item.WitcheryItems;
import net.r0nin_yt.witchery.common.network.packet.CauldronTeleportPacket;
import net.r0nin_yt.witchery.common.network.packet.TogglePressingForwardPacket;
import net.r0nin_yt.witchery.common.network.packet.TransformationAbilityPacket;
import net.r0nin_yt.witchery.common.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Witchery implements ModInitializer {
    public static final String MOD_ID = "witchery";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @SuppressWarnings("ConstantConditions")
    @Override
    public void onInitialize() {
        MidnightConfig.init(MOD_ID, WitcheryConfig.class);
        ServerPlayNetworking.registerGlobalReceiver(CauldronTeleportPacket.ID, CauldronTeleportPacket::handle);
        ServerPlayNetworking.registerGlobalReceiver(TransformationAbilityPacket.ID, TransformationAbilityPacket::handle);
        ServerPlayNetworking.registerGlobalReceiver(TogglePressingForwardPacket.ID, TogglePressingForwardPacket::handle);
     //   CommandRegistrationCallback.EVENT.register(WitcheryCommands::init);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> WitcheryAPI.fakePlayer = null);
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
   //         WitcheryComponents.TRANSFORMATION_COMPONENT.get(newPlayer).setAlternateForm(false);
       //     WitcheryComponents.ADDITIONAL_WEREWOLF_DATA_COMPONENT.get(newPlayer).setForcedTransformation(false);
        });
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
          if (alive && WitcheryComponents.TRANSFORMATION_COMPONENT.get(oldPlayer).isAlternateForm()) {
   //             TransformationAbilityPacket.useAbility(newPlayer, true);
    //        }
    //    });
     //   ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
     //       if (entity instanceof LivingEntity livingEntity) {
     //           if (livingEntity instanceof PlayerEntity player && killedEntity.getGroup() == EntityGroup.ARTHROPOD && WitcheryAPI.getFamiliar(player) == WitcheryEntityTypes.TOAD) {
      //              livingEntity.heal(livingEntity.getMaxHealth() * 1 / 4f);
      //          }
      //          if (livingEntity instanceof PlayerEntity player && WitcheryComponents.CONTRACTS_COMPONENT.get(player).hasContract(WitcheryContracts.DEATH)) {
      //              livingEntity.heal(livingEntity.getMaxHealth() / 4f);
       //         }
       //         if (livingEntity.getMainHandStack().getItem() instanceof AthameItem) {
       //             BlockPos glyphPos = WitcheryUtil.getClosestBlockPos(killedEntity.getBlockPos(), 6, currentPos -> world.getBlockEntity(currentPos) instanceof GlyphBlockEntity);
       //             if (glyphPos != null) {
        //                ((GlyphBlockEntity) world.getBlockEntity(glyphPos)).onUse(world, glyphPos, livingEntity, Hand.MAIN_HAND, killedEntity);

         //           if (world.isNight()) {
            //            boolean chicken = killedEntity instanceof ChickenEntity, wolf = killedEntity instanceof WolfEntity;
           //             if (chicken || wolf) {
           //                 RegistryEntry<Biome> biome = world.getBiome(killedEntity.getBlockPos());
            //                MobEntity boss = null;
           //                 if (chicken) {
             ///                   if (biome.isIn(ConventionalBiomeTags.EXTREME_HILLS) || biome.isIn(ConventionalBiomeTags.ICY) || biome.isIn(ConventionalBiomeTags.MOUNTAIN)) {
             //                      boss = WitcheryEntityTypes.LILITH.create(world);
               //                 }
             //               }
              //              if (wolf) {
            //                    if (biome.isIn(ConventionalBiomeTags.TAIGA) || biome.isIn(ConventionalBiomeTags.FOREST)) {
              //                      boss = WitcheryEntityTypes.HERNE.create(world);
              //                  }
            //                }
            //                if (boss != null) {
                //                BlockPos brazierPos = WitcheryUtil.getClosestBlockPos(killedEntity.getBlockPos(), 8, currentPos -> world.getBlockEntity(currentPos) instanceof BrazierBlockEntity brazier && brazier.incenseRecipe.effect == BWStatusEffects.MORTAL_COIL);
               //                 if (brazierPos != null) {
             //                       world.breakBlock(brazierPos, false);
             //                       world.createExplosion(null, brazierPos.getX() + 0.5, brazierPos.getY() + 0.5, brazierPos.getZ() + 0.5, 3, Explosion.DestructionType.BREAK);
             //                       boss.initialize(world, world.getLocalDifficulty(brazierPos), SpawnReason.EVENT, null, null);
              //                      boss.updatePositionAndAngles(brazierPos.getX() + 0.5, brazierPos.getY(), brazierPos.getZ() + 0.5, world.random.nextFloat() * 360, 0);
              //                      world.spawnEntity(boss);
               //                 }
               //             }
                //        }
                    }
   //                for (
 //  AthameDropRecipe recipe : world.getRecipeManager().listAllOfType(WitcheryRecipeTypes.ATHAME_DROP_RECIPE_TYPE)) {
 //                       if (recipe.entity_type.equals(killedEntity.getType()) && world.random.nextFloat() < recipe.chance * (EnchantmentHelper.getLooting(livingEntity) + 1)) {
   //                         ItemStack drop = recipe.getOutput().copy();
     //                       if (recipe.entity_type == EntityType.PLAYER) {
      //                          drop.getOrCreateNbt().putString("SkullOwner", killedEntity.getName().getString());
         //                  }
        //                    ItemScatterer.spawn(world, killedEntity.getX() + 0.5, killedEntity.getY() + 0.5, killedEntity.getZ() + 0.5, drop);
        //                }
       //             }
     //               if (livingEntity instanceof PlayerEntity player && livingEntity.getOffHandStack().getItem() == Items.GLASS_BOTTLE && WitcheryComponents.BLOOD_COMPONENT.get(killedEntity).getBlood() > 20 && killedEntity.getType().isIn(BWTags.HAS_BLOOD)) {
      //                  world.playSound(null, livingEntity.getBlockPos(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.PLAYERS, 1, 0.5f);
      //                  WitcheryUtil.addItemToInventoryAndConsume(player, Hand.OFF_HAND, new ItemStack(BWObjects.BOTTLE_OF_BLOOD));
       //             }
    //            }

  //      });
  /*      UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof LivingEntity livingEntity && hand == Hand.MAIN_HAND && player.isSneaking() && entity.isAlive() && BewitchmentAPI.isVampire(player, true) && player.getStackInHand(hand).isEmpty()) {
                int toGive = entity.getType().isIn(BWTags.HAS_BLOOD) ? 5 : entity instanceof AnimalEntity ? 1 : 0;
                toGive = BloodSuckEvents.BLOOD_AMOUNT.invoker().onBloodSuck(player, livingEntity, toGive);
                if (toGive > 0) {
                    BloodComponent playerBloodComponent = BWComponents.BLOOD_COMPONENT.get(player);
                    BloodComponent entityBloodComponent = BWComponents.BLOOD_COMPONENT.get(livingEntity);
                    if (playerBloodComponent.fillBlood(toGive, true) && entityBloodComponent.drainBlood(10, true)) {
                        if (!world.isClient && livingEntity.hurtTime == 0) {
                            BloodSuckEvents.ON_BLOOD_SUCK.invoker().onBloodSuck(player, livingEntity, toGive);
                            world.playSound(null, player.getBlockPos(), SoundEvents.ITEM_HONEY_BOTTLE_DRINK, player.getSoundCategory(), 1, 0.5f);
                            if (!livingEntity.isSleeping() || entityBloodComponent.getBlood() < BloodComponent.MAX_BLOOD / 2) {
                                entity.damage(BWDamageSources.VAMPIRE, 2);
                            }
                            playerBloodComponent.fillBlood(toGive, false);
                            entityBloodComponent.drainBlood(10, false);
                        }
                        return ActionResult.success(world.isClient);
                    }
                }
            }
            return ActionResult.PASS;
        });
        EntitySleepEvents.ALLOW_SLEEP_TIME.register((player, sleepingPos, vanillaResult) -> player.world.getBlockState(sleepingPos).getBlock() instanceof CoffinBlock && player.world.isDay() ? ActionResult.success(player.world.isClient) : ActionResult.PASS);
        EntitySleepEvents.ALLOW_SLEEPING.register((player, sleepingPos) -> {
            if (WitcheryComponents.TRANSFORMATION_COMPONENT.get(player).isAlternateForm()) {
                player.sendMessage(Text.translatable("block.minecraft.bed.transformation"), true);
                return PlayerEntity.SleepFailureReason.OTHER_PROBLEM;
            }
            if (player.world.getBlockState(sleepingPos).getBlock() instanceof CoffinBlock) {
                if (player.world.isNight()) {
                    player.sendMessage(Text.translatable("block.minecraft.bed.coffin"), true);
                    return PlayerEntity.SleepFailureReason.OTHER_PROBLEM;
                }
                return null;
            }
            return null;
        });
        EntitySleepEvents.STOP_SLEEPING.register((entity, sleepingPos) -> {
            if (!entity.world.isClient) {
                WitcheryUtil.getBlockPoses(entity.getBlockPos(), 12, currentPos -> entity.world.getBlockEntity(currentPos) instanceof BrazierBlockEntity brazier && brazier.incenseRecipe != null).forEach(foundPos -> {
                    IncenseRecipe recipe = ((BrazierBlockEntity) entity.world.getBlockEntity(foundPos)).incenseRecipe;
                    int durationMultiplier = 1;
                    BlockPos nearestSigil = BWUtil.getClosestBlockPos(entity.getBlockPos(), 16, currentPos -> entity.world.getBlockEntity(currentPos) instanceof SigilBlockEntity sigil && sigil.getSigil() == BWSigils.EXTENDING);
                    if (nearestSigil != null) {
                        BlockEntity blockEntity = entity.world.getBlockEntity(nearestSigil);
                        SigilHolder sigilHolder = ((SigilHolder) blockEntity);
                        if (sigilHolder.test(entity)) {
                            sigilHolder.setUses(sigilHolder.getUses() - 1);
                            blockEntity.markDirty();
                            durationMultiplier = 2;
                        }
                    }
                    entity.addStatusEffect(new StatusEffectInstance(recipe.effect, 24000 * durationMultiplier, recipe.amplifier, true, false));
                });
            }
     */








  //      WitcheryScaleTypes.init();
   //     WitcheryObjects.init();
  //      WitcheryBoatTypes.init();
        WitcheryBlockEntityTypes.init();
        WitcheryEntityTypes.init();
        WitcheryStatusEffects.init();
        WitcheryEnchantments.init();
  //     WitcheryRitualFunctions.init();
  //     WitcheryFortunes.init();
  //     WitcherySigils.init();
    //    WitcheryTransformations.init();
   //    WitcheryContracts.init();
        WitcheryCurses.init();
        WitcherySoundEvents.init();
        WitcheryStatusEffects.init();
  //      WitcheryRecipeTypes.init();
        WitcheryWorldGenerators.init();
  //      WitcheryScreenHandlerTypes.init();
  //      WitcheryAPI.registerAltarMapEntries(WitcheryBlocks.STONE_WITCH_ALTAR);
  //      WitcheryAPI.registerAltarMapEntries(WitcheryBlocks.MOSSY_COBBLESTONE_WITCH_ALTAR);
  //      WitcheryAPI.registerAltarMapEntries(WitcheryBlocks.PRISMARINE_WITCH_ALTAR);
  //      WitcheryAPI.registerAltarMapEntries(WitcheryBlocks.NETHER_BRICK_WITCH_ALTAR);
  //      WitcheryAPI.registerAltarMapEntries(WitcheryBlocks.BLACKSTONE_WITCH_ALTAR);
  //      WitcheryAPI.registerAltarMapEntries(WitcheryBlocks.GOLDEN_WITCH_ALTAR);
  //      WitcheryAPI.registerAltarMapEntries(WitcheryBlocks.END_STONE_WITCH_ALTAR);
  //      WitcheryAPI.registerAltarMapEntries(WitcheryBlocks.OBSIDIAN_WITCH_ALTAR);
  //      WitcheryAPI.registerAltarMapEntries(WitcheryBlocks.PURPUR_WITCH_ALTAR);
        WitcheryItems.registerModItems();
        WitcheryBlocks.registerModBlocks();


    });



}}


