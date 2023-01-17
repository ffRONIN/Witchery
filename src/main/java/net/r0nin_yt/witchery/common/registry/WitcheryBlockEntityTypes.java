

package net.r0nin_yt.witchery.common.registry;

import com.google.common.collect.Sets;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.r0nin_yt.witchery.Witchery;
import net.r0nin_yt.witchery.common.block.WitcheryBlocks;
import net.r0nin_yt.witchery.common.block.entity.PoppetShelfBlockEntity;
import net.r0nin_yt.witchery.common.block.entity.WitchAltarBlockEntity;
import net.r0nin_yt.witchery.common.block.entity.WitchCauldronBlockEntity;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class WitcheryBlockEntityTypes {
	private static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();

	public static final BlockEntityType<WitchAltarBlockEntity> WITCH_ALTAR = create("witch_altar", FabricBlockEntityTypeBuilder.create(WitchAltarBlockEntity::new, merge(WitcheryBlocks.STONE_WITCH_ALTAR, WitcheryBlocks.MOSSY_COBBLESTONE_WITCH_ALTAR, WitcheryBlocks.PRISMARINE_WITCH_ALTAR, WitcheryBlocks.NETHER_BRICK_WITCH_ALTAR, WitcheryBlocks.BLACKSTONE_WITCH_ALTAR, WitcheryBlocks.GOLDEN_WITCH_ALTAR, WitcheryBlocks.END_STONE_WITCH_ALTAR, WitcheryBlocks.OBSIDIAN_WITCH_ALTAR, WitcheryBlocks.PURPUR_WITCH_ALTAR)).build(null));
	public static final BlockEntityType<WitchCauldronBlockEntity> WITCH_CAULDRON = create("witch_cauldron", FabricBlockEntityTypeBuilder.create(WitchCauldronBlockEntity::new, WitcheryBlocks.WITCH_CAULDRON).build(null));
	public static final BlockEntityType<PoppetShelfBlockEntity> POPPET_SHELF = create("popet_shelf", FabricBlockEntityTypeBuilder.create(PoppetShelfBlockEntity::new, WitcheryBlocks.POPPET_SHELF).build(null));

	private static <T extends BlockEntity> BlockEntityType<T> create(String name, BlockEntityType<T> type) {
		BLOCK_ENTITY_TYPES.put(type, new Identifier(Witchery.MOD_ID, name));
		return type;
	}

	private static Block[] merge(Block[]... blockArrays) {
		Set<Block> merged = new HashSet<>();
		for (Block[] blockArray : blockArrays) {
			merged.addAll(Sets.newHashSet(blockArray));
		}
		return merged.toArray(new Block[0]);
	}

	public static void init() {
		BLOCK_ENTITY_TYPES.keySet().forEach(blockEntityType -> Registry.register(Registry.BLOCK_ENTITY_TYPE, BLOCK_ENTITY_TYPES.get(blockEntityType), blockEntityType));
	}
}
