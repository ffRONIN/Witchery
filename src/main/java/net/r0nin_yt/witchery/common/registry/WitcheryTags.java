package net.r0nin_yt.witchery.common.registry;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.r0nin_yt.witchery.Witchery;

public class WitcheryTags {
	public static final TagKey<EntityType<?>> VULNERABLE_TO_SILVER = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Witchery.MOD_ID, "vulnerable_to_silver"));
	public static final TagKey<EntityType<?>> IMMUNE_TO_SILVER = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Witchery.MOD_ID, "immune_to_silver"));
	public static final TagKey<EntityType<?>> HAS_BLOOD = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Witchery.MOD_ID, "has_blood"));
	public static final TagKey<EntityType<?>> TAGLOCK_BLACKLIST = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Witchery.MOD_ID, "taglock_blacklist"));
	public static final TagKey<EntityType<?>> ENCOUNTER_FORTUNE = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Witchery.MOD_ID, "encounter_fortune"));
	public static final TagKey<EntityType<?>> INSANITY_BLACKLIST = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Witchery.MOD_ID, "insanity_blacklist"));

	public static final TagKey<Block> ORES = TagKey.of(Registry.BLOCK_KEY, new Identifier("c", "ores"));
	public static final TagKey<Item> SKULLS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "skulls"));
	public static final TagKey<Block> GIVES_ALTAR_POWER = TagKey.of(Registry.BLOCK_KEY, new Identifier(Witchery.MOD_ID, "gives_altar_power"));
	public static final TagKey<Block> HEATS_CAULDRON = TagKey.of(Registry.BLOCK_KEY, new Identifier(Witchery.MOD_ID, "heats_cauldron"));
	public static final TagKey<Block> NATURAL_TERRAIN = TagKey.of(Registry.BLOCK_KEY, new Identifier(Witchery.MOD_ID, "natural_terrain"));
	public static final TagKey<Block> UNDEAD_MASK = TagKey.of(Registry.BLOCK_KEY, new Identifier(Witchery.MOD_ID, "undead_mask"));

	public static final TagKey<Item> SILVER_INGOTS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "silver_ingots"));
	public static final TagKey<Item> SILVER_NUGGETS = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "silver_nuggets"));
	public static final TagKey<Item> BARKS = TagKey.of(Registry.ITEM_KEY, new Identifier(Witchery.MOD_ID, "barks"));
	public static final TagKey<Item> WITCHBERRY_FOODS = TagKey.of(Registry.ITEM_KEY, new Identifier(Witchery.MOD_ID, "witchberry_foods"));

	public static final TagKey<Item> SWORDS = TagKey.of(Registry.ITEM_KEY, new Identifier(Witchery.MOD_ID, "swords"));
	public static final TagKey<Item> PENTACLES = TagKey.of(Registry.ITEM_KEY, new Identifier(Witchery.MOD_ID, "pentacles"));
	public static final TagKey<Item> WANDS = TagKey.of(Registry.ITEM_KEY, new Identifier(Witchery.MOD_ID, "wands"));

	public static final TagKey<Item> WEAK_SWORDS = TagKey.of(Registry.ITEM_KEY, new Identifier(Witchery.MOD_ID, "weak_swords"));
	public static final TagKey<Item> AVERAGE_SWORDS = TagKey.of(Registry.ITEM_KEY, new Identifier(Witchery.MOD_ID, "average_swords"));
	public static final TagKey<Item> STRONG_SWORDS = TagKey.of(Registry.ITEM_KEY, new Identifier(Witchery.MOD_ID, "strong_swords"));

	public static final TagKey<Item> WEAK_PENTACLES = TagKey.of(Registry.ITEM_KEY, new Identifier(Witchery.MOD_ID, "weak_pentacles"));
	public static final TagKey<Item> AVERAGE_PENTACLES = TagKey.of(Registry.ITEM_KEY, new Identifier(Witchery.MOD_ID, "average_pentacles"));
	public static final TagKey<Item> STRONG_PENTACLES = TagKey.of(Registry.ITEM_KEY, new Identifier(Witchery.MOD_ID, "strong_pentacles"));

	public static final TagKey<Item> WEAK_WANDS = TagKey.of(Registry.ITEM_KEY, new Identifier(Witchery.MOD_ID, "weak_wands"));
	public static final TagKey<Item> AVERAGE_WANDS = TagKey.of(Registry.ITEM_KEY, new Identifier(Witchery.MOD_ID, "average_wands"));
	public static final TagKey<Item> STRONG_WANDS = TagKey.of(Registry.ITEM_KEY, new Identifier(Witchery.MOD_ID, "strong_wands"));
}
