

package net.r0nin_yt.witchery.common.registry;


import net.fabricmc.fabric.api.biome.v1.*;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.FeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.r0nin_yt.witchery.Witchery;
import net.r0nin_yt.witchery.WitcheryConfig;
import net.r0nin_yt.witchery.common.block.WitcheryBlocks;

import java.util.List;
import java.util.function.Predicate;

public class WitcheryWorldGenerators {
	private static final FeatureSize EMPTY_SIZE = new TwoLayersFeatureSize(0, 0, 0);

	private static Object SimpleBlockStateProviderAccessor;
//	public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> JUNIPER_TREE = ConfiguredFeatures.register("bewitchment:juniper_tree", Feature.TREE, new TreeFeatureConfig.Builder(SimpleBlockStateProviderAccessor.callInit(WitcheryBlocks.JUNIPER_LOG.getDefaultState()), new ForkingTrunkPlacer(5, 0, 0), SimpleBlockStateProviderAccessor.callInit(WitcheryBlocks.JUNIPER_LEAVES.getDefaultState()), new AcaciaFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0)), EMPTY_SIZE).ignoreVines().build());
//	public static final RegistryEntry<PlacedFeature> JUNIPER_TREE_WITH_CHANCE = PlacedFeatures.register("bewitchment:juniper_tree_with_chance", JUNIPER_TREE, VegetationPlacedFeatures.modifiersWithWouldSurvive(RarityFilterPlacementModifier.of(10), WitcheryBlocks.JUNIPER_SAPLING));
//	public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> CYPRESS_TREE = ConfiguredFeatures.register("bewitchment:cypress_tree", Feature.TREE, new TreeFeatureConfig.Builder(SimpleBlockStateProviderAccessor.callInit(WitcheryBlocks.CYPRESS_LOG.getDefaultState()), new StraightTrunkPlacer(6, 1, 1), SimpleBlockStateProviderAccessor.callInit(WitcheryBlocks.CYPRESS_LEAVES.getDefaultState()), new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 4), EMPTY_SIZE).ignoreVines().build());
//	public static final RegistryEntry<PlacedFeature> CYPRESS_TREE_WITH_CHANCE = PlacedFeatures.register("bewitchment:cypress_tree_with_chance", CYPRESS_TREE, VegetationPlacedFeatures.modifiersWithWouldSurvive(RarityFilterPlacementModifier.of(10), WitcheryBlocks.CYPRESS_SAPLING));
//	public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> ELDER_TREE = ConfiguredFeatures.register("bewitchment:elder_tree", Feature.TREE, new TreeFeatureConfig.Builder(SimpleBlockStateProviderAccessor.callInit(WitcheryBlocks.ELDER_LOG.getDefaultState()), new StraightTrunkPlacer(4, 0, 1), SimpleBlockStateProviderAccessor.callInit(WitcheryBlocks.ELDER_LEAVES.getDefaultState()), new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 4), EMPTY_SIZE).ignoreVines().build());
//	public static final RegistryEntry<PlacedFeature> ELDER_TREE_WITH_CHANCE = PlacedFeatures.register("bewitchment:elder_tree_with_chance", ELDER_TREE, VegetationPlacedFeatures.modifiersWithWouldSurvive(RarityFilterPlacementModifier.of(10), WitcheryBlocks.ELDER_SAPLING));
//	public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> DRAGONS_BLOOD_TREE = ConfiguredFeatures.register("bewitchment:dragons_blood_tree", Feature.TREE, new TreeFeatureConfig.Builder(SimpleBlockStateProviderAccessor.callInit(WitcheryBlocks.DRAGONS_BLOOD_LOG.getDefaultState().with(WitcheryProperties.NATURAL, true)), new StraightTrunkPlacer(5, 1, 1), SimpleBlockStateProviderAccessor.callInit(WitcheryBlocks.DRAGONS_BLOOD_LEAVES.getDefaultState()), new MegaPineFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0), ConstantIntProvider.create(3)), EMPTY_SIZE).ignoreVines().build());

