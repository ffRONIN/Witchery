package net.r0nin_yt.witchery.common.effect;


import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.r0nin_yt.witchery.Witchery;

public class WitcheryEffects {
    public static StatusEffect DARKNESS;

    public static StatusEffect registerStatusEffect(String name) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(Witchery.MOD_ID, name),
                new DarknessEffect(StatusEffectCategory.NEUTRAL, 0000000));
    }




            public static void registerEffects () {
                DARKNESS = registerStatusEffect("darkness");


            }
        }
