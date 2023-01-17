package net.r0nin_yt.witchery.common.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.r0nin_yt.witchery.Witchery;
import net.r0nin_yt.witchery.api.item.PoppetItem;
import net.r0nin_yt.witchery.common.custom.WitcheryArmorMaterials;
import net.r0nin_yt.witchery.common.registry.WitcheryEntityTypes;
import net.r0nin_yt.witchery.common.registry.WitcheryFoodComponents;

import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.copyOf;
import static net.r0nin_yt.witchery.common.block.WitcheryBlocks.*;


public class WitcheryItems {

 public static final Item BABASHAT = create("babashat",
         new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
 public static final Item ARTHANA = create("arthana",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
 public static final Item BARKBELT = create("barkbelt",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
 public static final Item BOLINE = create("boline",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
 public static final Item BREW_DRINKABLE = create("brew_drinkable",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
 public static final Item BREWW_SPLASH = create("brew_splash",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
 public static final Item BREWBAG = create("brewbag",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
 public static final Item BUCKET_BREW = create("bucket_brew",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
 public static final Item BUCKET_HOLLOWTEARS = create("bucket_hollowtears",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
 public static final Item BUCKET_SPIRIT = create("bucket_spirit",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
 public static final Item CANESWORD = create("canesword",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
 public static final Item CHALKHEART = create("chalkheart",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
 public static final Item CHALKINFERNAL = create("chalkinfernal",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
 public static final Item CHALKRITUAL = create("chalkritual",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
 public static final Item CHALKRITUALCHARGED = create("chalkritualcharged",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
 public static final Item CIRCLETALISMAN = create("circletalisman",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
 public static final Item CHALKOTHERWHERE = create("chalkotherwhere",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
 public static final Item COFFIN = create("coffin",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item DEATHSHAND = create("deathshand",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item DEVILSTONGUECHARM = create("devilstonguecharm",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item DIVINERLAVA = create("divinerlava",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item DIVINERWATER = create("divinerwater",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item DUPGRENADE = create("dupgrenade",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item DUPSTAFF = create("dupstaff",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item EARMUFFS = create("earmuffs",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item GARLICGARLAND = create("garlicgarland",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item GLASSGOBLET = create("glassgoblet",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item GLASSGOBLETFULL = create("glassgobletfull",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item GULGGURDLE = create("gulggurdle",
            new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item  DEATHSCOWL = create("deathscowl",
            new ArmorItem(WitcheryArmorMaterials.DEATH, EquipmentSlot.HEAD,
                    new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item DEATHSROBE = create("deathsrobe",
            new ArmorItem(WitcheryArmorMaterials.DEATH, EquipmentSlot.CHEST,
                    new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item DEATHSLEGGINS = create("deathsleggins",
            new ArmorItem(WitcheryArmorMaterials.DEATH, EquipmentSlot.LEGS,
                    new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item DEATHSFEET = create("deathsfeet",
            new ArmorItem(WitcheryArmorMaterials.DEATH, EquipmentSlot.FEET,
                    new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));

    public static final Item WITCHBERRY = create("witchberry", new Item(new FabricItemSettings().food(WitcheryFoodComponents.WITCHBERRY).group(WitcheryItemGroup.WITCHERY)));
    public static final Item WITCHBERRY_PIE = create("witchberry_pie", new Item(new FabricItemSettings().food(WitcheryFoodComponents.WITCHBERRY_PIE).group(WitcheryItemGroup.WITCHERY)));
    public static final Item WITCHBERRY_COOKIE = create("witchberry_cookie", new Item(new FabricItemSettings().food(WitcheryFoodComponents.WITCHBERRY_COOKIE).group(WitcheryItemGroup.WITCHERY)));
    public static final Item OAK_BARK = create("oak_bark", new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item SPRUCE_BARK = create("spruce_bark", new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item BIRCH_BARK = create("birch_bark", new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item JUNIPER_BARK = create("juniper_bark", new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item CYPRESS_BARK = create("cypress_bark", new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item ELDER_BARK = create("elder_bark", new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
  //  public static final Item JUNIPER_BROOM = create("juniper_broom", new BroomItem(new FabricItemSettings().maxCount(1), WitcheryEntityTypes.JUNIPER_BROOM));
 //   public static final Item CYPRESS_BROOM = create("cypress_broom", new BroomItem(new FabricItemSettings().maxCount(1), WitcheryEntityTypes.CYPRESS_BROOM));
  //  public static final Item ELDER_BROOM = create("elder_broom", new BroomItem(new FabricItemSettings().maxCount(1), WitcheryEntityTypes.ELDER_BROOM));
   // public static final Item DRAGONS_BLOOD_BROOM = create("dragons_blood_broom", new DragonsBloodBroomItem(new FabricItemSettings().maxCount(1), BWEntityTypes.DRAGONS_BLOOD_BROOM).group(WitcheryItemGroup.WIRCHERY));
    public static final Item ACONITE = create("aconite", new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item ACONITE_SEEDS = create("aconite_seeds", new AliasedBlockItem(ACONITE_CROP, (new FabricItemSettings().group(WitcheryItemGroup.WITCHERY))));
    public static final Item BELLADONNA = create("belladonna", new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item BELLADONNA_SEEDS = create("belladonna_seeds", new AliasedBlockItem(BELLADONNA_CROP, (new FabricItemSettings().group(WitcheryItemGroup.WITCHERY))));
    public static final Item GARLIC = create("garlic", new AliasedBlockItem(GARLIC_CROP,(new FabricItemSettings().food(FoodComponents.POTATO).group(WitcheryItemGroup.WITCHERY))));
    public static final Item MANDRAKE_ROOT = create("mandrake_root", new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item MANDRAKE_SEEDS = create("mandrake_seeds", new AliasedBlockItem(MANDRAKE_CROP, (new FabricItemSettings().group(WitcheryItemGroup.WITCHERY))));
//    public static final Item LILITH_SPAWN_EGG = create("lilith_spawn_egg", new SpawnEggItem((EntityType<? extends MobEntity>) WitcheryEntityTypes.LILITH, 0x222621, 0xc9cbcd, (new FabricItemSettings().group(WitcheryItemGroup.WITCHERY))));
 //   public static final Item HERNE_SPAWN_EGG = create("herne_spawn_egg", new SpawnEggItem((EntityType<? extends MobEntity>) WitcheryEntityTypes.HERNE, 0x5d482d, 0x294e00, (new FabricItemSettings().group(WitcheryItemGroup.WITCHERY))));
    public static final Item VOODOO_PROTECTION_POPPET = create("voodoo_protection_poppet", new PoppetItem(new FabricItemSettings().maxCount(1).group(WitcheryItemGroup.WITCHERY)));

    public static final Item WOOD_ASH = create("wood_ash", new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));

    public static final Item SILVER_INGOT = create("silver_ingot", new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item VAMPIRE_SPAWN_EGG = create("vampire_spawn_egg", new SpawnEggItem((EntityType<? extends MobEntity>) WitcheryEntityTypes.VAMPIRE, 0x5d482d, 0x294e00, (new FabricItemSettings().group(WitcheryItemGroup.WITCHERY))));
    public static final Item SILVER_ARROW = create("silver_arrow", new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));
    public static final Item TAGLOCK = create("taglock", new Item(new FabricItemSettings().group(WitcheryItemGroup.WITCHERY)));




    private static Item create(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Witchery.MOD_ID,name), item);
    }
    public static void registerModItems() {
        Witchery.LOGGER.info("Items for mod for " + Witchery.MOD_ID);



    }

    }