	public static final List<OreFeatureConfig.Target> SILVER_ORES = List.of(OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, WitcheryBlocks.SILVER_ORE.getDefaultState()), OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, WitcheryBlocks.DEEPSLATE_SILVER_ORE.getDefaultState()));
	public static final List<OreFeatureConfig.Target> SALT_ORES = List.of(OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, WitcheryBlocks.SALT_ORE.getDefaultState()), OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, WitcheryBlocks.DEEPSLATE_SALT_ORE.getDefaultState()));

	public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> SILVER_ORE = ConfiguredFeatures.register("bewitchment:silver_ore", Feature.ORE, new OreFeatureConfig(SILVER_ORES, 10));
	public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> SILVER_ORE_BURIED = ConfiguredFeatures.register("bewitchment:silver_ore_buried", Feature.ORE, new OreFeatureConfig(SILVER_ORES, 10, 0.5f));
	public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> SALT_ORE = ConfiguredFeatures.register("bewitchment:salt_ore", Feature.ORE, new OreFeatureConfig(SALT_ORES, 15));
	public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> SALT_ORE_BURIED = ConfiguredFeatures.register("bewitchment:salt_ore_buried", Feature.ORE, new OreFeatureConfig(SALT_ORES, 15, 0.5f));

//	public static final RegistryEntry<PlacedFeature> SILVER_ORE_UPPER = PlacedFeatures.register("bewitchment:silver_ore_buried", SILVER_ORE_BURIED, OrePlacedFeaturesAccessor.callModifiersWithCount(4, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-64), YOffset.fixed(32))));
//	public static final RegistryEntry<PlacedFeature> SILVER_ORE_LOWER = PlacedFeatures.register("bewitchment:silver_ore_lower", SILVER_ORE_BURIED, OrePlacedFeaturesAccessor.callModifiers(CountPlacementModifier.of(UniformIntProvider.create(0, 1)), HeightRangePlacementModifier.uniform(YOffset.fixed(-64), YOffset.fixed(-48))));

