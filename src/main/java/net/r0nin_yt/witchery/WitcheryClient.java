package net.r0nin_yt.witchery;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.r0nin_yt.witchery.client.renderer.blockentity.WitchAltarBlockEntityRenderer;
import net.r0nin_yt.witchery.client.renderer.blockentity.WitchCauldronBlockEntityRenderer;
import net.r0nin_yt.witchery.common.particle.custom.DarknessParticle;
import net.r0nin_yt.witchery.common.particle.custom.WitcheryParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.r0nin_yt.witchery.common.registry.WitcheryBlockEntityTypes;

@SuppressWarnings({"unchecked", "ConstantConditions"})
@Environment(EnvType.CLIENT)
public class WitcheryClient implements ClientModInitializer {

    public static final EntityModelLayer BROOM_MODEL_LAYER = new EntityModelLayer(new Identifier(Witchery.MOD_ID, "broom"), "main");

    @Override
    public void onInitializeClient() {
         {

            BlockEntityRendererRegistry.register(WitcheryBlockEntityTypes.WITCH_ALTAR, ctx -> new WitchAltarBlockEntityRenderer());
            BlockEntityRendererRegistry.register(WitcheryBlockEntityTypes.WITCH_CAULDRON, ctx -> new WitchCauldronBlockEntityRenderer());
            ParticleFactoryRegistry.getInstance().register(WitcheryParticles.DARKNESS_PARTICLE, DarknessParticle.Factory::new);
        }
    }
}