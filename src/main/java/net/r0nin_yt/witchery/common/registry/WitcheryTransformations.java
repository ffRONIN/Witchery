
package net.r0nin_yt.witchery.common.registry;


import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.r0nin_yt.witchery.Witchery;
import net.r0nin_yt.witchery.api.registry.Transformation;
import net.r0nin_yt.witchery.common.transformation.VampireTransformation;
import net.r0nin_yt.witchery.common.transformation.WerewolfTransformation;

import java.util.LinkedHashMap;
import java.util.Map;

public class WitcheryTransformations {
	private static final Map<Transformation, Identifier> TRANSFORMATIONS = new LinkedHashMap<>();

	public static final Transformation HUMAN = create("human", new Transformation());
	public static final Transformation VAMPIRE = create("vampire", new VampireTransformation());
	public static final Transformation WEREWOLF = create("werewolf", new WerewolfTransformation());

	private static <T extends Transformation> T create(String name, T transformation) {
		TRANSFORMATIONS.put(transformation, new Identifier(Witchery.MOD_ID, name));
		return transformation;
	}

	public static void init() {
		TRANSFORMATIONS.keySet().forEach(contract -> Registry.register(WitcheryRegistries.TRANSFORMATIONS, TRANSFORMATIONS.get(contract), contract));
	}
}
