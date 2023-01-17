

package net.r0nin_yt.witchery.common.network.packet;

import io.netty.buffer.Unpooled;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.r0nin_yt.witchery.Witchery;
import net.r0nin_yt.witchery.api.WitcheryAPI;
import net.r0nin_yt.witchery.api.block.entity.UsesAltarPower;
import net.r0nin_yt.witchery.common.block.entity.WitchAltarBlockEntity;
import net.r0nin_yt.witchery.common.misc.WitcheryUtil;
import net.r0nin_yt.witchery.common.registry.WitcheryPledges;
import net.r0nin_yt.witchery.common.world.WitcheryWorldState;

import java.util.Map;

@SuppressWarnings("ConstantConditions")
public class CauldronTeleportPacket {
	public static final Identifier ID = new Identifier(Witchery.MOD_ID, "cauldron_teleport");

	public static void send(BlockPos cauldronPos, String message) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeBlockPos(cauldronPos);
		buf.writeString(message);
		ClientPlayNetworking.send(ID, buf);
	}

	public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
		BlockPos cauldronPos = buf.readBlockPos();
		String message = buf.readString(Short.MAX_VALUE);
		server.execute(() -> {
			World world = player.world;
			BlockPos closest = null;
			WitcheryWorldState worldState = WitcheryWorldState.get(world);
			for (Map.Entry<Long, String> entry : worldState.witchCauldrons.entrySet()) {
				if (message.equals(entry.getValue())) {
					BlockPos foundCauldronPos = BlockPos.fromLong(entry.getKey());
					if (closest == null || cauldronPos.getSquaredDistance(player.getPos()) < closest.getSquaredDistance(player.getPos())) {
						closest = foundCauldronPos;
					}
				}
			}
			if (closest != null) {
				boolean hasPower = WitcheryAPI.isPledged(player, WitcheryPledges.LEONARD);
				if (!hasPower) {
					BlockPos altarPos = ((UsesAltarPower) world.getBlockEntity(cauldronPos)).getAltarPos();
					if (altarPos != null) {
						BlockEntity altarBE = world.getBlockEntity(altarPos);
						if (altarBE instanceof WitchAltarBlockEntity altar && altar.drain((int) Math.sqrt(closest.getSquaredDistance(player.getPos())) / 2, false)) {
							hasPower = true;
						}
					}
				}
				if (hasPower) {
					WitcheryUtil.teleport(player, closest.getX() + 0.5, closest.getY() - 0.4375, closest.getZ() + 0.5, true);
				} else {
		//			player.sendMessage(Text.translatable(Witchery.MOD_ID + ".message.insufficent_altar_power", message), true);
				}
			} else {
		//		player.sendMessage(Text.translatable(Witchery.MOD_ID + ".message.invalid_cauldron", message), true);
			}
		});
	}
}
