

package net.r0nin_yt.witchery.api.item;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.api.entity.BroomEntity;

public class BroomItem extends Item {
	private final EntityType<?> broom;

	public BroomItem(Settings settings, EntityType<?> broom) {
		super(settings);
		this.broom = broom;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos().offset(context.getSide());
		boolean client = world.isClient;
		if (world.getBlockState(pos).isAir()) {
			if (!client) {
				Entity entity = broom.create(world);
				if (entity instanceof BroomEntity broomEntity) {
					entity.updatePositionAndAngles(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, context.getPlayerYaw(), context.getPlayer() == null ? 0 : context.getPlayer().getPitch());
					if (context.getPlayer() != null) {
						context.getStack().getOrCreateNbt().putUuid("OwnerUUID", context.getPlayer().getUuid());
					}
					ItemStack stack = context.getStack().split(1);
					broomEntity.init(stack);
					broomEntity.stack = stack;
					world.spawnEntity(entity);
				}
			}
			return ActionResult.success(client);
		}
		return super.useOnBlock(context);
	}
}
