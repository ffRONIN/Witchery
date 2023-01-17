package net.r0nin_yt.witchery.common.particle.custom;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.r0nin_yt.witchery.Witchery;

public class WitcheryParticles {
    public static final DefaultParticleType DARKNESS_PARTICLE = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Witchery.MOD_ID, "darkness_particle"),
                DARKNESS_PARTICLE);
    }
}