

package net.r0nin_yt.witchery.common.registry;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.r0nin_yt.witchery.Witchery;
import net.r0nin_yt.witchery.common.enchantment.MagicProtectionEnchantment;

public class WitcheryEnchantments {
	public static final Enchantment MAGIC_PROTECTION = new MagicProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ProtectionEnchantment.Type.FIRE, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);

	public static void init() {
		Registry.register(Registry.ENCHANTMENT, new Identifier(Witchery.MOD_ID, "magic_protection"), MAGIC_PROTECTION);
	}
}
