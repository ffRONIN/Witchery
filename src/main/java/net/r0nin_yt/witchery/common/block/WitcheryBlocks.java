package net.r0nin_yt.witchery.common.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;

import net.r0nin_yt.witchery.Witchery;
import net.r0nin_yt.witchery.api.block.PoppetShelfBlock;
import net.r0nin_yt.witchery.api.block.WitchAltarBlock;
import net.r0nin_yt.witchery.common.item.WitcheryItemGroup;
import net.r0nin_yt.witchery.common.util.WitcheryCropBlock;

import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.copyOf;
import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.of;



public class WitcheryBlocks {
    public static final Block ACONITE_CROP = create("aconite", new WitcheryCropBlock(copyOf(Blocks.WHEAT)), WitcheryItemGroup.WITCHERY);
    public static final Block BELLADONNA_CROP = create("belladonna", new WitcheryCropBlock(copyOf(ACONITE_CROP)), WitcheryItemGroup.WITCHERY);
    public static final Block GARLIC_CROP = create("garlic", new WitcheryCropBlock(copyOf(ACONITE_CROP)), WitcheryItemGroup.WITCHERY);
    public static final Block MANDRAKE_CROP = create("mandrake", new WitcheryCropBlock(copyOf(ACONITE_CROP)), WitcheryItemGroup.WITCHERY);
    public static final Block WITCH_CAULDRON = create("witch_cauldron", new WitchCauldronBlock(copyOf(Blocks.CAULDRON)), WitcheryItemGroup.WITCHERY);
    public static final ItemConvertible ACONITE_SEEDS = ACONITE_CROP;
    public static final ItemConvertible BELLADONNA_SEEDS = BELLADONNA_CROP;
    public static final ItemConvertible GARLIC = GARLIC_CROP;
    public static final ItemConvertible MANDRAKE_SEEDS = MANDRAKE_CROP;
    public static final Block SALT_BLOCK = create("salt_block", new Block(copyOf(Blocks.COAL_BLOCK)), WitcheryItemGroup.WITCHERY);
    public static final Block DRAGONS_BLOOD_RESIN_BLOCK = create("dragons_blood_resin_block", new Block(copyOf(Blocks.NETHER_WART_BLOCK)), WitcheryItemGroup.WITCHERY);
    public static final Block SILVER_ORE = create("silver_ore", new Block(copyOf(Blocks.GOLD_ORE)), WitcheryItemGroup.WITCHERY);
    public static final Block DEEPSLATE_SILVER_ORE = create("deepslate_silver_ore", new Block(copyOf(Blocks.DEEPSLATE_GOLD_ORE)), WitcheryItemGroup.WITCHERY);
    public static final Block SALT_ORE = create("salt_ore", new OreBlock(copyOf(Blocks.COAL_ORE), UniformIntProvider.create(0, 2)),WitcheryItemGroup.WITCHERY );
    public static final Block DEEPSLATE_SALT_ORE = create("deepslate_salt_ore", new OreBlock(copyOf(Blocks.DEEPSLATE_COAL_ORE), UniformIntProvider.create(0, 2)), WitcheryItemGroup.WITCHERY);
    public static final Block[] GOLDEN_WITCH_ALTAR = createAltar("golden_witch_altar", copyOf(Blocks.GOLD_BLOCK));
    public static final Block[] END_STONE_WITCH_ALTAR = createAltar("end_stone_witch_altar", copyOf(Blocks.END_STONE));
    public static final Block[] OBSIDIAN_WITCH_ALTAR = createAltar("obsidian_witch_altar", copyOf(Blocks.OBSIDIAN));
    public static final Block[] PURPUR_WITCH_ALTAR = createAltar("purpur_witch_altar", copyOf(Blocks.PURPUR_BLOCK));
    public static final Block[] PRISMARINE_WITCH_ALTAR = createAltar("prismarine_witch_altar", copyOf(Blocks.PRISMARINE));
    public static final Block[] NETHER_BRICK_WITCH_ALTAR = createAltar("nether_brick_witch_altar", copyOf(Blocks.NETHER_BRICKS));
    public static final Block[] BLACKSTONE_WITCH_ALTAR = createAltar("blackstone_witch_altar", copyOf(Blocks.BLACKSTONE));
    public static final Block[] STONE_WITCH_ALTAR = createAltar("stone_witch_altar", copyOf(Blocks.STONE));
    public static final Block[] MOSSY_COBBLESTONE_WITCH_ALTAR = createAltar("mossy_cobblestone_witch_altar", copyOf(Blocks.MOSSY_COBBLESTONE));
    public static final Block HEDGEWITCH_WOOL = create("hedgewitch_wool", new Block(FabricBlockSettings.of(Material.WOOL).strength(7.0f).requiresTool()), WitcheryItemGroup.WITCHERY);
    public static final Block BESMIRCHED_WOOL = create("besmirched_wool", new Block(FabricBlockSettings.of(Material.WOOL).strength(7.0f).requiresTool()), WitcheryItemGroup.WITCHERY);
    public static final Block ALCHEMIST_WOOL = create("alchemist_wool", new Block(FabricBlockSettings.of(Material.WOOL).strength(7.0f).requiresTool()), WitcheryItemGroup.WITCHERY);
    public static final Block POPPET_SHELF = create("popetshelf", new PoppetShelfBlock(copyOf(Blocks.BOOKSHELF)), WitcheryItemGroup.WITCHERY);
    public static final Block BLESSED_STONE = create("blessed_stone", new Block(copyOf(Blocks.STONE_BRICKS)), WitcheryItemGroup.WITCHERY);
    public static final Block SALT_LINE = create("salt_line", new Block(copyOf(Blocks.REDSTONE_WIRE)), WitcheryItemGroup.WITCHERY);
    public static final Block CYPRESS_PLANKS = create("cypress_planks", new Block(copyOf(Blocks.OAK_PLANKS)), WitcheryItemGroup.WITCHERY);











