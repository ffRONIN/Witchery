
package net.r0nin_yt.witchery.common.registry;


import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.r0nin_yt.witchery.Witchery;

import java.util.LinkedHashMap;
import java.util.Map;

public class WitcheryParticleTypes {
	private static final Map<ParticleType<?>, Identifier> PARTICLE_TYPES = new LinkedHashMap<>();

	public static final ParticleType<DefaultParticleType> CAULDRON_BUBBLE = create("cauldron_bubble", FabricParticleTypes.simple());
	public static final ParticleType<DefaultParticleType> INCENSE_SMOKE = create("incense_smoke", FabricParticleTypes.simple());

	private static <T extends ParticleEffect> ParticleType<T> create(String name, ParticleType<T> type) {
		PARTICLE_TYPES.put(type, new Identifier(Witchery.MOD_ID, name));
		return type;
	}

	public static void init() {
		PARTICLE_TYPES.keySet().forEach(particleType -> Registry.register(Registry.PARTICLE_TYPE, PARTICLE_TYPES.get(particleType), particleType));
	}
}
