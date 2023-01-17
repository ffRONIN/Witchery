

package net.r0nin_yt.witchery.common.registry;


import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.r0nin_yt.witchery.Witchery;
import net.r0nin_yt.witchery.api.registry.Curse;
import net.r0nin_yt.witchery.common.curse.*;

import java.util.LinkedHashMap;
import java.util.Map;


public class WitcheryCurses {
	private static final Map<Curse, Identifier> CURSES = new LinkedHashMap<>();

//	public static final Curse SOLAR_HATRED = create("solar_hatred", new SolarHatredCurse(Curse.Type.LESSER));
//	public static final Curse MISFORTUNE = create("misfortune", new MisfortuneCurse(Curse.Type.LESSER));
//	public static final Curse WEAK_LUNGS = create("weak_lungs", new WeakLungsCurse(Curse.Type.LESSER));
	public static final Curse OUTRAGE = create("outrage", new Curse(Curse.Type.LESSER));
	public static final Curse FORESTS_WRATH = create("forests_wrath", new Curse(Curse.Type.LESSER));
	public static final Curse LIGHTNING_ROD = create("lightning_rod", new Curse(Curse.Type.LESSER));
	public static final Curse UNLUCKY = create("unlucky", new Curse(Curse.Type.LESSER));
	public static final Curse COMPROMISED = create("compromised", new Curse(Curse.Type.LESSER));
	public static final Curse ARMY_OF_WORMS = create("army_of_worms", new Curse(Curse.Type.LESSER));
	public static final Curse ARACHNOPHOBIA = create("arachnophobia", new Curse(Curse.Type.LESSER));

	public static final Curse INSANITY = create("insanity", new InsanityCurse(Curse.Type.GREATER));
	public static final Curse SUSCEPTIBILITY = create("susceptibility", new SusceptibilityCurse(Curse.Type.GREATER));
	public static final Curse APATHY = create("apathy", new ApathyCurse(Curse.Type.GREATER));

	private static <T extends Curse> T create(String name, T curse) {
		CURSES.put(curse, new Identifier(Witchery.MOD_ID, name));
		return curse;
	}

	public static void init() {
		CURSES.keySet().forEach(curse -> Registry.register(WitcheryRegistries.CURSES, CURSES.get(curse), curse));
	}
}