//	public static final RegistryEntry<PlacedFeature> SALT_ORE_UPPER = PlacedFeatures.register("bewitchment:salt_ore_upper", SALT_ORE, OrePlacedFeaturesAccessor.callModifiersWithCount(30, HeightRangePlacementModifier.uniform(YOffset.fixed(136), YOffset.getTop())));
//	public static final RegistryEntry<PlacedFeature> SALT_ORE_LOWER = PlacedFeatures.register("bewitchment:salt_ore_lower", SALT_ORE_BURIED, OrePlacedFeaturesAccessor.callModifiersWithCount(20, HeightRangePlacementModifier.trapezoid(YOffset.fixed(0), YOffset.fixed(192))));

	public static void init() {
		BiomeModification worldGen = BiomeModifications.create(new Identifier(Witchery.MOD_ID, "world_features"));
	//	worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ConventionalBiomeTags.SAVANNA), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, JUNIPER_TREE_WITH_CHANCE.value()));
	//	worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ConventionalBiomeTags.TAIGA).or(BiomeSelectors.tag(ConventionalBiomeTags.SWAMP)), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, CYPRESS_TREE_WITH_CHANCE.value()));
	//	worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ConventionalBiomeTags.FOREST), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ELDER_TREE_WITH_CHANCE.value()));
		worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), context -> {
			if (WitcheryConfig.generateSilver) {
		//		context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.UNDERGROUND_ORES, SILVER_ORE_UPPER.value());
		//		context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.UNDERGROUND_ORES, SILVER_ORE_LOWER.value());
			}
			if (WitcheryConfig.generateSalt) {
	//			context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.UNDERGROUND_ORES, SALT_ORE_UPPER.value());
	//			context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.UNDERGROUND_ORES, SALT_ORE_LOWER.value());
			}
		});
	//	if (registerEntitySpawn(WitcheryEntityTypes.OWL, BiomeSelectors.foundInOverworld().and(BiomeSelectors.tag(ConventionalBiomeTags.TAIGA).or(BiomeSelectors.tag(ConventionalBiomeTags.FOREST))), WitcheryConfig.owlWeight, WitcheryConfig.owlMinGroupCount, WitcheryConfig.owlMaxGroupCount)) {
	//		SpawnRestrictionAccessor.callRegister(WitcheryEntityTypes.OWL, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
	//	}
	//	if (registerEntitySpawn(WitcheryEntityTypes.RAVEN, BiomeSelectors.foundInOverworld().and(BiomeSelectors.tag(ConventionalBiomeTags.PLAINS).or(BiomeSelectors.tag(ConventionalBiomeTags.FOREST))), WitcheryConfig.ravenWeight, WitcheryConfig.ravenMinGroupCount, WitcheryConfig.ravenMaxGroupCount)) {
	//		SpawnRestrictionAccessor.callRegister(WitcheryEntityTypes.RAVEN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
	//	}
	//	if (registerEntitySpawn(WitcheryEntityTypes.SNAKE, BiomeSelectors.foundInOverworld().and(BiomeSelectors.tag(ConventionalBiomeTags.PLAINS).or(BiomeSelectors.tag(ConventionalBiomeTags.SAVANNA).or(BiomeSelectors.tag(ConventionalBiomeTags.DESERT)))), WitcheryConfig.snakeWeight, WitcheryConfig.snakeMinGroupCount, WitcheryConfig.snakeMaxGroupCount)) {
	//		SpawnRestrictionAccessor.callRegister(WitcheryEntityTypes.SNAKE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
	//	}
	//	if (registerEntitySpawn(WitcheryEntityTypes.TOAD, BiomeSelectors.foundInOverworld().and(BiomeSelectors.tag(ConventionalBiomeTags.JUNGLE).or(BiomeSelectors.tag(ConventionalBiomeTags.SWAMP))), WitcheryConfig.toadWeight, WitcheryConfig.toadMinGroupCount, WitcheryConfig.toadMaxGroupCount)) {
	//		SpawnRestrictionAccessor.callRegister(WitcheryEntityTypes.TOAD, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
	//	}
	//	if (registerEntitySpawn(WitcheryEntityTypes.HELLHOUND, BiomeSelectors.foundInTheNether(), WitcheryConfig.hellhoundWeight, WitcheryConfig.hellhoundMinGroupCount, WitcheryConfig.hellhoundMaxGroupCount)) {
	//		SpawnRestrictionAccessor.callRegister(WitcheryEntityTypes.HELLHOUND, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HellhoundEntity::canSpawn);
	//	}
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			Identifier seeds = new Identifier(Witchery.MOD_ID, "inject/seeds");
			Identifier nether_fortress = new Identifier(Witchery.MOD_ID, "inject/nether_fortress");
			if (Blocks.GRASS.getLootTableId().equals(id) || Blocks.FERN.getLootTableId().equals(id) || Blocks.TALL_GRASS.getLootTableId().equals(id) || Blocks.LARGE_FERN.getLootTableId().equals(id)) {
				tableBuilder.pool(LootPool.builder().with(LootTableEntry.builder(seeds).weight(1)).build());
			}
			if (LootTables.NETHER_BRIDGE_CHEST.equals(id)) {
				tableBuilder.pool(LootPool.builder().with(LootTableEntry.builder(nether_fortress).weight(1)).build());
			}
		});
	}

	private static boolean registerEntitySpawn(EntityType<?> type, Predicate<BiomeSelectionContext> predicate, int weight, int minGroupSize, int maxGroupSize) {
		if (weight < 0) {
			throw new UnsupportedOperationException("Could not register entity type " + type.getTranslationKey() + ": weight " + weight + " cannot be negative.");
		} else if (minGroupSize < 0) {
			throw new UnsupportedOperationException("Could not register entity type " + type.getTranslationKey() + ": minGroupSize " + minGroupSize + " cannot be negative.");
		} else if (maxGroupSize < 0) {
			throw new UnsupportedOperationException("Could not register entity type " + type.getTranslationKey() + ": maxGroupSize " + maxGroupSize + " cannot be negative.");
		} else if (minGroupSize > maxGroupSize) {
			throw new UnsupportedOperationException("Could not register entity type " + type.getTranslationKey() + ": minGroupSize " + minGroupSize + " cannot be greater than maxGroupSize " + maxGroupSize + ".");
		} else if (weight == 0 || minGroupSize == 0) {
			return false;
		}
		BiomeModifications.addSpawn(predicate, type.getSpawnGroup(), type, weight, minGroupSize, maxGroupSize);
		return true;
	}
}
