

package net.r0nin_yt.witchery.api.misc;


import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.common.block.entity.PoppetShelfBlockEntity;
import net.r0nin_yt.witchery.common.world.WitcheryWorldState;

public class PoppetData {
	public static final PoppetData EMPTY = new PoppetData(ItemStack.EMPTY, null, null);

	public final ItemStack stack;
	public final Long longPos;
	public final Integer index;

	public PoppetData(ItemStack stack, Long longPos, Integer index) {
		this.stack = stack;
		this.longPos = longPos;
		this.index = index;
	}

	public void update(World world, boolean sync) {
		if (longPos != null) {
			WitcheryWorldState worldState = WitcheryWorldState.get(world);
			for (int i = worldState.poppetShelves.get(longPos).size() - 1; i >= 0; i--) {
				if (i == index) {
					worldState.poppetShelves.get(longPos).set(i, stack);
					boolean empty = true;
					for (ItemStack stack : worldState.poppetShelves.get(longPos)) {
						if (!stack.isEmpty()) {
							empty = false;
							break;
						}
					}
					if (empty) {
						worldState.poppetShelves.remove(longPos);
					}
					worldState.markDirty();
					if (sync) {
						BlockPos pos = BlockPos.fromLong(longPos);
						BlockEntity blockEntity = world.getBlockEntity(pos);
						if (blockEntity instanceof PoppetShelfBlockEntity poppetShelfBlockEntity) {
							poppetShelfBlockEntity.sync();
						}
					}
				}
			}
		}
	}
}
