

package net.r0nin_yt.witchery.mixin.client;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.r0nin_yt.witchery.common.registry.WitcheryStatusEffects;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.function.Supplier;

@SuppressWarnings("ConstantConditions")
@Environment(EnvType.CLIENT)
@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin extends World {
	protected ClientWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> dimension, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
		super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed);
	}

	@ModifyVariable(method = "playSound(DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFZ)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	private float modifySound0(float volume, double x, double y, double z, SoundEvent sound, SoundCategory category) {
		if (category != SoundCategory.MASTER && category != SoundCategory.VOICE) {
			ClientPlayerEntity clientPlayer = MinecraftClient.getInstance().player;
			if (clientPlayer != null && clientPlayer.hasStatusEffect(WitcheryStatusEffects.DEAFENED)) {
				return Math.max(0, volume * (1 - (0.2f * (clientPlayer.getStatusEffect(WitcheryStatusEffects.DEAFENED).getAmplifier() + 1))));
			}
		}
		return volume;
	}

	@ModifyVariable(method = "playSoundFromEntity", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	private float modifySound1(float volume, @Nullable PlayerEntity player, Entity entity, SoundEvent sound, SoundCategory category) {
		if (category != SoundCategory.MASTER && category != SoundCategory.VOICE) {
			ClientPlayerEntity clientPlayer = MinecraftClient.getInstance().player;
			if (clientPlayer != null && clientPlayer.hasStatusEffect(WitcheryStatusEffects.DEAFENED)) {
				return Math.max(0, volume * (1 - (0.2f * (clientPlayer.getStatusEffect(WitcheryStatusEffects.DEAFENED).getAmplifier() + 1))));
			}
		}
		return volume;
	}
}
