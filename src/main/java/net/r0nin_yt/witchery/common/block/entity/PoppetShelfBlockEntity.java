

package net.r0nin_yt.witchery.common.block.entity;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.api.item.PoppetItem;
import net.r0nin_yt.witchery.common.registry.WitcheryBlockEntityTypes;
import net.r0nin_yt.witchery.common.world.WitcheryWorldState;
import org.jetbrains.annotations.Nullable;

public class PoppetShelfBlockEntity extends BlockEntity {
	@Environment(EnvType.CLIENT)
	public DefaultedList<ItemStack> clientInventory = DefaultedList.ofSize(9, ItemStack.EMPTY);

	public PoppetShelfBlockEntity(BlockPos pos, BlockState state) {
		super(WitcheryBlockEntityTypes.POPPET_SHELF, pos, state);
	}

	public void onUse(World world, BlockPos pos, PlayerEntity player, Hand hand) {
		WitcheryWorldState worldState = WitcheryWorldState.get(world);
		ItemStack stack = player.getStackInHand(hand);
		if (stack.getItem() instanceof PoppetItem) {
			int firstEmpty = getFirstEmptySlot();
			if (firstEmpty != -1) {
				DefaultedList<ItemStack> inventory = worldState.poppetShelves.get(pos.asLong());
				inventory.set(firstEmpty, stack.split(1));
				sync();
				worldState.markDirty();
			}
		} else {
			DefaultedList<ItemStack> inventory = worldState.poppetShelves.get(pos.asLong());
			if (inventory != null) {
				ItemScatterer.spawn(world, pos, inventory);
				worldState.poppetShelves.remove(pos.asLong());
				sync();
				worldState.markDirty();
			}
		}
	}

	@Override
	public NbtCompound toInitialChunkDataNbt() {
		NbtCompound nbt = super.toInitialChunkDataNbt();
		writeNbt(nbt);
		return nbt;
	}

	@Nullable
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		clientInventory.clear();
		Inventories.readNbt(nbt, clientInventory);
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		if (world != null && !world.isClient) {
			DefaultedList<ItemStack> inventory = WitcheryWorldState.get(world).poppetShelves.get(pos.asLong());
			if (inventory != null) {
				Inventories.writeNbt(nbt, inventory);
			}
		}
	}

	public void sync() {
		if (world != null && !world.isClient) {
			//	PlayerLookup.tracking(this).forEach(trackingPlayer -> SyncPoppetShelfPacket.send(trackingPlayer, pos));
		}
	}

	private int getFirstEmptySlot() {
		WitcheryWorldState worldState = WitcheryWorldState.get(world);
		DefaultedList<ItemStack> inventory = worldState.poppetShelves.get(pos.asLong());
		if (inventory != null) {
			for (int i = 0; i < inventory.size(); i++) {
				if (inventory.get(i).isEmpty()) {
					return i;
				}
			}
		} else {
			worldState.poppetShelves.put(pos.asLong(), DefaultedList.ofSize(9, ItemStack.EMPTY));
			worldState.markDirty();
			return 0;
		}
		return -1;
	}
}
