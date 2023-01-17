

package net.r0nin_yt.witchery.common.registry;



import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.r0nin_yt.witchery.Witchery;
import net.r0nin_yt.witchery.api.registry.*;


public class WitcheryRegistries {
	public static final Registry<RitualFunction> RITUAL_FUNCTIONS = FabricRegistryBuilder.createSimple(RitualFunction.class, new Identifier(Witchery.MOD_ID, "ritual_functions")).buildAndRegister();
	public static final Registry<Sigil> SIGILS = FabricRegistryBuilder.createSimple(Sigil.class, new Identifier(Witchery.MOD_ID, "sigils")).buildAndRegister();
	public static final Registry<Fortune> FORTUNES = FabricRegistryBuilder.createSimple(Fortune.class, new Identifier(Witchery.MOD_ID, "fortunes")).buildAndRegister();
	public static final Registry<Transformation> TRANSFORMATIONS = FabricRegistryBuilder.createSimple(Transformation.class, new Identifier(Witchery.MOD_ID, "transformations")).buildAndRegister();
	public static final Registry<Contract> CONTRACTS = FabricRegistryBuilder.createSimple(Contract.class, new Identifier(Witchery.MOD_ID, "contracts")).buildAndRegister();
	public static final Registry<Curse> CURSES = FabricRegistryBuilder.createSimple(Curse.class, new Identifier(Witchery.MOD_ID, "curses")).buildAndRegister();
}