    private static Block create(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(Witchery.MOD_ID, name), block);
    }


    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(Witchery.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }


    private static Block[] createAltar(String name, FabricBlockSettings settings) {
        settings = settings.luminance(blockState -> blockState.get(Properties.LEVEL_15));
        Block[] altars = new Block[21];
        WitchAltarBlock unformed = (WitchAltarBlock) create(name, new WitchAltarBlock(settings, null, false), WitcheryItemGroup.WITCHERY);
        altars[0] = unformed;
        altars[1] = create("moss_" + name, new WitchAltarBlock(settings, unformed, true), WitcheryItemGroup.WITCHERY);
        for (int i = 0; i < DyeColor.values().length; i++) {
            altars[i + 2] = create(DyeColor.byId(i).getName() + "_" + name, new WitchAltarBlock(settings, unformed, true), WitcheryItemGroup.WITCHERY);
        }
        altars[18] = create("hedgewitch_" + name, new WitchAltarBlock(settings, unformed, true), WitcheryItemGroup.WITCHERY);
        altars[19] = create("alchemist_" + name, new WitchAltarBlock(settings, unformed, true), WitcheryItemGroup.WITCHERY);
        altars[20] = create("besmirched_" + name, new WitchAltarBlock(settings, unformed, true), WitcheryItemGroup.WITCHERY);
        return altars;
    }


    public static void registerModBlocks() {
        Witchery.LOGGER.info("Registring ModBlocks for" + Witchery.MOD_ID);
    }

  /*
             *
             *   public static void init() {
        FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;
        fuelRegistry.add(JUNIPER_FENCE, 300);
        fuelRegistry.add(JUNIPER_FENCE_GATE, 300);
        fuelRegistry.add(CYPRESS_FENCE, 300);
        fuelRegistry.add(CYPRESS_FENCE_GATE, 300);
        fuelRegistry.add(ELDER_FENCE, 300);
        fuelRegistry.add(ELDER_FENCE_GATE, 300);
        fuelRegistry.add(DRAGONS_BLOOD_FENCE, 300);
        fuelRegistry.add(DRAGONS_BLOOD_FENCE_GATE, 300);
        fuelRegistry.add(BWTags.BARKS, 100);
        fuelRegistry.add(SCORCHED_BRAMBLE, 800);
        FlammableBlockRegistry flammableRegistry = FlammableBlockRegistry.getDefaultInstance();
        flammableRegistry.add(STRIPPED_JUNIPER_LOG, 5, 5);
        flammableRegistry.add(STRIPPED_JUNIPER_WOOD, 5, 5);
        flammableRegistry.add(JUNIPER_LOG, 5, 5);
        flammableRegistry.add(JUNIPER_WOOD, 5, 5);
        flammableRegistry.add(JUNIPER_LEAVES, 30, 60);

        flammableRegistry.add(JUNIPER_PLANKS, 5, 20);
        flammableRegistry.add(JUNIPER_STAIRS, 5, 20);
        flammableRegistry.add(JUNIPER_SLAB, 5, 20);
        flammableRegistry.add(JUNIPER_FENCE, 5, 20);
        flammableRegistry.add(JUNIPER_FENCE_GATE, 5, 20);
        flammableRegistry.add(STRIPPED_CYPRESS_LOG, 5, 5);
        flammableRegistry.add(STRIPPED_CYPRESS_WOOD, 5, 5);
        flammableRegistry.add(CYPRESS_LOG, 5, 5);
        flammableRegistry.add(CYPRESS_WOOD, 5, 5);
        flammableRegistry.add(CYPRESS_LEAVES, 30, 60);
        flammableRegistry.add(CYPRESS_PLANKS, 5, 20);
        flammableRegistry.add(CYPRESS_STAIRS, 5, 20);
        flammableRegistry.add(CYPRESS_SLAB, 5, 20);
        flammableRegistry.add(CYPRESS_FENCE, 5, 20);
        flammableRegistry.add(CYPRESS_FENCE_GATE, 5, 20);
        flammableRegistry.add(STRIPPED_ELDER_LOG, 5, 5);

*/}

