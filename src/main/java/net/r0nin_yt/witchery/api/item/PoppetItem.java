
package net.r0nin_yt.witchery.api.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.common.item.TaglockItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class PoppetItem extends Item {
	public static final Predicate<DamageSource> PROTECTION_POPPET_SOURCES = source -> source.isFire() || source.isFallingBlock() || source.isFromFalling() || source == DamageSource.CACTUS || source == DamageSource.CRAMMING || source == DamageSource.DROWN || source == DamageSource.DRYOUT || source == DamageSource.FLY_INTO_WALL || source == DamageSource.FREEZE || source == DamageSource.IN_WALL || source == DamageSource.LIGHTNING_BOLT || source == DamageSource.SWEET_BERRY_BUSH || source == DamageSource.STARVE;
	public boolean worksInShelf;

    //public final boolean worksInShelf;

	public PoppetItem(Settings settings) {
		super(settings);
//		this.worksInShelf = worksInShelf;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if (TaglockItem.hasTaglock(stack)) {
		//	tooltip.add(Text.literal(TaglockItem.getTaglockName(stack)).formatted(Formatting.GRAY));
		}
	}
}
